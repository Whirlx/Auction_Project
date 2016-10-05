package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Auction_Project.Auction_Server.hibernate.model.user;

public class userImpl implements userInterface 
{
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
		Transaction tx = session.beginTransaction();
		session.save(u);
		tx.commit();
		session.close();
		logger.info("[Hibernate @ userImpl] - User saved successfully.");
	}

	@Override
	public void updateUser(user u) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		session.close();
		logger.info("[Hibernate @ userImpl] - User updated successfully.");
	}

	@Override
	public List<user> listUsers() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<user> usersList = session.createQuery("from user").list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ userImpl] - Received user list successfully.");
		return usersList;
	}

	@Override
	public user getUserById(int user_id) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		user u = (user) session.load(user.class, new Integer(user_id));
		tx.commit();
		session.close();
		logger.info("[Hibernate @ userImpl] - User by id loaded successfully.");
		return u;
	}

	@Override
	public user getUserByName(String user_name) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		user u = (user) session.byNaturalId( user.class ).using("user_name",new String(user_name)).load();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ userImpl] - User by name loaded successfully.");
		return u;
	}

	@Override
	public void removeUser(int user_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		user u = (user) session.load(user.class, new Integer(user_id));
		if (null != u ) {
			session.delete(u);
		}
		tx.commit();
		session.close();
		logger.info("[Hibernate @ userImpl] - User deleted successfully.");
	}

}
