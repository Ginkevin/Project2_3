package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author Kevin Stoffers
 * @version 1
 * @date 4-4-2015
 *
 *	Deze classe zorgt voor de visuele weergave van het framework
 */
public class Lobby extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane, gameListPanel, gameList;
	private JButton challengeButton, acceptButton;
	private JList<String> playerList, challengeList;
	private String name = "Saah Ed Nueb";
	
	public Lobby(String name){
		this.name = name;
		this.setIconImage(new ImageIcon("./src/Images/Saah_ed_Nueb.png").getImage());
		setTitle("Lobby");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1115, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Naam :");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		nameLabel.setBounds(30, 60, 70, 22);
		contentPane.add(nameLabel);

		JLabel playerLabel = new JLabel(name);
		playerLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		playerLabel.setBounds(110, 60, 200, 22);
		contentPane.add(playerLabel);

		JLabel playerListLabel = new JLabel("Beschikbare szuka:");
		playerListLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		playerListLabel.setBounds(30, 107, 215, 22);
		contentPane.add(playerListLabel);

		challengeButton = new JButton("For the Motherland! (daag uit)");
		challengeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JScrollPane playerPane = new JScrollPane();
		playerPane.setBounds(30, 140, 312, 192);
		contentPane.add(playerPane);

		playerList = new JList<>();
		playerPane.setViewportView(playerList);
		playerList.setBorder(null);
		playerList.setFont(new Font("Arial", Font.PLAIN, 18));
		playerList.setModel(new DefaultListModel<String>());
		challengeButton.setFont(new Font("Arial", Font.PLAIN, 18));
		challengeButton.setBounds(30, 343, 312, 36);
		contentPane.add(challengeButton);

		gameList = new JPanel();
		gameList.setBounds(30, 414, 312, 265);
		contentPane.add(gameList);
		gameList.setLayout(null);

		gameListPanel = new JPanel();
		gameListPanel.setBounds(0, 0, 312, 265);
		gameList.add(gameListPanel);
		gameListPanel.setLayout(null);

		JLabel challengeLabel = new JLabel("challenge:");
		challengeLabel.setBounds(0, 0, 96, 22);
		gameListPanel.add(challengeLabel);
		challengeLabel.setFont(new Font("Arial", Font.PLAIN, 18));

		JScrollPane challengePane = new JScrollPane();
		challengePane.setBounds(0, 33, 312, 100);
		gameListPanel.add(challengePane);

		challengeList = new JList<String>();
		challengePane.setViewportView(challengeList);
		challengeList.setVisibleRowCount(4);
		challengeList.setModel(new DefaultListModel<String>());
		
		challengeList.setFont(new Font("Arial", Font.PLAIN, 18));
		challengeList.setBorder(null);

		acceptButton = new JButton("Uitdaging accepteren crush them!");
		acceptButton.setBounds(0, 143, 312, 36);
		gameListPanel.add(acceptButton);
		acceptButton.setFont(new Font("Arial", Font.PLAIN, 18));

		acceptButton.addActionListener(this);
		challengeButton.addActionListener(this);
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
