package com.example.withme.intro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.intro.DescriptionActivity;
import com.example.withme.retorfit.GetProfile;
import com.example.withme.retorfit.RetrofitAPI;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo_1, logo_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        String accessToken = sf.getString("AccessToken", "");
        Log.e("AccessToken", accessToken);

        SharedPreferences sf2 = getSharedPreferences("storeAccessToken", MODE_PRIVATE);


        SharedPreferences.Editor editor;
        editor = sf2.edit();

        logo_1 = (ImageView)findViewById(R.id.with_me_logo_white_1);
        logo_2 = (ImageView)findViewById(R.id.with_me_logo);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

        logo_1.startAnimation(animation);
        logo_2.startAnimation(animation);

        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
        Intent intent2 = new Intent(getApplicationContext(), DescriptionActivity.class);

        retrofitAPI.getProfile(accessToken).enqueue(new Callback<GetProfile>() {
            @Override
            public void onResponse(Call<GetProfile> call, Response<GetProfile> response) {
                GetProfile data = response.body();
                String newAccessToken = response.headers().get("AccessToken");

                if (response.isSuccessful()) {
                    if (data.isSuccess() == true) {
                        if(newAccessToken == null) {
                            Log.e("바뀐 access token", "null"); // 무시!
                        } else {
                            Log.e("바뀐 access token", newAccessToken);
                            editor.putString("AccessToken", newAccessToken);
                            editor.commit();
                        }
                        Log.e("성공 시", String.valueOf(data.getStatus()));
                        Log.e("성공 시", String.valueOf(data.isSuccess()));
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                finish();
                            }
                        }, 2000); // 인트로 화면 로딩 시간

                    } else {
                        if (data.getStatus() == 401) { // 인증 오류!
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                    startActivity(intent2);
                                }
                            }, 2000); // 인트로 화면 로딩 시간
                            Log.e("인증 오류", String.valueOf(data.getStatus()));
                            Log.e("인증 오류", String.valueOf(data.isSuccess()));
                        } else {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                    startActivity(intent2);
                                }
                            }, 2000); // 인트로 화면 로딩 시간
                            Log.e("인증 오류는 아니지만 다른 오류", String.valueOf(data.getStatus()));
                            Log.e("인증 오류는 아니지만 다른 오류", String.valueOf(data.isSuccess()));
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<GetProfile> call, Throwable t) {
                Log.e("failure", t.getMessage());
                Log.e("failure", "전송 실패");
                startActivity(intent2);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}