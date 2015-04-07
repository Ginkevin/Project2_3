package Network;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connect {
	private String ip = "localhost";
	private int port = 7789;
	private Socket socketconnection;
	
	private static Connect networkcontroller;
	
	public static Connect getInstance() throws UnknownHostException, IOException{
		if(networkcontroller == null){
			networkcontroller = new Connect();
		}
		return networkcontroller;
	}
	
	public Connect() throws UnknownHostException, IOException{
		 Socket echoSocket = new Socket(ip, port);
		 this.socketconnection = echoSocket;
	}
	
	public Socket getConnection(){
		return this.socketconnection;
	}
	
	public void sendLogin(String name) throws IOException {
		Send.Message("LOGIN " + name);
	}
}
