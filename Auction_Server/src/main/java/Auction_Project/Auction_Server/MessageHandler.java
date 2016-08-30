package Auction_Project.Auction_Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

import Auction_Project.Auction_Server.hibernate.HibernateUtil;
import Auction_Project.Auction_Server.hibernate.Impl.userImpl;
import Auction_Project.Auction_Server.hibernate.model.user;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;



@Path("/") // Full path is: http://localhost:8080/Auction_Server/
@Produces(MediaType.APPLICATION_JSON)
public class MessageHandler 
{
	
	public static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
	private static HashMap<String, User> mapOfUsers = new HashMap<String, User>();
	private static HashMap<Integer, Item> mapOfItems = new HashMap<Integer, Item>();
	private static int numOfItems = 0;
	
	// |=================================================|
	// |               Server Root Function              |
	// |=================================================|
	
	@GET
    public Response openingMessage(@Context HttpServletRequest request)  // Opening message when entering the server
	{
		String userIP = request.getRemoteAddr();
		System.out.println("[User with IP: "+userIP+"] has entered the server lobby.");
		String message = "Welcome to the Auction Server!";
		
		User newUser = new User(0, "admin", "admin", "admin", "admin", "0000", "0000");
		mapOfUsers.put(newUser.getUserName(), newUser);
		
		return Response.status(200).entity(toJsonString(message)).build();
    }
	
	// |===========================================|
	// |               Register User               |
	// |===========================================|
	
	@POST
	@Path("/register") // http://localhost:8080/Auction_Server/register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User inputUser) 
	{
		User newUser = new User(inputUser);
		mapOfUsers.put(newUser.getUserName(), newUser);
		String message = "User "+newUser.getUserName()+" has been successfully registered.";
		System.out.println("["+inputUser.getUserName()+"] has successfully registered to the Auction Server.");
		return Response.status(201).entity(toJsonString(message)).build();
	}
	
	// |===============================================|
	// |               View User Profile               |
	// |===============================================|
	
	@GET 
    @Path("/users/{reqUser}") // Path = http://localhost:8080/Auction_Server/users/x/?username=x&password=x
    public Response userProfile(@PathParam("reqUser") int requestedUserName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			System.out.println("["+mapOfUsers.get(userName).getUserName()+"] -> User "+requestedUserName+" Profile");
			return Response.status(200).entity(toJsonString(mapOfUsers.get(requestedUserName))).build();
		}
		System.out.println("Failed getting user profile for user "+userName);
		String message = "Error: Getting profile failed because of bad authentication.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |============================================|
	// |               View all users               |
	// |============================================|
	
	@GET 
    @Path("/users") // Path = http://localhost:8080/Auction_Server/users/?username=x&password=x
    public Response getUsers(@QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			System.out.println("["+mapOfUsers.get(userName).getUserName()+"] -> View all users");
			return Response.status(200).entity(toJsonString(mapOfUsers.get(mapOfUsers))).build();
		}
		System.out.println("Failed viewing all users for user ID "+userName);
		String message = "Error: Getting all users failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |============================================|
	// |               View all users2               |
	// |============================================|
	
