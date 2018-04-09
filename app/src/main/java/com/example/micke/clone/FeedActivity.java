package com.example.micke.clone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micke.clone.Utils.Data;
import com.example.micke.clone.Utils.InstagramResponse;
import com.example.micke.clone.Utils.RestClient;
import com.example.micke.clone.Utils.SimpleListViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "FeedActivity";
    private EditText etSearch;
    private ListView lvFeed;

    private SimpleListViewAdapter lvAdapter;
    private ArrayList<Data> data = new ArrayList<>();

    private String access_token = "";
    private boolean loaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        // Get the access_token from the intent extra
        Intent i = this.getIntent();
        access_token = i.getStringExtra("token");
        
        //Check Access token
        Log.d(TAG, "onCreate: "+access_token);

        lvFeed = (ListView) findViewById(R.id.lv_feed);
        //etSearch = (EditText) findViewById(R.id.et_search);

        // Set the listview adapter
        lvAdapter = new SimpleListViewAdapter(this, 0, data);
        lvFeed.setAdapter(lvAdapter);
        lvAdapter.setOnItemClickListener(this);



        // Set the listener for the "Done" button of the soft keyboard
       /** etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                // Don't search if the etSearch is empty when pressing the done button
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if(etSearch.getText().length() <= 0){
                        Toast.makeText(getApplicationContext(), "Enter a search tag", Toast.LENGTH_SHORT).show();

                    } else {
                        lvAdapter.clearListView();
                        fetchData(etSearch.getText().toString());
                        etSearch.setText("");
                        etSearch.clearFocus();
                        //Hopefully fetch recent
                        fetchRecent(access_token);
                    }

                    // Close the soft keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        }

        ); **/

    }

    public void fetchData(String tag) {
        Call<InstagramResponse> call = RestClient.getRetrofitService().getTagPhotos(tag, access_token);
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {

                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().length; i++){
                        data.add(response.body().getData()[i]);
                    }

                    lvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                //Handle failure
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void load_feed(View view) {

        if(loaded == false){

            fetchRecent(access_token);
            Log.d(TAG, "load_feed: "+access_token);
            loaded = true;
            findViewById(R.id.loader).setVisibility(View.GONE);

        }else{findViewById(R.id.loader).setVisibility(View.GONE);}

    }


    public void fetchRecent(String access_token) {

        Call<InstagramResponse> call = RestClient.getRetrofitService().getFeed(access_token, null, null, null);
        call.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {

                Log.d(TAG, "onResponse: "+response);
                if (response.body() != null) {
                    for(int i = 0; i < response.body().getData().length; i++){
                        data.add(response.body().getData()[i]);
                    }

                    lvAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {
                //Handle failure
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
