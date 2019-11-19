package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import clueGame.Board;
import clueGame.Player;

/*
 * The GUI
 * Casey Turner, Murat Tuter
 */

public class ClueControlPanel extends JPanel {
	private JTextField name;
	private JTextField diceRoll;
	private JTextField guess;
	private JTextField response;
	private JButton nextPlayer = new JButton("Next Player");
	private JButton makeAnAccusation = new JButton("Make an Accusation");
	private Player currentPlayer;
	private static Boolean humanTurnDone;

	private Random random;
	
	public static final int HEIGHT = 215;
	

	public ClueControlPanel(Player p) {
		//Initialize everything
		currentPlayer = p;
		humanTurnDone = true;
		random = new Random();
		
		setSize(1000, HEIGHT);
		setLayout(new GridLayout(2,0));
		JPanel panel = createPanel1();
		add(panel);
		panel = createPanel2();
		add(panel);
				
	}
	
	
	private JPanel createPanel1() {
		  JPanel mainPanel = new JPanel();      
		  mainPanel.setLayout(new GridLayout(1,3));      
		
		  // make current player indicator
		  JPanel currentPlayerPanel = new JPanel(); 
		  currentPlayerPanel.setBorder(BorderFactory.createTitledBorder("Current Player"));
		  
		  JTextField cP = new JTextField(currentPlayer.getName());
		  cP.setEditable(false);
		  currentPlayerPanel.add(cP);
		
		  //create next player and make an accusation buttons
		  nextPlayer.addActionListener( new ActionListener() {
			
			  public void actionPerformed(ActionEvent ae) {
				  Board board = Board.getInstance();
				  if(humanTurnDone) {
					  
					  //get next player
					  currentPlayer = board.nextPlayer();
					  
					  // roll the dice
					  int roll = random.nextInt(6) + 1;
					  
					  //update control panel
					  diceRoll.setText(String.valueOf(roll));
					  cP.setText(currentPlayer.getName());
					  repaint();
					  
					  // handle movements
					  board.handleMovements(roll);
					  humanTurnDone = !board.getWaitingForHuman();
					  
				  } else {
					  // display error
						JOptionPane.showMessageDialog(null, "Please click your desired target move before moving to next player.", "Error", JOptionPane.ERROR_MESSAGE);
				  }

			  }
		  });
		  
		  makeAnAccusation.addActionListener(new ActionListener() {
			  public void actionPerformed(ActionEvent ae) {
				  Board.getInstance().makeAccusation();
				  
			  }
		  });
		  
		  
		  // add them all to main panel
		  mainPanel.add(currentPlayerPanel);
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
		guess = new JTextField(20);
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

	public static void setHumanTurnDone() {
		humanTurnDone = true;
	}

}
