package saver_loader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Set;

import org.jgraph.graph.DefaultEdge;

import resources.Activities;
import resources.Projects;
import resources.Users;


public class DataResource {

	public static ArrayList<Projects> projectList = new ArrayList<Projects>();
	
	public static Users currentUser; //this is the currently logged in user for which the projetList will be populated
	
	public static Projects selectedProject;
	
	
	public static Projects getProjectbyProjectId(int projectId){
		
		for(Projects project: projectList){
			
			if(project.getId() == projectId)
				return project;
		}
		return null;
	}
	
public static Projects getProjectbyProjectName(String name){
        
        for(Projects project: projectList){
            
            if(project.getProjectName() == name)
                return project;
        }
        return null;
    }
	
	public static void loadFromDB()
	{
		//query user_project_relationship
		//get all projID where userID = currentUserID
		
		Connection connection = null;
        PreparedStatement ps;
        
        try{
        	connection = DriverManager.getConnection("jdbc:sqlite:ultimate_sandwich.db");
        	
        	//set projectCount to max project id from database
        	
        	PreparedStatement ps3 = connection.prepareStatement("SELECT max(id) FROM projects;");
        	ResultSet result3 = ps3.executeQuery();
        	
        	if(result3.next())
        	{
        		Projects.setProjectCount(result3.getInt(1)) ;
        	}
        	
        	//set activityCount to max activity id from database
        	ps3 = connection.prepareStatement("SELECT max(id) FROM activities;");
        	result3 = ps3.executeQuery();
        	
        	if(result3.next())
        	{
        		Activities.setActivityCount(result3.getInt(1)) ;
        	
        	}

        	ps = connection.prepareStatement("SELECT * FROM projects "
        			+ "WHERE manager_id = ?;");
        	ps.setInt(1, currentUser.getID());
        	ResultSet result = ps.executeQuery();

        	while(result.next())
        	{
        		//we have project ids
        		//projIds.add(result.getInt(1));
        		int projectID = result.getInt(1);
        		int managerID = result.getInt(6);
        		String projectName = result.getString(2);
        		String description = result.getString(4);
        		double budget = result.getDouble(5);
        		String date = result.getString(3);

        		//getting all users associated with project
        		PreparedStatement ps1 = connection.prepareStatement("SELECT user_id FROM user_project_relationships WHERE "
        				+ "project_id = ?");
        		ps1.setInt(1, projectID);
        		ResultSet result1 = ps1.executeQuery();
        		
        		ArrayList<Users> userList = new ArrayList<Users>();
        		
        		while(result1.next())
        		{
        			//memeberIds.add(result1.getInt(1));//got all userids associated with project
        			int userID = result1.getInt(1);
        			
        			PreparedStatement ps2 = connection.prepareStatement("SELECT * FROM users WHERE "
            				+ "id = ?");
            		ps2.setInt(1, userID);
            		ResultSet result2 = ps2.executeQuery();
            		
            		
            		
            		while(result2.next())
            		{
            			String username = result2.getString(4);
            			String first_name = result2.getString(2);
            			String last_name = result2.getString(3);
            			String password = result2.getString(5);
            			int id = result2.getInt(1);
            			String userType = result2.getString(6);
            			
            			userList.add(new Users(username, first_name, last_name, password, id, userType));
            		}
        			
        			
        		}
        		
        		ArrayList<Activities> activityList = new ArrayList<Activities>();
        		
        		//query activity relation table to get activities associated with project
        		PreparedStatement ps4 = connection.prepareStatement("SELECT activity_id FROM activity_project_relationships WHERE "
        				+ "project_id = ?");
        		ps4.setInt(1, projectID);
        		ResultSet result4 = ps4.executeQuery();

        		while(result4.next())
        		{
        			//have all activities associated with project
        			
        			PreparedStatement ps5 = connection.prepareStatement("SELECT * FROM activities WHERE "
            				+ "id = ?");
            		ps5.setInt(1, result4.getInt(1));
            		ResultSet result5 = ps5.executeQuery();
            		
            		while(result5.next())
            		{
            			//now create activities and add to activiylist
            			int id = result5.getInt(1);
            			String name = result5.getString(2);
            			String desc = result5.getString(3);
            			int duration = result5.getInt(4);
            			
            			activityList.add(new Activities(desc, duration, name, id));
            		}
        			
        		}
        		
        		
        		
        		Projects project = new Projects(projectName,userList,date,projectID,managerID,description,budget);
        		
        		for(Activities acts : activityList)
        		{
        			project.addActivity(acts);//adding each activity to the project
        			
        		}
        		
        		//for each activity query activity table relation to get dependent activities
        		for(Activities activity: activityList)
        		{
        			//make db call
        			PreparedStatement ps5 = connection.prepareStatement("SELECT to_activity_id FROM activity_edge_relationship WHERE "
            				+ "from_activity_id = ?");
            		ps5.setInt(1, activity.getId());
            		ResultSet result5 = ps5.executeQuery();
            		while(result5.next())
            		{
            			for(Activities dependent_activity: activityList)
            			{
            				if(dependent_activity.getId()==result5.getInt(1))
            				{
            					project.addArrow(activity, dependent_activity);
            				}
            			}
            		}
        			
        	
        		}

        		
        		//creates projects with activities
        		projectList.add(project);
        		
        	}
		
        	//print to console to test if projects and activites and dependencies added correctly
          for(Projects P: projectList)
          {
        	  System.out.println(P.getProjectName());
        	  Set<Activities> vertices = P.getActivitySet();
        	  for(Activities a: vertices)
        	  {
        		  System.out.println(a.getId());
        	  }
        	  Set<DefaultEdge> edges = P.getArrowSet();
        	  for(DefaultEdge e: edges)
        	  {
        		  System.out.println(P.getActivityBefore(e).getId());
        		  System.out.println(P.getActivityAfter(e).getId());
        	  }
        	  
      		
          }
        }
        catch(Exception exception) {
        	System.out.println(exception.getMessage());
        }
        
        
        //close connection at end
        try{
        	connection.close();
        }catch(Exception closingException)
        {
        	System.out.println(closingException.getMessage());
        }
	
	}
	
	/** 
	 * Loops through projectList and inserts projects, users, activities, dependencies to database 
	 * */
	public static void saveToDB()
	{
		Connection connection = null;
		try{
        	connection = DriverManager.getConnection("jdbc:sqlite:ultimate_sandwich.db");
        	
        	
        	PreparedStatement ps1;
    		String projectName, description, date;
    		int projectID, managerID;
    		double budget;
    		
    		//load projects in database
    		for(Projects projects: projectList)
    		{
    			projectID = projects.getId();
    			description = projects.getDescription();
    			date = projects.getDate();
    			projectName = projects.getProjectName();
    			managerID = projects.getManagerID();
    			budget = projects.getBudget();
    		
    			ps1 = connection.prepareStatement("INSERT INTO Projects(id, name, date, description, budget, manager_id) "
    					+ "VALUES (" + projectID + ", " + projectName + ", " + date + " " + description + ", " + budget + "," + managerID+");");
    			ps1.executeQuery();		
    		}
    		

		}catch(Exception exception) {
        	System.out.println(exception.getMessage());
        }
		
		
    	
    	
        
		//close connection at end
		try{
        	connection.close();
        }catch(Exception closingException)
        {
        	System.out.println(closingException.getMessage());
        }
	}
	
}