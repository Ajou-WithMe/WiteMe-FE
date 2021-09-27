package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity3 extends AppCompatActivity {

    private EditText first, second, third, fourth, fifth, sixth;
    private Button password;

    private String fullPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up3);

        Intent intent = new Intent(this, SignUpActivity4_2.class);
        Intent data = getIntent();

        String email = data.getStringExtra("email");
        Log.e("email", email);

        first = (EditText)findViewById(R.id.firstPhoneNumber);
        second = (EditText)findViewById(R.id.SecondPhoneNumber);
        third = (EditText)findViewById(R.id.ThirdPhoneNumber);
        fourth = (EditText)findViewById(R.id.FourthPhoneNumber);
        fifth = (EditText)findViewById(R.id.FifthPhoneNumber);
        sixth = (EditText)findViewById(R.id.SixthPhoneNumber);

        password = (Button)findViewById(R.id.password);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    second.requestFocus();
                    first.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    third.requestFocus();
                    second.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    fourth.requestFocus();
                    third.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    fifth.requestFocus();
                    fourth.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        fifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    sixth.requestFocus();
                    fifth.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        sixth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    sixth.requestFocus();
                    sixth.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                    password.setBackgroundResource(R.drawable.radius_1_clickable);
                    password.setTextColor(Color.parseColor("#222222"));
                    password.setClickable(true);
                    fullPassword = first.getText().toString() + second.getText().toString()
                            + third.getText().toString() + fourth.getText().toString()
                            + fifth.getText().toString() + sixth.getText().toString();

                    Log.e("Password", fullPassword);
                    intent.putExtra("email", email);
                    intent.putExtra("password", fullPassword);
                }
            }
        });
    }
}