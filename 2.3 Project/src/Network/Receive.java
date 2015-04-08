package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import Network.Connect;
import Network.Parser;


public class Receive implements Runnable{
	private BufferedReader input;
	boolean running = true;
	
	public Receive() throws IOException{
		 input = new BufferedReader(new InputStreamReader(Connect.getInstance().getConnection().getInputStream()));
	}

	public void newReceiver() throws UnknownHostException, IOException{
		input = new BufferedReader(new InputStreamReader(Connect.getInstance().getConnection().getInputStream()));
	}
	
	@Override
	public void run() {
		while (running == true){
			String input_text;
			try {
		while((input_text = input.readLine()) != null){
					Parser.parse(input_text);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				running = false;
			}
		}
		
	}
}
