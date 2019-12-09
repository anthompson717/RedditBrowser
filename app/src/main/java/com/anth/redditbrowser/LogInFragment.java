package com.anth.redditbrowser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.util.Base64;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.loopj.android.http.AsyncHttpClient;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Handler;

public class LogInFragment extends Fragment {

    private String tokenURL = "https://www.reddit.com/api/v1/authorize?client_id=35XCcVTg-CoqyQ&response_type=code&state=";
    private String stateRandString = "true";
    private String continueURL = "&redirect_uri=https://github.com/anthompson717/RedditBrowser&duration=temporary&scope=*";
    String username = "";
    String password = "";
    String clientID = "";
    String secret = "";
    String stringURL = "https://www.reddit.com/api/v1/access_token";
    String[] code;
    Activity containerActivity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, container, false);
        super.onCreate(savedInstanceState);
        WebView a = v.findViewById(R.id.wv);
        a.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("code=")){
                code = url.split("code=");
                LoginSync i = new LoginSync();
                i.execute();}
                System.out.println(url);
                if(url=="https://www.reddit.com/"){
                    view.loadUrl(tokenURL + "new" + continueURL);

                    view.loadUrl(tokenURL + "new" + continueURL);}
                return false;
            }
        });
        a.loadUrl(tokenURL + "new" + continueURL);
        return v;
    }
    public void setContainerActivity(Activity containerActivity) {
        this.containerActivity = containerActivity;
    }

    class LoginSync extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                HttpURLConnection con = (HttpURLConnection) new URL("https://www.reddit.com/api/v1/access_token").openConnection();
                con.setRequestMethod("POST");
                String useCredentials = "35XCcVTg-CoqyQ:";
                String bassicAuth;
                bassicAuth = "Basic " + new String(Base64.encode(useCredentials.getBytes(), Base64.DEFAULT));
                con.setRequestProperty("Authorization", bassicAuth);
                con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64; rv:27.0)");
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String urlParams = "grant_type=authorization_code&code=" + code[1] + "&redirect_uri=https://github.com/anthompson717/RedditBrowser";

                con.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.writeBytes(urlParams);
                wr.flush();
                wr.close();

                int responscode = con.getResponseCode();
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
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", respString.getString("access_token"));
                editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        }
    }
}