package com.example.micke.clone.Utils;

import android.content.SharedPreferences;

import com.getinch.retrogram.model.Comments;
import com.getinch.retrogram.model.DeleteCommentResponse;

import retrofit.RestAdapter;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit2.Retrofit;

import android.content.Context;

import static android.content.Context.MODE_PRIVATE;
import static com.example.micke.clone.Utils.Constants.BASE_URL;
import static retrofit2.Retrofit.*;

public class CommentsEp {

    private static interface CommentsService {

    @GET("/media/{media_id}/comments")
    public Comments getComments(
            @Path("media_id") String mediaId,
            @Query("access_token") String accessToken);

    @FormUrlEncoded
    @POST("/media/{media_id}/comments")
    public Comments postComment(
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

    private final CommentsEp.CommentsService commentsService;


    String token = "";
    public CommentsEp(final String accessToken) {
        token =  accessToken;
        final Retrofit retrofit = new Builder().baseUrl(BASE_URL).build();
        commentsService = retrofit.create(CommentsEp.CommentsService.class);
    }

    public Comments getComments(final String mediaId) {
        return commentsService.getComments(mediaId, token);
    }

    public Comments comment(final String mediaId, final String text) {
        return commentsService.postComment(mediaId, text, token);
    }

    public boolean delete(final String mediaId, final String commentId) {
        return commentsService.delete(mediaId, commentId, token).isSuccessfull();
    }

}

