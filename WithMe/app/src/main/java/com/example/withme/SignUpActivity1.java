package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

public class SignUpActivity1 extends AppCompatActivity {

    private Button emailLogin, kakaoLogin;
    private LoginButton kakaoLoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        Intent intent1 = new Intent(this, SignUpActivity2.class);
        Intent intent2 = new Intent(this, SignUpActivity4_1.class);


        emailLogin = (Button) findViewById(R.id.emailLogin);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });

        kakaoLoginBtn = (LoginButton) findViewById(R.id.btn_kakao_login_basic);
        kakaoLogin = (Button) findViewById(R.id.kakaoLogin);
        kakaoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLoginBtn.performClick();
            }
        });
    }
}