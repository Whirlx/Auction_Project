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
import com.google.gson.GsonBuilder;

import Auction_Project.Auction_Server.hibernate.HibernateUtil;
import Auction_Project.Auction_Server.hibernate.Impl.itemCategoryImpl;
import Auction_Project.Auction_Server.hibernate.Impl.itemImpl;
import Auction_Project.Auction_Server.hibernate.Impl.userImpl;
import Auction_Project.Auction_Server.hibernate.model.user;
import Auction_Project.Auction_Server.hibernate.model.item;
import Auction_Project.Auction_Server.hibernate.model.itemCategory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import sun.misc.BASE64Decoder;

@Path("/") // Full path is: http://localhost:8080/Auction_Server/
@Produces(MediaType.APPLICATION_JSON)
public class MessageHandler 
{
	public static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
	
	@Context
	private HttpServletRequest request;
	
	// |=================================================|
	// |               Server Root Function              |
	// |=================================================|
	
	@GET
    public Response openingMessage()  // Opening message when entering the server
	{
		String userIP = request.getRemoteAddr();
		logger.info("[Guest @ "+userIP+"] has entered the server lobby.");
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
		String userIP = request.getRemoteAddr();
		user newUser = new user(inputUser);
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		String message;
		if( user_impl.getUserByName(newUser.getUserName()) == null)
		{
			user_impl.addUser(newUser);
			message = "["+newUser.getUserName()+" @ "+userIP+"]->[Register]: Success.";
			logger.info(message);
		}
		else
		{
			message = "["+newUser.getUserName()+" @ "+userIP+"]->[Register]: Failure, username is already taken.";
			logger.info(message);
		}
		return Response.status(200).entity(toJsonString(message)).build();
	}
	
	// |===============================================|
	// |               View User Profile               |
	// |===============================================|
	
