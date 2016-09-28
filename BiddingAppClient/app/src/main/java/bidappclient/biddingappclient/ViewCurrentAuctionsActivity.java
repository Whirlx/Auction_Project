package bidappclient.biddingappclient;

import java.util.ArrayList;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ViewCurrentAuctionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_auctions);

        //send server request to receive all item bids.
        //receive json with all information. item id, item name, last bid price, last bid time, lasting auction time.
        //process info and enter into array:

        ArrayList<AuctionInfo> auctionsInfoAL = new ArrayList<AuctionInfo>();
        ArrayList<String> displayInfoAL = new ArrayList<String>();
        for (int i = 0; i < auctionsInfoAL.size(); i++){
            displayInfoAL.add("Item ID: " + auctionsInfoAL.get(i).getItemId() + "   Item Name: " + auctionsInfoAL.get(i).getItemName() +
                    "   Current Bid: " + auctionsInfoAL.get(i).getLastBidPrice() + "   Last Bid Time: " + auctionsInfoAL.get(i).getTimeOfLastBid() + "   Remaining Time: " + auctionsInfoAL.get(i).getRemainingAuctionTime());
        }
        /*
        String[] auctionInfosToDisplay = new String[displayInfoAL.size()];
        auctionInfosToDisplay = displayInfoAL.toArray(auctionInfosToDisplay);
        ListAdapter auctionsInfoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, auctionInfosToDisplay);
        ListView auctionsInfoListView = (ListView) findViewById(R.id.listViewId);
        auctionsInfoListView.setAdapter(auctionsInfoAdapter);
        */
    }
}
