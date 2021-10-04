package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GroupActivity2 extends AppCompatActivity {

    private String groupName;
    private TextView group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group2);

        group = (TextView) findViewById(R.id.group);

        Intent groupIntent = getIntent();
        groupName = groupIntent.getStringExtra("groupName");

        group.setText(groupName);
    }
}