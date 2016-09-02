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

@Path("/") // Full path is: http://localhost:8080/Auction_Server/
@Produces(MediaType.APPLICATION_JSON)
public class MessageHandler 
{
	
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
			itemImpl item_impl = new itemImpl(sessionFactory);
			System.out.println("["+userName+"] -> View Items");
			return Response.status(200).entity(toJsonString(item_impl.listItems())).build();
		}
		System.out.println("Failed getting items for user ID "+userName);
		String message = "Error: Getting items failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=======================================|
	// |               View item               |
	// |=======================================|
	
	@GET 
    @Path("/items/{item_id}") // Path = http://localhost:8080/Auction_Server/items/y/?username=x&password=x
    public Response viewItem(@PathParam("item_id") int itemID, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemImpl item_impl = new itemImpl(sessionFactory);
			System.out.println("["+userName+"] -> View Item "+itemID);
			GsonBuilder gsonBuilder = new GsonBuilder();
		    Gson gson = gsonBuilder.registerTypeAdapter(item.class, new itemAdapter()).create();
			String jsonString = gson.toJson(item_impl.getItemById(itemID));
			return Response.status(200).entity(jsonString).build();
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
    public Response bidItem(int price, @PathParam("item_name") String itemName, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemImpl item_impl = new itemImpl(sessionFactory);
			item itemToBid = item_impl.getItemByName(itemName);
			itemToBid.setItemLastBidPrice(price);
			item_impl.updateItem(itemToBid);
			String message = "Successfully bid on item "+itemName;
			System.out.println("["+userName+"] -> Bid Item "+itemName);
			return Response.status(200).entity(toJsonString(message)).build();
		}
		System.out.println("Failed bid on item "+itemName+" by user ID "+userName);
		String message = "Error: Bid on item "+itemName+" failed.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |=====================================================|
	// |               Put item up for auction               |
	// |=====================================================|
	
	@POST 
    @Path("/items/add") // Path = http://localhost:8080/Auction_Server/items/add/?username=x&password=x
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
			System.out.println("["+newItem.getItemName()+"] has been successfully added to the Auction Server.");
			return Response.status(201).entity(toJsonString(message)).build();
		}
		System.out.println("Failed to add item "+inputItem.getItemName()+" by user ID "+userName);
		String message = "Error: item add fail.";
		return Response.status(400).entity(toJsonString(message)).build();
    }
	
	// |===============================================|
	// |               Add item category               |
	// |===============================================|
	
	@POST 
    @Path("/items/category/add") // Path = http://localhost:8080/Auction_Server/items/category/add/?username=x&password=x
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItemCategory(itemCategory inputItemCategory, @QueryParam("username") String userName, @QueryParam("password") String password) 
	{
		SessionFactory sessionFactory=HibernateUtil.getSessionAnnotationFactory();
		userImpl user_impl = new userImpl(sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) )
		{
			itemCategory newItemCategory = new itemCategory(inputItemCategory);
			itemCategoryImpl item_category_impl = new itemCategoryImpl(sessionFactory);
			item_category_impl.addItemCategory(newItemCategory);
			String message = "Item category "+newItemCategory.getItemCategoryName()+" has been successfully added.";
			System.out.println("["+newItemCategory.getItemCategoryName()+"] has been successfully added to the Auction Server.");
			return Response.status(201).entity(toJsonString(message)).build();
		}
		System.out.println("Failed to add item category "+inputItemCategory.getItemCategoryName()+" by user ID "+userName);
		String message = "Error: item category add fail.";
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
	

	