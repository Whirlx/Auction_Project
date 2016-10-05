package bidappclient.biddingappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class AddItemCategoryActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_category);
    }

    // button to add the category name to the server's database
    public void onClickAddNewCategoryName(View view)
    {
        EditText newCategoryNameEditText = (EditText) findViewById(R.id.itemCategoryNameToAddOrDeleteId);
        String newCategoryName = newCategoryNameEditText.getText().toString();
        if (newCategoryName.contains(" ")) // checks for invalid input
            Toast.makeText(getApplicationContext(), "Cannot add category name with spacing. Try again without spaces.", Toast.LENGTH_LONG).show();
        else invokeAddNewCategoryRequest(newCategoryName, 1);
    }

    // button to delete the category name to the server's database
    public void onClickDeleteCategoryName (View view)
    {
        EditText newCategoryNameEditText = (EditText) findViewById(R.id.itemCategoryNameToAddOrDeleteId);
        String categoryNameToDelete = newCategoryNameEditText.getText().toString();
        if (categoryNameToDelete.contains(" ")) // checks for invalid input
            Toast.makeText(getApplicationContext(), "Cannot delete category name with spacing. Try again without spaces.", Toast.LENGTH_LONG).show();
        else invokeAddNewCategoryRequest(categoryNameToDelete, 0);
    }

    //sending to server the add or delete request
    public void invokeAddNewCategoryRequest(String categoryName, int addOrDelete){ // 0 to delete, 1 to add.
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        String path = "";
        if (addOrDelete == 1) // decided whether we wanted to delete or add the category name and changes the url accordingly
            path = "http://" + globalURL + "/Auction_Server/items/category/add/"+categoryName;
        else path = "http://" + globalURL + "/Auction_Server/items/category/"+categoryName +"/delete";
        AsyncHttpResponseHandler addOrDeleteHandler = returnHandler(path);
        if (addOrDelete == 1)
            client.post(path, addOrDeleteHandler); // the request to add the category name
        else client.delete(path, addOrDeleteHandler); // the request to delete the category name
    }



    public void navigateToMainScreenActivity()
    {
        Intent i = new Intent (this, MainUserScreenActivity.class);
        startActivity(i);
    }


    // hander that will either ask server add or delete upon request.
    public AsyncHttpResponseHandler returnHandler(String path) {
        AsyncHttpResponseHandler test = new AsyncHttpResponseHandler() {// deleted params
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                try {
                    String response = new String(bytes, "UTF-8");
                    System.out.println("@@@@@@" + response);
                    Toast.makeText(AddItemCategoryActivity.this, response, Toast.LENGTH_LONG).show();
                    navigateToMainScreenActivity();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                System.out.println("blergh");
                String response = null;
                try {
                    response = new String(bytes, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }

        };
        return test;
    }
}
