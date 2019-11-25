package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class SubRedditResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_reddit_results);
        //setAdapter();
    }

    private void setAdapter() {

        final ListView subredditListview = (ListView) findViewById(R.id.subreddit_results);

        ArrayList<HashMap<String, String>> subredditList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hash = new HashMap<String, String>();

        String[] from = { }; //TODO: set the hash keys
        int[] to = { }; // TODO: set the layout_id names (some are set up but
                        //       feel to change them or add more on adapter_post_item.xml

        //TODO: set the content of the list & hashmap to place on adapter
        /*
        for(int i=0; i< ; i++) // TODO: set the size
        {
            hash.put(); // TODO: set the contents of Hashmap
            subredditList.add(hash);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, subredditList, R.layout.adapter_post_item, from, to);
        subredditListview.setAdapter(adapter);

        */
    }
}
