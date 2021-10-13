package com.example.withme;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

public class GroupActivity3 extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int PASSWORD_ACTIVITY = 20000;
    private String name, id, address, detailAddress, fullAddress;
    private ImageView checkbox1, checkbox2, checkbox3, checkbox4, checkbox5;
    private LinearLayout nameLayout, IDLayout, passwordLayout, addressLayout, detailAddressLayout;
    private EditText etName, etID, etDetailAddress;
    private TextView tvPassword, tvAddress;
    private Button startWithMe;
    private double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group3);

        Intent intent1 = new Intent(this, GroupActivity5.class);
        Intent data = getIntent();

        startWithMe = (Button) findViewById(R.id.startWithMe);

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

        startWithMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent1.putExtra("latitude", latitude);
                intent1.putExtra("longitude", longitude);
                startActivity(intent1);
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
                    if (data != null) {
                        tvPassword.setText("******");
                        tvPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        tvPassword.setTextColor(Color.parseColor("#333333"));

                        checkbox3.setVisibility(View.VISIBLE);
                        passwordLayout.setBackgroundResource(R.drawable.edittext_rounded_corner_sign4_selected);
                    }
                }
                break;
        }
    }
}