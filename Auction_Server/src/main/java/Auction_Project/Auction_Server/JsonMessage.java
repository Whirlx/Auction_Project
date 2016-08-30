package Auction_Project.Auction_Server;

public class JsonMessage {
	
	private int messageID;
	private String testString;
	
	public JsonMessage(){
		this.messageID = 0;
		this.testString = "";
	}
	
	public JsonMessage(int messageID, String testString){
		this.messageID = messageID;
		this.testString = testString;
	}
	
	public int getMessageID() {
		return messageID;
	}
	public void setMessageID(int messageID) {
		this.messageID = messageID;
	}
	public String getTestString() {
		return testString;
	}
	public void setTestString(String testString) {
		this.testString = testString;
	}
	
}