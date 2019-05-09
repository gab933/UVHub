/*
 * Envirover confidential
 * 
 *  [2019] Envirover
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains the property of 
 * Envirover and its suppliers, if any.  The intellectual and technical concepts
 * contained herein are proprietary to Envirover and its suppliers and may be 
 * covered by U.S. and Foreign Patents, patents in process, and are protected
 * by trade secret or copyright law.
 * 
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Envirover.
 */

package com.envirover.uvhub;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.common.msg_attitude;
import com.MAVLink.common.msg_command_ack;
import com.MAVLink.common.msg_command_int;
import com.MAVLink.common.msg_command_long;
import com.MAVLink.common.msg_global_position_int;
import com.MAVLink.common.msg_gps_raw_int;
import com.MAVLink.common.msg_heartbeat;
import com.MAVLink.common.msg_high_latency;
import com.MAVLink.common.msg_mission_ack;
import com.MAVLink.common.msg_mission_clear_all;
import com.MAVLink.common.msg_mission_count;
import com.MAVLink.common.msg_mission_current;
import com.MAVLink.common.msg_mission_item;
import com.MAVLink.common.msg_mission_item_int;
import com.MAVLink.common.msg_mission_request;
import com.MAVLink.common.msg_mission_request_list;
import com.MAVLink.common.msg_mission_set_current;
import com.MAVLink.common.msg_mission_write_partial_list;
import com.MAVLink.common.msg_nav_controller_output;
import com.MAVLink.common.msg_param_request_list;
import com.MAVLink.common.msg_param_request_read;
import com.MAVLink.common.msg_param_set;
import com.MAVLink.common.msg_param_value;
import com.MAVLink.common.msg_set_home_position;
import com.MAVLink.common.msg_set_mode;
import com.MAVLink.common.msg_sys_status;
import com.MAVLink.common.msg_vfr_hud;
import com.MAVLink.enums.MAV_CMD;
import com.MAVLink.enums.MAV_MISSION_RESULT;
import com.MAVLink.enums.MAV_MODE;
import com.MAVLink.enums.MAV_RESULT;
import com.MAVLink.enums.MAV_STATE;
import com.envirover.mavlink.MAVLinkChannel;
import com.envirover.mavlink.MAVLinkLogger;
import com.envirover.uvnet.Config;
import com.envirover.uvnet.shadow.UVShadow;

/**
 * TCP MAVLink client session that handle communications with GCS clients.
 * 
 * @author Pavel Bobov
 */
public class GCSClientSession implements ClientSession {

    private final static Logger logger = LogManager.getLogger(GCSClientSession.class);

    private static final Config config = Config.getInstance();

    private final ScheduledExecutorService heartbeatTimer;
    private final MAVLinkChannel src;
    private final MAVLinkChannel dst;
    private final UVShadow shadow;

    private AtomicBoolean isOpen = new AtomicBoolean(false);
    private int desiredMissionCount = 0;
    private List<msg_mission_item> reportedMission = new ArrayList<msg_mission_item>();

