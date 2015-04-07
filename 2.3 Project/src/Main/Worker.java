package Main;
import java.io.IOException;

import Main.Main;
import Network.Connect;
import Network.Receive;
import Network.Send;
import GUI.Login;

public class Worker {
	
	public Worker() throws IOException{
		Login start = new Login(this);
	}
	
}
