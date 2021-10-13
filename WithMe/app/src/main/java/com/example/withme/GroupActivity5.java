package com.example.withme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PolygonOverlay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

public class GroupActivity5 extends AppCompatActivity implements OnMapReadyCallback {

    private NaverMap naverMap;
    private MapView mapView;
    private Dialog dialog;
    private Button safeZoneComplete;
    private ConstraintLayout safeZoneDrawAlarm;
    private double longitudeGapPlus, longitudeGapMinus;
    private ArrayList<LatLng> latLngs = new ArrayList<LatLng>();


    double latitude, longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group5);

        safeZoneDrawAlarm = (ConstraintLayout) findViewById(R.id.safeZoneDrawAlarm);

        safeZoneComplete = (Button) findViewById(R.id.safeZoneComplete);

        // 네이버 지도
        mapView = (MapView) findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent maps = getIntent();
        latitude = maps.getDoubleExtra("latitude", 0);
        longitude = maps.getDoubleExtra("longitude", 0);

        Log.e("위치", latitude + ", " + longitude);

        longitudeGapPlus = 2.15 / getKilometerPlus(latitude, longitude);
        longitudeGapMinus = 2.15 / getKilometerMinus(latitude, longitude);

        dialog = new Dialog(this);
        openDialog();
    }

    private double getKilometerPlus(double latitude, double longitude) {
        double plusLongitude = longitude + 1;

        return distance(latitude, longitude, latitude, plusLongitude, "kilometer");
    }

    private double getKilometerMinus(double latitude, double longitude) {
        double minusLongitude = longitude - 1;

        return distance(latitude, longitude, latitude, minusLongitude, "kilometer");
    }

    private void openDialog() {
        dialog.setContentView(R.layout.safezone_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button understand = dialog.findViewById(R.id.understand);

        understand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;

        Marker marker = new Marker();
        marker.setPosition(new LatLng(latitude, longitude));
        marker.setIcon(OverlayImage.fromResource(R.drawable.present_location));
        marker.setWidth(180);
        marker.setHeight(180);
        marker.setMap(naverMap);

        PolygonOverlay polygonOverlay = new PolygonOverlay();
        polygonOverlay.setCoords(Arrays.asList(
                new LatLng(latitude+(2.15/109.958489), longitude + (longitudeGapPlus)),
                new LatLng(latitude+(2.15/109.958489), longitude - (longitudeGapMinus)),
                new LatLng(latitude-(2.15/109.958489), longitude - (longitudeGapMinus)),
                new LatLng(latitude-(2.15/109.958489), longitude + (longitudeGapPlus))
        ));

        Log.e("distance", String.valueOf(distance(latitude, longitude, latitude, longitude+(longitudeGapPlus), "kilometer")));
        Log.e("distance", String.valueOf(distance(latitude, longitude, latitude, longitude-(longitudeGapMinus), "kilometer")));

        polygonOverlay.setOutlineColor(Color.parseColor("#3E791A"));
        polygonOverlay.setOutlineWidth(18);
        polygonOverlay.setColor(Color.TRANSPARENT);
        polygonOverlay.setMap(naverMap);


        CameraPosition cameraPosition = new CameraPosition(
                new LatLng(latitude, longitude), 12.1, 0, 0
        );
        naverMap.setCameraPosition(cameraPosition);

        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setScaleBarEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);


        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {


                latLngs.add(new LatLng(latLng.latitude, latLng.longitude));

                Marker marker = new Marker();
                marker.setPosition(new LatLng(latLng.latitude, latLng.longitude));
                marker.setIcon(OverlayImage.fromResource(R.drawable.marker));
                marker.setWidth(84);
                marker.setHeight(84);
                marker.setMap(naverMap);

                Log.e("LatLng", String.valueOf(latLng.latitude + ", " +latLng.longitude));
                Log.e("array", String.valueOf(latLngs));
            }
        });

        safeZoneComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PolygonOverlay polygonOverlay1 = new PolygonOverlay();
                polygonOverlay1.setCoords(latLngs);

                polygonOverlay1.setOutlineColor(Color.parseColor("#FED537"));
                polygonOverlay1.setOutlineWidth(18);
                polygonOverlay1.setColor(Color.parseColor("#80FED537"));
                polygonOverlay1.setMap(naverMap);
            }
        });
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}