    public GCSClientSession(MAVLinkChannel src, MAVLinkChannel mtMessageQueue, UVShadow shadow) {
        this.heartbeatTimer = Executors.newScheduledThreadPool(2);
        this.src = src;
        this.dst = mtMessageQueue;
        this.shadow = shadow;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.envirover.nvi.ClientSession#onOpen()
     */
    @Override
    public void onOpen() throws IOException {
        Runnable heartbeatTask = new Runnable() {
            @Override
            public void run() {
                try {
                    reportState();
                } catch (IOException | InterruptedException ex) {
                    logger.debug(ex.getMessage(), ex);
                    try {
                        onClose();
                    } catch (IOException e) {
                        logger.error(e.getMessage());
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage());
                }
            }
        };

        heartbeatTimer.scheduleAtFixedRate(heartbeatTask, 0, config.getHeartbeatInterval(), TimeUnit.MILLISECONDS);

        isOpen.set(true);

        logger.info("GCS client session opened.");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.envirover.nvi.ClientSession#onClose()
     */
    @Override
    public void onClose() throws IOException {
        if (isOpen.getAndSet(false)) {
            heartbeatTimer.shutdownNow();

            if (src != null) {
                src.close();
            }

            logger.info("GCS client session closed.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.envirover.nvi.ClientSession#onMessage(com.MAVLink.MAVLinkPacket)
     */
    @Override
    public void onMessage(MAVLinkPacket packet) throws IOException {
        handleParams(packet);
        handleMissions(packet);
        handleCommand(packet);

        if (filter(packet)) {
            dst.sendMessage(packet);
        }
    }

    @Override
    public boolean isOpen() {
        return isOpen.get();
    }

    private void handleParams(MAVLinkPacket packet) throws IOException {
        if (packet == null) {
            return;
        }

        switch (packet.msgid) {
        case msg_param_request_list.MAVLINK_MSG_ID_PARAM_REQUEST_LIST: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);

            List<msg_param_value> params = shadow.getParams(Config.getInstance().getSystemId());
            for (msg_param_value param : params) {
                sendToSource(param);

                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            logger.info(MessageFormat.format("{0} on-board parameters sent to the MAVLink client.", params.size()));
            break;
        }
        case msg_param_request_read.MAVLINK_MSG_ID_PARAM_REQUEST_READ: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);

            msg_param_request_read request = (msg_param_request_read) packet.unpack();
            // logger.info(MessageFormat.format("Sending value of parameter ''{0}'' to
            // MAVLink client.", request.param_index));
            sendToSource(shadow.getParamValue(request.target_system, request.getParam_Id(), request.param_index));
            break;
        }
        case msg_param_set.MAVLINK_MSG_ID_PARAM_SET: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);

            msg_param_set paramSet = (msg_param_set) packet.unpack();
            sendToSource(shadow.getParamValue(paramSet.target_system, paramSet.getParam_Id(), (short) -1));
            break;
        }
        }
    }

