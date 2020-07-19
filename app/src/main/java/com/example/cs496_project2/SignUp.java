package com.example.cs496_project2;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "SignUpActivity";
    String register_result;

    String signupID="", signupPW="", signupNUM="";
    EditText idET,pwET,numET;

    ArrayList<String> data;
    ArrayAdapter<String> arrayAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        data=new ArrayList<String>();
        idET=(EditText) findViewById(R.id.addID);
        pwET=(EditText) findViewById(R.id.addPW);
        numET=(EditText) findViewById(R.id.addNUM);

        ImageButton searchID = (ImageButton) findViewById(R.id.searchID);
        searchID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase button parse
                signupID=idET.getText().toString();
                signupPW=pwET.getText().toString();
                signupNUM=numET.getText().toString();

                // 버튼 클릭시 존재하는 아이디인지 ??
            }
        });

        ImageButton profileID = (ImageButton) findViewById(R.id.addProfilePhoto);
        profileID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범에서 사진 가져오기

            }
        });

        TextView login = (TextView) findViewById(R.id.signupButton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Firebase button parse
                signupID=idET.getText().toString();
                signupPW=pwET.getText().toString();
                signupNUM=numET.getText().toString();
                //사진 uri(?)도 디비에 올려야함
                // 디비에 사용자 정보 올리기

                // RetroFit
                retrofitInterface = RetrofitUtility.getRetrofitInterface();
                retrofitInterface.user_register(new RegisterReq(signupID, signupPW, signupNUM)).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            register_result = response.body();
                            Log.d(TAG, register_result);
                            Intent signupIntent = new Intent(SignUp.this, LoginActivity.class);
                            startActivity(signupIntent);
                            Toast.makeText(SignUp.this,"Sign-up Success", Toast.LENGTH_SHORT).show();
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

    }
}