package com.example.withme.bulletin;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.withme.MainActivity;
import com.example.withme.R;

public class MainBulletin extends Fragment {

    MainActivity activity;
    private TextView category, region;
    private static final int SEARCH_LOCATION_ACTIVITY = 300;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_mainbulletin, container, false);
        // Inflate the layout for this fragment

        region = (TextView) rootView.findViewById(R.id.region);
        TextView myPost = (TextView) rootView.findViewById(R.id.myPost);
        category = (TextView) rootView.findViewById(R.id.category);

        ImageButton write = (ImageButton) rootView.findViewById(R.id.write);

        myPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(5);
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });

        category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, SEARCH_LOCATION_ACTIVITY);
            }
        });

        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SelectLocationActivity.class);
                startActivityForResult(intent, SEARCH_LOCATION_ACTIVITY);
            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch(requestCode) {
            case SEARCH_LOCATION_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("category");
                    if (data != null) {
                        category.setText(data);
                        region.setVisibility(View.GONE);


                    }
                }
                break;
        }
    }
}