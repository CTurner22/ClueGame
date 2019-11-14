package tests;


import java.util.Map;
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


/*
 * Tests for Adj and targets for board
 * Casey Turner, Murat Tuter
 * 
 */
public class GameSetupTests {

		private static Board board;

		
		@BeforeClass
		public static void setUp() {
			board = Board.getInstance();
			board.setConfigFiles("clueGameLayout.csv", "roomLegend.txt", "weapons.txt", "players.txt");		
			board.initialize();
		}

		// Test Loading in players
		@Test
		public void testLoadedPlayers(){
			
			Vector<Player> testList = board.getPlayers();
			
			// test first in file: name, color and location
			Player firstInPlayerList = testList.get(0);
			assertEquals("Magenta", firstInPlayerList.getColor());
			assertEquals( 18, firstInPlayerList.getRow());
			assertEquals( 0, firstInPlayerList.getColumn());
			
			// test last in file
			Player lastInPlayerList = testList.get(testList.size()-1);
			assertEquals("Yellow", lastInPlayerList.getColor());
			assertEquals( 7, lastInPlayerList.getRow());
			assertEquals( 24, lastInPlayerList.getColumn());

			// test mid file
			Player midInPlayerList = testList.get(2);
			assertEquals("Green", midInPlayerList.getColor());
			assertEquals( 24, midInPlayerList.getRow());
			assertEquals( 18, midInPlayerList.getColumn());
			
		}
		
		// Test Loading in all cards
		@Test
		public void testLoadedCards(){
			Vector<Card> testList = board.getDeck();
			
			// test size of deck
			// 9 rooms + 6 players + 6 weapons
			assertEquals(21, testList.size());

			// loop through and evaluate the deck
			Boolean mustard = false, rope = false, kitchen = false;
			int rooms = 0, players = 0, weapons= 0;
			
			for(Card card : testList) {
				switch(card.getType()){
					case PERSON:
						players++;
						break;
					case WEAPON:
						weapons++;
						break;
					case ROOM:
						rooms++;
				}
				
				switch(card.getName()){
					case "Colonel Mustard":
						mustard = true;
						break;
					case "Rope":
						rope = true;
						break;
					case "Kitchen":
						kitchen = true;
					default:
				}
			}
			
			// test deck contains Mr.Mustard, Rope, and Kitchen
			assertTrue(mustard);
			assertTrue(rope);
			assertTrue(kitchen);

			// test correct number of each type of card

			assertEquals( 6, players);
			assertEquals( 6, weapons);
			assertEquals( 9, rooms);
		}
		
		
		// Test dealing cards
		@Test
		public void testDealingCards(){
			
			board.deal();
			
			Vector<Player> players = board.getPlayers();
			Vector<Card> originalDeck = board.getDeck();
			
			// evaluate deck
			int cardsInHands = 0;
			int maxHand = 0;
			int minHand = originalDeck.size();
			for(Player person : players) {
				
				// test that each card dealt is in the original deck
				for(Card card : person.getHand()) {
					assertTrue(originalDeck.contains(card));
					cardsInHands++;
				}
				
				if (person.getHand().size() > maxHand)
					maxHand = person.getHand().size();
				if (person.getHand().size() < minHand)
					minHand = person.getHand().size();
			}
			
			// test that all cards (beside the three for the solution) were dealt
			assertEquals(cardsInHands, originalDeck.size() - 3);


			// test that all hands were roughly the same size
			assertTrue((maxHand-minHand) <= 1);
			
		}
		

	}
