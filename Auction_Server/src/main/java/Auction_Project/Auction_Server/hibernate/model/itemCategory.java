package Auction_Project.Auction_Server.hibernate.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="item_categories", uniqueConstraints={@UniqueConstraint(columnNames={"item_category_id"})})
public class itemCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="item_category_id", nullable=false, unique=true, length=11)
	private int item_category_id;
	
	@NaturalId
	@Column(name="item_category_name", length=100, nullable=true)
	private String item_category_name;
	
	@Column(name="insert_time", length=100, nullable=true)
	private String insert_time;
	
	public itemCategory() {}
	
	public itemCategory(itemCategory itemCategory) {
    	this.item_category_id = itemCategory.getItemCategoryID();
    	this.item_category_name = itemCategory.getItemCategoryName();
		this.insert_time = itemCategory.getInsert_time();
    }
	
	public itemCategory(String item_category_name) {
		this.item_category_name = item_category_name;
	}
	
	@JsonProperty("item_category_id")
	public int getItemCategoryID() {
		return item_category_id;
	}

	public void setItemCategoryID(int item_category_id) {
		this.item_category_id = item_category_id;
	}
	
	@JsonProperty("item_category_name")
	public String getItemCategoryName() {
		return item_category_name;
	}

	public void setItemCategoryName(String item_category_name) {
		this.item_category_name = item_category_name;
	}

	@JsonProperty("insert_time")
	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}
	
}
