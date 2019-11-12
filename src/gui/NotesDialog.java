package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.Border;

import org.omg.CORBA.PRIVATE_MEMBER;

import clueGame.Board;
import clueGame.Card;
import clueGame.Player;

public class NotesDialog extends JDialog{
	
	private JPanel peopleDrop;
	private JPanel roomsDrop;
	private JPanel weaponsDrop;
	
	private JPanel peopleChecks;
	private JPanel roomsChecks;
	private JPanel weaponsChecks;

	private Board board = Board.getInstance();

	
	public NotesDialog() {
		
		setTitle("Notes");
		setSize(1000,500);
		
		setLayout(new GridLayout(3,2));
		setUpComboBoxs();
		setUpCheckBoxs();
		
		//add each
		add(peopleChecks);
		add(peopleDrop);
		
		add(roomsChecks);
		add(roomsDrop);
		
		add(weaponsChecks);
		add(weaponsDrop);

		
	}


	private void setUpCheckBoxs() {
		JCheckBox cBox;
		peopleChecks = new JPanel();
		weaponsChecks= new JPanel();
		roomsChecks= new JPanel();
		
		//classify each card into options
		for(Card c : board.getDeck() ) {
			cBox = new JCheckBox(c.getName(), false);
			
			switch (c.getType()) {			
			case PERSON:
				peopleChecks.add(cBox);
				break;
			case WEAPON:
				weaponsChecks.add(cBox);
				break;
			case ROOM:
				roomsChecks.add(cBox);
				break;
			default:
				break;
			}
			
		}
		
		// make border
		Border titleBorder1 = BorderFactory.createTitledBorder("People Seen");
		Border titleBorder2 = BorderFactory.createTitledBorder("Weapons Seen");
		Border titleBorder3 = BorderFactory.createTitledBorder("Rooms Seen");
		peopleChecks.setBorder(titleBorder1);
		weaponsChecks.setBorder(titleBorder2);
		roomsChecks.setBorder(titleBorder3);


	}
	
	private void setUpComboBoxs() {
		
		// allocate memory
		JComboBox<Card> people = new JComboBox<Card>();
		JComboBox<Card> rooms = new JComboBox<Card>();
		JComboBox<Card> weapons = new JComboBox<Card>();
		
		//classify each card into options
		for(Card c : board.getDeck() ) {
			switch (c.getType()) {
			case PERSON:
				people.addItem(c);
				break;
			case WEAPON:
				weapons.addItem(c);
				break;
			case ROOM:
				rooms.addItem(c);
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
		roomsDrop.add(rooms);
		
		weaponsDrop = new JPanel();
		Border titleBorder2 = BorderFactory.createTitledBorder("Weapon Guess");
		weaponsDrop.setBorder(titleBorder2);
		weaponsDrop.add(weapons);
	}



}
