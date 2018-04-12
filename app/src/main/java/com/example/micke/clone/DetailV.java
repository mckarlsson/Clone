package com.example.micke.clone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
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

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap>
    {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_view);

        dview = findViewById(R.id.dVlv);
        // Get the access_token from the intent extra
        Intent i = this.getIntent();
        int id = i.getIntExtra("data", 0);
        dvAdapter = new dvAdapter(this, 0, data);
        dview.setAdapter(dvAdapter);


        Data data = FeedActivity.data.get(id);
        String urlStr = data.getImages().getStandard_resolution().getUrl();

        // You're not allowed to do network calls on the main app thread so schedule an async task.
        // Once that completes it shows the image.
        new DownloadImageTask((ImageView)findViewById((R.id.ivDetail))).execute(urlStr);

        Log.d(TAG, "onCreate: arrList "+ id);
        Log.d(TAG, "onCreate: data "+data);
    }
}
