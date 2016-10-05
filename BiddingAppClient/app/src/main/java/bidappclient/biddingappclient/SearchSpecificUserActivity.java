package bidappclient.biddingappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;
public class SearchSpecificUserActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_specific_user);
        Bundle userData = getIntent().getExtras();
        if (userData != null) {
            String usernameToSearch = userData.get("username").toString();
            TextView messageView = (TextView) findViewById(R.id.searchUserTextViewId);
            messageView.setText("Information of the user who won the auction:");
            EditText usernameToSearchView = (EditText) findViewById(R.id.specificUsernameToSearchId);
            usernameToSearchView.setVisibility(View.GONE);
            Button searchUserButton = (Button) findViewById(R.id.searchProfileSpecificUserButtonId);
            searchUserButton.setVisibility(View.GONE);
                invokeSearchSpecificUser(usernameToSearch);

        }
    }
    public void onClickSearchSpecificProfile(View view)
    {
        EditText usernameToSearchView = (EditText) findViewById(R.id.specificUsernameToSearchId);
        String usernameToSearch ="";
            usernameToSearch = usernameToSearchView.getText().toString();
        if (!usernameToSearch.equals (""))
            invokeSearchSpecificUser(usernameToSearch);
        else Toast.makeText(getApplicationContext(), "No username has been entered", Toast.LENGTH_LONG).show();
    }

    public void invokeSearchSpecificUser(String usernameToSearch){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/users/"+usernameToSearch, new AsyncHttpResponseHandler() { // deleted params

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    System.out.println("@@@@@@"+response);
                    JSONObject obj = new JSONObject(response);
                    String userName = obj.get("user_name").toString();
                    String firstName = obj.get("first_name").toString();
                    String lastName = obj.get("last_name").toString();
                    String phoneNumber = obj.get("phone_number").toString();
                    String email = obj.get("email").toString();
                    String creationDate = obj.get("insert_time").toString();
                    TextView fillUserInfo = (TextView) findViewById(R.id.fillUserInfoSpecificUserId);
                    fillUserInfo.setText ("Username: "+ userName + "\nFirst name: " + firstName + "\nLast name: " + lastName + "\nPhone number: " + phoneNumber + "\nE-Mail: " + email + "\nAccount creation date: " + creationDate.substring(0,16));
                    //navigateToMainScreenActivity(firstName, lastName, phoneNumber, email, creationDate);
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
