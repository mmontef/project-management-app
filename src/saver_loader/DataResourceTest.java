package saver_loader;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import resources.Activities;
import resources.Projects;
import resources.Users;

public class DataResourceTest {
    
	private static String testDB = "jdbc:sqlite:ultimate_sandwich_test.db";
	
	@Before
    public void setUp() {
		
		DataResource.setDatabase(testDB);
		
		tearDownTestDatabase();
    	setUpTestDatabase();

		DataResource.projectList.clear();

    }
	
	@Test
	public void testGetProjectbyProjectId() {		
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		//a project is created in the set up with id 1
		Projects p = DataResource.getProjectbyProjectId(1);
		assertTrue("should have id 1", p.getId() == 1);
		assertTrue("should be named TestProject", p.getProjectName().equals("TestProject"));
		assertTrue("should be 07-22-2016", p.getDate().equals("07-22-2016"));
		assertTrue("should be just a test", p.getDescription().equals("Just a test"));
		assertTrue("should be 100.00", p.getBudget() == 100.00);
		assertTrue("should be 1", p.getManagerID() == 1);	
	}

	@Test
	public void testGetProjectbyProjectName() {
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		//a project is created in the set up with name TestProject
		Projects p = DataResource.getProjectbyProjectName("TestProject");
		assertTrue("should have id 1", p.getId() == 1);
		assertTrue("should be named TestProject", p.getProjectName().equals("TestProject"));
		assertTrue("should be 07-22-2016", p.getDate().equals("07-22-2016"));
		assertTrue("should be just a test", p.getDescription().equals("Just a test"));
		assertTrue("should be 100.00", p.getBudget() == 100.00);
		assertTrue("should be 1", p.getManagerID() == 1);		
	}

	@Test
	public void testRemoveProject() {
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		Projects p = DataResource.projectList.get(0);
		
		DataResource.removeProject(p);
		
		assertTrue("should have no projects", DataResource.projectList.size() == 0);
		
		Connection connection = DataResource.createConnectionToDB(testDB);		
		try {
			
			String sql = "SELECT COUNT(*) as total FROM projects";
	    	Statement stmt = connection.createStatement();
	    	System.out.println("connecting to db");
	    	
	    	ResultSet result = stmt.executeQuery(sql);

	    	assertTrue(result.getInt("total") == 0);
		}
		catch(Exception exception) {
			System.out.println(exception.getMessage());
		}	
		
		DataResource.closeConnection(connection);
	}

	@Test
	public void testDeleteActivity() {
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		Projects p = DataResource.projectList.get(0);
		Activities a1 = p.getActivityList().get(0);
		Activities a2 = p.getActivityList().get(1);
		Activities a3 = p.getActivityList().get(2);
		
		DataResource.deleteActivity(a1);
		DataResource.deleteActivity(a2);
		DataResource.deleteActivity(a3);
		
		Connection connection = DataResource.createConnectionToDB(testDB);		
		try {
	
			String sql = "SELECT COUNT(*) as total FROM activities";
	    	Statement stmt = connection.createStatement();
	    	System.out.println("connecting to db");
	    	
	    	ResultSet result = stmt.executeQuery(sql);
	
	    	assertTrue(result.getInt("total") == 0);
		}
		catch(Exception exception) {
			System.out.println(exception.getMessage());
		}
		
		DataResource.closeConnection(connection);
	}

