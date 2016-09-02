package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import Auction_Project.Auction_Server.hibernate.model.item;

public interface itemInterface {

	public void addItem(item u);
	public void updateItem(item u);
	public List<item> listItems();
	public item getItemById(int item_id);
	public item getItemByName(String item_name);
	public void removeItem(int item_id);
	
}


