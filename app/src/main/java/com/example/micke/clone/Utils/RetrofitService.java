package com.example.micke.clone.Utils;

import com.getinch.retrogram.model.Comments;
import com.getinch.retrogram.model.DeleteCommentResponse;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("v1/tags/{tag_name}/media/recent")
    Call<InstagramResponse> getTagPhotos(@Path("tag_name") String tag_name,
                                         @Query("token") String token);

    @GET("v1/media/shortcode/%s")
    Call<InstagramResponse> getMediaId(
            @Query("access_token") String accessToken);

    @GET("v1/users/self/media/recent/")
    Call<InstagramResponse> getFeed(
            @Query("access_token") String accessToken,
            @Query("count") Integer count,
            @Query("min_id") String minId,
            @Query("max_id") String maxId);


    @GET("/media/{media_id}/comments")
    Call<InstagramResponse> getComments(
            @Path("media_id") String mediaId,
            @Query("access_token") String accessToken);

    @FormUrlEncoded
    @POST("/media/{media_id}/comments")
    Call<InstagramResponse> postComment(
            @Path("media_id") String mediaId,
            @Field("text") String text,
            @Query("access_token") String accessToken
    );

    @DELETE("/media/{media_id}/comments/{comment_id}")
    public DeleteCommentResponse delete(
            @Path("media_id") String mediaId,
            @Path("comment_id") String commentId,
            @Query("access_token") String accessToken
    );


}


