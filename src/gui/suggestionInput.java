package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import clueGame.Board;
import clueGame.Card;
import clueGame.Solution;

@SuppressWarnings("serial")
public class suggestionInput extends JDialog{
	

	private JPanel peopleDrop;
	private JPanel roomsDrop;
	private JPanel weaponsDrop;

	JComboBox<Card> people;
	JComboBox<Card> weapons;
	
	private Card room;
	private Board board = Board.getInstance();
	
	
	public suggestionInput(Card rm) {
		
		room = rm;
		setTitle("Make a guess");
		setModal(true);
		setSize(300,300);
		
		setLayout(new GridLayout(4,1));
		setUpComboBoxs();
		
		//add each		
		add(roomsDrop);
		add(peopleDrop);
		add(weaponsDrop);

		JButton submit = new JButton("Submit");
		  //create next player and make an accusation buttons
		submit.addActionListener( new ActionListener() {
			
			  public void actionPerformed(ActionEvent ae) {
				  Board board = Board.getInstance();
				  Object person = people.getSelectedItem();
				  Object weapon = weapons.getSelectedItem();
				  
				  Solution suggestion = new Solution(((Card)person), room, ((Card)weapon));
				  
				  board.handleSuggestion(board.getHumanPlayer(),suggestion);
				  dispose();
			  }
		  });
		
		add(submit);
	}

	
	private void setUpComboBoxs() {
		
		// allocate memory
		people = new JComboBox<Card>();
		 weapons = new JComboBox<Card>();
		
		//classify each card into options
		for(Card c : board.getDeck() ) {
			switch (c.getType()) {
			case PERSON:
				people.addItem(c);
				break;
			case WEAPON:
				weapons.addItem(c);
				break;
			default:
				break;
			}
		}
		
		// put each dropdown into panel
		peopleDrop = new JPanel();
		Border titleBorder = BorderFactory.createTitledBorder("Person Guess");
		peopleDrop.setBorder(titleBorder);
		peopleDrop.add(people);
		
		roomsDrop = new JPanel();
		Border titleBorder1 = BorderFactory.createTitledBorder("Room Guess");
		roomsDrop.setBorder(titleBorder1);
		
		JTextField rooms = new JTextField(15);
		rooms.setText(room.getName());
		rooms.setEditable(false);
		roomsDrop.add(rooms);
		
		weaponsDrop = new JPanel();
		Border titleBorder2 = BorderFactory.createTitledBorder("Weapon Guess");
		weaponsDrop.setBorder(titleBorder2);
		weaponsDrop.add(weapons);
	}
}
