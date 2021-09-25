package com.example.withme;

import android.graphics.PostProcessor;
import android.location.Location;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST("/location")
    Call<Location> postData(@Body HashMap<String, Object> param);

    @POST("/user/signup/certification")
    Call<PostEmail> postEmail(@Body HashMap<String, Object> param);

}
