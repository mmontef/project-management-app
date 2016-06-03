package listview_components;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;

import org.jgraph.graph.DefaultEdge;

import resources.Activities;
import resources.Projects;

@SuppressWarnings("serial")
public class ActivityListPane extends JPanel {

	public static JTable table = new JTable();
	public static DefaultTableModel mod = new DefaultTableModel();
	
	float fontScalar = Toolkit.getDefaultToolkit().getScreenSize().height/1800f;
	JLabel title;
		
	public ActivityListPane(){
		
		super();	
		
		//LayoutManager
		this.setLayout(new BorderLayout());
		
		//Set Headers to the Table Model
		String[] columnHeaders = {"Name" , "Label", "Duration", "Depedencies"};
		mod.setColumnIdentifiers(columnHeaders);
		
		
		
		//Set Basic Table Options
		table.setModel(mod);
		table.setFillsViewportHeight(true);
		table.setFont(table.getFont().deriveFont(fontScalar*30f));
		table.setRowHeight(35);
		table.getTableHeader().setFont(table.getFont().deriveFont(40f));

	
		//add the table to a ScrollPanel
		JScrollPane scrollpane = new JScrollPane(table);
		
		//Set the Title
		title = new JLabel("               Activity ViewPort");
		title.setFont(title.getFont().deriveFont(fontScalar*50f));
		
		//Add title and Table(scrollpane) to Panel
		this.add(title, BorderLayout.PAGE_START);
		this.add(scrollpane);
		
	}
	
	//Method deletes everything from table and makes new rows with the data from selected Project
	public static void updateTable(Projects selectedProject){
		
		int rows = selectedProject.getActivityList().size();
		
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.fireTableRowsDeleted(0, model.getRowCount());
		
		
		Activities[] activityList = new Activities [rows];
		selectedProject.getActivityList().toArray(activityList);
		
		for(int i = 0; i < rows; i++){
			
			//-----------The first task is to create a list of dependencies-------
			Set<DefaultEdge> edgeList=  selectedProject.getIncomingArrowsOfActivity(activityList[i]);
			String dependencies = "|";
			
			for(DefaultEdge e: edgeList)
				dependencies += selectedProject.getActivityBefore(e).getId()+ "| ";
				
							
			
			Object data[] = { activityList[i].getDescription(), activityList[i].getId(),
					activityList[i].getDuration(), dependencies}; 
			
			model.addRow(data);	
			
		}
				
	}
	
	
}
