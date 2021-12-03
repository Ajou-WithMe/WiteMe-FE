package com.example.withme.bulletin;

import static android.content.Context.MODE_PRIVATE;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.withme.MainActivity;
import com.example.withme.R;
import com.example.withme.retorfit.RetrofitAPI;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BottomSheetDialogBoard extends BottomSheetDialogFragment {

    Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl("http://withme-lb-1691720831.ap-northeast-2.elb.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create()) //gson converter 생성, gson은 JSON을 자바 클래스로 바꾸는데 사용된다.
            .build();
    RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

    MainActivity activity;


    private View view;
    private Dialog dialog;

    private ImageButton findProtection, revisePost, deletePost;
    private String accessToken;
    private long id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sf = getActivity().getSharedPreferences("storeAccessToken", MODE_PRIVATE);
        accessToken = sf.getString("AccessToken", "");

        dialog = new Dialog(getContext());

        view = inflater.inflate(R.layout.bottom_sheet_layout_board, container, false);

        findProtection = (ImageButton) view.findViewById(R.id.findProtection);
        revisePost = (ImageButton) view.findViewById(R.id.revisePost);
        deletePost = (ImageButton) view.findViewById(R.id.deletePost);

        findProtection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });

        revisePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    id = bundle.getLong("id"); //Name 받기.
                    Log.e("dialog_board", String.valueOf(id));
                }
                retrofitAPI.getPostUpdateBase(accessToken, id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                boolean success = jsonObject.getBoolean("success");

                                if (success == true) {
                                    JSONObject data = jsonObject.getJSONObject("data");

                                    String title = data.getString("title");
                                    String location = data.getString("location");
                                    String activityRadius = data.getString("activityRadius");
                                    String description = data.getString("description");
                                    int contact = data.getInt("contact");
                                    String content = data.getString("content");
                                    String protection = data.getString("protection");
                                    int state = data.getInt("state");
                                    String createdAt = data.getString("createdAt");

                                    Bundle bundle = new Bundle(); // 번들을 통해 값 전달

                                    //번들에 넘길 값 저장
                                    bundle.putString("title",title);
                                    bundle.putString("location",location);
                                    bundle.putString("activityRadius",activityRadius);
                                    bundle.putString("description",description);
                                    bundle.putInt("contact",contact);
                                    bundle.putString("content",content);
                                    bundle.putString("protection",protection);
                                    bundle.putInt("state",state);
                                    bundle.putString("createdAt",createdAt);

                                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    ReviseBulletin reviseBulletin = new ReviseBulletin();//프래그먼트2 선언
                                    reviseBulletin.setArguments(bundle);//번들을 프래그먼트2로 보낼 준비
                                    transaction.replace(R.id.fragment_container, reviseBulletin);
                                    transaction.commit();

                                    dismiss();
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

                    }
                });
            }
        });

        deletePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    id = bundle.getLong("id"); //Name 받기.
                    Log.e("dialog_board", String.valueOf(id));
                }
                retrofitAPI.deletePostById(accessToken, id).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                boolean success = jsonObject.getBoolean("success");
                                Log.e("deletePostById", jsonObject.toString());

                                if (success == true) {
                                    String data = jsonObject.getString("data");

                                    Log.e("deletePostById", data);
                                    Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();

                                    Fragment fragment = new Bulletin5();
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fmt = fm.beginTransaction();

                                    fmt.replace(R.id.fragment_container, fragment).commit();

                                    dismiss();
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

                    }
                });
            }
        });
        return view;
    }

    private void openDialog() {
        dialog.setContentView(R.layout.find_protection_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button ok = dialog.findViewById(R.id.understand);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = getArguments();
                if (bundle != null) {
                    id = bundle.getLong("id"); //id 받기.
                }

                HashMap<String, Object> input = new HashMap<>();

                input.put("id", id);
                input.put("state", 1);

                retrofitAPI.updatePostState(accessToken, input).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().string());
                                boolean success = jsonObject.getBoolean("success");

                                if (success == true) {
                                    Fragment fragment = new Bulletin5();
                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fmt = fm.beginTransaction();

                                    fmt.replace(R.id.fragment_container, fragment).commit();
                                    dismiss();
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

                    }
                });
                dialog.dismiss();

            }
        });
        dialog.show();
    }
}
