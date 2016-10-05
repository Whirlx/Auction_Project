package bidappclient.biddingappclient;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView;
import Decoder.BASE64Encoder;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;



public class AddItemActivity extends BaseActivity {
    private ImageView pictureImageView; // here we will display the image
    private String pictureBytesString; // variable that contains the picture encoded (base64) into string
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Button addImageButton = (Button) findViewById(R.id.addPictureButtonId);
        pictureImageView = (ImageView) findViewById(R.id.itemPictureImageViewId);

        //disables camera is there isn't one
        if (!hasCamera())
            addImageButton.setEnabled(false);
    }


    // when u click on submit item
    public void onClickSubmitItem(View view) throws JSONException, UnsupportedEncodingException {
        //retrieve information filled in textviews
        TextView itemNameView = (TextView) findViewById(R.id.itemNameAddId);
        String itemNameString = itemNameView.getText().toString();
        TextView initialPriceView = (TextView) findViewById(R.id.initialPriceAddId);
        String initialPriceString = initialPriceView.getText().toString();
        TextView lastingBidView = (TextView) findViewById(R.id.bidLastingAddId);
        String lastingBidString = lastingBidView.getText().toString();
        TextView descriptionView = (TextView) findViewById(R.id.descriptionAddId);
        String descriptionString = descriptionView.getText().toString();

        //create json with the information of the item you'd like to add
        JSONObject addItemJSON = new JSONObject();
        addItemJSON.put("item_name", itemNameString);
        addItemJSON.put("item_start_price", initialPriceString);
        addItemJSON.put("item_desc", descriptionString);
        addItemJSON.put("duration_in_minutes", lastingBidString);
        addItemJSON.put("item_picture", pictureBytesString);

        // pass to screen of picking a category for the item who you're about to add
        Intent i = new Intent (this, AddItemChooseCategoryActivity.class);
        i.putExtra ("additemjson", addItemJSON.toString());
        startActivity(i);
    }

    public void navigateToMainScreenActivity()
    { // sending you the the main screen of app
        Intent i = new Intent (this, MainUserScreenActivity.class);
        startActivity(i);
    }

    public void onClickAddPicture(View view)
    {
        // take a picture and pass results
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult (intent, 1);
    }

    @Override // getting the picture and displaying it
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap picture = (Bitmap) extras.get("data");
            pictureImageView.setImageBitmap(picture);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] pictureBytes = stream.toByteArray(); // picture from bitmap to byte array
            pictureBytesString = new BASE64Encoder().encodeBuffer(pictureBytes); // byte array encoded into string
        }
    }

    //checks if the smartphone has a camera
    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
}
