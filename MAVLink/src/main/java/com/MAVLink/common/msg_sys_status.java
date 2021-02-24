/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE SYS_STATUS PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * The general system state. If the system is following the MAVLink standard, the system state is mainly defined by three orthogonal states/modes: The system mode, which is either LOCKED (motors shut down and locked), MANUAL (system under RC control), GUIDED (system with autonomous position control, position setpoint controlled manually) or AUTO (system guided by path/waypoint planner). The NAV_MODE defined the current flight state: LIFTOFF (often an open-loop maneuver), LANDING, WAYPOINTS or VECTOR. This represents the internal navigation state machine. The system status shows whether the system is currently active or not and if an emergency occurred. During the CRITICAL and EMERGENCY states the MAV is still considered to be active, but should start emergency procedures autonomously. After a failure occurred it should first move from active to critical to allow manual intervention and then move to emergency after a certain timeout.
 */
public class msg_sys_status extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_SYS_STATUS = 1;
    public static final int MAVLINK_MSG_LENGTH = 31;
    private static final long serialVersionUID = MAVLINK_MSG_ID_SYS_STATUS;

      
    /**
     * Bitmap showing which onboard controllers and sensors are present. Value of 0: not present. Value of 1: present.
     */
    public long onboard_control_sensors_present;
      
    /**
     * Bitmap showing which onboard controllers and sensors are enabled:  Value of 0: not enabled. Value of 1: enabled.
     */
    public long onboard_control_sensors_enabled;
      
    /**
     * Bitmap showing which onboard controllers and sensors have an error (or are operational). Value of 0: error. Value of 1: healthy.
     */
    public long onboard_control_sensors_health;
      
    /**
     * Maximum usage in percent of the mainloop time. Values: [0-1000] - should always be below 1000
     */
    public int load;
      
    /**
     * Battery voltage, UINT16_MAX: Voltage not sent by autopilot
     */
    public int voltage_battery;
      
    /**
     * Battery current, -1: Current not sent by autopilot
     */
    public short current_battery;
      
    /**
     * Communication drop rate, (UART, I2C, SPI, CAN), dropped packets on all links (packets that were corrupted on reception on the MAV)
     */
    public int drop_rate_comm;
      
    /**
     * Communication errors (UART, I2C, SPI, CAN), dropped packets on all links (packets that were corrupted on reception on the MAV)
     */
    public int errors_comm;
      
    /**
     * Autopilot-specific errors
     */
    public int errors_count1;
      
    /**
     * Autopilot-specific errors
     */
    public int errors_count2;
      
    /**
     * Autopilot-specific errors
     */
    public int errors_count3;
      
    /**
     * Autopilot-specific errors
     */
    public int errors_count4;
      
    /**
     * Battery energy remaining, -1: Battery remaining energy not sent by autopilot
     */
    public byte battery_remaining;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_SYS_STATUS;
        
        packet.payload.putUnsignedInt(onboard_control_sensors_present);
        packet.payload.putUnsignedInt(onboard_control_sensors_enabled);
        packet.payload.putUnsignedInt(onboard_control_sensors_health);
        packet.payload.putUnsignedShort(load);
        packet.payload.putUnsignedShort(voltage_battery);
        packet.payload.putShort(current_battery);
        packet.payload.putUnsignedShort(drop_rate_comm);
        packet.payload.putUnsignedShort(errors_comm);
        packet.payload.putUnsignedShort(errors_count1);
        packet.payload.putUnsignedShort(errors_count2);
        packet.payload.putUnsignedShort(errors_count3);
        packet.payload.putUnsignedShort(errors_count4);
        packet.payload.putByte(battery_remaining);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a sys_status message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.onboard_control_sensors_present = payload.getUnsignedInt();
        this.onboard_control_sensors_enabled = payload.getUnsignedInt();
        this.onboard_control_sensors_health = payload.getUnsignedInt();
        this.load = payload.getUnsignedShort();
        this.voltage_battery = payload.getUnsignedShort();
        this.current_battery = payload.getShort();
        this.drop_rate_comm = payload.getUnsignedShort();
        this.errors_comm = payload.getUnsignedShort();
        this.errors_count1 = payload.getUnsignedShort();
        this.errors_count2 = payload.getUnsignedShort();
        this.errors_count3 = payload.getUnsignedShort();
        this.errors_count4 = payload.getUnsignedShort();
        this.battery_remaining = payload.getByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_sys_status() {
        this.msgid = MAVLINK_MSG_ID_SYS_STATUS;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_sys_status( long onboard_control_sensors_present, long onboard_control_sensors_enabled, long onboard_control_sensors_health, int load, int voltage_battery, short current_battery, int drop_rate_comm, int errors_comm, int errors_count1, int errors_count2, int errors_count3, int errors_count4, byte battery_remaining) {
        this.msgid = MAVLINK_MSG_ID_SYS_STATUS;

        this.onboard_control_sensors_present = onboard_control_sensors_present;
        this.onboard_control_sensors_enabled = onboard_control_sensors_enabled;
        this.onboard_control_sensors_health = onboard_control_sensors_health;
        this.load = load;
        this.voltage_battery = voltage_battery;
        this.current_battery = current_battery;
        this.drop_rate_comm = drop_rate_comm;
        this.errors_comm = errors_comm;
        this.errors_count1 = errors_count1;
        this.errors_count2 = errors_count2;
        this.errors_count3 = errors_count3;
        this.errors_count4 = errors_count4;
        this.battery_remaining = battery_remaining;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_sys_status( long onboard_control_sensors_present, long onboard_control_sensors_enabled, long onboard_control_sensors_health, int load, int voltage_battery, short current_battery, int drop_rate_comm, int errors_comm, int errors_count1, int errors_count2, int errors_count3, int errors_count4, byte battery_remaining, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_SYS_STATUS;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.onboard_control_sensors_present = onboard_control_sensors_present;
        this.onboard_control_sensors_enabled = onboard_control_sensors_enabled;
        this.onboard_control_sensors_health = onboard_control_sensors_health;
        this.load = load;
        this.voltage_battery = voltage_battery;
        this.current_battery = current_battery;
        this.drop_rate_comm = drop_rate_comm;
        this.errors_comm = errors_comm;
        this.errors_count1 = errors_count1;
        this.errors_count2 = errors_count2;
        this.errors_count3 = errors_count3;
        this.errors_count4 = errors_count4;
        this.battery_remaining = battery_remaining;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_sys_status(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_SYS_STATUS;
        
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.isMavlink2 = mavLinkPacket.isMavlink2;
        unpack(mavLinkPacket.payload);
    }

                              
    /**
     * Returns a string with the MSG name and data
     */
    @Override
    public String toString() {
        return "MAVLINK_MSG_ID_SYS_STATUS - sysid:"+sysid+" compid:"+compid+" onboard_control_sensors_present:"+onboard_control_sensors_present+" onboard_control_sensors_enabled:"+onboard_control_sensors_enabled+" onboard_control_sensors_health:"+onboard_control_sensors_health+" load:"+load+" voltage_battery:"+voltage_battery+" current_battery:"+current_battery+" drop_rate_comm:"+drop_rate_comm+" errors_comm:"+errors_comm+" errors_count1:"+errors_count1+" errors_count2:"+errors_count2+" errors_count3:"+errors_count3+" errors_count4:"+errors_count4+" battery_remaining:"+battery_remaining+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_SYS_STATUS";
    }
}
        