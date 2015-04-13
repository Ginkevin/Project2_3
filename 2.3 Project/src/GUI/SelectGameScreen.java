package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Network.Connect;
import GUI.Lobby;

public class SelectGameScreen extends JFrame implements ActionListener{

	private JPanel contentPane;
	private JButton Option1Button;
	private JButton Option2Button;
	
	public SelectGameScreen(String Player) throws UnknownHostException, IOException{
		String[] GameList = Connect.getInstance().GameList;
		Connect.getInstance().Player = Player;
		setResizable(false);
		setTitle("Speltype");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 500, 100);
		contentPane = new JPanel(new GridLayout(1,2)); // 1 row, 2 cols
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		Option1Button = new JButton(GameList[0]);
		Option1Button.setFont(new Font("Arial", Font.PLAIN, 18));
		Option1Button.setBounds(30, 25, 150, 36);
		contentPane.add(Option1Button);
		Option1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect.getInstance().Game = Option1Button.getText();
					String challenge = Connect.getInstance().sendChallenge(Connect.getInstance().Player , Connect.getInstance().Game);
					Dispose();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
			
		Option2Button = new JButton(GameList[1]);
		Option2Button.setFont(new Font("Arial", Font.PLAIN, 18));
		Option2Button.setBounds(300, 25, 150, 36);
		contentPane.add(Option2Button);
		Option2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect.getInstance().Game = Option2Button.getText();
					String challenge = Connect.getInstance().sendChallenge(Connect.getInstance().Player , Connect.getInstance().Game);
					Dispose();
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
	}
	public void Dispose(){
		this.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
	

	

