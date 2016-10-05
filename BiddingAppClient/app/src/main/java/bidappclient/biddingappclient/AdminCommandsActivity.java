package bidappclient.biddingappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


// this class contains all the buttons of options the admin can do, each button sends us to the appropriate intent (activity)
public class AdminCommandsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_commands);
    }

    public void onClickViewAllUsers(View view)
    {
        Intent i = new Intent (this, ViewAllUsersActivity.class);
        startActivity(i);
    }

    public void onClickViewSpecificUserProfile(View view)
    {
        Intent i = new Intent(this, SearchSpecificUserActivity.class);
        startActivity(i);
    }

    public void onClickDeleteUser(View view)
    {
        Intent i = new Intent (this, DeleteUserActivity.class);
        startActivity(i);
    }

    public void onClickAddItemCategory(View view)
    {
        Intent i = new Intent (this, AddItemCategoryActivity.class);
        startActivity(i);
    }

}
