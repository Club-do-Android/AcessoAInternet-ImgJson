package com.example.pedro.jsonimg.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by pedro on 19/02/17.
 */

public interface RetrofitInterface {

    @GET("posts/")
    Call<List<Post>> getAllPosts();

    @GET("posts/{id}")
    Call<Post> getPost(@Path("id") int postId);

    @POST("posts")
    Call<List<Post>> testPosts(@Body Post post);

}
