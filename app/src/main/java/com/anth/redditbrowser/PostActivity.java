package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.message.BasicHeader;

public class PostActivity extends AppCompatActivity {

    private String subreddit = null;
    private String title = null;
    private String content = null;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        sharedPreferences = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

    }

    public void submit(View v){
        subreddit = ((EditText) findViewById(R.id.subreddit)).getText().toString();
        title = ((EditText) findViewById(R.id.title)).getText().toString();
        content = ((EditText) findViewById(R.id.content)).getText().toString();
        Post post = new Post();
        post.execute();
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    class Post extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... voids) {/*
            try {
                String token = sharedPreferences.getString("token", "");

                HttpURLConnection con = (HttpURLConnection) new URL("https://oauth.reddit.com/api/submit").openConnection();
                con.setRequestMethod("POST");
                con.setRequestProperty("Authorization: ", token);
                con.setRequestProperty("User-Agent","Mozilla/5.0 ( compatible ) ");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String urlParams = "title=" + title + "sr=" + subreddit + "text=" + content;

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParams);
                wr.flush();
                wr.close();

                int responscode = con.getResponseCode();
                System.out.println(responscode);
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine = "";
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                //System.out.println(response.toString());
                System.out.println(response.toString());
                JSONObject respString = new JSONObject(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }*/

            String token = sharedPreferences.getString("token", "");
            Log.i("token", sharedPreferences.getString("token", ""));

            Header[] headers = new Header[2];
            headers[0] = new BasicHeader("User-Agent", "myRedditapp/0.1 by redditusername");
            headers[1] = new BasicHeader("Authorization", "bearer " + sharedPreferences.getString("token", ""));
            client.setBasicAuth("35XCcVTg-CoqyQ","");
            RequestParams requestParams = new RequestParams();
            requestParams.put("title", title);
            requestParams.put("sr", subreddit);
            System.out.println(content);
            requestParams.put("url", content);
            requestParams.put("kind", "link");
            requestParams.put("Authorization", "bearer " + token);
            requestParams.put("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0)");
            requestParams.put("Content-Type", "application/x-www-form-urlencoded");
            client.post("https://oauth.reddit.com/api/submit", requestParams, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers1, JSONObject response) {
                    System.out.println("WORKS!!!!!");
                }
                @Override
                public void onFailure(int statusCode,
                                      Header[] headers,
                                      String responseString,
                                      Throwable throwable){
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Log.i("test", responseString);
                }
                @Override
                public boolean getUseSynchronousMode() {
                    return false;
                }
            });
            return null;
        }
    }
}
