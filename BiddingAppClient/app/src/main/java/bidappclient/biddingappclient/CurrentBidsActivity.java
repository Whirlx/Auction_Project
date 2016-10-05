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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class CurrentBidsActivity extends BaseActivity {

    private ArrayList<JSONObject> jsonList;
    private String currentBid = "", itemName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_bids);
        ListView myCurrentBidsListView = (ListView) findViewById(R.id.currentBidsListView);
        myCurrentBidsListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            if (jsonList.get(position).get("isAuctionOver").toString().equals("true"))
                                Toast.makeText(getApplicationContext(), "Can't bid: Auction has already ended.", Toast.LENGTH_LONG).show();
                            else {
                                itemName = jsonList.get(position).get("item_name").toString();
                                currentBid = jsonList.get(position).get("item_last_bid_price").toString();
                                System.out.println ("#############" + itemName + "#######" +currentBid );
                                Intent i = new Intent(getApplicationContext(), PlaceNewBidActivity.class);
                                i.putExtra("currentbid", currentBid);
                                i.putExtra("itemname", itemName);
                                startActivity(i);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
        invokeViewMyOwnBidsRequest();
    }





    public void invokeViewMyOwnBidsRequest()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/users/" + globalUsername + "/auctions", new AsyncHttpResponseHandler() {
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
                            "\nLatest bid username: " + jsonList.get(j).get("item_latest_bid_username") + "\nLast Bid Time: " + jsonList.get(j).get("item_latest_bid_time").toString().substring(0,16));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ListAdapter usersAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, searchedItemResultsAL) // android.R.layout.simple_list_item_1
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
                    ListView itemListView = (ListView) findViewById(R.id.currentBidsListView);
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
