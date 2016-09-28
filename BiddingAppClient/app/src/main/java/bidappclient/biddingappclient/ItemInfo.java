package bidappclient.biddingappclient;

/**
 * Created by user on 28/08/2016.
 */
public class ItemInfo {
    private String itemCategory;
    private String itemName;
    private int startingPrice;
    private int lastingTime;
    private String description;

    public ItemInfo (String name, int bid, int time, String des)
    {
        itemName = name;
        startingPrice = bid;
        lastingTime = time;
        description = des;
    }

    public ItemInfo (String category, String name, String des, int bid, int time)
    {
        itemCategory = category;
        itemName = name;
        startingPrice = bid;
        lastingTime = time;
        description = des;
    }

    public String getName()
    {
        return itemName;
    }

    public int getStartingPrice()
    {
        return startingPrice;
    }

    public int getLastingTime()
    {
        return lastingTime;
    }

    public String getDescription()
    {
        return description;
    }
}
