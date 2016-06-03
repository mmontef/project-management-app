package swing_components;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import driver.ClientLauncher;
import graph_components.Arrow;
import graph_components.GraphBox;
import resources.Activities;
import saver_loader.DataResource;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel implements MouseListener {

	Arrow myArrows = new Arrow(200,200, 700,700);
	JLayeredPane layeredPane = new JLayeredPane();
	
	public DrawPanel(){
		
		this.setLayout(null);
		this.addMouseListener(this);
		//this.layeredPane = getRootPane().getLayeredPane();
		layeredPane.add(myArrows,(Integer) (JLayeredPane.DEFAULT_LAYER + 50));
		this.setBackground(Color.white);
	}
	public void drawGraphBox(){
		
	}
	
	public void drawArrows(){
		this.layeredPane.repaint();
	}
	
	public void updatePanel(){
		
		 this.drawGraphBox();
		 this.drawArrows();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		
		String activityDescription = (String) JOptionPane.showInputDialog(new JFrame(), "Enter Avtivity name/description",
				"Activity Name", JOptionPane.PLAIN_MESSAGE, null, null, null);
		
		String activityDuration = (String) JOptionPane.showInputDialog(new JFrame(), "Enter Duration of Activity",
				"Activity Duration", JOptionPane.PLAIN_MESSAGE, null, null, null);
		 
		
		int duration = Integer.parseInt(activityDuration);
		Point clickPoint = new Point(arg0.getPoint());
		
		//Create the activity
		//Activities activity = new Activities(activityDescription, duration, clickPoint.x, clickPoint.y); 
		
		//int currentProjectId = ClientLauncher.tabPane.getTabPanel().getSelectedIndex();
		
		//DataResource.getProjectbyProjectId(currentProjectId).addActivity(activity);
		
		GraphBox graph = new GraphBox();
		graph.setBounds(clickPoint.x, clickPoint.y, graph.getPreferredSize().width/2, graph.getPreferredSize().height/2);
		
		JPanel myPanel = (JPanel)arg0.getComponent();
		myPanel.add(graph);
		
		myPanel.repaint(clickPoint.x, clickPoint.y, clickPoint.x + graph.getWidth(), clickPoint.y + graph.getHeight());
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
