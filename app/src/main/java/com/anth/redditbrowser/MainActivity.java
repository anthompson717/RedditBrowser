package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //TODO: Check if access token is set in SharedPreferences, if it is auto login,  else send to login

    public void search(View v) {

        RadioButton radio = (RadioButton) findViewById(R.id.radio_subreddit);
        boolean isSubreddit = radio.isChecked();

        Intent searchResult;
        if (isSubreddit)
            searchResult = new Intent(this, SubRedditResults.class);
        else
            searchResult = new Intent(this, PostResults.class);

        //Todo: set up parameters before moving to intent.

        startActivity(searchResult);

    }
}
