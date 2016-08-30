package Auction_Project.Auction_Server.hibernate.listener;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jboss.logging.Logger;

import Auction_Project.Auction_Server.hibernate.HibernateUtil;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

	public final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) {

		HibernateUtil HibernateUtil= new HibernateUtil();
	   	servletContextEvent.getServletContext().setAttribute("SessionFactory", HibernateUtil.getSessionAnnotationFactory());
	   	logger.info("Hibernate SessionFactory Configured successfully");

	   	
		
		/*
		 Configuration configuration = new Configuration();
		 configuration.configure("hibernate.cfg.xml");
	   	logger.info("Hibernate Configuration created successfully");
	   	logger.info(configuration.toString());
	   	
	   	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	   	logger.info("ServiceRegistry created successfully");
	   	SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);
	   	logger.info("SessionFactory created successfully");
   	
	   	logger.debug("debug:" + sessionFactory);
	   	logger.debug("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	   	
	   	servletContextEvent.getServletContext().setAttribute("SessionFactory", sessionFactory);
	   	logger.info("Hibernate SessionFactory Configured successfully");
	*/
	}

	
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
	   	SessionFactory sessionFactory = (SessionFactory) servletContextEvent.getServletContext().getAttribute("SessionFactory");
	   	if(sessionFactory != null && !sessionFactory.isClosed()){
	   		logger.info("Closing sessionFactory");
	   		sessionFactory.close();
	   	}
	   	logger.info("Released Hibernate sessionFactory resource");
	}

	
}
