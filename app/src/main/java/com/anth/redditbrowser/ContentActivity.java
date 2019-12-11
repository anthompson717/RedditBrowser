package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ContentActivity extends AppCompatActivity {

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);


        Bundle extra = getIntent().getExtras();
        String permalink = extra.getString("permalink");
        JsonRetriever retriever = new JsonRetriever();
        retriever.execute(permalink);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.exit_in, R.anim.exit_out);
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

    private String retrieveMainData(JSONArray jsonArray, String key) {

        String retValue = null;
        try {
            retValue = jsonArray.
                    getJSONObject(0).
                    getJSONObject("data").
                    getJSONArray("children").
                    getJSONObject(0).
                    getJSONObject("data").
                    getString(key);
        } catch (JSONException e) {
            System.out.println("\nsomething wrong with the post retrieval in json\n");
            e.printStackTrace();
        }
        return retValue;
    }

    private void setCommentList(JSONArray JSONCommentList) {

        // jsonArray[1].data.children[COMMENTINDEX].data.body

        final ListView commentListview = (ListView) findViewById(R.id.content_comment_list);

        ArrayList<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>();
        String[] from = { "comment_user", "comment_score", "comment_content"};
        int[] to = { R.id.comment_user, R.id.comment_score, R.id.comment_content};

        for(int i=0; i<JSONCommentList.length() && i < 70; i++)
        {
            try {
                HashMap<String, String> hash = new HashMap<String, String>();
                JSONObject comment = JSONCommentList.
                        getJSONObject(i).
                        getJSONObject("data");
                String author = "u/" + comment.getString("author");
                hash.put("comment_user", author);
                String score = ""+comment.getInt("score");
                hash.put("comment_score", score);
                String body = comment.getString("body");
                hash.put("comment_content", body);

                commentList.add(hash);
            } catch (JSONException e) {
                System.out.println("ERROR in comment retrieval");
                e.printStackTrace();
            }
        }

        SimpleAdapter adapter = new SimpleAdapter(this, commentList, R.layout.adapter_comment_item, from, to);
        commentListview.setAdapter(adapter);
    }

    private void setContent(JSONObject comment) {
        try {

            TextView tv = new TextView(getApplicationContext());
            LinearLayout.LayoutParams tvlp = new
                    LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            tv.setPadding(8,8,8,8);
            tv.setLayoutParams(tvlp);
            tv.setTextColor(Color.parseColor("#FF5700"));

            FrameLayout fl = (FrameLayout) findViewById(R.id.content_main);
            fl.addView(tv);


            if (comment.getBoolean("is_self")) {
                tv.setText("\nbody: "+comment.getString("body"));
                System.out.println(comment.getString("body"));
            } else {
                String url = comment.getString("url");
                tv.setClickable(true);
                tv.setMovementMethod(LinkMovementMethod.getInstance());
                String text = "<a href='"+url+"'> Go to link </a>";
                tv.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println("errors in json, printing json");
            System.out.println(comment.toString());
        }
    }

    public void sharePost(View v) {

        Intent contactList = new Intent(this, ContactListActivity.class);
        try {
            String url = jsonArray.
                    getJSONObject(0).
                    getJSONObject("data").
                    getJSONArray("children").
                    getJSONObject(0).
                    getJSONObject("data").getString("permalink");
            contactList.putExtra("url","https://www.reddit.com" + url );
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startActivity(contactList);
    }

    private class JsonRetriever extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... permalink) {
            return RedditAPIHandler.getPost(permalink[0]);
        }

        @Override
        protected void onPostExecute(JSONArray jsonObject) {

            jsonArray = jsonObject;
            setAuthor(retrieveMainData(jsonObject, "author"));
            setSubreddit(retrieveMainData(jsonObject, "subreddit"));
            setTitle(retrieveMainData(jsonObject, "title"));

            try {
                JSONArray comments = jsonObject.
                        getJSONObject(1).
                        getJSONObject("data").
                        getJSONArray("children");
                setCommentList(comments);
                setContent(jsonObject.
                        getJSONObject(0).
                        getJSONObject("data").
                        getJSONArray("children").
                        getJSONObject(0).
                        getJSONObject("data"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
