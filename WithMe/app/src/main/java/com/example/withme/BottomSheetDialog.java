package com.example.withme;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.example.withme.group.GroupAddActivity2;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomSheetDialog extends BottomSheetDialogFragment {

    // 초기 변수 설정
    private View view;
    private Context context;
    private String accessToken;
    private TextView numGroup;
    private LinearLayout groupLayout;
    private JSONArray data;
    private ScrollView groupDetail;
    private JSONObject groupSpecific;
    ArrayList<CircleImageView> circleImageViews = new ArrayList<>();
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<String> codes = new ArrayList<>();

    int group_num;
    int i=0;

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
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

        numGroup = (TextView) view.findViewById(R.id.numGroup);
        groupLayout = (LinearLayout) view.findViewById(R.id.groupLayout);
        groupDetail = (ScrollView) view.findViewById(R.id.groupDetail);

        Bundle bundle = getArguments();
        if (bundle != null) {
            accessToken = bundle.getString("AccessToken");
            Log.e("accessToken", accessToken);
        }

        retrofitAPI.getParty(accessToken).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        data = jsonObject.getJSONArray("data");
                        Log.e("data", String.valueOf(data));
                        boolean success = jsonObject.getBoolean("success");

                        if (success == true) {
                            group_num = data.length();
                            Log.e("Party number", String.valueOf(group_num));
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

                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        ViewGroup.LayoutParams.WRAP_CONTENT,
                                        ViewGroup.LayoutParams.WRAP_CONTENT);
                                lp.setMargins(69,0,0,0);
                                linearLayout.setLayoutParams(lp);

                                linearLayout.addView(circleImageView);

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
                                        Log.e("layout", String.valueOf(linearLayout.getId()));
                                        for (int j=0; j<circleImageViews.size(); j++) {
                                            circleImageViews.get(j).setBorderWidth(0);
                                            textViews.get(j).setTextColor(Color.parseColor("#BDBDBD"));
                                        }
                                        circleImageViews.get(linearLayout.getId()).setBorderColor(Color.parseColor("#FED537"));
                                        circleImageViews.get(linearLayout.getId()).setBorderWidth(9);

                                        textView.setTextColor(Color.parseColor("#000000"));
                                        groupDetail.setVisibility(View.VISIBLE);

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
}
