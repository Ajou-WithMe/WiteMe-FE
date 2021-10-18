package com.example.withme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.withme.group.GroupActivity1;
import com.example.withme.user.LoginActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {


    private ConstraintLayout coachMark;
    private ImageButton makeGroup1, makeGroup2;
    private NaverMap naverMap;
    private MapView mapView;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, GroupActivity1.class);
        Intent intent1 = new Intent(this, LoginActivity.class);

        coachMark = (ConstraintLayout) findViewById(R.id.coach_mark_master_view);
        makeGroup1 = (ImageButton) findViewById(R.id.makeGroup_1);
        makeGroup2 = (ImageButton) findViewById(R.id.makeGroup_2);

        logout = (Button) findViewById(R.id.logout);

        // 네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);
    }
}