/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kostiskag.unitynetwork.rednode.Routing.Data;

import kostiskag.unitynetwork.rednode.RedNode.lvl3RedNode;
import java.net.InetAddress;

/**
 *
 * @author kostis
 *
 * thiiiis is a reverse arp table firstly if an arp is detected the arp table
 * registers it
 */
public class ReverseArpTable {
    String pre = "^ReverseArpTable ";
    ReverseArp[] table;
    ReverseArp temp;
    int count;
    int size;
    int nextMac;

    public ReverseArpTable(int size) {
        this.size = size;
        table = new ReverseArp[size];
        for (int i = 0; i < size; i++) {
            table[i] = new ReverseArp();
        }
        count = 0;
        nextMac = 1;
    }

    public void lease(InetAddress ip) {
        if (ip.equals(lvl3RedNode.login.connection.MyIP)) {            
            return;
        }

        byte[] addr = new byte[6];
        boolean[] head = new boolean[8];
        byte phead;

        //first we have the head where our mac is unicast and local set
        for (int i = 2; i < 8; i++) {
            head[i] = false;
        }
        head[1] = true;
        head[0] = false;
        phead = toByte(head);

        //then the rest of the mac is increasing by nextMac
        addr = new byte[]{
            phead,
            (byte) 0x0,
            (byte) (nextMac >>> 24),
            (byte) (nextMac >>> 16),
            (byte) (nextMac >>> 8),
            (byte) nextMac};

        nextMac++;

        MacAddress mac = new MacAddress(addr);        
        table[count].setMac(mac);
        table[count].setIp(ip);
        lvl3RedNode.login.monitor.writeToIntRead("new mac: " + table[count].getMac().toString()+" for "+table[count].getIp().getHostAddress());       
        byte[] arp = ArpGenerate.ArpGenerate(table[count].getMac(), table[count].getIp());
        if (arp == null){
            System.out.println("arp generate failed");
            lvl3RedNode.login.monitor.writeToIntRead(pre+"ARP FAILED");
        } else {
             lvl3RedNode.login.monitor.writeToIntWrite(pre+"GENERATING ARPS for "+table[count].getIp().getHostAddress());
             for(int i=0; i<2; i++){
                lvl3RedNode.login.connection.writeMan.offer(arp);
             }
        }
        count++;
    }

    private void release(int id) {
        for (int i = 0; i < count; i++) {
            if (i == id) {
                if (count != 0) {
                    temp = table[count - 1];
                    table[count - 1] = table[i];
                    table[i] = temp;
                    count--;
                    return;
                }
            }
        }
    }

    public static byte toByte(boolean[] data) {
        int result = 0;
        for (int i = 0; i < data.length; i++) {
            int value = (data[i] ? 1 : 0) << i;
            result = result | value;
        }
        byte bresult = (byte) result;
        return bresult;
    }

    public boolean isAssociated(InetAddress target) {
        for (int i = 0; i < count; i++) {
            if (table[i].getIp().equals(target)) {
                return true;
            }
        }
        return false;
    }

    public ReverseArp getByIP(InetAddress ip) {
        for (int i = 0; i < count; i++) {
            if (ip.equals(table[i].getIp())) {
                return table[i];
            }
        }
        return null;
    }
}
