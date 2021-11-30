package com.example.withme.bulletin;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.withme.MainActivity;
import com.example.withme.R;

public class Bulletin1 extends Fragment {

    MainActivity activity;

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

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_bulletin1, container, false);
        // Inflate the layout for this fragment

        TextView region = (TextView) rootView.findViewById(R.id.region);

        ImageButton write = (ImageButton) rootView.findViewById(R.id.write);

//        Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
//        ArrayAdapter adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(), R.array.order, R.layout.spinner_item);
//        spinner.setAdapter(adapter);
//        spinner.setSelection(0);

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });

        region.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(2);
            }
        });
        return rootView;
    }
}