/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE MOUNT_CONTROL PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Message to control a camera mount, directional antenna, etc.
 */
public class msg_mount_control extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_MOUNT_CONTROL = 157;
    public static final int MAVLINK_MSG_LENGTH = 15;
    private static final long serialVersionUID = MAVLINK_MSG_ID_MOUNT_CONTROL;

      
    /**
     * Pitch (centi-degrees) or lat (degE7), depending on mount mode.
     */
    public int input_a;
      
    /**
     * Roll (centi-degrees) or lon (degE7) depending on mount mode.
     */
    public int input_b;
      
    /**
     * Yaw (centi-degrees) or alt (cm) depending on mount mode.
     */
    public int input_c;
      
    /**
     * System ID.
     */
    public short target_system;
      
    /**
     * Component ID.
     */
    public short target_component;
      
    /**
     * If "1" it will save current trimmed position on EEPROM (just valid for NEUTRAL and LANDING).
     */
    public short save_position;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_MOUNT_CONTROL;
        
        packet.payload.putInt(input_a);
        packet.payload.putInt(input_b);
        packet.payload.putInt(input_c);
        packet.payload.putUnsignedByte(target_system);
        packet.payload.putUnsignedByte(target_component);
        packet.payload.putUnsignedByte(save_position);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a mount_control message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.input_a = payload.getInt();
        this.input_b = payload.getInt();
        this.input_c = payload.getInt();
        this.target_system = payload.getUnsignedByte();
        this.target_component = payload.getUnsignedByte();
        this.save_position = payload.getUnsignedByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_mount_control() {
        this.msgid = MAVLINK_MSG_ID_MOUNT_CONTROL;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_mount_control( int input_a, int input_b, int input_c, short target_system, short target_component, short save_position) {
        this.msgid = MAVLINK_MSG_ID_MOUNT_CONTROL;

        this.input_a = input_a;
        this.input_b = input_b;
        this.input_c = input_c;
        this.target_system = target_system;
        this.target_component = target_component;
        this.save_position = save_position;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_mount_control( int input_a, int input_b, int input_c, short target_system, short target_component, short save_position, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_MOUNT_CONTROL;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.input_a = input_a;
        this.input_b = input_b;
        this.input_c = input_c;
        this.target_system = target_system;
        this.target_component = target_component;
        this.save_position = save_position;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_mount_control(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_MOUNT_CONTROL;
        
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
        return "MAVLINK_MSG_ID_MOUNT_CONTROL - sysid:"+sysid+" compid:"+compid+" input_a:"+input_a+" input_b:"+input_b+" input_c:"+input_c+" target_system:"+target_system+" target_component:"+target_component+" save_position:"+save_position+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_MOUNT_CONTROL";
    }
}
        