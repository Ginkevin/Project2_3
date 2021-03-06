package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Stack;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import BoardGame.TicTacToe;
import BoardGame.Reversi;
import BoardGame.GameIntelligence;
import Network.Connect;
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
	public static boolean BeginGame;
	private JPanel contentPane, gameListPanel, gameList, mainPanel2, mainPanel;
	private JButton challengeButton, acceptButton, indicator, forfitB, AIButton;
	private JButton[] fieldlist, fieldlistOrtello;
	private JList<String> playerList, challengeList;
	public String name = "Saah Ed Nueb";
	public String previous = "leeg";
	private Thread players, challenges, moveReceiver, getResult, AImove;
	private int setPosition = 100;
	private boolean AI;
	private boolean AIsetSide = true;
	private TicTacToe t;
	private GameIntelligence intelli;
	
	public Lobby(String name) throws UnknownHostException, IOException, InterruptedException{
		this.name = name;
		t = new TicTacToe();
		intelli = new GameIntelligence();
		setTitle("Lobby");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1115, 650);
		//create layout for players and challenges
		setLayout();
		//System.out.println(Reversi.getReversi().toString());
		addTurnIndicator();
		//run threads that update player/challenge list
		setThreads();
		addTictactoeBoard();
		Reversi.getReversi().clearBoard();
		addOrtelloBoard();
	}
	
	
	
	private void setThreads() {
		//create new thread that updates the playerlist
				 players = new Thread(new Runnable() {
					public void run(){
					   // get playerlist
					      try {
					    	 
					    	  System.out.println("kom ik hier wel?");
							//Connect.getInstance().getPlayerList();
					    	  String[] getInstance = Connect.getInstance().getPlayerList();
					    	  System.out.println("volgens mij loopt het hier vast: RESULT: OUI!");
					    	  ((DefaultListModel<String>) playerList.getModel()).clear(); 
							for (int i= 0; i < getInstance.length; i++){								 
								((DefaultListModel<String>) playerList.getModel()).addElement(getInstance[i]);	
								System.out.println("zoekt spelers");
								System.out.println(Connect.getInstance().PlayerList[i]);
								playerList.setVisible(false);
								playerList.setVisible(true);
							}
							Thread.sleep(4000);	
							run();
					      } 
					       catch (InterruptedException | IOException e) {
								// TODO Auto-generated catch block
					    	   //players.interrupt();
					    	   //challenges.interrupt();
							}
					      
					 }
				});
				players.start();
			
				//create new thread that updates the challengelist
				challenges = new Thread(new Runnable() {
					public void run(){
					  // Insert some method call here.
					  try {
						  if(!Connect.getInstance().GameisPlaying){
						  String[] antwoord = Connect.getInstance().getChallangeList();
							if (antwoord != null && antwoord[0] != previous){
									((DefaultListModel<String>) challengeList.getModel()).clear();	
									((DefaultListModel<String>) challengeList.getModel()).addElement("Naam: "+ antwoord[0] +" Ticket: "+ antwoord[1] + " GameType: " + antwoord[2]);	
									previous = antwoord[0];
							}
						  }
						  if (Connect.getInstance().GameStart == true){
							if (Connect.getInstance().Game == "tictactoe"){
								mainPanel2.setVisible(true);
							}
							else if (Connect.getInstance().Game == "reversi"){
								if (Connect.getInstance().Myturn == true){
									fieldlistOrtello[27].setBackground(Color.red);
									fieldlistOrtello[27].setText("x");
									Reversi.getReversi().setMoveComputer(27);
									fieldlistOrtello[36].setBackground(Color.red);
									fieldlistOrtello[36].setText("x");
									Reversi.getReversi().setMoveComputer(36);
									fieldlistOrtello[35].setBackground(Color.green);
									fieldlistOrtello[35].setText("o");
									Reversi.getReversi().setMoveHuman(35);
									fieldlistOrtello[28].setBackground(Color.green);
									fieldlistOrtello[28].setText("o");
									Reversi.getReversi().setMoveHuman(28);
									
									getAImove();
									mainPanel.setVisible(true);
									System.out.println("MY TURN REVERSI");
								}
								else if(Connect.getInstance().Myturn == false){
									fieldlistOrtello[27].setBackground(Color.green);
									fieldlistOrtello[27].setText("o");
									Reversi.getReversi().setMoveHuman(27);
									fieldlistOrtello[36].setBackground(Color.green);
									fieldlistOrtello[36].setText("o");
									Reversi.getReversi().setMoveHuman(36);
									fieldlistOrtello[35].setBackground(Color.red);
									fieldlistOrtello[35].setText("x");
									Reversi.getReversi().setMoveComputer(35);
									fieldlistOrtello[28].setBackground(Color.red);
									fieldlistOrtello[28].setText("x");
									Reversi.getReversi().setMoveComputer(28);
									mainPanel.setVisible(true);
									System.out.println("HIS TURN REVERSI");
								}
							}
							Connect.getInstance().GameStart = false;
							//Connect.getInstance().GameisPlaying = true;
							forfitB.setText("Forfit");
							forfitB.setBackground(Color.red);
							//players.interrupt();
							//kochallenges.interrupt();
							
						}
						  
								Thread.sleep(3333);
								run();
							} 
					  catch (InterruptedException | IOException e) {
							// TODO Auto-generated catch block
							players.interrupt();
							challenges.interrupt();
						  //e.printStackTrace();
							
						}
					  }
				});
				challenges.start();
				
				//getGameStart and getGameMove thread
				moveReceiver = new Thread(new Runnable() {
					public void run(){
						try {
						while(true){
							
						
							if (Connect.getInstance().Myturn == true){
							indicator.setBackground(Color.green);
						}
						String moveEnemie = Connect.getInstance().getMove();
						System.out.println("getMove =" + moveEnemie);
						System.out.println("enemyMove = " + Connect.getInstance().EnemyMove);
						// EnemyMove ; 100 = default
						if (Connect.getInstance().EnemyMove != 100){
							//move contains tic from tictactoe
							if(Connect.getInstance().Game.toLowerCase().contains("tic")){
							System.out.println("move received : "+ Connect.getInstance().EnemyMove);
							int nr = Connect.getInstance().EnemyMove;
							fieldlist[nr].setText("X");
							fieldlist[nr].setBackground(Color.red);
							t.playMove(nr);
							System.out.print(t.toString());
							Connect.getInstance().EnemyMove = 100;
							}
							//move contains rev from reversi
							if(Connect.getInstance().Game.toLowerCase().contains("rev")){
								System.out.println("move received : "+ Connect.getInstance().EnemyMove);								
								int nr = Connect.getInstance().EnemyMove;
								Connect.getInstance().EnemyMove = 100;
								
								int tmprow = nr / 8;
								int tmpcol = nr % 8;
								//Reversi.getReversi().getLegalMove(tmprow, tmpcol, 1, 0, true);
								//Reversi.getReversi().setMoveComputer(nr);
								//if(Reversi.getReversi().getLegalMove(tmprow, tmpcol, 1, 0, true)){
								Stack<Integer> replaced = new Stack<Integer>();
								int tmp = 0;
								
								replaced = Reversi.getReversi().getLegalMove2(nr, 1);
								tmp = replaced.pop();
								
								//board[(int)Math.floor(tmp / columns)][tmp % columns] = 2;
								
								while (replaced.size() > 0) {
									tmp = replaced.pop();						
									Reversi.getReversi().setMoveForRepositioning((tmp/8),(tmp % 8), 1);
								}
								
								replaced = null;
								
									Reversi.getReversi().setMoveComputer(nr);
									setBoardColours();
									Connect.getInstance().Myturn = true;
									indicator.setBackground(Color.green);
									System.out.println("Registered enemy move, going to AI!");
									Thread.sleep(500);
									
									if(AI)
									{
										int[] aiMove = intelli.getAiMove(Reversi.getReversi().getBoard());
										System.out.println("AI move colum = " + aiMove[1]);
										System.out.println("AI move row = " + aiMove[0]);
										//if(Reversi.getReversi().getLegalMove(aiMove[0], aiMove[1], 0, 1, true)){
										
										int positionToSend = (aiMove[0] * 8) + (aiMove[1] % 8);		
										
										Stack<Integer> replaced2 = new Stack<Integer>();
										int tmp2 = 0;
										
										replaced2 = Reversi.getReversi().getLegalMove2(positionToSend, 0);
										tmp2 = replaced2.pop();
										
										while (replaced2.size() > 0) {
											tmp2 = replaced2.pop();						
											Reversi.getReversi().setMoveForRepositioning((tmp2 / 8),(tmp2 % 8), 0);
										}
										
										replaced = null;
										
										Reversi.getReversi().setMoveAi(aiMove[1], aiMove[0]);
										//}
																												
										System.out.println("Position to send: " + positionToSend);									
										indicator.setBackground(Color.red);
										mainPanel.setVisible(false);
										mainPanel.setVisible(true);										

										setBoardColours();
										System.out.println("Send with AI and changed board!");
										Connect.getInstance().Myturn = false;		
										
										System.out.println(Reversi.getReversi().toString());
										Connect.getInstance().sendMove(positionToSend);	
									}
								}								
								Thread.sleep(1000);
							}
						//}
						else {
							System.out.println("GEEN MOVE ONTVANGEN");
							Thread.sleep(1000);		
						}				
						} 
						}
					 catch (InterruptedException | IOException e) {
						e.printStackTrace();
					 }
					}  
				});
				moveReceiver.start();
				
				AImove = new Thread(new Runnable() {
					public void run(){
						try {
					if(Connect.getInstance().GameisPlaying){
						if(AI){
							if (Connect.getInstance().Myturn){
								Connect.getInstance().Myturn = false;
								Thread.sleep(2000);
								if (AIsetSide){
									t.setSide("ai");
									AIsetSide = false;
								}
								int tmp = t.chooseMove();
								t.playMove(tmp);
								System.out.print(t.toString());
								Connect.getInstance().sendMove(tmp);
								System.out.println("set myturn naar false");
								fieldlist[tmp].setText("O");
								fieldlist[tmp].setBackground(Color.green);
								indicator.setBackground(Color.red);
							}
							else if(!Connect.getInstance().Myturn){
								if (AIsetSide){
									t.setSide("speler");
									AIsetSide = false;
								}
							}
						}
					}
						Thread.sleep(2000);
						run();
					} 
					 catch (InterruptedException | IOException e) {
						e.printStackTrace();
					 }
					}  
				});
				AImove.start();
				
				getResult = new Thread(new Runnable() {
					public synchronized  void run(){
					try {
						Connect.getInstance().getParserResult();
						if (Connect.getInstance().GameisPlaying){
							
						}
						if (!Connect.getInstance().GameisPlaying){
						//	mainPanel2.setVisible(false);
						//	mainPanel.setVisible(false);
							if (Connect.getInstance().gameResult == 'w'){
								forfitB.setText("WON!");
								forfitB.setBackground(Color.green);
								Connect.getInstance().gameResult = 'u';
								if(Connect.getInstance().Game.contains("tic")){
									clearTicTacToeBoard();
									t = new TicTacToe();
								}
								else { clearOrtelloBoard(); Reversi.getReversi().clearBoard();}
							}
							if (Connect.getInstance().gameResult == 'l'){
								forfitB.setText("LOST!");
								forfitB.setBackground(Color.red);
								Connect.getInstance().gameResult = 'u';
								if(Connect.getInstance().Game.contains("tic")){
									clearTicTacToeBoard();
									t = new TicTacToe();
								}
								else { clearOrtelloBoard();Reversi.getReversi().clearBoard();}
							}
							if (Connect.getInstance().gameResult == 'd'){
								forfitB.setText("DRAW!");
								forfitB.setBackground(Color.yellow);
								Connect.getInstance().gameResult = 'u';
								if(Connect.getInstance().Game.contains("tic")){
									clearTicTacToeBoard();
									t = new TicTacToe();
								}
								else { clearOrtelloBoard();Reversi.getReversi().clearBoard();}
							}
							if (Connect.getInstance().gameResult == 'u'){
								
							}
						}
						Thread.sleep(2000);
						run();
					} 
					catch (IOException | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
				});
				getResult.start();
	}



	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public void getAImove() throws UnknownHostException, IOException, InterruptedException{
		if(AI)
		{
			int[] aiMove = intelli.getAiMove(Reversi.getReversi().getBoard());
			System.out.println("AI move colum = " + aiMove[1]);
			System.out.println("AI move row = " + aiMove[0]);
			//if(Reversi.getReversi().getLegalMove(aiMove[0], aiMove[1], 0, 1, true)){
			
			int positionToSend = (aiMove[0] * 8) + (aiMove[1] % 8);		
			
			Stack<Integer> replaced2 = new Stack<Integer>();
			int tmp2 = 0;
			
			replaced2 = Reversi.getReversi().getLegalMove2(positionToSend, 0);
			tmp2 = replaced2.pop();
			
			while (replaced2.size() > 0) {
				tmp2 = replaced2.pop();						
				Reversi.getReversi().setMoveForRepositioning((tmp2 / 8),(tmp2 % 8), 0);
			}
			
			//replaced = null;
			
			Reversi.getReversi().setMoveAi(aiMove[1], aiMove[0]);
			//}
			Connect.getInstance().sendMove(positionToSend);																			
			System.out.println("Position to send: " + positionToSend);									
			indicator.setBackground(Color.red);
			mainPanel.setVisible(false);
			mainPanel.setVisible(true);										

			setBoardColours();
			System.out.println("Send with AI and changed board!");
			Connect.getInstance().Myturn = false;		
			
			System.out.println(Reversi.getReversi().toString());
			
		}
	}
	
	public void setLayout(){
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

		//create challenge button. If challenge is selected; create pop-up menu
		challengeButton = new JButton("For the Motherland! (daag uit)");
		challengeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						try {
							SelectGameScreen selectGame = new SelectGameScreen(playerList.getSelectedValue());
						} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					
				} 
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
		challengeButton.setBounds(30, 343, 300, 36);
		contentPane.add(challengeButton);
		
		forfitB = new JButton();
		forfitB.setBounds(700, 25, 75, 36);
		forfitB.setText("Forfit");
		forfitB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Connect.getInstance().forfit();
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(forfitB);
		
		AIButton = new JButton();
		AIButton.setBounds(800, 25, 75, 36);
		AIButton.setText("AI");
		AIButton.setBackground(Color.red);
		AIButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(AIButton.getBackground() == Color.red){
					AI = true;
					AIButton.setBackground(Color.green);
					AIButton.setVisible(false);
					AIButton.setVisible(true);
				}
				else if(AIButton.getBackground() == Color.green){
					AI = false;
					AIButton.setBackground(Color.red);
					AIButton.setVisible(false);
					AIButton.setVisible(true);
				}
			}
		});
		contentPane.add(AIButton);

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
		acceptButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String acceptChallenge = Connect.getInstance().AcceptChallenge();
					System.out.println("Accepted challenge! ");
					((DefaultListModel<String>) challengeList.getModel()).clear();	
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setVisible(true);
		
	}
	
	public void addOrtelloBoard(){
		mainPanel = new JPanel(new GridLayout(8, 8, 1, 1));
		mainPanel.setBounds(450, 80, 621, 512);
		//contentPane.add(mainPanel);
		fieldlistOrtello = new JButton[64];
		for (int j = 0; j< (64); j++){
			fieldlistOrtello[j] = new JButton(""+j);
			fieldlistOrtello[j].setForeground(Color.white);
			fieldlistOrtello[j].setBackground(Color.white);
			fieldlistOrtello[j].setFont(new Font("Arial", Font.PLAIN, 40));
			fieldlistOrtello[j].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
					String position = e.getSource().toString().replace(",defaultCapable=true]", "");
					String[] resultposition = position.split("text=");
					//resultposition[1] TESTED: BETWEEN 0-63
					System.out.println(resultposition[1]);
					fieldlistOrtello[Integer.parseInt(resultposition[1])].setText("O");
					fieldlistOrtello[Integer.parseInt(resultposition[1])].setBackground(Color.green);
					setPosition = Integer.parseInt(resultposition[1]);
					Connect.getInstance().sendMove(setPosition);
					//getLegalMove(int row,int col,int currentplayer, int nextplayer, boolean flip)
					int rowtmp = setPosition / 8;
					int columntmp = setPosition % 8;
					//if(Reversi.getReversi().getLegalMove(rowtmp, columntmp, 0, 1, true)){
					Stack<Integer> replaced = new Stack<Integer>();
					int tmp = 0;
					
					replaced = Reversi.getReversi().getLegalMove2(setPosition, 0);
					tmp = replaced.pop();
					
					while (replaced.size() > 0) {
						tmp = replaced.pop();						
						Reversi.getReversi().setMoveForRepositioning((tmp/8),(tmp % 8), 0);
					}
					
					replaced = null;
					
					Reversi.getReversi().setMoveHuman(setPosition);
					
					//}
					
					System.out.println(Reversi.getReversi().toString());
					setBoardColours();
					System.out.println("move send: " + setPosition);
					Connect.getInstance().Myturn = false;
					indicator.setBackground(Color.red);
					mainPanel.setVisible(false);
					mainPanel.setVisible(true);
					}
					catch (IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}
	});	
			mainPanel.add(fieldlistOrtello[j]);
		}
		contentPane.add(mainPanel);
		mainPanel.setVisible(false);
	}
	
	public void addTictactoeBoard(){		
		mainPanel2 = new JPanel(new GridLayout(3, 3, 1, 1));
		mainPanel2.setBounds(450, 80, 621, 512);
		fieldlist = new JButton[9];
		for (int i = 0; i< (9); i++){
			fieldlist[i] = new JButton(""+i);
			fieldlist[i].setForeground(Color.white);
			fieldlist[i].setBackground(Color.white);
			fieldlist[i].setFont(new Font("Arial", Font.PLAIN, 70));
			fieldlist[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						//if(Connect.getInstance().GameisPlaying){
						String position = e.getSource().toString().replace(",defaultCapable=true]", "");
						String[] resultposition = position.split("text=");
						System.out.println(resultposition[1]);
						fieldlist[Integer.parseInt(resultposition[1])].setText("O");
						fieldlist[Integer.parseInt(resultposition[1])].setBackground(Color.green);
						//resultposition[1] TESTED: BETWEEN 0-8
						setPosition = Integer.parseInt(resultposition[1]);
						String test1 = Connect.getInstance().sendMove(setPosition);
						System.out.println(test1);
						System.out.println("move send: " + setPosition);
						Connect.getInstance().Myturn = false;
						indicator.setBackground(Color.red);
						mainPanel2.setVisible(false);
						mainPanel2.setVisible(true);
						//}
					} catch (IOException | InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		}
	});
			mainPanel2.add(fieldlist[i]);
		}
		contentPane.add(mainPanel2);
		mainPanel2.setVisible(false);
	}
	
	public void clearTicTacToeBoard(){
		for (int j = 0; j <= 8; j++){
			mainPanel2.setVisible(false);
			fieldlist[j].setForeground(Color.white);
			fieldlist[j].setBackground(Color.white);
			fieldlist[j].setText(""+j);
		}
	}
	
	public void clearOrtelloBoard(){
		for (int k = 0; k < 64; k++){
			mainPanel.setVisible(false);
			fieldlistOrtello[k].setForeground(Color.white);
			fieldlistOrtello[k].setBackground(Color.white);
			fieldlistOrtello[k].setText(""+k);
		}
	}
	public void addTurnIndicator(){
		JLabel turnIndicator = new JLabel("Your turn: ");
		turnIndicator.setFont(new Font("Arial", Font.PLAIN, 18));
		turnIndicator.setBounds(450, 30, 170, 22);
		contentPane.add(turnIndicator);
		
		indicator = new JButton();
		indicator.setBounds(550, 30, 25, 25);
		indicator.setBackground(Color.red);
		contentPane.add(indicator);
	}
	
	public String getName(){
		return this.name;
	}
	
	/**
	 * zet het board in de lobby gelijk aan het board in het back-end
	 * @static int[][] board: het board waar de back-end gebruik van maakt.
	 */
	public void setBoardColours(){
		int[][] board = Reversi.getReversi().getBoard();
		for (int y = 0; y < 8; y++){
			for (int z= 0; z < 8; z++){
				if(board[y][z] != 2){
					//ENEMY
					if (board[y][z] == 1){
						fieldlistOrtello[(8*y) + z].setBackground(Color.red);
						fieldlistOrtello[(8*y) + z].setText("X");
						System.out.println("zit in ENEMY setColour and text");
					}
					//US
					else if(board[y][z] == 0){
						fieldlistOrtello[(8*y) + z].setBackground(Color.green);
						fieldlistOrtello[(8*y) + z].setText("O");
						System.out.println("zit in Human setColour and text");
					}
				}
			}
		}
		mainPanel.setVisible(false);
		mainPanel.setVisible(true);
	}

}
