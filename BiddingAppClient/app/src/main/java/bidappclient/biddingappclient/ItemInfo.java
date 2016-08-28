package bidappclient.biddingappclient;

/**
 * Created by user on 28/08/2016.
 */
public class ItemInfo {
    private String itemName;
    private int startingBidPrice;
    private int lastingBidTime;
    private String description;

    public ItemInfo (String name, int bid, int time, String des)
    {
        itemName = name;
        startingBidPrice = bid;
        lastingBidTime = time;
        description = des;
    }

    public String getName()
    {
        return itemName;
    }

    public int getStartingPrice()
    {
        return startingBidPrice;
    }

    public int getLastingTime()
    {
        return lastingBidTime;
    }

    public String getDescription()
    {
        return description;
    }
}
