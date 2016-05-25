package resources;

import java.util.ArrayList;

public class Projects {

	private int id;
	private String projectName;
	private Users[] projectMembers;
	private String date;

	private ArrayList<Activities> activites;

	public Projects(int id, String projectName, Users[] projectMembers, String date, ArrayList<Activities> activites) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.projectMembers = projectMembers;
		this.date = date;
		this.activites = activites;
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

	public ArrayList<Activities> getActivites() {
		return activites;
	}

	public void setActivites(ArrayList<Activities> activites) {
		this.activites = activites;
	}
	
	
	
	
	
}
