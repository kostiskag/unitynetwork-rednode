package kostiskag.unitynetwork.rednode.functions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import kostiskag.unitynetwork.rednode.App;

/**
 *
 * @author kostis
 */

public class SocketFunctions {
	// socket stuff
	public static String pre = "^SOCKET ";

	public static InetAddress getAddress(String PhAddress) {
		InetAddress IPaddress = null;
		try {
			IPaddress = InetAddress.getByName(PhAddress);
		} catch (Exception ex) {
			return null;
		}
		return IPaddress;
	}

	public static Socket absoluteConnect(InetAddress IPaddress, int authPort) {
		Socket socket = null;
		if (authPort > 0 && authPort <= 65535) {
			try {
				socket = new Socket(IPaddress, authPort);
				socket.setSoTimeout(3000);
			} catch (java.net.NoRouteToHostException ex) {
				App.login.writeInfo(pre + "NO ROUTE");
				return null;
			} catch (java.net.UnknownHostException ex) {
				App.login.writeInfo(pre + "UNKNOWN HOST");
				return null;
		    } catch (java.net.ConnectException ex) {
				App.login.writeInfo(pre + "CONNECTION REFUSED");
				return null;
			} catch (java.net.SocketTimeoutException ex) {
				App.login.writeInfo(pre + "CONNECTION TIMED OUT");
				return null;
			} catch (Exception ex) {
				App.login.writeInfo(pre + "CONNECTION ERROR");
				ex.printStackTrace();
				return null;
			}
			return socket;
		} else {
			App.login.writeInfo(pre + "A Wrong port number was defined");
			return null;
		}
	}

	public static BufferedReader makeReadWriter(Socket socket) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return inputReader;
	}

	public static PrintWriter makeWriteWriter(Socket socket) {
		PrintWriter outputWriter = null;
		try {
			outputWriter = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException ex) {
			Logger.getLogger(SocketFunctions.class.getName()).log(Level.SEVERE, null, ex);
			return null;
		}
		return outputWriter;
	}

	public static String[] sendData(String data, PrintWriter outputWriter, BufferedReader inputReader) {
		if (outputWriter == null || inputReader == null) {
			System.out.println(pre + "SEND DATA FAILED, NO CONNECTION");
			return null;
		} else if (data == null) {
			System.out.println(pre + "NO DATA TO SEND");
			return null;
		}

		outputWriter.println(data);
		String receivedMessage = null;
		String[] args = null;
		try {
			receivedMessage = inputReader.readLine();
		} catch (IOException ex) {
			Logger.getLogger(SocketFunctions.class.getName()).log(Level.SEVERE, null, ex);
		}

		System.out.println(pre + receivedMessage);
		args = receivedMessage.split("\\s+");
		return args;
	}

	public static void sendFinalData(String data, PrintWriter outputWriter) {
		if (outputWriter == null) {
			System.out.println(pre + "SEND FINAL DATA FAILED, NO CONNECTION");
			return;
		} else if (data == null) {
			System.out.println(pre + "NO DATA TO SEND");
			return;
		}
		outputWriter.println(data);
	}

	public static String[] readData(BufferedReader inputReader) {
		if (inputReader == null) {
			System.out.println(pre + "READ DATA FAILED, NO CONNECTION");
			return null;
		}

		String receivedMessage = null;
		String[] args = null;
		try {
			receivedMessage = inputReader.readLine();
		} catch (IOException ex) {
			Logger.getLogger(SocketFunctions.class.getName()).log(Level.SEVERE, null, ex);
		}
		System.out.println(pre + receivedMessage);
		args = receivedMessage.split("\\s+");
		return args;
	}

	public static void connectionClose(Socket socket) {
		if (socket == null) {
			System.out.println(pre + "CONNECTION CLOSE FAILED, NO CONNECTION");
			return;
		}
		try {
			socket.close();
		} catch (IOException ex) {
			Logger.getLogger(SocketFunctions.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

}