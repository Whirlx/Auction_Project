package bidappclient.biddingappclient;

import android.support.v7.app.AppCompatActivity;


// all activities extends from this activity
public abstract class BaseActivity extends AppCompatActivity
{
    // static global variables (contains information about the user)
    static String globalUsername;
    static String globalPassword;
    static String globalFirstName;
    static String globalLastName;
    static String globalPhoneNumber;
    static String globalEmail;
    static String globalURL = "10.0.2.2:8080";
}