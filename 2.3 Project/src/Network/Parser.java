package Network;

public class Parser {
	private static String lastReceivedMessage;
	
	public static String getLastReceivedMessage() {
		return lastReceivedMessage;
	}
	
	public static void parse(String command) {
		if ("OK".equals(command)) {
			lastReceivedMessage = command;
		}
	}
	
}
