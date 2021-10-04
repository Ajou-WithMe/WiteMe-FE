package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private ConstraintLayout coachMark;
    private ImageButton makeGroup1, makeGroup2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, GroupActivity1.class);

        coachMark = (ConstraintLayout) findViewById(R.id.coach_mark_master_view);
        makeGroup1 = (ImageButton) findViewById(R.id.makeGroup_1);
        makeGroup2 = (ImageButton) findViewById(R.id.makeGroup_2);

        coachMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coachMark.setVisibility(View.GONE);
            }
        });

        makeGroup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });


    }
}