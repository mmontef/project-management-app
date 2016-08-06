package graphview_components;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.ext.JGraphModelAdapter;

import resources.Activities;
import saver_loader.DataResource;

public class CriticalPathChart extends JFrame {
	
	private DirectedGraph<activityVertex, DefaultEdge> directedGraph;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CriticalPathChart(DefaultDirectedGraph<Activities, DefaultEdge> graph, String title) {
		super(title);
		
		// create a visualization using JGraph, via the adapter
	    JGraph jgraph = new JGraph( new JGraphModelAdapter( graph ) );
	    
		setContentPane(jgraph);
	}
	
	public class activityVertex {
		public String ES;
		public String EF;
		public String LS;
		public String LF;
		public String Duration;
		public String Float;
		private ArrayList<activityVertex> parents;
		
		public activityVertex() {
			parents = new ArrayList<activityVertex>();
		}
		
		public void addParent(activityVertex v) {
			if (parents.contains(v) == false)
				parents.add(v);
		}
		
		public ArrayList<activityVertex> getParents() {
			return parents;
		}
		
		public String toString() {
			return "ES: " + ES + " EF: " + EF + " Duration: " + Duration + " LS: " + LS + " LF: " + LF + " Float: " + Float;
		}
	}

}
