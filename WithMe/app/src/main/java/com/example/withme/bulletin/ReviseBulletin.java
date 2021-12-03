package com.example.withme.bulletin;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReviseBulletin extends Fragment {

    private String phoneNumber, accessToken, uid, title, location, activityRadius, clothes, content, stFinalLocation;
    private int protectionPersonNum;
    private JSONObject protectionPerson;
    private LinearLayout protectionPersonLayout;
    private EditText etTitle, etActivityRadius, etClothes, etContent, finalLocation;
    private TextView category, register;
    private CheckBox checkBox;


    MainActivity activity;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    ArrayList<CircleImageView> circleImageViews = new ArrayList<>();
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<File> file = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_revise_bulletin, container, false);

        SharedPreferences sf = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        protectionPersonLayout = (LinearLayout) rootView.findViewById(R.id.getAllProtectionLayout);

        etTitle = (EditText) rootView.findViewById(R.id.etTitle);
        category = (TextView) rootView.findViewById(R.id.category);
        etActivityRadius = (EditText) rootView.findViewById(R.id.activityRadius);
        etClothes = (EditText) rootView.findViewById(R.id.clothes);
        etContent = (EditText) rootView.findViewById(R.id.etContent);
        finalLocation = (EditText) rootView.findViewById(R.id.finalLocation);

        register = (TextView) rootView.findViewById(R.id.register);

        checkBox = (CheckBox) rootView.findViewById(R.id.checkBox);


        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString("title");
            location = bundle.getString("location");
            activityRadius = bundle.getString("activityRadius");
            clothes = bundle.getString("description");
            content = bundle.getString("content");
        }

        etTitle.setText(title);
        category.setText(location);
        category.setTextColor(Color.parseColor("#333333"));
        etActivityRadius.setText(activityRadius);
        etClothes.setText(clothes);
        etContent.setText(content);
        finalLocation.setText("아직 구현 안됨");
        etContent.setText(content);

        etTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title = etTitle.getText().toString().trim();
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(4);
            }
        });

        etActivityRadius.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityRadius = etActivityRadius.getText().toString().trim();
            }
        });

        etClothes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                clothes = etClothes.getText().toString().trim();
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                content = etContent.getText().toString().trim();
            }
        });

        retrofitAPI.getAllprotection(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");
                        Log.e("getAllProtection", jsonObject.toString());

                        if (success == true) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            protectionPersonNum = data.length();
                            Log.e("너비", data.toString());

                            for (int j = 0; j < data.length(); j++) {
                                protectionPerson = data.getJSONObject(j);

                                String name = protectionPerson.getString("name");
                                String profile = protectionPerson.getString("profileImg");
                                uid = protectionPerson.getString("uid");

                                LinearLayout linearLayout = new LinearLayout(getContext());
                                linearLayout.setId(j);
                                ViewGroup.LayoutParams layout= new LinearLayout.LayoutParams(150, 222);

                                linearLayout.setLayoutParams(layout);
                                linearLayout.setOrientation(LinearLayout.VERTICAL);

                                protectionPersonLayout.addView(linearLayout);

                                CircleImageView circleImageView = new CircleImageView(getActivity().getApplicationContext());
                                ViewGroup.LayoutParams circle= new LinearLayout.LayoutParams(150, 150);
                                circleImageView.setLayoutParams(circle);

                                TextView textView = new TextView(getActivity().getApplicationContext());
                                textView.setText(name);
                                textView.setTextSize(16);
                                textView.setTextColor(Color.parseColor("#BDBDBD"));
//
                                Glide.with(getActivity().getApplicationContext()).load(profile).into(circleImageView);
                                if (profile.equals("null")) {
                                    circleImageView.setBackgroundResource(R.drawable.solo_white);
                                }

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(69,0,0,0);
                                linearLayout.setLayoutParams(lp);

                                linearLayout.addView(circleImageView);
                                linearLayout.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp_text.setMargins(0,12,0,0);
                                lp_text.gravity = Gravity.CENTER;
                                textView.setLayoutParams(lp_text);
                                linearLayout.addView(textView);
                                textViews.add(textView);
                                circleImageViews.add(circleImageView);

                                linearLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        circleImageViews.get(linearLayout.getId()).setBorderColor(Color.parseColor("#FED537"));
                                        circleImageViews.get(linearLayout.getId()).setBorderWidth(9);

                                        textView.setTextColor(Color.parseColor("#000000"));
                                    }
                                });
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> input = new HashMap<>();
                Log.e("clicked", "clicked");
                if (checkBox.isChecked()) {
                    retrofitAPI.getProfile(accessToken).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    phoneNumber = jsonObject.getString("phone");
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
                input.put("title", title);
                input.put("location", category.getText().toString());
                input.put("activityRadius", activityRadius);
                input.put("description", clothes);
                input.put("contact", 0);
                input.put("longitude", 126);
                input.put("latitude", 37);
                input.put("content", content);
                input.put("files", file);
                input.put("protection", uid);

                retrofitAPI.savePost(accessToken, input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                Log.e("savePost", "성공");
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                boolean success = jsonObject.getBoolean("success");
                                String data = jsonObject.getString("data");

                                if (success == true) {
                                    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();
                                    activity.onFragmentChange(0);
                                }
                                Log.e("savePost", data);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("savePost", t.getMessage());
                    }
                });
            }
        });


        return rootView;
    }
}