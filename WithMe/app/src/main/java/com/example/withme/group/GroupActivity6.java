package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.withme.MainActivity;
import com.example.withme.R;

public class GroupActivity6 extends AppCompatActivity {

    private Button safeZoneComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group6);

        safeZoneComplete = (Button) findViewById(R.id.safeZoneComplete);

        safeZoneComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity6.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}