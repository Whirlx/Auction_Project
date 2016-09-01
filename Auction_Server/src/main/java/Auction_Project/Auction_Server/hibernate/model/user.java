package Auction_Project.Auction_Server.hibernate.model;

//http://stackoverflow.com/questions/221611/creation-timestamp-and-last-update-timestamp-with-hibernate-and-mysql

import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.codehaus.jackson.annotate.JsonProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="users", uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})
public class user {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false, unique=true, length=11)
    private int user_id;
	
	@Column(name="user_name", length=100, nullable=true)
    private String user_name;
	
	@Column(name="user_pwd", length=100, nullable=true, insertable=true, updatable=true, columnDefinition=" default 'password'")
	@ColumnDefault("pwd")
	private String user_pwd;
	
	@Column(name="first_name", length=100, nullable=true)
    private String first_name;
	
	@Column(name="last_name", length=100, nullable=true)
    private String last_name;
	
	@Column(name="phone_number", length=100, nullable=true)
    private String phone_number;
	
	@Column(name="email", length=300, nullable=true)
    private String email;
	
	@Column(name="last_login_time", length=100, nullable=true)
	private String last_login_time;
	
	@Column(name="insert_time", length=100, nullable=true)
    private String insert_time;
	
	@Column(name="update_time", length=100, nullable=true)
    private String update_time;
     
    public user() {}
    
    public user(int user_id, String user_name, String user_pwd, String first_name, String last_name, String phone_number, String email) {
    	this.user_id = user_id;
    	this.user_name = user_name;
    	this.user_pwd = user_pwd;
    	this.first_name = first_name;
    	this.last_name = last_name;
    	this.phone_number = phone_number;
    	this.email = email;
    	Calendar c = Calendar.getInstance();
		this.insert_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		this.last_login_time = this.insert_time;
		this.update_time = this.insert_time;
    }
    
    public user(user user) {
    	this.user_id = user.getUserId();
    	this.user_name = user.getUserName();
    	this.user_pwd = user.getPassword();
    	this.first_name = user.getFirstName();
    	this.last_name = user.getLastName();
    	this.phone_number = user.getPhoneNumber();
    	this.email = user.getEmail();
    	Calendar c = Calendar.getInstance();
		this.insert_time = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(c.getTime()); // Set the start date to local time
		this.last_login_time = this.insert_time;
		this.update_time = this.insert_time;
    }
    
    public String toString(){
		return "\nUser details:{" +
				" user_id:"   		+ this.getUserId() + 
				" user_name:" 		+ this.getUserName() +
				" user_pwd:"		+ this.getPassword()+
				" first_name:" 		+ this.getFirstName() +
				" last_name:"  		+ this.getLastName() +
				" phone_number:"	+ this.getPhoneNumber() +
				" email:"			+ this.getEmail() +
				" last_login_time:" + this.getLast_login_time() +
				" insert_time:" 	+ this.getInsert_time() +
				" update_time:" 	+ this.getUpdate_time() +
				"}";
			
	}
    
    @JsonProperty("user_id")
	public int getUserId() {
		return user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
	
	@JsonProperty("user_name")
	public String getUserName() {
		return user_name;
	}

	public void setUserName(String user_name) {
		this.user_name = user_name;
	}

	@JsonProperty("user_pwd")
	public String getPassword() {
		return user_pwd;
	}

	public void setPassword(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	
	@JsonProperty("first_name")
	public String getFirstName() {
		return first_name;
	}

	public void setFirstName(String first_name) {
		this.first_name = first_name;
	}

	@JsonProperty("last_name")
	public String getLastName() {
		return last_name;
	}

	public void setLastName(String last_name) {
		this.last_name = last_name;
	}

	@JsonProperty("phone_number")
	public String getPhoneNumber() {
		return phone_number;
	}

	public void setPhoneNumber(String phone_number) {
		this.phone_number = phone_number;
	}
	
	@JsonProperty("email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonProperty("last_login_time")
	public String getLast_login_time() {
		return last_login_time;
	}

	public void setLast_login_time(String last_login_time) {
		this.last_login_time = last_login_time;
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
