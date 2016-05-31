package resources;

import java.util.Set;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import java.util.ArrayList;

public class Projects {

	private int id;
	private String projectName;
	private Users[] projectMembers;
	private String date;
	private DefaultDirectedGraph<Activities,DefaultEdge> activityGraph;
	private ArrayList<Activities> activityList;

	public Projects(int id, String projectName, Users[] projectMembers, String date, DefaultDirectedGraph<Activities,DefaultEdge> activityGraph, ArrayList<Activities> activityList) {
		this.id = id;
		this.projectName = projectName;
		this.projectMembers = projectMembers;
		this.date = date;
		this.activityGraph = activityGraph;
		this.activityList = activityList;
	}
	
	public Projects(int id, String projectName, Users[] projectMembers, String date) {
		this.id = id;
		this.projectName = projectName;
		this.projectMembers = projectMembers;
		this.date = date;
		this.activityGraph = new DefaultDirectedGraph<Activities,DefaultEdge>(DefaultEdge.class);
		this.activityList = new ArrayList<Activities>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Users[] getProjectMembers() {
		return projectMembers;
	}

	public void setProjectMembers(Users[] projectMembers) {
		this.projectMembers = projectMembers;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public DefaultDirectedGraph<Activities, DefaultEdge> getActivityGraph() {
		return activityGraph;
	}

	public void setActivityGraph(DefaultDirectedGraph<Activities, DefaultEdge> activityGraph) {
		this.activityGraph = activityGraph;
	}
	
	/**
	 * @return the activityList
	 */
	public ArrayList<Activities> getActivityList() {
		return activityList;
	}

	/**
	 * @param activityList the activityList to set
	 */
	public void setActivityList(ArrayList<Activities> activityList) {
		this.activityList = activityList;
	}

	public void addActivity(Activities A) {
		this.activityGraph.addVertex(A);
		this.activityList.add(A);
	}
	
	public void addArrow(Activities A, Activities B) {
		this.activityGraph.addEdge(A, B);
	}
	
	public Set<Activities> getActivitySet() {
		return this.activityGraph.vertexSet();
	}
	
	public Set<DefaultEdge> getArrowSet() {
		return this.activityGraph.edgeSet();
	}
	
	public Set<DefaultEdge> getIncomingArrowsOfActivity(Activities A) {
		return this.activityGraph.incomingEdgesOf(A);
	}
	
	public Set<DefaultEdge> getOutgoingArrowsOfActivity(Activities A) {
		return this.activityGraph.outgoingEdgesOf(A);
	}
	
	public Activities getActivityBefore(DefaultEdge e) {
		return this.activityGraph.getEdgeSource(e);
	}
	
	public Activities getActivityAfter(DefaultEdge e) {
		return this.activityGraph.getEdgeTarget(e);
	}
	
	public void setES(Activities A, double n) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				a.setEarliestStart(n);
		}
	}
	
	public double getES(Activities A) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				return a.getEarliestStart();
		}
		return -1;
	}
	
	public void setEF(Activities A, double n) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				a.setEarliestFinish(n);
		}
	}
	
	public double getEF(Activities A) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				return a.getEarliestFinish();
		}
		return -1;
	}
	
	public void setLS(Activities A, double n) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				a.setLatestStart(n);
		}
	}
	
	public double getLS(Activities A) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				return a.getLatestStart();
		}
		return -1;
	}
	
	public void setLF(Activities A, double n) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				a.setLatestFinish(n);
		}
	}
	
	public double getLF(Activities A) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				return a.getLatestFinish();
		}
		return -1;
	}
	
	public void setFloat(Activities A, double n) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				a.setActivityFloat(n);
		}
	}
	
	public double getFloat(Activities A) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				return a.getActivityFloat();
		}
		return -1;
	}
	
	public void setMaxDuration(Activities A, double n) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				a.setMaxDuration(n);
		}
	}
	
	public double getMaxDuration(Activities A) {
		for(Activities a : this.activityList)
		{
			if (a.getId() == A.getId())
				return a.getMaxDuration();
		}
		return -1;
	}
	
	public void calculateTimes() {
		Set<Activities> vertexList = getActivitySet();
		Set<DefaultEdge> edgeList = getArrowSet();		
		
		// forward pass
		for (Activities i : vertexList)
		{
			// check if activity is a "first level" activity, no incoming edges
			if (this.activityGraph.inDegreeOf(i) == 0)
			{
				setES(i, 0);
				setEF(i, i.getDuration());	
			}
			else
			{
				Set<DefaultEdge> inEdges = getIncomingArrowsOfActivity(i);
				double highestEF = 0;
				for (DefaultEdge e : inEdges)
				{
					if (getActivityBefore(e).getEarliestFinish() >= highestEF)
						highestEF = getActivityBefore(e).getEarliestFinish();
				}
				setES(i, highestEF);
				setEF(i, highestEF + i.getDuration());
			}
			
		}
		
		// backward pass
		
		// float
		
		// critial path
		
		// max duration
		
	}
	
	
	
}
