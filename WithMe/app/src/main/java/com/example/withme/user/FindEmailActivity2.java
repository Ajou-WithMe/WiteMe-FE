package com.example.withme.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.withme.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.Array;
import java.util.ArrayList;

public class FindEmailActivity2 extends AppCompatActivity {

    private LinearLayout emails;
    private String secret = "***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email2);

        ArrayList<String> emailArray = (ArrayList<String>)getIntent().getSerializableExtra("data");
        Log.e("email", String.valueOf(emailArray));

        emails = (LinearLayout) findViewById(R.id.emails);
        emails.setBackgroundResource(R.drawable.radius_6_button_no);

        for (int i=0; i<emailArray.size(); i++) {
            TextView email = new TextView(this);
            String text = emailArray.get(i);
            String subText = text.substring(3);

            String result = secret.concat(subText);

            email.setText(result);
            email.setTextSize(16);
            email.setTextColor(Color.parseColor("#222222"));
            emails.addView(email);

            LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp_text.setMargins(0,42,0,42);
            lp_text.gravity = Gravity.CENTER;
            email.setLayoutParams(lp_text);
        }
    }
}