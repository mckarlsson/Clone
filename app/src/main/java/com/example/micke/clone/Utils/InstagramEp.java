package com.example.micke.clone.Utils;

import com.getinch.retrogram.Scope;
import com.getinch.retrogram.endpoints.CommentsEndpoint;
import com.getinch.retrogram.endpoints.LikesEndpoint;
import com.getinch.retrogram.endpoints.LocationsEndpoint;
import com.getinch.retrogram.endpoints.MediaEndpoint;
import com.getinch.retrogram.endpoints.RelationshipsEndpoint;
import com.getinch.retrogram.endpoints.TagsEndpoint;
import com.getinch.retrogram.endpoints.UsersEndpoint;

import java.net.URI;
import java.net.URISyntaxException;

import retrofit.RestAdapter;

public final class InstagramEp {

    private final String accessToken;
    private final RestAdapter.LogLevel logLevel;

    private UsersEndpoint usersEndpoint;
    private RelationshipsEndpoint relationshipsEndpoint;
    private MediaEndpoint mediaEndpoint;
    private CommentsEp commentsEndpoint;
    private LikesEndpoint likesEndpoint;
    private TagsEndpoint tagsEndpoint;
    private LocationsEndpoint locationsEndpoint;

    public InstagramEp(final String accessToken, final RestAdapter.LogLevel logLevel) {
        this.accessToken = accessToken;
        this.logLevel = logLevel;
    }

    public InstagramEp(final String accessToken) {
        this.accessToken = accessToken;
        this.logLevel = RestAdapter.LogLevel.NONE;
    }

    public UsersEndpoint getUsersEndpoint() {
        if (usersEndpoint == null) {
            usersEndpoint = new UsersEndpoint(accessToken, logLevel);
        }
        return usersEndpoint;
    }

    public RelationshipsEndpoint getRelationshipsEndpoint() {
        if (relationshipsEndpoint == null) {
            relationshipsEndpoint = new RelationshipsEndpoint(accessToken, logLevel);
        }
        return relationshipsEndpoint;
    }

    public MediaEndpoint getMediaEndpoint() {
        if (mediaEndpoint == null) {
            mediaEndpoint = new MediaEndpoint(accessToken, logLevel);
        }
        return mediaEndpoint;
    }

    public CommentsEp getCommentsEndpoint() {
        if (commentsEndpoint == null) {
            commentsEndpoint = new CommentsEp(accessToken);
        }
        return commentsEndpoint;
    }

    public LikesEndpoint getLikesEndpoint() {
        if (likesEndpoint == null) {
            likesEndpoint = new LikesEndpoint(accessToken, logLevel);
        }
        return likesEndpoint;
    }

    public TagsEndpoint getTagsEndpoint() {
        if (tagsEndpoint == null) {
            tagsEndpoint = new TagsEndpoint(accessToken, logLevel);
        }
        return tagsEndpoint;
    }

    public LocationsEndpoint getLocationsEndpoint() {
        if (locationsEndpoint == null) {
            locationsEndpoint = new LocationsEndpoint(accessToken, logLevel);
        }
        return locationsEndpoint;
    }

    public static String requestOAuthUrl(final String clientId, final String redirectUri, final Scope... scopes) throws URISyntaxException {
        final StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append("response_type=").append("token");
        urlBuilder.append("&client_id=").append(clientId);
        urlBuilder.append("&redirect_uri=").append(redirectUri);
        if (scopes != null) {
            final StringBuilder scopeBuilder = new StringBuilder();
            for (int i = 0; i < scopes.length; i++) {
                scopeBuilder.append(scopes[i]);
                if (i < scopes.length - 1) {
                    scopeBuilder.append(' ');
                }
            }
            urlBuilder.append("&scope=").append(scopeBuilder.toString());
        }
        return new URI("https", "instagram.com", "/oauth/authorize", urlBuilder.toString(), null).toString();
    }

}