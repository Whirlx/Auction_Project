package Auction_Project.Auction_Server;

public class InfoMessage {
	
	private int messageID;
	private String message;
	
	public InfoMessage(int messageID, String message) {
		this.messageID = messageID;
		this.message = message;
	}
	
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
