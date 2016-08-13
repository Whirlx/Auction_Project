package Auction_Project.Auction_Server;

//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
//import java.io.InputStreamReader;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

@Path("/") // Full path is: http://localhost:8080/Auction_Server/
public class MessageHandler {
	
	/*	##########################################
	 *  ########## Server root function ##########
	 *  ##########################################
	 */ 
	
	@GET // Works
    @Produces(MediaType.APPLICATION_JSON)
    public String openingMessage()  // Opening message when entering the server
	{
		String jsonMessage = createJsonMessage(1, "Welcome to Auction_Server!");
        return jsonMessage;
    }

	/*	####################################
	 *  ########## @GET Functions ##########
	 *  ####################################
	 */ 
	
	@GET // Works
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public Response test() {
		String jsonString = "{\"messageID\":2,\"testString\":\"Testing.\"}";
		return Response.status(200).entity(jsonString).build();
    }
	
	@GET
    @Path("/viewBids")
    @Produces(MediaType.APPLICATION_JSON)
    public String viewBids() {
    	String result = "Get success";
        return result;
    }
	
	@GET
    @Path("/item")
    @Produces(MediaType.APPLICATION_JSON)
    public String getItemDetails() {
    	String result = "Get success";
        return result;
    }
	
	@GET
    @Path("/auctionDetails")
    @Produces(MediaType.APPLICATION_JSON)
    public String getAuctionDetails() {
    	String result = "Get success";
        return result;
    }
	
	/*	#####################################
	 *  ########## @POST Functions ##########
	 *  #####################################
	 */ 
	
	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registerUser(User inputUser) {
		System.out.println("[Register Request] -> "+inputUser.getName()+"\n");
		User newUser = new User();
		newUser.setID(inputUser.getID());
		newUser.setName(inputUser.getName());
		newUser.addBalance(inputUser.getBalance());
		return Response.status(200).entity(newUser).build();
	}
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public String loginUser(InputStream incomingData) {
		return "TODO";
	}
	
	@POST
	@Path("/addItem")
	@Consumes(MediaType.APPLICATION_JSON)
	public String addItem(InputStream incomingData) {
		return "TODO";
	}
	
	@POST
	@Path("/bid")
	@Consumes(MediaType.APPLICATION_JSON)
	public String bidOnItem(InputStream incomingData) {
		return "TODO";
	}
	
	/*	#####################################
	 *  ########## Class Functions ##########
	 *  #####################################
	 */ 
    
    private String createJsonMessage(int messageID, String message) {
    	ObjectMapper mapper = new ObjectMapper();
    	JsonMessage jsonMessage = new JsonMessage(messageID, message);
    	String jsonString="";
    	try {
			jsonString = mapper.writeValueAsString(jsonMessage); //Convert object to JSON string
		} 
		catch (JsonGenerationException e)	{ e.printStackTrace(); } 
		catch (JsonMappingException e)		{ e.printStackTrace(); } 
		catch (IOException e)				{ e.printStackTrace(); }
    	return jsonString;
    }
}