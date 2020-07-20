package com.example.cs496_project2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "Get Phone_number";
    String login_result;

    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }
    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    private Context context;
    private List<Postings> list = new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<Postings> list, OnListItemSelectedInterface listener, OnListItemLongSelectedInterface longListener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
        this.mLongListener = longListener;
    }

    // ViewHolder 생성
    // row layout을 화면에 뿌려주고 holder에 연결
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postings_item, viewGroup, false);
        return new Holder(view);
    }

    /*
     * Todo 만들어진 ViewHolder에 data 삽입 ListView의 getView와 동일
     *
     * */
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        holder.idText.setText(list.get(itemposition).getUserID());
        holder.placeText.setText(list.get(itemposition).getPlaceName());
        holder.dateText.setText(list.get(itemposition).getDate());
        holder.cateText.setText(list.get(itemposition).getCategory());
        //Log.e("StudyApp", "onBindViewHolder" + itemposition);
    }

    // 몇개의 데이터를 리스트로 뿌려줘야하는지 반드시 정의해줘야한다
    @Override
    public int getItemCount() {
        return list.size(); // RecyclerView의 size return
    }

    // ViewHolder는 하나의 View를 보존하는 역할을 한다
    public class Holder extends RecyclerView.ViewHolder{
        public TextView idText;
        public TextView placeText;
        public TextView dateText;
        public TextView cateText;

        public Holder(View view){
            super(view);
            idText = (TextView) view.findViewById(R.id.id_tv);
            placeText = (TextView) view.findViewById(R.id.place_tv);
            dateText = (TextView) view.findViewById(R.id.date_tv);
            Log.d("Contact", "make one");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    mListener.onItemSelected(v, position);
                    Log.d("Posting", "clicked "+getAdapterPosition());
                    //팝업으로 사용자 연락처


                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    int position = getAdapterPosition();
                    mLongListener.onItemLongSelected(v, getAdapterPosition());
                    Log.d("Recyclerview", "position = "+ getAdapterPosition());
                    //다이얼 나중에 갖다 쓸 코드

                    retrofitInterface = RetrofitUtility.getRetrofitInterface();
                    retrofitInterface.main_writer_id(list.get(position).getUserID()).enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                Users writer = response.body();
                                String tel="tel:" + writer.getPhone_number(); // getUserID로 ID 받아와서 전화번호 받아와야함
                                Log.d("MY PHONE:",tel);
                                context.startActivity(new Intent("android.intent.action.CALL", Uri.parse(tel)));
                            }
                        }
                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            Log.e(TAG, t.toString());
                        }
                    });
                    return false;
                }
            });
        }
    }
}
