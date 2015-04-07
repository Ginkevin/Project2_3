package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import Network.Connect;

public class Receive {
	BufferedReader input;
	public Receive(Socket socket) throws IOException{
		 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		 this.input = in;

	}
	
	public String ReceivedMessage() throws IOException{
		return input.readLine();
		
	}
}
