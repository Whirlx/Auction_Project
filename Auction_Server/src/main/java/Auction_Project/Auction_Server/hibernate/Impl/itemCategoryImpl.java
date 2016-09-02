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

	public itemCategoryImpl(SessionFactory sf)
	{
		if (sf !=null)
		{
			this.setSessionFactory(sf);
			logger.info("hibernate session factory was set on itemImpl");
			logger.info(sf.getStatistics().toString());
			if (sf.isClosed() ) 
			{
				logger.info("sf.isClosed()");
			}
			else 
			{
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
	public void addItemCategory(itemCategory itemCategory) {
		logger.info("Going to add new item to database "+itemCategory.toString());
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		//session.persist(u);
		session.save(itemCategory);
		tx.commit();
		logger.info("Item saved successfully, item Details="+itemCategory.toString());
	}

	@Override
	public void updateItemCategory(itemCategory itemCategory) {
		logger.info("Going to update item on database "+itemCategory.toString());
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		session.update(itemCategory);
		tx.commit();
		logger.info("Item update successfully, user Details="+itemCategory.toString());

	}

	@Override
	public List<itemCategory> listItemCategories() {
		System.out.println("111");
		Session session = this.sessionFactory.getCurrentSession();
		System.out.println("222");
		Transaction tx = session.beginTransaction();
		System.out.println("333");
		List<itemCategory> itemsList = session.createQuery("from itemCategory").list();
		System.out.println("444");
		for(itemCategory u : itemsList)
		{
			logger.info("item List::"+u.toString());
		}
		tx.commit();
		return itemsList;
	}

	@Override
	public itemCategory getItemCategoryById(int item_category_id) {
		Session session = this.sessionFactory.getCurrentSession();		
		Transaction tx = session.beginTransaction();
		itemCategory itemCategory = (itemCategory) session.load(itemCategory.class, new Integer(item_category_id));
		logger.info("Item loaded successfully, Item details="+itemCategory.toString());
		tx.commit();
		return itemCategory;
	}

	@Override
	public itemCategory getItemCategoryByName(String item_category_name) {
		Session session = this.sessionFactory.getCurrentSession();		
		Transaction tx = session.beginTransaction();
		//user u = (user) session.load(user.class, new Integer(user_id));
		itemCategory item = (itemCategory) session.byNaturalId( itemCategory.class ).using("item_category_name",new String(item_category_name)).load();
				//.getReference();
		logger.info("Item loaded successfully, Item details="+item.toString());
		tx.commit();
		return item;
	}

	@Override
	public void removeItemCategory(int item_category_id) {
		logger.info("Going to delete item_id=" +item_category_id);
		Session session = this.sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();
		itemCategory itemCategory = (itemCategory) session.load(itemCategory.class, new Integer(item_category_id));
		if (null != itemCategory ) {
			logger.info("delete item "+ itemCategory.toString());
			session.delete(itemCategory);
		}
		//Transaction tx = session.beginTransaction();
		tx.commit();
		logger.info("Item delete successfully, item_id="+item_category_id);
	}

	
} //itemImpl
