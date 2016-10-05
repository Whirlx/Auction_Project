package bidappclient.biddingappclient;

import java.io.UnsupportedEncodingException;
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
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewCurrentAuctionsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_current_auctions);
        invokeViewAllMyAuctionsRequest();
    }

    public void invokeViewAllMyAuctionsRequest()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/users/" + globalUsername + "/items", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String response = new String(bytes, "UTF-8");
                    System.out.println("@@@@@@" + response);
                    JSONArray jsonArray = new JSONArray(response);
                    ArrayList<JSONObject> jsonList = new ArrayList<JSONObject>();
                    for (int j = 0; j < jsonArray.length(); j++)
                        jsonList.add(jsonArray.getJSONObject(j));
                    ArrayList<String> searchedItemResultsAL = new ArrayList<String>();
                    for (int j = 0; j < jsonList.size(); j++) {
                        try {
                            searchedItemResultsAL.add("Item Name: " + jsonList.get(j).get("item_name") + "\nItem Description: " + jsonList.get(j).get("item_desc") +
                                    "\nCategory: " + jsonList.get(j).get("item_category") + "\nCurrent Bid: " + jsonList.get(j).get("item_latest_bid_price") +
                                    "\nNStarting price: " + jsonList.get(j).get("item_start_price") + "\nNumber of Bids: " + jsonList.get(j).get("item_num_bids") +
                                    "\nLatest bid username: " + jsonList.get(j).get("item_latest_bid_username") + "\nLast Bid Time: " + jsonList.get(j).get("item_latest_bid_time")
                                    + "\nAuction duration in hours: " + jsonList.get(j).get("duration_in_hours"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //String[] usersStringArray = new String[list.size()];
                    //usersStringArray = list.toArray(usersStringArray);
                    ListAdapter usersAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, searchedItemResultsAL);
                    ListView itemListView = (ListView) findViewById(R.id.viewMyOwnAuctionListViewId);
                    itemListView.setAdapter(usersAdapter);
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
            }
        });
    }
}
