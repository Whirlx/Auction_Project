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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class PlaceNewBidActivity extends BaseActivity {

    private String currentBid;
    private String itemName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_new_bid);

        Bundle currentBidData = getIntent().getExtras();
        if (currentBidData == null)
            return;
        currentBid = currentBidData.getString("currentbid");
        itemName = currentBidData.getString("itemname");

        TextView minimumBidText = (TextView) findViewById(R.id.NewBidMinimumBidId);
        minimumBidText.setText("Note: minimum bid of " + (Integer.parseInt(currentBid) + 10));
    }

    public void onClickSubmitBid(View view){
        TextView newBidText = (TextView) findViewById(R.id.NewBidNewBidId);
        String newBid = newBidText.getText().toString();
        if (Integer.parseInt (newBid) >= (Integer.parseInt(currentBid) + 10)) {
            RequestParams bidParams = new RequestParams();
            bidParams.put("price", newBid);
            invokeNewBidOnItem(bidParams, itemName);
        }
        else Toast.makeText(PlaceNewBidActivity.this, "Bid too low, minimum bid of: " + currentBid, Toast.LENGTH_LONG).show();
    }


    public void invokeNewBidOnItem(RequestParams bidParams, String itemName)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);

        //String address = "http://10.0.2.2:8080/Auction_Server/users/2/?username=" + username + "&password=" + password;
        System.out.println ("@@@@@@@@@@@@@@@@  " + itemName);
        client.get("http://" + globalURL + "/Auction_Server/items/"+itemName+"/bid", bidParams, new AsyncHttpResponseHandler() { // deleted params
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    System.out.println("@@@@@@"+response);
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                    navigateToMainScreenActivity();
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
