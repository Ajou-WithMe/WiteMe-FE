package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.android.gms.maps.model.Circle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupActivity6 extends AppCompatActivity {

    private Button safeZoneComplete;
    private CircleImageView profileImage;
    private String accessToken, uid;
    private ImageButton xButton;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group6);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");

        safeZoneComplete = (Button) findViewById(R.id.safeZoneComplete);

        xButton = findViewById(R.id.xButton);

        profileImage = (CircleImageView) findViewById(R.id.profileComplete);

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity6.this, MainActivity.class);
                startActivity(intent);
            }
        });

        retrofitAPI.getProtectionDetail(accessToken, uid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            Log.e("data", data.toString());
                            String profile = data.getString("profileImg");

                            if (profile.equals("null")) {
                                profileImage.setBackgroundResource(R.drawable.solo_white);
                            } else {
                                Glide.with(getApplicationContext()).load(profile).into(profileImage);
                            }
                        } else {
                            Log.e("data", "잘못됨");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

        safeZoneComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity6.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}