package bidappclient.biddingappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ListAdapter;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class ListOfSearchActivity extends BaseActivity {
    private ArrayList<JSONObject> list = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_search);
        Bundle itemData = getIntent().getExtras();
        if (itemData == null)
            return;
        String itemString = itemData.getString("searcheditem");
        invokeSearchingRequest(itemString); // now list object is full


        //searchedAuctionsInfoAL = new ArrayList<AuctionInfo>();
        ArrayList<String> displayAuctionInfoAL = new ArrayList<String>();
        for (int i = 0; i < list.size(); i++){
            try {
                displayAuctionInfoAL.add("Item ID: " + list.get(i).get("item_id") + "   Item Name: " + list.get(i).get("item_name") +
                        "   Current Bid: " + list.get(i).get("item_last_bid_price") + "   Description: " + list.get(i).get("item_desc") + "Remaining Time: " + list.get(i).get("update_time")
                        + "   Bid Made By: ");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        String[] auctionInfosToDisplay = new String[displayAuctionInfoAL.size()];
        auctionInfosToDisplay = displayAuctionInfoAL.toArray(auctionInfosToDisplay);
        ListAdapter auctionsInfoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, auctionInfosToDisplay);
        ListView searchedAuctionsListView = (ListView) findViewById(R.id.listViewId);
        searchedAuctionsListView.setAdapter(auctionsInfoAdapter);


        searchedAuctionsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String currentBid = "";
                        String itemId = "";
                        try {
                        JSONObject itemChosen = list.get(position);
                        currentBid = itemChosen.get("item_last_bid_price").toString();
                        itemId = itemChosen.get("item_id").toString();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        newBid(currentBid, itemId);
                    }
                }
        );


//        String[] items = {"Item ID: " + itemString + "   Current Bid: 50$   Time Left: 7 hours",
//                "Item ID: " + itemString + "   Current Bid: 43$   Time Left: 7 hours   Picture:",
//                "Item ID: " + itemString + "   Current Bid: 41$   Time Left: 4 hours   Picture:",
//                "Item ID: " + itemString + "   Current Bid: 52$   Time Left: 3 hours   Picture:",
//                "Item: ID " + itemString + "   Current Bid: 33$   Time Left: 9 hours   Picture:"};
//        ListAdapter itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        ListView itemListView = (ListView) findViewById(R.id.listViewId);
//        itemListView.setAdapter(itemsAdapter);


//        searchedAuctionsListView.setOnItemClickListener(
//            new AdapterView.OnItemClickListener(){
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    String itemChosen = String.valueOf(parent.getItemAtPosition(position));
//                    // here send to new screen with the id, ask how much would u like to bid, and send to server.
//                    Toast.makeText(ListOfSearchActivity.this, itemChosen, Toast.LENGTH_LONG).show();
//                }
//            }
//        );

    }

    public void invokeSearchingRequest(String itemName)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://localhost:8080/Auction_Server/items/" + itemName, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    System.out.println("@@@@@@"+response);
                    JSONArray itemResultsJSON = new JSONArray(response);
                    for(int j = 0; j < itemResultsJSON.length(); j++){
                        list.add(itemResultsJSON.getJSONObject(j));
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable)
            {
                Toast.makeText(getApplicationContext(), "Item not found.", Toast.LENGTH_LONG).show();
            } // need to add the json to the send request
        });
    }


    public void newBid (String currentBid, String itemId)
    {
        Intent i = new Intent (this, PlaceNewBidActivity.class);
        i.putExtra ("currentbid", currentBid);
        i.putExtra ("itemid", itemId);
        startActivity(i);
    }
}
