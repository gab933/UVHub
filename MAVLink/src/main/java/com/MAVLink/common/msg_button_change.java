/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE BUTTON_CHANGE PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Report button state change.
 */
public class msg_button_change extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_BUTTON_CHANGE = 257;
    public static final int MAVLINK_MSG_LENGTH = 9;
    private static final long serialVersionUID = MAVLINK_MSG_ID_BUTTON_CHANGE;

      
    /**
     * Timestamp (time since system boot).
     */
    public long time_boot_ms;
      
    /**
     * Time of last change of button state.
     */
    public long last_change_ms;
      
    /**
     * Bitmap for state of buttons.
     */
    public short state;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_BUTTON_CHANGE;
        
        packet.payload.putUnsignedInt(time_boot_ms);
        packet.payload.putUnsignedInt(last_change_ms);
        packet.payload.putUnsignedByte(state);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a button_change message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.time_boot_ms = payload.getUnsignedInt();
        this.last_change_ms = payload.getUnsignedInt();
        this.state = payload.getUnsignedByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_button_change() {
        this.msgid = MAVLINK_MSG_ID_BUTTON_CHANGE;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_button_change( long time_boot_ms, long last_change_ms, short state) {
        this.msgid = MAVLINK_MSG_ID_BUTTON_CHANGE;

        this.time_boot_ms = time_boot_ms;
        this.last_change_ms = last_change_ms;
        this.state = state;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_button_change( long time_boot_ms, long last_change_ms, short state, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_BUTTON_CHANGE;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.time_boot_ms = time_boot_ms;
        this.last_change_ms = last_change_ms;
        this.state = state;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_button_change(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_BUTTON_CHANGE;
        
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
        return "MAVLINK_MSG_ID_BUTTON_CHANGE - sysid:"+sysid+" compid:"+compid+" time_boot_ms:"+time_boot_ms+" last_change_ms:"+last_change_ms+" state:"+state+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_BUTTON_CHANGE";
    }
}
        