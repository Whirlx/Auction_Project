package Auction_Project.Auction_Server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
import Auction_Project.Auction_Server.hibernate.Impl.auctionBidTransactionsImpl;
import Auction_Project.Auction_Server.hibernate.Impl.itemCategoryImpl;
import Auction_Project.Auction_Server.hibernate.Impl.itemImpl;
import Auction_Project.Auction_Server.hibernate.Impl.userImpl;
import Auction_Project.Auction_Server.hibernate.model.user;
import Auction_Project.Auction_Server.hibernate.model.auctionBidTransactions;
import Auction_Project.Auction_Server.hibernate.model.item;
import Auction_Project.Auction_Server.hibernate.model.itemCategory;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.service.ServiceRegistry;

import sun.misc.BASE64Decoder;

@Path("/") // Full path is: http://localhost:8080/Auction_Server/
@Produces(MediaType.APPLICATION_JSON)
public class MessageHandler 
{
	public static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
	private SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();
	private String userIP;
	private String message;
	private String userName;
	private String password;
	
	@Context
	private HttpServletRequest request;
	
	/** |=================================================|
	    |               Server Root Function              |
	    |=================================================| */
	
	@GET
    public Response openingMessage()  // Opening message when entering the server
	{
		issueWelcomeMessage("Welcome to the Auction Server!");
		return Response.status(200).entity(toJsonString(message)).build(); // Success
    }
	
	/** |===========================================|
	    |               Register User               |
	    |===========================================| */
	
	@POST
	@Path("/register") // http://localhost:8080/Auction_Server/register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(user inputUser) 
	{
		String issuedCommand = "Register";
		String requestedEntity = "user";
		this.userIP = request.getRemoteAddr();
		user newUser = new user(inputUser);
		this.userName = newUser.getUserName();
		userImpl user_impl = new userImpl(this.sessionFactory);
		if( user_impl.getUserByName(newUser.getUserName()) != null)
		{
			issueEntityExistsErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		user_impl.addUser(newUser);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(message)).build(); // Success
	}
	
	/** |===============================================|
	    |               View User Profile               |
	    |===============================================| */
	
	@GET 
    @Path("/users/{reqUser}") // Path = http://localhost:8080/Auction_Server/users/Admin
    public Response userProfile(@PathParam("reqUser") String requestedUserName) 
	{
		String issuedCommand = "View User Profile - "+requestedUserName;
		String requestedEntity = "user";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		
		user requestedUser = user_impl.getUserByName(requestedUserName);
		if(requestedUser == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(requestedUser)).build(); // Success
		
    }
	
	/** |============================================|
	    |               View all users               |
	    |============================================| */
	
	@GET 
    @Path("/users") // Path = http://localhost:8080/Auction_Server/users
    public Response getUsers() 
	{
		String issuedCommand = "View all users";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( userToAuth.getUserId() != 0 ) // Not admin
		{
			issueAccessDeniedErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(user_impl.listUsers())).build(); // Success
    }
	
	/** |========================================|
	    |               Delete User              |
	    |========================================| */
	
	@DELETE 
    @Path("/users/{reqUser}/delete") // Path = http://localhost:8080/Auction_Server/users/Admin/delete
    public Response deleteUser(@PathParam("reqUser") String requestedUserName) 
	{
		String issuedCommand = "Delete User - "+requestedUserName;
		String requestedEntity = "user";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
			
		if( user_impl.getUserByName(requestedUserName) == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( userToAuth.getUserId() != 0 ) // Not admin
		{
			issueAccessDeniedErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		user_impl.removeUser(user_impl.getUserByName(requestedUserName).getUserId());
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(message)).build(); // Success
    }
	
	/** |===================================================|
	    |               View user/own Auctions              |
	    |===================================================| */
	
	@GET 
    @Path("/users/{reqUser}/items") // Path = http://localhost:8080/Auction_Server/users/Admin/items
    public Response viewUserItems(@PathParam("reqUser") String requestedUserName) 
	{
		String issuedCommand = "Delete User - "+requestedUserName;
		String requestedEntity = "user";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false)
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		user requestedUser = user_impl.getUserByName(requestedUserName);
		if( requestedUser == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( requestedUserName.equals(userToAuth.getUserName()) ) // If checking a different users' auctions
		{
			if( userToAuth.getUserId() != 0 ) // Not admin
			{
				issueAccessDeniedErrorMessage(issuedCommand);
				return Response.status(400).entity(toJsonString(message)).build(); // Failure
			}
		}
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(item_impl.listItemsForUserId(requestedUser.getUserId()))).build(); // Success

    }
	