	@GET 
    @Path("/users/{reqUser}") // Path = http://localhost:8080/Auction_Server/users/Admin
    public Response userProfile(@PathParam("reqUser") String requestedUserName) 
	{
		String userIP = request.getRemoteAddr();
		String message;
		String decodedHeader = decodeHeader();
		if(decodedHeader == null)
		{
			message = "[? @ "+userIP+"]->[View User Profile - "+requestedUserName+"]: Failure, invalid authentication header.";
			logger.warning(message);
			return Response.status(400).entity(toJsonString(message)).build();
		}
		String userName = decodedHeader.substring(0, decodedHeader.indexOf(":"));
		String password = decodedHeader.substring(decodedHeader.indexOf(":")+1);
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			user requestedUser = user_impl.getUserByName(requestedUserName);
			if(requestedUser != null)
			{
				message = "["+userName+" @ "+userIP+"]->[View User Profile - "+requestedUserName+"]: Success.";
				logger.info(message);
				return Response.status(200).entity(toJsonString(requestedUser)).build(); // Success
			}
			else
			{
				message = "["+userName+" @ "+userIP+"]->[View User Profile - "+requestedUserName+"]: Failure, no such username.";
				logger.warning(message);
				return Response.status(400).entity(toJsonString(message)).build(); // Failure
			}
		}
		message = "["+userName+" @ "+userIP+"]->[View User Profile - "+requestedUserName+"]: Failure, incorrect username/password.";
		logger.warning(message);
		return Response.status(400).entity(toJsonString(message)).build(); // Failure
    }
	
	// |============================================|
	// |               View all users               |
	// |============================================|
	
	@GET 
    @Path("/users") // Path = http://localhost:8080/Auction_Server/users
    public Response getUsers() 
	{
		String userIP = request.getRemoteAddr();
		String message;
		String decodedHeader = decodeHeader();
		if(decodedHeader == null)
		{
			message = "[? @ "+userIP+"]->[View all users]: Failure, invalid authentication header.";
			logger.warning(message);
			return Response.status(400).entity(toJsonString(message)).build();
		}
		String userName = decodedHeader.substring(0, decodedHeader.indexOf(":"));
		String password = decodedHeader.substring(decodedHeader.indexOf(":")+1);
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			message = "["+userName+" @ "+userIP+"]->[View all users]: Success.";
			logger.info(message);
			return Response.status(200).entity(toJsonString(user_impl.listUsers())).build();
		}
		message = "["+userName+" @ "+userIP+"]->[View all users]: Failure, incorrect username/password.";
		logger.warning(message);
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |========================================|
	// |               Delete User              |
	// |========================================|
	
	@DELETE 
    @Path("/users/{reqUser}/delete") // Path = http://localhost:8080/Auction_Server/users/Admin/delete
    public Response deleteUser(@PathParam("reqUser") String requestedUserName) 
	{
		String userIP = request.getRemoteAddr();
		String message;
		String decodedHeader = decodeHeader();
		if(decodedHeader == null)
		{
			message = "[? @ "+userIP+"]->[Delete User - "+requestedUserName+"]: Failure, invalid authentication header.";
			logger.warning(message);
			return Response.status(400).entity(toJsonString(message)).build();
		}
		String userName = decodedHeader.substring(0, decodedHeader.indexOf(":"));
		String password = decodedHeader.substring(decodedHeader.indexOf(":")+1);
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			if( user_impl.getUserByName(requestedUserName) != null)
			{
				user_impl.removeUser(user_impl.getUserByName(requestedUserName).getUserId());
				message = "["+userName+" @ "+userIP+"]->[Delete User - "+requestedUserName+"]: Success.";
				logger.info(message);
			}
			else
			{
				message = "["+userName+" @ "+userIP+"]->[Delete User - "+requestedUserName+"]: Failure, username doesn't exist.";
				logger.info(message);
			}
			
			return Response.status(200).entity(toJsonString(message)).build();
		}
		message = "["+userName+" @ "+userIP+"]->[Delete User - "+requestedUserName+"]: Failure, incorrect username/password.";
		logger.warning(message);
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |===================================================|
	// |               View user/own Auctions              |
	// |===================================================|
	
	@GET 
    @Path("/users/{reqUser}/items") // Path = http://localhost:8080/Auction_Server/users/Admin/items/?username=Admin&password=Admin
    public Response viewUserItems(@PathParam("reqUser") String requestedUserName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		System.out.println("Username: "+userName);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemImpl item_impl = new itemImpl(sessionFactory);
			user requestedUser = user_impl.getUserByName(requestedUserName);
			if(requestedUser != null)
			{
				logger.info("["+userName+"] -> User "+requestedUserName+" view auctions.");
				return Response.status(200).entity(toJsonString(item_impl.listItemsForUserId(requestedUser.getUserId()))).build(); // Success
			}
			else
			{
				logger.warning("Failed getting auctions for user "+requestedUserName+" because of bad username.");
				String message = "Error: Getting user auctions failed because of bad username.";
				return Response.status(400).entity(toJsonString(message)).build(); // Bad requested username
			}

		}
		return Response.status(200).entity("view user items").build();
    }
	
	// |=======================================================|
	// |               View participated Auctions              |
	// |=======================================================|
	
	@GET 
    @Path("/users/{reqUser}/auctions") // Path = http://localhost:8080/Auction_Server/users/Admin/?username=Admin&password=Admin
    public Response viewUserParticipatedAuctions(@PathParam("reqUser") String requestedUserName, @QueryParam("username") String userName, @QueryParam("password") String password) 
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
    @Path("/items/") // Path = http://localhost:8080/Auction_Server/items/?username=Admin&password=Admin
    public Response viewItems(@QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemImpl item_impl = new itemImpl(sessionFactory);
			logger.info("["+userName+"] -> View Items");
			return Response.status(200).entity(toJsonString(item_impl.listItems())).build();
		}
		logger.warning("Failed getting items for user ID "+userName);
		String message = "Error: Getting items failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=======================================|
	// |               View item               |
	// |=======================================|
	
	@GET 
    @Path("/items/{reqItemName}") // Path = http://localhost:8080/Auction_Server/items/Item1/?username=Admin&password=Admin
    public Response viewItem(@PathParam("reqItemName") String requestedItemName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemImpl item_impl = new itemImpl(sessionFactory);
			logger.info("["+userName+"] -> View Item "+requestedItemName);
			item requestedItem = item_impl.getItemByName(requestedItemName);
			if(requestedItem != null)
			{
				return Response.status(200).entity(toJsonString(requestedItem)).build(); // Success
			}
			else
			{
				logger.warning("Failed getting item profile for user "+userName+" because of bad requested item.");
				String message = "Error: Getting item profile failed because of bad requested item.";
				return Response.status(400).entity(toJsonString(message)).build(); // Bad requested Item
			}
		}
		logger.warning("Failed getting item "+requestedItemName+" for user "+userName);
		String message = "Error: Getting item "+requestedItemName+" failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=========================================|
	// |               Bid on item               |
	// |=========================================|
	
	@PUT 
    @Path("/items/{reqItemName}/bid") // Path = http://localhost:8080/Auction_Server/items/Item1/bid/?username=Admin&password=Admin
	@Consumes(MediaType.APPLICATION_JSON)
    public Response bidItem(int price, @PathParam("reqItemName") String requestedItemName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			
			itemImpl item_impl = new itemImpl(sessionFactory);
			logger.info("["+userName+"] -> View Item "+requestedItemName);
			item requestedItem = item_impl.getItemByName(requestedItemName);
			if(requestedItem != null)
			{
				requestedItem.setItemLastBidPrice(price);
				item_impl.updateItem(requestedItem);
				String message = "Successfully bid on item "+requestedItemName;
				logger.info("["+userName+"] -> Bid Item "+requestedItemName);
				return Response.status(200).entity(toJsonString(message)).build(); // Success
			}
			else
			{
				logger.warning("Failed getting ite profile for user "+userName+" because of bad requested item.");
				String message = "Error: Getting item profile failed because of bad requested item.";
				return Response.status(400).entity(toJsonString(message)).build(); // Bad requested Item
			}
		}
		logger.warning("Failed bid on item "+requestedItemName+" by user "+userName);
		String message = "Error: Bid on item "+requestedItemName+" failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=====================================================|
	// |               Put item up for auction               |
	// |=====================================================|
	
	@POST 
    @Path("/items/add") // Path = http://localhost:8080/Auction_Server/items/add/?username=Admin&password=Admin
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(item inputItem, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			item newItem = new item(inputItem);
			itemImpl item_impl = new itemImpl(sessionFactory);
			item_impl.addItem(newItem);
			String message = "Item "+newItem.getItemName()+" has been successfully added.";
			logger.info("["+newItem.getItemName()+"] has been successfully added to the Auction Server.");
			return Response.status(201).entity(toJsonString(message)).build();
		}
		logger.warning("Failed to add item "+inputItem.getItemName()+" by user ID "+userName);
		String message = "Error: item add fail.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |==================================================|
	// |               View item categories               |
	// |==================================================|
	
	@GET 
    @Path("/items/category") // Path = http://localhost:8080/Auction_Server/items/category/?username=Admin&password=Admin
    public Response viewItemCategories(@QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemCategoryImpl item_category_impl = new itemCategoryImpl(sessionFactory);
			logger.info("["+userToAuth.getUserName()+"] -> View all item categories");
			return Response.status(200).entity(toJsonString(item_category_impl.listItemCategories())).build();
		}
		logger.warning("Failed to view item categories by user ID "+userName);
		String message = "Error: view item categories failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |===============================================|
	// |               Add item category               |
	// |===============================================|
	
	@POST 
    @Path("/items/category/add") // Path = http://localhost:8080/Auction_Server/items/category/add/?username=Admin&password=Admin
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItemCategory(itemCategory inputItemCategory, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		String message;
		
		if( isAuthentic(userToAuth, password) )
		{
			itemCategory newItemCategory = new itemCategory(inputItemCategory);
			itemCategoryImpl item_category_impl = new itemCategoryImpl(sessionFactory);
			
			if( item_category_impl.getItemCategoryByName(newItemCategory.getItemCategoryName()) == null)
			{
				item_category_impl.addItemCategory(newItemCategory);
				message = "Item category "+newItemCategory.getItemCategoryName()+" has been successfully added.";
				logger.info("["+newItemCategory.getItemCategoryName()+"] has been successfully added to the Auction Server.");
			}
			else
			{
				message = "Item category "+newItemCategory.getItemCategoryName()+" has failed to be added because category name exists.";
				logger.info("["+newItemCategory.getItemCategoryName()+"] has failed to be added because category name exists.");
			}

			return Response.status(200).entity(toJsonString(message)).build();
		}
		logger.warning("Failed to add item category "+inputItemCategory.getItemCategoryName()+" by user ID "+userName);
		message = "Error: item category add fail.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |==================================================|
	// |               Delete item category               |
	// |==================================================|
	
	@DELETE
    @Path("/items/category/{reqItemCategory}/delete") // Path = http://localhost:8080/Auction_Server/items/category/Category1/delete/?username=Admin&password=Admin
    public Response deleteItemCategory(@PathParam("reqItemCategory") String requestedItemCategory, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		String message;
		
		if( isAuthentic(userToAuth, password) )
		{
			itemCategoryImpl item_category_impl = new itemCategoryImpl(sessionFactory);
			
			if( item_category_impl.getItemCategoryByName(requestedItemCategory) != null)
			{
				item_category_impl.removeItemCategory(item_category_impl.getItemCategoryByName(requestedItemCategory).getItemCategoryID());
				logger.info("["+userToAuth.getUserName()+"] -> Item category "+requestedItemCategory+" Delete");
				message = "Item category "+requestedItemCategory+" has been successfully deleted.";
			}
			else
			{
				logger.info("["+userToAuth.getUserName()+"] -> Item category "+requestedItemCategory+" wasn't deleted because no such category exists.");
				message = "Item category "+requestedItemCategory+" wasn't deleted because no such category exists.";
			}
			
			return Response.status(200).entity(toJsonString(message)).build();
		}
		logger.warning("Failed to delete item category "+requestedItemCategory+" by user "+userName);
		message = "Error: item category delete fail.";
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
	
	private String decodeHeader() 
	{
		String decodedHeader;
		try 
		{
			// Get the Authorisation Header from Request
			String header = request.getHeader("authorization");
			    
			// Header is in the format "Basic 3nc0dedDat4"
			// We need to extract data before decoding it back to original string
			String data = header.substring(header.indexOf(" ") +1 );
			    
			// Decode the data back to original string
			byte[] bytes = new BASE64Decoder().decodeBuffer(data);
			decodedHeader = new String(bytes); 
		}
		catch(Exception e)
		{
			decodedHeader = null;
		}
		return decodedHeader;
	}
}
	

	