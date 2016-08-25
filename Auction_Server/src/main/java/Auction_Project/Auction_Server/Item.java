package Auction_Project.Auction_Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Item {
	
	private int itemID;
	private String itemName;
	private String itemDescription;
	private int userID;
	private int startingPrice;
	private int latestBid;
	private int numOfOffers;
	private String bidStartDate;
	private String bidEndDate;
	
	public Item(int itemID, String itemName, String itemDescription, int userID, int startingPrice) {
		this.itemID = itemID;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.userID = userID;
		this.startingPrice = startingPrice;
		this.latestBid = 0;
		this.numOfOffers = 0;
		Calendar c = Calendar.getInstance();
		this.bidStartDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		c.add(Calendar.DATE, 1); // Add 1 day to the date
		this.bidEndDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the end date to 1 day ahead of start date
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public int getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}

	public int getLatestBid() {
		return latestBid;
	}

	public void setLatestBid(int latestBid) {
		this.latestBid = latestBid;
	}

	public int getNumOfOffers() {
		return numOfOffers;
	}

	public void setNumOfOffers(int numOfOffers) {
		this.numOfOffers = numOfOffers;
	}

	public String getBidStartDate() {
		return bidStartDate;
	}

	public void setBidStartDate(String bidStartDate) {
		this.bidStartDate = bidStartDate;
	}

	public String getBidEndDate() {
		return bidEndDate;
	}

	public void setBidEndDate(String bidEndDate) {
		this.bidEndDate = bidEndDate;
	}
	
	
}
