package tests;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.CardType;

import clueGame.Player;
import clueGame.Solution;


/*
 * Tests actions for board
 * Casey Turner, Murat Tuter
 * 
 */
public class GameActionTests {

	private static Board board;

	
	@BeforeClass
	public static void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("clueGameLayout.csv", "roomLegend.txt", "weapons.txt", "players.txt");		
		board.initialize();
	}
	

	/*
	 * Test Selecting a target location - ComputerPlayer
	 */
	
	// test if targets are random
	@Test
	public void testTargetRandomSelection() {
		 ComputerPlayer player = new ComputerPlayer();
		 
		 // Pick a location with no rooms in target, just three targets
		 board.calcTargets(18, 0, 2);
		 boolean loc_1 = false;
		 boolean loc_2 = false;
		 boolean loc_3 = false;
		 
		 // Run the test a large number of times
		 for (int i=0; i<100; i++) {
			 BoardCell selected = player.pickLocation(board.getTargets());
			 if (selected == board.getCellAt(17, 1))
				 loc_1 = true;
			 else if (selected == board.getCellAt(19, 1))
				 loc_2 = true;
			 else if (selected == board.getCellAt(18, 2))
				 loc_3 = true;
			 else {
				fail("Invalid target selected");

			 }
		 }
		 // Ensure each target was selected at least once
		 assertTrue(loc_1);
		 assertTrue(loc_2);
		 assertTrue(loc_3);
	}
	
	// test if an unvisited room is a target it is selected
	@Test
	public void testUnvisitedRoomSelection() {
		 ComputerPlayer player = new ComputerPlayer();
		 
		 // Pick a location with no rooms in target, just three targets
		 board.calcTargets(17, 3, 2);
			 
		 BoardCell selected = player.pickLocation(board.getTargets());
		 assertTrue(selected.isRoom());

	}
	
	// test if a room is recently visited, targets are random
	@Test
	public void testVisitedRoomSelection() {
		 ComputerPlayer player = new ComputerPlayer();
		 
		 player.addToVisited(board.getCellAt(11, 22));
		 
		 board.calcTargets(12, 22, 1);
		 boolean loc_1 = false;
		 boolean loc_2 = false;
		 boolean loc_3 = false;
		 boolean loc_4 = false;

		 // Run the test a large number of times
		 for (int i=0; i<100; i++) {
			 BoardCell selected = player.pickLocation(board.getTargets());
			 if (selected == board.getCellAt(11, 22))
				 loc_1 = true;
			 else if (selected == board.getCellAt(12, 23))
				 loc_2 = true;
			 else if (selected == board.getCellAt(12, 21))
				 loc_3 = true;
			 else if (selected == board.getCellAt(13, 22))
				 loc_4 = true;
			 else
				 fail("Invalid target selected");
		 }
		 // Ensure each target was selected at least once
		 assertTrue(loc_1);
		 assertTrue(loc_2);
		 assertTrue(loc_3);
		 assertTrue(loc_4);

	}
	
	/*
	 * Test accusations
	 */
	// test correct one
	@Test
	public void testCorrectAccusation() {
		Card murderer = null;
		Card crimeScene = null;
		Card murderWeapon = null;

		// get three of each type of card
        for(Card card : board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				murderer = card;
				break;
			case WEAPON:
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene = card;
			}
        }
        
        // create solution
		Solution testSolution = new Solution(murderer, crimeScene, murderWeapon);
		Solution actualSolution = new Solution(murderer, crimeScene, murderWeapon);

		board.setTheCrime(actualSolution);
		
		assertTrue(board.checkAccusation(testSolution));
		
	}    
	
	//test wrong person
	@Test
	public void testWrongPersonAccusation() {
		Card murderer = null;
		Card wrongMurderer = null;
		Card crimeScene = null;
		Card murderWeapon = null;

		// get three of each type of card
        for(Card card : board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				wrongMurderer = murderer;
				murderer = card;
				break;
			case WEAPON:
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene = card;
			}
        }
        
        // create solution
		Solution testSolution = new Solution(wrongMurderer, crimeScene, murderWeapon);
		Solution actualSolution = new Solution(murderer, crimeScene, murderWeapon);

		board.setTheCrime(actualSolution);
		
		assertFalse(board.checkAccusation(testSolution));
		
	} 
	
	// test wrong weapon
	@Test
	public void testWrongWeaponAccusation() {
		Card murderer = null;
		Card wrongWeapon = null;
		Card crimeScene = null;
		Card murderWeapon = null;

		// get three of each type of card
        for(Card card : board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				murderer = card;
				break;
			case WEAPON:
				wrongWeapon = murderWeapon;
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene = card;
			}
        }
        
        // create solution
		Solution testSolution = new Solution(murderer, crimeScene, wrongWeapon);
		Solution actualSolution = new Solution(murderer, crimeScene, murderWeapon);

		board.setTheCrime(actualSolution);
		
		assertFalse(board.checkAccusation(testSolution));
		
	} 
	// test wrong room
	@Test
	public void testWrongRoomAccusation() {
		Card murderer = null;
		Card wrongRoom = null;
		Card crimeScene = null;
		Card murderWeapon = null;

		// get three of each type of card
        for(Card card : board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				murderer = card;
				break;
			case WEAPON:
				murderWeapon = card;
				break;
			case ROOM:
				wrongRoom = crimeScene;
				crimeScene = card;
			}
        }
        
        // create solution
		Solution testSolution = new Solution(murderer, wrongRoom, murderWeapon);
		Solution actualSolution = new Solution(murderer, crimeScene, murderWeapon);

		board.setTheCrime(actualSolution);
		
		assertFalse(board.checkAccusation(testSolution));
		
	} 
	
	
	/*
	 * Test create suggestion 
	 * 
	 * room will be tested with each test
	 */
	
	
	// If only one weapon not seen, it's selected
	@Test
	public void testOneWeaponSuggestion() {
		ComputerPlayer player = new ComputerPlayer("Buster", "Red", 22, 20);

		Card weapon = null;

		// add all but one weapon to seen, and randomly add the rest
		int i = 0;
        for(Card card : board.getDeck()) {
        	i++;
			switch(card.getType()){
			case WEAPON:
				player.addToSeen(weapon);
				weapon = card;
				break;
			default:
				if(i%2 == 0)
					player.addToSeen(card);			}
        }
        
        // create solution
        Solution suggestion =  player.createSuggestion();
        
        // check the room is the room player is in
        assertEquals(suggestion.getCrimeScene().getName() , "Kitchen");
        
        //check the weapon is correct
        assertEquals(suggestion.getMurderWeapon(), weapon);
	} 
	
	// If only one person not seen, it's selected
	@Test
	public void testOnePersonSuggestion() {
		ComputerPlayer player = new ComputerPlayer("Buster", "Red", 5, 19);

		Card person = null;

		// add all but one person to seen, and randomly add the rest
		int i = 0;
        for(Card card : board.getDeck()) {
        	i++;
			switch(card.getType()){
			case PERSON:
				player.addToSeen(person);
				person = card;
				break;
			default:
				if(i%2 == 0)
					player.addToSeen(card);
			}
        }
        
        // create solution
        Solution suggestion =  player.createSuggestion();
        
        // check the room is the room player is in
        assertEquals(suggestion.getCrimeScene().getName() , "Library");
        
        //check the person is correct
        assertEquals(suggestion.getMurderer(), person);
	} 
	
	// test randomly selected from unseen
	@Test
	public void testRandomSuggestion() {
		ComputerPlayer player = new ComputerPlayer("Buster", "Red", 5, 19);

		Card person = null;
		Map<Card,Boolean> unseenCards = new HashMap<Card, Boolean>();
		int i = 0;
		
		// add half of cards to seen and half not
        for(Card card : board.getDeck()) {
        	i++;
			switch(card.getType()){
			case ROOM:
				player.addToSeen(card);
				break;
			default:
				if(i%2 == 0) {
					unseenCards.put(card, false);
				} else {
					player.addToSeen(card);
				}
			}
        }
        
			
		// run suggestions alot to test random
		for(int j = 0; j < 1000; j++) {
	        // create solution
	        Solution suggestion =  player.createSuggestion();
	        
	        unseenCards.put(suggestion.getMurderer(), true);
	        unseenCards.put(suggestion.getMurderWeapon(), true);

	        // check the room is the room player is in
	        assertEquals(suggestion.getCrimeScene().getName() , "Library");
		}
		
		// test each unseen person and weapon was suggested
		for(Boolean seen: unseenCards.values()) {
			assertTrue(seen);
		}
    }
	
	/*
	 * Disprove suggestion tests
	 * 
	 */
	

	// test returns null if no match
	@Test
	public void testNullSuggestion() {
		ComputerPlayer player = new ComputerPlayer();

    	Card murderer = null;
    	Card murderer1 = null;
		Card crimeScene = null;
		Card crimeScene1 = null;
		Card murderWeapon = null;
		Card murderWeapon1 = null;

		// get 2 of each type of card
        for(Card card : board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				murderer1 = murderer;
				murderer = card;
				break;
			case WEAPON:
				murderWeapon1 = murderWeapon;
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene1 = crimeScene;
				crimeScene = card;
			}
        }
        
        // create the player hand
        player.addToHand(murderer1);
        player.addToHand(crimeScene1);
        player.addToHand(murderWeapon1);

        // create the suggestion
        Solution suggestion = new Solution(murderer, crimeScene, murderWeapon);
        
        // check if null
        assertNull(player.disproveSuggestion(suggestion));
	} 
	
	// test one matching card
	@Test
	public void testOneMatchSuggestion() {
		ComputerPlayer player = new ComputerPlayer();

    	Card murderer = null;
		Card crimeScene = null;
		Card crimeScene1 = null;
		Card murderWeapon = null;
		Card murderWeapon1 = null;

		// get 2 of each type of card
        for(Card card : board.getDeck()) {
			switch(card.getType()){
			case PERSON:
				murderer = card;
				break;
			case WEAPON:
				murderWeapon1 = murderWeapon;
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene1 = crimeScene;
				crimeScene = card;
			}
        }
        
        // create the player hand
        player.addToHand(murderer);
        player.addToHand(crimeScene1);
        player.addToHand(murderWeapon1);

        // create the suggestion
        Solution suggestion = new Solution(murderer, crimeScene, murderWeapon);
        
        // check if murder card is returned
        assertEquals(murderer, player.disproveSuggestion(suggestion));
	} 
	
	// tests multiple cards, random selection
	@Test
	public void testMultipleMatchSuggestion() {
		ComputerPlayer player = new ComputerPlayer();
		
		Map<Card, Boolean> cards = new HashMap<Card, Boolean>();
		Vector<Card> people = new Vector<Card>();
		Vector<Card> weapons = new Vector<Card>();
		Vector<Card> rooms = new Vector<Card>();

		Random random = new Random();

		// sort cards for selection, and add everything to player hand
        for(Card card : board.getDeck()) {
            player.addToHand(card);
        	cards.put(card, false);
        	
			switch(card.getType()){
			case PERSON:
				people.add(card);
				break;
			case WEAPON:
				weapons.add(card);
				break;
			case ROOM:
				rooms.add(card);
			}
        }
        
        // run a ton of loops to ensure has chance to present each card in hand
        for(int i = 0; i < 10000 ; i ++) {
            // create the suggestion
            Solution suggestion = new Solution(people.get(random.nextInt(people.size())), 
            									rooms.get(random.nextInt(rooms.size())), 
            									weapons.get(random.nextInt(weapons.size())));
            cards.put(player.disproveSuggestion(suggestion), true);
        }

        
        // check if all cards were presented at one point
        for(boolean shown : cards.values()) {
        	assertTrue(shown);
        }
	} 
	

	
	/*
	 * Test for board to handle a suggestion
	 */

	// tests all types of suggestions
	@Test
	public void testUnProvableSuggestion() {
		board.deal();
		Map<String, Player> players = board.getPlayers();
		
		// test suggestion nobody can disprove
		assertNull(board.handleSuggestion("Mrs. Peacock", board.getTheCrime()));
	
	}
	
	// test Suggestion only accusing player can disprove returns null
	@Test
	public void testAccusserSuggestion() {
		board.deal();
		Map<String, Player> players = board.getPlayers();

	
		// test Suggestion only accusing player can disprove returns null
		Set<Card> hand = players.get("Mrs. Peacock").getHand();
		
    	Card murderer = null;
		Card crimeScene = null;
		Card murderWeapon = null;

		// get each type of card
        for(Card card : hand) {
			switch(card.getType()){
			case PERSON:
				murderer = card;
				break;
			case WEAPON:
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene = card;
			}
        }
        
		assertNull(board.handleSuggestion("Mrs. Peacock", new Solution(murderer, crimeScene, murderWeapon)));
	}
	
	// test human player
	@Test
	public void testHumanSuggestion() {
		board.deal();
		Map<String, Player> players = board.getPlayers();		
		Player p = players.get("Colonel Mustard");
		
		Card murderer = new Card("test", CardType.PERSON);
		Card murderWeapon = new Card("test", CardType.WEAPON);
		Card crimeScene = new Card("test", CardType.ROOM);
		
		p.addToHand(murderer);
		p.addToHand(crimeScene);
		p.addToHand(murderWeapon);


		// Suggestion only human can disprove, but human is accuser, returns null
		assertNull(board.handleSuggestion("Colonel Mustard", new Solution(murderer, crimeScene, murderWeapon)));
		
		// test Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		assertEquals(murderer, board.handleSuggestion("Mrs. Peacock", new Solution(murderer, new Card("test", CardType.ROOM), new Card("test", CardType.WEAPON))));


	} 
	
	// test multiple players are in order
	@Test
	public void testMultipleDisproveSuggestion() {
		board.deal();
		Map<String, Player> players = board.getPlayers();		
		Set<Card> hand = players.get("Colonel Mustard").getHand();
		
    	Card murderer = null;
		Card crimeScene = null;
		Card murderWeapon = null;


		// get each type of card from hand
        for(Card card : hand) {
			switch(card.getType()){
			case PERSON:
				murderer = card;
				break;
			case WEAPON:
				murderWeapon = card;
				break;
			case ROOM:
				crimeScene = card;
			}
        }
        
		// test Suggestion only human can disprove returns answer (i.e., card that disproves suggestion)
		assertEquals(murderer, board.handleSuggestion("Mrs. Peacock", new Solution(murderer, new Card("test", CardType.ROOM), new Card("test", CardType.WEAPON))));

		// Suggestion only human can disprove, but human is accuser, returns null
		assertNull(board.handleSuggestion("Colonel Mustard", new Solution(murderer, crimeScene, murderWeapon)));

	} 
	

	
}
