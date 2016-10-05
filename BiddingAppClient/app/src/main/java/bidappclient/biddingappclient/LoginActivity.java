package bidappclient.biddingappclient;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import cz.msebera.android.httpclient.Header;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Username Edit View Object
    EditText userInput;
    // Passwprd Edit View Object
    EditText passwordInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Instantiate Progress Dialog object
        prgDialog = new ProgressDialog(this);
        // Set Progress Dialog Text
        prgDialog.setMessage("Please wait...");
        // Set Cancelable as False
        prgDialog.setCancelable(false);
        invokeWelcomeServerMessage(); // requesting the welcome message from server and popping it on the screen.
        userInput = (EditText) findViewById(R.id.usernameEditText);
        passwordInput = (EditText) findViewById(R.id.passwordEditText);
    }

    // when pressing the login button getting the username and password and requesting the server to login with these pair.
    public void onClickLogin(View view) throws IOException {
        String userString = userInput.getText().toString();
        String passwordString = passwordInput.getText().toString();
        invokeLoginRequest(userString, userString, passwordString);
    }

    //requesting the server to login with the username and password
    public void invokeLoginRequest(String requestedUser, String userString, String password) {
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(userString, password);
        client.get("http://" + globalURL + "/Auction_Server/users/" + requestedUser, new AsyncHttpResponseHandler() { // deleted params

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                // Hide Progress Dialog
                prgDialog.hide();
                try {
                    String response = new String(bytes, "UTF-8");
                    JSONObject obj = new JSONObject(response);
                    String greeting = "Logged in successfully\nWelcome " + obj.get("first_name") + " " + obj.get("last_name") + "!";
                    globalUsername = obj.get("user_name").toString();
                    globalPassword = obj.get("user_pwd").toString();
                    String firstName = obj.get("first_name").toString();
                    String lastName = obj.get("last_name").toString();
                    String phoneNumber = obj.get("phone_number").toString();
                    String email = obj.get("email").toString();
                    String creationDate = obj.get("insert_time").toString();
                    Toast.makeText(LoginActivity.this, greeting, Toast.LENGTH_LONG).show();
                    navigateToMainScreenActivity(firstName, lastName, phoneNumber, email, creationDate);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                prgDialog.hide();
                String response = "";
                if (i == 0)
                    Toast.makeText(getApplicationContext(), "Timed out: Could not connect to the server", Toast.LENGTH_LONG).show();
                else
                    try {
                        response = new String(bytes, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                }
        });
    }


    // pop up the server welcome message again
    public void onClickPopupMessage(View view) {
        invokeWelcomeServerMessage();
    }

    //sending us to change ip screen
    public void onClickChangeIP(View view)
    {
        Intent i = new Intent(this, ChangeIPActivity.class);
        startActivity(i);
    }


    // requesting the welcome message from server
    public void invokeWelcomeServerMessage() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://" + globalURL + "/Auction_Server/", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String response = null;
                try {
                    response = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Could not connect to server.", Toast.LENGTH_LONG).show();
            }
        });
    }


    //sends us to the register screen
    public void onClickRegister(View view) {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }

    //after loggin in successfully sending us to the main screen with all the user information
    public void navigateToMainScreenActivity(String firstName, String lastName, String phoneNumber, String email, String creationDate) {
        Intent i = new Intent(this, MainUserScreenActivity.class);
        i.putExtra("firstname", firstName);
        i.putExtra("lastname", lastName);
        i.putExtra("phonenumber", phoneNumber);
        i.putExtra("email", email);
        i.putExtra("creationdate", creationDate);
        startActivity(i);
    }

    public void navigateToMainScreenActivity() {
        Intent i = new Intent(this, MainUserScreenActivity.class);
        startActivity(i);
    }
}