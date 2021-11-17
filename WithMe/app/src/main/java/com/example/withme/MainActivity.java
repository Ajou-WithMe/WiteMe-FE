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
import android.preference.PreferenceManager;
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

import com.bumptech.glide.Glide;
import com.example.withme.bulletin.Bulletin1;
import com.example.withme.bulletin.Bulletin2;
import com.example.withme.group.BottomSheetDialog;
import com.example.withme.group.GroupActivity1;
import com.example.withme.intro.DescriptionActivity;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

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
    private ArrayList<String> protectionPersonName = new ArrayList<>();

    private ConstraintLayout coachMark;
    private LinearLayout protectionPersonLayout;
    private ImageButton makeGroup1, makeGroup2;
    private NaverMap naverMap;
    private MapView mapView;
    private CircleImageView circleImageView;
    private Button logout, groupButton;
    private FusedLocationProviderClient mFusedLocationClient;
    private boolean mLocationPermissionGranted = false;
    private FusedLocationSource fusedLocationSource;
    private ImageButton bulletinBoard, group;

    Bulletin1 bulletin1;
    Bulletin2 bulletin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        protectionPersonName = getStringArrayPref(getApplicationContext(), "name");
        Log.e("protectionPersonName!", protectionPersonName.toString());

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

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

        bulletin1 = new Bulletin1();
        bulletin2 = new Bulletin2();

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

        for (int i = 0; i < protectionPersonName.size(); i++) {
            String name = protectionPersonName.get(i);
            Log.e("name", name);

            RelativeLayout relativeLayout = new RelativeLayout(this);
            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.setLayoutParams(relativeLayoutParams);
            relativeLayoutParams.setMargins(51,0,0,0);
            relativeLayout.setId(i);
            relativeLayout.setGravity(Gravity.CENTER_VERTICAL);

            CircleImageView circleImageView = new CircleImageView(this);
            RelativeLayout.LayoutParams circle= new RelativeLayout.LayoutParams(144, 144);
            circleImageView.setLayoutParams(circle);
            circleImageView.setBackgroundResource(R.drawable.oval_shape_green);

            TextView textView = new TextView(this);
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
        }

        group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "bottomSheet");
                bottomSheetDialog.setArguments(bundle);
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
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("AccessToken");
                editor.commit();
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        startActivity(intent1);
                    }
                });
                startActivity(intent1);
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

    private void startLocationService() {
        if(!isLocationServiceRunning()) {
            Intent intent = new Intent(this, LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
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
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            }else {
                Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onFragmentChange(int index){
        if(index == 2) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.fragment_container, bulletin2).commit();
            transaction.addToBackStack(null);
        }
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);

        this.naverMap = naverMap;

        LocationOverlay locationOverlay = naverMap.getLocationOverlay();
        locationOverlay.setVisible(true);

        locationOverlay.setIcon(OverlayImage.fromResource(R.drawable.sub_icon));
        locationOverlay.setIconWidth(70);
        locationOverlay.setIconHeight(70);

        naverMap.setLocationSource(fusedLocationSource); //현재 위치
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        CameraPosition cameraPosition = naverMap.getCameraPosition();

        CameraPosition currentPosition = new CameraPosition(cameraPosition.target, 12.1);

        naverMap.setCameraPosition(currentPosition);

        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private ArrayList<String> getStringArrayPref(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String json = prefs.getString(key, null);
        ArrayList<String> urls = new ArrayList<String>();
        if (json != null) {
            try {
                JSONArray a = new JSONArray(json);
                for (int i = 0; i < a.length(); i++) {
                    String url = a.optString(i);
                    urls.add(url);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return urls;
    }
}