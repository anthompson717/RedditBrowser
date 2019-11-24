package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

public class PostResults extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_results);
        //setAdapter();
    }

    private void setAdapter() {

        final ListView postListview = (ListView) findViewById(R.id.post_results);

        ArrayList<HashMap<String, String>> postList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hash = new HashMap<String, String>();

        String[] from = { }; //TODO: set the hash keys
        int[] to = { }; // TODO: set the layout_id names (some are set up but
                        //       feel to change them or add more on adapter_post_item.xml

        //TODO: set the content of the list & hashmap to place on adapter
        /*
        for(int i=0; i< ; i++) // TODO: set the size
        {
            hash.put(); // TODO: set the contents of Hashmap
            postList.add(hash);
        }

        postListView.setAdapter(postList);

        */
    }
}
