package bidappclient.biddingappclient;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

//class that displays all current items from the server that are open for bidding in the app
public class ViewAllItemsActivity extends BaseActivity {

    private ArrayList<JSONObject> jsonItemList; // will contain the items list that we need to display
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_items);
        jsonItemList = new ArrayList<JSONObject>();
        invokeViewAllItemsRequest(); // now list object is full
    }

    //request the server for the item list
    public void invokeViewAllItemsRequest()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/items", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    JSONArray itemResultsJSON = new JSONArray(response);
                    for(int j = 0; j < itemResultsJSON.length(); j++){
                        jsonItemList.add(itemResultsJSON.getJSONObject(j));
                    }
                    ArrayList<String> displayItemInfoAL = new ArrayList<String>();
                    for (int j = 0; j < jsonItemList.size(); j++){
                        try {
                            displayItemInfoAL.add("Item Name: " + jsonItemList.get(j).get("item_name") + "\nItem Description: " + jsonItemList.get(j).get("item_desc") +
                                    "\nCategory: " + jsonItemList.get(j).get("item_category") + "\nCurrent Bid: " + jsonItemList.get(j).get("item_latest_bid_price") +
                                    "\nStarting price: " + jsonItemList.get(j).get("item_start_price") + "\nNumber of Bids: " + jsonItemList.get(j).get("item_num_bids") +
                                    "\nLatest bid username: " + jsonItemList.get(j).get("item_latest_bid_username") + "\nLast Bid Time: " + jsonItemList.get(j).get("item_latest_bid_time").toString().substring(0,16));
                            System.out.println ("#####################" + displayItemInfoAL.get(j));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String[] auctionInfosToDisplay = new String[displayItemInfoAL.size()];
                    auctionInfosToDisplay = displayItemInfoAL.toArray(auctionInfosToDisplay);
                    ListAdapter auctionsInfoAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, auctionInfosToDisplay)
                    {
                        @Override // paint background red of ended auctions
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            try {
                                System.out.println (" @@@@@@@@@@@@### " + jsonItemList.get(position).get("isAuctionOver"));
                                if (jsonItemList.get(position).get("isAuctionOver").toString().equals("true"))
                                    view.setBackgroundColor(Color.parseColor("#66F44336"));
                                else view.setBackgroundColor(Color.parseColor("#000000"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return view;
                        }
                    };
                    ListView searchedAuctionsListView = (ListView) findViewById(R.id.viewAllItemsListViewId);
                    searchedAuctionsListView.setAdapter(auctionsInfoAdapter); //dispalying all items information on the list
                    searchedAuctionsListView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener(){ // when clikcing on item that his auction didnt end sends us to bidding screen
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String currentBid = "";
                                    String itemName = "";
                                    try {
                                        JSONObject itemChosen = jsonItemList.get(position);
                                        if (itemChosen.get("isAuctionOver").toString().equals("true")) {
                                            Toast.makeText(getApplicationContext(), "Auction has already ended.", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent (getApplicationContext() , MainUserScreenActivity.class);
                                            startActivity(i);
                                        }
                                        else {
                                            currentBid = itemChosen.get("item_latest_bid_price").toString();
                                            itemName = itemChosen.get("item_name").toString();
                                            newBid(currentBid, itemName);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
            {
                String response = "";
                try {
                    response = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        });
    }


    //sending us to the new bid screen on that item we clicked
    public void newBid (String currentBid, String itemName)
    {
        Intent i = new Intent (this, PlaceNewBidActivity.class);
        i.putExtra ("currentbid", currentBid);
        i.putExtra ("itemname", itemName);
        startActivity(i);
    }




}
