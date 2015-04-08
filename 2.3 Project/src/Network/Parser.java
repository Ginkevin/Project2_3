package Network;

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
	
	public static void parse(String command) {
		if ("OK".equals(command)) {
			lastReceivedMessage = command;
			if (getWaitingForMessage() == true){
				waiting_for_message = false;
			}
		}
		else if ("ERR".equals(command)){
			System.out.println("Invalid Command");
		}
	}
	
}
