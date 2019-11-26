package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import gui.ClueControlPanel;
import gui.ExitMenuItem;
import gui.NotesMenuItem;
import gui.PlayerCardPanels;

public class gameControl {
	
	private JFrame frame;
	private static Board board;
	
	public static void main(String[] args) {
		board = Board.getInstance();
		board.setConfigFiles("clueGameLayout.csv", "roomLegend.txt", "weapons.txt", "players.txt");		
		board.initialize();
		board.deal();

		gameControl game = new gameControl();
		game.setUpGUI();
		
	}
	
	private void setUpGUI() {
		// Create a JFrame
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Clue Game");
		frame.setSize(board.getNumColumns()*BoardCell.SCALE_FACTOR + PlayerCardPanels.WIDTH, board.getNumRows()*BoardCell.SCALE_FACTOR+ClueControlPanel.HEIGHT);
		
		//create menu bar
		JMenu menu = new JMenu("File");
		menu.add(new ExitMenuItem());
		menu.add(new NotesMenuItem());
		
		JMenuBar bar = new JMenuBar();
		bar.add(menu);
		frame.setJMenuBar(bar);
		
		// Create the JPanels and add it to the JFrame
		ClueControlPanel gui = new ClueControlPanel(board.getHumanPlayer());
		board.setControlPanel(gui);
		PlayerCardPanels cardsPanel = new PlayerCardPanels(board.getHumanPlayer().getHand());
		frame.add(gui, BorderLayout.SOUTH);
		frame.add(board, BorderLayout.CENTER);
		frame.add(cardsPanel, BorderLayout.EAST);
		
		// show it
		frame.setVisible(true);
		
		// make start popup
		String welcomMessage = "Hello, you are " + board.getHumanPlayer().getName() +  ", Press next player to begin play.";
		JOptionPane.showMessageDialog(frame, welcomMessage, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
}
