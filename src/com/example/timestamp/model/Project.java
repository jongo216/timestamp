package com.example.timestamp.model;
import java.lang.String;

public class Project {

	  public int id;
	  public String name;
	  public String description;
	  public String owner;
	  public String customer;
	  
	  //Default constructor
	  public Project()
	  {
		  id = -1;
		  name = "Default Project";
		  description = "";
		  owner = "Localhost";
		  customer = null;
	  }
	  
	  //Constructor
	  public Project(int ID, String Name, String Description, 
			  String Owner, String Customer)
	  {
		id = ID;
		name = Name;
		description = Description;
		owner = Owner;
		customer = Customer;
	  }
	
}
