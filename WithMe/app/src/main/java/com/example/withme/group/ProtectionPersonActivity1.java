package com.example.withme.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.withme.R;
import com.example.withme.user.SignUpActivity4_1;
import com.example.withme.user.WebViewActivity;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProtectionPersonActivity1 extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int PASSWORD_ACTIVITY = 20000;
    private final int GET_GALLERY_IMAGE = 200;

    private EditText protectionName;
    private CircleImageView profileImage;
    private TextView tvPassword, tvAddress, reviseProtectionPerson;
    private LinearLayout passwordLayout, addressLayout;
    private Uri selectedImageUri;

    String name, beforePassword, address, selectedImagePath, imageFromServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection_person1);

        protectionName = (EditText) findViewById(R.id.name);

        profileImage = (CircleImageView) findViewById(R.id.profileImage);

        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        reviseProtectionPerson = (TextView) findViewById(R.id.reviseProtectionPerson);

        passwordLayout = (LinearLayout)findViewById(R.id.password);
        addressLayout = (LinearLayout) findViewById(R.id.addressLayout);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        reviseProtectionPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProtectionPersonActivity1.this, "피보호자 정보가 수정되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        passwordLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProtectionPersonActivity1.this, GroupActivity4.class);
                startActivityForResult(intent, PASSWORD_ACTIVITY);
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProtectionPersonActivity1.this, WebViewActivity.class);
                startActivityForResult(intent, SEARCH_ADDRESS_ACTIVITY);
            }
        });

        Intent intent = getIntent();
        name = intent.getStringExtra("name");

        protectionName.setText(name);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        switch (requestCode) {
            case PASSWORD_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("password");
                    beforePassword = data;
                    if (data != null) {
                        tvPassword.setText("******");
                        tvPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        tvPassword.setTextColor(Color.parseColor("#333333"));
                    }
                }
                break;
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        tvAddress.setText(data);
                        tvAddress.setTextColor(Color.parseColor("#333333"));

                        address = tvAddress.getText().toString();
                    }
                }
                break;

            case GET_GALLERY_IMAGE:
                if (resultCode == RESULT_OK && intent != null && intent.getData() != null) {
                    selectedImageUri = intent.getData();
                    profileImage.setImageURI(selectedImageUri);
                    selectedImagePath = uri2path(this, selectedImageUri);
                }
                break;
        }
    }
    //Uri -> Path(파일경로)
    public static String uri2path(Context context, Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };

        Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
        cursor.moveToNext();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
        Uri uri = Uri.fromFile(new File(path));

        cursor.close();
        return path;
    }
}