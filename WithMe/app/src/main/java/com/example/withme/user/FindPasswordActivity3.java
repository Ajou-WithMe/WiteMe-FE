package com.example.withme.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.withme.R;

public class FindPasswordActivity3 extends AppCompatActivity {

    private Button toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password3);

        toLogin = (Button) findViewById(R.id.toLogin);

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FindPasswordActivity3.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}