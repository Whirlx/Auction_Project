package Auction_Project.Auction_Server.hibernate.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import Auction_Project.Auction_Server.hibernate.HibernateUtil;

@WebListener
public class HibernateSessionFactoryListener implements ServletContextListener {

	public final Logger logger = Logger.getLogger(HibernateSessionFactoryListener.class);

	public void contextInitialized(ServletContextEvent servletContextEvent) 
	{
	   	servletContextEvent.getServletContext().setAttribute("SessionFactory", HibernateUtil.getSessionAnnotationFactory());
	   	logger.info("Hibernate SessionFactory Configured successfully");
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
