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


public class PlaceNewBidActivity extends AppCompatActivity {

    private String currentBid;
    private String itemId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_new_bid);

        Bundle currentBidData = getIntent().getExtras();
        if (currentBidData == null)
            return;
        String currentBid = currentBidData.getString("currentbid");
        String itemId = currentBidData.getString("itemid");

        TextView minimumBidText = (TextView) findViewById(R.id.NewBidMinimumBidId);
        minimumBidText.setText("Note: minimum bid of " + (Integer.parseInt(currentBid) + 10));
    }

    public void onClickSubmitBid(View view){
        TextView newBidText = (TextView) findViewById(R.id.NewBidNewBidId);
        String newBid = newBidText.getText().toString();
        if (Integer.parseInt (newBid) >= (Integer.parseInt(currentBid) + 10)) {
            // sending new bid to server with currentBid and itemId;
            Intent i = new Intent (this, MainUserScreenActivity.class);
            startActivity(i);
        }
        else Toast.makeText(PlaceNewBidActivity.this, "Bid too low, minimum bid of: " + currentBid, Toast.LENGTH_LONG).show();
    }
}
