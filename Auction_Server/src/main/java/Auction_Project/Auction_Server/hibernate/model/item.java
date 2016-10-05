package Auction_Project.Auction_Server.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="items", uniqueConstraints={@UniqueConstraint(columnNames={"item_id"})})
public class item {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_id", nullable=false, unique=true, length=11)
	private int item_id;
	
	@Column(name="item_user_id", length=100, nullable=true)
	private int item_user_id;
	
	@Column(name="item_user_name", length=100, nullable=true)
	private String item_user_name;
	
	@Column(name="item_category", length=100, nullable=true)
	private String item_category;
																		
	@NaturalId
	@Column(name="item_name", length=100, nullable=true)
	private String item_name;
	
	@Column(name="item_desc", length=100, nullable=true)
	private String item_desc;
	
	@Column(name="item_picture", length=10000000,nullable=true)
	@Lob
	private String item_picture;

	@Column(name="item_num_bids", length=100, nullable=true)
	private int item_num_bids;

	@Column(name="item_start_price", length=100, nullable=true)
	private int item_start_price;
	
	@Column(name="item_latest_bid_price", length=100, nullable=true)
	private int item_latest_bid_price;
	
	@Column(name="item_latest_bid_time", length=100, nullable=true, updatable = false)
	private String item_latest_bid_time;
	
	@Column(name="item_latest_bid_userid", length=100, nullable=true)
	private int item_latest_bid_userid;
	
	@Column(name="item_latest_bid_username", length=100, nullable=true)
	private String item_latest_bid_username;

	@Column(name="auction_start_time", length=100, nullable=true)
	private String auction_start_time;
	
	@Column(name="duration_in_hours", length=100, nullable=true)
	private int duration_in_hours;
	
	@Column(name="isAuctionOver", length=100, nullable=true)
	private boolean isAuctionOver;

	public item() {}
	
	public item(item item) {
    	this.item_id = item.getItemID();
    	this.item_user_id = item.getItemUserId();
    	this.item_user_name = item.getItem_user_name();
    	this.item_category = item.getItemCategory();
    	this.item_name = item.getItemName();
    	this.item_picture = item.getItemPicture();
    	this.item_num_bids = item.getItem_num_bids();
    	this.item_desc = item.getItemDescription();
    	this.item_start_price = item.getItemStartingPrice();
    	this.item_latest_bid_price = item.getItemLatestBidPrice();
    	this.item_latest_bid_time = item.getItem_latest_bid_time();
		this.item_latest_bid_userid = item.getItem_latest_bid_userid();
		this.item_latest_bid_username = item.getItem_latest_bid_username();
		this.auction_start_time = item.getAuction_start_time();
		this.duration_in_hours = item.getDuration_in_hours();
		this.isAuctionOver = item.isAuctionOver();
    }
	
	public String toString(){
		return "\nItem details:{" +
				" item_id:"   		+ this.getItemID() + 
				" item_user_id:" 		+ this.getItemUserId() +
				" item_user_name:"		+ this.getItem_user_name()+
				" item_category:"		+ this.getItemCategory()+
				" item_name:" 		+ this.getItemName() +
				" item_desc:"  		+ this.getItemDescription() +
				" item_picture:"	+ this.getItemPicture() +
				" item_start_price:"			+ this.getItemStartingPrice() +
				" item_latest_bid_price:" + this.getItemLatestBidPrice() +
				" item_latest_bid_time:" 	+ this.getItem_latest_bid_time() +
				" item_latest_bid_userid:" 	+ this.getItemUserId() +
				" auction_start_time:" 	+ this.getAuction_start_time() +
				" duration_in_hours:" 	+ this.getDuration_in_hours() +
				" isAuctionOver:" 	+ this.isAuctionOver() +
				"}";
			
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
	public String getItemPicture() {
		return item_picture;
	}

	public void setItemPicture(String item_picture) {
		this.item_picture = item_picture;
	}

	@JsonProperty("item_num_bids")
	public int getItem_num_bids() {
		return item_num_bids;
	}

	public void setItem_num_bids(int item_num_bids) {
		this.item_num_bids = item_num_bids;
	}
	
	public void increaseItem_num_bids()
	{
		this.item_num_bids++;
	}
	
	@JsonProperty("item_start_price")
	public int getItemStartingPrice() {
		return item_start_price;
	}

	public void setItemStartingPrice(int item_start_price) {
		this.item_start_price = item_start_price;
	}

	@JsonProperty("item_latest_bid_price")
	public int getItemLatestBidPrice() {
		return item_latest_bid_price;
	}

	public void setItemLatestBidPrice(int item_latest_bid_price) {
		this.item_latest_bid_price = item_latest_bid_price;
	}
	
	@JsonProperty("item_latest_bid_time")
	public String getItem_latest_bid_time() {
		return item_latest_bid_time;
	}

	public void setItem_latest_bid_time(String item_latest_bid_time) {
		this.item_latest_bid_time = item_latest_bid_time;
	}

	@JsonProperty("item_latest_bid_userid")
	public int getItem_latest_bid_userid() {
		return item_latest_bid_userid;
	}

	public void setItem_latest_bid_userid(int item_latest_bid_userid) {
		this.item_latest_bid_userid = item_latest_bid_userid;
	}
	
	@JsonProperty("item_latest_bid_username")
	public String getItem_latest_bid_username() {
		return item_latest_bid_username;
	}

	public void setItem_latest_bid_username(String item_latest_bid_username) {
		this.item_latest_bid_username = item_latest_bid_username;
	}

	@JsonProperty("auction_start_time")
	public String getAuction_start_time() {
		return auction_start_time;
	}

	public void setAuction_start_time(String auction_start_time) {
		this.auction_start_time = auction_start_time;
	}

	@JsonProperty("duration_in_hours")
	public int getDuration_in_hours() {
		return duration_in_hours;
	}

	public void setDuration_in_hours(int duration_in_hours) {
		this.duration_in_hours = duration_in_hours;
	}
	
	@JsonProperty("item_user_name")
	public String getItem_user_name() {
		return item_user_name;
	}

	public void setItem_user_name(String item_user_name) {
		this.item_user_name = item_user_name;
	}

	@JsonProperty("isAuctionOver")
	public boolean isAuctionOver() {
		return isAuctionOver;
	}

	public void setAuctionOver(boolean isAuctionOver) {
		this.isAuctionOver = isAuctionOver;
	}
}
