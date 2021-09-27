package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kakao.auth.Session;
import com.kakao.usermgmt.LoginButton;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton, kakaoLogin_login, buttonLogin;
    private LoginButton kakaoLoginBtn_login;
    private EditText etPassword, etID;
    private SignUpActivity4_1 signUpActivity4;
    private KakaoLogin_SignUp1.SessionCallback sessionCallback;

    private int a = 5, b = 10;
    private static final String TAG = "유저";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent1 = new Intent(this, SignUpActivity1.class);
        Intent intent2 = new Intent(this, MainActivity.class);

        kakaoLoginBtn_login = (LoginButton) findViewById(R.id.btn_kakao_login_basic_login);
        kakaoLogin_login = (Button) findViewById(R.id.kakaoLogin_login);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etID = (EditText) findViewById(R.id.etPassword);

        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    a = 100;
                    if (a == 100 && b == 100) {
                        buttonLogin.setClickable(true);
                        buttonLogin.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    b = 100;
                    if (a == 100 && b == 100) {
                        buttonLogin.setTextColor(Color.parseColor("#000000"));
                        buttonLogin.setClickable(true);
                    }
                }
            }
        });

        kakaoLogin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLoginBtn_login.performClick();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}