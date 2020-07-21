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

// 여행지 / 맛집 / 술집 갤러리
public class Fragment2 extends Fragment {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "Fragment2";
    String feed_result;
    ArrayList<String> urls = new ArrayList<>();

    ImageAdapter ia;

    public Fragment2() {
        // Required empty public constructor
    }
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_2, container, false);
        GridView gv =  v.findViewById(R.id.ImgGridView);

        /* TODO: new request button + categorize from category
        *  TODO: get feedpost info array
        *   */
        // Get the all posts from the server to "posts"
        retrofitInterface = RetrofitUtility.getRetrofitInterface();
        retrofitInterface.post_all().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.isSuccessful()) {
                    for (String url : response.body()){
                        urls.add(url);
                        Log.d(TAG,  url);
                        ia.notifyDataSetChanged();
                    }
                    if (urls == null) {
                    }
                } else {
                    int statusCode  = response.code();
                    Log.d(TAG, String.valueOf(statusCode));
                }
            }
            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

        ia = new ImageAdapter(getActivity());
        gv.setAdapter(ia);
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