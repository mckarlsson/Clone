package com.example.micke.clone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micke.clone.Utils.CommentsEp;
import com.example.micke.clone.Utils.Constants;
import com.example.micke.clone.Utils.Data;
import com.example.micke.clone.Utils.InstagramEp;
import com.example.micke.clone.Utils.InstagramResponse;
import com.example.micke.clone.Utils.RestClient;
import com.example.micke.clone.Utils.SimpleListViewAdapter;
import com.example.micke.clone.Utils.dvAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jinstagram.Instagram;
import org.jinstagram.entity.users.feed.MediaFeedData;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailV extends AppCompatActivity{
    private static final String TAG = "DetailV";

    private String access_token = "";
    private String media_id = "";
    private Context mContext = DetailV.this;
    private ListView dview;
    private dvAdapter dvAdapter;
    private SimpleListViewAdapter lvAdapter;
    private ArrayList<Data> data = new ArrayList<>();

    private class GetMediaId extends AsyncTask<Void, String, String>{

        @Override
        protected String doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(Constants.GET_USER_INFO_URL+access_token);
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

    }

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

        dview = findViewById(R.id.commentDetail);
        // Get the access_token from the intent extra
        Intent i = this.getIntent();
        int id = i.getIntExtra("data", 0);

        dvAdapter = new dvAdapter(this, 0, data);
        dview.setAdapter(dvAdapter);


        Data data = FeedActivity.data.get(id);
        String urlStr = data.getImages().getStandard_resolution().getUrl();
        String usrName = data.getUser().getFull_name();
        String profPic = data.getUser().getProfile_picture();

        // You're not allowed to do network calls on the main app thread so schedule an async task.
        // Once that completes it shows the image.
        new DownloadImageTask((ImageView)findViewById((R.id.ivDetail))).execute(urlStr);
        new DownloadImageTask((ImageView)findViewById((R.id.iv_profile_detail))).execute(profPic);
        TextView uName = (TextView) findViewById(R.id.iv_username);
        uName.setText(data.getUser().getFull_name());

        Log.d(TAG, "onCreate: arrList "+ id);
        Log.d(TAG, "onCreate: data "+data);
        InstagramEp instagram = new InstagramEp(access_token);
        CommentsEp comments = instagram.getCommentsEndpoint();
        Log.d(TAG, "onCreate: comments "+comments);
        Call<InstagramResponse> callmediaId = RestClient.getRetrofitService().getMediaId(access_token);
        callmediaId.enqueue(new Callback<InstagramResponse>() {
            @Override
            public void onResponse(Call<InstagramResponse> call, Response<InstagramResponse> response) {
                Log.d(TAG, "onResponse: mediaId response "+response);

            }

            @Override
            public void onFailure(Call<InstagramResponse> call, Throwable t) {

            }
        });
        Log.d(TAG, "onCreate: mediaId "+media_id);

    }

    public void fetchComments(String access_token) {

        Call<InstagramResponse> call = RestClient.getRetrofitService().getComments(media_id, access_token);
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
