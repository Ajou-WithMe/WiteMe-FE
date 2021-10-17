package com.example.withme.user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class FindEmailActivity extends AppCompatActivity {

    private EditText etName, etPhoneNumber;
    private Button enterComplete;
    String name, phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_email);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        etName = (EditText) findViewById(R.id.etName);
        etPhoneNumber = (EditText)findViewById(R.id.etPhoneNumber);

        enterComplete = (Button) findViewById(R.id.enterComplete);

        enterComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = etName.getText().toString().trim();
                phoneNumber = etPhoneNumber.getText().toString().trim();
                HashMap<String, Object> input = new HashMap<>();
                input.put("name", name);
                input.put("phone", phoneNumber);

                retrofitAPI.postFindEmail(input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.body().string());
                            if (response.isSuccessful()) {
                                Log.e("Find Email", String.valueOf(jsonObject));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("Find Email", "전송 실패");
                    }
                });
            }
        });
    }
}