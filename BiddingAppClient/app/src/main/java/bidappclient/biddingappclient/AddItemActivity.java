package bidappclient.biddingappclient;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class AddItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
    }

    public void onClickSubmitItem(View view)
    {
        TextView itemNameView = (TextView) findViewById(R.id.itemNameAddId);
        String itemNameString = itemNameView.getText().toString();
        TextView initialPriceView = (TextView) findViewById(R.id.initialPriceAddId);
        String initialPriceString = initialPriceView.getText().toString();
        TextView lastingBidView = (TextView) findViewById(R.id.bidLastingAddId);
        String lastingBidString = lastingBidView.getText().toString();
        TextView descriptionView = (TextView) findViewById(R.id.descriptionAddId);
        String descriptionString = descriptionView.getText().toString();
        String summary = "Your item name is: " + itemNameString + ", your initial price is " + initialPriceString +", your bid will last: " + lastingBidString +", and your description for the item is: " + descriptionString +".";
        Toast.makeText(AddItemActivity.this, summary, Toast.LENGTH_LONG).show();
        ItemInfo item = new ItemInfo (itemNameString, Integer.parseInt(initialPriceString), Integer.parseInt(lastingBidString), descriptionString);
        //send info to server to store;
    }
}
