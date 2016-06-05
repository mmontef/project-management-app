package saver_loader;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import resources.Activities;
import resources.Projects;
import resources.Users;

public class DataResourceTest {
    
	@Before
    public void setUp() {
		tearDownTestDatabase();
    	setUpTestDatabase();
    	
		//tell resource to use test db
		selectTestDb();
		
		DataResource.projectList.clear();
    }
	
	@Test
	public void testGetProjectbyProjectId() {		
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "abc123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadFromDB();
		
		//a project is created in the set up with id 1
		Projects p = DataResource.getProjectbyProjectId(1);
		assertTrue("should have id 1", p.getId() == 1);
		assertTrue("should be named Project", p.getProjectName().equals("Project"));
		assertTrue("should be 01/04/2016", p.getDate().equals("01/04/2016"));
		assertTrue("should be just a test", p.getDescription().equals("Just a test"));
		assertTrue("should be 200.00", p.getBudget() == 200.00);
		assertTrue("should be 1", p.getManagerID() == 1);	
	}

	@Test
	public void testGetProjectbyProjectName() {
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "abc123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadFromDB();
		
		//a project is created in the set up with name Project
		Projects p = DataResource.getProjectbyProjectName("Project");
		assertTrue("should have id 1", p.getId() == 1);
		assertTrue("should be named Project", p.getProjectName().equals("Project"));
		assertTrue("should be 01/04/2016", p.getDate().equals("01/04/2016"));
		assertTrue("should be just a test", p.getDescription().equals("Just a test"));
		assertTrue("should be 200.00", p.getBudget() == 200.00);
		assertTrue("should be 1", p.getManagerID() == 1);	
	}

