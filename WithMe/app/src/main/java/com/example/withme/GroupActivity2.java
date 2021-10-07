package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class GroupActivity2 extends AppCompatActivity {

    private String groupName;
    private TextView group;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group2);

        group = (TextView) findViewById(R.id.group);
        registerButton = (Button) findViewById(R.id.registerButton);

        Intent groupIntent = getIntent();
        Intent intent = new Intent(this, GroupActivity3.class);

        groupName = groupIntent.getStringExtra("groupName");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        group.setText(groupName);
    }
}