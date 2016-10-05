package bidappclient.biddingappclient;

/**
 * Created by user on 28/08/2016.
 */
public class ItemInfo {
    private String item_category;
    private String item_name;
    private int item_start_price;
    //private int lastingTime;
    private String item_desc;

//    public ItemInfo (String name, int bid, int time, String des)
//    {
//        item_name = name;
//        item_start_price = bid;
//        //lastingTime = time;
//        item_desc = des;
//    }

    public ItemInfo (String category, String name, String des, int bid) // another argument shold be - int time (how many days will the bid last)
    {
        item_category = category;
        item_name = name;
        item_start_price = bid;
        //lastingTime = time;
        item_desc = des;
    }

    public String getName()
    {
        return item_name;
    }

    public String getItemCategory()
    {
        return item_category;
    }

    public int getStartingPrice()
    {
        return item_start_price;
    }

//    public int getLastingTime()
//    {
//        return lastingTime;
//    }

    public String getDescription()
    {
        return item_desc;
    }
}
