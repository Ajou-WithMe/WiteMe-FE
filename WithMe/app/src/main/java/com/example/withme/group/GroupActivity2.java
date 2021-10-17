package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.withme.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupActivity2 extends AppCompatActivity {

    private String groupName, code, imageUrl;
    private TextView group, groupCode;
    private Button registerButton;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group2);

        group = (TextView) findViewById(R.id.group);
        groupCode = (TextView) findViewById(R.id.groupCode);
        registerButton = (Button) findViewById(R.id.registerButton);
        profile = (CircleImageView) findViewById(R.id.profileComplete);

        Intent groupIntent = getIntent();
        Intent intent = new Intent(this, GroupActivity3.class);

        groupName = groupIntent.getStringExtra("groupName");
        code = groupIntent.getStringExtra("code");
        imageUrl = groupIntent.getStringExtra("image");

        Glide.with(this).load(imageUrl).into(profile);

        intent.putExtra("code", code);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        groupCode.setText(code);
        group.setText(groupName);
    }
}