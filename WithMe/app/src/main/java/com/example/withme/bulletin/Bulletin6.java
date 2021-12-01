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
import android.widget.TextView;

import com.example.withme.R;
import com.example.withme.group.BottomSheetDialogMain;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.StringTokenizer;

public class Bulletin6 extends Fragment {

    private ImageButton option;
    private long id;
    private String title, description, contents, radius, name, phone, createdAt;
    private TextView postTitle, clothes, activityRadius, content, nameAge, phoneNumber, finalLocation, date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_bulletin6, container, false);

        postTitle = (TextView) rootView.findViewById(R.id.postTitle);
        clothes = (TextView) rootView.findViewById(R.id.clothes);
        content = (TextView) rootView.findViewById(R.id.content);
        nameAge = (TextView) rootView.findViewById(R.id.nameAge);
        activityRadius = (TextView) rootView.findViewById(R.id.activityRadius);
        phoneNumber = (TextView) rootView.findViewById(R.id.phoneNumber);
        finalLocation = (TextView) rootView.findViewById(R.id.finalLocation);
        date = (TextView) rootView.findViewById(R.id.date);


        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getLong("id"); //id 받기.
            title = bundle.getString("title");
            name = bundle.getString("name");
            description = bundle.getString("description");
            contents = bundle.getString("content");
            createdAt = bundle.getString("createdAt");
            radius = bundle.getString("activityRadius");
            phone = bundle.getString("phone");
        }

        StringTokenizer st = new StringTokenizer(createdAt,"T");
        String tmpDate = st.nextToken();
        tmpDate = tmpDate.replaceAll("-",".");
        String tokenized = tmpDate.substring(2, 10);
        Log.e("tokenizer", tokenized);


        date.setText(tokenized);
        postTitle.setText(title);
        clothes.setText(description);
        activityRadius.setText(radius);
        content.setText(contents);
        nameAge.setText(name);
        phoneNumber.setText(phone);
        finalLocation.setText("아직 구현 못했습니당");



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