package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import Auction_Project.Auction_Server.hibernate.model.auctionBidTransactions;

public interface auctionBidTransactionsInterface {

	public void addAuctionBidTransaction(auctionBidTransactions bid_trx);
	public List<auctionBidTransactions> listAuctionBidTransactions();
	public List<auctionBidTransactions> listAuctionBidTransactionsForItemById(int item_id);
	public List<Integer> listParticipatedItemIDsForUserByUserID(int user_id);
}