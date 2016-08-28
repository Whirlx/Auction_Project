package bidappclient.biddingappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickLogin(View view){
        Intent i = new Intent (this, MainUserScreenActivity.class);
        final EditText userInput = (EditText) findViewById(R.id.usernameEditText);
        String userString = userInput.getText().toString();
        // add: send username and password to server for authentication.
        i.putExtra ("username", userString);
        startActivity(i);
    }
}
