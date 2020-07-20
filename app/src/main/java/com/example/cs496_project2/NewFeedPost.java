package com.example.cs496_project2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class NewFeedPost extends AppCompatActivity {
    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "NewFeedPosting Activity";
    String newfeedposting_result;

    String Place="";
    Number like = 0;
    String Contents="";
    String Category="";
    EditText newPlaceET,newDateET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed_post);
    }
}