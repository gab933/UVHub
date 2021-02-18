/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE HIL_STATE_QUATERNION PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Sent from simulation to autopilot, avoids in contrast to HIL_STATE singularities. This packet is useful for high throughput applications such as hardware in the loop simulations.
 */
public class msg_hil_state_quaternion extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_HIL_STATE_QUATERNION = 115;
    public static final int MAVLINK_MSG_LENGTH = 64;
    private static final long serialVersionUID = MAVLINK_MSG_ID_HIL_STATE_QUATERNION;

      
    /**
     * Timestamp (UNIX Epoch time or time since system boot). The receiving end can infer timestamp format (since 1.1.1970 or since system boot) by checking for the magnitude of the number.
     */
    public long time_usec;
      
    /**
     * Vehicle attitude expressed as normalized quaternion in w, x, y, z order (with 1 0 0 0 being the null-rotation)
     */
    public float attitude_quaternion[] = new float[4];
      
    /**
     * Body frame roll / phi angular speed
     */
    public float rollspeed;
      
    /**
     * Body frame pitch / theta angular speed
     */
    public float pitchspeed;
      
    /**
     * Body frame yaw / psi angular speed
     */
    public float yawspeed;
      
    /**
     * Latitude
     */
    public int lat;
      
    /**
     * Longitude
     */
    public int lon;
      
    /**
     * Altitude
     */
    public int alt;
      
    /**
     * Ground X Speed (Latitude)
     */
    public short vx;
      
    /**
     * Ground Y Speed (Longitude)
     */
    public short vy;
      
    /**
     * Ground Z Speed (Altitude)
     */
    public short vz;
      
    /**
     * Indicated airspeed
     */
    public int ind_airspeed;
      
    /**
     * True airspeed
     */
    public int true_airspeed;
      
    /**
     * X acceleration
     */
    public short xacc;
      
    /**
     * Y acceleration
     */
    public short yacc;
      
    /**
     * Z acceleration
     */
    public short zacc;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_HIL_STATE_QUATERNION;
        
        packet.payload.putUnsignedLong(time_usec);
        
        for (int i = 0; i < attitude_quaternion.length; i++) {
            packet.payload.putFloat(attitude_quaternion[i]);
        }
                    
        packet.payload.putFloat(rollspeed);
        packet.payload.putFloat(pitchspeed);
        packet.payload.putFloat(yawspeed);
        packet.payload.putInt(lat);
        packet.payload.putInt(lon);
        packet.payload.putInt(alt);
        packet.payload.putShort(vx);
        packet.payload.putShort(vy);
        packet.payload.putShort(vz);
        packet.payload.putUnsignedShort(ind_airspeed);
        packet.payload.putUnsignedShort(true_airspeed);
        packet.payload.putShort(xacc);
        packet.payload.putShort(yacc);
        packet.payload.putShort(zacc);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a hil_state_quaternion message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.time_usec = payload.getUnsignedLong();
         
        for (int i = 0; i < this.attitude_quaternion.length; i++) {
            this.attitude_quaternion[i] = payload.getFloat();
        }
                
        this.rollspeed = payload.getFloat();
        this.pitchspeed = payload.getFloat();
        this.yawspeed = payload.getFloat();
        this.lat = payload.getInt();
        this.lon = payload.getInt();
        this.alt = payload.getInt();
        this.vx = payload.getShort();
        this.vy = payload.getShort();
        this.vz = payload.getShort();
        this.ind_airspeed = payload.getUnsignedShort();
        this.true_airspeed = payload.getUnsignedShort();
        this.xacc = payload.getShort();
        this.yacc = payload.getShort();
        this.zacc = payload.getShort();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_hil_state_quaternion() {
        this.msgid = MAVLINK_MSG_ID_HIL_STATE_QUATERNION;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_hil_state_quaternion( long time_usec, float[] attitude_quaternion, float rollspeed, float pitchspeed, float yawspeed, int lat, int lon, int alt, short vx, short vy, short vz, int ind_airspeed, int true_airspeed, short xacc, short yacc, short zacc) {
        this.msgid = MAVLINK_MSG_ID_HIL_STATE_QUATERNION;

        this.time_usec = time_usec;
        this.attitude_quaternion = attitude_quaternion;
        this.rollspeed = rollspeed;
        this.pitchspeed = pitchspeed;
        this.yawspeed = yawspeed;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.ind_airspeed = ind_airspeed;
        this.true_airspeed = true_airspeed;
        this.xacc = xacc;
        this.yacc = yacc;
        this.zacc = zacc;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_hil_state_quaternion( long time_usec, float[] attitude_quaternion, float rollspeed, float pitchspeed, float yawspeed, int lat, int lon, int alt, short vx, short vy, short vz, int ind_airspeed, int true_airspeed, short xacc, short yacc, short zacc, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_HIL_STATE_QUATERNION;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.time_usec = time_usec;
        this.attitude_quaternion = attitude_quaternion;
        this.rollspeed = rollspeed;
        this.pitchspeed = pitchspeed;
        this.yawspeed = yawspeed;
        this.lat = lat;
        this.lon = lon;
        this.alt = alt;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        this.ind_airspeed = ind_airspeed;
        this.true_airspeed = true_airspeed;
        this.xacc = xacc;
        this.yacc = yacc;
        this.zacc = zacc;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_hil_state_quaternion(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_HIL_STATE_QUATERNION;
        
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
        return "MAVLINK_MSG_ID_HIL_STATE_QUATERNION - sysid:"+sysid+" compid:"+compid+" time_usec:"+time_usec+" attitude_quaternion:"+attitude_quaternion+" rollspeed:"+rollspeed+" pitchspeed:"+pitchspeed+" yawspeed:"+yawspeed+" lat:"+lat+" lon:"+lon+" alt:"+alt+" vx:"+vx+" vy:"+vy+" vz:"+vz+" ind_airspeed:"+ind_airspeed+" true_airspeed:"+true_airspeed+" xacc:"+xacc+" yacc:"+yacc+" zacc:"+zacc+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_HIL_STATE_QUATERNION";
    }
}
        