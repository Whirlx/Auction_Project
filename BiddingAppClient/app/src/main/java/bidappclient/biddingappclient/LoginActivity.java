package bidappclient.biddingappclient;

import java.net.URI;

//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import cz.msebera.android.httpclient.Header;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

//import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    // Progress Dialog Object
    ProgressDialog prgDialog;
    // Error Msg TextView Object
    TextView errorMsg;
    // Email Edit View Object
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
        invokeWelcomeServerMessage();

        userInput = (EditText) findViewById(R.id.usernameEditText);
        passwordInput = (EditText) findViewById(R.id.passwordEditText);
    }

    public void onClickLogin(View view) throws ClientProtocolException, IOException {
        String userString = userInput.getText().toString();
        String passwordString = passwordInput.getText().toString();
        //System.out.println ( userString + "    " + passwordString );
        // Instantiate Http Request Param Object
        RequestParams params = new RequestParams(); // not really needed for now.
        params.put("username", userString);
        params.put("password", passwordString);
        //invokeLoginRequest(params);
        invokeLoginRequest(userString, passwordString);

        /*
        Intent i = new Intent (this, MainUserScreenActivity.class);
        String userString = userInput.getText().toString();
        // add: send username and password to server for authentication.
        i.putExtra ("username", userString);
        startActivity(i);
        */
    }

    public void invokeLoginRequest(String username, String password){
        // Show Progress Dialog
        prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        //String address = "http://10.0.2.2:8080/Auction_Server/users/2/?username=" + username + "&password=" + password;
        //System.out.println (address);
        client.get("http://10.0.2.2:8080/Auction_Server/users/2/?username=" + username + "&password=" + password, new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                // Hide Progress Dialog
                prgDialog.hide();
                    try {
                        // JSON Object
                        System.out.println(response);
                        /*
                        String parsedJson = response.replace("\\" , "");
                        System.out.println(parsedJson);
                        StringBuilder sb = new StringBuilder(parsedJson);
                        sb.deleteCharAt(0);
                        sb.deleteCharAt(sb.length()-1);
                        parsedJson = sb.toString();
                        System.out.println(parsedJson);
                        */
                        JSONObject obj = new JSONObject(response);
                        String greeting = "Logged in successfully\nWelcome " + obj.get("first_name") + " " + obj.get("last_name") + "!";
                        Toast.makeText(LoginActivity.this, greeting, Toast.LENGTH_LONG).show();
                        navigateToMainScreenActivity(obj.get("first_name").toString(), obj.get("last_name").toString());
                        //System.out.println (obj.get("first_name"));
                        // Navigate to Home screen
                        //navigatetoHomeActivity();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                }



            @Override
            public void onFailure(Throwable e) {
                //super.onFailure(throwable, headers, errorResponse);
                // Hide Progress Dialog
                prgDialog.hide();

                /*
                // When Http response code is '404'
                if(statusCode == 404){
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if(statusCode == 500){
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                */

                //else{
                Toast.makeText(getApplicationContext(), "Wrong username or password! failed authentication.", Toast.LENGTH_LONG).show();
                //}
            }



        });
    }

    public void onClickPopupMessage (View view)
    {
        invokeWelcomeServerMessage();
    }



    public void invokeWelcomeServerMessage()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://10.0.2.2:8080/Auction_Server/" ,new AsyncHttpResponseHandler() {
            // When the response returned by REST has Http response code '200'
            @Override
            public void onSuccess(String response) {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_LONG).show();
            }
        });
    }




    public void onClickRegister(View view){
        Intent i = new Intent (this, RegisterActivity.class);
        startActivity(i);
    }

    public void navigateToMainScreenActivity(String firstName, String lastName)
    {
        Intent i = new Intent (this, MainUserScreenActivity.class);
        i.putExtra("firstname", firstName);
        i.putExtra("lastname", lastName);
        startActivity(i);
    }

}

















/*
    public void handleResponse(String response) {
        Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_LONG).show();
    }
*/


    /*
    private class ManageNetworking extends AsyncTask<String, Integer, String> {
        String responseToSend;

        public static final int POST_TASK = 1;
        public static final int GET_TASK = 2;

        private static final String TAG = "WebServiceTask";

        // connection timeout, in milliseconds (waiting to connect)
        private static final int CONN_TIMEOUT = 3000;

        // socket timeout, in milliseconds (waiting for data)
        private static final int SOCKET_TIMEOUT = 5000;

        private int taskType = GET_TASK;
        private Context mContext = null;
        private String processMessage = "Processing...";

        private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

        private ProgressDialog pDlg = null;

        public ManageNetworking(int taskType, Context mContext, String processMessage) {

            this.taskType = taskType;
            this.mContext = mContext;
            this.processMessage = processMessage;
        }

        public void addNameValuePair(String name, String value) {

            params.add(new BasicNameValuePair(name, value));
        }


        protected String doInBackground(String... urls) {

            String url = urls[0];
            String result = "";

            ClientConfig config = new ClientConfig();

            Client client = ClientBuilder.newClient(config);

            WebTarget target = client.target(getBaseURI());

            String response = target.path("rest").
                    path("hello").
                    request().
                    accept(MediaType.TEXT_PLAIN).
                    get(Response.class)
                    .toString();

            if (response == null) {
                return result;
            } else {

                try {

                    result = inputStreamToString(response.getEntity().getContent());

                } catch (IllegalStateException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);

                } catch (IOException e) {
                    Log.e(TAG, e.getLocalizedMessage(), e);
                }

            }

            return result;
        }

        @Override
        protected void onPostExecute(String response) {

            handleResponse(response);
            pDlg.dismiss();
        }
\*
        /*
        // Establish connection and socket (data retrieval) timeouts
        private HttpParams getHttpParams() {

            HttpParams htpp = new BasicHttpParams();

            HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
            HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

            return htpp;
        }
        */
/*
        private HttpResponse doResponse(String url) throws MalformedURLException, IOException {

            // Use our connection and data timeouts as parameters for our
            // DefaultHttpClient

            URL oracle = new URL(url);
            URLConnection yc = oracle.openConnection();

            HttpResponse response = null;

            try {
                switch (taskType) {

                    case POST_TASK:
                        HttpPost httppost = new HttpPost(url);
                        // Add parameters
                        httppost.setEntity(new UrlEncodedFormEntity(params));
                        response = yc.addRequestProperty();
                        //response = yc.execute(httppost);
                        break;
                    case GET_TASK:
                        HttpGet httpget = new HttpGet(url);
                        response = httpclient.execute(httpget);
                        break;
                }
            } catch (Exception e) {

                Log.e(TAG, e.getLocalizedMessage(), e);

            }

            return response;
        }

        private String inputStreamToString(InputStream is) {

            String line = "";
            StringBuilder total = new StringBuilder();

            // Wrap a BufferedReader around the InputStream
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));

            try {
                // Read response until the end
                while ((line = rd.readLine()) != null) {
                    total.append(line);
                }
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage(), e);
            }

            // Return full string
            return total.toString();
        }

    }
  */


 /*
        String sampleURL = "http://10.0.2.2:8080/Auction_Server/";
        ManageNetworking testNetworking = new ManageNetworking(ManageNetworking.GET_TASK, this, "GETting data...");
        testNetworking.execute(new String[] { sampleURL });
            // handle response here...
        */