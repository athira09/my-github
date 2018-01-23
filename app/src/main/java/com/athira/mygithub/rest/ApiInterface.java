package com.athira.mygithub.rest;

/**
 * Created by Athira.
 */

import com.athira.mygithub.model.AccessToken;
import com.athira.mygithub.model.Commit;
import com.athira.mygithub.model.Repository;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @Headers("Accept: application/json")
    @POST("login/oauth/access_token")
    @FormUrlEncoded
    Call<AccessToken> getAccessToken(
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret,
            @Field("code") String code
    );

    @GET("user/repos")
    Call<List<Repository>> getRepos(@Query("access_token") String token);

    @GET("repos/{owner}/{repo}/commits")
    Call<List<Commit>> getCommits(@Path("owner") String owner, @Path("repo") String repo);


}