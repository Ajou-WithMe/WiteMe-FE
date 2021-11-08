package com.example.withme.bulletin;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
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
        LinearLayout detail_seoul = (LinearLayout) rootView.findViewById(R.id.detail_seoul);
        ImageButton click_seoul = (ImageButton) rootView.findViewById(R.id.seoul_click);

        click_seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    seoul.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    detail_seoul.setVisibility(View.VISIBLE);
                    check = true;
                } else {
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_seoul.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        seoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == false) {
                    seoul.setBackgroundColor(Color.parseColor("#F6F6F6"));
                    detail_seoul.setVisibility(View.VISIBLE);
                    check = true;
                } else {
                    seoul.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    detail_seoul.setVisibility(View.GONE);
                    check = false;
                }
            }
        });

        return rootView;
    }
}