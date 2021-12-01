package com.example.withme.bulletin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.withme.R;
import com.example.withme.group.BottomSheetDialogMain;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Bulletin6 extends Fragment {

    private ImageButton option;
    private long id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bulletin6, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("id"); //Name 받기.
            Log.e("bulletin6", String.valueOf(id));
        }

        option = (ImageButton) rootView.findViewById(R.id.option);

        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle result = new Bundle();
                result.putLong("id", id);

                BottomSheetDialogBoard bottomSheetDialogBoard = new BottomSheetDialogBoard();
                bottomSheetDialogBoard.show(getActivity().getSupportFragmentManager(), "fuck");
                bottomSheetDialogBoard.setArguments(result);
            }
        });

        return rootView;
    }
}