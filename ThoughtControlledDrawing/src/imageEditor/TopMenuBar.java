package imageEditor;


import imageEditor.TopMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TopMenuBar extends JMenuBar{
	
	

	JMenu file;
    JMenuItem quit;
    
    public TopMenuBar(){
    	TopMenuBar.ItemHandler itemHandler = new TopMenuBar.ItemHandler();
	    /* Tabs */
	    file   = new JMenu("File");
	    //help   = new JMenu("Help"); TODO: Add quick help reference
	    
	    /* Buttons */
	    quit = new JMenuItem("Quit");
	    quit.addActionListener(itemHandler);
	    
	    //add quit button to file
	    file.add(quit);
	    
	    //add file to menu bar
	    add(file);
    }
    
    private class ItemHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == quit) {
				Main.painter.dispose();
				System.exit(0);
			}
			
		}
    	
    }
}
    
