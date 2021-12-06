package com.example.withme.group;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GroupActivity5 extends AppCompatActivity implements OnMapReadyCallback {

    private NaverMap naverMap;
    private MapView mapView;
    private ImageView refactor;
    private Dialog dialog;
    private Button safeZoneComplete, nextActivity;
    private ConstraintLayout safeZoneDrawAlarm;
    private double longitudeGapPlus, longitudeGapMinus;
    private JsonArray Vertexes = new JsonArray();
    PolygonOverlay polygonOverlay1 = new PolygonOverlay();
    private ArrayList<LatLng> latLngs = new ArrayList<LatLng>();
    private ArrayList<Marker> markers = new ArrayList<Marker>();
    double latitude, longitude;
    String name, message, accessToken, uid;
    boolean clickable = true;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://121.154.58.201:8000")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    Retrofit retrofit2 = new retrofit2.Retrofit.Builder()
            .baseUrl("http://3.37.163.203:8000")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI2 = retrofit2.create(RetrofitAPI.class);

    Retrofit retrofit3 = new retrofit2.Retrofit.Builder()
            .baseUrl("http://3.37.163.203:8040/zone_manage/certify_put/")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI3 = retrofit3.create(RetrofitAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group5);

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        uid = intent.getStringExtra("uid");

        safeZoneDrawAlarm = (ConstraintLayout) findViewById(R.id.safeZoneDrawAlarm);

        safeZoneComplete = (Button) findViewById(R.id.safeZoneComplete);
        nextActivity = (Button) findViewById(R.id.nextActivity);

        refactor = (ImageView) findViewById(R.id.refactor);

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

        Marker marker1 = new Marker();
        marker1.setPosition(new LatLng(latitude, longitude));
        marker1.setIcon(OverlayImage.fromResource(R.drawable.present_location));
        marker1.setWidth(180);
        marker1.setHeight(180);
        marker1.setMap(naverMap);

        PolygonOverlay polygonOverlay = new PolygonOverlay();
        polygonOverlay.setCoords(Arrays.asList(
                new LatLng(latitude+(2.15/109.958489), longitude + (longitudeGapPlus)),
                new LatLng(latitude+(2.15/109.958489), longitude - (longitudeGapMinus)),
                new LatLng(latitude-(2.15/109.958489), longitude - (longitudeGapMinus)),
                new LatLng(latitude-(2.15/109.958489), longitude + (longitudeGapPlus))
        ));

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
        uiSettings.setZoomControlEnabled(false);
        uiSettings.setRotateGesturesEnabled(false);


        naverMap.setOnMapClickListener(new NaverMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull PointF pointF, @NonNull LatLng latLng) {

                if (clickable == true) {
                    safeZoneDrawAlarm.setVisibility(View.INVISIBLE);
                    JsonObject Vertex = new JsonObject();
                    Vertex.addProperty("latitude", latLng.latitude);
                    Vertex.addProperty("longitude", latLng.longitude);

                    Vertexes.add(Vertex);
                    latLngs.add(new LatLng(latLng.latitude, latLng.longitude));

                    Marker marker = new Marker();

                    marker.setPosition(new LatLng(latLng.latitude, latLng.longitude));
                    marker.setIcon(OverlayImage.fromResource(R.drawable.marker_safezone));
                    marker.setWidth(80);
                    marker.setHeight(125);
                    marker.setMap(naverMap);

                    markers.add(marker); }
                }
        });

        safeZoneComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (latLngs.size() < 3) {
                    Toast.makeText(GroupActivity5.this, "적어도 세 개 이상의 점을 찍어야합니다.", Toast.LENGTH_SHORT).show();
                    for (int i=0; i < markers.size(); i++) {
                        markers.get(i).setMap(null);
                    }
                    polygonOverlay1.setMap(null);
                    markers.clear();
                    latLngs.clear();
                    while(Vertexes.size()>0)
                    {
                        Vertexes.remove(0);
                    }
                } else {
                    HashMap<String, Object> input = new HashMap<>();
                    input.put("safeZone", Vertexes);
                    retrofitAPI2.safeZoneVerification(accessToken, input).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(response.body().string());
                                        boolean success = jsonObject.getBoolean("success");

                                        if (success == false) {
                                            int data = jsonObject.getInt("data");
                                            Log.e("safe zone", String.valueOf(jsonObject));

                                            if(data == 2) {
                                                Toast.makeText(GroupActivity5.this, "가로 길이가 너무 짧아요 더 크게 그려주세요", Toast.LENGTH_SHORT).show();
                                                polygonOverlay1.setCoords(latLngs);
                                                polygonOverlay1.setOutlineColor(Color.parseColor("#FF302B"));
                                                polygonOverlay1.setOutlineWidth(18);
                                                polygonOverlay1.setColor(Color.parseColor("#4DFF302B"));
                                                polygonOverlay1.setMap(naverMap);

                                                nextActivity.setVisibility(View.INVISIBLE);
                                                safeZoneComplete.setVisibility(View.INVISIBLE);

                                            } else if (data == 3) {
                                                Toast.makeText(GroupActivity5.this, "세로 길이가 너무 짧아요 더 크게 그려주세요", Toast.LENGTH_SHORT).show();
                                                polygonOverlay1.setCoords(latLngs);
                                                polygonOverlay1.setOutlineColor(Color.parseColor("#FF302B"));
                                                polygonOverlay1.setOutlineWidth(18);
                                                polygonOverlay1.setColor(Color.parseColor("#4DFF302B"));
                                                polygonOverlay1.setMap(naverMap);

                                                nextActivity.setVisibility(View.INVISIBLE);
                                                safeZoneComplete.setVisibility(View.INVISIBLE);

                                            } else {
                                                Toast.makeText(GroupActivity5.this, "가로, 세로 길이가 너무 짧아요 더 크게 그려주세요", Toast.LENGTH_SHORT).show();
                                                polygonOverlay1.setCoords(latLngs);
                                                polygonOverlay1.setOutlineColor(Color.parseColor("#FF302B"));
                                                polygonOverlay1.setOutlineWidth(18);
                                                polygonOverlay1.setColor(Color.parseColor("#4DFF302B"));
                                                polygonOverlay1.setMap(naverMap);

                                                nextActivity.setVisibility(View.INVISIBLE);
                                                safeZoneComplete.setVisibility(View.INVISIBLE);
                                            }
                                        } else {
                                            JSONObject data = jsonObject.getJSONObject("data");
                                            double temp_x = data.getDouble("temp_x");
                                            double temp_y = data.getDouble("temp_y");

                                            Log.e("x, y", temp_x + ", " + temp_y);
                                            polygonOverlay1.setCoords(latLngs);
                                            polygonOverlay1.setOutlineColor(Color.parseColor("#FED537"));
                                            polygonOverlay1.setOutlineWidth(18);
                                            polygonOverlay1.setColor(Color.parseColor("#80FED537"));
                                            polygonOverlay1.setMap(naverMap);

                                            clickable = false;

                                            nextActivity.setVisibility(View.VISIBLE);
                                            safeZoneComplete.setVisibility(View.INVISIBLE);
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
                    refactor.setVisibility(View.VISIBLE);
                }
            }
        });

        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                polygonOverlay1.setMap(null);
                for (int i=0; i < markers.size(); i++) {
                    markers.get(i).setMap(null);
                }
                while (Vertexes.size() > 0) {
                    Vertexes.remove(0);
                }
                markers.clear();
                latLngs.clear();
                refactor.setVisibility(View.INVISIBLE);
                safeZoneDrawAlarm.setVisibility(View.VISIBLE);
                nextActivity.setVisibility(View.INVISIBLE);
                safeZoneComplete.setVisibility(View.VISIBLE);

                clickable = true;
            }
        });

        nextActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, Object> input = new HashMap<>();
                input.put("safeZone", Vertexes);
                input.put("uid", uid);
                retrofitAPI3.safeZoneInsert(accessToken, input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                boolean success = jsonObject.getBoolean("success");

                                if (success == true) {
                                    Intent intent = new Intent(GroupActivity5.this, GroupActivity6.class);
                                    intent.putExtra("uid", uid);
                                    startActivity(intent);
                                } else {
                                    Log.e("safeZoneInsert", "오류");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("safeZoneInsert", "오류");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("safeZoneInsert", "오류");
                    }
                });
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