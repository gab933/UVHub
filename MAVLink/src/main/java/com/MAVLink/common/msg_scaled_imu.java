/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE SCALED_IMU PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * The RAW IMU readings for the usual 9DOF sensor setup. This message should contain the scaled values to the described units
 */
public class msg_scaled_imu extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_SCALED_IMU = 26;
    public static final int MAVLINK_MSG_LENGTH = 24;
    private static final long serialVersionUID = MAVLINK_MSG_ID_SCALED_IMU;

      
    /**
     * Timestamp (time since system boot).
     */
    public long time_boot_ms;
      
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
     * Angular speed around X axis
     */
    public short xgyro;
      
    /**
     * Angular speed around Y axis
     */
    public short ygyro;
      
    /**
     * Angular speed around Z axis
     */
    public short zgyro;
      
    /**
     * X Magnetic field
     */
    public short xmag;
      
    /**
     * Y Magnetic field
     */
    public short ymag;
      
    /**
     * Z Magnetic field
     */
    public short zmag;
      
    /**
     * Temperature, 0: IMU does not provide temperature values. If the IMU is at 0C it must send 1 (0.01C).
     */
    public short temperature;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_SCALED_IMU;
        
        packet.payload.putUnsignedInt(time_boot_ms);
        packet.payload.putShort(xacc);
        packet.payload.putShort(yacc);
        packet.payload.putShort(zacc);
        packet.payload.putShort(xgyro);
        packet.payload.putShort(ygyro);
        packet.payload.putShort(zgyro);
        packet.payload.putShort(xmag);
        packet.payload.putShort(ymag);
        packet.payload.putShort(zmag);
        
        if (isMavlink2) {
             packet.payload.putShort(temperature);
            
        }
        return packet;
    }

    /**
     * Decode a scaled_imu message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.time_boot_ms = payload.getUnsignedInt();
        this.xacc = payload.getShort();
        this.yacc = payload.getShort();
        this.zacc = payload.getShort();
        this.xgyro = payload.getShort();
        this.ygyro = payload.getShort();
        this.zgyro = payload.getShort();
        this.xmag = payload.getShort();
        this.ymag = payload.getShort();
        this.zmag = payload.getShort();
        
        if (isMavlink2) {
             this.temperature = payload.getShort();
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_scaled_imu() {
        this.msgid = MAVLINK_MSG_ID_SCALED_IMU;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_scaled_imu( long time_boot_ms, short xacc, short yacc, short zacc, short xgyro, short ygyro, short zgyro, short xmag, short ymag, short zmag, short temperature) {
        this.msgid = MAVLINK_MSG_ID_SCALED_IMU;

        this.time_boot_ms = time_boot_ms;
        this.xacc = xacc;
        this.yacc = yacc;
        this.zacc = zacc;
        this.xgyro = xgyro;
        this.ygyro = ygyro;
        this.zgyro = zgyro;
        this.xmag = xmag;
        this.ymag = ymag;
        this.zmag = zmag;
        this.temperature = temperature;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_scaled_imu( long time_boot_ms, short xacc, short yacc, short zacc, short xgyro, short ygyro, short zgyro, short xmag, short ymag, short zmag, short temperature, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_SCALED_IMU;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.time_boot_ms = time_boot_ms;
        this.xacc = xacc;
        this.yacc = yacc;
        this.zacc = zacc;
        this.xgyro = xgyro;
        this.ygyro = ygyro;
        this.zgyro = zgyro;
        this.xmag = xmag;
        this.ymag = ymag;
        this.zmag = zmag;
        this.temperature = temperature;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_scaled_imu(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_SCALED_IMU;
        
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
        return "MAVLINK_MSG_ID_SCALED_IMU - sysid:"+sysid+" compid:"+compid+" time_boot_ms:"+time_boot_ms+" xacc:"+xacc+" yacc:"+yacc+" zacc:"+zacc+" xgyro:"+xgyro+" ygyro:"+ygyro+" zgyro:"+zgyro+" xmag:"+xmag+" ymag:"+ymag+" zmag:"+zmag+" temperature:"+temperature+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_SCALED_IMU";
    }
}
        