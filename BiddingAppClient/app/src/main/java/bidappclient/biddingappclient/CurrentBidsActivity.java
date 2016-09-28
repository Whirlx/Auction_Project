package bidappclient.biddingappclient;
import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CurrentBidsActivity extends AppCompatActivity {


    private ArrayList<AuctionInfo> bidsInfoAL;
    private String itemId; // **************** just pass through method
    private String currentBid; // **************** just pass through method

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bids);


        //send server request to receive all item bids.
        //receive json with all information. item id, item name, last bid price, last bid time, lasting auction time.
        //process info and enter into array:

        /*
        bidsInfoAL = new ArrayList<AuctionInfo>();
        ArrayList<String> displayInfoAL = new ArrayList<String>();
        for (int i = 0; i < bidsInfoAL.size(); i++){
            displayInfoAL.add("Item ID: " + bidsInfoAL.get(i).getItemId() + "   Item Name: " + bidsInfoAL.get(i).getItemName() +
            "   Current Bid: " + bidsInfoAL.get(i).getLastBidPrice() + "   Last Bid Time: " + bidsInfoAL.get(i).getTimeOfLastBid() + "   Remaining Time: " + bidsInfoAL.get(i).getRemainingAuctionTime()
            + "   Bid Made By: ");
        }

        String[] auctionInfosToDisplay = new String[displayInfoAL.size()];
        auctionInfosToDisplay = displayInfoAL.toArray(auctionInfosToDisplay);
        ListAdapter auctionsInfoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, auctionInfosToDisplay);
        ListView auctionsInfoListView = (ListView) findViewById(R.id.listViewId); // *************** IMPORTANT CHANGE ID *********************
        auctionsInfoListView.setAdapter(auctionsInfoAdapter);

        auctionsInfoListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        AuctionInfo bidInfoChosen = bidsInfoAL.get(position);
                        currentBid = bidInfoChosen.getLastBidPrice();
                        itemId = bidInfoChosen.getItemId();
                        //String itemChosen = String.valueOf(parent.getItemAtPosition(position));
                        // here send to new screen with the id, ask how much would u like to bid, and send to server.
                        //Toast.makeText(ListOfSearchActivity.this, itemChosen, Toast.LENGTH_LONG).show();
                    }
                }
        );

        Intent i = new Intent (this, PlaceNewBidActivity.class);
        i.putExtra ("currentbid", currentBid);
        i.putExtra ("itemid", itemId);
        startActivity(i);
       */

        String[] items = {"Item ID: blergh machine" + "   Current Bid: 50$   Time Left: 7 hours   Picture:",
                "Item ID: blerb machine" + "   Current Bid: 43$   Time Left: 7 hours   Picture:",
                "Item ID: flang" + "   Current Bid: 41$   Time Left: 4 hours   Picture:",
                "Item ID: bamba" + "   Current Bid: 52$   Time Left: 3 hours   Picture:",
                "Item: ID: charmander" + "   Current Bid: 33$   Time Left: 9 hours   Picture:"};
        ListAdapter itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
        ListView itemListView = (ListView) findViewById(R.id.listViewId);
        itemListView.setAdapter(itemsAdapter);

    }
}
