package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    }

    private void setAuthor(String author) {
        TextView tv = (TextView) findViewById(R.id.content_author);
        tv.setText("u/" + author);
    }

    private void setSubreddit(String subreddit) {
        TextView tv = (TextView) findViewById(R.id.content_subreddit);
        tv.setText("r/" + subreddit);
    }

    private void setTitle(String title) {
        TextView tv = (TextView) findViewById(R.id.content_title);
        tv.setText(title);
    }

    private void setCommentList() {

        final ListView commentListview = (ListView) findViewById(R.id.content_comment_list);

        ArrayList<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> hash = new HashMap<String, String>();

        String[] from = { }; //TODO: set the hash keys
        int[] to = { }; // TODO: set the layout_id names (some are set up but
        //       feel to change them or add more on adapter_post_item.xml

        //TODO: set the content of the list & hashmap to place on adapter
        /*
        for(int i=0; i< ; i++) // TODO: set the size
        {
            hash.put(); // TODO: set the contents of Hashmap
            commentList.add(hash);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, postList, R.layout.adapter_comment_item, from, to);
        commentListView.setAdapter(adapter);

        */
    }
}
