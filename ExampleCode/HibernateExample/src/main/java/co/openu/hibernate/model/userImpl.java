package co.openu.hibernate.model;

import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import co.openu.hibernate.model.User;

public class userImpl implements UserInterface {
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
	public void addUser(User u) {
		
		logger.info("Going to add new user to database "+u.toString());
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		//session.persist(u);
		session.save(u);
		tx.commit();
		logger.info("User saved successfully, user Details="+u.toString());
	}

	
	@Override
	public void updateUser(User u) {
		logger.info("Going to update user on database "+u.toString());
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(u);
		tx.commit();
		logger.info("User update successfully, user Details="+u.toString());

	}

	@Override
	public List<User> listUsers() {
		
		Session session = this.sessionFactory.getCurrentSession();
		List<User> usersList = session.createQuery("from User").list();
		for(User u : usersList){
			logger.info("User List::"+u.toString());
		}
		return usersList;
	}

	@Override
	public User getUserById(int id) {

		Session session = this.sessionFactory.getCurrentSession();		
		User u = (User) session.load(User.class, new Integer(id));
		logger.info("User loaded successfully, User details="+u.toString());
		return u;
	}

	@Override
	public void removeUser(int user_id) {
		logger.info("Going to delete user_id=" +user_id);
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		User u = (User) session.load(User.class, new Integer(user_id));
		if (null != u ) {
			logger.info("delete user "+ u.toString());
			session.delete(u);
		}

		//Transaction tx = session.beginTransaction();
		tx.commit();
		logger.info("User delete successfully, user_id="+user_id);
		
	}

}
