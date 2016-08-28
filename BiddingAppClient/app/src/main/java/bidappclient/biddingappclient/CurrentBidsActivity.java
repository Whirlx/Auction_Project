package bidappclient.biddingappclient;

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


public class CurrentBidsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bids);

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
