package bidappclient.biddingappclient;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ViewCurrentAuctionsActivity extends BaseActivity {
    private ArrayList<JSONObject> jsonList;
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
                    jsonList = new ArrayList<JSONObject>();
                    for (int j = 0; j < jsonArray.length(); j++)
                        jsonList.add(jsonArray.getJSONObject(j));
                    ArrayList<String> searchedItemResultsAL = new ArrayList<String>();
                    for (int j = 0; j < jsonList.size(); j++) {
                        try {
                            searchedItemResultsAL.add("Item Name: " + jsonList.get(j).get("item_name") + "\nItem Description: " + jsonList.get(j).get("item_desc") +
                                    "\nCategory: " + jsonList.get(j).get("item_category") + "\nCurrent Bid: " + jsonList.get(j).get("item_latest_bid_price") +
                                    "\nStarting price: " + jsonList.get(j).get("item_start_price") + "\nNumber of Bids: " + jsonList.get(j).get("item_num_bids") +
                                    "\nLatest bid username: " + jsonList.get(j).get("item_latest_bid_username") + "\nLast Bid Time: " + jsonList.get(j).get("item_latest_bid_time").toString().substring(0,16)
                                    + "\nAuction duration in hours: " + jsonList.get(j).get("duration_in_minutes"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //String[] usersStringArray = new String[list.size()];
                    //usersStringArray = list.toArray(usersStringArray);
                    ListAdapter usersAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, searchedItemResultsAL)
                    {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View view = super.getView(position, convertView, parent);
                            try {
                                System.out.println (" @@@@@@@@@@@@### " + jsonList.get(position).get("isAuctionOver"));
                                if (jsonList.get(position).get("isAuctionOver").toString().equals("true"))
                                    view.setBackgroundColor(Color.parseColor("#66F44336"));
                                else view.setBackgroundColor(Color.parseColor("#000000"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            return view;
                        }
                    };
                    ListView itemListView = (ListView) findViewById(R.id.viewMyOwnAuctionListViewId);
                    itemListView.setAdapter(usersAdapter);
                    itemListView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    try {
                                        JSONObject itemChosen = jsonList.get(position);
                                        if (itemChosen.get("isAuctionOver").toString().equals("true")) {
                                            if (itemChosen.get("item_latest_bid_username").toString().equals("-"))
                                                Toast.makeText(getApplicationContext(), "No one bid on that auction and it has ended.", Toast.LENGTH_LONG).show();
                                            else {
                                                Toast.makeText(getApplicationContext(), "Displaying info of the user who won the auction!", Toast.LENGTH_LONG).show();
                                                Intent i = new Intent(getApplicationContext(), SearchSpecificUserActivity.class);
                                                i.putExtra("username", itemChosen.get("item_latest_bid_username").toString());
                                                System.out.println("#@$@#$#@   " + itemChosen.get("item_latest_bid_username").toString());
                                                startActivity(i);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
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
