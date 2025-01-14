package com.example.withme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.withme.bulletin.BottomSheetDialogBoard;
import com.example.withme.bulletin.MainBulletin;
import com.example.withme.bulletin.WriteBulletin;
import com.example.withme.bulletin.MyBulletin;
import com.example.withme.bulletin.BulletinDetail;
import com.example.withme.group.BottomSheetDialogMain;
import com.example.withme.group.BottomSheetDialogProtection;
import com.example.withme.group.GroupActivity1;
import com.example.withme.intro.DescriptionActivity;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.settings.Settings;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kyleduo.switchbutton.SwitchButton;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.CircleOverlay;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.PolygonOverlay;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private static final String[] PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    };

    private String accessToken;
    private double latitude, longitude;
    private double latitude_protection, longitude_protection;
    private int disconnected, type;
    private int nChecked = 0;
    private JSONObject coordinate1, coordinate2, coordinate3, coordinate4;
    private ArrayList<String> protectionPersonName = new ArrayList<>();
    private ArrayList<String> protectionPersonName_sec = new ArrayList<>();
    private ArrayList<String> protectionPersonUid = new ArrayList<>();
    private ArrayList<String> protectionPersonUid_sec = new ArrayList<>();
    private ArrayList<Integer> protectionPersonDisconnected_sec = new ArrayList<>();

    private ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
    private ArrayList<PolygonOverlay> polygons = new ArrayList<>();
    private JSONArray safeZoneCoord;

    private ConstraintLayout coachMark, navigationView;
    private ImageView protectionSettings;
    private LinearLayout protectionPersonLayout;
    private NaverMap naverMap;
    private MapView mapView;
    private TextView tv_settings, tv_bulletin;
    private Marker marker = new Marker();
    private PolygonOverlay polygon = new PolygonOverlay();
    private CircleOverlay circleOverlay = new CircleOverlay();
    private CircleImageView circleImageView;
    private Button groupButton;
    private Dialog dialog;
    private Handler mHandler;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationSource fusedLocationSource;
    private ImageButton bulletinBoard, group, makeGroup2, settings, refresh
            , prediction_realtime, prediction_location, often_visited;

    MainBulletin mainBulletin;
    WriteBulletin bulletin3;
    MyBulletin bulletin5;
    BulletinDetail bulletin6;
    BottomSheetDialogBoard bottomSheetDialogBoard;
    Settings settingsFragment;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    Retrofit retrofit2 = new retrofit2.Retrofit.Builder()
            .baseUrl("http://3.38.11.108:8080")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI2 = retrofit2.create(RetrofitAPI.class);

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,5000);

        startLocationService();

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.e("FCM", "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.e("FCM", token);
                    }
                });

        Intent intent = new Intent(this, GroupActivity1.class);
        Intent intent1 = new Intent(this, DescriptionActivity.class);

        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("AccessToken", accessToken);

        prediction_location = findViewById(R.id.prediction_location);
        prediction_realtime = findViewById(R.id.prediction_realtime);
        often_visited = findViewById(R.id.often_visited);
        protectionSettings = findViewById(R.id.protectionSettings);

        tv_bulletin = findViewById(R.id.tv_bulletin);
        tv_settings = findViewById(R.id.tv_settings);

        coachMark = (ConstraintLayout) findViewById(R.id.coach_mark_master_view);
        navigationView = (ConstraintLayout) findViewById(R.id.navigationView);
        protectionPersonLayout = (LinearLayout) findViewById(R.id.protectionPersonLayout);

        settings = (ImageButton) findViewById(R.id.settings);
        makeGroup2 = (ImageButton) findViewById(R.id.makeGroup_2);
        bulletinBoard = (ImageButton) findViewById(R.id.bulletinBoard);
        group = (ImageButton) findViewById(R.id.group);

        refresh = (ImageButton) findViewById(R.id.refresh);

        mainBulletin = new MainBulletin();
        bulletin3 = new WriteBulletin();
        bulletin5 = new MyBulletin();
        bulletin6 = new BulletinDetail();
        bottomSheetDialogBoard = new BottomSheetDialogBoard();
        settingsFragment = new Settings();

        // 네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        fusedLocationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        retrofitAPI.getAllParty(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            JSONArray data = jsonObject.getJSONArray("data");
                            if (data.length() > 0) {
                                coachMark.setVisibility(View.GONE);
                                makeGroup2.setVisibility(View.GONE);
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

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 2) {
                    BottomSheetDialogProtection bottomSheetDialogProtection = new BottomSheetDialogProtection();
                    bottomSheetDialogProtection.show(getSupportFragmentManager(), "bottomSheetProtection");
                    bottomSheetDialogProtection.setArguments(bundle);
                } else {
                    BottomSheetDialogMain bottomSheetDialogMain = new BottomSheetDialogMain();
                    bottomSheetDialogMain.show(getSupportFragmentManager(), "bottomSheet");
                    bottomSheetDialogMain.setArguments(bundle);
                }
            }
        });

        bulletinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, mainBulletin)
                        .commit();
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, settingsFragment)
                        .commit();
            }
        });

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reload = new Intent(MainActivity.this, MainActivity.class);
                startActivity(reload);
            }
        });

        coachMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coachMark.setVisibility(View.GONE);
            }
        });

        makeGroup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                startActivity(intent);
            }
        });
    }

    public void stopLocationService() {
        if (isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this, "Location service stopped", Toast.LENGTH_SHORT).show();
        }
    }

    private void startLocationService() {
        if(!isLocationServiceRunning()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            } else {
                startService(intent);
            }
        }
    }

    private boolean isLocationServiceRunning() {
        ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null) {
            for (ActivityManager.RunningServiceInfo service:
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if(LocationService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground) {
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (fusedLocationSource.onRequestPermissionsResult(
                requestCode, permissions, grantResults)) {
            if (!fusedLocationSource.isActivated()) {

            }
            naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            return;
        }
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            }else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onFragmentChange(int index){
        if(index == 0) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, mainBulletin).commit();
            transaction.addToBackStack(null);
        }
        if (index == 3) {
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, bulletin3).commit();
            transaction.addToBackStack(null);
        }
        if (index == 5) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, bulletin5).commit();
            transaction.addToBackStack(null);
        }
        if (index == 6) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, bulletin6).commit();
            transaction.addToBackStack(null);
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(true);
        uiSettings.setRotateGesturesEnabled(false);

        this.naverMap = naverMap;
        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);
        this.mHandler = new Handler();

        this.mHandler.postDelayed(m_Runnable,5000);

        (new Thread(new Runnable() {
            @Override
            public void run() {
                while   (!Thread.interrupted()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = getIntent();
                            int out = intent.getIntExtra("out", 3);
                            Log.e("out", String.valueOf(out));
                        }
                    });
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        })).start();

        retrofitAPI.getProfile(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    JSONObject profile = null;
                    try {
                        profile = new JSONObject(response.body().string());
                        boolean success = profile.getBoolean("success");

                        if (success == true) {
                            JSONObject data = profile.getJSONObject("data");
                            type = data.getInt("type");
                            Log.e("User Type", String.valueOf(type));

                            if (type == 2) {
                                naverMap.setLocationSource(fusedLocationSource);
                                ActivityCompat.requestPermissions(MainActivity.this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

                                protectionSettings.setVisibility(View.VISIBLE);
                                bulletinBoard.setVisibility(View.GONE);
                                settings.setVisibility(View.GONE);
                                tv_bulletin.setVisibility(View.GONE);
                                tv_settings.setVisibility(View.GONE);
                            } else { // 0 : 이메일, 1 : 카카오, 2 : 피보호자
                                Intent getIntent = getIntent();

                                protectionPersonName = (ArrayList<String>) getIntent.getSerializableExtra("protectionPersonName");
                                protectionPersonUid = (ArrayList<String>) getIntent.getSerializableExtra("protectionPersonUid");

                                if (protectionPersonName == null) {
                                    retrofitAPI.getAllprotection(accessToken).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                    boolean success = jsonObject.getBoolean("success");

                                                    if (success == true) {
                                                        JSONArray data = jsonObject.getJSONArray("data");
                                                        Log.e("getAllProtection(null)", data.toString());

                                                        for (int i = 0; i < data.length(); i++) {
                                                            JSONObject protectionPerson = data.getJSONObject(i);
                                                            disconnected = protectionPerson.getInt("isDisconnected");
                                                            String name = protectionPerson.getString("name");
                                                            String uid = protectionPerson.getString("uid");
                                                            protectionPersonDisconnected_sec.add(disconnected);
                                                            protectionPersonName_sec.add(name);
                                                            protectionPersonUid_sec.add(uid);

                                                            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
                                                            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                            relativeLayout.setLayoutParams(relativeLayoutParams);
                                                            relativeLayoutParams.setMargins(51, 0, 0, 0);
                                                            relativeLayout.setId(i);
                                                            relativeLayout.setGravity(Gravity.CENTER_VERTICAL);

                                                            CircleImageView circleImageView = new CircleImageView(getApplicationContext());
                                                            RelativeLayout.LayoutParams circle = new RelativeLayout.LayoutParams(144, 144);
                                                            circleImageView.setId(i);
                                                            circleImageView.setLayoutParams(circle);
                                                            circleImageView.setClickable(true);
                                                            if (disconnected == 0) { // 연결됐을 때
                                                                circleImageView.setBackgroundResource(R.drawable.oval_shape_green);
                                                            } else if (disconnected == 1) { // 실종 상태
                                                                circleImageView.setBackgroundResource(R.drawable.oval_shape_red);
                                                            }

                                                            TextView textView = new TextView(getApplicationContext());
                                                            RelativeLayout.LayoutParams text = new RelativeLayout.LayoutParams(
                                                                    144,
                                                                    144
                                                            );
                                                            textView.setGravity(Gravity.CENTER);
                                                            textView.setPadding(20, 0, 20, 0);
                                                            textView.setText(name);
                                                            textView.setTextSize(12);
                                                            textView.setMaxLines(1);
                                                            textView.setEllipsize(TextUtils.TruncateAt.END);
                                                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                                                            textView.setLayoutParams(text);

                                                            protectionPersonLayout.addView(relativeLayout);
                                                            relativeLayout.addView(circleImageView);
                                                            relativeLayout.addView(textView);

                                                            circleImageView.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    circleOverlay.setMap(null);
                                                                    for (int i = 0; i < polygons.size(); i++) {
                                                                        PolygonOverlay a = polygons.get(i);
                                                                        a.setMap(null);
                                                                    }

                                                                    prediction_location.setVisibility(View.VISIBLE);
                                                                    prediction_realtime.setVisibility(View.VISIBLE);
                                                                    often_visited.setVisibility(View.VISIBLE);

                                                                    Log.e("disconnected", protectionPersonDisconnected_sec.toString());
                                                                    if (protectionPersonDisconnected_sec.get(circleImageView.getId()) == 1) {
                                                                        Log.e("disconnected", "disconnected");
                                                                        retrofitAPI2.findVisitOftenAndDistanceAfterMissing(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                            @Override
                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                if (response.isSuccessful()) {
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                        if (success == true) {
                                                                                            JSONObject data = jsonObject.getJSONObject("data");
                                                                                            Log.e("visitOften", data.toString());
                                                                                            JSONArray visitOften = data.getJSONArray("visitOften");
                                                                                            double distance = data.getDouble("distance");

                                                                                            for (int i = 0; i < polygons.size(); i++) {
                                                                                                polygons.get(i).setMap(null);
                                                                                                polygons.clear();
                                                                                            }

                                                                                            prediction_location.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    circleOverlay.setCenter(new LatLng(latitude_protection, longitude_protection));
                                                                                                    circleOverlay.setRadius(distance);
                                                                                                    circleOverlay.setOutlineWidth(12);
                                                                                                    circleOverlay.setColor(Color.parseColor("#1AFF302B"));
                                                                                                    circleOverlay.setOutlineColor(Color.parseColor("#FF302B"));
                                                                                                    circleOverlay.setMap(naverMap);

                                                                                                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude_protection, longitude_protection))
                                                                                                            .animate(CameraAnimation.Fly, 2000);
                                                                                                    naverMap.moveCamera(cameraUpdate);
                                                                                                }
                                                                                            });

                                                                                            often_visited.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {

                                                                                                    circleOverlay.setMap(null);
                                                                                                    for (int i = 0; i < polygons.size(); i++) {
                                                                                                        polygons.get(i).setMap(null);
                                                                                                    }

                                                                                                    double lat, lon;

                                                                                                    try {
                                                                                                        JSONObject coordinate = (JSONObject) visitOften.get(0);
                                                                                                        lat = coordinate.getDouble("latitude");
                                                                                                        lon = coordinate.getDouble("longitude");

                                                                                                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lon))
                                                                                                                .animate(CameraAnimation.Fly, 2000);
                                                                                                        naverMap.moveCamera(cameraUpdate);
                                                                                                    } catch (JSONException e) {
                                                                                                        e.printStackTrace();
                                                                                                    }

                                                                                                    for (int i = 0; i < (visitOften.length() / 4); i++) {
                                                                                                        PolygonOverlay polygon = new PolygonOverlay();

                                                                                                        try {
                                                                                                            coordinate1 = (JSONObject) visitOften.get(i * 4);
                                                                                                            double lat1 = coordinate1.getDouble("latitude");
                                                                                                            double long1 = coordinate1.getDouble("longitude");
                                                                                                            int grade = coordinate1.getInt("grade");

                                                                                                            coordinate2 = (JSONObject) visitOften.get(i * 4 + 1);
                                                                                                            double lat2 = coordinate2.getDouble("latitude");
                                                                                                            double long2 = coordinate2.getDouble("longitude");

                                                                                                            coordinate3 = (JSONObject) visitOften.get(i * 4 + 2);
                                                                                                            double lat3 = coordinate3.getDouble("latitude");
                                                                                                            double long3 = coordinate3.getDouble("longitude");

                                                                                                            coordinate4 = (JSONObject) visitOften.get(i * 4 + 3);
                                                                                                            double lat4 = coordinate4.getDouble("latitude");
                                                                                                            double long4 = coordinate4.getDouble("longitude");

                                                                                                            polygon.setCoords(Arrays.asList(
                                                                                                                    new LatLng(lat1, long1),
                                                                                                                    new LatLng(lat2, long2),
                                                                                                                    new LatLng(lat3, long3),
                                                                                                                    new LatLng(lat4, long4)
                                                                                                            ));
                                                                                                            polygon.setOutlineColor(Color.TRANSPARENT);
                                                                                                            if (grade == 1 || grade == 2) {
                                                                                                                polygon.setColor(Color.parseColor("#4D3E791A"));
                                                                                                            } else {
                                                                                                                polygon.setColor(Color.parseColor("#66FF302B"));
                                                                                                            }
                                                                                                            polygon.setMap(naverMap);

                                                                                                            polygons.add(polygon);
                                                                                                        } catch (JSONException e) {
                                                                                                            e.printStackTrace();
                                                                                                        }
                                                                                                    }

                                                                                                }
                                                                                            });

                                                                                            prediction_realtime.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    circleOverlay.setMap(null);

                                                                                                    for (int i = 0; i < polygons.size(); i++) {
                                                                                                        polygons.get(i).setMap(null);
                                                                                                    }
                                                                                                    retrofitAPI2.findPredictionLocation(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                                                        @Override
                                                                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                                            if (response.isSuccessful()) {
                                                                                                                try {
                                                                                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                                                    boolean success = jsonObject.getBoolean("success");

                                                                                                                    if (success == true) {
                                                                                                                        JSONArray data = jsonObject.getJSONArray("data");
                                                                                                                        Log.e("predict", data.toString());

                                                                                                                        double lat, lon;

                                                                                                                        try {
                                                                                                                            JSONObject coordinate = (JSONObject) data.get(0);
                                                                                                                            lat = coordinate.getDouble("latitude");
                                                                                                                            lon = coordinate.getDouble("longitude");

                                                                                                                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lon))
                                                                                                                                    .animate(CameraAnimation.Fly, 2000);
                                                                                                                            naverMap.moveCamera(cameraUpdate);
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                        }

                                                                                                                        for (int i = 0; i < (data.length() / 4); i++) {
                                                                                                                            PolygonOverlay polygon = new PolygonOverlay();

                                                                                                                            try {
                                                                                                                                coordinate1 = (JSONObject) data.get(i * 4);
                                                                                                                                double lat1 = coordinate1.getDouble("latitude");
                                                                                                                                double long1 = coordinate1.getDouble("longitude");

                                                                                                                                coordinate2 = (JSONObject) data.get(i * 4 + 1);
                                                                                                                                double lat2 = coordinate2.getDouble("latitude");
                                                                                                                                double long2 = coordinate2.getDouble("longitude");

                                                                                                                                coordinate3 = (JSONObject) data.get(i * 4 + 2);
                                                                                                                                double lat3 = coordinate3.getDouble("latitude");
                                                                                                                                double long3 = coordinate3.getDouble("longitude");

                                                                                                                                coordinate4 = (JSONObject) data.get(i * 4 + 3);
                                                                                                                                double lat4 = coordinate4.getDouble("latitude");
                                                                                                                                double long4 = coordinate4.getDouble("longitude");

                                                                                                                                polygon.setCoords(Arrays.asList(
                                                                                                                                        new LatLng(lat1, long1),
                                                                                                                                        new LatLng(lat2, long2),
                                                                                                                                        new LatLng(lat3, long3),
                                                                                                                                        new LatLng(lat4, long4)
                                                                                                                                ));
                                                                                                                                polygon.setOutlineColor(Color.TRANSPARENT);
                                                                                                                                polygon.setColor(Color.parseColor("#4DF7BD03"));

                                                                                                                                polygon.setMap(naverMap);

                                                                                                                                polygons.add(polygon);
                                                                                                                            } catch (JSONException e) {
                                                                                                                                e.printStackTrace();
                                                                                                                            }
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
                                                                    } else {
                                                                        retrofitAPI2.findSafeZone(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                            @Override
                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                if (response.isSuccessful()) {
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                        if (success == true) {
                                                                                            for (int i = 0; i< polygons.size(); i++) {
                                                                                                polygons.get(i).setMap(null);
                                                                                            }
                                                                                            polygons.clear();

                                                                                            safeZoneCoord = jsonObject.getJSONArray("data");
                                                                                            for (int i = 0; i < (safeZoneCoord.length() / 4); i++) {

                                                                                                coordinate1 = (JSONObject) safeZoneCoord.get(i * 4);
                                                                                                double lat1 = coordinate1.getDouble("latitude");
                                                                                                double long1 = coordinate1.getDouble("longitude");

                                                                                                coordinate2 = (JSONObject) safeZoneCoord.get(i * 4 + 1);
                                                                                                double lat2 = coordinate2.getDouble("latitude");
                                                                                                double long2 = coordinate2.getDouble("longitude");

                                                                                                coordinate3 = (JSONObject) safeZoneCoord.get(i * 4 + 2);
                                                                                                double lat3 = coordinate3.getDouble("latitude");
                                                                                                double long3 = coordinate3.getDouble("longitude");

                                                                                                coordinate4 = (JSONObject) safeZoneCoord.get(i * 4 + 3);
                                                                                                double lat4 = coordinate4.getDouble("latitude");
                                                                                                double long4 = coordinate4.getDouble("longitude");
                                                                                                PolygonOverlay polygon_sa = new PolygonOverlay();
                                                                                                polygon_sa.setCoords(Arrays.asList(
                                                                                                        new LatLng(lat1, long1),
                                                                                                        new LatLng(lat2, long2),
                                                                                                        new LatLng(lat3, long3),
                                                                                                        new LatLng(lat4, long4)
                                                                                                ));
                                                                                                polygon_sa.setOutlineColor(Color.TRANSPARENT);
                                                                                                polygon_sa.setColor(Color.parseColor("#803E791A"));
                                                                                                polygon_sa.setMap(naverMap);

                                                                                                polygons.add(polygon_sa);
                                                                                            }
                                                                                        } else {
                                                                                            Log.e("findSafeZone", "false");
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
                                                                                Log.e("findSafeZone", "false");
                                                                            }
                                                                        });

                                                                    }
                                                                    (new Thread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            while (!Thread.interrupted()) {
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        marker.setMap(null);

                                                                                        retrofitAPI2.getUserCurrentLocation(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                                if (response.isSuccessful()) {
                                                                                                    try {
                                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                                        if (success == true) {
                                                                                                            JSONObject data = jsonObject.getJSONObject("data");
                                                                                                            latitude_protection = data.getDouble("latitude");
                                                                                                            longitude_protection = data.getDouble("longitude");

                                                                                                            marker.setPosition(new LatLng(latitude_protection, longitude_protection));
                                                                                                            marker.setMap(naverMap);

                                                                                                        } else {
                                                                                                            Log.e("location", "success false");
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
                                                                                                Log.e("location", "전송 실패");
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                                try {
                                                                                    Thread.sleep(5000);
                                                                                } catch (InterruptedException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }
                                                                    })).start();
                                                                    if (latitude_protection == 0.0 && longitude_protection == 0.0) {
                                                                        Toast.makeText(MainActivity.this, "다시 한번 눌러주세요!", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude_protection, longitude_protection))
                                                                                .animate(CameraAnimation.Fly, 2000);
                                                                        naverMap.moveCamera(cameraUpdate);
                                                                    }
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
                                } else {
                                    retrofitAPI.getAllprotection(accessToken).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                    boolean success = jsonObject.getBoolean("success");

                                                    if (success == true) {
                                                        JSONArray data = jsonObject.getJSONArray("data");
                                                        Log.e("data",data.toString());
                                                        for (int i = 0; i < data.length(); i++) {
                                                            JSONObject protectionPerson = data.getJSONObject(i);
                                                            disconnected = protectionPerson.getInt("isDisconnected");
                                                            String name = protectionPerson.getString("name");
                                                            String uid = protectionPerson.getString("uid");
                                                            protectionPersonDisconnected_sec.add(disconnected);
                                                            protectionPersonName_sec.add(name);
                                                            protectionPersonUid_sec.add(uid);

                                                            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
                                                            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                            relativeLayout.setLayoutParams(relativeLayoutParams);
                                                            relativeLayoutParams.setMargins(51, 0, 0, 0);
                                                            relativeLayout.setId(i);
                                                            relativeLayout.setGravity(Gravity.CENTER_VERTICAL);

                                                            CircleImageView circleImageView = new CircleImageView(getApplicationContext());
                                                            RelativeLayout.LayoutParams circle = new RelativeLayout.LayoutParams(144, 144);
                                                            circleImageView.setId(i);
                                                            circleImageView.setLayoutParams(circle);
                                                            circleImageView.setClickable(true);
                                                            if (disconnected == 0) { // 연결됐을 때
                                                                circleImageView.setBackgroundResource(R.drawable.oval_shape_green);
                                                            } else if (disconnected == 1) { // 실종 상태
                                                                circleImageView.setBackgroundResource(R.drawable.oval_shape_red);
                                                            }

                                                            TextView textView = new TextView(getApplicationContext());
                                                            RelativeLayout.LayoutParams text = new RelativeLayout.LayoutParams(
                                                                    144,
                                                                    144
                                                            );
                                                            textView.setGravity(Gravity.CENTER);
                                                            textView.setPadding(20, 0, 20, 0);
                                                            textView.setText(name);
                                                            textView.setTextSize(12);
                                                            textView.setMaxLines(1);
                                                            textView.setEllipsize(TextUtils.TruncateAt.END);
                                                            textView.setTextColor(Color.parseColor("#FFFFFF"));
                                                            textView.setLayoutParams(text);

                                                            protectionPersonLayout.addView(relativeLayout);
                                                            relativeLayout.addView(circleImageView);
                                                            relativeLayout.addView(textView);

                                                            circleImageView.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    circleOverlay.setMap(null);
                                                                    for (int i = 0; i < polygons.size(); i++) {
                                                                        PolygonOverlay a = polygons.get(i);
                                                                        a.setMap(null);
                                                                    }
                                                                    polygons.clear();

                                                                    prediction_location.setVisibility(View.VISIBLE);
                                                                    prediction_realtime.setVisibility(View.VISIBLE);
                                                                    often_visited.setVisibility(View.VISIBLE);

                                                                    Log.e("disconnected", protectionPersonDisconnected_sec.toString());
                                                                    if (protectionPersonDisconnected_sec.get(circleImageView.getId()) == 1) {
                                                                        Log.e("disconnected", "disconnected");

                                                                        retrofitAPI2.findVisitOftenAndDistanceAfterMissing(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                            @Override
                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                if (response.isSuccessful()) {
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                        if (success == true) {
                                                                                            JSONObject data = jsonObject.getJSONObject("data");
                                                                                            JSONArray visitOften = data.getJSONArray("visitOften");
                                                                                            double distance = data.getDouble("distance");

                                                                                            for (int i = 0; i < polygons.size(); i++) {
                                                                                                polygons.get(i).setMap(null);
                                                                                                polygons.clear();
                                                                                            }

                                                                                            circleOverlay.setCenter(new LatLng(latitude_protection, longitude_protection));
                                                                                            circleOverlay.setRadius(distance);
                                                                                            circleOverlay.setOutlineWidth(12);
                                                                                            circleOverlay.setColor(Color.parseColor("#1AFF302B"));
                                                                                            circleOverlay.setOutlineColor(Color.parseColor("#FF302B"));
                                                                                            circleOverlay.setMap(naverMap);

                                                                                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude_protection, longitude_protection))
                                                                                                    .animate(CameraAnimation.Fly, 2000);
                                                                                            naverMap.moveCamera(cameraUpdate);

                                                                                            prediction_location.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {

                                                                                                    circleOverlay.setCenter(new LatLng(latitude_protection, longitude_protection));
                                                                                                    circleOverlay.setRadius(distance);
                                                                                                    circleOverlay.setOutlineWidth(12);
                                                                                                    circleOverlay.setColor(Color.parseColor("#1AFF302B"));
                                                                                                    circleOverlay.setOutlineColor(Color.parseColor("#FF302B"));
                                                                                                    circleOverlay.setMap(naverMap);

                                                                                                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude_protection, longitude_protection))
                                                                                                            .animate(CameraAnimation.Fly, 2000);
                                                                                                    naverMap.moveCamera(cameraUpdate);
                                                                                                }
                                                                                            });

                                                                                            often_visited.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    circleOverlay.setMap(null);

                                                                                                    for (int i = 0; i < polygons.size(); i++) {
                                                                                                        polygons.get(i).setMap(null);
                                                                                                    }

                                                                                                    double lat, lon;

                                                                                                    try {
                                                                                                        JSONObject coordinate = (JSONObject) visitOften.get(0);
                                                                                                        lat = coordinate.getDouble("latitude");
                                                                                                        lon = coordinate.getDouble("longitude");

                                                                                                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lon))
                                                                                                                .animate(CameraAnimation.Fly, 2000);
                                                                                                        naverMap.moveCamera(cameraUpdate);
                                                                                                    } catch (JSONException e) {
                                                                                                        e.printStackTrace();
                                                                                                    }

                                                                                                    for (int i = 0; i < (visitOften.length() / 4); i++) {
                                                                                                        PolygonOverlay polygon = new PolygonOverlay();

                                                                                                        try {
                                                                                                            coordinate1 = (JSONObject) visitOften.get(i * 4);
                                                                                                            double lat1 = coordinate1.getDouble("latitude");
                                                                                                            double long1 = coordinate1.getDouble("longitude");
                                                                                                            int grade = coordinate1.getInt("grade");

                                                                                                            coordinate2 = (JSONObject) visitOften.get(i * 4 + 1);
                                                                                                            double lat2 = coordinate2.getDouble("latitude");
                                                                                                            double long2 = coordinate2.getDouble("longitude");

                                                                                                            coordinate3 = (JSONObject) visitOften.get(i * 4 + 2);
                                                                                                            double lat3 = coordinate3.getDouble("latitude");
                                                                                                            double long3 = coordinate3.getDouble("longitude");

                                                                                                            coordinate4 = (JSONObject) visitOften.get(i * 4 + 3);
                                                                                                            double lat4 = coordinate4.getDouble("latitude");
                                                                                                            double long4 = coordinate4.getDouble("longitude");

                                                                                                            polygon.setCoords(Arrays.asList(
                                                                                                                    new LatLng(lat1, long1),
                                                                                                                    new LatLng(lat2, long2),
                                                                                                                    new LatLng(lat3, long3),
                                                                                                                    new LatLng(lat4, long4)
                                                                                                            ));
                                                                                                            polygon.setOutlineColor(Color.TRANSPARENT);
                                                                                                            if (grade == 1 || grade == 2) {
                                                                                                                polygon.setColor(Color.parseColor("#4D3E791A"));
                                                                                                            } else {
                                                                                                                polygon.setColor(Color.parseColor("#66FF302B"));
                                                                                                            }
                                                                                                            polygon.setMap(naverMap);

                                                                                                            polygons.add(polygon);
                                                                                                        } catch (JSONException e) {
                                                                                                            e.printStackTrace();
                                                                                                        }
                                                                                                    }
                                                                                                }
                                                                                            });

                                                                                            prediction_realtime.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View v) {
                                                                                                    circleOverlay.setMap(null);

                                                                                                    for (int i = 0; i < polygons.size(); i++) {
                                                                                                        polygons.get(i).setMap(null);
                                                                                                    }
                                                                                                    retrofitAPI2.findPredictionLocation(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                                                        @Override
                                                                                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                                            if (response.isSuccessful()) {
                                                                                                                try {
                                                                                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                                                    boolean success = jsonObject.getBoolean("success");

                                                                                                                    if (success == true) {
                                                                                                                        JSONArray data = jsonObject.getJSONArray("data");
                                                                                                                        Log.e("predict", data.toString());

                                                                                                                        double lat, lon;

                                                                                                                        try {
                                                                                                                            JSONObject coordinate = (JSONObject) data.get(0);
                                                                                                                            lat = coordinate.getDouble("latitude");
                                                                                                                            lon = coordinate.getDouble("longitude");

                                                                                                                            CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(lat, lon))
                                                                                                                                    .animate(CameraAnimation.Fly, 2000);
                                                                                                                            naverMap.moveCamera(cameraUpdate);
                                                                                                                        } catch (JSONException e) {
                                                                                                                            e.printStackTrace();
                                                                                                                        }

                                                                                                                        for (int i = 0; i < (data.length() / 4); i++) {
                                                                                                                            PolygonOverlay polygon = new PolygonOverlay();

                                                                                                                            try {
                                                                                                                                coordinate1 = (JSONObject) data.get(i * 4);
                                                                                                                                double lat1 = coordinate1.getDouble("latitude");
                                                                                                                                double long1 = coordinate1.getDouble("longitude");

                                                                                                                                coordinate2 = (JSONObject) data.get(i * 4 + 1);
                                                                                                                                double lat2 = coordinate2.getDouble("latitude");
                                                                                                                                double long2 = coordinate2.getDouble("longitude");

                                                                                                                                coordinate3 = (JSONObject) data.get(i * 4 + 2);
                                                                                                                                double lat3 = coordinate3.getDouble("latitude");
                                                                                                                                double long3 = coordinate3.getDouble("longitude");

                                                                                                                                coordinate4 = (JSONObject) data.get(i * 4 + 3);
                                                                                                                                double lat4 = coordinate4.getDouble("latitude");
                                                                                                                                double long4 = coordinate4.getDouble("longitude");

                                                                                                                                polygon.setCoords(Arrays.asList(
                                                                                                                                        new LatLng(lat1, long1),
                                                                                                                                        new LatLng(lat2, long2),
                                                                                                                                        new LatLng(lat3, long3),
                                                                                                                                        new LatLng(lat4, long4)
                                                                                                                                ));
                                                                                                                                polygon.setOutlineColor(Color.TRANSPARENT);
                                                                                                                                polygon.setColor(Color.parseColor("#4DF7BD03"));

                                                                                                                                polygon.setMap(naverMap);

                                                                                                                                polygons.add(polygon);
                                                                                                                            } catch (JSONException e) {
                                                                                                                                e.printStackTrace();
                                                                                                                            }
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
                                                                    } else {
                                                                        retrofitAPI2.findSafeZone(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                            @Override
                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                if (response.isSuccessful()) {
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                        if (success == true) {

                                                                                            safeZoneCoord = jsonObject.getJSONArray("data");
                                                                                            for (int i = 0; i < (safeZoneCoord.length() / 4); i++) {

                                                                                                coordinate1 = (JSONObject) safeZoneCoord.get(i * 4);
                                                                                                double lat1 = coordinate1.getDouble("latitude");
                                                                                                double long1 = coordinate1.getDouble("longitude");

                                                                                                coordinate2 = (JSONObject) safeZoneCoord.get(i * 4 + 1);
                                                                                                double lat2 = coordinate2.getDouble("latitude");
                                                                                                double long2 = coordinate2.getDouble("longitude");

                                                                                                coordinate3 = (JSONObject) safeZoneCoord.get(i * 4 + 2);
                                                                                                double lat3 = coordinate3.getDouble("latitude");
                                                                                                double long3 = coordinate3.getDouble("longitude");

                                                                                                coordinate4 = (JSONObject) safeZoneCoord.get(i * 4 + 3);
                                                                                                double lat4 = coordinate4.getDouble("latitude");
                                                                                                double long4 = coordinate4.getDouble("longitude");

                                                                                                PolygonOverlay polygon_sa = new PolygonOverlay();
                                                                                                polygon_sa.setCoords(Arrays.asList(
                                                                                                        new LatLng(lat1, long1),
                                                                                                        new LatLng(lat2, long2),
                                                                                                        new LatLng(lat3, long3),
                                                                                                        new LatLng(lat4, long4)
                                                                                                ));
                                                                                                polygon_sa.setOutlineColor(Color.TRANSPARENT);
                                                                                                polygon_sa.setColor(Color.parseColor("#803E791A"));
                                                                                                polygon_sa.setMap(naverMap);

                                                                                                polygons.add(polygon);
                                                                                            }
                                                                                        } else {
                                                                                            Log.e("findSafeZone", "false");
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
                                                                                Log.e("findSafeZone", "false");
                                                                            }
                                                                        });

                                                                    }
                                                                    (new Thread(new Runnable() {
                                                                        @Override
                                                                        public void run() {
                                                                            while (!Thread.interrupted()) {
                                                                                runOnUiThread(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        marker.setMap(null);

                                                                                        retrofitAPI2.getUserCurrentLocation(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                                            @Override
                                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                                if (response.isSuccessful()) {
                                                                                                    try {
                                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                                        if (success == true) {
                                                                                                            JSONObject data = jsonObject.getJSONObject("data");
                                                                                                            latitude_protection = data.getDouble("latitude");
                                                                                                            longitude_protection = data.getDouble("longitude");

//                                                                                                            Log.e("location", latitude_protection + ", " + longitude_protection);

                                                                                                            marker.setPosition(new LatLng(latitude_protection, longitude_protection));
                                                                                                            marker.setMap(naverMap);

                                                                                                        } else {
                                                                                                            Log.e("location", "success false");
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
                                                                                                Log.e("location", "전송 실패");
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                });
                                                                                try {
                                                                                    Thread.sleep(5000);
                                                                                } catch (InterruptedException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        }
                                                                    })).start();
                                                                    if (latitude_protection == 0.0 && longitude_protection == 0.0) {
                                                                        Toast.makeText(MainActivity.this, "다시 한번 눌러주세요!", Toast.LENGTH_SHORT).show();
                                                                    } else {
                                                                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude_protection, longitude_protection))
                                                                                .animate(CameraAnimation.Fly, 2000);
                                                                        naverMap.moveCamera(cameraUpdate);
                                                                    }
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
        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);

        protectionSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(MainActivity.this);

                openDialog();
            }
        });
    }

    private void openDialog() {
        dialog.setContentView(R.layout.protection_setting_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setGravity(Gravity.TOP);

        ImageButton xButton = dialog.findViewById(R.id.xButton);
        TextView logout = dialog.findViewById(R.id.logout);
        SwitchButton ansim = dialog.findViewById(R.id.ansim);

        Intent intent = getIntent();

        String protectionId = intent.getStringExtra("uid");

        retrofitAPI.getProtectionDetail(accessToken, protectionId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            JSONObject data = jsonObject.getJSONObject("data");

                            Log.e("data", data.toString());
                            int safeMove = data.getInt("safeMove");

                            ansim.setChecked(safeMove != 0);
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

        ansim.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    nChecked = 0;
                    HashMap<String, Object> input = new HashMap<>();

                    input.put("uid", protectionId);
                    input.put("safemove", nChecked);
                    retrofitAPI.changeSafeMove(accessToken, input).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success) {
                                        Toast.makeText(MainActivity.this, "안심이동 설정 변경을 완료하였습니다.", Toast.LENGTH_SHORT).show();
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
                } else {
                    nChecked = 1;
                    HashMap<String, Object> input = new HashMap<>();

                    input.put("uid", protectionId);
                    input.put("safemove", nChecked);
                    retrofitAPI.changeSafeMove(accessToken, input).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                    boolean success = jsonObject.getBoolean("success");

                                    if (success == true) {
                                        Toast.makeText(MainActivity.this, "안심이동 설정 변경을 완료하였습니다.", Toast.LENGTH_SHORT).show();
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
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);

                SharedPreferences pref = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.remove("AccessToken");
                editor1.apply();
                stopLocationService();

                startActivity(intent);

            }
        });

        xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()
        {
            MainActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    }; //runnable
}