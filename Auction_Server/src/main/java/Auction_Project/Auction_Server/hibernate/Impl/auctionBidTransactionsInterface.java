package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import Auction_Project.Auction_Server.hibernate.model.auctionBidTransactions;
import Auction_Project.Auction_Server.hibernate.model.itemCategory;;

public interface auctionBidTransactionsInterface {

	public void addAuctionBidTransaction(auctionBidTransactions bid_trx);
	public void updateAuctionBidTransaction(auctionBidTransactions bid_trx);
	public List<auctionBidTransactions> listAuctionBidTransactions();
	
	public auctionBidTransactions getAuctionBidTransactionByID(int auc_bid_trx_id);
	public void removeAuctionBidTransactions(int auc_bid_trx_id);
	public void GetTopBidsForItem(int item_id,int number_of_bids);
	public void updateWinbid(int item_id);
		
}
