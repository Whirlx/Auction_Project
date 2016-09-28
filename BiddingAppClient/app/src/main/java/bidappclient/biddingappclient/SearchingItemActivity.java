package bidappclient.biddingappclient;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.google.gson.Gson;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;



public class SearchingItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_item);
    }


    public void onClickSearchingItem(View view) {
        TextView searchedItemText = (TextView) findViewById(R.id.searchedItemTextId);
        String searchedItemString = searchedItemText.getText().toString();
        TextView searchingForText = (TextView) findViewById(R.id.searchingItemTextId);
        searchingForText.setText("Currently searching for: " + searchedItemString);
        // send searched item to server to receive details
        Intent i = new Intent(this, ListOfSearchActivity.class);
        i.putExtra("searcheditem", searchedItemString);
        startActivity(i);
        // sendSearchToServer (searchedItemString);
    }


    public void sendSearchToServer(String itemName) {

    }
}
    /*
    public void sendSearchToServer(String itemName) {
        String postUrlTo = "server ip";
        ItemInfo meh = new ItemInfo ("blergh", 50, 5, "very good blergh");
        Gson gson = new Gson();
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost(postUrlTo);
        StringEntity postingString = new StringEntity (); //gson.tojson() converts your pojo to json
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse response = httpClient.execute(post);
    }



    HttpClient httpClient = HttpClientBuilder.create().build();

        try {
            ItemInfo meh = new ItemInfo ("blergh", 50, 5, "very good blergh");
            HttpPost request = new HttpPost("our server ip");
            Gson gson = new Gson();
            StringEntity params = new StringEntity(gson.toJson(meh));
            request.addHeader("content-type", "application/x-www-form-urlencoded");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            // handle response here...
        } catch (Exception ex) {} finally {}}

    */

//}


