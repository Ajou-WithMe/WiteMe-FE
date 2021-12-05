package com.example.withme.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.intro.DescriptionActivity;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

public class Settings extends Fragment {

    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Button logout = (Button) rootView.findViewById(R.id.logout);
        Intent intent1 = new Intent(getContext(), DescriptionActivity.class);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
                SharedPreferences.Editor editor1 = pref.edit();
                editor1.remove("AccessToken");
                editor1.commit();
                activity.stopLocationService();
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        startActivity(intent1);
                    }
                });
                startActivity(intent1);
            }
        });
        return rootView;
    }
}