package resources;

public class Users {

	String name;
	String firstName;
	String lastName;
	String password;
	int id;
	userType type;	
	
	enum userType {	
		ADMIN, MANAGER, MEMBER
	}
	
	
	public Users(){
		this.name = null;
		this.password = null;
		this.id = -1;
		this.type = null;
		this.firstName = null;
		this.lastName = null;
	}
	
	public Users(String userName, String firstName, String lastName, String password, int iD, String usertype){
		this.name = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.id = iD;
		switch (usertype)
		{
		case "MANAGER":
		{
			this.type = userType.MANAGER;
			break;
		}
		case "ADMIN":
		{
			this.type = userType.ADMIN;
			break;
		}
		case "MEMEBER":
		{
			this.type = userType.MEMBER;
			break;
		}
		default:
			this.type = userType.MEMBER;
			break;
		}		
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String userName){
		this.name = userName;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword(String newPassword){
		this.password = newPassword;
	}
	
	public int getID(){
		return this.id;
	}
	
	public void setID(int newID) {
		this.id = newID;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public userType getType() {
		return type;
	}

	public void setType(userType type) {
		this.type = type;
	}

	public void drawMenu(){
		
		switch(this.type){
		
		case ADMIN: ;
		
		case MANAGER: ;
		
		case MEMBER: ;
		
		}
	}
}