	@GET 
    @Path("/users2") // Path = http://localhost:8080/Auction_Server/users/?id=3&password=123abc
    public Response getUsers2(@QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{

			Gson gson = new Gson();
			String jsonMessage = gson.toJson(mapOfUsers);
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> View all users");
			return Response.status(200).entity(jsonMessage).build();
		}

		logger.info("get all users using hibernate method ");
		
		//helper h= new helper();
		//h.getSession();
		try {

			/*
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			DataSource ds = (DataSource)envContext.lookup("jdbc/MyLocalDB");
			Connection conn = ds.getConnection();
			*/
			
					
			//etc.
			
			//getNameInNamespace
			//Context initContext = new InitialContext();
			//InitialContext envContext  = initCtx.lookup("java:/comp/env");
			//SessionFactory sessionFactoryx = (SessionFactory) ((InitialContext) envContext).lookup("jdbc/MyLocalDB");

			//initCtx.lookup("java:comp/env/jdbc/MyLocalDB");
			//SessionFactory sessionFactoryx = (SessionFactory) initCtx.lookup("java:comp/env/jdbc/MyLocalDB");

			  //Context initContext = new InitialContext();
	          //  Context envContext  = (Context)initContext.lookup("java:/comp/env");
	          //  DataSource ds = (DataSource)envContext.lookup("jdbc/slingemp");
			
			logger.info("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");

			user u = new user();
			u.setfirst_name("Yuda");
			u.setlast_name("finkel");
			logger.info("user="+u.toString());
			
			SessionFactory sessionFactory=null;
		/*
		   	Configuration configuration = new Configuration();
		   	configuration.configure("hibernate.cfg.xml");
		   	logger.info("Hibernate Configuration created successfully");
		    	
		   	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		   	logger.info("ServiceRegistry created successfully");
		   	sessionFactory = configuration
					.buildSessionFactory(serviceRegistry);
		   	logger.info("SessionFactory created successfully");
	   	
		   	logger.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		   	
			logger.info(sessionFactory.getStatistics().toString());
			if (sessionFactory.isClosed() ) {
				logger.info("sessionFactory.isClosed()");
			}
		*/	
			logger.info("Hibernate Annotation Configuration loaded");
			//HibernateUtil HibernateUtil= new HibernateUtil();
			sessionFactory=HibernateUtil.getSessionAnnotationFactory();
			sessionFactory=HibernateUtil.getSessionAnnotationFactory();
			sessionFactory=HibernateUtil.getSessionAnnotationFactory();
			
			
			//Get Session
			Session session = sessionFactory.getCurrentSession();
			//start transaction
			logger.info("before save XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			session.beginTransaction();
			//Save the Model object
			user uu = new user();
			uu.setuser_name("ASDF");
			uu.setfirst_name("xxxx");
			uu.setlast_name("aaaa");
			session.save(uu);
			//Commit transaction
			session.getTransaction().commit();
			logger.info("after save XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

			
			// using Impl 
			logger.info("checking implement class ...");
			userImpl user_impl = new userImpl(sessionFactory);

			user u3 = new user();
			u3.setuser_name("user3");
			u3.setfirst_name("yuda3");
			u3.setlast_name("fin3");
			u3.setphone_number("050-3333333");
			u3.setemail("abc@333.com");
			user_impl.addUser(u3);
						
			u3.setfirst_name("Yuda_update");	
			user_impl.updateUser(u3);
			user_impl.removeUser(u3.getuser_id()-1);
			
			logger.info("get all users ...");
			List<user> userTable = user_impl.listUsers();
			logger.info("userTable="+ userTable.size());
			
			logger.info("getting user object by id ...");
			user u4 = user_impl.getUserById(u3.getuser_id());
			logger.info("userTable="+ userTable.size());
			
		}
		catch (Exception e) {
			logger.warning("error add user !!!");
			e.printStackTrace();
		}


		
		InfoMessage message = new InfoMessage(-1, "Error: Getting all users failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed viewing all users for user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
	
	// |=============================================|
	// |               View user items               |
	// |=============================================|
	
	@GET 
    @Path("/users/{reqId}/items") // Path = http://localhost:8080/Auction_Server/users/1/?id=3&password=123abc
    public Response viewUserItems(@PathParam("reqId") int requestedUserID, @QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			if( requestedUserID == userID )
			{
				// price
				// buyers info
			}
		}
		return Response.status(200).entity("view user items").build();
    }
	
	// |========================================|
	// |               Delete User              |
	// |========================================|
	
	// |====================================================|
	// |               View user item auctions              |
	// |====================================================|
	
	@GET 
    @Path("/users/{reqId}/items") // Path = http://localhost:8080/Auction_Server/users/1/?id=3&password=123abc
    public Response viewUserItems(@PathParam("reqId") int requestedUserID, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			//if( requestedUserID == userName )
			//{
				// price
				// buyers info
			//}
		}
		return Response.status(200).entity("view user items").build();
    }
	
	// |============================================|
	// |               View all Items               |
	// |============================================|
	
	@GET 
    @Path("/items/") // Path = http://localhost:8080/Auction_Server/items/?username=x&password=x
    public Response viewItems(@QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			System.out.println("["+mapOfUsers.get(userName).getUserName()+"] -> View Items");
			return Response.status(200).entity(toJsonString(mapOfItems)).build();
		}
		System.out.println("Failed getting items for user ID "+userName);
		String message = "Error: Getting items failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=======================================|
	// |               View item               |
	// |=======================================|
	
	@GET 
    @Path("/items/{id}") // Path = http://localhost:8080/Auction_Server/items/y/?username=x&password=x
    public Response viewItem(@PathParam("id") int itemID, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			System.out.println("["+mapOfUsers.get(userName).getUserName()+"] -> View Item "+itemID);
			return Response.status(200).entity(toJsonString(mapOfItems.get(itemID))).build();
		}
		System.out.println("Failed getting item "+itemID+" for user ID "+userName);
		String message = "Error: Getting item "+itemID+" failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=========================================|
	// |               Bid on item               |
	// |=========================================|
	
	@PUT 
    @Path("/items/{id}/bid") // Path = http://localhost:8080/Auction_Server/items/y/bid/?username=x&password=x
	@Consumes(MediaType.APPLICATION_JSON)
    public Response bidItem(int price, @PathParam("id") int itemID, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			mapOfItems.get(itemID).setItemLastBidPrice(price);
			String message = "Successfully bid on item "+itemID;
			System.out.println("["+mapOfUsers.get(userName).getUserName()+"] -> Bid Item "+itemID);
			return Response.status(200).entity(toJsonString(message)).build();
		}
		System.out.println("Failed bid on item "+itemID+" by user ID "+userName);
		String message = "Error: Bid on item "+itemID+" failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=====================================================|
	// |               Put item up for auction               |
	// |=====================================================|
	
	@POST 
    @Path("/items/add") // Path = http://localhost:8080/Auction_Server/items/add/?username=x&password=x
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(Item inputItem, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		if( isAuthentic(userName, password) )
		{
			Item newItem = new Item(inputItem);
			numOfItems++;
			newItem.setItemID(numOfItems);
			mapOfItems.put(numOfItems, newItem);
			String message = "Item has been successfully added.";
			System.out.println("["+mapOfUsers.get(userName).getUserName()+"] -> Add item "+inputItem.getItemName());
			return Response.status(200).entity(toJsonString(message)).build();
		}
		System.out.println("Failed to add item "+inputItem.getItemName()+" by user ID "+userName);
		String message = "Error: item add fail.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	/*	#####################################
	 *  ########## Class Functions ##########
	 *  #####################################
	 */ 
    
	private String toJsonString(Object object)
	{
		Gson gson = new Gson();
		String jsonString = gson.toJson(object);
		return jsonString;
	}
	
	private boolean isAuthentic(String userName, String password) 
	{
		if( mapOfUsers.containsKey(userName) )
		{
			if ( mapOfUsers.get(userName).getPassword().compareTo(password) == 0 )
			{
				return true;
			}
		}
		return false;
	}
	
}