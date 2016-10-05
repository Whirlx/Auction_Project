package bidappclient.biddingappclient;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import cz.msebera.android.httpclient.Header;

// placing a new bid on an item class
public class PlaceNewBidActivity extends BaseActivity {
    private String currentBid; // will store the current bid of that item
    private String itemName; // the item on which we will bid
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_new_bid);

        Bundle currentBidData = getIntent().getExtras();
        if (currentBidData == null)
            return;
        currentBid = currentBidData.getString("currentbid");
        itemName = currentBidData.getString("itemname");

        TextView currentBidText = (TextView) findViewById(R.id.NewBidMinimumBidId);
        currentBidText.setText("Current bid is: " + currentBid);
    }

    //pressing the button to request a new bid from the server on that item
    public void onClickSubmitBid(View view){
        TextView newBidText = (TextView) findViewById(R.id.NewBidNewBidId);
        String newBid = newBidText.getText().toString();
            RequestParams bidParams = new RequestParams();
            bidParams.put("price", newBid);
            invokeNewBidOnItem(bidParams, itemName);
    }


    // sending the new bid request to the server
    public void invokeNewBidOnItem(RequestParams bidParams, String itemName)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/items/"+itemName+"/bid", bidParams, new AsyncHttpResponseHandler() { // deleted params
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    navigateToMainScreenActivity();
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
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void navigateToMainScreenActivity() {
        Intent i = new Intent(this, MainUserScreenActivity.class);
        startActivity(i);
    }
}
