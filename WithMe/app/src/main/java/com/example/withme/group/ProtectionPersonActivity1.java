package com.example.withme.group;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.example.withme.retorfit.UploadImage;
import com.example.withme.user.SignUpActivity4_1;
import com.example.withme.user.WebViewActivity;
import com.naver.maps.map.style.sources.GeoJsonSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProtectionPersonActivity1 extends AppCompatActivity {

    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private static final int PASSWORD_ACTIVITY = 20000;
    private final int GET_GALLERY_IMAGE = 200;

    private EditText protectionName, protectionID, protectionDetailAddress;
    private CircleImageView profileImage;
    private Dialog dialog, reDialog;
    private TextView tvPassword, tvAddress, reviseProtectionPerson, close;
    private ImageButton deleteProtectionPerson;
    private LinearLayout passwordLayout, addressLayout;
    private Uri selectedImageUri;

    String newName, fullAddress, detailAddress, accessToken, id, uid,
            name, beforePassword, address, selectedImagePath, imageFromServer;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protection_person1);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        uid = intent.getStringExtra("uid");

        SharedPreferences sf = getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        retrofitAPI.getProtectionDetail(accessToken, uid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            String profile = data.getString("profileImg");
                            String id = data.getString("email");
                            Glide.with(getApplicationContext()).load(profile).into(profileImage);
                            Log.e("ProtectionDetail", data.toString());
                            protectionID.setText(id);
                        } else {
                            Log.e("ProtectionDetail", String.valueOf(false));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("ProtectionDetail", String.valueOf(false));
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("getProtectionDetail", t.getMessage());
            }
        });

        protectionName = (EditText) findViewById(R.id.name);
        protectionID = (EditText) findViewById(R.id.id);
        protectionDetailAddress = (EditText) findViewById(R.id.detailAddress);

        deleteProtectionPerson = (ImageButton) findViewById(R.id.deleteProtectionPerson);

        profileImage = (CircleImageView) findViewById(R.id.profileImage);

        tvPassword = (TextView) findViewById(R.id.tvPassword);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        reviseProtectionPerson = (TextView) findViewById(R.id.reviseProtectionPerson);
        close = findViewById(R.id.close);

        passwordLayout = (LinearLayout)findViewById(R.id.password);
        addressLayout = (LinearLayout) findViewById(R.id.addressLayout);

        protectionID.setFocusable(false);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProtectionPersonActivity1.this, MainActivity.class);
                startActivity(intent);
            }
        });

        deleteProtectionPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new Dialog(ProtectionPersonActivity1.this);
                openDialog();
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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        protectionName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newName = protectionName.getText().toString().trim();
            }
        });

        protectionDetailAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                detailAddress = protectionDetailAddress.getText().toString().trim();
                fullAddress = address + ", " + detailAddress;
            }
        });

        reviseProtectionPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(selectedImagePath);
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
                MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                retrofitAPI.uploadImage(accessToken, body).enqueue(new Callback<UploadImage>() {
                    @Override
                    public void onResponse(Call<UploadImage> call, Response<UploadImage> response) {
                        UploadImage data = response.body();

                        if(response.isSuccessful()) {
                            Log.e("make Profile", selectedImagePath);
                            Log.e("make Profile", data.getData());
                            if (!data.getData().equals("이미지 파일이 아닙니다.")) {
                                imageFromServer = data.getData();

                                HashMap<String, Object> input = new HashMap<>();
                                input.put("name", newName);
                                input.put("uid", uid);
                                input.put("profileImg", imageFromServer);
                                input.put("pwd", beforePassword);
                                input.put("address", fullAddress);

                                retrofitAPI.updateProtectionProfile(accessToken, input).enqueue(new Callback<ResponseBody>() {
                                    @Override
                                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                        if (response.isSuccessful()) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response.body().string());
                                                boolean success = jsonObject.getBoolean("success");

                                                if (success == true) {
                                                    Intent intent = new Intent(ProtectionPersonActivity1.this, MainActivity.class);
                                                    String data = jsonObject.getString("data");
                                                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                                                    startActivity(intent);
                                                } else {
                                                    String data = jsonObject.getString("data");
                                                    Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (IOException | JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                                        Log.e("create Party", t.getMessage());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<UploadImage> call, Throwable t) {
                        Log.e("make Profile", t.getMessage());
                    }
                });
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

    private void reOpenDialog() {
        reDialog = new Dialog(ProtectionPersonActivity1.this);
        reDialog.setContentView(R.layout.protection_person_re_delete_dialog);
        reDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = reDialog.findViewById(R.id.yes);
        Button no = reDialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofitAPI.deleteProtection(accessToken, uid).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                boolean success = jsonObject.getBoolean("success");
                                reDialog.dismiss();

                                if (success == true) {
                                    String data = jsonObject.getString("data");
                                    Log.e("deleteProtection", data);
                                    Toast.makeText(getApplicationContext(), "피보호자 계정이 삭제되었습니다", Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(ProtectionPersonActivity1.this, MainActivity.class);
//                                    startActivity(intent);
                                } else {
                                    Log.e("deleteProtection", jsonObject.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("deleteProtection", "전송 실패");
                    }
                });
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reDialog.dismiss();
            }
        });
        reDialog.show();
    }

    private void openDialog() {
        dialog.setContentView(R.layout.protection_person_delete_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button yes = dialog.findViewById(R.id.yes);
        Button no = dialog.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reOpenDialog();
                dialog.dismiss();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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