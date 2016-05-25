package graph_components;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class GraphBox extends JComponent{
	/*
	String earliestStart;
	String latestStart;
	String earliestFinish;
	String latestFinish;
	String activityDescription;
	String activityLabel;
	String activityFloat;
	String duration;
	String maxDuration;
*/
	//Basic Constructor, makes component visible gives it a preferred size.
	public GraphBox(){
		
		Dimension dimension = new Dimension(250, 150);
		this.setPreferredSize(dimension);
		this.setBackground(Color.black);
		this.setVisible(true);
	}
	
	//Will need to be adapted for Algorithm
	public GraphBox(int es, int ls, int ef, int lf, String description){
		
		Dimension dimension = new Dimension(250, 150);
		this.setPreferredSize(dimension);
		this.setBackground(Color.black);
		this.setVisible(true);
	/*	
		this.earliestStart = String.valueOf(es);
		this.latestStart = String.valueOf(ls);
		this.earliestFinish = String.valueOf(ef);
		this.latestFinish = String.valueOf(lf);
		this.activityDescription = description;
		*/
	}
	
	//How to component is painted.
	public void paintComponent(Graphics g){
		
		g.setColor(Color.BLACK);
		
		
		g.drawRect(0, 0, getWidth()-1,getHeight()-1);
		
		//Draw all horizontal lines
		g.drawLine(0, getHeight()/4, getWidth(), getHeight()/4);
		g.drawLine(0, getHeight()/2, getWidth()/5, getHeight()/2);
		g.drawLine(4*getWidth()/5, getHeight()/2,  getWidth(), getHeight()/2);
		g.drawLine(0, 3*getHeight()/4, getWidth(), 3*getHeight()/4);
		
		//Draw all vertical lines
		g.drawLine(getWidth()/2, 0, getWidth()/2, getHeight()/4);
		g.drawLine(getWidth()/5, getHeight()/4, getWidth()/5, 3*getHeight()/4);
		g.drawLine(4*getWidth()/5, getHeight()/4, 4*getWidth()/5, 3*getHeight()/4);
		g.drawLine(getWidth()/2, 3*getHeight()/4, getWidth()/2, getHeight());
		
		//Write in Values
		g.setFont(g.getFont().deriveFont(Font.BOLD, 25f));
		/*/g.drawString(earliestStart, getWidth()/30, getHeight()/2 - getHeight()/20);
		g.drawString(earliestFinish, 4*getWidth()/5+getWidth()/30, getHeight()/2 - getHeight()/20);
		g.drawString(latestStart, getWidth()/30, 3*getHeight()/4 - getHeight()/20);
		g.drawString(latestFinish, 4*getWidth()/5 + getWidth()/30, 3*getHeight()/4 - getHeight()/20);
		g.drawString(activityDescription, getWidth()/5 + getWidth()/25, getHeight()/2 + getHeight()/16);
*/	}
	

}
