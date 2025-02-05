package com.example.withme.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.withme.MainActivity;
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

public class GroupActivity1 extends AppCompatActivity {

    private ImageButton xButton;
    private EditText groupName;
    private Button groupComplete;
    private CircleImageView profileImage;
    private ImageView editProfile;
    private final int GET_GALLERY_IMAGE = 200;
    private Uri selectedImageUri;
    private String selectedImagePath, imageFromServer;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group1);

        verifyStoragePermissions(GroupActivity1.this);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        String accessToken = sf.getString("AccessToken", "");

        xButton = (ImageButton) findViewById(R.id.xButton);
        groupName = (EditText) findViewById(R.id.groupName);
        groupComplete = (Button) findViewById(R.id.groupComplete);
        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        editProfile = (ImageView) findViewById(R.id.editProfile);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Intent intent1 = new Intent(this, GroupActivity2.class);
        Intent intent2 = new Intent(this, MainActivity.class);

        groupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 1) {
                    groupComplete.setBackgroundResource(R.drawable.radius_1_clickable);
                    groupComplete.setClickable(true);
                    groupComplete.setTextColor(Color.parseColor("#222222"));
                }
            }
        });

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent2);
            }
        });

        groupComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("groupName", groupName.getText().toString());

                // 프로필 사진을 넣었을 때, 안넣었을 때 구분
                if (selectedImagePath != null) {
                    File file = new File(selectedImagePath);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    retrofitAPI.uploadImage(accessToken, body).enqueue(new Callback<UploadImage>() {
                        @Override
                        public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                            UploadImage data = response.body();

                            if (response.isSuccessful()) {
                                Log.e("make Profile", selectedImagePath);
                                if (!data.getData().equals("이미지 파일이 아닙니다.")) {
                                    imageFromServer = data.getData();
                                    intent1.putExtra("image", imageFromServer);
                                    String name = groupName.getText().toString();

                                    HashMap<String, Object> input = new HashMap<>();
                                    input.put("name", name);
                                    input.put("profile", imageFromServer);

                                    retrofitAPI.postCreateParty(accessToken, input).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                    JSONObject data = jsonObject.getJSONObject("data");
                                                    String code = data.getString("code");

                                                    Log.e("create Party", String.valueOf(jsonObject));
                                                    Log.e("create Party", code);
                                                    intent1.putExtra("code", code);
                                                    startActivity(intent1);
                                                } catch (IOException | JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                                            Log.e("create Party", t.getMessage());
                                        }
                                    });
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<UploadImage> call, Throwable t) {
                            Log.e("make Profile", t.getMessage());
                        }
                    });
                } else {
                    Toast.makeText(GroupActivity1.this, "프로필 사진을 등록하셔야 합니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
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