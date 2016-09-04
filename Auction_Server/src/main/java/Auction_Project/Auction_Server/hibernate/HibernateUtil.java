package Auction_Project.Auction_Server.hibernate;

import java.util.logging.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


public class HibernateUtil {
	public static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());
	
	//Annotation based configuration
	private static SessionFactory sessionAnnotationFactory;
	private static String HIBERNATE_CONFIG_FILE="hibernate.cfg.xml";

/********************************************
 * create new hibernate session factory objects
 * 
/********************************************/

    private static SessionFactory buildSessionAnnotationFactory()
    {
    	try 
    	{
    		logger.info("initial Hibernate sessionFactory ");
            // Create the SessionFactory from hibernate.cfg.xml
    		logger.info("hibernate configuration file -->" +HIBERNATE_CONFIG_FILE);
        	Configuration configuration = new Configuration();
        	//configuration.configure("hibernate.cfg.xml");
        	configuration.configure(HIBERNATE_CONFIG_FILE);
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
	
}
