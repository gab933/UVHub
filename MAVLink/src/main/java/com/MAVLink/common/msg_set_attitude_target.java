/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE SET_ATTITUDE_TARGET PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Sets a desired vehicle attitude. Used by an external controller to command the vehicle (manual controller or other system).
 */
public class msg_set_attitude_target extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_SET_ATTITUDE_TARGET = 82;
    public static final int MAVLINK_MSG_LENGTH = 39;
    private static final long serialVersionUID = MAVLINK_MSG_ID_SET_ATTITUDE_TARGET;

      
    /**
     * Timestamp (time since system boot).
     */
    public long time_boot_ms;
      
    /**
     * Attitude quaternion (w, x, y, z order, zero-rotation is 1, 0, 0, 0)
     */
    public float q[] = new float[4];
      
    /**
     * Body roll rate
     */
    public float body_roll_rate;
      
    /**
     * Body pitch rate
     */
    public float body_pitch_rate;
      
    /**
     * Body yaw rate
     */
    public float body_yaw_rate;
      
    /**
     * Collective thrust, normalized to 0 .. 1 (-1 .. 1 for vehicles capable of reverse trust)
     */
    public float thrust;
      
    /**
     * System ID
     */
    public short target_system;
      
    /**
     * Component ID
     */
    public short target_component;
      
    /**
     * Bitmap to indicate which dimensions should be ignored by the vehicle.
     */
    public short type_mask;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_SET_ATTITUDE_TARGET;
        
        packet.payload.putUnsignedInt(time_boot_ms);
        
        for (int i = 0; i < q.length; i++) {
            packet.payload.putFloat(q[i]);
        }
                    
        packet.payload.putFloat(body_roll_rate);
        packet.payload.putFloat(body_pitch_rate);
        packet.payload.putFloat(body_yaw_rate);
        packet.payload.putFloat(thrust);
        packet.payload.putUnsignedByte(target_system);
        packet.payload.putUnsignedByte(target_component);
        packet.payload.putUnsignedByte(type_mask);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a set_attitude_target message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.time_boot_ms = payload.getUnsignedInt();
         
        for (int i = 0; i < this.q.length; i++) {
            this.q[i] = payload.getFloat();
        }
                
        this.body_roll_rate = payload.getFloat();
        this.body_pitch_rate = payload.getFloat();
        this.body_yaw_rate = payload.getFloat();
        this.thrust = payload.getFloat();
        this.target_system = payload.getUnsignedByte();
        this.target_component = payload.getUnsignedByte();
        this.type_mask = payload.getUnsignedByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_set_attitude_target() {
        this.msgid = MAVLINK_MSG_ID_SET_ATTITUDE_TARGET;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_set_attitude_target( long time_boot_ms, float[] q, float body_roll_rate, float body_pitch_rate, float body_yaw_rate, float thrust, short target_system, short target_component, short type_mask) {
        this.msgid = MAVLINK_MSG_ID_SET_ATTITUDE_TARGET;

        this.time_boot_ms = time_boot_ms;
        this.q = q;
        this.body_roll_rate = body_roll_rate;
        this.body_pitch_rate = body_pitch_rate;
        this.body_yaw_rate = body_yaw_rate;
        this.thrust = thrust;
        this.target_system = target_system;
        this.target_component = target_component;
        this.type_mask = type_mask;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_set_attitude_target( long time_boot_ms, float[] q, float body_roll_rate, float body_pitch_rate, float body_yaw_rate, float thrust, short target_system, short target_component, short type_mask, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_SET_ATTITUDE_TARGET;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.time_boot_ms = time_boot_ms;
        this.q = q;
        this.body_roll_rate = body_roll_rate;
        this.body_pitch_rate = body_pitch_rate;
        this.body_yaw_rate = body_yaw_rate;
        this.thrust = thrust;
        this.target_system = target_system;
        this.target_component = target_component;
        this.type_mask = type_mask;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_set_attitude_target(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_SET_ATTITUDE_TARGET;
        
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
        return "MAVLINK_MSG_ID_SET_ATTITUDE_TARGET - sysid:"+sysid+" compid:"+compid+" time_boot_ms:"+time_boot_ms+" q:"+q+" body_roll_rate:"+body_roll_rate+" body_pitch_rate:"+body_pitch_rate+" body_yaw_rate:"+body_yaw_rate+" thrust:"+thrust+" target_system:"+target_system+" target_component:"+target_component+" type_mask:"+type_mask+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_SET_ATTITUDE_TARGET";
    }
}
        