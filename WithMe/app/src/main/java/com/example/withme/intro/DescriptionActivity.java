package com.example.withme.intro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.withme.R;
import com.example.withme.user.LoginActivity;

public class DescriptionActivity extends AppCompatActivity {

    private ViewPager2 mPager;
    private Button startButton;
    private FragmentStateAdapter pagerAdapter;
    private int num_page = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        startButton = (Button)findViewById(R.id.startButton);
        Intent intent = new Intent(this, LoginActivity.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        // 가로 슬라이드 뷰 Fragment

        // ViewPager
        mPager = findViewById(R.id.viewpager);

        // Adapter
        pagerAdapter = new MyAdapter(this, num_page);
        mPager.setAdapter(pagerAdapter);

        // ViewPager Setting
        mPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        mPager.setCurrentItem(1);
        mPager.setOffscreenPageLimit(1);

        mPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    mPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {

                }
            }
        });
    }
}