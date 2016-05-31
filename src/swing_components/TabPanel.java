package swing_components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import graph_components.GraphBox;
import resources.Activities;

@SuppressWarnings("serial")
public  class TabPanel extends JPanel {
	
	
	public JTabbedPane tabPane;
	float fontScalar = Toolkit.getDefaultToolkit().getScreenSize().height/1800f;

	public TabPanel(){
		
		
				
		tabPane = new JTabbedPane();
	    

		Font fs30 = tabPane.getFont().deriveFont(Font.BOLD, fontScalar*30f);
		
		tabPane.setFont(fs30);
	   
	    //This line insures the tab panel doesnt grow as tabs get added
	    tabPane.setPreferredSize(this.getPreferredSize());
	   
	  //GridLayout so the child will fill the whole container
	  	this.setLayout(new GridLayout());
	    
	    this.add(tabPane);

	}

	@Override
	public void paintComponent(Graphics g){
		
		BufferedImage blurBackground = null;
		
		try {
			blurBackground = ImageIO.read(new File("blur.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	
		g.drawImage(blurBackground, 0, 0, getWidth(), getHeight(), null);
		
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(Font.BOLD, fontScalar*50f));
		g.drawString("No Project Currently in Progress", getWidth()/4, getHeight()/2);
		g.setFont(g.getFont().deriveFont(Font.PLAIN, fontScalar*40f));
		g.drawString("Create a new Project to get Started", getWidth()/4, (int) (getHeight()/1.6));

		

		
	}
	
	public void setTabPanel(JTabbedPane newTabPane){
		this.tabPane = newTabPane;
	}
	
	public JTabbedPane getTabPanel(){
		return this.tabPane;
	}
	
	public String toString(){
		return "I am the tabPanel";
	}
	
	public  void  addProjectTab(String name){
		
		//Create a JPanel Tab
		JPanel tab = new JPanel(null);
		
		
		tab.setPreferredSize(this.getPreferredSize());
		tab.setBackground(Color.white);
		tab.addMouseListener(new Mouse());
			
		
		//this.getTabPanel().insertTab("Project " + (this.getTabPanel().getTabCount()+1), null, tab, null, this.getTabPanel().getTabCount());
		this.getTabPanel().insertTab(name,null, tab, null,this.getTabPanel().getTabCount());
	}
	
	
	private class Mouse implements MouseListener{

		private ArrayList<Point> myPoints = new ArrayList<Point>();
		@Override
		public void mouseClicked(MouseEvent arg0) {

			
			String activityDescription = (String) JOptionPane.showInputDialog(new JFrame(), "Enter Avtivity name/description",
					"Activity Name", JOptionPane.PLAIN_MESSAGE, null, null, null);
			
			String activityDuration = (String) JOptionPane.showInputDialog(new JFrame(), "Enter Duration of Activity",
					"Activity Duration", JOptionPane.PLAIN_MESSAGE, null, null, null);
			 
			
			int duration = Integer.parseInt(activityDuration);
			Point clickPoint = new Point(arg0.getPoint());
			myPoints.add(clickPoint);
			
			Activities activity = new Activities(1,activityDescription, duration, clickPoint.x, clickPoint.y);
			
			//TODO MICHAEL NEED ACTIVITY TO GO TO DATABASE
			
			GraphBox graph = new GraphBox();
			graph.setBounds(clickPoint.x, clickPoint.y, graph.getPreferredSize().width/2, graph.getPreferredSize().height/2);
			

			JPanel myPanel = (JPanel)arg0.getComponent();
			myPanel.add(graph);
			
			myPanel.repaint(clickPoint.x, clickPoint.y, clickPoint.x + graph.getWidth(), clickPoint.y + graph.getHeight());
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
