package com.example.micke.clone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.micke.clone.Utils.AuthenticationDialog;
import com.example.micke.clone.Utils.AuthenticationListener;
import com.example.micke.clone.Utils.Constants;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements AuthenticationListener{
    private AuthenticationDialog auth_dialog;
    SharedPreferences prefs = null;
    Button loginButton = null;
    String token = null;


    TextView tv_name = null;
    ImageView prof_pic = null;
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = (Button) findViewById(R.id.loginButton);
        tv_name = (TextView) findViewById(R.id.username);
        prof_pic = (ImageView) findViewById(R.id.prof_pic);
        //check for access token
        prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        token = prefs.getString("token", null);
        if (token != null){
            loginButton.setText("Logout");
            getUserInfoByAccessToken(token);
        }else {
            loginButton.setText("Log In");
            findViewById(R.id.profile_layout).setVisibility(View.GONE);
            findViewById(R.id.homeScreen).setVisibility(View.GONE);
        }

        getUserInfoByAccessToken(token);
    }

    private void getUserInfoByAccessToken(String token) {
        new RequestInstagramAPI().execute();

    }

    public void click_enter(View view) {
         token = prefs.getString("token", null);
        Intent intent = new Intent(mContext, FeedActivity.class);
        intent.putExtra("token", token);
        startActivity(intent);
        }


    private class RequestInstagramAPI extends AsyncTask<Void, String, String>{

        @Override
        protected String doInBackground(Void... voids) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Constants.GET_USER_INFO_URL+token);
            try{
                HttpResponse response = httpClient.execute(httpGet);
                HttpEntity httpEntity = response.getEntity();
                String json = EntityUtils.toString(httpEntity);
                return json;
            } catch (ClientProtocolException e){
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null){
                try{
                    JSONObject json = new JSONObject(response);
                    Log.e("response", json.toString());
                    JSONObject jsonData = json.getJSONObject("data");
                    if(jsonData.has("id")){
                        String id = jsonData.getString("id");
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("userId", id);
                        editor.apply();

                        String user_name = jsonData.getString("username");
                        String profile_pic = jsonData.getString("profile_picture");
                        tv_name.setText(user_name);
                        Picasso.with(MainActivity.this).load(profile_pic).into(prof_pic);

                        findViewById(R.id.profile_layout).setVisibility(View.VISIBLE);
                        findViewById(R.id.homeScreen).setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCodeReceived(String auth_token) {
        if (auth_token == null);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("token", auth_token);
        editor.apply();
        token = auth_token;
        loginButton.setText("Logout");
        getUserInfoByAccessToken(token);
    }

    public void after_click_login(View view) {
        if (token != null){
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            loginButton.setText("Log In");
            token = null;
            findViewById(R.id.profile_layout).setVisibility(View.GONE);
            findViewById(R.id.homeScreen).setVisibility(View.GONE);

        }else {
            auth_dialog = new AuthenticationDialog(this, this);
            auth_dialog.setCancelable(true);
            auth_dialog.show();
        }
    }

}
