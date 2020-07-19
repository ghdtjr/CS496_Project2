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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

// 동행 구함 !!
public class Fragment1 extends Fragment implements RecyclerViewAdapter.OnListItemLongSelectedInterface,RecyclerViewAdapter.OnListItemSelectedInterface{

    // 변수들
    String userID="";
    String placeName="";
    String date="";
    String category="";

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    ArrayList<Postings> posts = new ArrayList<>();

    public Fragment1() {
        // Required empty public constructor
    }
    public static Fragment1 newInstance(String param1, String param2) {
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onItemSelected(View v, int position) {
        RecyclerViewAdapter.Holder viewHolder = (RecyclerViewAdapter.Holder)recyclerView.findViewHolderForAdapterPosition(position);
        Toast.makeText(this.getContext(),  "Press long to call", Toast.LENGTH_SHORT).show();
        Log.d("test","long clicked");
        //v.setBackgroundColor(Color.BLUE);
        //팝업

    }

    @Override
    public void onItemLongSelected(View v, int position) {
        Log.d("tab1test","clicked");
        //Toast.makeText(getActivity().getApplicationContext(), position+" " , Toast.LENGTH_SHORT).show();
        //다이얼
        //String tel="tel:" + number;
        //Log.d("MY PHONE:",tel);
        //startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView1);

        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(getActivity().getApplicationContext(), posts, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity().getApplicationContext(),
                        new LinearLayoutManager(getActivity().getApplicationContext()).getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(dividerItemDecoration);

        // recyclerview Item 선택시 user의 연락처 정보 나타내는 팝업?? 보여줘야함

        ImageButton newContact = (ImageButton) v.findViewById(R.id.newPost);
        newContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent
                Context context2 = v.getContext();                                                    // Context 수정
                Intent newPostIntent = new Intent(context2, NewPost.class);
                startActivity(newPostIntent);
            }
        });

        return v;
    }
}