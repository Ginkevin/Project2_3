package Network;
import Network.Connect;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Send {
	PrintWriter output;
	
	public Send(Socket socket) throws IOException{
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		this.output = out;
	}
	
	public static void Message(String message) throws UnknownHostException, IOException{
		OutputStreamWriter command = new OutputStreamWriter(Connect.getInstance().getConnection().getOutputStream());
		command.write(message + "\n");
		command.flush();
	}
}


