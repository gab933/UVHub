/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE OSD_PARAM_SHOW_CONFIG_REPLY PACKING
package com.MAVLink.ardupilotmega;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Read configured OSD parameter reply.
 */
public class msg_osd_param_show_config_reply extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY = 11036;
    public static final int MAVLINK_MSG_LENGTH = 34;
    private static final long serialVersionUID = MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY;

      
    /**
     * Request ID - copied from request.
     */
    public long request_id;
      
    /**
     * OSD parameter minimum value.
     */
    public float min_value;
      
    /**
     * OSD parameter maximum value.
     */
    public float max_value;
      
    /**
     * OSD parameter increment.
     */
    public float increment;
      
    /**
     * Config error type.
     */
    public short result;
      
    /**
     * Onboard parameter id, terminated by NULL if the length is less than 16 human-readable chars and WITHOUT null termination (NULL) byte if the length is exactly 16 chars - applications have to provide 16+1 bytes storage if the ID is stored as string
     */
    public byte param_id[] = new byte[16];
      
    /**
     * Config type.
     */
    public short config_type;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY;
        
        packet.payload.putUnsignedInt(request_id);
        packet.payload.putFloat(min_value);
        packet.payload.putFloat(max_value);
        packet.payload.putFloat(increment);
        packet.payload.putUnsignedByte(result);
        
        for (int i = 0; i < param_id.length; i++) {
            packet.payload.putByte(param_id[i]);
        }
                    
        packet.payload.putUnsignedByte(config_type);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a osd_param_show_config_reply message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.request_id = payload.getUnsignedInt();
        this.min_value = payload.getFloat();
        this.max_value = payload.getFloat();
        this.increment = payload.getFloat();
        this.result = payload.getUnsignedByte();
         
        for (int i = 0; i < this.param_id.length; i++) {
            this.param_id[i] = payload.getByte();
        }
                
        this.config_type = payload.getUnsignedByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_osd_param_show_config_reply() {
        this.msgid = MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_osd_param_show_config_reply( long request_id, float min_value, float max_value, float increment, short result, byte[] param_id, short config_type) {
        this.msgid = MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY;

        this.request_id = request_id;
        this.min_value = min_value;
        this.max_value = max_value;
        this.increment = increment;
        this.result = result;
        this.param_id = param_id;
        this.config_type = config_type;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_osd_param_show_config_reply( long request_id, float min_value, float max_value, float increment, short result, byte[] param_id, short config_type, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.request_id = request_id;
        this.min_value = min_value;
        this.max_value = max_value;
        this.increment = increment;
        this.result = result;
        this.param_id = param_id;
        this.config_type = config_type;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_osd_param_show_config_reply(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY;
        
        this.sysid = mavLinkPacket.sysid;
        this.compid = mavLinkPacket.compid;
        this.isMavlink2 = mavLinkPacket.isMavlink2;
        unpack(mavLinkPacket.payload);
    }

               
    /**
    * Sets the buffer of this message with a string, adds the necessary padding
    */
    public void setParam_Id(String str) {
        int len = Math.min(str.length(), 16);
        for (int i=0; i<len; i++) {
            param_id[i] = (byte) str.charAt(i);
        }

        for (int i=len; i<16; i++) {            // padding for the rest of the buffer
            param_id[i] = 0;
        }
    }

    /**
    * Gets the message, formated as a string
    */
    public String getParam_Id() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            if (param_id[i] != 0)
                buf.append((char) param_id[i]);
            else
                break;
        }
        return buf.toString();

    }
                           
    /**
     * Returns a string with the MSG name and data
     */
    @Override
    public String toString() {
        return "MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY - sysid:"+sysid+" compid:"+compid+" request_id:"+request_id+" min_value:"+min_value+" max_value:"+max_value+" increment:"+increment+" result:"+result+" param_id:"+param_id+" config_type:"+config_type+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_OSD_PARAM_SHOW_CONFIG_REPLY";
    }
}
        