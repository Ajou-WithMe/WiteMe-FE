package com.example.withme.intro;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.withme.intro.Fragment_1;
import com.example.withme.intro.Fragment_2;
import com.example.withme.intro.Fragment_3;

public class MyAdapter extends FragmentStateAdapter {

    public int mCount;

    public MyAdapter(FragmentActivity fa, int count) {
        super(fa);
        mCount = count;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        int index = getRealPosition(position);

        if (index == 0) {
            Log.e("fragment 1", "시작");
            return new Fragment_1();
        }
        else if(index==1) {
            Log.e("fragment 2", "시작");
            return new Fragment_2();
        }
        else {
            Log.e("fragment 3", "시작");
            return new Fragment_3();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }



    public int getRealPosition(int position) {
        return position % mCount;
    }
}
