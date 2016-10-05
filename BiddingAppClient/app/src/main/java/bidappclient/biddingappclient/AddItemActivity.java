package bidappclient.biddingappclient;


import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
//import com.sun.jersey.core.util.Base64; // the base64 encode on server
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;


public class AddItemActivity extends BaseActivity {
    private ImageView pictureImageView;
    private String pictureBytesString;
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



    public void onClickSubmitItem(View view) throws JSONException, UnsupportedEncodingException {
        TextView itemNameView = (TextView) findViewById(R.id.itemNameAddId);
        String itemNameString = itemNameView.getText().toString();
        TextView initialPriceView = (TextView) findViewById(R.id.initialPriceAddId);
        String initialPriceString = initialPriceView.getText().toString();
        TextView lastingBidView = (TextView) findViewById(R.id.bidLastingAddId);
        String lastingBidString = lastingBidView.getText().toString();
        TextView descriptionView = (TextView) findViewById(R.id.descriptionAddId);
        String descriptionString = descriptionView.getText().toString();
        //TextView categoryView = (TextView) findViewById(R.id.AddItemCategoryId);
        //String categoryString = categoryView.getText().toString();

        JSONObject addItemJSON = new JSONObject();
        addItemJSON.put("item_name", itemNameString);
        //addItemJSON.put("item_category", categoryString);
        addItemJSON.put("item_start_price", initialPriceString);
        addItemJSON.put("item_desc", descriptionString);
        addItemJSON.put("duration_in_minutes", lastingBidString);
        //addItemJSON.put("item_picture", pictureBytes);
        addItemJSON.put("item_picture", pictureBytesString); // @@@@@@@@@@@@@ONE THAT SHOULD WORK
        Intent i = new Intent (this, AddItemChooseCategoryActivity.class);
        i.putExtra ("additemjson", addItemJSON.toString());
        startActivity(i);

    }

    public void navigateToMainScreenActivity()
    {
        Intent i = new Intent (this, MainUserScreenActivity.class);
        startActivity(i);
    }

    public void onClickAddPicture(View view)
    {
        // take a picture and pass results
        Intent intent = new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult (intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult (requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK)
        {
            Bundle extras = data.getExtras();
            Bitmap picture = (Bitmap) extras.get("data");
            pictureImageView.setImageBitmap(picture);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            picture.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] pictureBytes = stream.toByteArray();
            System.out.println("@@@@@ 1111::: "+pictureBytes+" ###");
            //String encodedString = org.apache.commons.codec.binary.Base64.encodeBase64String(pictureBytes);
            //pictureBytesString = Base64.encodeToString(pictureBytes, Base64.DEFAULT);
            pictureBytesString = new BASE64Encoder().encodeBuffer(pictureBytes);
            System.out.println("@@@@@ 2222::: "+pictureBytesString+" ###");
            //byte[] bla = Base64.decode(pictureBytesString, Base64.DEFAULT);
//            byte[] bla = new byte[0];
//            try {
//                bla = new BASE64Decoder().decodeBuffer(pictureBytesString);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            Bitmap test = BitmapFactory.decodeByteArray(bla, 0, bla.length);
//            pictureImageView.setImageBitmap(test);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }
}
