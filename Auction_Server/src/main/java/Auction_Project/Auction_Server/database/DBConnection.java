package Auction_Project.Auction_Server.database;
//http://www.programcreek.com/java-api-examples/index.php?source_dir=jfinal-admin-gen-master/src/main/java/com/mbaobao/gen/db/DBConnection.java
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.SQLException; 
import java.sql.Statement; 
 

import org.apache.tomcat.jdbc.pool.DataSource; 
import org.apache.tomcat.jdbc.pool.PoolProperties; 
 

public class DBConnection { 

	 private DataSource dataSource; 
	  
	 public DBConnection(JdbcConfiguration configuration) { 
	  PoolProperties p =  new  PoolProperties (); 
	  p.setDriverClassName(configuration.getDriverClass()); 
	  p.setUrl(configuration.getUrl()); 
	  p.setUsername(configuration.getUsername()); 
	  p.setPassword(configuration.getPassword()); 
	  p.setJmxEnabled(true); 
	  p.setTestWhileIdle ( false ); 
	  p.setTestOnBorrow ( true ); 
	  p.setValidationQuery("SELECT 1"); 
	  p.setTestOnReturn ( false ); 
	  p.setValidationInterval(30000); 
	  p.setTimeBetweenEvictionRunsMillis ( 30000 ); 
	  p.setMaxActive(100); 
	  p.setInitialSize( 10 ); 
	  p.setMaxWait(10000); 
	  p.setRemoveAbandonedTimeout(60); 
	  p.setMinEvictableIdleTimeMillis(30000); 
	  p.setMinIdle(10); 
	  p.setLogAbandoned(true); 
	  p.setRemoveAbandoned(true); 
	  p.setJdbcInterceptors("org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;" 
	        + "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer"); 
	   
	  this.dataSource = new DataSource(); 
	  this.dataSource.setPoolProperties(p); 
	   
	 } 
	 
	 public DataSource getDataSource() { 
		  return this.dataSource; 
	 } 

	 public static Connection getConnection(JdbcConfiguration configuration) { 
		  try { 
		   Connection con = new DBConnection(configuration).getDataSource().getConnection(); 
		   return  con; 
		  } 
		  catch (SQLException e) { 
		  } 
		  throw  new  RuntimeException ( "Unable to obtain the data source connection" ); 
	} 
	
	 
	 /**
	  * Release resource database. 
	  *  
	  * @param conn 
	  * Database Connection 
	  * @param stat 
	  *         {@link} The Statement objects 
	  * @param rs 
	  *         {@link} The ResultSet Object 
	  */ 
	 public static void closeConnection(Connection conn, Statement stat, ResultSet rs) 
	 { 
	 
		 try { 
			   // Close the result set objects. 
			   if (rs != null && !rs.isClosed()) { 
			    rs.close(); 
			    rs = null; 
			   } 
			  } catch (SQLException e) { 
			   // When closed fails, record error message and report the upper code. 
			   throw new RuntimeException(e); 
			  } finally { 
			   try { 
			    // Close Statement object. 
			    if (stat != null && !stat.isClosed()) { 
			     stat.close(); 
			     stat = null; 
			    } 
			   } catch (SQLException e) { 
			    // When closed fails, record error message and report the upper code. 
			    throw new RuntimeException(e); 
			   } finally { 
			    try { 
			     // Close the database connection. 
			     if (conn != null && !conn.isClosed()) { 
			      conn.close(); 
			      conn = null; 
			     } 
			    } catch (SQLException e) { 
			     // When closed fails, record error message and report the upper code. 
			     throw new RuntimeException(e); 
			    } 
			   } 
			  } 		 
		 
	 }
	 
	 
} //DBConnection





/**
 * 
	//DB connection pool
			{
			logger.info("createing connection pool ...");
			PoolProperties pp = new PoolProperties();
			   pp.setDriverClassName("com.mysql.jdbc.Driver");
			   pp.setUrl("jdbc:mysql://localhost:3306/auction_db");
			   pp.setUsername("auction_user");
			   pp.setPassword("auction_user_pw");
			   DataSource ds = new DataSource(pp);
				
			   Connection conn = null;
			   Statement stmt = null;  // Or PreparedStatement if needed
			   ResultSet rs = null;
				   
			   conn = ds.getConnection();
			   stmt = conn.createStatement();
			   stmt.execute("update users set phone_number='000000'");

				   
			   rs = stmt.executeQuery("select * from users");
			   logger.info("complete rows update");
				
			}
			   



 * /
 */