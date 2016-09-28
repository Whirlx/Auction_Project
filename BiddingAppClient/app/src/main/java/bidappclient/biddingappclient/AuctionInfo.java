package bidappclient.biddingappclient;

/**
 * Created by user on 03/09/2016.
 */
public class AuctionInfo {
    private String itemId;
    private String itemName;
    private String lastBidPrice;
    private String timeOfLastBid;
    private String remainingAuctionTime;

    public AuctionInfo (String id, String name, String bidPrice, String timeLastBid, String remainingTime)
    {
        itemId = id;
        itemName = name;
        lastBidPrice = bidPrice;
        timeOfLastBid = timeLastBid;
        remainingAuctionTime = remainingTime;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public String getLastBidPrice() {
        return lastBidPrice;
    }

    public String getTimeOfLastBid() {
        return timeOfLastBid;
    }

    public String getRemainingAuctionTime() {
        return remainingAuctionTime;
    }
}
