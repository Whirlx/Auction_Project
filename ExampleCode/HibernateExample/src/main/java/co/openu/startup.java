package co.openu;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.jboss.logging.Logger;

//import co.openu.hibernate.model.Employee;
//import co.openu.hibernate.model.Employee1;
import co.openu.hibernate.model.*;
import co.openu.hibernate.util.HibernateUtil;

public class startup {
	public static final Logger logger = Logger.getLogger(startup.class.getName());

	
	public static void main(String[] args) {
//		org.apache.log4j.BasicConfigurator.configure();
		logger.info("start hibernate test");
		
		// with mapping file 
		//hb1();
		
		// with JPA annotations
		hb_annotaions();
		
		
		System.exit(0);
		
		// HibernateJavaConfigMain 
		{

			//Get Session
			Session session = HibernateUtil.getSessionJavaConfigFactory().getCurrentSession();
			session = HibernateUtil.getSessionJavaConfigFactory().openSession();
			
			 List User = session.createQuery("FROM Users").list();
	         for (Iterator iterator = 
	                           User.iterator(); iterator.hasNext();){
	            User u = (User) iterator.next(); 
	            logger.info( u.toString() );
	         }
	        session.getTransaction().commit();
		    
			if  ( ! HibernateUtil.getSessionJavaConfigFactory().isClosed() ) {
				//terminate session factory, otherwise program won't end
				logger.info("Closing sessionFactory...");
				HibernateUtil.getSessionJavaConfigFactory().close();
			}
			else {
				logger.info("sessionFactory is already closed");
			}
			
		} //
		
		
		{
		    logger.info( "***************************");
			SessionFactory sessionFactory = HibernateUtil.getSessionJavaConfigFactory();
			Session session = (Session) sessionFactory.openStatelessSession();
			//start transaction
			session.beginTransaction();

					
			List result = session.createQuery( "select * from users" ).list();
			
			
			for ( User  u : (List<User>) result ) {
			    logger.info(u.toString());
			}
		    logger.info( "***************************");
		
		    session.getTransaction().commit();
		    session.close();
		}
	}

	
	
	
	public static void hb1() {
		
		Session session;
		
		User u1 = new User();
		
		u1.setuser_name("user1");
		u1.setfirst_name("ASDF");
		u1.setlast_name("YYY");
		u1.setphone_number("111111111");
		u1.setemail("aaa@aaa.com");
		//u1.setlast_login_time(last_login_time);
		
		//Get Session
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session = HibernateUtil.getSessionFactory().openSession();
		
		//start transaction
		session.beginTransaction();
		//Save the Model object
		Serializable id1 = session.save(u1);
		logger.info("return code from insert is "+ id1 );
		logger.info("User ID="+u1.getuser_id());
		logger.info(u1.toString());
		
		//Commit transaction
		session.getTransaction().commit();
		session.close();
		logger.info("User ID="+u1.getuser_id());
		u1=null;
		
		
		logger.info("Now going to update new row ...");
		//Get Session
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		session = HibernateUtil.getSessionFactory().openSession();

		//start transaction
		session.beginTransaction();
		//Serializable i=1;
		u1=new User();
		u1=(User) session.get(u1.getClass(),id1);
		logger.info(u1.toString());
		u1.setphone_number("999999999");
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		u1.setlast_login_time(timestamp);
		u1.setupdate_time(new Date());		
		
		logger.info(u1.toString());

		session.merge(u1);
	
		//Commit transaction
		session.getTransaction().commit();
		logger.info("User ID="+u1.getuser_id());
		
		
		//terminate session factory, otherwise program won't end
		HibernateUtil.getSessionFactory().close();		
			
	}
	

	// with JPA annotations	
	public static void hb_annotaions() {
		
		Session session;
		
		User u1 = new User();
		
		u1.setuser_name("user2");
		u1.setfirst_name("ASD");
		u1.setlast_name("AQW");
		u1.setphone_number("22222222");
		u1.setemail("abc@bb.com");
		//u1.setlast_login_time(last_login_time);
		
		//Get Session
		session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		session.close();
		session = HibernateUtil.getSessionAnnotationFactory().openSession();
		
		//start transaction
		session.beginTransaction();
		//Save the Model object
		Serializable id1 = session.save(u1);
		logger.info("return code from insert is "+id1);
		logger.info("User ID="+u1.getuser_id());
		logger.info(u1.toString());
		
		//Commit transaction
		session.getTransaction().commit();
		session.close();
		logger.info("User ID="+u1.getuser_id());
		u1=null;
		
		
		logger.info("Now going to update new row ...");
		//Get Session
		session = HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
		session.close();
		session = HibernateUtil.getSessionAnnotationFactory().openSession();

		//start transaction
		session.beginTransaction();
		//Serializable i=1;
		u1=new User();
		u1=(User) session.get(u1.getClass(),id1);
		logger.info(u1.toString());
		u1.setphone_number("999999999");
		java.util.Date date = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
		u1.setlast_login_time(timestamp);
		logger.info(u1.toString());

		session.merge(u1);
	
		//Commit transaction
		session.getTransaction().commit();
		logger.info("User ID="+u1.getuser_id());
		
		
		session = HibernateUtil.getSessionAnnotationFactory().openSession();
		session.beginTransaction();
		 List User = session.createQuery("FROM User").list();
         for (Iterator iterator = 
                           User.iterator(); iterator.hasNext();){
            User u = (User) iterator.next(); 
            logger.info( u.toString() );
         }
        session.getTransaction().commit();
        
        
		// test 2 sessions
		Session session2= HibernateUtil.getSessionAnnotationFactory().getCurrentSession();
        
		User u2 = new User();
			u2.setuser_name("user2");
			u2.setfirst_name("ASD");
			u2.setlast_name("AQW");
			u2.setphone_number("22222222");
			u2.setemail("abc@bb.com");

		//start transaction
		session2.beginTransaction();
		session2.persist(u2);
		
		u2.setfirst_name("QQQQQQQQQQQ");
		session2.flush();
		session2.getTransaction().commit();
		

		
		// using Impl 
		logger.info("checking implement class ...");
		userImpl user_impl = new userImpl(HibernateUtil.getSessionAnnotationFactory());

		User u3 = new User();
		u3.setuser_name("user3");
		u3.setfirst_name("ASD");
		u3.setlast_name("AQW");
		u3.setphone_number("224444222222");
		u3.setemail("abc@bb.com");
		user_impl.addUser(u3);
		
		
		u3.setfirst_name("Yuda");	
		user_impl.updateUser(u3);
		user_impl.removeUser(u3.getuser_id()-1);
		
		
		logger.info(HibernateUtil.getSessionAnnotationFactory().getStatistics().toString());
		//terminate session factory, otherwise program won't end
		HibernateUtil.getSessionAnnotationFactory().close();		
			
	}
	

	
}
