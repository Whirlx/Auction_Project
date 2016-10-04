package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Auction_Project.Auction_Server.hibernate.model.user;

public class userImpl implements userInterface {
	public static final Logger logger = Logger.getLogger(userImpl.class.getName());
	private SessionFactory sessionFactory;

	public userImpl(SessionFactory sessionFactory)
	{
		if( sessionFactory == null )
		{
			logger.warning("[Hibernate @ userImpl] - Error, received null SessionFactory.");
		}
		if( sessionFactory.isClosed() )
		{
			logger.warning("[Hibernate @ userImpl] - Error, SessionFactory is closed.");
		}
		this.setSessionFactory(sessionFactory);
		logger.info("[Hibernate @ userImpl] - Received valid SessionFactory.");
	}
	
	public void setSessionFactory(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addUser(user u) {
		Session session = this.sessionFactory.openSession();
		logger.info("Going to add new user to database "+u.toString());
		Transaction tx = session.beginTransaction();
		//session.persist(u);
		session.save(u);
		tx.commit();
		session.close();
		logger.info("User saved successfully, user Details="+u.toString());
	}

	@Override
	public void updateUser(user u) {
		logger.info("Going to update user on database "+u.toString());
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		session.close();
		logger.info("User update successfully, user Details="+u.toString());

	}

	@Override
	public List<user> listUsers() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<user> usersList = session.createQuery("from user").list();
		for(user u : usersList){
			logger.info("user List::"+u.toString());
		}
		tx.commit();
		session.close();
		return usersList;
	}

	@Override
	public user getUserById(int user_id) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		user u = (user) session.load(user.class, new Integer(user_id));
		logger.info("User loaded successfully, User details="+u.toString());
		
		tx.commit();
		session.close();
		return u;
	}

	@Override
	public user getUserByName(String user_name) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		//user u = (user) session.load(user.class, new Integer(user_id));
		user u = (user) session.byNaturalId( user.class ).using("user_name",new String(user_name)).load();
				//.getReference();
		//logger.info("User loaded successfully, User details="+u.toString());
		tx.commit();
		session.close();
		return u;
	}

	@Override
	public void removeUser(int user_id) {
		logger.info("Going to delete user_id=" +user_id);
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		user u = (user) session.load(user.class, new Integer(user_id));
		if (null != u ) {
			logger.info("delete user "+ u.toString());
			session.delete(u);
		}

		//Transaction tx = session.beginTransaction();
		tx.commit();
		session.close();
		logger.info("User delete successfully, user_id="+user_id);
		
	}

	
} //userImpl
