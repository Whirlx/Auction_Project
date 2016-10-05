package bidappclient.biddingappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//can change the ip of the server we connect to
public class ChangeIPActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_ip);
        TextView currentIP = (TextView) findViewById(R.id.currentIpTextViewId);
        currentIP.setText("Current IP: " + globalURL);
    }

    // button that when clicked changes the ip and notifies
    public void onClickSubmitNewIp(View view)
    {
        EditText newIpView = (EditText) findViewById(R.id.newIpEditTextId);
        String newIP = newIpView.getText().toString();
        globalURL = newIP;
        Toast.makeText(ChangeIPActivity.this, "IP Successfully changed to: " + newIP, Toast.LENGTH_LONG).show();
        Intent i = new Intent (this, LoginActivity.class);
        startActivity(i);
    }
}
