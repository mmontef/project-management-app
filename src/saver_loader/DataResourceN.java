package saver_loader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;

import resources.Activities;
import resources.Projects;
import resources.Users;

/**
 * The DataResource class is used to store and manipulate the application data
 * in the current instance of execution. The class contains static variables
 * that are used to define the current instance of the data.
 * 
 * projectList contains a list of current projects loaded from the database.
 * This list can be modified by the user, and all changes are saved to the
 * database.
 * 
 * currentUser defines the user that is currently logged in. Only projects
 * associated with this user are displayed.
 * 
 * selectedProject defines the currently active Project selected by the user.
 * Modifications are made to this Project.
 * 
 * selectedActivity defines the currently active Activity selected by the user.
 * Modifications are made to this Activity
 * 
 * The class also contains methods to make saves and loads to and from the
 * database.
 * 
 * @author daveT
 *
 */
public class DataResourceN {

	public static ArrayList<Users> memberEmployees = new ArrayList<Users>();
	
	public static ArrayList<Projects> projectList = new ArrayList<Projects>();
	
	public static Users currentUser;

	// current selected project by user
	public static Projects selectedProject;

	// current selected activity by user
	public static Activities selectedActivity;

	public static String dataBase = "jdbc:sqlite:ultimate_sandwich.db";

	public static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
	
	
	/*************************load functions****************************/
	
	//loads the ids and names of projects associated with user
	public void loadProjectList(Users user){
		
	}
	
	//as soon as you click on project name in gui, load that project
	public void loadSelectedProject(int projectID)
	{
		
	}
	
	//load current project's activities 
	public void loadActivities(Projects currentProject)
	{
		
	}
	
	//load all member employees
	public void loadMembers(){
		
	}
	
	
	/*******************save functions**************************/
	
	//save current selected project
	public void saveProject(Projects selectedProject)
	{
		//compare current project id in database and save
	}
	
	public void saveActivity(Activities selectedActivity)
	{
		//save the tuple in Activities table in the database where that id is equal to the selected activity id
		Connection conn = createConnectionToDB(dataBase);
		
		int activityID, dependentActivityID;
		String actLabel, actDescription, start, end;
		
		activityID = selectedActivity.getId();
		actLabel = selectedActivity.getLabel();
		actDescription = selectedActivity.getDescription();
		start = dateFormatter.format(selectedActivity.getStartDate());
		end =  dateFormatter.format(selectedActivity.getEndDate());
		
		
		String sql = ("INSERT OR REPLACE INTO activities(id, label, description, startdate, endate) VALUES "
				+ "(?, ?, ?, ?, ?)");
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, activityID);
			ps.setString(2, actLabel);
			ps.setString(3, actDescription);
			ps.setString(4, start);
			ps.setString(5, end);
			ps.executeUpdate();
			
			sql = ("INSERT OR REPLACE INTO activity_project_relationships(project_id, activity_id) VALUES "
					+ "(?, ?)");
			ps = conn.prepareStatement(sql);
			ps.setInt(1, selectedProject.getId());
			ps.setInt(2, activityID);
			ps.executeUpdate();
			
			int memberID;

			for (Users member : selectedActivity.getMemberList()) {
				memberID = member.getID();

				sql = ("INSERT OR REPLACE INTO activity_user_project_relationships(activity_id, user_id, project_id) VALUES "
						+ "(?, ?, ?)");
				ps = conn.prepareStatement(sql);
				ps.setInt(1, activityID);
				ps.setInt(2, memberID);
				ps.setInt(3, selectedProject.getId());
				ps.executeUpdate();
			}

			Set<DefaultEdge> edges = selectedProject.getArrowSet();
			// for currently selected activity, add all the edges to
			// activity_edge_relationship
			for (DefaultEdge e : edges) {
				if (activityID == selectedProject.getActivityBefore(e).getId()) {
					dependentActivityID = selectedProject.getActivityAfter(e).getId();
					// if the activityID is a before edge, put the
					// before and after edge into table under
					// from_activity_id and to_activity_id
					sql = ("INSERT OR REPLACE INTO activity_edge_relationship(from_activity_id, to_activity_id) VALUES "
							+ "(?, ?)");
					ps = conn.prepareStatement(sql);
					ps.setInt(1, activityID);
					ps.setInt(2, dependentActivityID);
					ps.executeUpdate();
				}
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		closeConnection(conn);
		
	}
	
	/*****************************helper functions to connect to and close database connections***************/
	public Connection createConnectionToDB(String database)
	{
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(dataBase);
		}catch (Exception exception) {
			System.out.println(exception.getMessage());
		}
		return connection;
	}
	
	public void closeConnection(Connection connection)
	{
		// close connection at end
		try {
			connection.close();
		} catch (Exception closingException) {
			System.out.println(closingException.getMessage());
		}
	}
	
	
	
}