	@Test
	public void testLoadFromDB() {		
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		
		// test the method
		System.out.println("calling loadFromDb method");
		DataResource.loadManagerDataFromDB();
		System.out.println("called loadFromDb method");
		
		// the resource should have one project in it
		assertTrue("resource should have one project", DataResource.projectList.size() == 1);
		System.out.println("resource size tested");
		
		// make sure the project has the correct attributes
		Projects p = DataResource.projectList.get(0);
		assertTrue("should have id 1", p.getId() == 1);
		assertTrue("should be named TestProject", p.getProjectName().equals("TestProject"));
		assertTrue("should be 07-22-2016", p.getDate().equals("07-22-2016"));
		assertTrue("should be just a test", p.getDescription().equals("Just a test"));
		assertTrue("should be 100.00", p.getBudget() == 100.00);
		assertTrue("should be 1", p.getManagerID() == 1);
		
		System.out.println("project attributes tested");
		
		// make sure it has one activity associated with it
		assertTrue("should have three activities", p.getActivityList().size() == 3);
		
		// make sure activity attributes are correct
		Activities a1 = p.getActivityList().get(0);
		assertTrue("should have id 1", a1.getId() == 1);
		assertTrue("should have label TestActivity 1", a1.getLabel().equals("TestActivity 1"));
		assertTrue("should have description", a1.getDescription().equals("Just testing an activity"));
		//assertTrue("should have a start date of 07-22-2016, actual date: " + a1.getStartDate() , a1.getStartDate().equals("07-22-2016"));
		//assertTrue("should have an end date of 07-23-2016", a1.getEndDate().equals("07-23-2016"));
		
		Activities a2 = p.getActivityList().get(1);
		assertTrue("should have id 1", a2.getId() == 2);
		assertTrue("should have label TestActivity 2", a2.getLabel().equals("TestActivity 2"));
		assertTrue("should have description", a2.getDescription().equals("Just testing an activity"));
		//assertTrue("should have a start date of 07-22-2016", a2.getStartDate().equals("07-22-2016"));
		//assertTrue("should have an end date of 07-23-2016", a2.getEndDate().equals("07-23-2016"));
		
		Activities a3 = p.getActivityList().get(2);
		assertTrue("should have id 1", a3.getId() == 3);
		assertTrue("should have label TestActivity 3", a3.getLabel().equals("TestActivity 3"));
		assertTrue("should have description", a3.getDescription().equals("Just testing an activity"));
		//assertTrue("should have a start date of 07-22-2016", a3.getStartDate().equals("07-22-2016"));
		//assertTrue("should have an end date of 07-23-2016", a3.getEndDate().equals("07-23-2016"));
		
		System.out.println("activity attributes tested");
		
	}
	
	@Test
	public void testEditProject(){
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		//get the project to edit
		Projects p = DataResource.getProjectbyProjectId(1);
		
		//edit the project
		p.setBudget(250.00);
		p.setDate("07-25-2016");
		p.setDescription("Changed the test description");
		p.setProjectName("TestProjectEdited");
		
		//save the changes to the test database
		DataResource.saveProject(p);
		
		//load the changes to test if changed correctly
		DataResource.loadManagerDataFromDB();
		
		//get the same project to test if changes were made
		Projects p1 = DataResource.getProjectbyProjectId(1);
		
		assertTrue("budget should equal 250.00", p1.getBudget()==250.00);
		assertTrue("date should equal 07-25-2016", p1.getDate().equals("07-25-2016"));
		assertTrue("description should equal: Changed the test description", p1.getDescription().equals("Changed the test description"));
		assertTrue("project name should equal: TestProjectEdited", p1.getProjectName().equals("TestProjectEdited"));
		
		System.out.println("project editing tested");
	}
	
