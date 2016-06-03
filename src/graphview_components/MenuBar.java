package graphview_components;

import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

import listview_components.Activity_form;
import listview_components.Project_form;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar{

	final JMenuBar menuBar = new JMenuBar();
	
	public MenuBar()
	{
	    //create menus
	    JMenu projectMenu = new JMenu("Projects");
	    JMenu activityMenu = new JMenu("Activities");
	    JMenu helpMenu = new JMenu("Help");
	  
	    //create project menu items
	    JMenuItem newProjectMenuItem = new JMenuItem("New");
	    newProjectMenuItem.setActionCommand("New Project");

	    JMenuItem openProjectMenuItem = new JMenuItem("Open");
	    openProjectMenuItem.setActionCommand("Open");

	    JMenuItem saveProjectMenuItem = new JMenuItem("Save");
	    saveProjectMenuItem.setActionCommand("Save");
	    
	    JMenuItem deleteProjectMenuItem = new JMenuItem("Delete");
	    deleteProjectMenuItem.setActionCommand("Delete");

	    JMenuItem exitMenuItem = new JMenuItem("Exit");
	    exitMenuItem.setActionCommand("Exit");
	    
	    //activity menu items
	    JMenuItem newActivityMenuItem = new JMenuItem("New");
	    newActivityMenuItem.setActionCommand("New");
	    
	    JMenuItem deleteActivityMenuItem = new JMenuItem("Delete");
	    deleteActivityMenuItem.setActionCommand("Delete");
	    
	    //about menu items
	    JMenuItem aboutMenuItem = new JMenuItem("About");
	    aboutMenuItem.setActionCommand("About");

	    MenuItemListener menuItemListener = new MenuItemListener();

	    //project menu items action listeners
	    newProjectMenuItem.addActionListener(menuItemListener);
	    openProjectMenuItem.addActionListener(menuItemListener);
	    saveProjectMenuItem.addActionListener(menuItemListener);
	    deleteProjectMenuItem.addActionListener(menuItemListener);
	    exitMenuItem.addActionListener(menuItemListener);
	    
	    //activity menu items action listeners
	    newActivityMenuItem.addActionListener(menuItemListener);
	    deleteActivityMenuItem.addActionListener(menuItemListener);
	    
	    //about menu items action listeners
	    aboutMenuItem.addActionListener(menuItemListener);

	    //add menu items to menus
	    projectMenu.add(newProjectMenuItem);
	    //projectMenu.addSeparator();
	    projectMenu.add(openProjectMenuItem);
	    //projectMenu.addSeparator();
	    projectMenu.add(saveProjectMenuItem);
	   // projectMenu.addSeparator();
	    projectMenu.add(deleteProjectMenuItem);
	    //projectMenu.addSeparator();
	    projectMenu.add(exitMenuItem);
	    
	    activityMenu.add(newActivityMenuItem);
	    //activityMenu.addSeparator();
	    activityMenu.add(deleteActivityMenuItem);
	    
	    helpMenu.add(aboutMenuItem);
	   
	    //add menu to menubar
	    menuBar.add(projectMenu);
	    menuBar.add(activityMenu);
	    menuBar.add(helpMenu);
	    
	    
	}
	
    public JMenuBar getMenuBar() {
		return menuBar;
	}

	private class MenuItemListener implements ActionListener {
        
		public void actionPerformed(ActionEvent e) {            
           
        	if(e.getActionCommand()== "New Project"){
        		SwingUtilities.invokeLater(new Runnable(){

					@Override
					public void run() {
						
						Project_form frame = new Project_form();
						frame.setVisible(true);
						
					}
        	});
        		}
        	
        		if(e.getActionCommand() == "Exit"){
        		System.exit(0);
        		}
        		
        		if(e.getActionCommand() == "New"){
        			
        			SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							Activity_form frame =  new Activity_form();
							frame.setVisible(true);
						}
					});
        			
        		}
        		
        	
        }    
     }
	
	}

