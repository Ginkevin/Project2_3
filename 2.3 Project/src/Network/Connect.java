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
	
	//ChallangeList is altijd .length = 3 (Naam, Spelnummer, Game)
	public String[] ChallangeList, GameList, PlayerList;
	public String Game, Player, Playername;
	//u = unknown, w = win, l = lose
	public char gameResult = 'u';
	public boolean GameStart, GameisPlaying, Myturn = false;
	public int EnemyMove = 100;
	
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
	
	public String sendLogout() throws UnknownHostException, IOException, InterruptedException{
		Parser.setWaitingForMessage();
		Send.Message("logout");
		return getParserResult();
	}
	
	public String sendSubscribe(String game) throws InterruptedException, UnknownHostException, IOException{
		Parser.setWaitingForMessage();
		Send.Message("subscribe " + game);
		return getParserResult();
	}
	
	public String sendChallenge(String player, String Game) throws InterruptedException, UnknownHostException, IOException{
		Parser.setWaitingForMessage();
		Send.Message("CHALLENGE \"" + player + "\" \"" + Game + "\"");
		return getParserResult();
	}
	
	public String[] getPlayerList() throws UnknownHostException, IOException, InterruptedException{
		Parser.setWaitingForMessage();
		Send.Message("get playerlist");
		getParserResult();
		return PlayerList;
	}
	
	public String getGameList() throws UnknownHostException, IOException, InterruptedException{
		Parser.setWaitingForMessage();
		Send.Message("get gamelist");
		return getParserResult();
	}
	
	public String[] getChallangeList() throws InterruptedException{
		Parser.setWaitingForMessage();
		getParserResult();
		return ChallangeList;
	}
	
	public String AcceptChallenge() throws InterruptedException, UnknownHostException, IOException{
		Parser.setWaitingForMessage();
		Send.Message("challenge accept "+ChallangeList[1]);
		return getParserResult();
	}
	
	public String sendMove(int boardnumber) throws InterruptedException, UnknownHostException, IOException{
		Parser.setWaitingForMessage();
		Send.Message("move " + boardnumber);
		return getParserResult();
	}
	
	public String getMove() throws InterruptedException{
		Parser.setWaitingForMessage();
		return getParserResult();
	}
	
	public void forfit() throws InterruptedException, UnknownHostException, IOException{
		Parser.setWaitingForMessage();
		Send.Message("forfeit");
		getParserResult();
	}
	
	public String getParserResult() throws InterruptedException{
		while(Parser.getWaitingForMessage() == true){
			Thread.sleep(10);
		//	System.out.println("waiting...");
		}
		//System.out.println("out of the wait!");
		Thread.sleep(1000);
		return getReceivedMessage();
	}
	
	public String getReceivedMessage(){
		return Parser.getLastReceivedMessage();
	}
}
