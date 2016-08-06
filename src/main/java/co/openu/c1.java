package co.openu;
import java.util.List; 
import java.util.Date;
import java.util.Iterator; 

import org.apache.log4j.Logger;



/*
import javax.persistence.Entity;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
*/
public class c1 {
	final static Logger logger = Logger.getLogger(c1.class);
	
	void c1()
	{
	logger.info("This is info : " + this.getClass().getName());
	logger.debug("This is debug: " + this.getClass().getName());
	
		
	}
	
}
