package bidappclient.biddingappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;


// admin privilege to ask from the server to delete certain user
public class DeleteUserActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);
    }

    //clicking on the delete button will make a request from the server to delete
    public void onClickDeleteUser(View view)
    {
        EditText usernameToDeleteView = (EditText) findViewById(R.id.usernameDeleteUserId);
        String usernameToDeleteString = usernameToDeleteView.getText().toString();
        invokeDeleteUser(usernameToDeleteString);
    }

    // requesting from the server to delete the user
    public void invokeDeleteUser(String usernameToDelete)
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.delete("http://" + globalURL + "/Auction_Server/users/" + usernameToDelete + "/delete", new AsyncHttpResponseHandler() { // deleted params
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    Toast.makeText(DeleteUserActivity.this, response, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                navigateToMainScreenActivity();
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

    //sending us to main screen
    public void navigateToMainScreenActivity() {
        Intent i = new Intent(this, MainUserScreenActivity.class);
        startActivity(i);
    }

}
