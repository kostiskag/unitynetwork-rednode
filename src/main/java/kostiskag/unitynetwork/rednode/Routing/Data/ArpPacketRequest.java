/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kostiskag.unitynetwork.rednode.Routing.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kostis
 */
public class ArpPacketRequest {
    byte[] packet;    
    InetAddress source;
    InetAddress target;
    MacAddress sourcemac;
    MacAddress destmac;
    
    public ArpPacketRequest(byte[] frame) {                                
        packet = new byte[frame.length];
        int j=14;
        for (int i=0; i<frame.length-14; i++){
            packet[i] = frame[j];
            j++;
        }                
    }

    public InetAddress getTarget() {
        byte[] addr = new byte[4];
        for(int i=0; i<4; i++){
            addr[i] = packet[24+i];             
        }
        try {
            target = InetAddress.getByAddress(addr);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ArpPacketRequest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return target;
    }        
}
