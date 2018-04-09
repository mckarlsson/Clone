package com.example.micke.clone.Utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitServiceM {

    @GET("v1/tags/{tag_name}/media/recent")
    Call<InstagramResponse> getTagPhotos(@Path("tag_name") String tag_name,
                                         @Query("token") String token);
}

//https://api.instagram.com/v1/users/self/media/recent/?access_token=ACCESS-TOKEN