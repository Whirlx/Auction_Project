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

	public userImpl(SessionFactory sf)
	{
		if (sf !=null)
		{
			this.setSessionFactory(sf);
			logger.info("hibernate session factory was set on userImpl");
			logger.info(sf.getStatistics().toString());
			if (sf.isClosed() ) {
				logger.info("sf.isClosed()");
			}
			else {
			sf.openSession();
			logger.info(sf.getStatistics().toString());
				
			}
		}
		else
		{
			logger.info("hibernate session factory need to be initail with contructor ");
		}
		
	}
	
	public void setSessionFactory(SessionFactory sf){
		this.sessionFactory = sf;
	}

	@Override
	public void addUser(user u) {
		// TODO Auto-generated method stub
		logger.info("Going to add new user to database "+u.toString());
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		//session.persist(u);
		session.save(u);
		tx.commit();
		logger.info("User saved successfully, user Details="+u.toString());
	}

	@Override
	public void updateUser(user u) {
		// TODO Auto-generated method stub
		logger.info("Going to update user on database "+u.toString());
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		logger.info("User update successfully, user Details="+u.toString());

	}

	@Override
	public List<user> listUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		List<user> usersList = session.createQuery("from user").list();
		for(user u : usersList){
			logger.info("user List::"+u.toString());
		}
		tx.commit();
		return usersList;
	}

	@Override
	public user getUserById(int user_id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Transaction tx = session.beginTransaction();
		user u = (user) session.load(user.class, new Integer(user_id));
		logger.info("User loaded successfully, User details="+u.toString());
		
		tx.commit();
		return u;
	}
	
	@Override
		Session session = this.sessionFactory.getCurrentSession();		
		Transaction tx = session.beginTransaction();
		user u = (user) session.load(user.class, new String(user_name));
		logger.info("User loaded successfully, User details="+u.toString());
		
		tx.commit();
		return u;
	}

	@Override
	public user getUserByName(String user_name) {
		// TODO Auto-generated method stub
		Session session = this.sessionFactory.getCurrentSession();		
		Transaction tx = session.beginTransaction();
		//user u = (user) session.load(user.class, new Integer(user_id));
		user u = (user) session.byNaturalId( user.class ).using("user_name",new String(user_name)).load();
				//.getReference();
		
		
		logger.info("User loaded successfully, User details="+u.toString());
		
		
		
		
		
		tx.commit();
		return u;
	}

	@Override
	public void removeUser(int user_id) {
		logger.info("Going to delete user_id=" +user_id);
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		user u = (user) session.load(user.class, new Integer(user_id));
		if (null != u ) {
			logger.info("delete user "+ u.toString());
			session.delete(u);
		}

		//Transaction tx = session.beginTransaction();
		tx.commit();
		logger.info("User delete successfully, user_id="+user_id);
		
	}

	
} //userImpl
