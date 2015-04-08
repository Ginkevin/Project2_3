package Main;
import GUI.Splash;

import java.io.IOException;


public class Main {
	
	public static void main(String[] args) throws IOException {
		
		Splash splash = new Splash();    	
    	try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
		}    	
		
		Worker arbeit = new Worker();
		splash.dispose();
	}
}
