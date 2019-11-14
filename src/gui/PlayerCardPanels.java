package gui;

import java.awt.GridLayout;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import clueGame.Card;

/*
 * Player Cards Display
 * Casey turner, Murat Tuter
 * 
 */
@SuppressWarnings("serial")
public class PlayerCardPanels extends JPanel {
	public static final int HEIGHT = 1000;
	public static final int WIDTH = 150;

	private Set<Card> hand;
	
	public PlayerCardPanels(Set<Card> cards) {
		
		// set the hand
		hand = cards;
		
		//make panel
		setSize(WIDTH, HEIGHT);
		setLayout(new GridLayout(hand.size(),0));
		Border titleBorder = BorderFactory.createTitledBorder("Your Cards");
		setBorder(titleBorder);
		
		// create the card displays
		createCardPanels();
	}

	private void createCardPanels() {

		// create panel for each type
		JPanel cardPanelPeople = new JPanel();
		JPanel cardPanelWeapon = new JPanel();
		JPanel cardPanelRoom = new JPanel();;

		//create each border
		Border titleBorder = BorderFactory.createTitledBorder("People");
		cardPanelPeople.setBorder(titleBorder);
		titleBorder = BorderFactory.createTitledBorder("Weapons");
		cardPanelWeapon.setBorder(titleBorder);
		titleBorder = BorderFactory.createTitledBorder("Rooms");
		cardPanelRoom.setBorder(titleBorder);

		//add each card to each panel
		JTextField cardName;
	
		for(Card c : hand) {
			cardName = 	new JTextField(c.getName());
			cardName.setEditable(false);
			
			switch(c.getType()) {
			case WEAPON:
				cardPanelWeapon.add(cardName);
				break;
			case PERSON:
				cardPanelPeople.add(cardName);
				break;
			case ROOM:
				cardPanelRoom.add(cardName);
				break;
			}
		}
		
		
		//add all panels
		add(cardPanelPeople);
		add(cardPanelWeapon);
		add(cardPanelRoom);

	}
	
}
