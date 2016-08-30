package Auction_Project.Auction_Server;

import java.util.HashMap;
import java.util.logging.Logger;
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