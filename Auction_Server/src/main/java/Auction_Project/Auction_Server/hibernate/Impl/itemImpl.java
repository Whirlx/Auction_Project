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
		if( sessionFactory == null )
		{
			logger.warning("[Hibernate @ itemImpl] - Error, received null SessionFactory.");
		}
		if( sessionFactory.isClosed() )
		{
			logger.warning("[Hibernate @ itemImpl] - Error, SessionFactory is closed.");
		}
		this.setSessionFactory(sessionFactory);
		logger.info("[Hibernate @ itemImpl] - Received valid SessionFactory.");
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addItem(item item) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(item);
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Item saved successfully.");
	}

	@Override
	public void updateItem(item item) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(item);
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Item update successfully.");
	}

	@Override
	public List<item> listItems() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<item> itemsList = session.createQuery("FROM item i ORDER BY i.item_category DESC").list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Received item list successfully.");
		return itemsList;
	}
	
	@Override
	public List<item> listItemsForUserId(int user_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<item> itemsList = session.createQuery("from item where item_user_id = "+user_id).list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Received item list for user id successfully.");
		return itemsList;
	}
	
	@Override
	public List<item> listItemsByCategoryName(String category_name) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<item> itemsList = session.createQuery("from item where item_category = '"+category_name+"'").list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Received item list by category successfully.");
		return itemsList;
	}

	@Override
	public item getItemById(int item_id) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		item item = (item) session.load(item.class, new Integer(item_id));
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Item by id loaded successfully.");
		return item;
	}

	@Override
	public item getItemByName(String item_name) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		item item = (item) session.byNaturalId( item.class ).using("item_name",new String(item_name)).load();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Item by name loaded successfully.");
		return item;
	}

	@Override
	public void removeItem(int item_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		item item = (item) session.load(item.class, new Integer(item_id));
		if (null != item ) {
			session.delete(item);
		}
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemImpl] - Item deleted successfully.");
	}

} 
