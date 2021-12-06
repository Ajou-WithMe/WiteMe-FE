package com.example.withme.settings;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.R;
import com.example.withme.group.GroupActivity3;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.user.WebViewActivity;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class KakaoProfile extends Fragment {

    String name, type, profile, phone, address, detailAddress, fullAddress;
    double latitude, longitude;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int PASSWORD_ACTIVITY = 20000;
    private final int GET_GALLERY_IMAGE = 200;

    private CircleImageView profileImage;
    private TextView protectorName, loginHow, tvAddress, close;
    private EditText phoneNumber, etDetailAddress;
    private LinearLayout addressLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_kakao_profile, container, false);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        profileImage = rootView.findViewById(R.id.profileImage);

        protectorName = rootView.findViewById(R.id.protectorName);
        loginHow = rootView.findViewById(R.id.loginHow);
        tvAddress = rootView.findViewById(R.id.tvAddress);
        close = rootView.findViewById(R.id.close);
        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        etDetailAddress = rootView.findViewById(R.id.etDetailAddress);

        addressLayout = rootView.findViewById(R.id.addressLayout);

        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            type = bundle.getString("type");
            profile = bundle.getString("profile");
            phone = bundle.getString("phoneNumber");
        }

        protectorName.setText(name);
        loginHow.setText(type);
        phoneNumber.setText(phone);

        if (profile.equals("null")) {
            profileImage.setBackgroundResource(R.drawable.solo_white);
        } else {
            Glide.with(getContext()).load(profile).into(profileImage);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Settings();
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction fmt = fm.beginTransaction();

                fmt.replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        phoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = phoneNumber.getText().toString().trim();
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

                fullAddress = address + ", " + detailAddress;
            }
        });

        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        final Geocoder geocoder = new Geocoder(getContext());

        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        tvAddress.setText(data);
                        tvAddress.setTextColor(Color.parseColor("#333333"));

                        address = (String)tvAddress.getText();
                        List<Address> list = null;

                        try {
                            list = geocoder.getFromLocationName(address, 10); // 지역 이름, 읽을 개수
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("GeoCoder", "입출력 오류 - 서버에서 주소 전환 시 에러 발생");
                        }

                        // 지도 화면에 위도 경도 정보 넘기기
                        if (list != null) {
                            if (list.size() == 0) {
                                Toast.makeText(getContext(), "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                latitude = list.get(0).getLatitude();
                                longitude = list.get(0).getLongitude();

                                Log.e("위도", String.valueOf(latitude));
                                Log.e("경도", String.valueOf(longitude));
                            }
                        }
                    }
                }
                break;

//            case GET_GALLERY_IMAGE:
//                if (resultCode == RESULT_OK && intent != null && intent.getData() != null) {
//                    selectedImageUri = intent.getData();
//                    profileImage.setImageURI(selectedImageUri);
//                    selectedImagePath = uri2path(this, selectedImageUri);
//                }
//                break;
        }
    }
}