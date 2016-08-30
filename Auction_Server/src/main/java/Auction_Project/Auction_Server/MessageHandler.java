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
public class MessageHandler {
	
	public static final Logger logger = Logger.getLogger(MessageHandler.class.getName());
	private static HashMap<Integer, User> mapOfUsers = new HashMap<Integer, User>();
	private static HashMap<Integer, Item> mapOfItems = new HashMap<Integer, Item>();
	private static int numOfUsers = 0;
	private static int numOfItems = 0;
	
	// |=================================================|
	// |               Server Root Function              |
	// |=================================================|
	
	@GET
    public Response openingMessage(@Context HttpServletRequest request)  // Opening message when entering the server
	{
		String userIP = request.getRemoteAddr();
		logger.info("[User with IP: "+userIP+"] has entered the server lobby.");
		InfoMessage message = new InfoMessage(0, "Welcome to the Auction Server!");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		return Response.status(200).entity(jsonMessage).build();
		//logger.log(Level.SEVERE, "@@@@@@@@@@@@@@Exception occur");
    }

	// |===========================================|
	// |               Register User               |
	// |===========================================|
	
	@POST
	@Path("/register") // http://localhost:8080/Auction_Server/register
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerUser(User inputUser) {
		if( inputUser == null )
		{
			InfoMessage message = new InfoMessage(-1, "Error: Registration Failed.");
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(message);
			logger.warning("User registration Failed.");
			return Response.status(400).entity(jsonMessage).build();
		}
		
		User newUser = new User(inputUser);
		numOfUsers++;
		newUser.setUserId(numOfUsers);
		mapOfUsers.put(numOfUsers, newUser);
		InfoMessage message = new InfoMessage(1, "User "+newUser.getUserName()+" (ID:"+numOfUsers+") has been successfully registered.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.info("["+inputUser.getUserName()+"] has successfully registered to the Auction Server.");
		return Response.status(201).entity(jsonMessage).build();
	}
	
	// |===============================================|
	// |               View User Profile               |
	// |===============================================|
	
	@GET 
    @Path("/users/{reqId}") // Path = http://localhost:8080/Auction_Server/users/1/?id=3&password=123abc
    public Response userProfile(@PathParam("reqId") int requestedUserID, @QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(mapOfUsers.get(requestedUserID));
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> User "+requestedUserID+" Profile");
			return Response.status(200).entity(jsonMessage).build();
		}
		InfoMessage message = new InfoMessage(-1, "Error: Getting profile failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed getting user profile for user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
	
	// |============================================|
	// |               View all users               |
	// |============================================|
	
	@GET 
    @Path("/users") // Path = http://localhost:8080/Auction_Server/users/?id=3&password=123abc
    public Response getUsers(@QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(mapOfUsers);
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> View all users");
			return Response.status(200).entity(jsonMessage).build();
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
	
	// |============================================|
	// |               View all Items               |
	// |============================================|
	
	@GET 
    @Path("/items/") // Path = http://localhost:8080/Auction_Server/items/?id=3&password=123abc
    public Response viewItems(@QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(mapOfItems);
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> View Items");
			return Response.status(200).entity(jsonMessage).build();
		}
		InfoMessage message = new InfoMessage(-1, "Error: Getting items failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed getting items for user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
	
	// |=======================================|
	// |               View item               |
	// |=======================================|
	
	@GET 
    @Path("/items/{id}") // Path = http://localhost:8080/Auction_Server/items/1/?id=3&password=123abc
    public Response viewItem(@PathParam("id") int itemID, @QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(mapOfItems.get(itemID));
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> View Item "+itemID);
			return Response.status(200).entity(jsonMessage).build();
		}
		InfoMessage message = new InfoMessage(-1, "Error: Getting item "+itemID+" failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed getting item "+itemID+" for user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
	
	// |=========================================|
	// |               Bid on item               |
	// |=========================================|
	
	@PUT 
    @Path("/items/{id}/bid") // Path = http://localhost:8080/Auction_Server/items/1/bid/?id=3&password=123abc
	@Consumes(MediaType.APPLICATION_JSON)
    public Response bidItem(int price, @PathParam("id") int itemID, @QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			mapOfItems.get(itemID).setItemLastBidPrice(price);
			InfoMessage message = new InfoMessage(2, "Successfully bid on item "+itemID);
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(message);
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> Bid Item "+itemID);
			return Response.status(200).entity(jsonMessage).build();
		}
		InfoMessage message = new InfoMessage(-1, "Error: Bid on item "+itemID+" failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed bid on item "+itemID+" by user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
	
	// |=====================================================|
	// |               Put item up for auction               |
	// |=====================================================|
	
	@POST 
    @Path("/items/add") // Path = http://localhost:8080/Auction_Server/items/add/?id=3&password=123abc
	@Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(Item inputItem, @PathParam("id") int itemID, @QueryParam("id") int userID, @QueryParam("password") String password) {
		if( isAuthentic(userID, password) )
		{
			Item newItem = new Item(inputItem);
			numOfItems++;
			newItem.setItemID(numOfItems);
			mapOfItems.put(numOfItems, newItem);
			InfoMessage message = new InfoMessage(3, "Item has been successfully added.");
			Gson gson = new Gson();
			String jsonMessage = gson.toJson(message);
			logger.info("["+mapOfUsers.get(userID).getUserName()+"] -> Add item "+itemID);
			return Response.status(200).entity(jsonMessage).build();
		}
		InfoMessage message = new InfoMessage(-1, "Error: item add fail.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed to add item "+itemID+" by user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
	
	/*	#####################################
	 *  ########## Class Functions ##########
	 *  #####################################
	 */ 
    
	private boolean isAuthentic(int userID, String password) {
		if( mapOfUsers.containsKey(userID) )
		{
			if ( mapOfUsers.get(userID).getPassword().compareTo(password) == 0 )
			{
				return true;
			}
		}
		return false;
	}
	
}