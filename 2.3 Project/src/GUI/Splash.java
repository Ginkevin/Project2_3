package GUI;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.*;

/**
 * splashscreen 
 * @author Kevin Venema
 *
 */

@SuppressWarnings("serial")

public class Splash extends JFrame{

	/**
	 * constructor for splash
	 */
	public Splash(){
		
		this.setUndecorated(true);
		this.draw();
		this.setResizable(false);
		this.setIconImage(new ImageIcon("./src/Images/Saah_ed_Nueb.png").getImage());
		this.pack();
		this.setVisible(true);
		this.repaint();
		this.setLocationRelativeTo(null);
		
	}
	
	/**
	 * method which draws the splashscreen
	 */
	public void draw(){
		
		Container container = this.getContentPane();
		container.setLayout(new VerticalFlowLayout());
		container.setPreferredSize(new Dimension(335, 385));
		container.setBackground(Color.BLACK);
		Logo logo = new Logo();
		container.add(logo);
		
		
	}

	/**
	 * this class is added in Splash
	 * @author Kevin Venema
	 *
	 */
	class Logo extends JPanel{
        private ImageIcon image;

        public Logo() {
        	this.setPreferredSize(new Dimension(325, 325));
            image = new ImageIcon("./src/Images/Nueb_splash.png");
        }       

        public void paintComponent(Graphics g){
            super.paintComponent(g);

            image.paintIcon(this, g, 0, 0);
        }

    }
}
