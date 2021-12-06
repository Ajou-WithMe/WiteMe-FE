package com.example.withme.bulletin;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.group.BottomSheetDialogMain;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.settings.Settings;
import com.example.withme.user.WebViewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

public class WriteBulletin extends Fragment {

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    ArrayList<CircleImageView> circleImageViews = new ArrayList<>();
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<String> file = new ArrayList<>();
    ArrayList<String> pathList = new ArrayList<>();
    ArrayList<String> imagesFromServer = new ArrayList<>();


    MainActivity activity;
    private Handler mHandler = new Handler();

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private String selectedImagePath, imageFromServer, profile, uid, accessToken, title, clothes, location,
            activityRadius, content, phoneNumber, finalLocations;
    private double latitude, longitude;
    private View view;
    private int protectionPersonNum;
    private JSONObject protectionPerson;
    private LinearLayout protectionPersonLayout, pictureLayout, finalLocationLayout;
    private HorizontalScrollView horizontalScrollView;
    private EditText etTitle, etClothes, etActivityRadius, etContent;
    private TextView category, register, uploadPicture, finalLocation;
    private CheckBox checkBox;
    private ImageButton settings, group;

    int i=0;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private final int SEARCH_LOCATION_ACTIVITY = 300;
    private final int GET_GALLERY_IMAGE = 200;


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
        // Inflate the layout for this fragment
        SharedPreferences sf = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        view = inflater.inflate(R.layout.fragment_writebulletin, container, false);

        protectionPersonLayout = (LinearLayout) view.findViewById(R.id.getAllProtectionLayout);
        pictureLayout = (LinearLayout) view.findViewById(R.id.pictureLayout);
        finalLocationLayout = (LinearLayout) view.findViewById(R.id.finalLocationLayout);
        horizontalScrollView = (HorizontalScrollView) view.findViewById(R.id.horizontalScrollView);

        register = (TextView) view.findViewById(R.id.register);
        category = (TextView) view.findViewById(R.id.category);
        uploadPicture = (TextView) view.findViewById(R.id.uploadPicture);
        finalLocation = (TextView) view.findViewById(R.id.finalLocation);

        etClothes = (EditText) view.findViewById(R.id.clothes);
        etTitle = (EditText) view.findViewById(R.id.etTitle);
        etContent = (EditText) view.findViewById(R.id.etContent);
        etActivityRadius = (EditText) view.findViewById(R.id.activityRadius);

        checkBox = (CheckBox) view.findViewById(R.id.checkBox);

        settings = view.findViewById(R.id.settings);
        group = view.findViewById(R.id.group);

        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("AccessToken", accessToken);

        if (getArguments() != null)
        {
            location = getArguments().getString("location"); // 프래그먼트1에서 받아온 값 넣기
            category.setText(location);
            category.setTextColor(Color.parseColor("#333333"));
        }

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

        finalLocationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
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

        uploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyStoragePermissions(getActivity());

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
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
                                profile = protectionPerson.getString("profileImg");
                                uid = protectionPerson.getString("uid");

                                Log.e("profile", profile);

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
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, SEARCH_LOCATION_ACTIVITY);
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
                input.put("longitude", longitude);
                input.put("latitude", latitude);
                input.put("content", content);
                input.put("files", imagesFromServer);
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
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        final Geocoder geocoder = new Geocoder(getContext());

        switch(requestCode) {
            case SEARCH_LOCATION_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("category");
                    if (data != null) {
                        category.setText(data);
                        category.setTextColor(Color.parseColor("#333333"));
                    }
                }
                break;

            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        finalLocation.setText(data);
                        finalLocation.setTextColor(Color.parseColor("#333333"));

                        finalLocations = (String)finalLocation.getText();
                        List<Address> list = null;

                        try {
                            list = geocoder.getFromLocationName(finalLocations, 1); // 지역 이름, 읽을 개수
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.e("GeoCoder", "입출력 오류 - 서버에서 주소 전환 시 에러 발생");
                        }

                        // 지도 화면에 위도 경도 정보 넘기기
                        if (list != null) {
                            if (list.size() == 0) {
                                Toast.makeText(getActivity(), "해당되는 주소 정보가 없습니다.", Toast.LENGTH_SHORT).show();
                            } else {
                                latitude = list.get(0).getLatitude();
                                longitude = list.get(0).getLongitude();

                                Log.e("FinalLocation", latitude + ", " + longitude);
                            }
                        }
                    }
                }
                break;

            case GET_GALLERY_IMAGE:
                pathList.clear();
                imagesFromServer.clear();
                if(intent == null) {   // 어떤 이미지도 선택하지 않은 경우
                    Toast.makeText(getContext(), "이미지를 선택하지 않았습니다.", Toast.LENGTH_LONG).show();
                    imagesFromServer = null;
                }
                else{      // 이미지를 여러장 선택한 경우
                    ClipData clipData = intent.getClipData();
                    Log.e("clipData", String.valueOf(clipData.getItemCount()));

                    if(clipData.getItemCount() > 5){   // 선택한 이미지가 5장 이상인 경우
                        Toast.makeText(getContext(), "사진은 5장까지 선택 가능합니다.", Toast.LENGTH_LONG).show();
                    } else{   // 선택한 이미지가 1장 이상 5장 이하인 경우
                        for (int i = 0; i < clipData.getItemCount(); i++){
                            Uri selectedImageUri = clipData.getItemAt(i).getUri();  // 선택한 이미지들의 uri를 가져온다.
                            try {
                                selectedImagePath = uri2path(getContext(), selectedImageUri);

                                pathList.add(selectedImagePath);  //uri를 list에 담는다.
                            } catch (Exception e) {
                            }
                        }

                        Log.e("pathList", pathList.toString());

                        for (int j = 0; j < pathList.size(); j++) {

                            File file = new File(pathList.get(j));
                            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                            MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                            retrofitAPI.uploadPostFile(accessToken, body).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                            boolean success = jsonObject.getBoolean("success");

                                            if (success == true) {
                                                imageFromServer = jsonObject.getString("data");

                                                imagesFromServer.add(imageFromServer); // 서버로부터 파일 받아오기(리스트에 저장)
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Log.e("data", "?!");
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {
                                    Log.e("data", t.getMessage());
                                }
                            });
                        }
                    }
                }
                mHandler.postDelayed(mMyTask, 2000); // 1초후에 실행
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

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private Runnable mMyTask = new Runnable() {
        @Override
        public void run() {
            pictureLayout.removeAllViews();
            Log.e("runnable", imagesFromServer.toString());
            for (int i = 0; i < imagesFromServer.size(); i++) {
                ImageView imageView = new ImageView(getActivity().getApplicationContext());
                ViewGroup.LayoutParams image= new LinearLayout.LayoutParams(216, 216);
                imageView.setLayoutParams(image);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        216,
                        216);
                lp.setMargins(0,0,24,0);
                imageView.setLayoutParams(lp);

                if (imagesFromServer.get(i).equals("null")) {
                    imageView.setBackgroundColor(Color.parseColor("#BDBDBD"));
                } else {
                    Glide.with(getActivity()).load(pathList.get(i)).into(imageView);
                }

                pictureLayout.addView(imageView);
            }

        }
    };
}