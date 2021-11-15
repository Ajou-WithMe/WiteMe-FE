package com.example.withme.user;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

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

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton, kakaoLogin_login, buttonLogin;
    private TextView findEmail, findPassword;
    private LoginButton kakaoLoginBtn_login;
    private EditText etPassword, etID;
    private Dialog dialog;
    private SignUpActivity4_1 signUpActivity4;
    private ISessionCallback mSessionCallback; // 로그인 관리

    private int a = 5, b = 10;

    String email, password, accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences storeAccessToken;
        SharedPreferences.Editor editor;

        Intent intent1 = new Intent(this, SignUpActivity1.class);
        Intent intent2 = new Intent(this, MainActivity.class);
        dialog = new Dialog(this);

        findEmail = (TextView) findViewById(R.id.findEmail);
        findPassword = (TextView) findViewById(R.id.findPassword);

        kakaoLoginBtn_login = (LoginButton) findViewById(R.id.btn_kakao_login_basic_login);
        kakaoLogin_login = (Button) findViewById(R.id.kakaoLogin_login);
        signUpButton = (Button) findViewById(R.id.signUpButton);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        etPassword = (EditText) findViewById(R.id.etPassword);
        etID = (EditText) findViewById(R.id.etID);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI1 = retrofit.create(RetrofitAPI.class);

        storeAccessToken = getSharedPreferences("storeAccessToken", Activity.MODE_PRIVATE);
        editor = storeAccessToken.edit();

        findEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindEmailActivity.class);
                startActivity(intent);
            }
        });

        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, FindPasswordActivity.class);
                startActivity(intent);
            }
        });

        mSessionCallback = new ISessionCallback() {
            @Override
            public void onSessionOpened() {
                // 로그인 요청
                UserManagement.getInstance().me(new MeV2ResponseCallback() {

                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        // 로그인을 요청했지만 실패했을 때
                        Toast.makeText(LoginActivity.this, "로그인 도중에 오류가 발생하였습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                        // 세션이 닫혔을 때
                        Toast.makeText(LoginActivity.this, "세션이 닫혔습니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        // 로그인 성공 시
                        HashMap<String, Object> input = new HashMap<>();
                        String uID = String.valueOf(result.getId());
                        Log.e("uID", uID);

                        input.put("uid", uID);

                        retrofitAPI1.postUID(input).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        JSONObject jsonObject= new JSONObject(response.body().string());
                                        boolean data = jsonObject.getBoolean("data");

                                        Log.e("isExistUid", String.valueOf(jsonObject));
                                        Log.e("isExistUid", String.valueOf(data));

                                        if (data == false) { // uid 가 중복이라면?
                                            Log.e("여기?", "중복이므로 메인으로 이동");
                                            retrofitAPI1.postLoginKakao(input).enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()) {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                                            boolean success = jsonObject.getBoolean("success");
                                                            JSONObject data = jsonObject.getJSONObject("data");
                                                            String accessToken = data.getString("accessToken");

                                                            Log.e("kakao Login", String.valueOf(jsonObject));

                                                            if (success == true) {
                                                                editor.putString("AccessToken", accessToken);
                                                                editor.commit();
                                                                startActivity(intent2);
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        } catch (IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                }
                                            });

                                        } else { // 중복이 아닐 때
                                            Intent intent = new Intent(LoginActivity.this, SignUpActivity4_1.class);
                                            intent.putExtra("name", result.getKakaoAccount().getProfile().getNickname());
                                            intent.putExtra("uid", uID);
                                            Log.e("여기?", "중복 안되었으므로 SignUpActivity 4-1 로 이동");
                                            startActivity(intent);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.e("isExistUid", "실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e("isExistUid", "전송 실패");
                                Log.e("isExistUid", t.getMessage());

                            }
                        });
                        Toast.makeText(LoginActivity.this, "환영합니다!", Toast.LENGTH_SHORT).show();

                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException exception) {
                Toast.makeText(LoginActivity.this, "onSessionOpenFailed", Toast.LENGTH_SHORT).show();
                if (exception != null) {
                    Logger.e(exception);
                }
            }
        };
        Session.getCurrentSession().addCallback(mSessionCallback);

        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    a = 100;
                    if (a == 100 && b == 100) {
                        buttonLogin.setClickable(true);
                        buttonLogin.setTextColor(Color.parseColor("#000000"));
                    }
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    b = 100;
                    if (a == 100 && b == 100) {
                        buttonLogin.setTextColor(Color.parseColor("#000000"));
                        buttonLogin.setClickable(true);
                    }
                }
            }
        });

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = etID.getText().toString().trim();
                password = etPassword.getText().toString();
                HashMap<String, Object> input = new HashMap<>();
                input.put("email", email);
                input.put("pwd", password);

                retrofitAPI1.postLoginEmail(input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        JSONObject jsonObject = null;
                        try {
                            if (response.isSuccessful()) {
                                Log.e("Email Login", "isSuccessful");

                                jsonObject = new JSONObject(response.body().string());
                                String error = jsonObject.getString("data");
                                boolean success = jsonObject.getBoolean("success");
                                Log.e("Email Login", error + " " + success);

                                if (success == true) {
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    String accessToken = data.getString("accessToken");

                                    Log.e("Email Login", "로그인 성공");
                                    Log.e("Email Login", accessToken);

                                    editor.putString("AccessToken", accessToken);
                                    editor.commit();
                                    startActivity(intent2);
                                } else {
                                    Log.e("Not Equal Email", "못가");
                                    openDialog();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Failure", "Post 실패");
                        Log.e("Failure", t.getMessage());
                    }
                });
            }
        });

        kakaoLogin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kakaoLoginBtn_login.performClick();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent1);
            }
        });
    }

    private void openDialog() {
        dialog.setContentView(R.layout.login_failure_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button understand = dialog.findViewById(R.id.understand);

        understand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}