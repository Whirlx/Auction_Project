package bidappclient.biddingappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class ViewAllItemsActivity extends BaseActivity {

    private ArrayList<JSONObject> jsonItemList; // = new ArrayList<JSONObject>;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_items);
        jsonItemList = new ArrayList<JSONObject>();
        invokeViewAllItemsRequest(); // now list object is full
        System.out.println ("#####################  " +  jsonItemList.size());
    }

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
                    System.out.println("@@@@@@"+response);
                    JSONArray itemResultsJSON = new JSONArray(response);
                    for(int j = 0; j < itemResultsJSON.length(); j++){
                        jsonItemList.add(itemResultsJSON.getJSONObject(j));
                        System.out.println ("^^^^^^^^^^^^^^^^^  " + jsonItemList.get(j).toString());
                    }
                    ArrayList<String> displayItemInfoAL = new ArrayList<String>();
                    for (int j = 0; j < jsonItemList.size(); j++){
                        try {
                            displayItemInfoAL.add("Item Name: " + jsonItemList.get(j).get("item_name") + "\nItem Description: " + jsonItemList.get(j).get("item_desc") +
                                    "\nCategory: " + jsonItemList.get(j).get("item_category") + "\nCurrent Bid: " + jsonItemList.get(j).get("item_latest_bid_price") +
                                    "\nNStarting price: " + jsonItemList.get(j).get("item_start_price") + "\nNumber of Bids: " + jsonItemList.get(j).get("item_num_bids") +
                                    "\nLatest bid username: " + jsonItemList.get(j).get("item_latest_bid_username") + "\nLast Bid Time: " + jsonItemList.get(j).get("item_latest_bid_time"));
                            System.out.println ("#####################" + displayItemInfoAL.get(j));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    String[] auctionInfosToDisplay = new String[displayItemInfoAL.size()];
                    auctionInfosToDisplay = displayItemInfoAL.toArray(auctionInfosToDisplay);
                    ListAdapter auctionsInfoAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, auctionInfosToDisplay); // simple_list_item_1, bidappclient.biddingappclient.R.layout.custom_listview
                    ListView searchedAuctionsListView = (ListView) findViewById(R.id.viewAllItemsListViewId);
                    searchedAuctionsListView.setAdapter(auctionsInfoAdapter);
                    searchedAuctionsListView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    String currentBid = "";
                                    String itemName = "";
                                    try {
                                        JSONObject itemChosen = jsonItemList.get(position);
                                        currentBid = itemChosen.get("item_latest_bid_price").toString();
                                        itemName = itemChosen.get("item_name").toString();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    newBid(currentBid, itemName);
                                }
                            }
                    );
                } catch (Exception e) {
                    // TODO Auto-generated catch block
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
            } // need to add the json to the send request
        });
    }


    public void newBid (String currentBid, String itemName)
    {
        Intent i = new Intent (this, PlaceNewBidActivity.class);
        i.putExtra ("currentbid", currentBid);
        i.putExtra ("itemname", itemName);
        startActivity(i);
    }




}
