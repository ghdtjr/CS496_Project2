package com.example.cs496_project2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @POST("/user/register")
    Call<String> user_register(@Body RegisterReq body);

    @POST("/user/login")
    Call<String> user_login(@Body LoginReq body);
    
    @GET("/main/get")
    Call<List<Postings>> main_get();

    @POST("/main/write")
    Call<Postings> main_write(@Body String body);

    @GET("/main/{writer_id}")
    Call<Users> main_writer_id(@Path("writer_id") String writer_id);

    /*
    @POST("/gallery/write")
    @GET("/gallery/post_all")
    @POST("/gallery/post_on_category")

    @GET("/feed/get")
    */
}
