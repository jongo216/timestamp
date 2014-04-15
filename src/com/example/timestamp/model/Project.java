package com.example.timestamp.model;
import java.lang.String;

public class Project {

	  private int id;
	  private String name;
	  private String description;
	  private String owner;
	  private String customer;
	  private Boolean isPrivate;
	  
	  
	
	  
	  //Default constructor
	  public Project()
	  {
		  setId(-1);
		  setName("Default Project");
		  setDescription("");
		  setOwner("Default Owner");
		  setCustomer("Default Customer");
		  setIsPrivate(false);
	  }
	  
	  //Constructor
	  public Project(int ID, String Name, Boolean IsPrivate, String Description, 
			  String Owner, String Customer)
	  {
		setId(ID);
		setName(Name);
		setDescription(Description);
		setOwner(Owner);
		setCustomer(Customer);
		setIsPrivate(IsPrivate);
	  }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
}
