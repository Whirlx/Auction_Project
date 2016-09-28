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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onClickSubmitRegistration(View view){
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
        User newRegisteredUser = new User (usernameString, passwordString, firstnameString, lastnameString, phoneString, emailString);
        Gson gson = new Gson();
        String registerUserJson = gson.toJson(newRegisteredUser);
        System.out.println(registerUserJson);
        //invokeRegistrationRequest(registerUserJson);

        //Toast.makeText(RegisterActivity.this, (usernameString+passwordString+firstnameString+lastnameString+phoneString+emailString), Toast.LENGTH_LONG).show();



        //Intent i = new Intent (this, MainUserScreenActivity.class);
        //startActivity(i);
    }

    public void invokeRegistrationRequest(String registerUserJson){
        // Show Progress Dialog
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://localhost:8080/Auction_Server/register", new AsyncHttpResponseHandler() { // need to add the json to the send request
            //should be like this: client.post(context, restApiUrl, entity, "application/json", responseHandler);

            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                navigateToMainScreenActivity("firstname123", "lastname123"); // need to take first and last name from the json registerUserJson
            }



            @Override
            public void onFailure(Throwable e) {
                Toast.makeText(getApplicationContext(), "Something went wrong with registration.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void navigateToMainScreenActivity(String firstName, String lastName)
    {
        Intent i = new Intent (this, MainUserScreenActivity.class);
        i.putExtra("firstname", firstName);
        i.putExtra("lastname", lastName);
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
