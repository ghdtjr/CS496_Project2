package com.example.cs496_project2;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitInterface {

    /*
    @POST("/user/register")
    Call<> user_register();


    @POST("/user/login")
    Call<> user_login();
    */

    @GET("/main/get")
    Call<List<Postings>> main_get();

    @POST("/main/write")
    Call<Postings> main_write();

    @GET("/main/{writer_id}")
    Call<Postings> main_writer_id();

    /*
    @POST("/gallery/write")
    @GET("/gallery/post_all")
    @POST("/gallery/post_on_category")

    @GET("/feed/get")
    */
}
