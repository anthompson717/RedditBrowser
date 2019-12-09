package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class SearchActivity extends AppCompatActivity {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private boolean isSubreddit = true;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        getUsername();
        setContentView(R.layout.activity_search);
        String token = sharedPreferences.getString("token", "");
        System.out.println(token);
        Test test = new Test();
        test.execute();
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

        searchResult.putExtra("search", search);
        startActivity(searchResult);
    }

    public void getUsername() {
        Username doinb = new Username();
        doinb.execute();
    }

    public void post(View v){
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    class Username extends AsyncTask<Void, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                String token = sharedPreferences.getString("token", "");
                HttpURLConnection con = (HttpURLConnection) new URL("https://oauth.reddit.com/api/v1/me").openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0)");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("Authorization", "Bearer " + token);


                int responsecode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + "https://oauth.reddit.com/api/v1/me");
                System.out.println("Response Code : " + responsecode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                System.out.println(response.toString());
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject;
            }
            catch (Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                String username = json.getJSONObject("subreddit").getString("display_name_prefixed");
                TextView usernameBox = findViewById(R.id.username);
                usernameBox.setText("Welcome " + username);
                String karma = Integer.toString((Integer.parseInt(json.getString("comment_karma")) + Integer.parseInt(json.getString("link_karma"))));
                TextView karmaBox = findViewById(R.id.karma);
                karmaBox.setText("Karma: " + karma);
            }
            catch (Exception e){e.printStackTrace();}
        }
    }

    class Test extends AsyncTask<Void, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(Void... voids) {
            try {
                String token = sharedPreferences.getString("token", "");
                HttpURLConnection con = (HttpURLConnection) new URL("https://oauth.reddit.com/subreddits/mine/subscriber").openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0)");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestProperty("Authorization", "bearer " + token);


                int responsecode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + "https://oauth.reddit.com/api/v1/me");
                System.out.println("Response Code : " + responsecode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null){
                    response.append(inputLine);
                }
                in.close();
                JSONObject jsonObject = new JSONObject(response.toString());
                return jsonObject;
            }
            catch (Exception e){e.printStackTrace();}
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            System.out.println(json);
        }
    }

    public void info(View v){
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }
}
