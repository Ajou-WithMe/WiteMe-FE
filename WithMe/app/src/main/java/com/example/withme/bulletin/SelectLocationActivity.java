package com.example.withme.bulletin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.group.BottomSheetDialogMain;
import com.example.withme.settings.Settings;

public class SelectLocationActivity extends AppCompatActivity {

    private boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);

        ConstraintLayout seoul = (ConstraintLayout) findViewById(R.id.seoul);
        ConstraintLayout incheon = (ConstraintLayout) findViewById(R.id.incheon);
        ConstraintLayout gyeongi = (ConstraintLayout) findViewById(R.id.gyeongi);
        ConstraintLayout gangwon = (ConstraintLayout) findViewById(R.id.gangwon);
        ConstraintLayout jeonbuk = (ConstraintLayout) findViewById(R.id.jeonbuk);
        ConstraintLayout jeonnam = (ConstraintLayout) findViewById(R.id.jeonnam);
        ConstraintLayout gyeongbuk = (ConstraintLayout) findViewById(R.id.gyeongbuk);
        ConstraintLayout gyeongnam = (ConstraintLayout) findViewById(R.id.gyeongnam);
        ConstraintLayout choongbuk = (ConstraintLayout) findViewById(R.id.choongbuk);
        ConstraintLayout choongnam = (ConstraintLayout) findViewById(R.id.choongnam);
        ConstraintLayout ulsan = (ConstraintLayout) findViewById(R.id.ulsan);
        ConstraintLayout daejeon = (ConstraintLayout) findViewById(R.id.daejeon);
        ConstraintLayout gwangju = (ConstraintLayout) findViewById(R.id.gwangju);
        ConstraintLayout jeju = (ConstraintLayout) findViewById(R.id.jeju);
        ConstraintLayout busan = (ConstraintLayout) findViewById(R.id.busan);
        ConstraintLayout daegu = (ConstraintLayout) findViewById(R.id.daegu);

        ConstraintLayout detail_seoul = (ConstraintLayout) findViewById(R.id.detail_seoul);
        ConstraintLayout detail_gyeongi = (ConstraintLayout) findViewById(R.id.detail_gyeongi);
        ConstraintLayout detail_incheon = (ConstraintLayout) findViewById(R.id.detail_incheon);
        ConstraintLayout detail_gangwon = (ConstraintLayout) findViewById(R.id.detail_ganwon);
        ConstraintLayout detail_jeonbuk = (ConstraintLayout) findViewById(R.id.detail_jeonbuk);
        ConstraintLayout detail_jeonnam = (ConstraintLayout) findViewById(R.id.detail_jeonnam);
        ConstraintLayout detail_gyeongbuk = (ConstraintLayout) findViewById(R.id.detail_gyeonbuk);
        ConstraintLayout detail_gyeongnam = (ConstraintLayout) findViewById(R.id.detail_gyeongnam);
        ConstraintLayout detail_choongbuk = (ConstraintLayout) findViewById(R.id.detail_choongbuk);
        ConstraintLayout detail_choongnam = (ConstraintLayout) findViewById(R.id.detail_choongnam);
        ConstraintLayout detail_ulsan = (ConstraintLayout) findViewById(R.id.detail_ulsan);
        ConstraintLayout detail_daejeon = (ConstraintLayout) findViewById(R.id.detail_daejeon);
        ConstraintLayout detail_gwangju = (ConstraintLayout) findViewById(R.id.detail_gwangju);
        ConstraintLayout detail_jeju = (ConstraintLayout) findViewById(R.id.detail_jeju);
        ConstraintLayout detail_busan = (ConstraintLayout) findViewById(R.id.detail_busan);
        ConstraintLayout detail_daegu = (ConstraintLayout) findViewById(R.id.detail_daegu);

        TextView tv_andong = (TextView) findViewById(R.id.tv_andong);
        tv_andong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_andong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_ansan = (TextView) findViewById(R.id.tv_ansan);
        tv_ansan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_ansan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_ansung = (TextView) findViewById(R.id.tv_ansung);
        tv_ansung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_ansung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_anyang = (TextView) findViewById(R.id.tv_anyang);
        tv_anyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_anyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_asan = (TextView) findViewById(R.id.tv_asan);
        tv_asan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_asan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_boeun = (TextView) findViewById(R.id.tv_boeun);
        tv_boeun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_boeun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_bonghwa = (TextView) findViewById(R.id.tv_bonghwa);
        tv_bonghwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_bonghwa.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_boryeong = (TextView) findViewById(R.id.tv_boryeong);
        tv_boryeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_boryeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_bosung = (TextView) findViewById(R.id.tv_bosung);
        tv_bosung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_bosung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_buan = (TextView) findViewById(R.id.tv_buan);
        tv_buan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_buan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_bucheon = (TextView) findViewById(R.id.tv_bucheon);
        tv_bucheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_bucheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_buk_busan = (TextView) findViewById(R.id.tv_buk_busan);
        tv_buk_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_buk_busan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_buk_daegu = (TextView) findViewById(R.id.tv_buk_daegu);
        tv_buk_daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_buk_daegu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_buk_gwangju = (TextView) findViewById(R.id.tv_buk_gwangju);
        tv_buk_gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_buk_gwangju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_buk_ulsan = (TextView) findViewById(R.id.tv_buk_ulsan);
        tv_buk_ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_buk_ulsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_bupyeong = (TextView) findViewById(R.id.tv_bupyeong);
        tv_bupyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_bupyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_buyeo = (TextView) findViewById(R.id.tv_buyeo);
        tv_buyeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_buyeo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_changnyeong = (TextView) findViewById(R.id.tv_changnyeong);
        tv_changnyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_changnyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_changwon = (TextView) findViewById(R.id.tv_changwon);
        tv_changwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_changwon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_cheonan = (TextView) findViewById(R.id.tv_cheonan);
        tv_cheonan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_cheonan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chilgok = (TextView) findViewById(R.id.tv_chilgok);
        tv_chilgok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chilgok.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_choongju = (TextView) findViewById(R.id.tv_choongju);
        tv_choongju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_choongju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chulwon = (TextView) findViewById(R.id.tv_chulwon);
        tv_chulwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chulwon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chunchon = (TextView) findViewById(R.id.tv_chunchon);
        tv_chunchon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chunchon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chungdo = (TextView) findViewById(R.id.tv_chungdo);
        tv_chungdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chungdo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chungju = (TextView) findViewById(R.id.tv_chungju);
        tv_chungju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chungju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chungsong = (TextView) findViewById(R.id.tv_chungsong);
        tv_chungsong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chungsong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_chungyang = (TextView) findViewById(R.id.tv_chungyang);
        tv_chungyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_chungyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_daeduk = (TextView) findViewById(R.id.tv_daeduk);
        tv_daeduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_daeduk.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dalseo = (TextView) findViewById(R.id.tv_dalseo);
        tv_dalseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dalseo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dalsung = (TextView) findViewById(R.id.tv_dalsung);
        tv_dalsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dalsung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_damyang = (TextView) findViewById(R.id.tv_damyang);
        tv_damyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_damyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dangjin = (TextView) findViewById(R.id.tv_dangjin);
        tv_dangjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dangjin.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_danyang = (TextView) findViewById(R.id.tv_danyang);
        tv_danyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_danyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dobong = (TextView) findViewById(R.id.tv_dobong);
        tv_dobong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dobong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dong_busan = (TextView) findViewById(R.id.tv_dong_busan);
        tv_dong_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dong_busan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dong_daegu = (TextView) findViewById(R.id.tv_dong_daegu);
        tv_dong_daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dong_daegu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dong_daejon = (TextView) findViewById(R.id.tv_dong_daejeon);
        tv_dong_daejon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dong_daejon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dong_gwangju = (TextView) findViewById(R.id.tv_dong_gwangju);
        tv_dong_gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dong_gwangju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dong_incheon = (TextView) findViewById(R.id.tv_dong_incheon);
        tv_dong_incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dong_incheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dong_ulsan = (TextView) findViewById(R.id.tv_dong_ulsan);
        tv_dong_ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dong_ulsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dongdaemun = (TextView) findViewById(R.id.tv_dongdaemun);
        tv_dongdaemun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dongdaemun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dongducheon = (TextView) findViewById(R.id.tv_dongducheon);
        tv_dongducheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dongducheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_donghae = (TextView) findViewById(R.id.tv_donghae);
        tv_donghae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_donghae.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_dongjak = (TextView) findViewById(R.id.tv_dongjak);
        tv_dongjak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_dongjak.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_donglae = (TextView) findViewById(R.id.tv_donglae);
        tv_donglae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_donglae.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_eijungbu = (TextView) findViewById(R.id.tv_eijungbu);
        tv_eijungbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_eijungbu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_eiryung = (TextView) findViewById(R.id.tv_eiryung);
        tv_eiryung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_eiryung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_eisung = (TextView) findViewById(R.id.tv_eisung);
        tv_eisung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_eisung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_eiwang = (TextView) findViewById(R.id.tv_eiwang);
        tv_eiwang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_eiwang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_eumsung = (TextView) findViewById(R.id.tv_eumsung);
        tv_eumsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_eumsung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_eunpyeong = (TextView) findViewById(R.id.tv_eunpyeong);
        tv_eunpyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_eunpyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangnam = (TextView) findViewById(R.id.tv_gangnam);
        tv_gangnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangnam.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangbuk = (TextView) findViewById(R.id.tv_gangbuk);
        tv_gangbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangbuk.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangdong = (TextView) findViewById(R.id.tv_gangdong);
        tv_gangdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangdong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_ganghwa = (TextView) findViewById(R.id.tv_ganghwa);
        tv_ganghwa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_ganghwa.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangjin = (TextView) findViewById(R.id.tv_gangjin);
        tv_gangjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangjin.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangneung = (TextView) findViewById(R.id.tv_gangneung);
        tv_gangneung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangneung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangseo = (TextView) findViewById(R.id.tv_gangseo);
        tv_gangseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangseo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gangseo_busan = (TextView) findViewById(R.id.tv_gangseo_busan);
        tv_gangseo_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gangseo_busan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gapyeong = (TextView) findViewById(R.id.tv_gapyeong);
        tv_gapyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gapyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_geochang = (TextView) findViewById(R.id.tv_geochang);
        tv_geochang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_geochang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_geoje = (TextView) findViewById(R.id.tv_geoje);
        tv_geoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_geoje.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_geumcheon = (TextView) findViewById(R.id.tv_geumcheon);
        tv_geumcheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_geumcheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_geumjung = (TextView) findViewById(R.id.tv_geumjung);
        tv_geumjung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_geumjung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gijang = (TextView) findViewById(R.id.tv_gijang);
        tv_gijang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gijang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gimchun = (TextView) findViewById(R.id.tv_gimchun);
        tv_gimchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gimchun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gimhae = (TextView) findViewById(R.id.tv_gimhae);
        tv_gimhae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gimhae.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gimje = (TextView) findViewById(R.id.tv_gimje);
        tv_gimje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gimje.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gimpo = (TextView) findViewById(R.id.tv_gimpo);
        tv_gimpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gimpo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gochang = (TextView) findViewById(R.id.tv_gochang);
        tv_gochang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gochang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_goheung = (TextView) findViewById(R.id.tv_goheung);
        tv_goheung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_goheung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_goksung = (TextView) findViewById(R.id.tv_goksung);
        tv_goksung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_goksung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gongju = (TextView) findViewById(R.id.tv_gongju);
        tv_gongju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gongju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_goryeong = (TextView) findViewById(R.id.tv_goryeong);
        tv_goryeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_goryeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gosung = (TextView) findViewById(R.id.tv_gosung);
        tv_gosung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gosung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gosung_gangwon = (TextView) findViewById(R.id.tv_gosung_gangwon);
        tv_gosung_gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gosung_gangwon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_goyang = (TextView) findViewById(R.id.tv_goyang);
        tv_goyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_goyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gumi = (TextView) findViewById(R.id.tv_gumi);
        tv_gumi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gumi.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gumsan = (TextView) findViewById(R.id.tv_gumsan);
        tv_gumsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gumsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gunpo = (TextView) findViewById(R.id.tv_gunpo);
        tv_gunpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gunpo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gunsan = (TextView) findViewById(R.id.tv_gunsan);
        tv_gunsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gunsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gunwi = (TextView) findViewById(R.id.tv_gunwi);
        tv_gunwi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gunwi.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_guri_gyeongi = (TextView) findViewById(R.id.tv_guri_gyeongi);
        tv_guri_gyeongi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_guri_gyeongi.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_guro = (TextView) findViewById(R.id.tv_guro);
        tv_guro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_guro.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gurye = (TextView) findViewById(R.id.tv_gurye);
        tv_gurye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gurye.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwacheon = (TextView) findViewById(R.id.tv_gwacheon);
        tv_gwacheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwacheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwanak = (TextView) findViewById(R.id.tv_gwanak);
        tv_gwanak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwanak.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwangjin = (TextView) findViewById(R.id.tv_gwangjin);
        tv_gwangjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwangjin.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwangju_gyeongi = (TextView) findViewById(R.id.tv_gwangju_gyeongi);
        tv_gwangju_gyeongi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwangju_gyeongi.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwangmyeong = (TextView) findViewById(R.id.tv_gwangmyeong);
        tv_gwangmyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwangmyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwangsan = (TextView) findViewById(R.id.tv_gwangsan);
        tv_gwangsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwangsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwangyang = (TextView) findViewById(R.id.tv_gwangyang);
        tv_bosung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_bosung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gwesan = (TextView) findViewById(R.id.tv_gwesan);
        tv_gwesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gwesan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gyeongju = (TextView) findViewById(R.id.tv_gyeongju);
        tv_gyeongju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gyeongju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gyeongsan = (TextView) findViewById(R.id.tv_gyeongsan);
        tv_gyeongsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gyeongsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gyeryong = (TextView) findViewById(R.id.tv_gyeryong);
        tv_gyeryong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gyeryong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_gyeyang = (TextView) findViewById(R.id.tv_gyeyang);
        tv_gyeyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_gyeyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hadong = (TextView) findViewById(R.id.tv_hadong);
        tv_hadong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hadong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_haenam = (TextView) findViewById(R.id.tv_haenam);
        tv_haenam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_haenam.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_haeundae = (TextView) findViewById(R.id.tv_haeundae);
        tv_haeundae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_haeundae.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_haman = (TextView) findViewById(R.id.tv_haman);
        tv_haman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_haman.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hampyeong = (TextView) findViewById(R.id.tv_hampyeong);
        tv_hampyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hampyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hamyang = (TextView) findViewById(R.id.tv_hamyang);
        tv_hamyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hamyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hanam = (TextView) findViewById(R.id.tv_hanam);
        tv_hanam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hanam.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hapcheon = (TextView) findViewById(R.id.tv_hapcheon);
        tv_hapcheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hapcheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hongchun = (TextView) findViewById(R.id.tv_hongchun);
        tv_hongchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hongchun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hongsung = (TextView) findViewById(R.id.tv_hongsung);
        tv_hongsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hongsung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hwachun = (TextView) findViewById(R.id.tv_hwachun);
        tv_hwachun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hwachun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hwasun = (TextView) findViewById(R.id.tv_hwasun);
        tv_hwasun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hwasun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hwasung = (TextView) findViewById(R.id.tv_hwasung);
        tv_hwasung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hwasung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_hwengsung = (TextView) findViewById(R.id.tv_hwengsung);
        tv_hwengsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_hwengsung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_icheon = (TextView) findViewById(R.id.tv_icheon);
        tv_icheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_icheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_iksan = (TextView) findViewById(R.id.tv_iksan);
        tv_iksan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_iksan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_imsil = (TextView) findViewById(R.id.tv_imsil);
        tv_imsil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_imsil.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_inje = (TextView) findViewById(R.id.tv_inje);
        tv_inje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_inje.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jangheung = (TextView) findViewById(R.id.tv_jangheung);
        tv_jangheung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jangheung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jansu = (TextView) findViewById(R.id.tv_jangsu);
        tv_jansu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jansu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jangsung = (TextView) findViewById(R.id.tv_jangsung);
        tv_jangsung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jangsung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jechun = (TextView) findViewById(R.id.tv_jechun);
        tv_jechun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jechun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jejusi = (TextView) findViewById(R.id.tv_jejusi);
        tv_jejusi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jejusi.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jeongeup = (TextView) findViewById(R.id.tv_jeongeup);
        tv_jeongeup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jeongeup.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jeongpyeong = (TextView) findViewById(R.id.tv_jeungpyeong);
        tv_jeongpyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jeongpyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jin = (TextView) findViewById(R.id.tv_jin);
        tv_jin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jin.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jinan = (TextView) findViewById(R.id.tv_jinan);
        tv_jinan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jinan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jinchun = (TextView) findViewById(R.id.tv_jinchun);
        tv_jinchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jinchun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jindo = (TextView) findViewById(R.id.tv_jindo);
        tv_jindo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jindo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jinju = (TextView) findViewById(R.id.tv_jinju);
        tv_jinju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jinju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jongro = (TextView) findViewById(R.id.tv_jongro);
        tv_jongro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jongro.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_joong_busan = (TextView) findViewById(R.id.tv_joong_busan);
        tv_joong_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_joong_busan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_joong_daegu = (TextView) findViewById(R.id.tv_joong_daegu);
        tv_joong_daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_joong_daegu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_joong_daejeon = (TextView) findViewById(R.id.tv_joong_daejeon);
        tv_joong_daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_joong_daejeon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_joong_incheon = (TextView) findViewById(R.id.tv_joong_incheon);
        tv_joong_incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_joong_incheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_joong_seoul = (TextView) findViewById(R.id.tv_joong_seoul);
        tv_joong_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_joong_seoul.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_joong_ulsan = (TextView) findViewById(R.id.tv_joong_ulsan);
        tv_joong_ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_joong_ulsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jungrang = (TextView) findViewById(R.id.tv_jungrang);
        tv_jungrang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jungrang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_jungsun = (TextView) findViewById(R.id.tv_jungsun);
        tv_jungsun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_jungsun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_junju = (TextView) findViewById(R.id.tv_junju);
        tv_junju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_junju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_mapo = (TextView) findViewById(R.id.tv_mapo);
        tv_mapo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_mapo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_michuhol = (TextView) findViewById(R.id.tv_michuhol);
        tv_michuhol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_michuhol.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_milyang = (TextView) findViewById(R.id.tv_milyang);
        tv_milyang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_milyang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_mokpo = (TextView) findViewById(R.id.tv_mokpo);
        tv_mokpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_mokpo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_muan = (TextView) findViewById(R.id.tv_muan);
        tv_muan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_muan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_muju = (TextView) findViewById(R.id.tv_muju);
        tv_muju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_muju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_mungyeong = (TextView) findViewById(R.id.tv_mungyeong);
        tv_mungyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_mungyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_naju = (TextView) findViewById(R.id.tv_naju);
        tv_naju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_naju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_nam_busan = (TextView) findViewById(R.id.tv_nam_busan);
        tv_nam_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_nam_busan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_nam_daegu = (TextView) findViewById(R.id.tv_nam_daegu);
        tv_nam_daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_nam_daegu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_nam_gwangju = (TextView) findViewById(R.id.tv_nam_gwangju);
        tv_nam_gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_nam_gwangju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_nam_ulsan = (TextView) findViewById(R.id.tv_nam_ulsan);
        tv_nam_ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_nam_ulsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_namdong = (TextView) findViewById(R.id.tv_namdong);
        tv_junju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_junju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_namhae = (TextView) findViewById(R.id.tv_namhae);
        tv_namhae.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_namhae.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_namwon = (TextView) findViewById(R.id.tv_namwon);
        tv_namwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_namwon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_namyangju = (TextView) findViewById(R.id.tv_namyangju);
        tv_namyangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_namyangju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_nonsan = (TextView) findViewById(R.id.tv_nonsan);
        tv_nonsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_nonsan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_noweon = (TextView) findViewById(R.id.tv_noweon);
        tv_noweon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_noweon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_okchun = (TextView) findViewById(R.id.tv_okchun);
        tv_okchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_okchun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_ongjin = (TextView) findViewById(R.id.tv_ongjin);
        tv_ongjin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_ongjin.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_osan = (TextView) findViewById(R.id.tv_osan);
        tv_osan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_osan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_paju = (TextView) findViewById(R.id.tv_paju);
        tv_paju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_paju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_pochun = (TextView) findViewById(R.id.tv_pochun);
        tv_pochun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_pochun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_pohang = (TextView) findViewById(R.id.tv_pohang);
        tv_pohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_pohang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_pyeongchang = (TextView) findViewById(R.id.tv_pyeongchang);
        tv_pyeongchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_pyeongchang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_pyeongtaek = (TextView) findViewById(R.id.tv_pyeongtaek);
        tv_pyeongtaek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_pyeongtaek.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sachun = (TextView) findViewById(R.id.tv_sachun);
        tv_sachun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sachun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_saha = (TextView) findViewById(R.id.tv_saha);
        tv_saha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_saha.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_samchuk = (TextView) findViewById(R.id.tv_samchuk);
        tv_samchuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_samchuk.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sanchung = (TextView) findViewById(R.id.tv_sanchung);
        tv_sanchung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sanchung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sangju = (TextView) findViewById(R.id.tv_sangju);
        tv_sangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sangju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sasang = (TextView) findViewById(R.id.tv_sasang);
        tv_sasang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sasang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_secho = (TextView) findViewById(R.id.tv_secho);
        tv_secho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_secho.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seo_busan = (TextView) findViewById(R.id.tv_seo_busan);
        tv_seo_busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seo_busan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seo_daegu = (TextView) findViewById(R.id.tv_seo_daegu);
        tv_seo_daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seo_daegu.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seo_daejeon = (TextView) findViewById(R.id.tv_seo_daejeon);
        tv_seo_daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seo_daejeon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seo_gwangju = (TextView) findViewById(R.id.tv_seo_gwangju);
        tv_seo_gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seo_gwangju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seo_incheon = (TextView) findViewById(R.id.tv_seo_incheon);
        tv_seo_incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seo_incheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seodaemun = (TextView) findViewById(R.id.tv_seodaemun);
        tv_seodaemun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seodaemun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_seoguipo = (TextView) findViewById(R.id.tv_seoguipo);
        tv_seoguipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_seoguipo.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_siheung = (TextView) findViewById(R.id.tv_siheung);
        tv_siheung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_siheung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sinan = (TextView) findViewById(R.id.tv_sinan);
        tv_sinan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sinan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sokcho = (TextView) findViewById(R.id.tv_sokcho);
        tv_sokcho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sokcho.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_songpa = (TextView) findViewById(R.id.tv_songpa);
        tv_songpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_songpa.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_suchun = (TextView) findViewById(R.id.tv_suchun);
        tv_suchun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_suchun.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sunchang = (TextView) findViewById(R.id.tv_sunchang);
        tv_sunchang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sunchang.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_suncheon = (TextView) findViewById(R.id.tv_suncheon);
        tv_suncheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_suncheon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sungbuk = (TextView) findViewById(R.id.tv_sungbuk);
        tv_sungbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sungbuk.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sungdong = (TextView) findViewById(R.id.tv_sungdong);
        tv_sungdong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sungdong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sungju = (TextView) findViewById(R.id.tv_sungju);
        tv_sungju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sungju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_sungnam = (TextView) findViewById(R.id.tv_sungnam);
        tv_sungnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_sungnam.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_susan = (TextView) findViewById(R.id.tv_susan);
        tv_susan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_susan.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_susung = (TextView) findViewById(R.id.tv_susung);
        tv_susung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_susung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_suwon = (TextView) findViewById(R.id.tv_suwon);
        tv_suwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_suwon.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_suyeong = (TextView) findViewById(R.id.tv_suyeong);
        tv_suyeong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_suyeong.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_taean = (TextView) findViewById(R.id.tv_taean);
        tv_taean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_taean.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_taebaek = (TextView) findViewById(R.id.tv_taebaek);
        tv_taebaek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_taebaek.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_tongyoung = (TextView) findViewById(R.id.tv_tongyoung);
        tv_tongyoung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_tongyoung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_uleung = (TextView) findViewById(R.id.tv_uleung);
        tv_uleung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_uleung.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_uljin = (TextView) findViewById(R.id.tv_uljin);
        tv_uljin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_uljin.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_ulju = (TextView) findViewById(R.id.tv_ulju);
        tv_ulju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_ulju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_wando = (TextView) findViewById(R.id.tv_wando);
        tv_wando.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_wando.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_wanju = (TextView) findViewById(R.id.tv_wanju);
        tv_wanju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_wanju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        TextView tv_wonju = (TextView) findViewById(R.id.tv_wonju);
        tv_wonju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extra = new Bundle();
                Intent intent = new Intent();
                extra.putString("category", tv_wonju.getText().toString());
                intent.putExtras(extra);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    seoul.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));


                    detail_seoul.setVisibility(View.VISIBLE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_seoul.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        gyeongi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    gyeongi.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_gyeongi.setVisibility(View.VISIBLE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_gyeongi.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        incheon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    incheon.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_incheon.setVisibility(View.VISIBLE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_incheon.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        gangwon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    gangwon.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_gangwon.setVisibility(View.VISIBLE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_gangwon.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        jeonbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    jeonbuk.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_jeonbuk.setVisibility(View.VISIBLE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_jeonbuk.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        jeonnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    jeonnam.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_jeonnam.setVisibility(View.VISIBLE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_jeonnam.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        gyeongbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    gyeongbuk.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_gyeongbuk.setVisibility(View.VISIBLE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_gyeongbuk.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        gyeongnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    gyeongnam.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_gyeongnam.setVisibility(View.VISIBLE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_gyeongnam.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        choongbuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    choongbuk.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_choongbuk.setVisibility(View.VISIBLE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_choongbuk.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        choongnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    choongnam.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_choongnam.setVisibility(View.VISIBLE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_choongnam.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        ulsan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    ulsan.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_ulsan.setVisibility(View.VISIBLE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_ulsan.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        daejeon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    daejeon.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_daejeon.setVisibility(View.VISIBLE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_daejeon.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        gwangju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    gwangju.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_gwangju.setVisibility(View.VISIBLE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_gwangju.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        jeju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    jeju.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_jeju.setVisibility(View.VISIBLE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_busan.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_jeju.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        busan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    busan.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_busan.setVisibility(View.VISIBLE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);
                    detail_daegu.setVisibility(View.GONE);

                    check = true;
                } else {
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_busan.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        daegu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    daegu.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    busan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gwangju.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    daejeon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    ulsan.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    choongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonnam.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    jeonbuk.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gangwon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    incheon.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    gyeongi.setBackgroundColor(Color.parseColor("#FFFFFF"));

                    detail_daegu.setVisibility(View.VISIBLE);
                    detail_busan.setVisibility(View.GONE);
                    detail_jeju.setVisibility(View.GONE);
                    detail_gwangju.setVisibility(View.GONE);
                    detail_daejeon.setVisibility(View.GONE);
                    detail_ulsan.setVisibility(View.GONE);
                    detail_choongnam.setVisibility(View.GONE);
                    detail_choongbuk.setVisibility(View.GONE);
                    detail_gyeongnam.setVisibility(View.GONE);
                    detail_gyeongbuk.setVisibility(View.GONE);
                    detail_jeonnam.setVisibility(View.GONE);
                    detail_jeonbuk.setVisibility(View.GONE);
                    detail_gangwon.setVisibility(View.GONE);
                    detail_incheon.setVisibility(View.GONE);
                    detail_gyeongi.setVisibility(View.GONE);
                    detail_seoul.setVisibility(View.GONE);

                    check = true;
                } else {
                    daegu.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_daegu.setVisibility(View.GONE);
                    check = false;
                }
            }
        });
    }
}