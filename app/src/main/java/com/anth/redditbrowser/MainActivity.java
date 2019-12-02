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

    public void search(View v) {
        EditText insert = findViewById(R.id.search_query);
        String search = insert.getText().toString();

        RadioButton radio = (RadioButton) findViewById(R.id.radio_subreddit);
        boolean isSubreddit = radio.isChecked();

        Intent searchResult;
        if (isSubreddit) {
            searchResult = new Intent(this, SubRedditResults.class);
            searchResult.putExtra("search", search);
            startActivity(searchResult);
        }
        else
            searchResult = new Intent(this, PostResults.class);

        //Todo: set up parameters before moving to intent.

        startActivity(searchResult);

    }
}
