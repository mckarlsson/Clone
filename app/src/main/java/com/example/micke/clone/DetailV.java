package com.example.micke.clone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micke.clone.Utils.Data;
import com.example.micke.clone.Utils.InstagramResponse;
import com.example.micke.clone.Utils.RestClient;
import com.example.micke.clone.Utils.SimpleListViewAdapter;
import com.example.micke.clone.Utils.dvAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailV extends AppCompatActivity{
    private static final String TAG = "DetailV";

    private String access_token = "";
    private Context mContext = DetailV.this;
    private ListView dview;
    private dvAdapter dvAdapter;
    private ArrayList<Data> data = new ArrayList<>();
    private int item;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);


        dview = findViewById(R.id.dVlv);
        // Get the access_token from the intent extra
        Intent i = this.getIntent();
        Long id = i.getLongExtra("id", 0L);
        int itm = i.getIntExtra("item", item);

        dvAdapter = new dvAdapter(this, 0, data);
        dview.setAdapter(dvAdapter);




        Log.d(TAG, "onCreate: arrList "+id);
        Log.d(TAG, "onCreate: data "+data.toString());
        Log.d(TAG, "onCreate: itm "+itm);
        Picasso.with(mContext)
                .load(itm)
                .resize(100,100).into((ImageView) findViewById(R.id.ivDetail));

    }




}
