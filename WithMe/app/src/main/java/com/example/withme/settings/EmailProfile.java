package com.example.withme.settings;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.R;
import com.example.withme.bulletin.MainBulletin;
import com.example.withme.group.GroupActivity1;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.retorfit.UploadImage;
import com.example.withme.user.WebViewActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class EmailProfile extends Fragment {

    String email, name, type, profile, phone, firstPhone,
            address, detailAddress, fullAddress, firstAddress, accessToken,
            selectedImagePath, imageFromServer;
    double latitude, longitude;

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int PASSWORD_ACTIVITY = 20000;
    private final int GET_GALLERY_IMAGE = 200;

    private Uri selectedImageUri;
    private CircleImageView profileImage;
    private TextView protectorName, loginHow, tvAddress, close, revise;
    private EditText phoneNumber, etDetailAddress, etEmail;
    private LinearLayout addressLayout;
    private ImageButton bulletinBoard;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sf = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        View rootView = inflater.inflate(R.layout.fragment_email_profile, container, false);

        close = rootView.findViewById(R.id.close);

        profileImage = rootView.findViewById(R.id.profileImage);

        bulletinBoard = rootView.findViewById(R.id.bulletinBoard);

        protectorName = rootView.findViewById(R.id.protectorName);
        loginHow = rootView.findViewById(R.id.loginHow);
        tvAddress = rootView.findViewById(R.id.tvAddress);
        close = rootView.findViewById(R.id.close);
        revise = rootView.findViewById(R.id.revise);

        phoneNumber = rootView.findViewById(R.id.phoneNumber);
        etDetailAddress = rootView.findViewById(R.id.etDetailAddress);
        etEmail = rootView.findViewById(R.id.email);

        addressLayout = rootView.findViewById(R.id.addressLayout);

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
        Bundle bundle = getArguments();
        if (bundle != null) {
            name = bundle.getString("name");
            type = bundle.getString("type");
            profile = bundle.getString("profile");
            email = bundle.getString("email");
            firstPhone = bundle.getString("phoneNumber");
            firstAddress = bundle.getString("address");
        }

        protectorName.setText(name);
        loginHow.setText(type);
        phoneNumber.setText(firstPhone);
        etEmail.setText(email);

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

                fmt.replace(R.id.fragment_container, fragment).commit();
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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        revise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = null;
                Log.e("fist", firstPhone + ", " + phone);
                Log.e("fist", firstAddress + ", " + fullAddress);

                if (phone != null) {
                    if (phone.equals(firstPhone)) {
                        phone = null;
                    }
                }
                if (fullAddress.equals(firstAddress)) {
                    fullAddress = null;
                }
                if (selectedImagePath != null) {
                    File file = new File(selectedImagePath);
                    RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    retrofitAPI.uploadImage(accessToken, body).enqueue(new Callback<UploadImage>() {
                        @Override
                        public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                            UploadImage data = response.body();

                            if (response.isSuccessful()) {
                                Log.e("make Profile", selectedImagePath);
                                if (!data.getData().equals("이미지 파일이 아닙니다.")) {
                                    imageFromServer = data.getData();
                                    if (selectedImagePath.equals(profile)) {
                                        selectedImagePath = null;
                                    }
                                        HashMap<String, Object> input = new HashMap<>();
                                        input.put("name", name);
                                        input.put("phone", phone);
                                        input.put("address", fullAddress);
                                        input.put("profileImg", selectedImagePath);

                                        retrofitAPI.changeProfile(accessToken, input).enqueue(new Callback<ResponseBody>() {
                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(getContext(), "프로필 변경을 완료하였습니다.", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                                            }
                                        });
                                }
                            }
                        }
                        @Override
                        public void onFailure(Call<UploadImage> call, Throwable t) {
                            Toast.makeText(getActivity(), "갤러리 접근 권한을 허용해주세요!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "프로필 사진을 등록하셔야 합니다.", Toast.LENGTH_SHORT).show();
                }
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

            case GET_GALLERY_IMAGE:
                if (resultCode == RESULT_OK && intent != null && intent.getData() != null) {
                    selectedImageUri = intent.getData();
                    profileImage.setImageURI(selectedImageUri);
                    selectedImagePath = uri2path(getContext(), selectedImageUri);
                }
                break;
        }
    }

    //Uri -> Path(파일경로)
    public static String uri2path(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }
}