package tests;


import java.util.Map;
import java.util.Set;

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
public class CardsPeopleTests {

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
			
			Map<String, Player> testList = board.getPlayers();
			
			// test first in file: name, color and location
			assertEquals("Purple", testList.get("Professor Plum").getColor());
			assertEquals( 18, testList.get("Professor Plum").getRow());
			assertEquals( 0, testList.get("Professor Plum").getColumn());
			
			// test last in file
			assertEquals("Yellow", testList.get("Colonel Mustard").getColor());
			assertEquals( 7, testList.get("Colonel Mustard").getRow());
			assertEquals( 24, testList.get("Colonel Mustard").getColumn());

			// test mid file
			assertEquals("Green", testList.get("Mr. Green").getColor());
			assertEquals( 24, testList.get("Mr. Green").getRow());
			assertEquals( 18, testList.get("Mr. Green").getColumn());
			
		}
		
		// Test Loading in all cards
		@Test
		public void testLoadedCards(){
			
			Set<Card> testList = board.getDeck();
			
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
					case WEAPON:
						weapons++;
					case ROOM:
						rooms++;
				}
				switch(card.getName()){
					case "Mr. Mustard":
						mustard = true;
					case "Rope":
						rope = true;
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

			assertEquals(6, players);
			assertEquals( 6, weapons);
			assertEquals( 9, rooms);
		}

	}
