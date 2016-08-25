package Auction_Project.Auction_Server;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
	
    private int ID;
    private String password;
    private String firstName;
    private String lastName;
    private String description;
    private List<Item> listOfItems;
    private List<Bid> listOfBids;
     
    public User() {}
    
    public User(int ID, String password, String firstName, String lastName, String description) {
    	this.ID = ID;
    	this.password = password;
    	this.firstName = firstName;
    	this.lastName = lastName;
    	this.description = description;
    	this.listOfItems = new ArrayList<Item>();
    	this.listOfBids = new ArrayList<Bid>();
    }
    
    public User(User user) {
    	this.ID = user.getID();
    	this.password = user.getPassword();
    	this.firstName = user.getFirstName();
    	this.lastName = user.getLastName();
    	this.description = user.getDescription();
    	this.listOfItems = new ArrayList<Item>();
    	this.listOfBids = new ArrayList<Bid>();
    }
    
    @JsonProperty("ID")
	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return firstName+" "+lastName;
	}
	
	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Item> getListOfItems() {
		return listOfItems;
	}

	public void addItem(Item item) {
		this.listOfItems.add(item);
	}

	public List<Bid> getListOfBids() {
		return listOfBids;
	}

	public void setListOfBids(List<Bid> listOfBids) {
		this.listOfBids = listOfBids;
	}
     
}