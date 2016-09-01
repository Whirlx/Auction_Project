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
import javax.ws.rs.DELETE;
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
	private static HashMap<String, user> mapOfUsers = new HashMap<String, user>();
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
		return Response.status(200).entity(toJsonString(message)).build();
    }
	
	// |===========================================|
	// |               Register User               |
	// |===========================================|
	
	@POST
	@Path("/register") // http://localhost:8080/Auction_Server/register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(user inputUser) 
	{
		user newUser = new user(inputUser);
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user_impl.addUser(newUser);
		String message = "User "+newUser.getUserName()+" has been successfully registered.";
		System.out.println("["+inputUser.getUserName()+"] has successfully registered to the Auction Server.");
		return Response.status(201).entity(toJsonString(message)).build();
	}
	
	// |===============================================|
	// |               View User Profile               |
	// |===============================================|
	
	@GET 
    @Path("/users/{reqUser}") // Path = http://localhost:8080/Auction_Server/users/x/?username=x&password=x
    public Response userProfile(@PathParam("reqUser") String requestedUserName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			System.out.println("["+userToAuth.getUserName()+"] -> User "+requestedUserName+" Profile");
			return Response.status(200).entity(toJsonString(user_impl.getUserByName(requestedUserName))).build();
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
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			System.out.println("["+userToAuth.getUserName()+"] -> View all users");
			return Response.status(200).entity(toJsonString(user_impl.listUsers())).build();
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
    public Response getUsers2(@QueryParam("username") String userName, @QueryParam("password") String password) {

		logger.info("get all users using hibernate method ");
		
		//helper h= new helper();
		//h.getSession();
		try {

			logger.info("ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
			SessionFactory sessionFactory=null;

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
			uu.setUserName("ASDF");
			uu.setPassword("pwd");
			uu.setFirstName("xxxx");
			uu.setLastName("aaaa");
			session.save(uu);
			//Commit transaction
			session.getTransaction().commit();
			logger.info("after save XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

			
			// using Impl 
			logger.info("checking implement class ...");
			userImpl user_impl = new userImpl(sessionFactory);

			user u3 = new user();
			u3.setUserName("user3");
			u3.setPassword("pwd");
			u3.setFirstName("yuda3");
			u3.setLastName("fin3");
			u3.setPhoneNumber("050-3333333");
			u3.setEmail("abc@333.com");
			user_impl.addUser(u3);
						
			u3.setFirstName("Yuda_update");	
			user_impl.updateUser(u3);
			user_impl.removeUser(u3.getUserId()-1);
			
			logger.info("get all users ...");
			List<user> userTable = user_impl.listUsers();
			logger.info("userTable="+ userTable.size());
			
			logger.info("getting user object by id ...");
			user u4 = user_impl.getUserById(u3.getUserId());
			logger.info("userTable="+ userTable.size());
			
		}
		catch (Exception e) {
			logger.warning("error add user !!!");
			e.printStackTrace();
		}


		

		String message = "test";
		logger.warning("Failed viewing all users for user ID "+userName);
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |========================================|
	// |               Delete User              |
	// |========================================|
	
	@DELETE 
    @Path("/users/{reqUser}/delete") // Path = http://localhost:8080/Auction_Server/users/x/delete/?username=x&password=x
    public Response deleteUser(@PathParam("reqUser") String requestedUserName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			System.out.println("["+userToAuth.getUserName()+"] -> User "+requestedUserName+" Delete");
			user_impl.removeUser(user_impl.getUserByName(requestedUserName).getUserId());
			String message = "User "+requestedUserName+" has been successfully deleted.";
			return Response.status(200).entity(toJsonString(message)).build();
		}
		System.out.println("Failed deleting "+requestedUserName);
		String message = "Error: Deleting user failed because of bad authentication.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |====================================================|
	// |               View user item auctions              |
	// |====================================================|
	
	@GET 
    @Path("/users/{reqId}/items") // Path = http://localhost:8080/Auction_Server/users/1/?id=3&password=123abc
    public Response viewUserItems(@PathParam("reqId") int requestedUserID, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
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
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
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
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
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
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
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
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
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
	
	private boolean isAuthentic(user userToAuth, String password) 
	{
		if( userToAuth != null )
		{
			if ( userToAuth.getPassword().compareTo(password) == 0 )
			{
				return true;
			}
		}
		return false;
	}
	
}
	

	