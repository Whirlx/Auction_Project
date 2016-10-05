package bidappclient.biddingappclient;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

// admin privelege to display all the users using the app
public class ViewAllUsersActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_users2);
          invokeViewAllUsersRequest();
    }

    // send the request to receive all users ifnormations and display them in a list
    public void invokeViewAllUsersRequest(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/users", new AsyncHttpResponseHandler() {

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
                            searchedItemResultsAL.add("Username: " + jsonList.get(j).get("user_name") +
                                    "\nPassword: " + jsonList.get(j).get("user_pwd") + "\nFirst name: " + jsonList.get(j).get("first_name") + "\nLast name: " + jsonList.get(j).get("last_name") +
                                    "\nEmail: " + jsonList.get(j).get("email") + "\nPhone number: " + jsonList.get(j).get("phone_number") + "\nLast login time: " + jsonList.get(j).get("last_login_time").toString().substring(0,16));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    ListAdapter usersAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, searchedItemResultsAL); // android.R.layout.simple_list_item_1
                    ListView itemListView = (ListView) findViewById(R.id.listAllUsersViewId);
                    itemListView.setAdapter(usersAdapter);
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }

        });
    }
}
