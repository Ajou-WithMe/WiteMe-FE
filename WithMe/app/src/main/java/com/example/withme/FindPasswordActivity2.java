package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPasswordActivity2 extends AppCompatActivity {

    private EditText first, second, third, fourth, fifth, sixth;
    private Button password;

    private String fullPassword, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password2);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");

        first = (EditText)findViewById(R.id.firstPhoneNumber);
        second = (EditText)findViewById(R.id.SecondPhoneNumber);
        third = (EditText)findViewById(R.id.ThirdPhoneNumber);
        fourth = (EditText)findViewById(R.id.FourthPhoneNumber);
        fifth = (EditText)findViewById(R.id.FifthPhoneNumber);
        sixth = (EditText)findViewById(R.id.SixthPhoneNumber);

        password = (Button)findViewById(R.id.password);

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> input = new HashMap();
                input.put("email", email);
                input.put("pwd", fullPassword);
                retrofitAPI.postChangePwd(input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                String data = jsonObject.getString("data");
                                boolean success = jsonObject.getBoolean("success");

                                if (success == true) {
                                    Intent intent = new Intent(FindPasswordActivity2.this, FindPasswordActivity3.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(FindPasswordActivity2.this, "등록되지 않은 이메일입니다.", Toast.LENGTH_SHORT).show();
                                }
                                Log.e("change Pwd", fullPassword);
                                Log.e("change Pwd", String.valueOf(jsonObject));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("change Pwd", "전송 실패");
                    }
                });
            }
        });

        first.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    second.requestFocus();
                    first.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        second.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    third.requestFocus();
                    second.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        third.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    fourth.requestFocus();
                    third.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        fourth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    fifth.requestFocus();
                    fourth.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        fifth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 1) {
                    sixth.requestFocus();
                    fifth.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                }
            }
        });

        sixth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    sixth.requestFocus();
                    sixth.setBackgroundResource(R.drawable.edittext_rounded_corner_password_focused);
                    password.setBackgroundResource(R.drawable.radius_1_clickable);
                    password.setTextColor(Color.parseColor("#222222"));
                    password.setClickable(true);
                    fullPassword = first.getText().toString() + second.getText().toString()
                            + third.getText().toString() + fourth.getText().toString()
                            + fifth.getText().toString() + sixth.getText().toString();

                    Log.e("Password", fullPassword);
                }
            }
        });
    }
}