    /**
     * Handles MAVLink mission protocol communications between GCS and UV as
     * described at https://mavlink.io/en/protocol/mission.html
     * 
     * @param packet MAVLink packet
     * @throws IOException I/O exception
     */
    private void handleMissions(MAVLinkPacket packet) throws IOException {
        if (packet == null) {
            return;
        }

        switch (packet.msgid) {
        case msg_mission_request_list.MAVLINK_MSG_ID_MISSION_REQUEST_LIST: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            msg_mission_request_list msg = (msg_mission_request_list) packet.unpack();
            reportedMission = shadow.getMission(msg.target_system);
            msg_mission_count count = new msg_mission_count();
            count.count = reportedMission != null ? reportedMission.size() : 0;
            count.sysid = msg.target_system;
            count.compid = msg.target_component;
            count.target_system = (short) packet.sysid;
            count.target_component = (short) packet.compid;
            sendToSource(count);
            break;
        }
        case msg_mission_request.MAVLINK_MSG_ID_MISSION_REQUEST: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            msg_mission_request msg = (msg_mission_request) packet.unpack();
            if (reportedMission != null && msg.seq < reportedMission.size()) {
                msg_mission_item mission = reportedMission.get(msg.seq);
                mission.sysid = msg.target_system;
                mission.compid = msg.target_component;
                sendToSource(mission);
            }
            break;
        }
        case msg_mission_clear_all.MAVLINK_MSG_ID_MISSION_CLEAR_ALL: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            shadow.getDesiredMission().clear();
            desiredMissionCount = 0;
            break;
        }
        case msg_mission_count.MAVLINK_MSG_ID_MISSION_COUNT: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            msg_mission_count msg = (msg_mission_count) packet.unpack();
            shadow.getDesiredMission().clear();
            desiredMissionCount = msg.count;
            msg_mission_request request = new msg_mission_request();
            request.seq = 0;
            request.sysid = msg.target_system;
            request.compid = msg.target_component;
            request.target_system = (short) packet.sysid;
            request.target_component = (short) packet.compid;
            sendToSource(request);
            break;
        }
        case msg_mission_item.MAVLINK_MSG_ID_MISSION_ITEM: {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            msg_mission_item msg = (msg_mission_item) packet.unpack();
            shadow.getDesiredMission().add(msg);
            if (msg.seq + 1 < desiredMissionCount) {
                msg_mission_request mission_request = new msg_mission_request();
                mission_request.seq = msg.seq + 1;
                mission_request.sysid = msg.target_system;
                mission_request.compid = msg.target_component;
                mission_request.target_system = (short) packet.sysid;
                mission_request.target_component = (short) packet.compid;
                sendToSource(mission_request);
            } else {
                msg_mission_ack mission_ack = new msg_mission_ack();
                mission_ack.type = MAV_MISSION_RESULT.MAV_MISSION_ACCEPTED;
                mission_ack.sysid = msg.target_system;
                mission_ack.compid = msg.target_component;
                mission_ack.target_system = (short) packet.sysid;
                mission_ack.target_component = (short) packet.compid;
                sendToSource(mission_ack);
            }
            break;
        }
        }
    }

    /**
     * Immediately return COMMAND_ACK for COMMAND_LONG and and COMMAND_INT.
     * 
     * @param packet
     * @throws IOException
     * @throws InterruptedException
     */
    private synchronized void handleCommand(MAVLinkPacket packet) throws IOException {
        if (packet == null) {
            return;
        }

        if (packet.msgid == msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG) {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            msg_command_long msg = (msg_command_long) packet.unpack();
            msg_command_ack command_ack = new msg_command_ack();
            command_ack.command = msg.command;
            command_ack.result = MAV_RESULT.MAV_RESULT_ACCEPTED;
            command_ack.sysid = msg.target_system;
            command_ack.compid = msg.target_component;
            sendToSource(command_ack);
        } else if (packet.msgid == msg_command_int.MAVLINK_MSG_ID_COMMAND_INT) {
            MAVLinkLogger.log(Level.INFO, "<<", packet);
            msg_command_int msg = (msg_command_int) packet.unpack();
            msg_command_ack command_ack = new msg_command_ack();
            command_ack.command = msg.command;
            command_ack.result = MAV_RESULT.MAV_RESULT_ACCEPTED;
            command_ack.sysid = msg.target_system;
            command_ack.compid = msg.target_component;
            sendToSource(command_ack);
        }
    }

    // White list message filter
    private static boolean filter(MAVLinkPacket packet) {
        if (packet == null) {
            return false;
        }

        if (packet.msgid == msg_command_long.MAVLINK_MSG_ID_COMMAND_LONG) {
            int command = ((msg_command_long) packet.unpack()).command;
            return command != MAV_CMD.MAV_CMD_REQUEST_AUTOPILOT_CAPABILITIES
                    && command != 519 /* MAV_CMD_REQUEST_PROTOCOL_VERSION */;
        } else if (packet.msgid == msg_param_set.MAVLINK_MSG_ID_PARAM_SET) {
            msg_param_set paramSet = (msg_param_set) packet.unpack();
            return !OnBoardParams.getReadOnlyParamIds().contains(paramSet.getParam_Id());
        }

        return packet.msgid == msg_set_mode.MAVLINK_MSG_ID_SET_MODE
                || packet.msgid == msg_param_set.MAVLINK_MSG_ID_PARAM_SET
                || packet.msgid == msg_mission_write_partial_list.MAVLINK_MSG_ID_MISSION_WRITE_PARTIAL_LIST
                || packet.msgid == msg_mission_item.MAVLINK_MSG_ID_MISSION_ITEM
                || packet.msgid == msg_mission_set_current.MAVLINK_MSG_ID_MISSION_SET_CURRENT
                || packet.msgid == msg_mission_current.MAVLINK_MSG_ID_MISSION_CURRENT
                || packet.msgid == msg_mission_count.MAVLINK_MSG_ID_MISSION_COUNT
                || packet.msgid == msg_mission_clear_all.MAVLINK_MSG_ID_MISSION_CLEAR_ALL
                || packet.msgid == msg_mission_item_int.MAVLINK_MSG_ID_MISSION_ITEM_INT
                || packet.msgid == msg_command_int.MAVLINK_MSG_ID_COMMAND_INT
                || packet.msgid == msg_set_home_position.MAVLINK_MSG_ID_SET_HOME_POSITION;
    }

    private void sendToSource(MAVLinkMessage msg) throws IOException {
        if (msg == null) {
            return;
        }

        MAVLinkPacket packet = msg.pack();
        packet.sysid = msg.sysid;
        packet.compid = 1;
        src.sendMessage(packet);
        // MAVLinkLogger.log(Level.DEBUG, ">>", packet);
    }

    /**
     * Sends heartbeat and other status messages derived from HIGH_LATENCY message
     * to the specified client channel.
     *
     * @param dst destination channel
     * @throws IOException          if a message sending failed
     * @throws InterruptedException
     */
    private synchronized void reportState() throws IOException, InterruptedException {
        msg_high_latency msgHighLatency = (msg_high_latency) shadow.getLastMessage(Config.getInstance().getSystemId(),
                msg_high_latency.MAVLINK_MSG_ID_HIGH_LATENCY);

        sendToSource(getHeartbeatMsg(msgHighLatency));
        sendToSource(getSysStatusMsg(msgHighLatency));
        sendToSource(getGpsRawIntMsg(msgHighLatency));
        sendToSource(getAttitudeMsg(msgHighLatency));
        sendToSource(getGlobalPositionIntMsg(msgHighLatency));
        sendToSource(getMissionCurrentMsg(msgHighLatency));
        sendToSource(getNavControllerOutputMsg(msgHighLatency));
        sendToSource(getVfrHudMsg(msgHighLatency));
    }

    private MAVLinkMessage getHeartbeatMsg(msg_high_latency msgHighLatency) {
        msg_heartbeat msg = new msg_heartbeat();

        if (msgHighLatency != null) {
            msg.sysid = msgHighLatency.sysid;
            msg.compid = msgHighLatency.compid;
            msg.base_mode = msgHighLatency.base_mode;
            msg.custom_mode = msgHighLatency.custom_mode;
        } else {
            msg.sysid = Config.getInstance().getSystemId();
            msg.compid = 0;
            msg.base_mode = MAV_MODE.MAV_MODE_PREFLIGHT;
            msg.custom_mode = 0;
        }

        msg.system_status = MAV_STATE.MAV_STATE_ACTIVE;
        msg.autopilot = config.getAutopilot();
        msg.type = config.getMavType();

        return msg;
    }

    private MAVLinkMessage getSysStatusMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_sys_status msg = new msg_sys_status();
        msg.sysid = msgHighLatency.sysid;
        msg.battery_remaining = (byte) msgHighLatency.battery_remaining;
        msg.voltage_battery = msgHighLatency.temperature * 1000;
        msg.current_battery = msgHighLatency.temperature_air < 0 ? -1 : (short) (msgHighLatency.temperature_air * 100);
        return msg;
    }

    private MAVLinkMessage getGpsRawIntMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_gps_raw_int msg = new msg_gps_raw_int();
        msg.sysid = msgHighLatency.sysid;
        msg.fix_type = msgHighLatency.gps_fix_type;
        msg.satellites_visible = msgHighLatency.gps_nsat;
        msg.lat = msgHighLatency.latitude;
        msg.lon = msgHighLatency.longitude;
        msg.alt = msgHighLatency.altitude_amsl;
        return msg;
    }

    private MAVLinkMessage getAttitudeMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_attitude msg = new msg_attitude();
        msg.sysid = msgHighLatency.sysid;
        msg.yaw = (float) Math.toRadians(msgHighLatency.heading / 100.0);
        msg.pitch = (float) Math.toRadians(msgHighLatency.pitch / 100.0);
        msg.roll = (float) Math.toRadians(msgHighLatency.roll / 100.0);
        return msg;
    }

    private MAVLinkMessage getGlobalPositionIntMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_global_position_int msg = new msg_global_position_int();
        msg.sysid = msgHighLatency.sysid;
        msg.alt = msgHighLatency.altitude_amsl;
        msg.lat = msgHighLatency.latitude;
        msg.lon = msgHighLatency.longitude;
        msg.hdg = msgHighLatency.heading;
        msg.relative_alt = msgHighLatency.altitude_sp;
        return msg;
    }

    private MAVLinkMessage getMissionCurrentMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_mission_current msg = new msg_mission_current();
        msg.sysid = msgHighLatency.sysid;
        msg.seq = msgHighLatency.wp_num;
        return msg;
    }

    private MAVLinkMessage getNavControllerOutputMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_nav_controller_output msg = new msg_nav_controller_output();
        msg.sysid = msgHighLatency.sysid;
        msg.nav_bearing = (short) (msgHighLatency.heading_sp / 100);
        return msg;
    }

    private MAVLinkMessage getVfrHudMsg(msg_high_latency msgHighLatency) {
        if (msgHighLatency == null) {
            return null;
        }

        msg_vfr_hud msg = new msg_vfr_hud();
        msg.sysid = msgHighLatency.sysid;
        msg.airspeed = msgHighLatency.airspeed;
        msg.alt = msgHighLatency.altitude_amsl;
        msg.climb = msgHighLatency.climb_rate;
        msg.groundspeed = msgHighLatency.groundspeed;
        msg.heading = (short) (msgHighLatency.heading / 100);
        msg.throttle = msgHighLatency.throttle;
        return msg;
    }

}
