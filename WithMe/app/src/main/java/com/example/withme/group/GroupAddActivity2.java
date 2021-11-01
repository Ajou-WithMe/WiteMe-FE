package com.example.withme.group;

import androidx.appcompat.app.AppCompatActivity;

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

public class GroupAddActivity2 extends AppCompatActivity {

    private EditText groupCode;
    private TextView warningMessage;
    private Button getInGroup;
    private String code;

    private Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add2);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        String accessToken = sf.getString("AccessToken", "");

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        groupCode = (EditText) findViewById(R.id.groupCode);

        warningMessage = (TextView)findViewById(R.id.warningMessage);

        dialog = new Dialog(this);

        getInGroup = (Button) findViewById(R.id.startWithMe);

        groupCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                code = groupCode.getText().toString().trim();

                if (code.length() == 6 && s.length() > 0) {
                    groupCode.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    getInGroup.setBackgroundResource(R.drawable.radius_1_clickable);
                    getInGroup.setTextColor(Color.parseColor("#222222"));
                    getInGroup.setEnabled(true);
                    warningMessage.setVisibility(View.INVISIBLE);
                } else {
                    getInGroup.setBackgroundResource(R.drawable.radius_1_nonclickable);
                    getInGroup.setEnabled(false);
                    groupCode.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected_false);
                    warningMessage.setText("코드 여섯자리를 정확히 입력해주세요");
                    warningMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        getInGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> input = new HashMap<>();

                input.put("code", code);

                retrofitAPI.postPartyMember(accessToken, input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(response.body().string());
                                String data = jsonObject.getString("data");
                                boolean success = jsonObject.getBoolean("success");

                                Log.e("Party Member", String.valueOf(jsonObject));

                                if (success == false) {
                                    Toast.makeText(GroupAddActivity2.this, data, Toast.LENGTH_SHORT).show();
                                } else {
                                    if (getInGroup.isEnabled()) {
                                        openDialog();
                                    }
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
            }
        });
    }
    private void openDialog() {
        dialog.setContentView(R.layout.group_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GroupAddActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(GroupAddActivity2.this, GroupAddActivity1.class);
                startActivity(intent);
            }
        });
        dialog.show();
    }
}