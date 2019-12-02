package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

public class MainActivity extends AppCompatActivity {

    private boolean isSubreddit = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);/*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LogInFragment fragment = new LogInFragment();
        fragmentTransaction.add(R.id.outer, fragment);
        fragmentTransaction.commit();*/
    }

    public void onRadioButtonClicked(View v){
        // Is the button now checked?
        boolean checked = ((RadioButton) v).isChecked();

// Check which radio button was clicked
        switch(v.getId()) {
            case R.id.radio_post:
                if (checked)
                    isSubreddit = true;
                break;
            case R.id.radio_subreddit:
                if (checked)
                    isSubreddit = false;
                break;
        }
    }

    public void search(View v) {
        EditText insert = findViewById(R.id.search_query);
        String search = insert.getText().toString();

        RadioButton radio = (RadioButton) findViewById(R.id.radio_subreddit);
        boolean isSubreddit = radio.isChecked();

        Intent searchResult;
        if (isSubreddit) {
            searchResult = new Intent(this, SubRedditResults.class);
        }
        else {
            searchResult = new Intent(this, PostResults.class);
        }
        //Todo: set up parameters before moving to intent.
        searchResult.putExtra("search", search);
        startActivity(searchResult);

    }
}
