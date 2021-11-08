package com.example.withme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.withme.bulletin.Bulletin1;
import com.example.withme.bulletin.Bulletin2;
import com.example.withme.group.GroupActivity1;
import com.example.withme.group.GroupAddActivity1;
import com.example.withme.group.GroupAddActivity2;
import com.example.withme.intro.DescriptionActivity;
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

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;

    private static final String[] PERMISSION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private ConstraintLayout coachMark;
    private ImageButton makeGroup1, makeGroup2;
    private NaverMap naverMap;
    private MapView mapView;
    private Button logout, groupButton;
    private FusedLocationSource fusedLocationSource;
    private ImageButton bulletinBoard, group;

    Bulletin1 bulletin1;
    Bulletin2 bulletin2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        String accessToken = sf.getString("AccessToken", "");

        Intent intent = new Intent(this, GroupActivity1.class);
        Intent intent1 = new Intent(this, DescriptionActivity.class);

        Bundle bundle = new Bundle(1); // 파라미터의 숫자는 전달하려는 값의 갯수
        bundle.putString("AccessToken", accessToken);

        coachMark = (ConstraintLayout) findViewById(R.id.coach_mark_master_view);
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (fusedLocationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if(!fusedLocationSource.isActivated()) { //권한 거부됨
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            } else {
                naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    public void onFragmentChange(int index){
        if(index == 2) {
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();

            transaction.replace(R.id.fragment_container, bulletin2).commit();
            transaction.addToBackStack(null);

        }

//        }else if(index ==1){
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment2).commit();
//        }
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
        locationOverlay.setSubIcon(OverlayImage.fromResource(R.drawable.maskgroup));

        naverMap.setLocationSource(fusedLocationSource); //현재 위치
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        CameraPosition cameraPosition = naverMap.getCameraPosition();

        CameraPosition currentPosition = new CameraPosition(cameraPosition.target, 17);

        naverMap.setCameraPosition(currentPosition);

        ActivityCompat.requestPermissions(this, PERMISSION, LOCATION_PERMISSION_REQUEST_CODE);
    }
}