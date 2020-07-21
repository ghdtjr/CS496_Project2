package com.example.cs496_project2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewFeedPost extends AppCompatActivity {
    // Variables for server communication
    private RetrofitInterface retrofitInterface;
    private static String TAG = "NewFeedPosting Activity";
    String newfeedposting_result;

    String FilePathStr = null;
    Uri filepath;

    String newPlace="";
    String newContents="";
    String newCategory="";
    EditText newPlaceET,newContentsET;
    private Spinner spinner;
    private TextView spinner_result;

    private static final int PICK_IMAGE=777;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed_post);
        String like = "0";
        newPlaceET = (EditText) findViewById(R.id.addPlace);
        newContentsET = (EditText) findViewById(R.id.postContent);
        spinner_result=(TextView)findViewById(R.id.spinner_result3);
        spinner=(Spinner)findViewById(R.id.spinner3);

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

        /* get image file from gallery
        */
        ImageButton profileID = (ImageButton) findViewById(R.id.postImage);
        profileID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 앨범에서 사진 가져오기
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });

        TextView createPost = (TextView) findViewById(R.id.createFeedPost);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPlace = newPlaceET.getText().toString();
                newContents = newContentsET.getText().toString();
                newCategory = spinner_result.getText().toString();

                if ((newPlace.length() * newContents.length()) == 0) {
                    Toast.makeText(NewFeedPost.this, "Please input contents", Toast.LENGTH_SHORT).show();
                }else {
                    File file = new File(FilePathStr);
                    String fileName = file.getName();

                    /* TODO: POST FeedReq parameters */
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part imageBody = MultipartBody.Part.createFormData("file", fileName, requestBody);
                    RequestBody place = RequestBody.create(MediaType.parse("text/plain"), newPlace);
                    RequestBody id = RequestBody.create(MediaType.parse("text/plain"), LoginActivity.user_ID);
                    RequestBody like = RequestBody.create(MediaType.parse("text/plain"), "0");
                    RequestBody contents = RequestBody.create(MediaType.parse("text/plain"), newContents);
                    RequestBody category = RequestBody.create(MediaType.parse("text/plain"), newCategory);
                    retrofitInterface.feed_write(imageBody, place, id, like, contents, category).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if (response.isSuccessful()) {
                                newfeedposting_result = response.body();
                                Log.d(TAG, newfeedposting_result);
                                if (newfeedposting_result.equals("1")) {
                                    Toast.makeText(NewFeedPost.this, "Feed Posting success", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (newfeedposting_result.equals("0")) {
                                    Toast.makeText(NewFeedPost.this, "Feed Posting failed", Toast.LENGTH_SHORT).show();
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
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            ImageButton img = (ImageButton) findViewById(R.id.postImage);
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