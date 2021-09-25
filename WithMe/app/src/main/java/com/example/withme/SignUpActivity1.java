package com.example.withme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.withme.KakaoLogin.SessionCallback;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.KakaoSDK;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class SignUpActivity1 extends AppCompatActivity {

    private Button emailLogin, kakaoLogin;
    private LoginButton kakaoLoginBtn;
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up1);

        Intent intent = new Intent(this, SignUpActivity2.class);

        emailLogin = (Button) findViewById(R.id.emailLogin);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
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

    public void directToSecondActivity(Boolean result) {
        Intent intent = new Intent(SignUpActivity1.this, SignUpActivity4.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        if (result) {
            Toast.makeText(getApplicationContext(), "로그인 성공!", Toast.LENGTH_SHORT).show();
            startActivity(intent);
            finish();
        }
    }
}