package domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

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

        try
        {
            if (!projectName.isEmpty() && !projectIDDescription.isEmpty() && budget > 0)
            {
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
            else
            {
                if (budget < 0)
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Budget cannot be negative",
                            "Budget cannot be negative",
                            JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please fill in all values",
                            "Values are Empty",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        }
        catch (Exception exception)
        {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please fill in all values",
                    "Values are Empty",
                    JOptionPane.WARNING_MESSAGE);
        }

	}
	
	public static void editProject(String projectName, String projectIDDescription, double budget) {
		Projects myProject = DataResource.selectedProject;

        try
        {
            if (!projectName.isEmpty() && !projectIDDescription.isEmpty() && budget > 0)
            {
                myProject.setProjectName(projectName);
                myProject.setDescription(projectIDDescription);
                myProject.setBudget(budget);

                DataResource.saveProject(myProject); //Save the new project to the database.

                notifyObservers();
            }
            else
            {
                if (budget < 0)
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Budget cannot be negative",
                            "Budget cannot be negative",
                            JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "Please fill in all values",
                            "Values are Empty",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        }
        catch(Exception exception)
        {
                    JOptionPane.showMessageDialog(new JFrame(),
                    "Please fill in all values",
                    "Values are Empty",
                            JOptionPane.WARNING_MESSAGE);
        }

	}
	
	public static void deleteProject() {
		Projects toDelete = DataResource.selectedProject;
		
		DataResource.removeProject(toDelete);
		DataResource.selectedProject = null;
				
		notifyObservers();
	}
}
