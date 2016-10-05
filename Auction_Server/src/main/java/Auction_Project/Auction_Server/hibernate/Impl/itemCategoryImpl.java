package Auction_Project.Auction_Server.hibernate.Impl;

import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import Auction_Project.Auction_Server.hibernate.model.itemCategory;

public class itemCategoryImpl implements itemCategoryInterface 
{
	public static final Logger logger = Logger.getLogger(userImpl.class.getName());
	private SessionFactory sessionFactory;

	public itemCategoryImpl(SessionFactory sessionFactory)
	{
		if( sessionFactory == null )
		{
			logger.warning("[Hibernate @ itemCategoryImpl] - Error, received null SessionFactory.");
		}
		if( sessionFactory.isClosed() )
		{
			logger.warning("[Hibernate @ itemCategoryImpl] - Error, SessionFactory is closed.");
		}
		this.setSessionFactory(sessionFactory);
		logger.info("[Hibernate @ itemCategoryImpl] - Received valid SessionFactory.");
	}
	
	public void setSessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void addItemCategory(itemCategory itemCategory) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(itemCategory);
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemCategoryImpl] - Item category saved successfully.");
	}

	@Override
	public void updateItemCategory(itemCategory itemCategory) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(itemCategory);
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemCategoryImpl] - Item category updated successfully.");
	}

	@Override
	public List<itemCategory> listItemCategories() {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		@SuppressWarnings("unchecked")
		List<itemCategory> itemsList = session.createQuery("from itemCategory").list();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemCategoryImpl] - Received item category list successfully.");
		return itemsList;
	}

	@Override
	public itemCategory getItemCategoryById(int item_category_id) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		itemCategory itemCategory = (itemCategory) session.load(itemCategory.class, new Integer(item_category_id));
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemCategoryImpl] - Received item category by id successfully.");
		return itemCategory;
	}

	@Override
	public itemCategory getItemCategoryByName(String item_category_name) {
		Session session = this.sessionFactory.openSession();	
		Transaction tx = session.beginTransaction();
		itemCategory item = (itemCategory) session.byNaturalId( itemCategory.class ).using("item_category_name",new String(item_category_name)).load();
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemCategoryImpl] - Received item category by name successfully.");
		return item;
	}

	@Override
	public void removeItemCategory(int item_category_id) {
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		itemCategory itemCategory = (itemCategory) session.load(itemCategory.class, new Integer(item_category_id));
		if (null != itemCategory ) {
			session.delete(itemCategory);
		}
		tx.commit();
		session.close();
		logger.info("[Hibernate @ itemCategoryImpl] - Item category deleted successfully.");
	}
	
}
