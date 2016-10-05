package bidappclient.biddingappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RegisterActivity extends BaseActivity {
    private JSONObject jsonObject = new JSONObject();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickSubmitRegistration(View view) throws JSONException, UnsupportedEncodingException {
        // retrieiving all info the user just filled
        TextView usernameTextView = (TextView) findViewById(R.id.RegisterUsernameId);
        String usernameString = usernameTextView.getText().toString();
        TextView passwordStringTextView = (TextView) findViewById(R.id.RegisterPasswordId);
        String passwordString = passwordStringTextView.getText().toString();
        TextView firstnameTextView = (TextView) findViewById(R.id.RegisterFirstNameId);
        String firstnameString = firstnameTextView.getText().toString();
        TextView lastnameTextView = (TextView) findViewById(R.id.RegisterLastNameId);
        String lastnameString = lastnameTextView.getText().toString();
        TextView phoneStringTextView = (TextView) findViewById(R.id.RegisterPhoneNumberId);
        String phoneString = phoneStringTextView.getText().toString();
        TextView emailStringTextView = (TextView) findViewById(R.id.RegisterEmailId);
        String emailString = emailStringTextView.getText().toString();

        jsonObject.put("user_name", usernameString);
        jsonObject.put("user_pwd", passwordString);
        jsonObject.put("first_name", firstnameString);
        jsonObject.put("last_name", lastnameString);
        jsonObject.put("phone_number", phoneString);
        jsonObject.put("email", emailString);

        invokeRegistrationRequest();
    }

    // sending a regiration request to the server
    public void invokeRegistrationRequest() throws UnsupportedEncodingException {
        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonObject.toString());
        client.post(getApplicationContext(), "http://" + globalURL + "/Auction_Server/register", entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes){
                try {
                    String response = new String(bytes, "UTF-8");
                    globalUsername = jsonObject.get("user_name").toString();
                    globalPassword = jsonObject.get("user_pwd").toString();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    navigateToMainScreenActivity(); // need to take first and last name from the json registerUserJson
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
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

    // when sending to the main screen put the info the user filled in the global variables in order to remember.
    public void navigateToMainScreenActivity()
    {
        Intent i = new Intent (this, MainUserScreenActivity.class);
        try {
            i.putExtra("firstname", jsonObject.get("first_name").toString());
            i.putExtra("lastname", jsonObject.get("last_name").toString());
            i.putExtra("phonenumber", jsonObject.get("phone_number").toString());
            i.putExtra("email", jsonObject.get("email").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(i);
    }
}
