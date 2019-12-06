package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class SubRedditResults extends AppCompatActivity {

    public String url1 = "https://www.reddit.com/subreddits/search.json?q=";
    public String searchTerm = "";
    public ArrayList<String> args = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_reddit_results);
        Bundle extra = getIntent().getExtras();
        searchTerm = extra.getString("search");

        Subreddits untitled = new Subreddits();
        untitled.execute();
        //setAdapter();
    }

    private void setAdapter() {
        ListView tasksListView = (ListView)findViewById(R.id.subreddit_results);

        // Create a new Array Adapter
        // Specify which layout and view to use for a row
        // and the data (array) to use
        ArrayAdapter<String> taskArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_subreddit_item, R.id.subreddit_desc, args);

        // Link the ListView and the Adapter
        tasksListView.setAdapter(taskArrayAdapter);
        /*
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



    public void getHot(View v) {


    }

    private class Subreddits extends AsyncTask<Void, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                String json = "";
                String line;

                URL url = new URL(url1 + searchTerm);

                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                while ((line = in.readLine()) != null) {
                    System.out.println("JSON LINE " + line);
                    json += line;
                }
                in.close();
                JSONObject subredditResults = new JSONObject(json).getJSONObject("data");
                return subredditResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                JSONArray subslist = jsonObject.getJSONArray("children");
                for(int i = 0; i < subslist.length();i++){
                    JSONObject info = subslist.getJSONObject(i).getJSONObject("data");
                    String name = info.getString("display_name_prefixed");
                    String subs = info.getString("subscribers");
                    args.add(name + "\nSubscribers: " + subs);
                }
                setAdapter();
            }
            catch(Exception e){e.printStackTrace();}
        }
    }
}
