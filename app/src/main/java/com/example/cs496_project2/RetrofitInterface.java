package com.example.cs496_project2;

import android.net.ConnectivityDiagnosticsManager;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitInterface {

    @Multipart
    @POST("/user/register")
    Call<String> user_register(@Part MultipartBody.Part profile,
                               @Part("id")RequestBody id,
                               @Part("password")RequestBody password,
                               @Part("phone_number")RequestBody phone_number);

    @GET("/user/register/{newID}")
    Call<String> validID(@Path("newID") String newID);

    @POST("/user/login")
    Call<String> user_login(@Body LoginReq body);

    @GET("/user/login/get_profile/{id}")
    Call<String> get_profile(@Path("id") String id);
    
    @GET("/main/get")
    Call<ArrayList<Postings>> main_get();

    @POST("/main/write")
    Call<String> main_write(@Body PostingReq body);

    @GET("/main/{writer_id}")
    Call<Users> main_writer_id(@Path("writer_id") String writer_id);

    @GET("/gallery/post_all")
    Call<ArrayList<String>> post_all();

//    @POST("/gallery/post_on_category")

    @GET("/feed/get/{writer_id}")
    Call<ArrayList<Feedphotos>> feed_get(@Path("writer_id") String writer_id);

    @Multipart
    @POST("/feed/write")
    Call<String> feed_write(@Part MultipartBody.Part file,
                            @Part("place")RequestBody place,
                            @Part("id")RequestBody id,
                            @Part("like")RequestBody like,
                            @Part("contents")RequestBody contents,
                            @Part("category")RequestBody category);

}
