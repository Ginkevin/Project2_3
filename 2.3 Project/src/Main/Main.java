package Main;
import java.io.IOException;

import Network.Connect;


public class Main {
	
	public static void main(String[] args) throws IOException {
		Connect.getInstance().setConnection();
		Worker arbeit = new Worker();
	}
}
