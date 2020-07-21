package com.example.cs496_project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Fragment3 extends Fragment {

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

    public Fragment3() {
        // Required empty public constructor
    }
    public static Fragment3 newInstance(String param1, String param2) {
        Fragment3 fragment = new Fragment3();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /* TODO: get feedpost from writer_id, url array, feedpost array */
    /* IN feedpost item, like request and get */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_3, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView3);

        // Get the all feedphotos from the server to "posts"
        retrofitInterface = RetrofitUtility.getRetrofitInterface();
        retrofitInterface.feed_get(LoginActivity.user_ID).enqueue(new Callback<ArrayList<Feedphotos>>() {
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
        adapter = new RecyclerViewAdapter2(getActivity().getApplicationContext(), feedphotos);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity().getApplicationContext(),
                        new LinearLayoutManager(getActivity().getApplicationContext()).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        ImageButton newFeedPost = (ImageButton) v.findViewById(R.id.newFeedPost);
        newFeedPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent
                Context context2 = v.getContext();                                                    // Context 수정
                Intent newPostIntent = new Intent(context2, NewFeedPost.class);
                startActivity(newPostIntent);
            }
        });

        return v;
    }
}