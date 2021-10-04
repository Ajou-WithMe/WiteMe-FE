package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupActivity1 extends AppCompatActivity {

    private ImageButton xButton;
    private EditText groupName;
    private Button groupComplete;
    private CircleImageView profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group1);

        xButton = (ImageButton) findViewById(R.id.xButton);
        groupName = (EditText) findViewById(R.id.groupName);
        groupComplete = (Button) findViewById(R.id.groupComplete);

        Intent intent1 = new Intent(this, GroupActivity2.class);
        Intent intent2 = new Intent(this, MainActivity.class);

        groupName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() > 0) {
                    groupComplete.setBackgroundResource(R.drawable.radius_1_clickable);
                    groupComplete.setClickable(true);
                }
            }
        });

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent2);
            }
        });

        groupComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
    }
}