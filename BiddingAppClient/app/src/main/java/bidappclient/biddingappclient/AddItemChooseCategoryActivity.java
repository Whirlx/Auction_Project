package bidappclient.biddingappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

// displaying the categories in which we need to choose from for the item we're adding
public class AddItemChooseCategoryActivity extends BaseActivity {
    private ArrayList<JSONObject> jsonCategoryList; // json that holds the list of categories
    private String addItemJSONString; // holds information of the item
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item_choose_category);
        Bundle addItemJSONData = getIntent().getExtras();
        if (addItemJSONData == null)
            return;
        addItemJSONString = addItemJSONData.getString("additemjson");
        jsonCategoryList = new ArrayList<JSONObject>();
        invokeChooseCategory();
    }

    // method to request server to receive all categories we need to choose from
    public void invokeChooseCategory ()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth(globalUsername, globalPassword);
        client.get("http://" + globalURL + "/Auction_Server/items/category", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) // if server returned success
            {
                try {
                    String response = new String(bytes, "UTF-8");
                    JSONArray itemResultsJSON = new JSONArray(response);
                    for(int j = 0; j < itemResultsJSON.length(); j++){ // adding the category
                        jsonCategoryList.add(itemResultsJSON.getJSONObject(j));
                    }
                    ArrayList<String> displayCategoryNamesList = new ArrayList<String>();
                    for (int j = 0; j < jsonCategoryList.size(); j++){
                        try {
                            displayCategoryNamesList.add("Category name: " + jsonCategoryList.get(j).get("item_category_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    String[] CategoryInfoToDisplay = new String[displayCategoryNamesList.size()]; // string array in order to give to the arrayadapter
                    CategoryInfoToDisplay = displayCategoryNamesList.toArray(CategoryInfoToDisplay);
                    ListAdapter categoryNamesAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.custom_listview, CategoryInfoToDisplay); // android.R.layout.simple_list_item_1
                    ListView CategoriesListView = (ListView) findViewById(R.id.viewCategoriesListViewId);
                    CategoriesListView.setAdapter(categoryNamesAdapter); // displays the list with the categories
                    CategoriesListView.setOnItemClickListener(
                            new AdapterView.OnItemClickListener(){
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) { // when we click on one of the categories add it to the item add request
                                    try {
                                        JSONObject categoryChosen = jsonCategoryList.get(position);
                                        String categoryName = categoryChosen.get("item_category_name").toString();
                                        JSONObject addItemJson = new JSONObject(addItemJSONString);
                                        addItemJson.put("item_category", categoryName);
                                        invokeAddItem(addItemJson);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (UnsupportedEncodingException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                    );
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }


            }
            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) // when we received failure from the server
            {
                Toast.makeText(getApplicationContext(), "Something went wrong with retrieving item list.", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void invokeAddItem (JSONObject addItemJson) throws UnsupportedEncodingException { // final request from the server to add the item
        AsyncHttpClient client = new AsyncHttpClient();
        StringEntity entity = new StringEntity(addItemJson.toString());
        client.setBasicAuth(globalUsername, globalPassword);
        client.post(getApplicationContext(), "http://" + globalURL + "/Auction_Server/items/add", entity, "application/json", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes){
                try {
                    String response = new String(bytes, "UTF-8");
                    Toast.makeText(AddItemChooseCategoryActivity.this, response, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error Occured [Server's JSON response might be invalid]!", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
                navigateToMainScreenActivity();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
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

    // sending us to the main screen
    public void navigateToMainScreenActivity() {
        Intent i = new Intent(this, MainUserScreenActivity.class);
        startActivity(i);
    }

}
