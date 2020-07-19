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
import android.widget.Toast;

import java.util.ArrayList;

// 여행지 / 맛집 / 술집 갤러리
public class Fragment2 extends Fragment implements RecyclerViewAdapter.OnListItemLongSelectedInterface,RecyclerViewAdapter.OnListItemSelectedInterface{

    //어떤 변수들 ?? => 다시 정해서 넣어줘야함

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    ArrayList<Postings> posts = new ArrayList<>();

    public Fragment2() {
        // Required empty public constructor
    }
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

//일단 필요없음
//    @Override
//    public void onItemSelected(View v, int position) {
//        Toast.makeText(getActivity().getApplicationContext(), position+" pressed!" , Toast.LENGTH_SHORT).show();
//
//        //intent로 사용자 정보 보여주기
//        //intent 에서 사용자 연락처 보여주고, 다이얼 혹은 메세지 전송 기능 필요
//        //Intent fullScreenIntent=new Intent(getActivity().getApplicationContext(), FullScreenActivity.class); //=> 사진 선택시 자신 확대 기능, 이거 수정해보장
//        //fullScreenIntent.putExtra("imgPath", position);
//        //v.imageView.getContext().startActivity(fullScreenIntent);
//    }
//
//    @Override
//    public void onItemLongSelected(View v, int position) {
//        Toast.makeText(getActivity().getApplicationContext(), position + " long pressed!", Toast.LENGTH_SHORT).show(); // 필요 ??
//    }

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
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView2);

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

        return v;
    }
}