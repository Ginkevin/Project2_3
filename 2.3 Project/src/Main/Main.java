package Main;
import java.io.IOException;

import Network.Connect;
import GUI.SelectGameScreen;

public class Main {
	
	public static void main(String[] args) throws IOException {
		Connect.getInstance().setConnection();
		Worker arbeit = new Worker();
	}
}
