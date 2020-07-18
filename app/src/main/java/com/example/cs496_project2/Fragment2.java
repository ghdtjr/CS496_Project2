package com.example.cs496_project2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment2 extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_2, container, false);

        return v;
    }
}