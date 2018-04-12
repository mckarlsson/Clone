package com.example.micke.clone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micke.clone.Fragments.DetailView;
import com.example.micke.clone.Utils.Data;
import com.example.micke.clone.Utils.InstagramResponse;
import com.example.micke.clone.Utils.RestClient;
import com.example.micke.clone.Utils.SimpleListViewAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedActivity extends AppCompatActivity {
    private static final String TAG = "FeedActivity";

    private EditText etSearch;
    private ListView lvFeed;

    private SimpleListViewAdapter lvAdapter;
    private ArrayList<Data> data = new ArrayList<>();

    private String access_token = "";
    private boolean loaded = false;

    private Context mContext = FeedActivity.this;
    DetailView.viewPostListener mViewPostListener;

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
        lvFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            private String detailContent = null;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               /** TextView tv_user_fullname = (TextView) findViewById(R.id.tv_user_fullname);
                ImageView iv_photo = (ImageView) findViewById(R.id.iv_photo);
                ImageView iv_profile = (ImageView) findViewById(R.id.iv_profile); **/


                Log.d(TAG, "onItemClick: parent "+parent);
                Log.d(TAG, "onItemClick: view "+view);
                Log.d(TAG, "onItemClick: position "+position);
                Log.d(TAG, "onItemClick: id "+id);

                Log.d(TAG, "onItemClick: data "+data);
                Log.d(TAG, "onItemClick: lvApdapter "+lvAdapter);
                Intent intent = new Intent(mContext, DetailV.class);
                intent.putExtra("data", id);
                startActivity(intent);

                //mViewPostListener.onViewPost(parent, view, position, id);


            }
        });



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

   /** private void onItemClick(ListView lvFeed, String access_token) {

        public void onItemClick(AdapterView lvFeed, View view, int position, long id){
            Intent intent = new Intent(mContext, DetailV.class);
            intent.putExtra("token", access_token);
            startActivity(intent);
        }
    } **/


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

}