	/** |================================================================|
	    |               View user/own participated Auctions              |
	    |================================================================| */
	
	@GET 
    @Path("/users/{reqUser}/auctions") // Path = http://localhost:8080/Auction_Server/users/Admin/auctions
    public Response viewUserParticipatedAuctions(@PathParam("reqUser") String requestedUserName) 
	{
		String issuedCommand = "View participated auctions for - "+requestedUserName;
		String requestedEntity = "user";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		user requestedUser = user_impl.getUserByName(requestedUserName);
		if( requestedUser == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( requestedUserName.equals(userToAuth.getUserName()) ) // If checking a different users' auctions
		{
			if( userToAuth.getUserId() != 0 ) // Not admin
			{
				issueAccessDeniedErrorMessage(issuedCommand);
				return Response.status(400).entity(toJsonString(message)).build(); // Failure
			}
		}
		auctionBidTransactionsImpl trx_impl = new auctionBidTransactionsImpl(this.sessionFactory);
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		List<Integer> participatedItemIDsList = trx_impl.listParticipatedItemIDsForUserByUserID(requestedUser.getUserId());
		List<item> participatedItemsList = new ArrayList<item>();
		for(Integer i : participatedItemIDsList)
		{
			item itemToAdd = new item(item_impl.getItemById(i));
			participatedItemsList.add(itemToAdd);
		}
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(participatedItemsList).build(); // Success
    }
	
	/** |============================================|
	    |               View all Items               |
	    |============================================| */
	
	@GET 
    @Path("/items") // Path = http://localhost:8080/Auction_Server/items
    public Response viewItems() 
	{
		String issuedCommand = "View all items";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(item_impl.listItems())).build(); // Success
    }
	
	/** |===================================================|
	    |               View Items by Category              |
	    |===================================================| */
	
	@GET 
    @Path("/items/category/{reqCategoryName}") // Path = http://localhost:8080/Auction_Server/items/category/Default_Category
    public Response viewItemsByCategory(@PathParam("reqCategoryName") String requestedItemCategory) 
	{
		String issuedCommand = "View items from category "+requestedItemCategory;
		String requestedEntity = "item category";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemCategoryImpl item_category_impl = new itemCategoryImpl(this.sessionFactory);
		if( item_category_impl.getItemCategoryByName(requestedItemCategory) == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(item_impl.listItemsByCategoryName(requestedItemCategory))).build(); // Success
    }
	
	/** |=======================================|
	    |               View item               |
	    |=======================================| */
	
	@GET 
    @Path("/items/{reqItemName}") // Path = http://localhost:8080/Auction_Server/items/Item1
    public Response viewItem(@PathParam("reqItemName") String requestedItemName) 
	{
		String issuedCommand = "View item - "+requestedItemName;
		String requestedEntity = "item";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		item requestedItem = item_impl.getItemByName(requestedItemName);
		if(requestedItem == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(requestedItem)).build(); // Success
    }
	
	/** |=========================================|
	    |               Bid on item               |
	    |=========================================| */
	
