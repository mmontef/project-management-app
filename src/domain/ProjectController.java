package domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import listview_components.ProjectListPane;
import resources.Projects;
import resources.Users;
import saver_loader.DataResource;

public class ProjectController extends ProjectSubject{
	ProjectController() {
		
	}
	
	public static void addProject(String projectName, String projectIDDescription, double budget) {
		//Initialize A user list and add current user to it
		ArrayList<Users> userList = new ArrayList<Users>();
        userList.add(DataResource.currentUser);
        
        // Create the date
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        
        //Create a new Project with information from the form, userList, and date
        Projects newProject = new Projects(projectName, userList, date, DataResource.currentUser.getID(), projectIDDescription, budget);
        DataResource.projectList.add(newProject);
        
        DataResource.saveProject(newProject);
        
        notifyObservers();
	}
	
	public static void editProject(String projectName, String projectIDDescription, double budget) {
		Projects myProject = DataResource.selectedProject;
		
		myProject.setProjectName(projectName);
		myProject.setDescription(projectIDDescription);
		myProject.setBudget(budget);
		
		DataResource.saveProject(myProject); //Save the new project to the database.
	   	    
		notifyObservers();
	}
	
	public static void deleteProject() {
		Projects toDelete = DataResource.selectedProject;
		
		DataResource.removeProject(toDelete);
		DataResource.selectedProject = null;
				
		notifyObservers();
	}
}
