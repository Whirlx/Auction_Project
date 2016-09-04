package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import Auction_Project.Auction_Server.hibernate.model.auctionBidTransactions;;

public interface auctionBidTransactionsInterface {


	public void addIauctionBidTransactions(auctionBidTransactions bid_trx);
	//public void updateItemCategory(itemCategory u);
	public List<auctionBidTransactions> listauctionBidTransactions();
	public auctionBidTransactions getauctionBidTransactionsByTrxID(int auc_bid_trx_id);
	//public auctionBidTransactions getItemCategoryByName(String item_category_name);
	public void removeAuctionBidTransactions(int auc_bid_trx_id);
	
	public void GetTopBidsForItem(int item_id,int number_of_bids);
	public void updateWinbid(int item_id);
	
	
		
}
