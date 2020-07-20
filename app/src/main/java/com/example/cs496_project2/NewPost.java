package com.example.cs496_project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewPost extends AppCompatActivity {//implements AdapterView.OnItemSelectedListener { //implements NavigationView.OnNavigationItemSelectedListener, 나중에 DRAWER 할때!!

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "NewPosting Activity";
    String newposting_result;

    DrawerLayout drawerLayout;

    // Database
    String newPlace="";
    String newDate="";
    String newCategory=""; //체크박스
    EditText newPlaceET,newDateET;
    private Spinner spinner;
    private TextView spinner_result;
    String[] item;

    ArrayList<String> data;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        data = new ArrayList<String>();
        newPlaceET = (EditText) findViewById(R.id.addPlace);
        newDateET = (EditText) findViewById(R.id.addDate);
        //publicPostCB=(CheckBox) findViewById(R.id.publicPost); //=> 선택지로
        spinner=(Spinner)findViewById(R.id.spinner);
        spinner_result=(TextView)findViewById(R.id.spinner_result);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_result.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                newCategory="";
            }
        });

        TextView createPost = (TextView) findViewById(R.id.createPost);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlace = newPlaceET.getText().toString();
                newDate = newDateET.getText().toString();
                newCategory = spinner_result.getText().toString(); // 아마 이러면 될듯???
                if ((newPlace.length() * newDate.length()) == 0) {
                    Toast.makeText(NewPost.this, "Please input contents", Toast.LENGTH_SHORT).show();
                } else {
                    // RetroFit
                    retrofitInterface = RetrofitUtility.getRetrofitInterface();
                    retrofitInterface.main_write(new PostingReq(LoginActivity.user_ID, newPlace, newDate,newCategory)).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                newposting_result = response.body();
                                Log.d(TAG, newposting_result);

                                // Posting Success
                                if(newposting_result.equals("1")){
                                    Intent createPostIntent = new Intent(NewPost.this, MainActivity.class);
                                    startActivity(createPostIntent);
                                    Toast.makeText(NewPost.this,"Posting success", Toast.LENGTH_SHORT).show();
                                }
                                else if (newposting_result.equals("0"))
                                {
                                    Toast.makeText(NewPost.this,"Posting failed", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                int statusCode  = response.code();
                                Log.d(TAG, String.valueOf(statusCode));
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e(TAG, t.toString());
                        }
                    });
                }
                // fragment 따라 어떻게??
            }
        });

    }

}