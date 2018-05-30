package imageEditor;


import imageEditor.TopMenuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TopMenuBar extends JMenuBar{
	
	
	PaintApp _pa;
	JMenu file;
    JMenuItem quit, clear;
    
    public TopMenuBar(PaintApp pa){
    	_pa = pa;
    	TopMenuBar.ItemHandler itemHandler = new TopMenuBar.ItemHandler();
	    /* Tabs */
	    file   = new JMenu("File");
	    //help   = new JMenu("Help"); TODO: Add quick help reference
	    
	    /* Buttons */
	    quit = new JMenuItem("Quit");
	    clear = new JMenuItem("New Drawing");
	    quit.addActionListener(itemHandler);
	    clear.addActionListener(itemHandler);
	    
	    //add quit button to file
	    file.add(quit);
	    file.add(clear);
	    
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
			else if(e.getSource() == clear) {
				_pa.clear();
				System.out.println("Calling New Drawing");
			} else {
				System.out.println("Nothing");
			}
			
		}
    	
    }
}
    
