package bidappclient.biddingappclient;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
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
import cz.msebera.android.httpclient.Header;

//class the displays all of your current bids (list of items which the user bidded on)
public class CurrentBidsActivity extends BaseActivity {
    private ArrayList<JSONObject> jsonList; // will contain all items
    private String currentBid = "", itemName = ""; // info for when we want to bid on certain item from the list
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bids);
        ListView myCurrentBidsListView = (ListView) findViewById(R.id.currentBidsListView);
        myCurrentBidsListView.setOnItemClickListener( // when we click on an element in the list we can bid on the item chosen
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            if (jsonList.get(position).get("isAuctionOver").toString().equals("true")) // checks if the auction ended if not continue with bidding
                                Toast.makeText(getApplicationContext(), "Can't bid: Auction has already ended.", Toast.LENGTH_LONG).show();
                            else {
                                itemName = jsonList.get(position).get("item_name").toString();
                                currentBid = jsonList.get(position).get("item_last_bid_price").toString();
                                Intent i = new Intent(getApplicationContext(), PlaceNewBidActivity.class);
                                i.putExtra("currentbid", currentBid);
                                i.putExtra("itemname", itemName);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        invokeViewMyOwnBidsRequest();
    }





    //request from the server to retrieve the list of items with bids i was participated in
    public void invokeViewMyOwnBidsRequest()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/users/" + globalUsername + "/auctions", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String response = new String(bytes, "UTF-8");
                    JSONArray jsonArray = new JSONArray(response);
                    jsonList = new ArrayList<JSONObject>();
                    for (int j = 0; j < jsonArray.length(); j++)
                        jsonList.add(jsonArray.getJSONObject(j));
                    ArrayList<String> searchedItemResultsAL = new ArrayList<String>();
                    for (int j = 0; j < jsonList.size(); j++) { // creating arraylist with each element containing the info about the items we received in order to display this information
                        try {
                            searchedItemResultsAL.add("Item Name: " + jsonList.get(j).get("item_name") + "\nItem Description: " + jsonList.get(j).get("item_desc") +
                            "\nCategory: " + jsonList.get(j).get("item_category") + "\nCurrent Bid: " + jsonList.get(j).get("item_latest_bid_price") +
                            "\nStarting price: " + jsonList.get(j).get("item_start_price") + "\nNumber of Bids: " + jsonList.get(j).get("item_num_bids") +
                            "\nLatest bid username: " + jsonList.get(j).get("item_latest_bid_username") + "\nLast Bid Time: " + jsonList.get(j).get("item_latest_bid_time").toString().substring(0,16));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ListAdapter usersAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, searchedItemResultsAL) // android.R.layout.simple_list_item_1
                    { // this adapter paints the background color to red of an item element which auction has already ended
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            try {
                                if (jsonList.get(position).get("isAuctionOver").toString().equals("true"))
                                    view.setBackgroundColor(Color.parseColor("#66F44336"));
                                else view.setBackgroundColor(Color.parseColor("#000000"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return view;
                        }
                    };
                    ListView itemListView = (ListView) findViewById(R.id.currentBidsListView);
                    itemListView.setAdapter(usersAdapter);
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
}
