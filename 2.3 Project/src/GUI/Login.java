package GUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Login extends JFrame implements ActionListener{
	/**
	 * 
	 */
	private JPanel contentPane;
	private JTextField nameField;
	private JButton startButton;
	private List<JButton> colorButtons = new ArrayList<>();
	private static final long serialVersionUID = 1L;

	public Login(){
		setResizable(false);
		setTitle("Log in");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel nameLabel = new JLabel("Naam:");
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		nameLabel.setBounds(30, 30, 59, 17);
		contentPane.add(nameLabel);
		
		nameField = new JTextField();
		nameField.setFont(new Font("Arial", Font.PLAIN, 16));
		nameField.setBounds(99, 27, 160, 22);
		contentPane.add(nameField);
		nameField.setColumns(10);
		
		
		startButton = new JButton("Enter Lobby");
		startButton.setFont(new Font("Arial", Font.PLAIN, 18));
		startButton.setBounds(31, 60, 240, 36);
		contentPane.add(startButton);

		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Lobby gameloby = new Lobby(nameField.getText());
				Dispose();
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
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