	@GET
    @Path("/items/{reqItemName}/bid") // Path = http://localhost:8080/Auction_Server/items/Item1/bid/?price=100
	@Consumes(MediaType.APPLICATION_JSON)
    public Response bidItem(@PathParam("reqItemName") String requestedItemName, @QueryParam("price") String price) 
	{
		String issuedCommand = "Bid on item - "+requestedItemName;
		String requestedEntity = "item";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		item requestedItem = item_impl.getItemByName(requestedItemName);
		if(requestedItem == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		int newBid = Integer.parseInt(price);
		int oldBid = requestedItem.getItemLatestBidPrice();
		int minBid = getMininumBid(oldBid);
		if( newBid < minBid )
		{
			issueInvalidBidErrorMessage(issuedCommand, minBid);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		requestedItem.setItemLatestBidPrice(newBid);
		item_impl.updateItem(requestedItem);
		auctionBidTransactionsImpl trx_impl = new auctionBidTransactionsImpl(this.sessionFactory);
		auctionBidTransactions bid_trx = new auctionBidTransactions(userToAuth.getUserId(), requestedItem.getItemID(), requestedItem.getItemLatestBidPrice());
		trx_impl.addAuctionBidTransaction(bid_trx);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(message)).build(); // Success
    }
	
	/** |=====================================================|
	    |               Put item up for auction               |
	    |=====================================================| */
	
	@POST 
    @Path("/items/add") // Path = http://localhost:8080/Auction_Server/items/add
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(item inputItem) 
	{
		String issuedCommand = "Put item up for auction - "+inputItem.getItemName();
		String requestedEntity = "item";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		item newItem = new item(inputItem);
		newItem.setItem_latest_bid_username(userToAuth.getUserName());
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		if( item_impl.getItemByName(newItem.getItemName()) != null)
		{
			issueEntityExistsErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		item_impl.addItem(newItem);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(message)).build(); // Success
    }
	
	/** |==================================================|
	    |               View item categories               |
	    |==================================================| */
	
	@GET 
    @Path("/items/category") // Path = http://localhost:8080/Auction_Server/items/category
    public Response viewItemCategories() 
	{
		String issuedCommand = "View item categories";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemCategoryImpl item_category_impl = new itemCategoryImpl(this.sessionFactory);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(item_category_impl.listItemCategories())).build(); // Success
    }
	
	/** |===============================================|
	    |               Add item category               |
	    |===============================================| */
	
