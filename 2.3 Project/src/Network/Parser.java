package Network;

import java.io.IOException;
import java.net.UnknownHostException;

public class Parser {
	private static String lastReceivedMessage;
	private static boolean waiting_for_message =  false;
	
	public static String getLastReceivedMessage() {
		return lastReceivedMessage;
	}
	
	public static void setWaitingForMessage(){
		waiting_for_message = true;
	}
	
	public static boolean getWaitingForMessage(){
		return waiting_for_message;
	}
	
	public static void parse(String command) throws UnknownHostException, IOException {
		if ("OK".equals(command)) {
			lastReceivedMessage = command;
			if (getWaitingForMessage() == true){
				waiting_for_message = false;
			}
		}
		else if ("ERR".equals(command)){
			System.out.println("Invalid Command");
			if (getWaitingForMessage() == true){
				waiting_for_message = false;
			}
		}
		else if(command.contains("SVR PLAYERLIST")){
			//lastReceivedMessage = command;
			//lastReceivedMessage = command;
			command = command.replace("SVR PLAYERLIST [", "");
			command = command.replace("]", "");
			command = command.replace("\"", "");
			command = command.replace(",", "");
			String[] playerList = command.split("\\s+");
			Connect.getInstance().PlayerList = playerList;
			if (getWaitingForMessage() == true){
				waiting_for_message = false;
			}
		}
		//uitdaging ontvangen
		else if(command.contains("SVR GAME CHALLENGE")){
			//todo
			lastReceivedMessage = command;
			// SVR GAME CHALLENGE {CHALLENGER: "Sjors", GAMETYPE: "Guess Game", CHALLENGENUMBER: "1"}
			command = command.replace("SVR GAME CHALLENGE {CHALLENGER: ", "");
			command = command.replace("GAMETYPE: ", "");
			command = command.replace("CHALLENGENUMBER: ", "");
			command = command.replace("\"", "");
			command = command.replace(",", "");
			String[] challangeList = command.split("\\s+");
			Connect.getInstance().ChallangeList = challangeList;
			if (getWaitingForMessage() == true){
				waiting_for_message = false;
			}
		}
		else if (command.contains("SVR GAMELIST")){
			lastReceivedMessage = command;
			command = command.replace("SVR GAMELIST [", "");
			command = command.replace("\"", "");
			command = command.replace("]", "");
			String[] gamelist = command.split(",");
			Connect.getInstance().GameList = gamelist; 
			if (getWaitingForMessage() == true){
				 waiting_for_message = false;
			 }
		}
		else if (command.contains("YOURTURN")){
			Connect.getInstance().Myturn = true;
			if (getWaitingForMessage() == true){
				 waiting_for_message = false;
			 }
		}
		else if(command.contains("WIN")){
			Connect.getInstance().GameisPlaying = false;
			Connect.getInstance().gameResult = 'w';
			Connect.getInstance().EnemyMove = 100;
			if (getWaitingForMessage() == true){
				 waiting_for_message = false;
			 }
		}
		else if (command.contains("LOSS")){
			Connect.getInstance().GameisPlaying = false;
			Connect.getInstance().gameResult = 'l';
			Connect.getInstance().EnemyMove = 100;
			if (getWaitingForMessage() == true){
				 waiting_for_message = false;
			 }
		}
		else if (command.contains("DRAW")){
			Connect.getInstance().GameisPlaying = false;
			Connect.getInstance().gameResult = 'd';
			Connect.getInstance().EnemyMove = 100;
			if (getWaitingForMessage() == true){
				 waiting_for_message = false;
			 }
		}
		else if (command.contains("SVR GAME MATCH")){
			String GAMEMATCH = command.toLowerCase();
			if(GAMEMATCH.contains("tic")){
				Connect.getInstance().Game = "tictactoe";
			}
			else if(GAMEMATCH.contains("rev")){
				Connect.getInstance().Game = "reversi";
			}
			System.out.println(command);
			command = command.replace("SVR GAME MATCH {PLAYERTOMOVE: ", "");
			command = command.replace(", OPPONENT: \"testdummy\"}", "");
			command = command.replace(" GAMETYPE: ", "");
			command = command.replace("\"", "");
			command = command.replace("\"", "");
			command = command.replace("}", "");
			command = command.replace("OPPONENT: ", "");
			lastReceivedMessage = command;
			System.out.println(command);
			Connect.getInstance().GameStart = true;
			if (getWaitingForMessage() == true){
				 waiting_for_message = false;
			 }
		}

		else if (command.contains("SVR GAME MOVE")){
			//SVR GAME MOVE {PLAYER: "<speler>", DETAILS: "<reactie spel op zet>", MOVE: "<zet>"}
			//System.out.println("ZET IS GEDAAN: " + command);
			command = command.replace("SVR GAME MOVE {PLAYER: ", "");
			command = command.replace("DETAILS: ", "");
			command = command.replace("MOVE: ", "");
			command = command.replace("}","");
			command = command.replace("\"","");
			command = command.replace(",","");
			String[] receiveList = command.split("\\s+");
			//0 = player 1= position
			if(!receiveList[0].equalsIgnoreCase(Connect.getInstance().Playername)){
				lastReceivedMessage = command;
				Connect.getInstance().EnemyMove = Integer.parseInt(receiveList[1]);
				System.out.println(receiveList[1]);
				System.out.println("enemy turn being processed");
				System.out.println(""+ receiveList[0].toLowerCase() + " and " + Connect.getInstance().Playername.toLowerCase()+ " not the same");
				if (getWaitingForMessage() == true){
					 waiting_for_message = false;
				 }
			}
			else if(receiveList[0].equalsIgnoreCase(Connect.getInstance().Playername)){
				lastReceivedMessage = "EMPTY";
				System.out.println("my turn being processed: output: "+ receiveList[0]);
				if (getWaitingForMessage() == true){
					 waiting_for_message = false;
				 }
			}
			
			
		}
		
	}
	
}
