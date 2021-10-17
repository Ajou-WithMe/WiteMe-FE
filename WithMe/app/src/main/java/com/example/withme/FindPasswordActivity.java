package com.example.withme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class FindPasswordActivity extends AppCompatActivity implements TextWatcher {

    private EditText editTextEmail, emailCodeText;
    private FindPasswordActivity.MainHandler mainHandler;
    private TextView warningMessage, authenticateComplete, messageComplete;
    private Button emailAuthentication, authenticate;
    private LinearLayout layoutAuthenticate;
    private String emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private String email;

    static int value;
    String GmailCode;
    int mailSend = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);

        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextEmail.addTextChangedListener(this);

        warningMessage = (TextView)findViewById(R.id.warningMessage);
        messageComplete = (TextView) findViewById(R.id.messageComplete);
        authenticateComplete = (TextView) findViewById(R.id.authenticateComplete);
        emailCodeText = (EditText)findViewById(R.id.emailCodeText);
        emailAuthentication = (Button)findViewById(R.id.emailAuthentication);
        authenticate = (Button)findViewById(R.id.authenticate);
        layoutAuthenticate = (LinearLayout) findViewById(R.id.layoutAuthenticate);

        // 이메일 인증하는 부분
        // 인증코드 시간 초가 흐르는데, 이때 인증을 마치지 못하면 인증 코드를 지우게 만든다.
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailCodeText.getText().toString().equals(GmailCode)) {
                    authenticate.setBackgroundResource(R.drawable.radius_1_nonclickable);
                    emailCodeText.setBackgroundResource(R.drawable.edittext_bg_selector_authenticate_complete);
                    emailCodeText.setClickable(false);
                    emailCodeText.setFocusable(false);
                    authenticateComplete.setVisibility(View.INVISIBLE);
                    messageComplete.setText("인증이 완료되었습니다!");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(FindPasswordActivity.this, FindPasswordActivity2.class);
                            startActivity(intent);
                        }
                    }, 1000);
                } else {
                    emailCodeText.setBackgroundResource(R.drawable.edittext_bg_selector_not_validate);
                    Toast.makeText(getApplicationContext(), "인증번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        email = editTextEmail.getText().toString().trim();
        if (email.matches(emailValidation) && s.length() > 0) {
            editTextEmail.setBackgroundResource(R.drawable.edittext_bg_selector);
            warningMessage.setText("");

            emailAuthentication.setBackgroundResource(R.drawable.radius_3);
            emailAuthentication.setClickable(true);
            authenticate.setBackgroundResource(R.drawable.radius_3);
            authenticate.setClickable(true);

            emailAuthentication.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, Object> input = new HashMap<>();
                    input.put("email", email);

                    Retrofit retrofit = new retrofit2.Retrofit.Builder()
                            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                            .build();
                    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

                    retrofitAPI.postFindPwd(input).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if(response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    String data = jsonObject.getString("data");
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success == true) {
                                        Log.e("인증코드 발송", String.valueOf(jsonObject));
                                        emailAuthentication.setText("이메일로 인증번호 다시 받기");
                                        authenticateComplete.setText("입력해주신 이메일로 인증코드를 보내드렸어요! 인증코드 확인 후 입력해주세요.");
                                        GmailCode = data;
                                        mainHandler = new MainHandler();
                                        // 핸들러 객체 생성

                                        if(mailSend == 0) {
                                            value = 300;
                                            // 쓰레드 객체 생성
                                            BackgroundThread backgroundThread = new BackgroundThread();
                                            // 쓰레드 스타트
                                            backgroundThread.start();
                                            mailSend += 1;
                                        } else {
                                            value = 300;
                                        }
                                    }
                                    Log.e("인증코드 발송", data);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.e("인증코드 발송", "Post 실패");

                        }
                    });
                }
            });
        } else {
            editTextEmail.setBackgroundResource(R.drawable.edittext_bg_selector_not_validate);
            warningMessage.setText("이메일을 끝까지 정확히 입력해주세요!");
            emailAuthentication.setBackgroundColor(Color.parseColor("#E9E9E9"));
            emailAuthentication.setClickable(false);
            authenticate.setBackgroundColor(Color.parseColor("#E9E9E9"));
            authenticate.setClickable(false);
        }
    }
    // 쓰레드로부터 메시지를 받아 처리하는 핸들러
    // 메인에서부터 생성된 핸들러만이 UI를 컨트롤 할 수 있다.
    class MainHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int min, sec;

            Bundle bundle = message.getData();
            int value = bundle.getInt("value");

            min = value / 60;
            sec = value % 60;

            // 초가 10보다 작으면 앞에 0이 더 붙어서 나오도록 한다.
            if (sec < 10) {
                // 텍스트뷰에 시간초가 카운팅
                emailCodeText.setHint("0" + min + " : 0" + sec);
            } else {
                emailCodeText.setHint("0" + min + " : " + sec);
            }
            emailCodeText.setHintTextColor(Color.parseColor("#FF302B"));
        }
    }

    // 시간 초가 카운트 되는 쓰레드
    class BackgroundThread extends Thread {
        // 300초는 5분
        // 메인 쓰레드에 value 를 전달하여 시간초가 카운트 되게 한다.

        public void run() {
            // 300초 보다 밸류값이 적거나 같으면 계속 실행시켜라.
            while(true) {
                value -= 1;
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                }

                Message message = mainHandler.obtainMessage();
                // 메세지는 번들의 객체에 담아서 메인 핸들러에 전달한다.
                Bundle bundle = new Bundle();
                bundle.putInt("value", value);
                message.setData(bundle);

                // 핸들러에 메세지 객체 보내기
                mainHandler.sendMessage(message);

                if (value <= 0) {
                    GmailCode = "";
                    break;
                }
            }
        }
    }
}