package com.example.withme.settings;
import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.bulletin.BulletinDetail;
import com.example.withme.bulletin.MainBulletin;
import com.example.withme.group.BottomSheetDialogMain;
import com.example.withme.intro.DescriptionActivity;
import com.example.withme.retorfit.EmailSignUp;
import com.example.withme.retorfit.RetrofitAPI;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kyleduo.switchbutton.SwitchButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Settings extends Fragment {

    MainActivity activity;

    private String accessToken, name, image, phoneNumber, email, address;
    private int type;

    private TextView protectorName, loginHow;
    private CircleImageView profileImg;
    private ImageButton reviseProfile, bulletinBoard, group;
    private SwitchButton pushAlarm;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        SharedPreferences sf = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        SharedPreferences pref;
        SharedPreferences.Editor editor;

        pref = getActivity().getSharedPreferences("pref", Activity.MODE_PRIVATE);
        editor = pref.edit();

        protectorName = rootView.findViewById(R.id.protectorName);
        loginHow = rootView.findViewById(R.id.loginHow);

        pushAlarm = rootView.findViewById(R.id.pushAlarm);

        profileImg = rootView.findViewById(R.id.profileImage);

        reviseProfile = rootView.findViewById(R.id.reviseProfile);
        bulletinBoard = rootView.findViewById(R.id.bulletinBoard);
        group = rootView.findViewById(R.id.group);

        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("AccessToken", accessToken);

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialogMain bottomSheetDialogMain = new BottomSheetDialogMain();
                bottomSheetDialogMain.show(getActivity().getSupportFragmentManager(), "bottomSheet");
                bottomSheetDialogMain.setArguments(bundle);
            }
        });

        bulletinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new MainBulletin();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
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
                            Log.e("data", data.toString());
                            email = data.getString("email");
                            type = data.getInt("type");
                            name = data.getString("name");
                            phoneNumber = data.getString("phone");
                            image = data.getString("profileImg");
                            address = data.getString("address");

                            protectorName.setText(name);
                            if (image.equals("null")) {
                                profileImg.setBackgroundResource(R.drawable.solo_white);
                            } else {
                                Glide.with(getContext()).load(image).into(profileImg);
                            }

                            if (type == 0) {
                                loginHow.setText("이메일 로그인");
                            } else if (type == 1) {
                                loginHow.setText("카카오톡 로그인");
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

        reviseProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    Bundle result = new Bundle();
                    result.putString("name", name);
                    result.putString("type", "카카오톡 로그인");
                    result.putString("profile", image);
                    result.putString("phoneNumber", phoneNumber);
                    result.putString("address", address);

                    Fragment fragment = new KakaoProfile();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fmt = fm.beginTransaction();
                    fragment.setArguments(result);

                    fmt.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                } else if (type == 0) {
                    Bundle result = new Bundle();
                    result.putString("email", email);
                    result.putString("name", name);
                    result.putString("type", "이메일 로그인");
                    result.putString("profile", image);
                    result.putString("phoneNumber", phoneNumber);
                    result.putString("address", address);

                    Fragment fragment = new EmailProfile();
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    FragmentTransaction fmt = fm.beginTransaction();
                    fragment.setArguments(result);

                    fmt.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
                }
            }
        });

        pushAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editor.putString("checked", "yes");
                } else {
                    editor.putString("checked", "no");
                }
                editor.apply();
            }
        });

        if (pref.getString("checked", "no").equals("yes")) {
            pushAlarm.setChecked(true);
        } else {
            pushAlarm.setChecked(false);
        }

        return rootView;
    }
}