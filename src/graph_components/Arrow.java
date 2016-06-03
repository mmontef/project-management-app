package graph_components;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.ArrayList;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class Arrow extends JComponent {

	

	ArrayList<Point> arrowPoints = new ArrayList<Point>();
	
	public Arrow(){
		super();
	}
	
	public Arrow(int x1, int y1, int x2, int y2){
		
		super();
		Point p1 = new Point(x1,y1);
		Point p2 = new Point(x2,y2);
		
		this.arrowPoints.add(p1);
		this.arrowPoints.add(p2);
		
		
	}
	
	public Arrow(ArrayList<Point> points){
		this.arrowPoints = points;
	}
	
	public void paintComponent(Graphics g){
		
		double arcDegrees = 30*Math.PI/180;
		
		
		Graphics2D g2d = (Graphics2D) g;

		g2d.setStroke(new BasicStroke(3));
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(int i = 0; i < this.arrowPoints.size(); i+=2 ){
		
			int x1, x2, y1, y2;

		x1 = arrowPoints.get(i).x;
		y1 = arrowPoints.get(i).y;
		x2 = arrowPoints.get(i+1).x;
		y2 = arrowPoints.get(i+1).y;
		
		
		double theta;
		Point left ;
		Point right;
		if(x2-x1 > 0){
			
			theta= Math.atan((double)(y1 - y2)/(x2 -x1));
			left = new Point((int)(x2 + 50*Math.cos(Math.PI + theta - arcDegrees)),
					(int) (y2 -
							50*Math.sin(Math.PI + theta - arcDegrees)));
			
			right = new Point((int)(x2 + 50*Math.cos(Math.PI + theta + arcDegrees)),
					(int) (y2 -
							50*Math.sin(Math.PI + theta + arcDegrees)));

		}
		else{
			
			theta = Math.atan((double)(y1 - y2)/(x1 - x2));

			left = new Point((int)(x2 + 50*Math.cos(-theta - arcDegrees)),
					(int) (y2 -
							50*Math.sin(-theta - arcDegrees)));
			
			right = new Point((int)(x2 + 50*Math.cos(-theta + arcDegrees)),
					(int) (y2 -
							50*Math.sin(-theta + arcDegrees)));

		}
		
				
		g2d.drawLine(x1, y1, x2, y2);
		
		g2d.drawLine(x2, y2, left.x, left.y);
		
		g2d.drawLine(x2, y2, right.x, right.y);
		}
	}
	
	
	public ArrayList<Point> getArrowPoints() {
		return arrowPoints;
	}

	public void setArrowPoints(ArrayList<Point> arrowPoints) {
		this.arrowPoints = arrowPoints;
	}
}
