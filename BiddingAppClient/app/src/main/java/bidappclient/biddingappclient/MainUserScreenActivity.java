package bidappclient.biddingappclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainUserScreenActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user_screen);
        Bundle userData = getIntent().getExtras();
        if (userData == null)
            return;
        String firstName  = userData.getString("firstname");
        String lastName  = userData.getString("lastname");
        String phoneNumber  = userData.getString("phonenumber");
        String email  = userData.getString("email");
        String creationDate  = userData.getString("creationdate");
        final TextView helloText = (TextView) findViewById(R.id.helloTextView);
        helloText.setText("Welcome " + firstName + " " + lastName + ".\nPhone number: " + phoneNumber +"   E-Mail: " + email + "\nCreation Date: " + creationDate);
    }


//    public void onClickSearchItem (View view) {
//        Intent i = new Intent (this, SearchingItemActivity.class);
//        startActivity(i);
//    }

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

    public void onClickAdminCommands (View view) {
        Intent i = new Intent (this, AdminCommandsActivity.class);
        startActivity(i);
    }

    public void onClickViewAllItems (View view)
    {
        Intent i = new Intent (this, ViewAllItemsActivity.class);
        startActivity(i);
    }

    public void onClickViewItem (View view)
    {
        Intent i = new Intent (this, ViewItemActivity.class);
        startActivity(i);
    }

    public void onClickViewItemsByCategory (View view)
    {
        Intent i = new Intent (this, ViewItemsByCategoryActivity.class);
        startActivity(i);
    }





}
