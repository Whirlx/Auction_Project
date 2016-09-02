package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import Auction_Project.Auction_Server.hibernate.model.itemCategory;

public interface itemCategoryInterface {

	public void addItemCategory(itemCategory i);
	public void updateItemCategory(itemCategory u);
	public List<itemCategory> listItemCategories();
	public itemCategory getItemCategoryById(int item_category_id);
	public itemCategory getItemCategoryByName(String item_category_name);
	public void removeItemCategory(int item_category_id);
	
}