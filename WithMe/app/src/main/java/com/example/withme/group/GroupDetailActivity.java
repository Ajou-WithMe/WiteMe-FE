package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.withme.R;

public class GroupDetailActivity extends AppCompatActivity {

    private String code, name;
    private TextView revise, complete;
    private EditText groupName, codeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);

        Intent intent = getIntent();
        code = intent.getStringExtra("code");
        name = intent.getStringExtra("name");

        groupName = (EditText) findViewById(R.id.groupName);
        codeName = (EditText) findViewById(R.id.codeName);

        revise = (TextView) findViewById(R.id.revise);
        complete = (TextView) findViewById(R.id.complete);

        groupName.setText(name);
        codeName.setText(code);

        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groupName.setFocusable(true);
                groupName.setFocusableInTouchMode(true);
                revise.setVisibility(View.INVISIBLE);
                complete.setVisibility(View.VISIBLE);
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(GroupDetailActivity.this, "그룹명 변경이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                groupName.setFocusable(false);
                complete.setVisibility(View.INVISIBLE);
                revise.setVisibility(View.VISIBLE);
            }
        });
    }
}