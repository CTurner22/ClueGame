package gui;

import java.awt.Dialog;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JMenuItem;

public class NotesMenuItem extends JMenuItem{
	

		public NotesMenuItem() {
			super("Notes");
			
			class MenuItemListener implements ActionListener{
				public void actionPerformed(ActionEvent e) {
					JDialog d = new NotesDialog();
					d.setVisible(true);
				}
			}
			
			addActionListener(new MenuItemListener());
		}

}
