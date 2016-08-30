package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import Auction_Project.Auction_Server.hibernate.model.user;

public interface userInterface {

	public void addUser(user u);
	public void updateUser(user u);
	public List<user> listUsers();
	public user getUserById(int user_id);
	public void removeUser(int user_id);
	
}


