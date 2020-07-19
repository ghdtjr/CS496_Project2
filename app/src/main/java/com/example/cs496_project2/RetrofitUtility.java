package com.example.cs496_project2;

public class RetrofitUtility {
    public static final String base_url = "http://192.249.19.243:0280";

    public static RetrofitInterface getRetrofitInterface() {
        return RetrofitClient.getClient(base_url).create(RetrofitInterface.class);
    }
}
