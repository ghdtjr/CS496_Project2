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
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "LoginActivity";
    String login_result;

    String uid="", pw="";
    EditText usernameET,passwordET;

    ArrayList<String> data;
    ArrayAdapter<String> arrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

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

                // Global로 id와 사용자 정보 넘기기!!
                retrofitInterface = RetrofitUtility.getRetrofitInterface();
                retrofitInterface.user_login(new LoginReq(uid, pw)).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            login_result = response.body();
                            Log.d(TAG, login_result);
                            if (login_result.equals("ID not exists")) {
                                Toast.makeText(LoginActivity.this,"ID not exists", Toast.LENGTH_SHORT).show();
                            } 
                            else if (login_result.equals("Login success"))
                            {
                                Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(loginIntent);
                            }
                            else if(login_result.equals("Wrong password"))
                            {
                                Toast.makeText(LoginActivity.this,"Wrong password", Toast.LENGTH_SHORT).show();
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