package com.example.withme.bulletin;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.airbnb.lottie.L;
import com.example.withme.R;

public class Bulletin2 extends Fragment {

    private boolean check = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bulletin2, container, false);
        // Inflate the layout for this fragment
        ConstraintLayout seoul = (ConstraintLayout) rootView.findViewById(R.id.seoul);
        ConstraintLayout incheon = (ConstraintLayout) rootView.findViewById(R.id.incheon);
        ConstraintLayout gyeongi = (ConstraintLayout) rootView.findViewById(R.id.gyeongi);
        ConstraintLayout gangwon = (ConstraintLayout) rootView.findViewById(R.id.gangwon);
        ConstraintLayout jeonbuk = (ConstraintLayout) rootView.findViewById(R.id.jeonbuk);
        ConstraintLayout jeonnam = (ConstraintLayout) rootView.findViewById(R.id.jeonnam);
        ConstraintLayout gyeongbuk = (ConstraintLayout) rootView.findViewById(R.id.gyeongbuk);
        ConstraintLayout gyeongnam = (ConstraintLayout) rootView.findViewById(R.id.gyeongnam);
        ConstraintLayout choongbuk = (ConstraintLayout) rootView.findViewById(R.id.choongbuk);
        ConstraintLayout choongnam = (ConstraintLayout) rootView.findViewById(R.id.choongnam);
        ConstraintLayout ulsan = (ConstraintLayout) rootView.findViewById(R.id.ulsan);
        ConstraintLayout daejeon = (ConstraintLayout) rootView.findViewById(R.id.daejeon);
        ConstraintLayout gwangju = (ConstraintLayout) rootView.findViewById(R.id.gwangju);
        ConstraintLayout jeju = (ConstraintLayout) rootView.findViewById(R.id.jeju);
        ConstraintLayout busan = (ConstraintLayout) rootView.findViewById(R.id.busan);
        ConstraintLayout daegu = (ConstraintLayout) rootView.findViewById(R.id.daegu);

        ConstraintLayout detail_seoul = (ConstraintLayout) rootView.findViewById(R.id.detail_seoul);
        ConstraintLayout detail_gyeongi = (ConstraintLayout) rootView.findViewById(R.id.detail_gyeongi);
        ConstraintLayout detail_incheon = (ConstraintLayout) rootView.findViewById(R.id.detail_incheon);
        ConstraintLayout detail_gangwon = (ConstraintLayout) rootView.findViewById(R.id.detail_ganwon);
        ConstraintLayout detail_jeonbuk = (ConstraintLayout) rootView.findViewById(R.id.detail_jeonbuk);
        ConstraintLayout detail_jeonnam = (ConstraintLayout) rootView.findViewById(R.id.detail_jeonnam);
        ConstraintLayout detail_gyeongbuk = (ConstraintLayout) rootView.findViewById(R.id.detail_gyeonbuk);
        ConstraintLayout detail_gyeongnam = (ConstraintLayout) rootView.findViewById(R.id.detail_gyeongnam);
        ConstraintLayout detail_choongbuk = (ConstraintLayout) rootView.findViewById(R.id.detail_choongbuk);
        ConstraintLayout detail_choongnam = (ConstraintLayout) rootView.findViewById(R.id.detail_choongnam);
        ConstraintLayout detail_ulsan = (ConstraintLayout) rootView.findViewById(R.id.detail_ulsan);
        ConstraintLayout detail_daejeon = (ConstraintLayout) rootView.findViewById(R.id.detail_daejeon);
        ConstraintLayout detail_gwangju = (ConstraintLayout) rootView.findViewById(R.id.detail_gwangju);
        ConstraintLayout detail_jeju = (ConstraintLayout) rootView.findViewById(R.id.detail_jeju);
        ConstraintLayout detail_busan = (ConstraintLayout) rootView.findViewById(R.id.detail_busan);
        ConstraintLayout detail_daegu = (ConstraintLayout) rootView.findViewById(R.id.detail_daegu);


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
        return rootView;
    }
}