package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SignUpActivity4 extends AppCompatActivity{

    private EditText etName, etPhoneNumber, etAddress;
    private LinearLayout nameLayout, phoneNumberLayout, addressLayout;
    private ImageView checkbox1, checkbox2, checkbox3;
    private String name, phoneNumber, address;
    private Button startWithMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);

        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) findViewById(R.id.etAddress);

        nameLayout = (LinearLayout) findViewById(R.id.nameLayout);
        phoneNumberLayout = (LinearLayout) findViewById(R.id.phoneNumberLayout);
        addressLayout = (LinearLayout) findViewById(R.id.addressLayout);

        checkbox1 = (ImageView) findViewById(R.id.checkbox1);
        checkbox2 = (ImageView) findViewById(R.id.checkbox2);
        checkbox3 = (ImageView) findViewById(R.id.checkbox3);

        startWithMe = (Button) findViewById(R.id.startWithMe);

        Intent intent = new Intent(this, MainActivity.class);

        startWithMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = etName.getText().toString().trim();
                if (s.length() > 0) {
                    nameLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox1.setVisibility(View.VISIBLE);
                } else {
                    nameLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox1.setVisibility(View.INVISIBLE);
                }

                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                } else {
                    // Either gone or invisible
                }
            }
        });

        etPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phoneNumber = etPhoneNumber.getText().toString().trim();
                if (s.length() > 0) {
                    phoneNumberLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox2.setVisibility(View.VISIBLE);
                } else {
                    phoneNumberLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox2.setVisibility(View.INVISIBLE);
                }

                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                } else {
                    // Either gone or invisible
                }
            }
        });

        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                address = etAddress.getText().toString().trim();
                if (s.length() > 0) {
                    addressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox3.setVisibility(View.VISIBLE);
                } else {
                    addressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox3.setVisibility(View.INVISIBLE);
                }
                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundResource(R.drawable.radius_3);
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                } else {
                    // Either gone or invisible
                    startWithMe.setBackgroundResource(R.drawable.radius_1_nonclickable);
                    startWithMe.setTextColor(Color.parseColor("#BDBDBD"));
                    startWithMe.setClickable(false);

                }
            }
        });
    }
}