	@POST 
    @Path("/items/category/add/{category_name}") // Path = http://localhost:8080/Auction_Server/items/category/add/Default
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItemCategory(@PathParam("category_name") String category_name) 
	{
		String issuedCommand = "Add item category - "+category_name;
		String requestedEntity = "item category";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( userToAuth.getUserId() != 0 ) // Not admin
		{
			issueAccessDeniedErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemCategory newItemCategory = new itemCategory(category_name);
		itemCategoryImpl item_category_impl = new itemCategoryImpl(this.sessionFactory);
		if( item_category_impl.getItemCategoryByName(category_name) != null)
		{
			issueEntityExistsErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		item_category_impl.addItemCategory(newItemCategory);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(message)).build(); // Success
    }
	
	/** |==================================================|
	    |               Delete item category               |
	    |==================================================| */
	
	@DELETE
    @Path("/items/category/{reqItemCategory}/delete") // Path = http://localhost:8080/Auction_Server/items/category/default-categories/delete
    public Response deleteItemCategory(@PathParam("reqItemCategory") String requestedItemCategory) 
	{
		String issuedCommand = "Delete item category - "+requestedItemCategory;
		String requestedEntity = "item category";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( userToAuth.getUserId() != 0 ) // Not admin
		{
			issueAccessDeniedErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemCategoryImpl item_category_impl = new itemCategoryImpl(this.sessionFactory);
		if( item_category_impl.getItemCategoryByName(requestedItemCategory) == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		item_category_impl.removeItemCategory(item_category_impl.getItemCategoryByName(requestedItemCategory).getItemCategoryID());
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(message)).build();
    }
	
	/** |===========================================================|
	    |               View all auction transactions               |
	    |===========================================================| */
	
	@GET 
    @Path("/transactions") // Path = http://localhost:8080/Auction_Server/transactions
    public Response viewAllAuctionTransactions() 
	{
		String issuedCommand = "View all auction transactions";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		if( userToAuth.getUserId() != 0 ) // Not admin
		{
			issueAccessDeniedErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		auctionBidTransactionsImpl trx_impl = new auctionBidTransactionsImpl(this.sessionFactory);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(trx_impl.listAuctionBidTransactions())).build(); // Success
    }
	
	/** |================================================================|
	    |               View specific auction transactions               |
	    |================================================================| */
	
	@GET 
    @Path("/transactions/{reqItemName}") // Path = http://localhost:8080/Auction_Server/transactions/Item1
    public Response viewAllAuctionTransactions(@PathParam("reqItemName") String requestedItemName) 
	{
		String issuedCommand = "View auction transactions for - "+requestedItemName;
		String requestedEntity = "item";
		if( verifyHeader() == false )
		{
			issueHeaderErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		userImpl user_impl = new userImpl(this.sessionFactory);
		user userToAuth = user_impl.getUserByName(userName);
		if( isAuthentic(userToAuth, password) == false )
		{
			issueAuthenticationErrorMessage(issuedCommand);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		itemImpl item_impl = new itemImpl(this.sessionFactory);
		item requestedItem = item_impl.getItemByName(requestedItemName);
		if(requestedItem == null)
		{
			issueEntityMissingErrorMessage(issuedCommand, requestedEntity);
			return Response.status(400).entity(toJsonString(message)).build(); // Failure
		}
		auctionBidTransactionsImpl trx_impl = new auctionBidTransactionsImpl(this.sessionFactory);
		issueSuccessMessage(issuedCommand);
		return Response.status(200).entity(toJsonString(trx_impl.listAuctionBidTransactionsForItemById(requestedItem.getItemID()))).build(); // Success
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
	
	private boolean verifyHeader()
	{
		String decodedHeader = decodeHeader();
		this.userName = "";
		this.password = "";
		this.userIP = request.getRemoteAddr();
		if(decodedHeader == null)
		{
			return false;
		}
		this.userName = decodedHeader.substring(0, decodedHeader.indexOf(":"));
		this.password = decodedHeader.substring(decodedHeader.indexOf(":")+1);
		return true;
	}
	
	private int getMininumBid(int oldBid)
	{
		int minBid;
		if( oldBid < 100 ) minBid = oldBid + 5;
		else if( 100 <= oldBid && (oldBid < 1000) ) minBid = oldBid + 10;
		else if( 1000 <= oldBid && (oldBid < 10000) ) minBid = oldBid + 100;
		else if( 10000 <= oldBid && (oldBid < 100000) ) minBid = oldBid + 1000;
		else minBid = oldBid + 10000;
		return minBid;
	}
	
	private void issueWelcomeMessage(String welcomeMessage)
	{
		this.message = welcomeMessage;
		logger.info("[Guest @ "+this.userIP+"] has entered the server lobby.");
	}
	
	private void issueSuccessMessage(String issuedCommand)
	{
		this.message = "["+this.userName+" @ "+this.userIP+"]->["+issuedCommand+"]: Success.";
		logger.info(message);
	}
	
	private void issueHeaderErrorMessage(String issuedCommand)
	{
		this.message = "[? @ "+this.userIP+"]->["+issuedCommand+"]: Failure, invalid authentication header.";
		logger.warning(message);
	}
	
	private void issueAuthenticationErrorMessage(String issuedCommand)
	{
		this.message = "["+this.userName+" @ "+this.userIP+"]->["+issuedCommand+"]: Failure, incorrect username/password.";
		logger.warning(message);
	}
	
	private void issueEntityMissingErrorMessage(String issuedCommand, String requestedEntity)
	{
		this.message = "["+this.userName+" @ "+this.userIP+"]->["+issuedCommand+"]: Failure, no such "+requestedEntity+".";
		logger.warning(message);
	}
	
	private void issueEntityExistsErrorMessage(String issuedCommand, String requestedEntity)
	{
		this.message = "["+this.userName+" @ "+this.userIP+"]->["+issuedCommand+"]: Failure, "+requestedEntity+" already exists.";
		logger.warning(message);
	}
	
	private void issueAccessDeniedErrorMessage(String issuedCommand)
	{
		this.message = "["+this.userName+" @ "+this.userIP+"]->["+issuedCommand+"]: Failure, access denied.";
		logger.warning(message);
	}
	
	private void issueInvalidBidErrorMessage(String issuedCommand, int minBid)
	{
		this.message = "["+this.userName+" @ "+this.userIP+"]->["+issuedCommand+"]: Failure, bid is too low (must be "+minBid+"+).";
		logger.warning(message);
	}
	
}