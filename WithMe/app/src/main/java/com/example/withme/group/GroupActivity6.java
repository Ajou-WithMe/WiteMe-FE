package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.google.android.gms.maps.model.Circle;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupActivity6 extends AppCompatActivity {

    private Button safeZoneComplete;
    private CircleImageView profileImage;

    private String profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group6);

        Intent intent = getIntent();
        profile = intent.getStringExtra("profile");

        Log.e("profile", profile);

        safeZoneComplete = (Button) findViewById(R.id.safeZoneComplete);

        profileImage = (CircleImageView) findViewById(R.id.profileComplete);

        Glide.with(getApplicationContext()).load(profile).into(profileImage);

        safeZoneComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity6.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}