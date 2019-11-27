package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LogInFragment extends AppCompatActivity {

    private String tokenURL = "https://www.reddit.com/api/v1/authorize?client_id=RreNfw-0hG4xsA&response_type=code&state=";
    private String stateRandString = "";
    private String tokenURL2 = "&redirect_uri=";
    private String redirectURL = "https://github.com/anthompson717/RedditBrowser";
    private String "&duration=permanent";
    "&scope=SCOPE_STRING";
    String username = "";
    String password = "";
    String clientID = "";
    String secret = "";
    String stringURL = "https://www.reddit.com/api/v1/access_token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_fragment);
        if (isSet()){skipLogin(null);}
    }

    public void skipLogin(View v) {
        Intent searchActivity = new Intent(this, MainActivity.class);
        startActivity(searchActivity);
    }

    public boolean isSet(){
        return false;
    }
}
