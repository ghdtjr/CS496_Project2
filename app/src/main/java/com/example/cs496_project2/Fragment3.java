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

<<<<<<< HEAD
public class Fragment3 extends Fragment implements RecyclerViewAdapter.OnListItemLongSelectedInterface,RecyclerViewAdapter.OnListItemSelectedInterface{

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "Fragment3";
    String fragment3_result;
=======
public class Fragment3 extends Fragment{

    //어떤 변수들 ?? => 다시 정해서 넣어줘야함
    private RetrofitInterface retrofitInterface;
    private static String TAG = "Fragment3";
    String personal_feed_result;
    ArrayList<String> urls = new ArrayList<>();

    ImageAdapter ia;
>>>>>>> origin/feature/sally/design

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;

    ArrayList<Feedphotos> feedphotos = new ArrayList<>();

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

//    @Override
//    public void onItemSelected(View v, int position) {
//        RecyclerViewAdapter.Holder viewHolder = (RecyclerViewAdapter.Holder)recyclerView.findViewHolderForAdapterPosition(position);
//        Toast.makeText(this.getContext(),  "Press long to call", Toast.LENGTH_SHORT).show();
//        Log.d("test","long clicked");
//        //v.setBackgroundColor(Color.BLUE);
//        //팝업
//
//    }
//
//    @Override
//    public void onItemLongSelected(View v, int position) {
//        Log.d("tab1test","clicked");
//        //Toast.makeText(getActivity().getApplicationContext(), position+" " , Toast.LENGTH_SHORT).show();
//        //다이얼
//        //String tel="tel:" + number;
//        //Log.d("MY PHONE:",tel);
//        //startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
//    }

    /* TODO: get feedpost from writer_id, url array, feedpost array */
    /* IN feedpost item, like request and get */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_3, container, false);
        GridView gv =  v.findViewById(R.id.ImgGridView);

        // Get the all feedphotos from the server to "posts"
        retrofitInterface = RetrofitUtility.getRetrofitInterface();
        retrofitInterface.feed_get(LoginActivity.user_ID).enqueue(new Callback<ArrayList<Feedphotos>>() {
            @Override
            public void onResponse(Call<ArrayList<Feedphotos>> call, Response<ArrayList<Feedphotos>> response) {
                if (response.isSuccessful()) {
                    for (Feedphotos feedphoto : response.body()){
                        feedphotos.add(feedphoto);
//                        adapter.notifyDataSetChanged();
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
        ia = new ImageAdapter(getActivity());
        gv.setAdapter(ia);
//        return v;

        //RecyclerView Adapter 호출
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter2(getActivity().getApplicationContext(), posts); //, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        recyclerView.setAdapter(adapter);
//
//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(getActivity().getApplicationContext(),
//                        new LinearLayoutManager(getActivity().getApplicationContext()).getOrientation());
//        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
//        recyclerView.addItemDecoration(dividerItemDecoration);

        // recyclerview Item 선택시 user의 연락처 정보 나타내는 팝업?? 보여줘야함

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

    /* Adapter class */
    public class ImageAdapter extends BaseAdapter {

        ImageAdapter(Context c){
        }

        public int getCount() {
            return urls.size();
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        /* setting the data which will be placed on the view */
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null){
                imageView = new ImageView(getActivity());
                imageView.setAdjustViewBounds(false);
            }else{
                imageView = (ImageView) convertView;
            }
            /* Show image */
            Glide.with(getActivity()).load(("http://192.249.19.243:0280/gallery/" + urls.get(position)).toString()).override(450,350).centerCrop().into(imageView);
            return imageView;
        }
    }
}