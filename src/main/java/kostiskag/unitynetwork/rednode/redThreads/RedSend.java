package kostiskag.unitynetwork.rednode.redThreads;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

import kostiskag.unitynetwork.rednode.App;
import kostiskag.unitynetwork.rednode.Routing.QueueManager;
import kostiskag.unitynetwork.rednode.Routing.packets.IPv4Packet;
import kostiskag.unitynetwork.rednode.Routing.packets.UnityPacket;

/**
 *
 * @author kostis
 */
public class RedSend extends Thread {

    private final String pre = "^RedSend ";
    
    private final InetAddress address;
    private final int port;
    private final QueueManager sendQueue;
    private DatagramSocket socket;
    private AtomicBoolean kill = new AtomicBoolean(false);

    public RedSend(InetAddress address, int port, QueueManager sendQueue) {
        this.address = address;
        this.port = port;
        this.sendQueue = sendQueue; //App.login.connection.upMan
    }

    @Override
    public void run() {
        socket = null;
        try {
            socket = new DatagramSocket();
        } catch (java.net.BindException ex1) {
            App.login.writeInfo(pre + "SOCKET ALLREADY BIND EXCEPTION");
            return;
        } catch (SocketException ex) {
            Logger.getLogger(RedSend.class.getName()).log(Level.SEVERE, null, ex);
            App.login.writeInfo(pre + "SOCKET EXCEPTION");
            return;
        }

        byte[] packet;
        while (!kill.get()) {
            try {
                packet = sendQueue.poll();
            } catch (java.lang.NullPointerException ex1){       
            	App.login.writeInfo(pre + "QUEUE NULL EXCEPTION");
                continue;
            } catch (java.util.NoSuchElementException ex) {
            	App.login.writeInfo(pre + "QUEUE NO SUCH ELEMENT EXCEPTION");
                continue;
            }
            
            int len = packet.length;
            if (len <= 0 || len > 1500) {
            	App.login.writeInfo(pre +"wrong length");
                continue;
            }
            
            DatagramPacket sendPacket = new DatagramPacket(packet, len, address, port);                        
            try {
                socket.send(sendPacket); 
                App.login.monitor.updateConDownBufferQueue(sendQueue.getlen());
                if (UnityPacket.isUnity(packet)) {
                	if (UnityPacket.isKeepAlive(packet)) {
                		App.login.monitor.writeToConnectionUp(pre+"KEEP ALIVE SENT");
                	} else if (UnityPacket.isUping(packet)) {
                		App.login.monitor.writeToConnectionUp(pre+"UPING SENT");
                	} else if (UnityPacket.isDping(packet)) {
                		App.login.monitor.writeToConnectionUp(pre+"DPING SENT");
                	} else if (UnityPacket.isShortRoutedAck(packet)) {
                		App.login.monitor.writeToConnectionUp(pre+"SHORT ACK SENT");
                	} else if (UnityPacket.isLongRoutedAck(packet)) {
                		App.login.monitor.writeToConnectionUp(pre+"LONG ACK SENT");
                	} else if (UnityPacket.isMessage(packet)) {
                		App.login.monitor.writeToConnectionUp(pre+"MESSAGE SENT");
                	}                    
                } else if (IPv4Packet.isIPv4(packet)){
                    try {
						App.login.monitor.writeToConnectionUp(pre+"IPV4 PACKET SENT Len:" + packet.length + " To: " + IPv4Packet.getDestAddress(packet).getHostAddress());
					} catch (Exception e) {
						e.printStackTrace();
					}
                }
            } catch (java.net.SocketException ex1) {
                break;
            } catch (IOException ex) {
                Logger.getLogger(RedSend.class.getName()).log(Level.SEVERE, null, ex);
                break;
            }                        
        }        
        App.login.monitor.writeToCommands(pre+"ended");
    }

    public void kill() {
        kill.set(true);
        sendQueue.exit();
        socket.close();        
    }
}
