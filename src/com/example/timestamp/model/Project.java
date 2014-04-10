package com.example.timestamp.model;
import java.lang.String;

public class Project {

	  public int id;
	  public String name;
	  public String description;
	  public String owner;
	  public String customer;
	  public Boolean isPrivate;
	  
	  //Default constructor
	  public Project()
	  {
		  id = -1;
		  name = "Default Project";
		  description = "";
		  owner = "Localhost";
		  customer = null;
		  isPrivate = true;
	  }
	  
	  //Constructor
	  public Project(int ID, String Name, Boolean IsPrivate, String Description, 
			  String Owner, String Customer)
	  {
		id = ID;
		name = Name;
		description = Description;
		owner = Owner;
		customer = Customer;
		isPrivate = IsPrivate;
	  }
	
}
