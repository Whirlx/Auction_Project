package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Auction_Project.Auction_Server.hibernate.model.auctionBidTransactions;

public class auctionBidTransactionsImpl implements auctionBidTransactionsInterface
{
	public static final Logger logger = Logger.getLogger(userImpl.class.getName());
	private SessionFactory sessionFactory;

	public auctionBidTransactionsImpl(SessionFactory sessionFactory)
	{
		if( sessionFactory == null )
		{
			logger.warning("[Hibernate @ auctionBidTransactionsImpl] - Error, received null SessionFactory.");
		}
		if( sessionFactory.isClosed() )
		{
			logger.warning("[Hibernate @ auctionBidTransactionsImpl] - Error, SessionFactory is closed.");
		}
		this.setSessionFactory(sessionFactory);
		logger.info("[Hibernate @ auctionBidTransactionsImpl] - Received valid SessionFactory.");
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
		logger.info("[Hibernate @ auctionBidTransactionsImpl] - Auction Bid Transaction added successfully.");
	}

	@Override
	public List<auctionBidTransactions> listAuctionBidTransactions() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<auctionBidTransactions> transactionsList = session.createQuery("from auctionBidTransactions").list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ auctionBidTransactionsImpl] - Received Auction Bid Transaction list successfully.");
		return transactionsList;
	}
	
	@Override
	public List<Integer> listParticipatedItemIDsForUserByUserID(int user_id) {
		List<Integer> participatedItemIDsList = new ArrayList<Integer>();
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
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
		logger.info("[Hibernate @ auctionBidTransactionsImpl] - Received participated items list successfully.");
		return participatedItemIDsList;
	}

	@Override 
	public List<auctionBidTransactions> listAuctionBidTransactionsForItemById(int item_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<auctionBidTransactions> transactionsList = session.createQuery("from auctionBidTransactions where item_id = "+item_id).list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ auctionBidTransactionsImpl] - Received Auction Bid Transaction list for item successfully.");
		return transactionsList;
	}

}