	@Test
	public void testEditActivities(){
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		//get the project to edit
		Projects p = DataResource.getProjectbyProjectId(1);
		DataResource.selectedProject = p;
		
		//get the activities to edit
		Activities a1 = p.getActivityList().get(0);
		Activities a2 = p.getActivityList().get(1);
		Activities a3 = p.getActivityList().get(2);
		
		//edit the activities
		a1.setDescription("New Description");
		a2.setDescription("New Description");
		a3.setDescription("New Description");
		a1.setLabel("New Label");
		a2.setLabel("New Label");
		a3.setLabel("New Label");
		//a1.setStartDate("07-25-2016");
		//a2.setStartDate("07-25-2016");
		//a3.setStartDate("07-25-2016");
		//a1.setStartDate("07-25-2016");
		//a2.setEndDate("07-25-2016");
		//a3.setEndDate("07-25-2016");
		
		//save changes to the test database
		DataResource.saveActivity(a1);
		DataResource.saveActivity(a2);
		DataResource.saveActivity(a3);
		
		//load from database to check if changes there
		DataResource.loadManagerDataFromDB();
		
		//get the project and activities to test
		p = DataResource.getProjectbyProjectId(1);
		
		a1 = p.getActivityList().get(0);
		a2 = p.getActivityList().get(1);
		a3 = p.getActivityList().get(2);
		
		assertTrue("should equal New Description",a1.getDescription().equals("New Description"));
		assertTrue("should equal New Label",a1.getLabel().equals("New Label"));
		//assertTrue("should equal 07-25-2016",a1.getStartDate().equals("07-25-2016"));
		//assertTrue("should equal 07-25-2016",a1.getEndDate().equals("07-25-2016"));
		
		assertTrue("should equal New Description",a2.getDescription().equals("New Description"));
		assertTrue("should equal New Label",a2.getLabel().equals("New Label"));
		//assertTrue("should equal 07-25-2016",a2.getStartDate().equals("07-25-2016"));
		//assertTrue("should equal 07-25-2016",a2.getEndDate().equals("07-25-2016"));
		
		assertTrue("should equal New Description",a3.getDescription().equals("New Description"));
		assertTrue("should equal New Label",a3.getLabel().equals("New Label"));
		//assertTrue("should equal 07-25-2016",a3.getStartDate().equals("07-25-2016"));
		//assertTrue("should equal 07-25-2016",a3.getEndDate().equals("07-25-2016"));
		
		System.out.println("tested edit activities");
		
	}
	
	 
	@Test
	public void testAddActivity(){
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		//get the project to edit
		Projects p = DataResource.getProjectbyProjectId(1);
		DataResource.selectedProject = p;
		
		Date start = new Date();
		Date end = new Date();
		
		//create the activity to add
		Activities a = new Activities("New Activity Created", start, end, "New Label Created", 4 );
		//add the activity
		p.addActivity(a);
		
		ArrayList<String> dependencies = new ArrayList<String>();
		ArrayList<String> members = new ArrayList<String>();
		
		// Set the dependencies in the JGraphT
					for (String element : dependencies) {

						ArrayList<Activities> activities = DataResource.selectedProject.getActivityList();

						for (Activities activity : activities) {

							if (activity.getLabel().equals(element))
								DataResource.selectedProject.addArrow(activity, a);
						}
					}

					ArrayList<Users> users = DataResource.projectMembers;
					ArrayList<Users> tmp = new ArrayList<Users>();

					for (String element : members) {
						for (Users user : users) {
							if (user.getName().equals(element))
								tmp.add(user);
						}
					}
					a.setMemberList(tmp);
		
		//save the new activity
		DataResource.saveActivity(a);
		
		//load from database to check if changes there
		DataResource.loadManagerDataFromDB();
		p = DataResource.getProjectbyProjectId(1);
		DataResource.selectedProject = p;
		
		Activities newActivity = p.getActivityByLabel("New Label Created");
		
		assertTrue("",newActivity.getLabel().equals("New Label Created"));
		assertTrue("",newActivity.getDescription().equals("New Activity Created"));
		//TODO assert tests for dates
		assertTrue("",newActivity.getId()==4);
		
		System.out.println("tested adding an activity");
		
		
	}
	/*
	
	@Test
	public void testEditActivityDependencies(){
		
	}
	
	@Test
	public void testDeleteActivityDependencies(){
		
	}
	*/
	
