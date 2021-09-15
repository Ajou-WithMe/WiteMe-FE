package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo_1, logo_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo_1 = (ImageView)findViewById(R.id.with_me_logo_white_1);
        logo_2 = (ImageView)findViewById(R.id.with_me_logo);

        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);

        logo_1.startAnimation(animation);
        logo_2.startAnimation(animation);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
            }
        }, 1500); // 인트로 화면 로딩 시간
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}