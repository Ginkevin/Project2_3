package Network;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import Network.Parser;

public class Connect {
	private String ip = "localhost";
	private int port = 7789;
	private Socket socketconnection;
	private boolean threadStarted = false;
	private Receive receive;
	
	private static Connect networkcontroller;
	public String[] PlayerList;
	//ChallangeList is altijd .length = 3 (Naam, Spel, SpelNummer)
	public String[] ChallangeList;
	
	public static Connect getInstance() throws UnknownHostException, IOException{
		if(networkcontroller == null){
			networkcontroller = new Connect();
		}
		return networkcontroller;
	}
	
	public void setConnection() throws UnknownHostException, IOException{
		 Socket echoSocket = new Socket(ip, port);
		 this.socketconnection = echoSocket;
		 if (threadStarted == false){
			 receive = new Receive();
			 new Thread(receive).start();
		 }
		 else {
			 receive.newReceiver();
		 }
	}
	
	public Socket getConnection(){
		return this.socketconnection;
	}
	
	public String sendLogin(String name) throws IOException, InterruptedException {
		Parser.setWaitingForMessage();
		Send.Message("LOGIN " + name);
		return getParserResult();
	}
	
	public String[] getPlayerList() throws UnknownHostException, IOException, InterruptedException{
		Parser.setWaitingForMessage();
		Send.Message("get playerlist");
		getParserResult();
		return PlayerList;
	}
	
	public String[] getChallangeList() throws InterruptedException{
		Parser.setWaitingForMessage();
		getParserResult();
		return ChallangeList;
	}
	
	public String getParserResult() throws InterruptedException{
		while(Parser.getWaitingForMessage() == true){
			Thread.sleep(10);
		}
		return getReceivedMessage();
	}
	
	public String getReceivedMessage(){
		return Parser.getLastReceivedMessage();
	}
}
