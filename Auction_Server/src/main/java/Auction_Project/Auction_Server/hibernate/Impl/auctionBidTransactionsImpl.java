package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Auction_Project.Auction_Server.hibernate.model.auctionBidTransactions;
import Auction_Project.Auction_Server.hibernate.model.item;

public class auctionBidTransactionsImpl implements auctionBidTransactionsInterface
{
	private SessionFactory sessionFactory;

	public auctionBidTransactionsImpl(SessionFactory sessionFactory)
	{
		if (sessionFactory !=null)
		{
			this.setSessionFactory(sessionFactory);
			if (sessionFactory.isClosed() ) 
			{
			}
			else 
			{
			//sessionFactory.openSession();
			}
		}
		else
		{
		}
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void addAuctionBidTransaction(auctionBidTransactions bid_trx) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(bid_trx);
		tx.commit();
		session.close();
	}

	@Override
	public void updateAuctionBidTransaction(auctionBidTransactions bid_trx) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<auctionBidTransactions> listAuctionBidTransactions() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<auctionBidTransactions> transactionsList = session.createQuery("from auctionBidTransactions").list();
		tx.commit();
		session.close();
		return transactionsList;
	}
	
	@Override
	public List<Integer> listParticipatedItemIDsForUserByUserID(int user_id) {
		List<Integer> participatedItemIDsList = new ArrayList<Integer>();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<auctionBidTransactions> transactionsListForUser = session.createQuery("from auctionBidTransactions where user_id = "+user_id).list();
		for(auctionBidTransactions u : transactionsListForUser)
		{
			int itemId = u.getItem_id();
			if( participatedItemIDsList.contains(itemId) == false )
			{
				participatedItemIDsList.add(itemId);
			}
		}
		tx.commit();
		session.close();
		return participatedItemIDsList;
	}

	@Override
	public auctionBidTransactions getAuctionBidTransactionByID(int auc_bid_trx_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAuctionBidTransactions(int auc_bid_trx_id) {
		// TODO Auto-generated method stub
	}

	@Override
	public void GetTopBidsForItem(int item_id, int number_of_bids) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateWinbid(int item_id) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<auctionBidTransactions> listAuctionBidTransactionsForItemById(int item_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<auctionBidTransactions> transactionsList = session.createQuery("from auctionBidTransactions where item_id = "+item_id).list();
		tx.commit();
		session.close();
		return transactionsList;
	}

	

}
