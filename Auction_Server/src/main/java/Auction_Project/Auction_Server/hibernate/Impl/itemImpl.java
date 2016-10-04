package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import Auction_Project.Auction_Server.hibernate.model.item;

public class itemImpl implements itemInterface 
{
	public static final Logger logger = Logger.getLogger(userImpl.class.getName());
	private SessionFactory sessionFactory;

	public itemImpl(SessionFactory sessionFactory)
	{
		if (sessionFactory !=null)
		{
			this.setSessionFactory(sessionFactory);
			logger.info("hibernate session factory was set on itemImpl");
			logger.info(sessionFactory.getStatistics().toString());
			if (sessionFactory.isClosed() ) 
			{
				logger.info("sessionFactory.isClosed()");
			}
			else 
			{
			//sessionFactory.openSession();
			logger.info(sessionFactory.getStatistics().toString());
			}
		}
		else
		{
			logger.info("hibernate session factory need to be initail with contructor ");
		}
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addItem(item item) {
		logger.info("Going to add new item to database "+item.toString());
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		//session.persist(u);
		session.save(item);
		tx.commit();
		session.close();
		logger.info("Item saved successfully, item Details="+item.toString());
	}

	@Override
	public void updateItem(item item) {
		logger.info("Going to update item on database "+item.toString());
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		session.close();
		logger.info("Item update successfully, user Details="+item.toString());

	}

	@Override
	public List<item> listItems() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<item> itemsList = session.createQuery("FROM item i ORDER BY i.item_category DESC").list();
		for(item u : itemsList)
		{
			logger.info("item List::"+u.toString());
		}
		tx.commit();
		session.close();
		return itemsList;
	}
	
	@Override
	public List<item> listItemsForUserId(int user_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<item> itemsList = session.createQuery("from item where item_user_id = "+user_id).list();
		for(item u : itemsList)
		{
			logger.info("item List::"+u.toString());
		}
		tx.commit();
		session.close();
		return itemsList;
	}
	
	@Override
	public List<item> listItemsByCategoryName(String category_name) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<item> itemsList = session.createQuery("from item where item_category = '"+category_name+"'").list();
		tx.commit();
		session.close();
		return itemsList;
	}

	@Override
	public item getItemById(int item_id) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		item item = (item) session.load(item.class, new Integer(item_id));
		logger.info("Item loaded successfully, Item details="+item.toString());
		tx.commit();
		session.close();
		return item;
	}

	@Override
	public item getItemByName(String item_name) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		//user u = (user) session.load(user.class, new Integer(user_id));
		item item = (item) session.byNaturalId( item.class ).using("item_name",new String(item_name)).load();
				//.getReference();
		//logger.info("Item loaded successfully, Item details="+item.toString());
		tx.commit();
		session.close();
		return item;
	}

	@Override
	public void removeItem(int item_id) {
		logger.info("Going to delete item_id=" +item_id);
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		item item = (item) session.load(item.class, new Integer(item_id));
		if (null != item ) {
			logger.info("delete item "+ item.toString());
			session.delete(item);
		}
		//Transaction tx = session.beginTransaction();
		tx.commit();
		session.close();
		logger.info("Item delete successfully, item_id="+item_id);
	}

	
} //itemImpl
