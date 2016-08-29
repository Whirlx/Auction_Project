package Auction_Project.Auction_Server.hibernate.model;

//http://stackoverflow.com/questions/221611/creation-timestamp-and-last-update-timestamp-with-hibernate-and-mysql

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="users", 
		   uniqueConstraints={@UniqueConstraint(columnNames={"user_id"})})

public class users {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", nullable=false, unique=true, length=11)
	private int user_id;


	@Column(name="user_name", length=100, nullable=true)
	private String user_name;

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
	
    //private Date update_time=new Date();

    
    
	public int getuser_id() {
		return user_id;
	}
	public void setuser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getuser_name() {
		return user_name;
	}
	public void setuser_name(String user_name) {
		this.user_name= user_name;
	}

	public String getufirst_name() {
		return first_name;
	}
	public void setfirst_name(String first_name) {
		this.first_name= first_name;
	}
	
	public String getlast_name() {
			return last_name;
	}
	public void setlast_name(String last_name) {
			this.last_name= last_name;
	}
			
	public String getphone_number() {
				return phone_number;
			}
	public void setphone_number(String phone_number) {
				this.phone_number= phone_number;
	}
	

	public String getemail() {
		return email;
	}
	public void setemail(String email) {
		this.email= email;
	}

	public String getlast_login_time() {
		return last_login_time;
	}
	public void setlast_login_time(String last_login_time) {
		this.last_login_time= last_login_time;
	}


	public String getinsert_time() {
		return insert_time;
	}
	public void setinsert_time(String insert_time) {
		this.insert_time= insert_time;
	}
	
	public String getupdate_time() {
		return update_time;
	}
	public void setupdate_time(String update_time) {
		this.update_time= update_time;
	}	
	
} // users
