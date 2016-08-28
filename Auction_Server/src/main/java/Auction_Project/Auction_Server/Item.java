package Auction_Project.Auction_Server;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.codehaus.jackson.annotate.JsonProperty;

public class Item {
	
	private int item_id;
	private int item_user_id;
	private String item_category;
	private String item_name;
	private String item_desc;
	private int item_picture; // int placeholder type
	private int item_start_price;
	private int item_last_bid_price;
	private String item_last_bid_time;
	private int item_last_bid_userid;
	private String insert_time;
	private String update_time;
	
	public Item() {}
	
	public Item(int item_id, int item_user_id, String item_category, String item_name, String item_desc, int item_picture, int item_start_price) {
		this.item_id = item_id;
		this.item_user_id = item_user_id;
		this.item_category = item_category;
		this.item_name = item_name;
		this.item_desc = item_desc;
		this.item_picture = item_picture;
		this.item_start_price = item_start_price;
		this.item_last_bid_price = 0;
		this.item_last_bid_time = "";
		this.item_last_bid_userid = 0;
		Calendar c = Calendar.getInstance();
		this.insert_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		this.update_time = this.insert_time;
	}
	
	public Item(Item item) {
    	this.item_id = item.getItemID();
    	this.item_user_id = item.getItemUserId();
    	this.item_category = item.getItemCategory();
    	this.item_name = item.getItemName();
    	this.item_desc = item.getItemDescription();
    	this.item_picture = item.getItemPicture();
    	this.item_start_price = item.getItemStartingPrice();
    	this.item_last_bid_price = 0;
		this.item_last_bid_time = "";
		this.item_last_bid_userid = 0;
		Calendar c = Calendar.getInstance();
		this.insert_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		this.update_time = this.insert_time;
    }

	@JsonProperty("item_id")
	public int getItemID() {
		return item_id;
	}

	public void setItemID(int item_id) {
		this.item_id = item_id;
	}
	
	@JsonProperty("item_user_id")
	public int getItemUserId() {
		return item_user_id;
	}

	public void setItemUserId(int item_user_id) {
		this.item_user_id = item_user_id;
	}

	@JsonProperty("item_category")
	public String getItemCategory() {
		return item_category;
	}

	public void setItemCategory(String item_category) {
		this.item_category = item_category;
	}
	
	@JsonProperty("item_name")
	public String getItemName() {
		return item_name;
	}

	public void setItemName(String item_name) {
		this.item_name = item_name;
	}

	@JsonProperty("item_desc")
	public String getItemDescription() {
		return item_desc;
	}

	public void setItemDescription(String item_desc) {
		this.item_desc = item_desc;
	}
	
	@JsonProperty("item_picture")
	public int getItemPicture() {
		return item_picture;
	}

	public void setItemPicture(int item_picture) {
		this.item_picture = item_picture;
	}

	@JsonProperty("item_start_price")
	public int getItemStartingPrice() {
		return item_start_price;
	}

	public void setItemStartingPrice(int item_start_price) {
		this.item_start_price = item_start_price;
	}

	@JsonProperty("item_last_bid_price")
	public int getItemLastBidPrice() {
		return item_last_bid_price;
	}

	public void setItemLastBidPrice(int item_last_bid_price) {
		this.item_last_bid_price = item_last_bid_price;
	}
	
	@JsonProperty("item_last_bid_time")
	public String getItem_last_bid_time() {
		return item_last_bid_time;
	}

	public void setItem_last_bid_time(String item_last_bid_time) {
		this.item_last_bid_time = item_last_bid_time;
	}

	@JsonProperty("item_last_bid_userid")
	public int getItem_last_bid_userid() {
		return item_last_bid_userid;
	}

	public void setItem_last_bid_userid(int item_last_bid_userid) {
		this.item_last_bid_userid = item_last_bid_userid;
	}

	@JsonProperty("insert_time")
	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

	@JsonProperty("update_time")
	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
