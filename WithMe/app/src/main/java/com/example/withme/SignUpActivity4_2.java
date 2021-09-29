package com.example.withme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity4_2 extends AppCompatActivity{

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;

    private EditText etName, etPhoneNumber, etAddress, etDetailAddress;
    private LinearLayout detailAddressLayout, nameLayout, phoneNumberLayout, addressLayout;
    private ImageView checkbox1, checkbox2, checkbox3, checkbox4;
    private String name, phoneNumber, address, detailAddress, fullAddress;
    private Button startWithMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4_1);

        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText) findViewById(R.id.etPhoneNumber);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etDetailAddress = (EditText) findViewById(R.id.etDetailAddress);

        nameLayout = (LinearLayout) findViewById(R.id.nameLayout);
        phoneNumberLayout = (LinearLayout) findViewById(R.id.phoneNumberLayout);
        addressLayout = (LinearLayout) findViewById(R.id.addressLayout);
        detailAddressLayout = (LinearLayout) findViewById(R.id.detailAddressLayout);

        checkbox1 = (ImageView) findViewById(R.id.checkbox1);
        checkbox2 = (ImageView) findViewById(R.id.checkbox2);
        checkbox3 = (ImageView) findViewById(R.id.checkbox3);
        checkbox4 = (ImageView) findViewById(R.id.checkbox4);

        startWithMe = (Button) findViewById(R.id.startWithMe);

        Intent intent1 = new Intent(this, LoginActivity.class);
        Intent data = getIntent();

        String email = data.getStringExtra("email");
        String pwd = data.getStringExtra("password");

        Log.e("받은 데이터", "email : " + email + ", password : " + pwd);

        startWithMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> input = new HashMap<>();
                input.put("name", name);
                input.put("email", email);
                input.put("pwd", pwd);
                input.put("address", fullAddress);
                input.put("phone", phoneNumber);

                Retrofit retrofit = new retrofit2.Retrofit.Builder()
                        .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                        .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                        .build();
                RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                retrofitAPI.postEmailSignUp(input).enqueue(new Callback<EmailSignUp>() {
                    @Override
                    public void onResponse(Call<EmailSignUp> call, Response<EmailSignUp> response) {
                        EmailSignUp data = response.body();
                        if(response.isSuccessful()) {
                            Log.e("Test", "Post 성공");
                            Log.e("Test", String.valueOf(data.getStatus()));
                            Log.e("Test", String.valueOf(data.getSuccess()));
                        }
                    }

                    @Override
                    public void onFailure(Call<EmailSignUp> call, Throwable t) {
                        Log.e("Failure", "Post 실패");
                    }
                });

                startActivity(intent1);
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
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE) {
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
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                } else {
                    // Either gone or invisible
                }
            }
        });

        etAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity4_2.this, WebViewActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
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
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                } else {
                    // Either gone or invisible
                }
            }
        });

        etDetailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                detailAddress = etDetailAddress.getText().toString().trim();
                if (s.length() > 0) {
                    detailAddressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox4.setVisibility(View.VISIBLE);
                } else {
                    detailAddressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox4.setVisibility(View.INVISIBLE);
                }
                fullAddress = address + ", " + detailAddress;

                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE) {
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
    public void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        etAddress.setText(data);
                    }
                }
                break;
        }
    }
}