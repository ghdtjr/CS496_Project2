package com.example.cs496_project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WritersFeed extends AppCompatActivity {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "Fragment3";
    String fragment3_result;

    RecyclerView recyclerView;
    RecyclerViewAdapter2 adapter;

    public static ArrayList<Feedphotos> feedphotos = new ArrayList<>();

    // 변수들
    String file_name="";
    String placeName="";
    String userID="";
    String like="";
    String contents="";
    String category="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writers_feed);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView4);

        // Get the all feedphotos from the server to "posts"
        retrofitInterface = RetrofitUtility.getRetrofitInterface();
        retrofitInterface.feed_get(getIntent().getExtras().toString()).enqueue(new Callback<ArrayList<Feedphotos>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedphotos>> call, Response<ArrayList<Feedphotos>> response) {
                if (response.isSuccessful()) {
                    for (Feedphotos feedphoto : response.body()){
                        feedphotos.add(feedphoto);
                        adapter.notifyDataSetChanged();
                    }
                    if (feedphotos == null) {
                    }
                } else {
                    int statusCode  = response.code();
                    Log.d(TAG, String.valueOf(statusCode));
                }
            }
            @Override
            public void onFailure(Call<ArrayList<Feedphotos>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        //RecyclerView Adapter 호출
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter2(this, feedphotos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this,
                        new LinearLayoutManager(this).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }
}