package com.example.withme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.withme.bulletin.BottomSheetDialogBoard;
import com.example.withme.bulletin.Bulletin1;
import com.example.withme.bulletin.Bulletin2;
import com.example.withme.bulletin.Bulletin3;
import com.example.withme.bulletin.Bulletin4;
import com.example.withme.bulletin.Bulletin5;
import com.example.withme.bulletin.Bulletin6;
import com.example.withme.group.BottomSheetDialogMain;
import com.example.withme.group.GroupActivity1;
import com.example.withme.intro.DescriptionActivity;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
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
    private int disconnected;
    private JSONObject coordinate1, coordinate2, coordinate3, coordinate4;
    private ArrayList<String> protectionPersonName = new ArrayList<>();
    private ArrayList<String> protectionPersonName_sec = new ArrayList<>();
    private ArrayList<String> protectionPersonUid = new ArrayList<>();
    private ArrayList<String> protectionPersonUid_sec = new ArrayList<>();
    private ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
    private ArrayList<PolygonOverlay> polygons = new ArrayList<>();
    private JSONArray safeZoneCoord;

    private ConstraintLayout coachMark;
    private LinearLayout protectionPersonLayout;
    private ImageButton makeGroup1, makeGroup2;
    private NaverMap naverMap;
    private MapView mapView;
    private Marker marker = new Marker();
    private PolygonOverlay polygon = new PolygonOverlay();
    private CircleOverlay circleOverlay = new CircleOverlay();
    private CircleImageView circleImageView;
    private Button logout, groupButton, refresh;
    private Handler mHandler;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationSource fusedLocationSource;
    private ImageButton bulletinBoard, group;

    Bulletin1 bulletin1;
    Bulletin2 bulletin2;
    Bulletin3 bulletin3;
    Bulletin4 bulletin4;
    Bulletin5 bulletin5;
    Bulletin6 bulletin6;
    BottomSheetDialogBoard bottomSheetDialogBoard;

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

        coachMark = (ConstraintLayout) findViewById(R.id.coach_mark_master_view);
        protectionPersonLayout = (LinearLayout) findViewById(R.id.protectionPersonLayout);

        makeGroup1 = (ImageButton) findViewById(R.id.makeGroup_1);
        makeGroup2 = (ImageButton) findViewById(R.id.makeGroup_2);
        bulletinBoard = (ImageButton) findViewById(R.id.bulletinBoard);
        group = (ImageButton) findViewById(R.id.group);

        logout = (Button) findViewById(R.id.logout);
        refresh = (Button) findViewById(R.id.refresh);

        bulletin1 = new Bulletin1();
        bulletin2 = new Bulletin2();
        bulletin3 = new Bulletin3();
        bulletin4 = new Bulletin4();
        bulletin5 = new Bulletin5();
        bulletin6 = new Bulletin6();
        bottomSheetDialogBoard = new BottomSheetDialogBoard();

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
                BottomSheetDialogMain bottomSheetDialogMain = new BottomSheetDialogMain();
                bottomSheetDialogMain.show(getSupportFragmentManager(), "bottomSheet");
                bottomSheetDialogMain.setArguments(bundle);
            }
        });

        bulletinBoard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, bulletin1)
                        .commit();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.remove("AccessToken");
                editor1.commit();
                stopLocationService();
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        startActivity(intent1);
                    }
                });
                startActivity(intent1);
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

    private void stopLocationService() {
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
            transaction.replace(R.id.fragment_container, bulletin1).commit();
            transaction.addToBackStack(null);
        }
        if(index == 2) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, bulletin2).commit();
            transaction.addToBackStack(null);
        }
        if (index == 3) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, bulletin3).commit();
            transaction.addToBackStack(null);
        }
        if (index == 4) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fragment_container, bulletin4).commit();
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
                            int type = data.getInt("type");
                            Log.e("User Type", String.valueOf(type));

                            if (type == 2) {
                                naverMap.setLocationSource(fusedLocationSource);
                                ActivityCompat.requestPermissions(MainActivity.this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);
                            } else { // 0 : 이메일, 1 : 카카오, 2 : 피보호자
                                Intent getIntent = getIntent();

                                protectionPersonName = (ArrayList<String>) getIntent.getSerializableExtra("protectionPersonName");
                                protectionPersonUid = (ArrayList<String>) getIntent.getSerializableExtra("protectionPersonUid");

                                if (protectionPersonName == null) {
                                    Log.e("null", "null");
                                    retrofitAPI.getAllprotection(accessToken).enqueue(new Callback<ResponseBody>() {
                                        @Override
                                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                            if (response.isSuccessful()) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(response.body().string());
                                                    boolean success = jsonObject.getBoolean("success");

                                                    if (success == true) {
                                                        JSONArray data = jsonObject.getJSONArray("data");
                                                        Log.e("getAllProtection", data.toString());

                                                        for (int i = 0; i < data.length(); i++) {
                                                            JSONObject protectionPerson = data.getJSONObject(i);
                                                            disconnected = protectionPerson.getInt("isDisconnected");
                                                            String name = protectionPerson.getString("name");
                                                            String uid = protectionPerson.getString("uid");
                                                            protectionPersonName_sec.add(name);
                                                            protectionPersonUid_sec.add(uid);

                                                            RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
                                                            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                                            relativeLayout.setLayoutParams(relativeLayoutParams);
                                                            relativeLayoutParams.setMargins(51,0,0,0);
                                                            relativeLayout.setId(i);
                                                            relativeLayout.setGravity(Gravity.CENTER_VERTICAL);

                                                            CircleImageView circleImageView = new CircleImageView(getApplicationContext());
                                                            RelativeLayout.LayoutParams circle= new RelativeLayout.LayoutParams(144, 144);
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
                                                            textView.setPadding(20,0,20,0);
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
                                                                    if (disconnected == 1) {
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

                                                                                            for (int i=0; i<polygons.size(); i++) {
                                                                                                polygons.get(i).setMap(null);
                                                                                                polygons.clear();
                                                                                            }

                                                                                            circleOverlay.setCenter(new LatLng(latitude, longitude));
                                                                                            circleOverlay.setRadius(distance);
                                                                                            circleOverlay.setOutlineWidth(12);
                                                                                            circleOverlay.setColor(Color.parseColor("#1AFF302B"));
                                                                                            circleOverlay.setOutlineColor(Color.parseColor("#FF302B"));
                                                                                            circleOverlay.setMap(naverMap);


                                                                                            for (int i=0; i < (visitOften.length() / 4); i++) {
                                                                                                PolygonOverlay polygon = new PolygonOverlay();

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
                                                                        retrofitAPI2.findSafeZone(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                                                                            @Override
                                                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                                                if (response.isSuccessful()) {
                                                                                    try {
                                                                                        JSONObject jsonObject = new JSONObject(response.body().string());
                                                                                        boolean success = jsonObject.getBoolean("success");

                                                                                        if (success == true) {
                                                                                            safeZoneCoord = jsonObject.getJSONArray("data");
                                                                                            for (int i=0; i < (safeZoneCoord.length() / 4); i++) {

                                                                                                coordinate1 = (JSONObject) safeZoneCoord.get(i*4);
                                                                                                double lat1 = coordinate1.getDouble("latitude");
                                                                                                double long1 = coordinate1.getDouble("longitude");

                                                                                                coordinate2 = (JSONObject) safeZoneCoord.get(i*4+1);
                                                                                                double lat2 = coordinate2.getDouble("latitude");
                                                                                                double long2 = coordinate2.getDouble("longitude");

                                                                                                coordinate3 = (JSONObject) safeZoneCoord.get(i*4+2);
                                                                                                double lat3 = coordinate3.getDouble("latitude");
                                                                                                double long3 = coordinate3.getDouble("longitude");

                                                                                                coordinate4 = (JSONObject) safeZoneCoord.get(i*4+3);
                                                                                                double lat4 = coordinate4.getDouble("latitude");
                                                                                                double long4 = coordinate4.getDouble("longitude");

                                                                                                polygon.setCoords(Arrays.asList(
                                                                                                        new LatLng(lat1, long1),
                                                                                                        new LatLng(lat2, long2),
                                                                                                        new LatLng(lat3, long3),
                                                                                                        new LatLng(lat4, long4)
                                                                                                ));
                                                                                                polygon.setOutlineColor(Color.TRANSPARENT);
                                                                                                polygon.setColor(Color.parseColor("#803E791A"));
                                                                                                polygon.setMap(naverMap);

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
                                                                            while   (!Thread.interrupted()) {
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
                                                                                                            latitude = data.getDouble("latitude");
                                                                                                            longitude = data.getDouble("longitude");

                                                                                                            Log.e("location", latitude + ", " + longitude);

                                                                                                            marker.setPosition(new LatLng(latitude, longitude));
                                                                                                            marker.setMap(naverMap);

                                                                                                            CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(latitude, longitude), 12.1)
                                                                                                                    .animate(CameraAnimation.Fly, 2000);
                                                                                                            naverMap.moveCamera(cameraUpdate);

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
                                    Log.e("not null", "not null");
                                    for (int i = 0; i < protectionPersonName.size(); i++) {
                                        String name = protectionPersonName.get(i);
                                        String uid = protectionPersonUid.get(i);

                                        RelativeLayout relativeLayout = new RelativeLayout(getApplicationContext());
                                        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                                        relativeLayout.setLayoutParams(relativeLayoutParams);
                                        relativeLayoutParams.setMargins(51, 0, 0, 0);
                                        relativeLayout.setId(i);
                                        relativeLayout.setGravity(Gravity.CENTER_VERTICAL);

                                        CircleImageView circleImageView = new CircleImageView(getApplicationContext());
                                        RelativeLayout.LayoutParams circle = new RelativeLayout.LayoutParams(144, 144);
                                        circleImageView.setLayoutParams(circle);
                                        circleImageView.setClickable(true);
                                        circleImageView.setId(i);
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
                                                if (disconnected == 1) {
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

                                                                        for (int i=0; i<polygons.size(); i++) {
                                                                            polygons.get(i).setMap(null);
                                                                            polygons.clear();
                                                                        }

                                                                        circleOverlay.setCenter(new LatLng(latitude, longitude));
                                                                        circleOverlay.setRadius(distance);
                                                                        circleOverlay.setOutlineWidth(12);
                                                                        circleOverlay.setColor(Color.parseColor("#1AFF302B"));
                                                                        circleOverlay.setOutlineColor(Color.parseColor("#FF302B"));
                                                                        circleOverlay.setMap(naverMap);


                                                                        for (int i=0; i < (visitOften.length() / 4); i++) {
                                                                            PolygonOverlay polygon = new PolygonOverlay();

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
                                                                            if (grade == 1) {
                                                                                polygon.setColor(Color.parseColor("#4D3E791A"));
                                                                            } else {
                                                                                polygon.setColor(Color.parseColor("#66FF302B"));
                                                                            }
                                                                            polygon.setMap(naverMap);

                                                                            polygons.add(polygon);
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
                                                                            PolygonOverlay polygon = new PolygonOverlay();

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

                                                                            polygon.setCoords(Arrays.asList(
                                                                                    new LatLng(lat1, long1),
                                                                                    new LatLng(lat2, long2),
                                                                                    new LatLng(lat3, long3),
                                                                                    new LatLng(lat4, long4)
                                                                            ));
                                                                            polygon.setOutlineColor(Color.TRANSPARENT);
                                                                            polygon.setColor(Color.parseColor("#803E791A"));
                                                                            polygon.setMap(naverMap);

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
                                                                                        double latitude = data.getDouble("latitude");
                                                                                        double longitude = data.getDouble("longitude");

                                                                                        Log.e("location", latitude + ", " + longitude);

                                                                                        marker.setPosition(new LatLng(latitude, longitude));
                                                                                        marker.setMap(naverMap);

                                                                                        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(new LatLng(latitude, longitude))
                                                                                                .animate(CameraAnimation.Fly, 2000);
                                                                                        naverMap.moveCamera(cameraUpdate);

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
                                            }
                                        });
                                    }
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
    }

    private final Runnable m_Runnable = new Runnable()
    {
        public void run()
        {
            MainActivity.this.mHandler.postDelayed(m_Runnable, 5000);
        }
    };//runnable
}