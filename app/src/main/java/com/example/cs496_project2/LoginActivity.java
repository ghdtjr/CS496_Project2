package com.example.cs496_project2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private RetrofitInterface retrofitInterface;
    private static String TAG = "MainActivity";
    String uid="", pw="";
    EditText usernameET,passwordET;

    ArrayList<String> data;
    ArrayAdapter<String> arrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        retrofitInterface = RetrofitUtility.getRetrofitInterface();
        retrofitInterface.main_get().enqueue(new Callback<List<Postings>>() {
            @Override
            public void onResponse(Call<List<Postings>> call, Response<List<Postings>> response) {
                if (response.isSuccessful()) {
                    List<Postings> postingsList = response.body();
                    if (postingsList != null) {
                        Log.d(TAG, postingsList.get(0).getUserID());
                    } else {
                        Log.d(TAG, "posting is null");
                    }
                } else {
                    int statusCode  = response.code();
                    Log.d(TAG, String.valueOf(statusCode));
                }
            }
            @Override
            public void onFailure(Call<List<Postings>> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
        data=new ArrayList<String>();
        usernameET=(EditText) findViewById(R.id.userid);
        passwordET=(EditText) findViewById(R.id.password);

        //View view = getWindow().getDecorView();
        getWindow().setStatusBarColor(Color.parseColor("#EC5176"));

        if(getIntent().getExtras() != null){
            EditText username = (EditText)findViewById(R.id.userid);
            Intent signupIntent = getIntent();
            username.setText(signupIntent.getStringExtra("Username"));
        }

        TextView login = (TextView) findViewById(R.id.loginButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uid=usernameET.getText().toString();
                pw=passwordET.getText().toString();

                // 디비에서 아이디, 비밀번호 일치여부
                // Global로 id와 사용자 정보 넘기기!!


            }
        });

        TextView signup = (TextView)findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupIntent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(signupIntent);
            }
        });
    }
}