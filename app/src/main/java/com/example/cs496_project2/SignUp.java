package com.example.cs496_project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "SignUpActivity";
    String register_result;
    String valid;

    String signupID="", signupPW="", signupNUM="";
    EditText idET,pwET,numET;

    //image
    private static final int PICK_IMAGE=777;
    String FilePathStr = null;
    Uri filepath;

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
                signupID=idET.getText().toString();
                retrofitInterface = RetrofitUtility.getRetrofitInterface();
                retrofitInterface.validID(signupID).enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            valid = response.body();
                            if(valid.equals("1")) {
                                Toast.makeText(SignUp.this, "available ID", Toast.LENGTH_SHORT).show();
                            } else if (valid.equals("0")){
                                Toast.makeText(SignUp.this, signupID + " already exists", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            int statusCode = response.code();
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

        ImageButton profileID = (ImageButton) findViewById(R.id.addProfilePhoto);
        profileID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범에서 사진 가져오기
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery,PICK_IMAGE);
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

                if(!signupID.equals("") && !signupPW.equals("") && !signupNUM.equals("")) {
                    File file = new File(FilePathStr);
                    String fileName = file.getName();

                    RequestBody filebody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part imageBody = MultipartBody.Part.createFormData("file", fileName, filebody);
                    RequestBody id = RequestBody.create(MediaType.parse("text/plain"), signupID);
                    RequestBody password = RequestBody.create(MediaType.parse("text/plain"), signupPW);
                    RequestBody phone_number = RequestBody.create(MediaType.parse("text/plain"), signupNUM);

                    retrofitInterface = RetrofitUtility.getRetrofitInterface();
                    retrofitInterface.user_register(imageBody, id, password, phone_number).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                register_result = response.body();
                                Log.d(TAG, register_result);
                                Toast.makeText(SignUp.this, "Sign-up Success", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent signupIntent = new Intent(SignUp.this, LoginActivity.class);
                                startActivity(signupIntent);
                            } else {
                                int statusCode = response.code();
                                Log.d(TAG, String.valueOf(statusCode));
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.e(TAG, t.toString());
                        }
                    });
                }else{
                    Toast.makeText(SignUp.this, "Enter all necessary information", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            ImageButton img = (ImageButton) findViewById(R.id.addProfilePhoto);
            filepath = data.getData();
            img.setImageURI(filepath);

            if (data != null) {
                String[] FilePath = {MediaStore.Images.Media.DATA};
                Cursor c = getContentResolver().query(filepath, FilePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(FilePath[0]);
                FilePathStr = c.getString(columnIndex);
                c.close();
            }
        }
    }
}