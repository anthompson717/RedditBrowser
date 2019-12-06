package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PostResults extends AppCompatActivity {

    public String url1 = "https://www.reddit.com/search.json?q=";
    public String searchTerm = "";
    public String url2 = "&sort=new&limit=20";
    //public ArrayList<String> args = new ArrayList<String>();
    ListView tasksListView;
    Intent content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_results);
        //setAdapter();

        Bundle extra = getIntent().getExtras();
        searchTerm = extra.getString("search");

        content = new Intent(this, ContentActivity.class);

        Posts untitled = new Posts();
        untitled.execute();

    }



    private void setAdapter(JSONObject jsonObject) {


        tasksListView = (ListView)findViewById(R.id.post_results);

        // Create a new Array Adapter
        // Specify which layout and view to use for a row
        // and the data (array) to use
        //ArrayAdapter<String> taskArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_post_item, R.id.post_comment_counter, args);

        // Link the ListView and the Adapter
        //tasksListView.setAdapter(taskArrayAdapter);

        //final ListView subredditListview = (ListView) findViewById(R.id.subreddit_results);

        ArrayList<HashMap<String, String>> subredditList = new ArrayList<HashMap<String, String>>();

        String[] from = {"title","author","subreddit" };
        int[] to = {R.id.post_title, R.id.post_user, R.id.post_subreddit };

        try {
            JSONArray subslist = jsonObject.getJSONArray("children");
            for(int i = 0; i < subslist.length();i++){
                JSONObject info = subslist.getJSONObject(i).getJSONObject("data");
                HashMap<String, String> hash = new HashMap<String, String>();
                hash.put("title", info.getString("title"));
                hash.put( "author", info.getString("author"));
                hash.put("subreddit", info.getString("subreddit_name_prefixed"));
                hash.put("permalink", info.getString("permalink"));
                subredditList.add(hash);

            }
        }
        catch(Exception e){e.printStackTrace();}

        SimpleAdapter adapter = new SimpleAdapter(this, subredditList, R.layout.adapter_post_item, from, to);
        tasksListView.setAdapter(adapter);
    }

    private void setListListener() {
        tasksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item text from ListView
                HashMap<String,String> selectedItem = (HashMap) parent.getItemAtPosition(position);

                String permalink = selectedItem.get("permalink");

                content.putExtra("permalink", permalink );
                startActivity(content);
            }
        });
    }

    private class Posts extends AsyncTask<Void, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(Void... voids) {

            /*
            try {
                String json = "";
                String line;

                URL url = new URL(url1 + searchTerm + url2);

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

             */

            return RedditAPIHandler.searchPost(searchTerm);
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            setAdapter(jsonObject);
            setListListener();


            /*
            try {
                JSONArray subslist = jsonObject.getJSONArray("children");
                for(int i = 0; i < subslist.length();i++){
                    JSONObject info = subslist.getJSONObject(i).getJSONObject("data");
                    String postTitle = info.getString("title");
                    String author = info.getString("author");
                    String subreddit = info.getString("subreddit_name_prefixed");
                    args.add(postTitle + "\nu/" + author + "\n" + subreddit);
                }
                setAdapter();
            }
            catch(Exception e){e.printStackTrace();}
            */

        }
    }
}
