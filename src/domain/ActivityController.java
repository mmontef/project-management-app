package domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;

import resources.Activities;
import resources.TaskProgress;
import resources.Users;
import saver_loader.DataResource;


public class ActivityController extends ActivitySubject{
	ActivityController() {

	}
	
	private static void addDependencies(ArrayList<String> dependencies, Activities a)
	{
		for (String element : dependencies)
		{
			ArrayList<Activities> activities = DataResource.selectedProject.getActivityList();

            for (Activities activity : activities) 
            {

                if (activity.getLabel().equals(element))
                    DataResource.selectedProject.addArrow(activity, a);
            }
		}
	}
	
	private static ArrayList<Users> addMembers(ArrayList<String> members, Activities a)
	{
		ArrayList<Users> users = DataResource.projectMembers;
        ArrayList<Users> tmp = new ArrayList<Users>();

        for (String element : members) {
            for (Users user : users) {
                if (user.getName().equals(element))
                    tmp.add(user);
            }
        }
        return tmp;
	}
	
	public static boolean addActivity(String description, String startDate, String endDate, String label, ArrayList<String> dependencies, ArrayList<String> members, String progress, int budget, int mTime, int oTime, int pTime, int targetDate) {
		try {
			DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			Date start = dateFormatter.parse(startDate);
			Date end = dateFormatter.parse(endDate);

            if (start.before(end) && budget >= 0 && mTime >= 0 && oTime >= 0 && pTime >= 0 && targetDate >= 0 && oTime <= mTime && pTime >= mTime && (progress.equals("pending") || progress.equals("started") || progress.equals("complete")))
            {
                // Create activity and add it to current Project
                Activities newActivity = new Activities(description, start, end, label, TaskProgress.valueOf(progress), budget, mTime, oTime, pTime, targetDate);
                DataResource.selectedProject.addActivity(newActivity);

                // Set the dependencies in the JGraphT
                if (!dependencies.isEmpty())
                	addDependencies(dependencies, newActivity);
                
                if (!members.isEmpty())
                {
                ArrayList<Users> tmp = addMembers(members, newActivity);
                newActivity.setMemberList(tmp);
                }

                //***************************** SAVE NEW ACTIVITY TO DATABASE **********************
                DataResource.saveActivity(newActivity);

                notifyObservers();
                return true;
            }
            else
            {
                if (start.after(end))
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "End date must be AFTER start date",
                            "Incorrect Dates",
                            JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "No changes made: Please Fill in all values correctly",
                            "Values are incorrect format or missing",
                            JOptionPane.WARNING_MESSAGE);
                }
                return false;
            }

		} catch (Exception exception) {

            JOptionPane.showMessageDialog(new JFrame(),
                    "Please Fill in all values correctly",
                    "Values are incorrect format or missing",
                    JOptionPane.WARNING_MESSAGE);
            return false;
		}
	}
	
	public static boolean editActivity(String description, String startDate, String endDate, String label, ArrayList<String> dependencies, ArrayList<String> members, String progress, int budget, int mTime, int oTime, int pTime, int targetDate) {		
		try {
			DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
			Date start = dateFormatter.parse(startDate);
			Date end = dateFormatter.parse(endDate);
			
			Activities myActivity = DataResource.selectedActivity;

            if (start.before(end) && budget >= 0 && mTime >= 0 && oTime >= 0 && pTime >= 0 && targetDate >= 0 && oTime <= mTime && pTime >= mTime && (progress.equals("pending") || progress.equals("started") || progress.equals("complete")))
            {
                myActivity.setDescription(description);
                myActivity.setStartDate(start);
                myActivity.setEndDate(end);
                myActivity.setLabel(label);
                myActivity.setProgress(TaskProgress.valueOf(progress));
                myActivity.setBudget(budget);
                myActivity.setOptimisticTime(oTime);
                myActivity.setPessimisticTime(pTime);
                myActivity.setMostLikelyTime(mTime);
                myActivity.setTargetDate(targetDate);

                if (!dependencies.isEmpty()) {                	
                	String firstNode = new String();
                	String lastNode = new String();
                	for(Activities a : DataResource.selectedProject.getActivityList()) {
                		if(DataResource.selectedProject.getOutgoingArrowsOfActivity(a).size() == 0)
                			lastNode = a.getLabel();
                		else if(DataResource.selectedProject.getIncomingArrowsOfActivity(a).size() == 0)
                			firstNode = a.getLabel();
                	}
                	
                	if(myActivity.getLabel().equals(firstNode)) {
                		JOptionPane.showMessageDialog(new JFrame(),
                                "Cannot add dependencies to the first activity",
                                "Incorrect Dependency",
                                JOptionPane.WARNING_MESSAGE);
                		return false;
            		}
                	
                	if(dependencies.contains(lastNode)) {
                		JOptionPane.showMessageDialog(new JFrame(),
                                "Activity cannot depend on the last activity",
                                "Incorrect Dependency",
                                JOptionPane.WARNING_MESSAGE);
                		return false;
            		}

                    DataResource.selectedProject.resetIncomingEdges(myActivity);

                    // Set the dependencies in the JGraphT
                    addDependencies(dependencies, myActivity);
                }

                if (!members.isEmpty()) {
                    DataResource.resetActivityMembers(DataResource.selectedActivity.getId());
                    ArrayList<Users> tmp = addMembers(members, myActivity);
                    myActivity.setMemberList(tmp);
                    DataResource.selectedActivity.setMemberList(tmp);
                }


                //******************************SAVE TO DATABASE METHOD*********************************8
                DataResource.saveActivity(DataResource.selectedActivity);

                notifyObservers();
                return true;
            }
            else
            {
                if (start.after(end))
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                            "End date must be AFTER start date",
                            "Incorrect Dates",
                            JOptionPane.WARNING_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(new JFrame(),
                    		"No changes made: Please Fill in all values correctly",
                            "Values are incorrect format or missing",
                            JOptionPane.WARNING_MESSAGE);
                }
                return false;
            }

		} catch (Exception exception) {
            JOptionPane.showMessageDialog(new JFrame(),
                    "Please Fill in all values",
                    "Empty Values",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }

	}
	
	public static void deleteActivity() {
		Activities myActivity = DataResource.selectedActivity;
		DataResource.selectedProject.deleteActivity(myActivity);
		
		notifyObservers();
	}
}
