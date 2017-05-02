package kostiskag.unitynetwork.rednode.Routing.data;

import java.net.InetAddress;
import kostiskag.unitynetwork.rednode.Routing.UploadManager;

/**
 *
 * @author Konstantinos Kagiampakis
 */
public class ReverseARPInstance {
    private final MacAddress mac;
    private final InetAddress ip;
    
    public ReverseARPInstance(InetAddress ip, MacAddress mac) { 
    	this.ip = ip;
    	this.mac = mac;
    }
    
    public InetAddress getIp() {
        return ip;
    }

    public MacAddress getMac() {
        return mac;
    }  
}
