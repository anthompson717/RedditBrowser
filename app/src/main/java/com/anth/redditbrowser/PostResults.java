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
import org.json.JSONException;
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
    Boolean enhance = null;
    String subreddit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
        setContentView(R.layout.activity_post_results);
        //setAdapter();

        Bundle extra = getIntent().getExtras();
        searchTerm = extra.getString("search");
        enhance = extra.getBoolean("enhance");

        content = new Intent(this, ContentActivity.class);

        Posts untitled = new Posts();
        untitled.execute();

    }

    private void setAdapter(JSONObject jsonObject) {

        tasksListView = (ListView)findViewById(R.id.post_results);

        ArrayList<HashMap<String, String>> subredditList = new ArrayList<HashMap<String, String>>();
System.out.println(jsonObject);
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

            JSONObject returnJSON = null;

            try {
                if (!enhance) {
                    returnJSON = RedditAPIHandler.searchPost(searchTerm).getJSONObject("data");
                }
                else{
                    returnJSON = RedditAPIHandler.searchSubreddit(searchTerm).getJSONObject("data");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return  returnJSON;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {

            setAdapter(jsonObject);
            setListListener();

        }
    }
}
