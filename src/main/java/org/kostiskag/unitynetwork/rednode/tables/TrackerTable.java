package org.kostiskag.unitynetwork.rednode.tables;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class TrackerTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4596986766485247624L;
	private LinkedList<TrackerEntry> list = new LinkedList<>();
	
	public TrackerTable() {
		list = new LinkedList<TrackerEntry>();
	}
	
	public synchronized void addEntry(String address, int port) throws Exception {
		//search to see if it exists first
		Iterator<TrackerEntry> it = list.listIterator();
		while(it.hasNext()) {
			TrackerEntry element = it.next();
			if (element.getAddress().equals(address) && element.getPort() ==  port) {
				throw new Exception("Entry already exists in table");
			}
		}
		
		TrackerEntry element = new TrackerEntry(address, port);
		list.add(element);
	}
	
	public synchronized TrackerEntry getEntry(String address, int port) throws Exception {
		Iterator<TrackerEntry> it = list.listIterator();
		while(it.hasNext()) {
			TrackerEntry element = it.next();
			if (element.getAddress().equals(address) && element.getPort() ==  port) {
				return element;
			}
		}
		throw new Exception("No such entry was found in the table.");
	}
	
	public synchronized boolean checkIfExisting(String address, int port) {
		Iterator<TrackerEntry> it = list.listIterator();
		while(it.hasNext()) {
			TrackerEntry element = it.next();
			if (element.getAddress().equals(address) && element.getPort() ==  port) {
				return true;
			}
		}
		return false;
	}
	
	
	public synchronized void removeEntry(String address, int port) throws Exception {
		Iterator<TrackerEntry> it = list.listIterator();
		while(it.hasNext()) {
			TrackerEntry element = it.next();
			if (element.getAddress().equals(address) && element.getPort() ==  port) {
				it.remove();
				return;
			}
		}
		throw new Exception("No such entry was found in the table.");
	}

	public synchronized String[][] buildStringTable() {
		String[][] data = new String[list.size()][2];
		Iterator<TrackerEntry> it = list.listIterator();
		int i = 0;
		while(it.hasNext()) {
			TrackerEntry element = it.next();
			data[i][0] = element.getAddress();
			data[i][1] = element.getPort()+"";
			i++;
		}
		return data;
	}	
}
