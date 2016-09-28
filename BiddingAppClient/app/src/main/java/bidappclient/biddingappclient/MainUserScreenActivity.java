package bidappclient.biddingappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainUserScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_screen);
        Bundle usernameData = getIntent().getExtras();
        if (usernameData == null)
            return;
        String firstName = usernameData.getString("firstname");
        String lastName = usernameData.getString("lastname");
        final TextView helloText = (TextView) findViewById(R.id.helloTextView);
        helloText.setText("Welcome " + firstName + " " + lastName + ".");
    }


    public void onClickSearchItem (View view) {
        Intent i = new Intent (this, SearchingItemActivity.class);
        startActivity(i);
    }

    public void onClickAddItem (View view){
        Intent i = new Intent (this, AddItemActivity.class);
        startActivity(i);
    }

    public void onClickCurrentBids (View view) {
        Intent i = new Intent (this, CurrentBidsActivity.class);
        startActivity(i);
    }

    public void onClickViewProfile (View view){
        Intent i = new Intent (this, ViewProfileActivity.class);
        startActivity(i);
    }

    public void onClickViewCurrentAuctions (View view){
        Intent i = new Intent (this, ViewCurrentAuctionsActivity.class);
        startActivity(i);
    }

    public void onClickViewAllUsers (View view) {
        Intent i = new Intent (this, ViewAllUsersActivity.class);
        startActivity(i);
    }



}
