package Auction_Project.Auction_Server;
/*
import java.util.HashMap;
import java.util.logging.Logger;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

@Path("/users") // Full path is: http://localhost:8080/Auction_Server/users
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

	private static int numOfUsers = 0;
	private static HashMap<Integer, User> mapOfUsers = new HashMap<Integer, User>();
	public static final Logger logger = Logger.getLogger(UserResource.class.getName());
	
	// |===================================================|
	// |               Root - View all users               |
	// |===================================================|
	
	@GET 
    public Response getUsers(@QueryParam("id") int userID, @QueryParam("password") String password) {
		if( mapOfUsers.containsKey(userID) )
		{
			if ( mapOfUsers.get(userID).getPassword().compareTo(password) == 0 )
			{
				Gson gson = new Gson();
				String jsonMessage = gson.toJson(mapOfUsers);
				logger.info("["+mapOfUsers.get(userID).getFullName()+"] -> View all users");
				return Response.status(200).entity(jsonMessage).build();
			}
		}
		InfoMessage message = new InfoMessage(-1, "Error: Getting all users failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed viewing all users for user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
		
	// |===========================================|
	// |               Register User               |
	// |===========================================|
	
	@POST
	@Path("/register") // Full path is: http://localhost:8080/Auction_Server/users/register
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
		newUser.setID(numOfUsers);
		mapOfUsers.put(newUser.getID(), newUser);
		InfoMessage message = new InfoMessage(1, "User "+newUser.getFullName()+" (ID:"+numOfUsers+") has been successfully registered.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.info("["+inputUser.getFullName()+"] has successfully registered to the Auction Server.");
		return Response.status(201).entity(jsonMessage).build();
	}
		
	// |===============================================|
	// |               View User Profile               |
	// |===============================================|
	
	@GET 
    @Path("/users/{reqId}") // Path = http://localhost:8080/Auction_Server/users/1/?id=3&password=123abc
    public Response userProfile(@PathParam("reqId") int requestedUserID, @QueryParam("id") int userID, @QueryParam("password") String password) {
		if( mapOfUsers.containsKey(userID) )
		{
			if ( mapOfUsers.get(userID).getPassword().compareTo(password) == 0 )
			{
				Gson gson = new Gson();
				String jsonMessage = gson.toJson(mapOfUsers.get(requestedUserID));
				logger.info("["+mapOfUsers.get(userID).getFullName()+"] -> User "+requestedUserID+" Profile");
				return Response.status(200).entity(jsonMessage).build();
			}
		}
		InfoMessage message = new InfoMessage(-1, "Error: Getting profile failed.");
		Gson gson = new Gson();
		String jsonMessage = gson.toJson(message);
		logger.warning("Failed getting user profile for user ID "+userID);
		return Response.status(400).entity(jsonMessage).build();
    }
    */
public class UserResource {}