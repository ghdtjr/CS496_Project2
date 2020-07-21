package com.example.cs496_project2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.Holder> {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "Adapter2";
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
    private ArrayList<FeedPostings> list = new ArrayList<>();

    public RecyclerViewAdapter2(Context context, ArrayList<FeedPostings> list, OnListItemSelectedInterface listener, OnListItemLongSelectedInterface longListener) {
        this.context = context;
        this.list = list;
        this.mListener = listener;
        this.mLongListener = longListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.postings_item, viewGroup, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter2.Holder holder, int position) {
        // 각 위치에 문자열 세팅
        int itemposition = position;
        Glide.with(holder.itemView.getContext())
                .load(("http://192.249.19.243:0280/gallery/" + feedphotos.getFile_name))
                .into(holder.ivFeedPhoto);
        holder.fileText.setText(list.get(itemposition).getFileName());
        holder.placeText.setText(list.get(itemposition).getPlaceName());
        holder.idText.setText(list.get(itemposition).getUserID());
        holder.likeText.setText(list.get(itemposition).getLike());
        holder.contentsText.setText(list.get(itemposition).getContents());
        holder.cateText.setText(list.get(itemposition).getCategory());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        ImageView ivFeedPhoto;
        public TextView fileText;
        public TextView placeText;
        public TextView idText;
        public TextView likeText;
        public TextView contentsText;
        public TextView cateText;

        public Holder(View view){
            super(view);

            ivFeedPhoto = itemView.findViewById(R.id.feedphoto_f3);

            placeText = (TextView) view.findViewById(R.id.place_tv_f3);
            idText = (TextView) view.findViewById(R.id.id_tv_f3);
            likeText= (TextView) view.findViewById(R.id.likes_tv_f3);
            contentsText = (TextView) view.findViewById(R.id.contents_tv_f3);
            cateText = (TextView) view.findViewById(R.id.cate_tv_f3);
            Log.d("Contact", "make one");

            /* TODO: Get writer feed from writer_id */
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    mListener.onItemSelected(v, position);
                    Log.d("FeedPosting", "clicked "+getAdapterPosition());
                    //팝업으로 사용자 연락처
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v){
                    int position = getAdapterPosition();
                    mLongListener.onItemLongSelected(v, getAdapterPosition());
                    Log.d("Recyclerview", "position = "+ getAdapterPosition());

                    retrofitInterface = RetrofitUtility.getRetrofitInterface();
                    retrofitInterface.main_writer_id(list.get(position).getUserID()).enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            if (response.isSuccessful()) {
                                // 하트
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
