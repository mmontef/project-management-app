package driver;

/*HERE ARE SOME COMMENTS
 * 
 */

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import swing_components.ActivityPanel;
import swing_components.TabPanel;
import swing_components.*;

public class ClientLauncher {
	
	public static TabPanel tabPane;
	public static ActivityPanel activityPane;
	public static MenuBar menuBar;
	public static ProjectPanel projectPane;

	public static JFrame clientFrame;
	public static JFrame loginFrame;
	
	public static void main(String[] args) {

		final double loginWidthRatio =  1800.0/3200;
		final double loginHeightRatio = 800.0/1800;
		final double clientWidthRatio =  1800.0/3200;
		final double clientHeightRatio = 1200.0/1800;
		
		final int screenY = Toolkit.getDefaultToolkit().getScreenSize().height;
		final int screenX = Toolkit.getDefaultToolkit().getScreenSize().width;
		
		
		//Launch the Client Window and set basic frame variables
		SwingUtilities.invokeLater(new Runnable(){public void run(){
		
			clientFrame = new JFrame("Ultimate Sandwhich Program Management UI");
		
			clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			clientFrame.setSize((int)(screenX*clientWidthRatio), (int)(screenY*clientHeightRatio));
			clientFrame.setVisible(false);                                // Client Starts off hidden
			
			//Initialize JMenuBar
			menuBar = new MenuBar();
			clientFrame.setJMenuBar(menuBar.getMenuBar());
			
			clientFrame.getContentPane().setBackground(Color.black);
					
			loginFrame = new JFrame("Login");
			loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			loginFrame.setSize((int)(screenX*loginWidthRatio), (int)(screenY*loginHeightRatio));
			loginFrame.setVisible(true); //Login starts off visible
			loginFrame.setResizable(false);
			loginFrame.add(new LoginPanel());

			//Set the layout Manager of the Client frame
			clientFrame.getContentPane().setLayout(new GridBagLayout());
			GridBagConstraints c = new GridBagConstraints();
		
			//Initialize the Necessary Panels
			tabPane = new TabPanel();
			projectPane = new ProjectPanel();
			activityPane = new ActivityPanel();
				
			//Constraints for tabPanel
			c.insets = new Insets(0 ,0, 4, 0);
			c.weightx = 1;
			c.weighty = 3;
			c.gridx = 0;
			c.gridy = 0;
			c.gridwidth = 2;
			c.fill = GridBagConstraints.BOTH;
			//c.gridwidth = GridBagConstraints.BOTH;
			
			//Add Tab Panel
			clientFrame.add(tabPane, c);
			
			//Constraints for ProjectPanel
			c.insets = new Insets(1 ,0, 0, 0);
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 0;
			c.gridy = 1;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.BOTH;
			//c.gridwidth = GridBagConstraints.RELATIVE;
			
			//Add Project Panel
			clientFrame.add(projectPane, c);
			
			//Constraints for Activity Panel
			c.insets = new Insets(1,0,0,0);
			c.weightx = 1;
			c.weighty = 1;
			c.gridx = 1;
			c.gridy = 1;
			c.gridwidth = 1;
			c.fill = GridBagConstraints.BOTH;
			//c.gridwidth = GridBagConstraints.REMAINDER;
			
			//Add Activity Panel
			clientFrame.add(activityPane,c);
				
	}
		});
		
	}
}
