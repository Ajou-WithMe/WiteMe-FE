package com.example.withme.group;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.kyleduo.switchbutton.SwitchButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomSheetDialogProtection extends BottomSheetDialogFragment {

    // 초기 변수 설정
    private View view;
    private Context context;
    private Dialog dialog;
    private ImageView bottomSheetButton;
    private String accessToken, protectionPersonUid;
    private TextView numGroup, numProtector, addGroup, groupDescription;
    private ScrollView scrollView;
    private ConstraintLayout groupDetail;
    private LinearLayout groupLayout, protectorLayout, protectionPersonLayout;
    private JSONArray data, protectors, protectionPersons;
    private JSONObject groupSpecific;
    ArrayList<CircleImageView> circleImageViews = new ArrayList<>();
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<String> codes = new ArrayList<>();
    ArrayList<String> protectionPersonName = new ArrayList<>();
    ArrayList<String> protectionPersonUids = new ArrayList<>();

    int group_num, protector_num, protectionPerson_num;
    int i=0;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    // 인터페이스 변수

    // 바텀 시트 숨기기 변수
    private Button btn_hide_bt_sheet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottom_sheet_layout, container, false);

        addGroup = (TextView) view.findViewById(R.id.addGroup);
        groupDescription = (TextView) view.findViewById(R.id.groupDescription);
        numGroup = (TextView) view.findViewById(R.id.numGroup);
        numProtector = (TextView) view.findViewById(R.id.numProtector);

        bottomSheetButton = (ImageView) view.findViewById(R.id.bottomSheetButton);

        groupLayout = (LinearLayout) view.findViewById(R.id.groupLayout);
        protectorLayout = (LinearLayout) view.findViewById(R.id.protectorLayout);
        protectionPersonLayout = (LinearLayout) view.findViewById(R.id.protectionPersonLayout);
        protectionPersonLayout.setOrientation(LinearLayout.HORIZONTAL);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);

        groupDetail = (ConstraintLayout) view.findViewById(R.id.groupDetail);

        scrollView.setVisibility(View.INVISIBLE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            accessToken = bundle.getString("AccessToken");
        }

        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                getActivity().finish();
                getActivity().startActivity(intent);
            }
        });

        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GroupAddActivity1.class);
                startActivity(intent);
            }
        });

        retrofitAPI.getAllParty(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        data = jsonObject.getJSONArray("data");
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            group_num = data.length();
                            numGroup.setText(String.valueOf(group_num));

                            for (i=0; i<group_num; i++) {
                                JSONObject group = data.getJSONObject(i);
                                String name = group.getString("name");
                                Log.e("i", String.valueOf(i) +", " + name);

                                String profile = group.getString("profile");
                                String code = group.getString("code");
                                int type = group.getInt("type");

                                codes.add(code);

                                LinearLayout linearLayout = new LinearLayout(getActivity().getApplicationContext());
                                linearLayout.setId(i);
                                ViewGroup.LayoutParams layout= new LinearLayout.LayoutParams(150, 222);

                                linearLayout.setLayoutParams(layout);
                                linearLayout.setOrientation(LinearLayout.VERTICAL);

                                groupLayout.addView(linearLayout);

                                CircleImageView circleImageView = new CircleImageView(getActivity().getApplicationContext());
                                ViewGroup.LayoutParams circle= new LinearLayout.LayoutParams(150, 150);
                                circleImageView.setLayoutParams(circle);

                                TextView textView = new TextView(getActivity().getApplicationContext());
                                textView.setText(name);
                                textView.setTextSize(16);
                                textView.setTextColor(Color.parseColor("#BDBDBD"));

                                Glide.with(getActivity().getApplicationContext()).load(profile).into(circleImageView);
                                if (type == 2) {
                                    circleImageView.setAlpha(0.8f);
                                    circleImageView.setClickable(false);
                                }

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(69,0,0,0);
                                linearLayout.setLayoutParams(lp);

                                linearLayout.addView(circleImageView);
                                linearLayout.setGravity(Gravity.CENTER);

                                LinearLayout.LayoutParams lp_text = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp_text.setMargins(0,12,0,0);
                                lp_text.gravity = Gravity.CENTER;
                                textView.setLayoutParams(lp_text);
                                linearLayout.addView(textView);
                                textViews.add(textView);
                                circleImageViews.add(circleImageView);

                                linearLayout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        protectionPersonName.clear();
                                        if (type == 2) {

                                        }
                                        else {
                                            groupDescription.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    Intent intent = new Intent(getActivity(), GroupDetailActivity.class);
                                                    intent.putExtra("name", name);
                                                    intent.putExtra("profile", profile);
                                                    intent.putExtra("code", code);
                                                    startActivity(intent);
                                                }
                                            });
                                            scrollView.setVisibility(View.VISIBLE);
                                            protectorLayout.removeAllViews();
                                            protectionPersonLayout.removeAllViews();
                                            for (int j=0; j<circleImageViews.size(); j++) {
                                                circleImageViews.get(j).setBorderWidth(0);
                                                textViews.get(j).setTextColor(Color.parseColor("#BDBDBD"));
                                            }
                                            circleImageViews.get(linearLayout.getId()).setBorderColor(Color.parseColor("#FED537"));
                                            circleImageViews.get(linearLayout.getId()).setBorderWidth(9);

                                            textView.setTextColor(Color.parseColor("#000000"));

                                            retrofitAPI.getPartyDetail(accessToken, codes.get(linearLayout.getId())).enqueue(new Callback<ResponseBody>() {
                                                @Override
                                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                                    if (response.isSuccessful()) {
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response.body().string());
                                                            JSONObject data = jsonObject.getJSONObject("data");

                                                            protectors = data.getJSONArray("protector");
                                                            protectionPersons = data.getJSONArray("protectionPerson");

                                                            boolean success = jsonObject.getBoolean("success");

                                                            Log.e("protector", String.valueOf(protectors));
                                                            Log.e("protectionPerson", String.valueOf(protectionPersons));

                                                            if (success == true) {
                                                                protector_num = protectors.length();
                                                                protectionPerson_num = protectionPersons.length();

                                                                numProtector.setText(String.valueOf(protector_num + protectionPerson_num));

                                                                if ( protectionPerson_num == 0) {

                                                                } else {
                                                                    for (int l = 0; l < protectionPerson_num; l++) {
                                                                        JSONObject protectionPerson = protectionPersons.getJSONObject(l);
                                                                        String name = protectionPerson.getString("name");

                                                                        protectionPersonUid = protectionPerson.getString("uid");
                                                                        String profile = protectionPerson.getString("profile");
                                                                        int type = protectionPerson.getInt("type");
                                                                        int safeMove = protectionPerson.getInt("safemove");

                                                                        protectionPersonName.add(name);
                                                                        protectionPersonUids.add(protectionPersonUid);

                                                                        LinearLayout linearLayoutProtectionPerson = new LinearLayout(getActivity().getApplicationContext());
                                                                        linearLayoutProtectionPerson.setId(l);
                                                                        linearLayoutProtectionPerson.setOrientation(LinearLayout.HORIZONTAL);

                                                                        protectionPersonLayout.addView(linearLayoutProtectionPerson);
                                                                        protectionPersonLayout.setOrientation(LinearLayout.VERTICAL);

                                                                        CircleImageView circleImageViewProtectionPerson = new CircleImageView(getActivity().getApplicationContext());
                                                                        LinearLayout.LayoutParams circle = new LinearLayout.LayoutParams(144, 144);
                                                                        circleImageViewProtectionPerson.setLayoutParams(circle);

                                                                        TextView textViewProtectionPerson = new TextView(getActivity().getApplicationContext());
                                                                        textViewProtectionPerson.setText(name);
                                                                        textViewProtectionPerson.setTextSize(14);
                                                                        textViewProtectionPerson.setTextColor(Color.parseColor("#333333"));

                                                                        Glide.with(getActivity().getApplicationContext()).load(profile).into(circleImageViewProtectionPerson);
                                                                        if (profile.equals("null")) {
                                                                            circleImageViewProtectionPerson.setBackgroundResource(R.drawable.solo_white);
                                                                        }

                                                                        LinearLayout.LayoutParams lpProtectorPerson = new LinearLayout.LayoutParams(
                                                                                1000,
                                                                                144);
                                                                        lpProtectorPerson.setMargins(42,72,0,0);
                                                                        linearLayoutProtectionPerson.setLayoutParams(lpProtectorPerson);

                                                                        LinearLayout.LayoutParams text_protectionPerson = new LinearLayout.LayoutParams(
                                                                                250,
                                                                                ViewGroup.LayoutParams.WRAP_CONTENT);
                                                                        text_protectionPerson.setMargins(36,0,0,0);
                                                                        textViewProtectionPerson.setLayoutParams(text_protectionPerson);
                                                                        text_protectionPerson.gravity = Gravity.CENTER_VERTICAL;

                                                                        linearLayoutProtectionPerson.addView(circleImageViewProtectionPerson);
                                                                        linearLayoutProtectionPerson.addView(textViewProtectionPerson);
                                                                    }

                                                                    setStringArrayPref(getActivity().getApplicationContext(), "name", protectionPersonName);
                                                                    setStringArrayPref(getActivity().getApplicationContext(), "uid", protectionPersonUids);
                                                                }

                                                                for (int k = 0; k < protector_num; k++) {
                                                                    JSONObject protector = protectors.getJSONObject(k);
                                                                    String name = protector.getString("name");
                                                                    Log.e("protector", k + ", " + name);

                                                                    String uid = protector.getString("uid");
                                                                    String profile = protector.getString("profile");
                                                                    int type = protector.getInt("type");

                                                                    LinearLayout linearLayoutProtector= new LinearLayout(getActivity().getApplicationContext());
                                                                    linearLayoutProtector.setId(k);
                                                                    ViewGroup.LayoutParams layout= new LinearLayout.LayoutParams(144, 210);

                                                                    linearLayoutProtector.setLayoutParams(layout);
                                                                    linearLayoutProtector.setOrientation(LinearLayout.VERTICAL);

                                                                    protectorLayout.addView(linearLayoutProtector);

                                                                    CircleImageView circleImageViewProtector = new CircleImageView(getActivity().getApplicationContext());
                                                                    ViewGroup.LayoutParams circle= new LinearLayout.LayoutParams(144, 144);
                                                                    circleImageViewProtector.setLayoutParams(circle);

                                                                    TextView textViewProtector = new TextView(getActivity().getApplicationContext());
                                                                    textViewProtector.setText(name);
                                                                    textViewProtector.setTextSize(14);
                                                                    textViewProtector.setTextColor(Color.parseColor("#333333"));

                                                                    Glide.with(getActivity().getApplicationContext()).load(profile).into(circleImageViewProtector);
                                                                    if (profile.equals("null")) {
                                                                        circleImageViewProtector.setBackgroundResource(R.drawable.solo_white);
                                                                    }

                                                                    LinearLayout.LayoutParams lpProtector = new LinearLayout.LayoutParams(
                                                                            ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                                                    lpProtector.setMargins(48,0,0,0);
                                                                    linearLayoutProtector.setLayoutParams(lpProtector);

                                                                    linearLayoutProtector.addView(circleImageViewProtector);

                                                                    lp_text.gravity = Gravity.CENTER;
                                                                    textViewProtector.setLayoutParams(lp_text);
                                                                    linearLayoutProtector.addView(textViewProtector);
                                                                }
                                                                ImageView addButton = new ImageView(getActivity().getApplicationContext());
                                                                ViewGroup.LayoutParams add= new LinearLayout.LayoutParams(144, 210);
                                                                addButton.setLayoutParams(add);
                                                                addButton.setBackgroundResource(R.drawable.plus);

                                                                LinearLayout.LayoutParams lpProtector = new LinearLayout.LayoutParams(144, 210);
                                                                lpProtector.setMargins(60,0,0,0);
                                                                addButton.setLayoutParams(lpProtector);

                                                                protectorLayout.addView(addButton);

                                                                addButton.setOnClickListener(new View.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(View v) {
                                                                        dialog = new Dialog(getActivity());
                                                                        openDialogCode(code);
                                                                    }
                                                                });

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
                                                    Log.e("party detail", String.valueOf(false));
                                                }
                                            });
                                        }

                                    }
                                });
                            }
                        } else {
                            Log.e("Party", String.valueOf(false));
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
                Log.e("Party", "전송 실패");
            }
        });
        return view;
    }

    private void openDialogCode(String code) {
        dialog.setContentView(R.layout.invite_protector_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button kakaoTalk = dialog.findViewById(R.id.kakaoTalk);

        kakaoTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //클립보드 사용 코드
                ClipboardManager clipboardManager = (ClipboardManager) getActivity().getApplicationContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("CODE", code); //클립보드에 ID라는 이름표로 id 값을 복사하여 저장
                clipboardManager.setPrimaryClip(clipData);
                Log.e("code", code);

                dialog.dismiss();

                openDialogComplete();
            }
        });
        dialog.show();
    }

    private void openDialogComplete() {
        dialog.setContentView(R.layout.code_copy_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button close = dialog.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setStringArrayPref(Context context, String key, ArrayList<String> values) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        JSONArray a = new JSONArray();
        for (int i = 0; i < values.size(); i++) {
            a.put(values.get(i));
        }
        if (!values.isEmpty()) {
            editor.putString(key, a.toString());
        } else {
            editor.putString(key, null);
        }
        editor.apply();
    }
}