	@Test
	public void testRemoveProject() {
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "abc123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadFromDB();
		
		Projects p = DataResource.projectList.get(0);
		
		DataResource.removeProject(p);
		
		assertTrue("should have no projects", DataResource.projectList.size() == 0);
		
		Connection connection = null;		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:ultimate_sandwich_test.db");
	    	Statement stmt = connection.createStatement();
	    	System.out.println("connecting to db");
	    	
	    	ResultSet result = stmt.executeQuery("SELECT COUNT(*) as total FROM projects");
	    	
	    	try{
	        	connection.close();
	        }
	    	catch(Exception closingException) {
	        	System.out.println(closingException.getMessage());
	        }
	    	
	    	assertTrue(result.getInt("total") == 0);
		}
		catch(Exception exception) {
			System.out.println(exception.getMessage());
		}	
	}

	@Test
	public void testDeleteActivity() {
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "abc123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadFromDB();
		
		Projects p = DataResource.projectList.get(0);
		Activities a = p.getActivityList().get(0);
		
		DataResource.deleteActivity(a);
		
		Connection connection = null;		
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:ultimate_sandwich_test.db");
	    	Statement stmt = connection.createStatement();
	    	System.out.println("connecting to db");
	    	
	    	ResultSet result = stmt.executeQuery("SELECT COUNT(*) as total FROM activities");
	    	
	    	try{
	        	connection.close();
	        }
	    	catch(Exception closingException) {
	        	System.out.println(closingException.getMessage());
	        }
	    	
	    	assertTrue(result.getInt("total") == 0);
		}
		catch(Exception exception) {
			System.out.println(exception.getMessage());
		}
	}

	@Test
	public void testLoadFromDB() {		
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "abc123", 1, "MANAGER");
		
		// test the method
		System.out.println("calling loadFromDb method");
		DataResource.loadFromDB();
		System.out.println("called loadFromDb method");
		
		// the resource should have one project in it
		assertTrue("resource should have one project", DataResource.projectList.size() == 1);
		System.out.println("resource size tested");
		
		// make sure the project has the correct attributes
		Projects p = DataResource.projectList.get(0);
		assertTrue("should have id 1", p.getId() == 1);
		assertTrue("should be named Project", p.getProjectName().equals("Project"));
		assertTrue("should be 01/04/2016", p.getDate().equals("01/04/2016"));
		assertTrue("should be just a test", p.getDescription().equals("Just a test"));
		assertTrue("should be 200.00", p.getBudget() == 200.00);
		assertTrue("should be 1", p.getManagerID() == 1);
		
		System.out.println("project attributes tested");
		
		// make sure it has one activity associated with it
		assertTrue("should have one activity", p.getActivityList().size() == 1);
		
		// make sure activity attributes are correct
		Activities a = p.getActivityList().get(0);
		assertTrue("should have id 1", a.getId() == 1);
		assertTrue("should have label Project Activity 1", a.getLabel().equals("Project Activity 1"));
		assertTrue("should have description", a.getDescription().equals("Just testing an activity"));
		assertTrue("should have a duration", a.getDuration() == 100.00);
	}

	private void selectTestDb() {
		DataResource.setDatabase("jdbc:sqlite:ultimate_sandwich_test.db");
	}
	
	private void setUpTestDatabase() {
		// set up the db with test data
		Connection connection = null;
		String sql = "";
		try{
	    	connection = DriverManager.getConnection("jdbc:sqlite:ultimate_sandwich_test.db");
	    	Statement stmt = connection.createStatement();
	    	
	    	System.out.println("connecting to db");
	    	
	    	//create a user
	    	sql = ("INSERT OR REPLACE INTO users(id, first_name, last_name, username, password, user_type) " +
	    	"VALUES (1, 'Turkey', 'Sandwhich', 'tsand', '123abc', 'MANAGER')");
	    	stmt.executeUpdate(sql);
	    	
	    	System.out.println("created user");
	    	
	    	//create project
	    	sql = ("INSERT OR REPLACE INTO projects(id, name, date, description, budget, manager_id) " +
	    	"VALUES (1, 'Project', '01/04/2016', 'Just a test', 200.00, '1')");
	    	stmt.executeUpdate(sql);
	    	
	    	System.out.println("created project");
	    	
	    	//relate the user to the project
			sql = ("INSERT OR REPLACE INTO user_project_relationships(project_id, user_id) " +
			"VALUES (1, 1)");
			stmt.executeUpdate(sql);
			
			System.out.println("related user to project");
	    	
	    	//create activity
			sql = ("INSERT OR REPLACE INTO activities(id, label, description, duration) " +
	    	"VALUES (1, 'Project Activity 1', 'Just testing an activity', 100.00)");
			stmt.executeUpdate(sql);
			
			System.out.println("created activity");
			
			//relate the activity to the project
			sql = ("INSERT OR REPLACE INTO activity_project_relationships(project_id, activity_id) " +
			"VALUES (1, 1)");
			stmt.executeUpdate(sql);
			
			System.out.println("related activity to project");
			
			//close connection at end
			try{
	        	connection.close();
	        }catch(Exception closingException)
	        {
	        	System.out.println(closingException.getMessage());
	        }
			
		}catch(Exception exception) {
	    	System.out.println(exception.getMessage());
	    }
	}
	
	private void tearDownTestDatabase() {

		Connection connection = null;
		String sql = "";
		try{
	    	connection = DriverManager.getConnection("jdbc:sqlite:ultimate_sandwich_test.db");
	    	Statement stmt = connection.createStatement();
	    	
	    	System.out.println("connecting to db");
	    	
	    	//delete all associations
			sql = ("DELETE FROM user_project_relationships");
			stmt.executeUpdate(sql);
			
			sql = ("DELETE FROM activity_project_relationships");
			stmt.executeUpdate(sql);
			
	    	//delete user
			sql = ("DELETE FROM users");
	    	stmt.executeUpdate(sql);
	    	
	    	//delete project
	    	sql = ("DELETE FROM projects");
	    	stmt.executeUpdate(sql);
	    	
	    	//delete activity
	    	sql = ("DELETE FROM activities");
	    	stmt.executeUpdate(sql);
			
			//close connection at end
			try{
	        	connection.close();
	        }catch(Exception closingException)
	        {
	        	System.out.println(closingException.getMessage());
	        }
			
		}catch(Exception exception) {
	    	System.out.println(exception.getMessage());
	    }
	}
}
