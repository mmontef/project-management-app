package swing_components;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import graph_components.Arrow;

@SuppressWarnings("serial")
public class drawPanel extends JPanel implements MouseListener {

	Arrow myArrows;
	JLayeredPane layeredPane;
	
	public drawPanel(){
		
		this.addMouseListener(this);
		this.layeredPane = getRootPane().getLayeredPane();
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
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
