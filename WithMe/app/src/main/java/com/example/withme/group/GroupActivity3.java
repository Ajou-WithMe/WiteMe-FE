package com.example.withme.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.user.WebViewActivity;
import com.example.withme.retorfit.UploadImage;

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

public class GroupActivity3 extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int PASSWORD_ACTIVITY = 20000;
    private final int GET_GALLERY_IMAGE = 200;
    private String name, id, address, detailAddress, fullAddress, code, beforePassword;
    private ImageView checkbox1, checkbox2, checkbox3, checkbox4, checkbox5;
    private LinearLayout nameLayout, IDLayout, passwordLayout, addressLayout, detailAddressLayout;
    private EditText etName, etID, etDetailAddress;
    private TextView tvPassword, tvAddress;
    private Button startWithMe;
    private ImageButton xButton;
    private ImageView editProfile;
    private CircleImageView profileImage;
    private double latitude, longitude;
    private Uri selectedImageUri;
    private String selectedImagePath, imageFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group3);

        Intent intent1 = new Intent(this, GroupActivity5.class);
        Intent data = getIntent();
        code = data.getStringExtra("code");
        intent1.putExtra("id", id);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        String accessToken = sf.getString("AccessToken", "");
        Log.e("AccessToken", accessToken);

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        startWithMe = (Button) findViewById(R.id.startWithMe);

        xButton = findViewById(R.id.xButton);

        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        editProfile = (ImageView) findViewById(R.id.editProfile);

        etName = (EditText) findViewById(R.id.etName);
        etID = (EditText) findViewById(R.id.etID);
        etDetailAddress = (EditText) findViewById(R.id.etDetailAddress);

        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        checkbox1 = (ImageView) findViewById(R.id.checkbox1);
        checkbox2 = (ImageView) findViewById(R.id.checkbox2);
        checkbox3 = (ImageView) findViewById(R.id.checkbox3);
        checkbox4 = (ImageView) findViewById(R.id.checkbox4);
        checkbox5 = (ImageView) findViewById(R.id.checkbox5);

        nameLayout = (LinearLayout) findViewById(R.id.nameLayout);
        IDLayout = (LinearLayout) findViewById(R.id.IDLayout);
        passwordLayout = (LinearLayout) findViewById(R.id.passwordLayout);
        addressLayout = (LinearLayout) findViewById(R.id.addressLayout);
        detailAddressLayout = (LinearLayout) findViewById(R.id.detailAddressLayout);

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity3.this, MainActivity.class);
                startActivity(intent);
            }
        });

        startWithMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                                Log.e("imageFromServer", imageFromServer);

                                HashMap<String, Object> input = new HashMap<>();
                                input.put("name", name);
                                input.put("email", id);
                                input.put("pwd", beforePassword);
                                input.put("address", fullAddress);
                                input.put("code", code);
                                input.put("profile", imageFromServer);

                                Log.e("input", input.toString());

                                retrofitAPI.postProtection(accessToken, input).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            JSONObject jsonObject = null;
                                            try {
                                                jsonObject = new JSONObject(response.body().string());
                                                String data = jsonObject.getString("data");
                                                boolean success = jsonObject.getBoolean("success");

                                                Log.e("Protection", String.valueOf(jsonObject));

                                                if (success == false) {
                                                    Toast.makeText(GroupActivity3.this, "이미 회원가입된 아이디입니다.", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    intent1.putExtra("uid", data);
                                                    intent1.putExtra("latitude", latitude);
                                                    intent1.putExtra("longitude", longitude);
                                                    startActivity(intent1);

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
                                        Log.e("Protection", "전송 실패");
                                    }
                                });

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImage> call, Throwable t) {

                    }
                });
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
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


        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name = etName.getText().toString().trim();
                if (s.length() > 0) {
                    nameLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox1.setVisibility(View.VISIBLE);
                } else {
                    nameLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox1.setVisibility(View.INVISIBLE);
                }

                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE
                        && checkbox5.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                    startWithMe.setText("다음");
                } else {
                    // Either gone or invisible
                }
            }
        });

        etID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                id = etID.getText().toString().trim();
                if (s.length() > 0) {
                    IDLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox2.setVisibility(View.VISIBLE);
                } else {
                    IDLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox2.setVisibility(View.INVISIBLE);
                }

                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE
                        && checkbox5.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setClickable(true);
                    startWithMe.setText("다음");
                } else {
                    // Either gone or invisible
                }
            }
        });

        tvPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity3.this, GroupActivity4.class);
                startActivityForResult(intent, PASSWORD_ACTIVITY);
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupActivity3.this, WebViewActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
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
                if (s.length() > 0) {
                    detailAddressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    checkbox5.setVisibility(View.VISIBLE);
                } else {
                    detailAddressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                    checkbox5.setVisibility(View.INVISIBLE);
                }
                fullAddress = address + ", " + detailAddress;

                if (checkbox1.getVisibility() == View.VISIBLE && checkbox2.getVisibility() == View.VISIBLE
                        && checkbox3.getVisibility() == View.VISIBLE && checkbox4.getVisibility() == View.VISIBLE
                        && checkbox5.getVisibility() == View.VISIBLE) {
                    // Its visible
                    startWithMe.setBackgroundColor(Color.parseColor("#FED537"));
                    startWithMe.setTextColor(Color.parseColor("#222222"));
                    startWithMe.setText("다음");
                    startWithMe.setClickable(true);
                } else {
                    // Either gone or invisible
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        final Geocoder geocoder = new Geocoder(this);

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
                                Toast.makeText(GroupActivity3.this, "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                latitude = list.get(0).getLatitude();
                                longitude = list.get(0).getLongitude();

                                Log.e("위도", String.valueOf(latitude));
                                Log.e("경도", String.valueOf(longitude));
                            }
                        }

                        if (address.length() > 8) {
                            addressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                            checkbox4.setVisibility(View.VISIBLE);
                        } else {
                            addressLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_not_selected);
                            checkbox4.setVisibility(View.INVISIBLE);
                        }
                    }
                }
                break;
            case PASSWORD_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("password");
                    beforePassword = data;
                    if (data != null) {
                        tvPassword.setText("******");
                        tvPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        tvPassword.setTextColor(Color.parseColor("#333333"));

                        checkbox3.setVisibility(View.VISIBLE);
                        passwordLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    }
                }
                break;

            case GET_GALLERY_IMAGE:
                if (resultCode == RESULT_OK && intent != null && intent.getData() != null) {
                    selectedImageUri = intent.getData();
                    profileImage.setImageURI(selectedImageUri);
                    selectedImagePath = uri2path(this, selectedImageUri);
                    Log.e("selectedImagePath", selectedImagePath);
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