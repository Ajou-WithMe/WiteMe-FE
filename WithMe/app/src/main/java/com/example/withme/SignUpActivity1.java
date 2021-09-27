package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.withme.KakaoLogin_SignUp1.SessionCallback;
import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

public class SignUpActivity1 extends AppCompatActivity {

    private Button emailLogin, kakaoLogin;
    private LoginButton kakaoLoginBtn;
    private KakaoLogin_SignUp1.SessionCallback sessionCallback;

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

        if (!HasKakaoSession()) {
            sessionCallback = new SessionCallback(getApplicationContext(), SignUpActivity1.this);
            Session.getCurrentSession().addCallback(sessionCallback);
        } else if (HasKakaoSession()) {
            sessionCallback = new SessionCallback(getApplicationContext(), SignUpActivity1.this);
            Session.getCurrentSession().addCallback(sessionCallback);
//            Session.getCurrentSession().checkAndImplicitOpen();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    private boolean HasKakaoSession() {
        if (!Session.getCurrentSession().checkAndImplicitOpen()) {
            return false;
        }
        return true;
    }

    public void directToSecondActivitySignUp(Boolean result) {
        Intent intent = new Intent(SignUpActivity1.this, SignUpActivity4_1.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (result) {
            Toast.makeText(getApplicationContext(), "sign up activity 로그인 성공!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}