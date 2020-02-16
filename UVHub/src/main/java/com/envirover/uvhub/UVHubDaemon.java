/*
 * Envirover confidential
 * 
 *  [2017] Envirover
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
import java.net.InetSocketAddress;
import java.util.List;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.MAVLink.common.msg_param_value;
import com.envirover.mavlink.MAVLinkMessageQueue;
import com.envirover.rockblock.RockBlockClient;
import com.envirover.rockblock.RockBlockHttpHandler;
import com.envirover.uvhub.Config;
import com.envirover.uvnet.shadow.impl.PersistentUVShadow;
import com.sun.net.httpserver.HttpServer;

/**
 * Daemon interface for UV Hub server.
 * 
 * @author Pavel Bobov
 */
@SuppressWarnings("restriction")
public class UVHubDaemon implements Daemon {
    private final static Logger logger = LogManager.getLogger(UVHubDaemon.class);

    private final Config config = Config.getInstance();

    private PersistentUVShadow shadow = null;
    private GCSTcpServer gcsTcpServer = null;
    private RRTcpServer rrTcpServer = null;
    private ShadowTcpServer shadowServer = null;
    private HttpServer httpServer = null;
    private Thread mtMsgPumpThread = null;

    @Override
    public void destroy() {
        if (httpServer != null) {
            httpServer.removeContext(config.getHttpContext());
        }
    }

    @Override
    public void init(DaemonContext context) throws DaemonInitException, IOException {
        config.init();

        logger.info(String.format("MAV type: %d, MAV system ID: %d, Autopilot class: %d", config.getMavType(),
                config.getMavSystemId(), config.getAutopilot()));

        shadow = new PersistentUVShadow(config.getShadowConnectionString());
        logger.info(String.format("Connecting to MongoDB at %s...", config.getShadowConnectionString()));

        shadow.open();

        logger.info("Connected to Elasticsearch.");

        // Load default on-board parameters for the MAV_TYPE and AUTOPILOT
        List<msg_param_value> params = OnBoardParams.getDefaultParams(config.getMavType(),
                Config.getInstance().getMavSystemId(), config.getAutopilot());

        logger.info("Loading default on-board parameters into UV shadow...");
        shadow.setParams(Config.getInstance().getMavSystemId(), params);
        logger.info(String.format("%d parameters loaded.", params.size()));

        // Mobile-terminated queue contains MAVLink messages to be sent to the vehicle.
        MAVLinkMessageQueue mtMessageQueue = new MAVLinkMessageQueue(config.getQueueSize());

        // GCS TCP server accepts GCS client connections on port 5760. It handles
        // MAVLink messages received form the connected clients and forwards some
        // of the messages to the specified mobile-terminated queue.
        gcsTcpServer = new GCSTcpServer(config.getMAVLinkPort(), mtMessageQueue, shadow, shadow);

        // Shadow TCP server accepts GCS client connections on port 5757. It handles
        // MAVLink messages received form the connected clients and updates on-board
        // parameters
        // missions in the vehicle shadow without sending any messages to the vehicle.
        shadowServer = new ShadowTcpServer(config.getShadowPort(), shadow, shadow);

        // Mobile-originated queue contains messages receive form the vehicle.
        MAVLinkMessageQueue moMessageQueue = new MAVLinkMessageQueue(config.getQueueSize());

        // Mobile-originated message handler is an implementation of MAVLinkChannel.
        // Messages sent to MOMessageHandler either used to update the vehicle's shadow,
        // or pushed to the provided mobile-originated queue.
        MOMessageHandler moHandler = new MOMessageHandler(moMessageQueue, shadow, shadow);

        // RadioRoom TCP server accepts TCP/IP connections on port 5060 from SPL
        // RadioRoom.
        // MAVLink messages received from RadioRoom are sent to the specified
        // mobile-originated
        // message handler.
        rrTcpServer = new RRTcpServer(config.getRadioRoomPort(), moHandler);

        // RockBLOCK HTTP handler listens on on port 5080 and sends mobile-originated
        // MAVLink messages received from RockBLOCK to the specified mobile-originated
        // message handler.
        httpServer = HttpServer.create(new InetSocketAddress(config.getRockblockPort()), 0);

        httpServer.createContext(config.getHttpContext(),
                new RockBlockHttpHandler(moHandler, config.getRockBlockIMEI()));
        httpServer.setExecutor(null);

        // RockBLOCK HTTP client sends MAVLink messages to RockBLOCK Web Services.
        RockBlockClient rockblock = null;

        if (config.getRockBlockIMEI() != null && !config.getRockBlockIMEI().isEmpty()) {
            rockblock = new RockBlockClient(config.getRockBlockIMEI(), config.getRockBlockUsername(),
                    config.getRockBlockPassword(), config.getRockBlockURL());

            if (config.getRockBlockUsername() == null || config.getRockBlockUsername().isEmpty()) {
                logger.warn(
                        "RockBLOCK user name is not specified. Mobile-terminated messages cannot be sent to RockBLOCK.");
            }

            if (config.getRockBlockPassword() == null || config.getRockBlockPassword().isEmpty()) {
                logger.warn(
                        "RockBLOCK password is not specified. Mobile-terminated messages cannot be sent to RockBLOCK.");
            }
        } else {
            logger.info("RockBLOCK IMEI is not specified. ISBD channel is disabled.");
        }

        // Mobile-terminated message pump pumps MAVLink messages from the specified
        // mobile-terminated queue to the last connected RadioRoom TCP client or
        // the specified RockBLOCK HTTP client.
        MTMessagePump mtMsgPump = new MTMessagePump(mtMessageQueue, rrTcpServer, rockblock);
        mtMsgPumpThread = new Thread(mtMsgPump, "mt-message-pump");
    }

    @Override
    public void start() throws Exception {
        // Start all the server threads.
        gcsTcpServer.start();
        shadowServer.start();
        rrTcpServer.start();
        httpServer.start();
        mtMsgPumpThread.start();

        Thread.sleep(1000);

        logger.info("UV Hub started.");
    }

    @Override
    public void stop() throws Exception {
        // Stop all the server threads.
        if (mtMsgPumpThread != null) {
            mtMsgPumpThread.interrupt();
        }

        if (httpServer != null) {
            httpServer.stop(0);
        }

        if (rrTcpServer != null) {
            rrTcpServer.stop();
        }

        if (shadowServer != null) {
            shadowServer.stop();
        }

        if (gcsTcpServer != null) {
            gcsTcpServer.stop();
        }

        if (shadow != null) {
            shadow.close();
        }

        Thread.sleep(1000);

        logger.info("UV Hub stopped.");
    }

}
