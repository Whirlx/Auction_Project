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

import java.util.ArrayList;

public class ListOfSearchActivity extends AppCompatActivity {
    private ArrayList<AuctionInfo> searchedAuctionsInfoAL;
    private ArrayList<ItemInfo> searchResultsAL = new ArrayList<ItemInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_search);
        Bundle itemData = getIntent().getExtras();
        if (itemData == null)
            return;
        String itemString = itemData.getString("searcheditem");
        invokeSearchingRequest(itemString);
        // send searched item to server and receive many item objects.
        /*
        searchedAuctionsInfoAL = new ArrayList<AuctionInfo>();
        ArrayList<String> displayAuctionInfoAL = new ArrayList<String>();
        for (int i = 0; i < searchedAuctionsInfoAL.size(); i++){
            displayAuctionInfoAL.add("Item ID: " + searchedAuctionsInfoAL.get(i).getItemId() + "   Item Name: " + searchedAuctionsInfoAL.get(i).getItemName() +
                    "   Current Bid: " + searchedAuctionsInfoAL.get(i).getLastBidPrice() + "   Remaining Time: " + searchedAuctionsInfoAL.get(i).getRemainingAuctionTime()
                    + "   Bid Made By: ");
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
                        AuctionInfo itemChosen = searchedAuctionsInfoAL.get(position);
                        String currentBid = itemChosen.getLastBidPrice();
                        String itemId = itemChosen.getItemId();
                        newBid(currentBid, itemId);
                    }
                }
        );


        */
        String[] items = {"Item ID: " + itemString + "   Current Bid: 50$   Time Left: 7 hours",
                "Item ID: " + itemString + "   Current Bid: 43$   Time Left: 7 hours   Picture:",
                "Item ID: " + itemString + "   Current Bid: 41$   Time Left: 4 hours   Picture:",
                "Item ID: " + itemString + "   Current Bid: 52$   Time Left: 3 hours   Picture:",
                "Item: ID " + itemString + "   Current Bid: 33$   Time Left: 9 hours   Picture:"};
        ListAdapter itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView itemListView = (ListView) findViewById(R.id.listViewId);
        itemListView.setAdapter(itemsAdapter);


        itemListView.setOnItemClickListener(
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String itemChosen = String.valueOf(parent.getItemAtPosition(position));
                    // here send to new screen with the id, ask how much would u like to bid, and send to server.
                    Toast.makeText(ListOfSearchActivity.this, itemChosen, Toast.LENGTH_LONG).show();
                }
            }
        );

    }

    public void invokeSearchingRequest(String itemName)
    {

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://localhost:8080/Auction_Server/TO-BE-DETERMINED/?itemName = ITEMNAME", new AsyncHttpResponseHandler() { // need to add the json to the send request

            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // parse json response into itemInfo objects and insert them to an arrayList
                searchResultsAL.add(null);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

            }



            @Override
            public void onFailure(Throwable e) {
                Toast.makeText(getApplicationContext(), "Item not found.", Toast.LENGTH_LONG).show();
            }
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
