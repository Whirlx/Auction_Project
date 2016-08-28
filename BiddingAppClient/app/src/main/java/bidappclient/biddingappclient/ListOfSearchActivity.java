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

public class ListOfSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_search);
        Bundle itemData = getIntent().getExtras();
        if (itemData == null)
            return;
        String itemString = itemData.getString("searcheditem");
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
}
