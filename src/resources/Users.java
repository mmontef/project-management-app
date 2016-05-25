package resources;

public class Users {

	String name;
	String password;
	int id;
	userType type;	
	
	enum userType {	
		ADMIN, MANAGER, MEMBER
	}
	
	
	public Users(){
		this.name = null;
		this.password = null;
		this.id = 0;
		this.type = null;
	}
	
	public Users(String userName, String userPassword, int iD, userType usertype){
		this.name = userName;
		this.password = userPassword;
		this.id = iD;
		this.type = usertype;
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
	
	public void setID(int newID){
		this.id = newID;
	}
	
	public void drawMenu(){
		
		switch(this.type){
		
		case ADMIN: ;
		
		case MANAGER: ;
		
		case MEMBER: ;
		
		}
	}
}
