/* AUTO-GENERATED FILE.  DO NOT MODIFY.
 *
 * This class was automatically generated by the
 * java mavlink generator tool. It should not be modified by hand.
 */

// MESSAGE TERRAIN_DATA PACKING
package com.MAVLink.common;
import com.MAVLink.MAVLinkPacket;
import com.MAVLink.Messages.MAVLinkMessage;
import com.MAVLink.Messages.MAVLinkPayload;
        
/**
 * Terrain data sent from GCS. The lat/lon and grid_spacing must be the same as a lat/lon from a TERRAIN_REQUEST. See terrain protocol docs: https://mavlink.io/en/services/terrain.html
 */
public class msg_terrain_data extends MAVLinkMessage {

    public static final int MAVLINK_MSG_ID_TERRAIN_DATA = 134;
    public static final int MAVLINK_MSG_LENGTH = 43;
    private static final long serialVersionUID = MAVLINK_MSG_ID_TERRAIN_DATA;

      
    /**
     * Latitude of SW corner of first grid
     */
    public int lat;
      
    /**
     * Longitude of SW corner of first grid
     */
    public int lon;
      
    /**
     * Grid spacing
     */
    public int grid_spacing;
      
    /**
     * Terrain data MSL
     */
    public short data[] = new short[16];
      
    /**
     * bit within the terrain request mask
     */
    public short gridbit;
    

    /**
     * Generates the payload for a mavlink message for a message of this type
     * @return
     */
    @Override
    public MAVLinkPacket pack() {
        MAVLinkPacket packet = new MAVLinkPacket(MAVLINK_MSG_LENGTH,isMavlink2);
        packet.sysid = 255;
        packet.compid = 190;
        packet.msgid = MAVLINK_MSG_ID_TERRAIN_DATA;
        
        packet.payload.putInt(lat);
        packet.payload.putInt(lon);
        packet.payload.putUnsignedShort(grid_spacing);
        
        for (int i = 0; i < data.length; i++) {
            packet.payload.putShort(data[i]);
        }
                    
        packet.payload.putUnsignedByte(gridbit);
        
        if (isMavlink2) {
            
        }
        return packet;
    }

    /**
     * Decode a terrain_data message into this class fields
     *
     * @param payload The message to decode
     */
    @Override
    public void unpack(MAVLinkPayload payload) {
        payload.resetIndex();
        
        this.lat = payload.getInt();
        this.lon = payload.getInt();
        this.grid_spacing = payload.getUnsignedShort();
         
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = payload.getShort();
        }
                
        this.gridbit = payload.getUnsignedByte();
        
        if (isMavlink2) {
            
        }
    }

    /**
     * Constructor for a new message, just initializes the msgid
     */
    public msg_terrain_data() {
        this.msgid = MAVLINK_MSG_ID_TERRAIN_DATA;
    }
    
    /**
     * Constructor for a new message, initializes msgid and all payload variables
     */
    public msg_terrain_data( int lat, int lon, int grid_spacing, short[] data, short gridbit) {
        this.msgid = MAVLINK_MSG_ID_TERRAIN_DATA;

        this.lat = lat;
        this.lon = lon;
        this.grid_spacing = grid_spacing;
        this.data = data;
        this.gridbit = gridbit;
        
    }
    
    /**
     * Constructor for a new message, initializes everything
     */
    public msg_terrain_data( int lat, int lon, int grid_spacing, short[] data, short gridbit, int sysid, int compid, boolean isMavlink2) {
        this.msgid = MAVLINK_MSG_ID_TERRAIN_DATA;
        this.sysid = sysid;
        this.compid = compid;
        this.isMavlink2 = isMavlink2;

        this.lat = lat;
        this.lon = lon;
        this.grid_spacing = grid_spacing;
        this.data = data;
        this.gridbit = gridbit;
        
    }

    /**
     * Constructor for a new message, initializes the message with the payload
     * from a mavlink packet
     *
     */
    public msg_terrain_data(MAVLinkPacket mavLinkPacket) {
        this.msgid = MAVLINK_MSG_ID_TERRAIN_DATA;
        
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
        return "MAVLINK_MSG_ID_TERRAIN_DATA - sysid:"+sysid+" compid:"+compid+" lat:"+lat+" lon:"+lon+" grid_spacing:"+grid_spacing+" data:"+data+" gridbit:"+gridbit+"";
    }
    
    /**
     * Returns a human-readable string of the name of the message
     */
    @Override
    public String name() {
        return "MAVLINK_MSG_ID_TERRAIN_DATA";
    }
}
        