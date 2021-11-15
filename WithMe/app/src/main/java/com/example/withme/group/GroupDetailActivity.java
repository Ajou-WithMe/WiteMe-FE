package com.example.withme.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.location.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.retorfit.UploadImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupDetailActivity extends AppCompatActivity {

    private String code, name, profile, selectedImagePath, imageFromServer;
    private TextView revise, complete;
    private EditText groupName, codeName;
    private boolean success;
    private Uri selectedImageUri;
    private CircleImageView profileImage;
    private ImageButton xButton;

    private final int GET_GALLERY_IMAGE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        String accessToken = sf.getString("AccessToken", "");

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        name = intent.getStringExtra("name");
        profile = intent.getStringExtra("profile");

        profileImage = (CircleImageView) findViewById(R.id.profileImage);

        xButton = (ImageButton) findViewById(R.id.xButton);

        groupName = (EditText) findViewById(R.id.groupName);
        codeName = (EditText) findViewById(R.id.codeName);

        revise = (TextView) findViewById(R.id.revise);
        complete = (TextView) findViewById(R.id.complete);

        groupName.setText(name);
        codeName.setText(code);

        Glide.with(this).load(profile).into(profileImage);

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupDetailActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName.setFocusable(true);
                groupName.setFocusableInTouchMode(true);
                revise.setVisibility(View.INVISIBLE);
                complete.setVisibility(View.VISIBLE);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(selectedImagePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                retrofitAPI.uploadImage(accessToken, body).enqueue(new Callback<UploadImage>() {
                    @Override
                    public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                        UploadImage data = response.body();

                        if(response.isSuccessful()) {
                            Log.e("make Profile", selectedImagePath);
                            if (!data.getData().equals("이미지 파일이 아닙니다.")) {
                                imageFromServer = data.getData();

                                HashMap<String, Object> input = new HashMap<>();
                                input.put("name", groupName.getText().toString());
                                input.put("profile", imageFromServer);
                                input.put("code", code);

                                retrofitAPI.updateParty(accessToken, input).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body().string());
                                                boolean success = jsonObject.getBoolean("success");
                                                Log.e("updateParty1", String.valueOf(jsonObject));

                                                if (success = true) {
                                                    Toast.makeText(GroupDetailActivity.this, "그룹명 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.e("updateParty1", String.valueOf(success));
                                                }
                                                groupName.setFocusable(false);
                                                complete.setVisibility(View.INVISIBLE);
                                                revise.setVisibility(View.VISIBLE);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("updateParty1", "전송 실패");
                                    }
                                });
                            } else {
                                HashMap<String, Object> input = new HashMap<>();
                                input.put("name", name);
                                input.put("profile", profile);
                                input.put("code", code);

                                retrofitAPI.updateParty(accessToken, input).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body().string());
                                                Log.e("updateParty2", String.valueOf(jsonObject));
                                                success = jsonObject.getBoolean("success");

                                                if (success = true) {
                                                    Toast.makeText(GroupDetailActivity.this, "그룹명 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.e("updateParty2", String.valueOf(success));
                                                }
                                                groupName.setFocusable(false);
                                                complete.setVisibility(View.INVISIBLE);
                                                revise.setVisibility(View.VISIBLE);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("updateParty", "전송 실패");
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImage> call, Throwable t) {

                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            profileImage.setImageURI(selectedImageUri);
            selectedImagePath = uri2path(this, selectedImageUri);
        }
    }

    //Uri -> Path(파일경로)
    public static String uri2path(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }
}