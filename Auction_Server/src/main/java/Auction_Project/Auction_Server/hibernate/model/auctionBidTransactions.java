package Auction_Project.Auction_Server.hibernate.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.codehaus.jackson.annotate.JsonProperty;

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
	
	public auctionBidTransactions() {}
	
	public auctionBidTransactions(auctionBidTransactions auc_bid_trx) {
    	this.auc_bid_trx_id = auc_bid_trx.getAuc_bid_trx_id();
    	this.user_id = auc_bid_trx.getUser_id();
    	this.item_id = auc_bid_trx.getItem_id();
    	this.item_bid_time = auc_bid_trx.getItem_bid_time();
    	this.item_bid_price = auc_bid_trx.getitem_bid_price();
    	this.insert_time = auc_bid_trx.getInsert_time();
    }

	public auctionBidTransactions(int userId, int itemID, int itemLastBidPrice) {
		this.user_id = userId;
		this.item_id = itemID;
		this.item_bid_price = itemLastBidPrice;
	}

	@JsonProperty("auc_bid_trx_id")
	public int getAuc_bid_trx_id() {
		return auc_bid_trx_id;
	}

	public void setAuc_bid_trx_id(int auc_bid_trx_id) {
		this.auc_bid_trx_id = auc_bid_trx_id;
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
	
}
