package graphview_components;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jgraph.JGraph;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgraph.layout.JGraphLayoutAlgorithm;
import org.jgraph.layout.SugiyamaLayoutAlgorithm;
import org.jgrapht.graph.*;
import org.jgrapht.ext.JGraphModelAdapter;

import resources.Activities;
import saver_loader.DataResource;

public class EarnedValueAnalysis extends JFrame {

	private static final long serialVersionUID = 1L;

	public EarnedValueAnalysis(String title) {
		super(title);
		
		String[] performanceIndicatorTitles = {
				"Planned Value", 
				"Earned Value", 
				"Actual Cost", 
				"Schedule Variance", 
				"Schedule Performance Index", 
				"Cost Variance", 
				"Cost Performance Index"
				};
		
		String[] forcastTitles = {
				"Is the project on schedule?", 
				"Is the project respecting the budget?", 
				"Are we spending too much?", 
				"Are we on schedule?", 
				"Are costs as planned?", 
				"Are we being efficient?"
				};
		
		String[] forcast = {
				DataResource.selectedProject.isProjectOnSchedule(),
				DataResource.selectedProject.isProjectRespectingBudget(),
				DataResource.selectedProject.areWeSpendingTooMuch(),
				DataResource.selectedProject.areWeOnSchedule(),
				DataResource.selectedProject.isCostAsPlanned(),
				DataResource.selectedProject.isTheProjectEfficient()
				};
		
		double [] performanceIndicator = {
				DataResource.selectedProject.getBudget(),
				DataResource.selectedProject.getEarnedValue(),
				DataResource.selectedProject.getActualCost(), 
				DataResource.selectedProject.getScheduleVariance(),
				DataResource.selectedProject.getSchedulePerformanceIndex(),
				DataResource.selectedProject.getCostVariance(),
				DataResource.selectedProject.getCostPerformanceIndex()
		};
		
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(13,2));
		
		for(int i = 0; i < performanceIndicatorTitles.length; i++) {
			panel.add(new JLabel(performanceIndicatorTitles[i]));
			panel.add(new JLabel("" + performanceIndicator[i]));
		}
		
		for(int i = 0; i < forcastTitles.length; i++) {
			panel.add(new JLabel(forcastTitles[i]));
			panel.add(new JLabel("" + forcast[i]));
		}

		panel.setPreferredSize(new Dimension(1000, 400));
		setContentPane(panel);
	}
}
