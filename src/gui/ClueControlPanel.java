package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;

public class ClueControlPanel extends JPanel {
	private JTextField name;
	private JTextField diceRoll;
	private JTextField guess;
	private JTextField response;
	private JButton nextPlayer = new JButton("Next Player");
	private JButton makeAnAccusation = new JButton("Make an Accusation");

//	private static ClueControlPanel theInstance = new ClueControlPanel();
//	private ClueControlPanel() {};
//	
//	public static ClueControlPanel getInstance() {
//		return theInstance;
//	}
//	
	public ClueControlPanel() {
		setLayout(new GridLayout(2,0));
//		setSize(1000, 1000);
		JPanel panel = createPanel1();
		add(panel);
		panel = createPanel2();
		add(panel);
				
	}
	
	
	private JPanel createPanel1() {
	      JPanel mainPanel = new JPanel();      
	      mainPanel.setLayout(new GridLayout(1,3));      

	      JPanel upperLeft = new JPanel();
	      upperLeft.setLayout(new GridLayout(2,1));
	      JLabel nameLabel = new JLabel("Whose turn?", JLabel.CENTER);

	    
	      //create next player and make an accusation buttons
	      nextPlayer.addActionListener( new ActionListener() {
	    	  public void actionPerformed(ActionEvent ae) {
	    		  Board.getInstance().nextPlayer();
	    		  repaint();
	    	  }
	      });
	      makeAnAccusation.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent ae) {
	    		  Board.getInstance().makeAccusation();
	    		  
	    	
	    	  }
	      });
	      mainPanel.add(nextPlayer);
	      mainPanel.add(makeAnAccusation);
	      return mainPanel;
	}

	
	private JPanel createPanel2(){
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1,3));
		
		JPanel left = new JPanel(); //Die Roll
		Border titleBorder = BorderFactory.createTitledBorder("Die Roll");
		left.setBorder(titleBorder);
		diceRoll = new JTextField(2);
	    diceRoll.setEditable(false);
	    left.add(diceRoll);
		mainPanel.add(left);
		
		JPanel center = new JPanel(); //Guess
		titleBorder = BorderFactory.createTitledBorder("Guess");
		center.setBorder(titleBorder);
		guess = new JTextField(30);
	    guess.setEditable(false);
		center.add(guess);
	    mainPanel.add(center);
		
		
		JPanel right = new JPanel(); //Guess result
		titleBorder = BorderFactory.createTitledBorder("Guess Result");
		right.setBorder(titleBorder);
		response = new JTextField(10);
	    response.setEditable(false);
		right.add(response);
		mainPanel.add(right);
		
		
		return mainPanel;
	}
	
	public static void main(String[] args) {
		// Create a JFrame
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue GUI");frame.setSize(1000, 1);
		
		
		// Create the JPanel and add it to the JFrame
		ClueControlPanel gui = new ClueControlPanel();
		frame.add(gui, BorderLayout.CENTER);
		frame.setVisible(true);
	
		
	}

}
