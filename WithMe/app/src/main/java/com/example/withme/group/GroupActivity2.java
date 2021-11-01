package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupActivity2 extends AppCompatActivity {

    private String groupName, code, imageUrl;
    private TextView group, groupCode, copyCode;
    private Button registerButton;
    private CircleImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group2);

        group = (TextView) findViewById(R.id.group);
        groupCode = (TextView) findViewById(R.id.groupCode);
        copyCode = (TextView)findViewById(R.id.copyCode);
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

        copyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                clipboardManager.setText(code);

                Toast.makeText(GroupActivity2.this, "복사가 완료되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}