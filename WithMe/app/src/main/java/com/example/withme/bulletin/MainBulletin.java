package com.example.withme.bulletin;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.group.BottomSheetDialogMain;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.settings.Settings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainBulletin extends Fragment {

    MainActivity activity;
    private String accessToken, phoneNumber;
    private TextView category, region;
    private ImageButton settings, group;
    private LinearLayout allPostLayout;
    private long page = 0;
    private static final int SEARCH_LOCATION_ACTIVITY = 300;
    private List<Address> finalLocations;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mainbulletin, container, false);
        // Inflate the layout for this fragment

        SharedPreferences sf = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        region = (TextView) rootView.findViewById(R.id.region);
        TextView myPost = (TextView) rootView.findViewById(R.id.myPost);
        category = (TextView) rootView.findViewById(R.id.category);
        settings = rootView.findViewById(R.id.settings);
        group = rootView.findViewById(R.id.group);

        allPostLayout = rootView.findViewById(R.id.allPostLayout);

        ImageButton write = (ImageButton) rootView.findViewById(R.id.write);

        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("AccessToken", accessToken);

        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(5);
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, SEARCH_LOCATION_ACTIVITY);
            }
        });

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogMain bottomSheetDialogMain = new BottomSheetDialogMain();
                bottomSheetDialogMain.show(getActivity().getSupportFragmentManager(), "bottomSheet");
                bottomSheetDialogMain.setArguments(bundle);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Settings();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }
        });

        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, SEARCH_LOCATION_ACTIVITY);
            }
        });

        retrofitAPI.getProfile(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            phoneNumber = data.getString("phone");
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
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode) {
            case SEARCH_LOCATION_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("category");
                    if (data != null) {
                        category.setText(data);

                        region.setVisibility(View.GONE);

                        retrofitAPI.postPaging(accessToken, page, data).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                        boolean success = jsonObject.getBoolean("success");
                                        Log.e("postPaging", jsonObject.toString());

                                        if (success == true) {
                                            JSONObject data = jsonObject.getJSONObject("data");
                                            JSONArray post = data.getJSONArray("post");
                                            allPostLayout.removeAllViews();
                                            for (int i = 0; i < post.length(); i++) {
                                                JSONObject comment = post.getJSONObject(i);
                                                Log.e("post", comment.toString());

                                                long id = comment.getLong("id");
                                                String img = comment.getString("img");
                                                String title = comment.getString("title");
                                                String clothes = comment.getString("description");
                                                double latitude = comment.getDouble("latitude");
                                                double longitude = comment.getDouble("longitude");
                                                String createdAt = comment.getString("createdAt");

                                                Log.e("post2", comment.toString());

                                                LinearLayout linearLayout = new LinearLayout(getContext());
                                                linearLayout.setId(i);
                                                ViewGroup.LayoutParams layout= new LinearLayout.LayoutParams(939, 198);

                                                linearLayout.setLayoutParams(layout);
                                                linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                                                allPostLayout.addView(linearLayout);

                                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                                        939,
                                                        198);
                                                lp.setMargins(0,72,0,0);
                                                linearLayout.setLayoutParams(lp);

                                                ImageView postImg = new ImageView(getContext());
                                                ViewGroup.LayoutParams imgParam= new LinearLayout.LayoutParams(300, 198);
                                                postImg.setLayoutParams(imgParam);

                                                Glide.with(getActivity().getApplicationContext()).load(img).into(postImg);
                                                if (img.equals("null")) {
                                                    postImg.setBackgroundResource(R.drawable.no_profile);
                                                } else {
                                                    Glide.with(getContext()).load(img).into(postImg);
                                                }
                                                linearLayout.addView(postImg);

                                                LinearLayout detailPost = new LinearLayout(getContext());
                                                detailPost.setId(i);
                                                ViewGroup.LayoutParams layout_detail = new LinearLayout.LayoutParams(606, 198);

                                                detailPost.setLayoutParams(layout_detail);
                                                detailPost.setOrientation(LinearLayout.VERTICAL);

                                                linearLayout.addView(detailPost);
                                                detailPost.setGravity(Gravity.RIGHT);

                                                TextView tv_title = new TextView(getActivity().getApplicationContext());
                                                tv_title.setText(title);
                                                tv_title.setTextSize(14);
                                                tv_title.setTextColor(Color.parseColor("#222222"));

                                                LinearLayout.LayoutParams lp_text1 = new LinearLayout.LayoutParams(
                                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                                lp_text1.setMargins(36,30,0,0);

                                                LinearLayout.LayoutParams lp_text2 = new LinearLayout.LayoutParams(
                                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                                lp_text2.setMargins(36,12,0,0);

                                                LinearLayout.LayoutParams lp_text3 = new LinearLayout.LayoutParams(
                                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                                lp_text3.setMargins(36,6,0,0);

                                                detailPost.addView(tv_title);
                                                tv_title.setLayoutParams(lp_text3);

                                                TextView tv_final = new TextView(getActivity().getApplicationContext());

                                                Geocoder g = new Geocoder(getContext());

                                                try {
                                                    finalLocations = g.getFromLocation(latitude,longitude,10);
                                                } catch (IOException e) {
                                                    e.printStackTrace();
                                                    Log.d("test","입출력오류");
                                                }
                                                if(finalLocations!=null){
                                                    if(finalLocations.size()==0){
                                                        tv_final.setText("마지막 목격 장소 : 주소 찾기 오류");
                                                    }else{
                                                        tv_final.setText("마지막 목격 장소 : " + finalLocations.get(0).getAddressLine(0));
                                                    }
                                                    tv_final.setTextSize(12);
                                                    tv_final.setTextColor(Color.parseColor("#222222"));
                                                    tv_final.setSelected(true);
                                                    tv_final.setSingleLine();
                                                    tv_final.setEllipsize(TextUtils.TruncateAt.MARQUEE);
                                                }

                                                detailPost.addView(tv_final);
                                                tv_final.setLayoutParams(lp_text1);

                                                TextView tv_clothes = new TextView(getActivity().getApplicationContext());
                                                tv_clothes.setText("인상 착의 : " + clothes);
                                                tv_clothes.setTextSize(12);
                                                tv_clothes.setTextColor(Color.parseColor("#222222"));

                                                detailPost.addView(tv_clothes);
                                                tv_clothes.setLayoutParams(lp_text2);

                                                linearLayout.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        Bundle result = new Bundle();
                                                        result.putLong("id", id);

                                                        retrofitAPI.getPostDetail(accessToken, id).enqueue(new Callback<ResponseBody>() {
                                                            @Override
                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                if (response.isSuccessful()) {
                                                                    try {
                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                        boolean success = jsonObject.getBoolean("success");

                                                                        if (success == true) {
                                                                            JSONObject data = jsonObject.getJSONObject("data");
                                                                            String title = data.getString("title");
                                                                            JSONArray files = data.getJSONArray("files");
                                                                            String createdAt = data.getString("createdAt");
                                                                            String content = data.getString("content");
                                                                            String activityRadius = data.getString("activityRadius");
                                                                            String name = data.getString("name");
                                                                            String contact = data.getString("contact");
                                                                            String description = data.getString("description");
                                                                            ArrayList<String> fileList = new ArrayList<>();
                                                                            double latitude = data.getDouble("latitude");
                                                                            double longitude = data.getDouble("longitude");


                                                                            for (int file = 0; file < files.length(); file++) {
                                                                                fileList.add(files.getString(file));
                                                                            }

                                                                            result.putString("title", title);
                                                                            result.putString("createdAt", createdAt);
                                                                            Log.e("createdAt", createdAt);
                                                                            result.putStringArrayList("files", fileList);
                                                                            result.putString("content", content);
                                                                            result.putString("activityRadius", activityRadius);
                                                                            result.putString("name", name);
                                                                            result.putString("description", description);
                                                                            result.putString("phone", contact);
                                                                            result.putDouble("latitude", latitude);
                                                                            result.putDouble("longitude", longitude);
                                                                        }
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    } catch (IOException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                    Fragment fragment = new BulletinDetail();
                                                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                                                    FragmentTransaction fmt = fm.beginTransaction();
                                                                    fragment.setArguments(result);

                                                                    fmt.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                                                                }

                                                            }

                                                            @Override
                                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                                            }
                                                        });
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
                    }
                }
                break;
        }
    }
}