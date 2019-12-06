package com.anth.redditbrowser;

import android.app.Application;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public final class RedditAPIHandler {

    private static final String searchSubredditURL =
            "https://www.reddit.com/subreddits/search.json?q=";
    private static final String searchPostURL =
            "https://www.reddit.com/search.json?q=";
    private static final String subredditHotURL1 = "https://www.reddit.com/r/";
    private static final String subredditHotURL2 = "/top.json?limit=40";
    private static final String commentURL =  "https://www.reddit.com/subreddits";

    private RedditAPIHandler() {
        // setting singleton class
    }
    public static JSONObject searchSubreddit (String q) {
        return getJSON(searchSubredditURL + q);
    }

    public static JSONObject searchPost(String q) {
        return getJSON(searchPostURL + q);
    }

    public static JSONObject subredditHot(String subreddit) {
        return getJSON(subredditHotURL1 + subreddit + subredditHotURL2);
    }

    public static JSONObject getPost(String permalink) {
        return getJSON(commentURL + permalink + ".json");
    }

    private static JSONObject getJSON(String urlString) {
        try {
            String json = "";
            String line;

            URL url = new URL(urlString);

            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            while ((line = in.readLine()) != null) {
                System.out.println("JSON LINE " + line);
                json += line;
            }
            in.close();
            JSONObject subredditResults = new JSONObject(json).getJSONObject("data");
            return subredditResults;
        } catch (Exception e) {
            System.out.println("\nerror found in retrieving JSONObject\n");
            e.printStackTrace();
        }
        return null;
    }



}
