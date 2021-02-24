/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE LIMITS_STATUS PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Status of AP_Limits. Sent in extended status stream when AP_Limits is enabled.
 */
public class msg_limits_status extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_LIMITS_STATUS = 167;
    public static final int MAVLINK_MSG_LENGTH = 22;
    private static final long serialVersionUID = MAVLINK_MSG_ID_LIMITS_STATUS;

      
    /**
     * Time (since boot) of last breach.
     */
    public long last_trigger;
      
    /**
     * Time (since boot) of last recovery action.
     */
    public long last_action;
      
    /**
     * Time (since boot) of last successful recovery.
     */
    public long last_recovery;
      
    /**
     * Time (since boot) of last all-clear.
     */
    public long last_clear;
      
    /**
     * Number of fence breaches.
     */
    public int breach_count;
      
    /**
     * State of AP_Limits.
     */
    public short limits_state;
      
    /**
     * AP_Limit_Module bitfield of enabled modules.
     */
    public short mods_enabled;
      
    /**
     * AP_Limit_Module bitfield of required modules.
     */
    public short mods_required;
      
    /**
     * AP_Limit_Module bitfield of triggered modules.
     */
    public short mods_triggered;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_LIMITS_STATUS;
        
        packet.payload.putUnsignedInt(last_trigger);
        packet.payload.putUnsignedInt(last_action);
        packet.payload.putUnsignedInt(last_recovery);
        packet.payload.putUnsignedInt(last_clear);
        packet.payload.putUnsignedShort(breach_count);
        packet.payload.putUnsignedByte(limits_state);
        packet.payload.putUnsignedByte(mods_enabled);
        packet.payload.putUnsignedByte(mods_required);
        packet.payload.putUnsignedByte(mods_triggered);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a limits_status message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.last_trigger = payload.getUnsignedInt();
        this.last_action = payload.getUnsignedInt();
        this.last_recovery = payload.getUnsignedInt();
        this.last_clear = payload.getUnsignedInt();
        this.breach_count = payload.getUnsignedShort();
        this.limits_state = payload.getUnsignedByte();
        this.mods_enabled = payload.getUnsignedByte();
        this.mods_required = payload.getUnsignedByte();
        this.mods_triggered = payload.getUnsignedByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_limits_status() {
        this.msgid = MAVLINK_MSG_ID_LIMITS_STATUS;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_limits_status( long last_trigger, long last_action, long last_recovery, long last_clear, int breach_count, short limits_state, short mods_enabled, short mods_required, short mods_triggered) {
        this.msgid = MAVLINK_MSG_ID_LIMITS_STATUS;

        this.last_trigger = last_trigger;
        this.last_action = last_action;
        this.last_recovery = last_recovery;
        this.last_clear = last_clear;
        this.breach_count = breach_count;
        this.limits_state = limits_state;
        this.mods_enabled = mods_enabled;
        this.mods_required = mods_required;
        this.mods_triggered = mods_triggered;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_limits_status( long last_trigger, long last_action, long last_recovery, long last_clear, int breach_count, short limits_state, short mods_enabled, short mods_required, short mods_triggered, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_LIMITS_STATUS;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.last_trigger = last_trigger;
        this.last_action = last_action;
        this.last_recovery = last_recovery;
        this.last_clear = last_clear;
        this.breach_count = breach_count;
        this.limits_state = limits_state;
        this.mods_enabled = mods_enabled;
        this.mods_required = mods_required;
        this.mods_triggered = mods_triggered;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_limits_status(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_LIMITS_STATUS;
        
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
        return "MAVLINK_MSG_ID_LIMITS_STATUS - sysid:"+sysid+" compid:"+compid+" last_trigger:"+last_trigger+" last_action:"+last_action+" last_recovery:"+last_recovery+" last_clear:"+last_clear+" breach_count:"+breach_count+" limits_state:"+limits_state+" mods_enabled:"+mods_enabled+" mods_required:"+mods_required+" mods_triggered:"+mods_triggered+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_LIMITS_STATUS";
    }
}
        