package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

public class ExitMenuItem extends JMenuItem {

	public ExitMenuItem() {
		super("Exit");
		
		class MenuItemListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		}
		
		addActionListener(new MenuItemListener());
	}

}
