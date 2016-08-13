package Auction_Project.Auction_Server;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({ "ID", "name", "balance", "bids", "items" })
public class User {
	
    private int ID = 0;
    private String name = "default";
    private List<Bid> listOfBids;
    private List<Item> listOfItems;
    private int balance = 0;
     
    public User() {
    	this.listOfBids = new ArrayList<Bid>();
    	this.listOfItems = new ArrayList<Item>();
    }
    
    @JsonProperty("ID")
    public int getID() {
        return this.ID;
    }
    
    public void setID(int ID) {
        this.ID = ID;
    }
    
    @JsonProperty("name")
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    @JsonProperty("bids")
    public List<Bid> getBidsList() {
        return this.listOfBids;
    }
    
    public void addBid(Bid bid) {
    	this.listOfBids.add(bid);
    }
    
    public void removeBid(Bid bid) {
    	this.listOfBids.remove(bid);
    }
    
    @JsonProperty("items")
    public List<Item> getItemsList() {
        return this.listOfItems;
    }
    
    public void addItem(Item item) {
    	this.listOfItems.add(item);
    }
    
    public void removeItem(Item item) {
    	this.listOfItems.remove(item);
    }
    
    @JsonProperty("balance")
    public int getBalance() {
        return this.balance;
    }
    
    public void addBalance(int addedBalance) {
        this.balance = addedBalance;
    }
     
}