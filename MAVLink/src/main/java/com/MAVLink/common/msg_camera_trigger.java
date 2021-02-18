/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE CAMERA_TRIGGER PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Camera-IMU triggering and synchronisation message.
 */
public class msg_camera_trigger extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_CAMERA_TRIGGER = 112;
    public static final int MAVLINK_MSG_LENGTH = 12;
    private static final long serialVersionUID = MAVLINK_MSG_ID_CAMERA_TRIGGER;

      
    /**
     * Timestamp for image frame (UNIX Epoch time or time since system boot). The receiving end can infer timestamp format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
     */
    public long time_usec;
      
    /**
     * Image frame sequence
     */
    public long seq;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_CAMERA_TRIGGER;
        
        packet.payload.putUnsignedLong(time_usec);
        packet.payload.putUnsignedInt(seq);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a camera_trigger message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.time_usec = payload.getUnsignedLong();
        this.seq = payload.getUnsignedInt();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_camera_trigger() {
        this.msgid = MAVLINK_MSG_ID_CAMERA_TRIGGER;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_camera_trigger( long time_usec, long seq) {
        this.msgid = MAVLINK_MSG_ID_CAMERA_TRIGGER;

        this.time_usec = time_usec;
        this.seq = seq;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_camera_trigger( long time_usec, long seq, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_CAMERA_TRIGGER;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.time_usec = time_usec;
        this.seq = seq;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_camera_trigger(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_CAMERA_TRIGGER;
        
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
        return "MAVLINK_MSG_ID_CAMERA_TRIGGER - sysid:"+sysid+" compid:"+compid+" time_usec:"+time_usec+" seq:"+seq+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_CAMERA_TRIGGER";
    }
}
        