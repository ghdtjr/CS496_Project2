package com.example.cs496_project2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

    //image
    private static final int PICK_IMAGE=777;
    Uri currentImageUri;
    boolean check;

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

                /* TODO: POST profile image */
                // RetroFit
                if(!signupID.equals("") && !signupPW.equals("") && !signupNUM.equals("")) {
                    retrofitInterface = RetrofitUtility.getRetrofitInterface();
                    retrofitInterface.user_register(new RegisterReq(signupID, signupPW, signupNUM)).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                register_result = response.body();
                                Log.d(TAG, register_result);
                                Intent signupIntent = new Intent(SignUp.this, LoginActivity.class);
                                startActivity(signupIntent);
                                Toast.makeText(SignUp.this, "Sign-up Success", Toast.LENGTH_SHORT).show();
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
            currentImageUri = data.getData();
            //Log.d("URI INFO: ", data.getData().getPath());
            //Log.d("URI INFO: ", "data.getData().toString()");
            check=true;
            img.setImageURI(currentImageUri);
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),currentImageUri);
//                String filename = currentImageUri.getLastPathSegment(); // =>
//                //Log.d("File Name: ",filename);
//                //Log.d("File Name: ","filename");
//                File file = new File(filename);
//                FileOutputStream filestream=null;
//                try{
//                    filestream=new FileOutputStream(file);
//                    bitmap.compress(Bitmap.CompressFormat.PNG,0,filestream);
//                }catch(FileNotFoundException e){
//                    e.printStackTrace();
//                    Log.d("Error1","error1");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                Log.d("Error2","error2");
//            }
//            Log.d("IMG INFO: ", currentImageUri.toString());
        }
    }
}