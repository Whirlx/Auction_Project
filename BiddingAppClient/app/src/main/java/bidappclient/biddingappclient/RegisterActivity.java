package bidappclient.biddingappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.StringEntity;

public class RegisterActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickSubmitRegistration(View view) throws JSONException, UnsupportedEncodingException {
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
//        User newRegisteredUser = new User (usernameString, passwordString, firstnameString, lastnameString, phoneString, emailString);
//        Gson gson = new Gson();
//        String registerUserJson = gson.toJson(newRegisteredUser);
//        System.out.println(registerUserJson);

//        RequestParams params = new RequestParams();
//        params.put("user_name", usernameString);
//        params.put("user_pwd", passwordString);
//        params.put("first_name", firstnameString);
//        params.put("last_name", lastnameString);
//        params.put("phone_number", phoneString);
//        params.put("email", emailString);
//        params.setUseJsonStreamer(true);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_name", usernameString);
        jsonObject.put("user_pwd", passwordString);
        jsonObject.put("first_name", firstnameString);
        jsonObject.put("last_name", lastnameString);
        jsonObject.put("phone_number", phoneString);
        jsonObject.put("email", emailString);
        invokeRegistrationRequest(jsonObject);

        //Toast.makeText(RegisterActivity.this, (usernameString+passwordString+firstnameString+lastnameString+phoneString+emailString), Toast.LENGTH_LONG).show();



        //Intent i = new Intent (this, MainUserScreenActivity.class);
        //startActivity(i);
    }

    public void invokeRegistrationRequest(final JSONObject jsonObject) throws UnsupportedEncodingException {
        // Show Progress Dialog
        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(jsonObject.toString());

        client.post(getApplicationContext(), "http://" + globalURL + "/Auction_Server/register", entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes){
                try {
                    String response = new String(bytes, "UTF-8");
                    globalUsername = jsonObject.get("user_name").toString();
                    globalPassword = jsonObject.get("user_pwd").toString();
                    globalFirstName = jsonObject.get("first_name").toString();
                    globalLastName = jsonObject.get("last_name").toString();
                    Toast.makeText(RegisterActivity.this, response, Toast.LENGTH_LONG).show();
                    navigateToMainScreenActivity(); // need to take first and last name from the json registerUserJson
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("@@@@@@@@@@@@"+i);
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

    public void navigateToMainScreenActivity()
    {
        Intent i = new Intent (this, MainUserScreenActivity.class);
        startActivity(i);
    }




    /*
    public void sendUserToServer(User newRegistered) {
        HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            HttpPost request = new HttpPost("http://localhost:8080/Auction_Server/register");
            Gson gson = new Gson();
            StringEntity params = new StringEntity(gson.toJson(newRegistered));
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            // handle response here...
        } catch (Exception ex) {} finally {}
    }
    */

}
