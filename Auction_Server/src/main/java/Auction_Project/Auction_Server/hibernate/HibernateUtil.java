package Auction_Project.Auction_Server.hibernate;

import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import Auction_Project.Auction_Server.hibernate.model.*;


public class HibernateUtil {
	public static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());
	
	//Annotation based configuration
	private static SessionFactory sessionAnnotationFactory;


/********************************************
 * create new hibernate session factory objects
 * 
/********************************************/

    private static SessionFactory buildSessionAnnotationFactory() {
    	try {
    		
    		logger.info("initial Hibernate sessionFactory ");
    		
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuration = new Configuration();
        	configuration.configure("hibernate.cfg.xml");
        	logger.info("Hibernate Annotation Configuration loaded");
        	logger.info("configuration" + configuration.toString());
        	
        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        	logger.info("Hibernate Annotation serviceRegistry created");
        	
        	SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        	logger.info("Hibernate sessionfactory created");
        	logger.info(sessionFactory.getStatistics().toString());
        	return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
	}
	

    public static SessionFactory getSessionAnnotationFactory() {
		if(sessionAnnotationFactory == null) {
			logger.info("recreate SessionFactory !!! ");
			sessionAnnotationFactory = buildSessionAnnotationFactory();
		}
		else{
			logger.info("SessionFactory already exists !!! ");

		}
		
		return sessionAnnotationFactory;
    }
	
	
	
	public int getSession () {
		
		try {
			
			//SessionFactory sessionFactory = (SessionFactory) request.getServletContext().getAttribute("SessionFactory");
			
			logger.info("trying to get hibernate session manager from JNDI ...");
			Context initCtx = new InitialContext();
			SessionFactory sessionFactory = (SessionFactory) initCtx.lookup("java:comp/env/jdbc/MyLocalDB");
			
			//Context envCtx = (Context) initCtx.lookup("java:comp/env/jdbc/MyLocalDB");
			//SessionFactory sessionFactory = (SessionFactory) envCtx.lookup("java:jdbc/MyLocalDB");

			
			//Session session = sessionFactory.getCurrentSession();
			//Transaction tx = session.beginTransaction();
			//Employee1 emp1 = (Employee1) session.get(Employee1.class,15);
			//tx.commit();
			
					
			return 0;
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.warning(" error when trying to get hibernate session manager fron JNDI ...");
			e.printStackTrace();
		}
		//DataSource ds = (DataSource)
		 // envCtx.lookup("jdbc/EmployeeDB");
		
		
		
		return 0;
		
	}


	
	

}
