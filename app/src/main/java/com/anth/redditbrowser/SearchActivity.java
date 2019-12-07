package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

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
        Log.i("token", sharedPreferences.getString("token", ""));

        Header[] headers = new Header[3];
        headers[0] = new BasicHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0)");
        headers[1] = new BasicHeader("Authorization", "bearer " + sharedPreferences.getString("token", ""));
        headers[2] = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");


        client.get(this, "https://oauth.reddit.com/api/v1/me", headers, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("response", response.toString());
                try {
                    String username = response.getString("name").toString();
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putString("username", username);
                    edit.commit();
                    System.out.println(username);
                } catch (JSONException j) {
                    j.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("response", errorResponse.toString());
                Log.i("statusCode", "" + statusCode);
            }
        });
    }
}
