package Auction_Project.Auction_Server.hibernate.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.UniqueConstraint;

import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.type.TimestampType;

@Entity
@Table(name="auction_bid_transactions", uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})
public class auctionBidTransactions {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="auc_bid_trx_id", nullable=false, unique=true, length=11)
	private int auc_bid_trx_id;
	
	@Column(name="user_id", nullable=false, unique=true, length=11)
	private int user_id;
	
	@Column(name="item_id", nullable=false, unique=true, length=11)
	private int item_id;
	
	@Column(name="item_bid_time", nullable=true, unique=true, length=11)
	private String  item_bid_time;

	@Column(name="item_bid_price", nullable=false, unique=true, length=11)
	private long item_bid_price;

	@Column(name="insert_time", length=100, nullable=true)
	private String insert_time;
	
	@Column(name="update_time", length=100, nullable=true)
	private String update_time;
	
	
	public auctionBidTransactions() {}
	
	public auctionBidTransactions(int user_id, int  item_id ,String  item_bid_time ,long item_bid_price) {
		//this.item_category_id = item_category_id;
		//this.item_category_name = item_category_name;
		Calendar c = Calendar.getInstance();
		this.insert_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		this.update_time = this.insert_time;
	}
	
	public auctionBidTransactions(auctionBidTransactions auc_bid_trx) {
    	//this.item_category_id = auc_bid_trx();
    	//this.item_category_name = auc_bid_trx.getItemCategoryName();
    	Calendar c = Calendar.getInstance();
		this.insert_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		this.update_time = this.insert_time;
    }

	public auctionBidTransactions(int userId, int itemID, int itemLastBidPrice) {
		this.user_id = userId;
		this.item_id = itemID;
		this.item_bid_price = itemLastBidPrice;
	}

	@JsonProperty("user_id")
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	
	@JsonProperty("item_id")
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}

		
	@JsonProperty("item_bid_time")
	public String  getItem_bid_time() {
		return item_bid_time;
	}
	public void setItem_bid_time(String item_bid_time) {
		this.item_bid_time = item_bid_time;
	}

	
	
	@JsonProperty("item_bid_price")
	public long getitem_bid_price() {
		return item_bid_price;
	}

	public void setItem_bid_price(long item_bid_price) {
		this.item_bid_price = item_bid_price;
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
