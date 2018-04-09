package com.example.micke.clone.Utils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("v1/tags/{tag_name}/media/recent")
    Call<InstagramResponse> getTagPhotos(@Path("tag_name") String tag_name,
                                         @Query("token") String token);

    @GET("v1/users/{user_id}/media/recent")
    Call<InstagramResponse> getRecent(
            @Path("user_id") String userId,
            @Query("token") String token,
            @Query("count") Integer count,
            @Query("min_id") String minId,
            @Query("max_id") String maxId,
            @Query("min_timestamp") Long minTimestamp,
            @Query("max_timestamp") Long maxTimestamp);

    @GET("v1/users/self/media/recent/")
    Call<InstagramResponse> getFeed(
            @Query("access_token") String accessToken,
            @Query("count") Integer count,
            @Query("min_id") String minId,
            @Query("max_id") String maxId);
}

