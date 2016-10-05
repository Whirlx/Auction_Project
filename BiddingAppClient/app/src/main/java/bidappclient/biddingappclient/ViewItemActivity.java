package bidappclient.biddingappclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;

import Decoder.BASE64Decoder;
import cz.msebera.android.httpclient.Header;

public class ViewItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
    }

    public void onClickSubmitViewItem(View view)
    {
        EditText itemToSearchView = (EditText) findViewById(R.id.itemToViewEditTextId);
        String itemToSearch = itemToSearchView.getText().toString();
        if (!itemToSearch.equals (""))
            invokeViewItem(itemToSearch);
        else Toast.makeText(getApplicationContext(), "No item has been entered", Toast.LENGTH_LONG).show();
    }

    public void invokeViewItem(String itemToSearch){
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/items/"+itemToSearch, new AsyncHttpResponseHandler() { // deleted params

            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes)
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    //System.out.println("@@@@@@"+response);
                    JSONObject obj = new JSONObject(response);
                    System.out.println("****************" + obj.toString());
                    String itemId = obj.get("item_id").toString();
                    String itemName = obj.get("item_name").toString();
                    String itemCategory = obj.get("item_category").toString();
                    String itemDescription = obj.get("item_desc").toString();
                    String itemStartPrice = obj.get("item_start_price").toString();
                    String itemLastBidPrice = obj.get("item_latest_bid_price").toString();
                    String itemPictureString = "";
                    if (!obj.isNull("item_picture")) {
                        itemPictureString = obj.get("item_picture").toString();
                        byte[] itemPictureBytes = new BASE64Decoder().decodeBuffer(itemPictureString);
                        System.out.println("@@@@@ 3333::: " + itemPictureBytes + " ###");
                        //System.out.println (itemPictureBytes.toString() + "    @@@@@" + itemPictureBytes.length);
                        Bitmap itemPictureBitmap = BitmapFactory.decodeByteArray(itemPictureBytes, 0, itemPictureBytes.length);
                        ImageView showItemPicture = (ImageView) findViewById(R.id.viewItemImageViewId);
                        showItemPicture.setImageBitmap(itemPictureBitmap);
                    }
                    //System.out.println("@@@@@ 4444::: "+itemPictureBitmap+" ###");
                    TextView fillUserInfo = (TextView) findViewById(R.id.viewItemInfoTextViewId);
                    fillUserInfo.setText("Item Name: " + obj.get("item_name") + "\nItem Description: " + obj.get("item_desc") +
                            "\nCategory: " + obj.get("item_category") + "\nCurrent Bid: " + obj.get("item_latest_bid_price") +
                            "\nStarting price: " + obj.get("item_start_price") + "\nNumber of Bids: " + obj.get("item_num_bids") +
                            "\nLatest bid username: " + obj.get("item_latest_bid_username") + "\nLast Bid Time: " + obj.get("item_latest_bid_time").toString().substring(0,16));

                    //navigateToMainScreenActivity(firstName, lastName, phoneNumber, email, creationDate);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
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
}