	public void testAddActivityDependencies(){
		// set current user
		DataResource.currentUser = new Users( "tsand", "Turkey", "Sandwhich", "123", 1, "MANAGER");
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		
		//get the project to edit
		Projects p = DataResource.getProjectbyProjectId(1);
		DataResource.selectedProject = p;
		
		//get the activities to edit
		Activities a1 = p.getActivityList().get(0);
		Activities a2 = p.getActivityList().get(1);
		Activities a3 = p.getActivityList().get(2);
		
		//add dependency between a2 and a3
		p.addArrow(a2, a3);
		
		//save to test database
		DataResource.saveProject(p);
		DataResource.saveActivity(a2);
		DataResource.saveActivity(a3);
		
		//loads all projects and activities
		DataResource.loadManagerDataFromDB();
		p = DataResource.getProjectbyProjectId(1);
		//get the activities to edit
		a1 = p.getActivityList().get(0);
		a2 = p.getActivityList().get(1);
		a3 = p.getActivityList().get(2);
		
		//test to see if added dependency there
		//TODO write asserts testing if dependency is there
	}
	
	
	private void setUpTestDatabase() {
		// set up the database with test data
		Connection connection = DataResource.createConnectionToDB(testDB);
		String sql = "";
		try{
	    	
			Statement stmt = connection.createStatement();
	    	
	    	System.out.println("connecting to db");
	    	
	    	//create a test user that is a manager
	    	sql = ("INSERT OR REPLACE INTO users(id, first_name, last_name, username, password, user_type) " +
	    	"VALUES (1, 'Turkey', 'Sandwhich', 'tsand', '123', 'MANAGER')");
	    	stmt.executeUpdate(sql);
	    	
	    	//create a test user that is a member
	    	sql = ("INSERT OR REPLACE INTO users(id, first_name, last_name, username, password, user_type) " +
	    	"VALUES (2, 'Chicken', 'Sandwhich', 'csand', '123', 'MEMBER')");
	    	stmt.executeUpdate(sql);
	    	
	    	//create a test user that is a member
	    	sql = ("INSERT OR REPLACE INTO users(id, first_name, last_name, username, password, user_type) " +
	    	"VALUES (3, 'BLT', 'Sandwhich', 'bsand', '123', 'MEMBER')");
	    	stmt.executeUpdate(sql);
	    	
	    	System.out.println("created test users");
	    	
	    	//create project
	    	sql = ("INSERT OR REPLACE INTO projects(id, name, date, description, budget, manager_id) " +
	    	"VALUES (1, 'TestProject', '07-22-2016', 'Just a test', 100.00, '1')");
	    	stmt.executeUpdate(sql);
	    	
	    	System.out.println("created test project");
	    	
	    	//relate the users to the project
			sql = ("INSERT OR REPLACE INTO user_project_relationships(project_id, user_id) " +
			"VALUES (1, 1), (1,2), (1,3)");
			stmt.executeUpdate(sql);
			
			System.out.println("related users to project");
	    	
	    	//create activities
			sql = ("INSERT OR REPLACE INTO activities(id, label, description, startdate, endate) " +
	    	"VALUES (1, 'TestActivity 1', 'Just testing an activity', '07-22-2016', '07-23-2016'), "
	    	+ "(2, 'TestActivity 2', 'Just testing an activity', '07-22-2016', '07-23-2016'),"
	    	+ "(3, 'TestActivity 3', 'Just testing an activity', '07-22-2016', '07-23-2016')");
			stmt.executeUpdate(sql);
			
			sql = ("INSERT OR REPLACE INTO activities(id, label, description, startdate, endate) " +
	    	"VALUES (2, 'TestActivity 2', 'Just testing an activity', '07-22-2016', '07-23-2016')");
			stmt.executeUpdate(sql);
					
			sql = ("INSERT OR REPLACE INTO activities(id, label, description, startdate, endate) " +
	    	"VALUES (3, 'TestActivity 3', 'Just testing an activity', '07-22-2016', '07-23-2016')");
			stmt.executeUpdate(sql);
			
			System.out.println("created activities");
			
			//relate the activities to the project
			sql = ("INSERT OR REPLACE INTO activity_project_relationships(project_id, activity_id) " +
			"VALUES (1, 1), (1,2), (1,3)");
			stmt.executeUpdate(sql);
			System.out.println("related activities to project");
			
			//create edges between activities for later testing
			sql = ("INSERT OR REPLACE INTO activity_edge_relationship(from_activity_id, to_activity_id) " +
			"VALUES (1, 2), (1,3)");
			stmt.executeUpdate(sql);
			System.out.println("created edges between activities");
			
			//relate the members to the activities
			sql = ("INSERT OR REPLACE INTO activity_user_project_relationships(activity_id, user_id, project_id) " +
					"VALUES (1,2,1), (2,2,1), (3,1,1)");
			stmt.executeUpdate(sql);
			System.out.println("related members to activities");
			

		}catch(Exception exception) {
	    	System.out.println(exception.getMessage());
	    }
		
		DataResource.closeConnection(connection);
	}
	
	private void tearDownTestDatabase() {

		Connection connection = DataResource.createConnectionToDB(testDB);
		String sql = "";
		try{
	    	
	    	Statement stmt = connection.createStatement();
	    	
	    	System.out.println("connecting to db");
	    	
	    	//delete all associations
			sql = ("DELETE FROM user_project_relationships");
			stmt.executeUpdate(sql);
			
			sql = ("DELETE FROM activity_project_relationships");
			stmt.executeUpdate(sql);
			
			sql = ("DELETE FROM activity_user_project_relationships");
			stmt.executeUpdate(sql);
			
			sql = ("DELETE FROM activity_edge_relationship");
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
		
		}catch(Exception exception) {
	    	System.out.println(exception.getMessage());
	    }
		System.out.println("Successfully tore down database.");
		DataResource.closeConnection(connection);
	}
	
	
}
