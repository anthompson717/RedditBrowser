package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class LogInFragment extends Fragment {

    private String tokenURL = "https://www.reddit.com/api/v1/authorize?client_id=RreNfw-0hG4xsA&response_type=code&state=";
    private String stateRandString = "";
    private String continueURL = "&redirect_uri=https://github.com/anthompson717/RedditBrowser&duration=permanent&scope=SCOPE_STRING";
    String username = "";
    String password = "";
    String clientID = "";
    String secret = "";
    String stringURL = "https://www.reddit.com/api/v1/access_token";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        super.onCreate(savedInstanceState);
        if (isSet()){skipLogin(null);}
        WebView a = v.findViewById(R.id.wv);
        a.loadUrl(tokenURL + "new" + continueURL);
        return v;
    }

    public void skipLogin(View v) {
        //Intent searchActivity = new Intent(this, MainActivity.class);
        //startActivity(searchActivity);
    }

    public boolean isSet(){
        return false;
    }
}