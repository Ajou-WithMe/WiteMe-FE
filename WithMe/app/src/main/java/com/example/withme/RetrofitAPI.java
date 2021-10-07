package com.example.withme;

import android.graphics.PostProcessor;
import android.location.Location;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST("/location")
    Call<Location> postData(@Body HashMap<String, Object> param);

    @POST("/user/signup/certification")
    Call<PostEmail> postEmail(@Body HashMap<String, Object> param);

    @POST("/user/signup/email")
    Call<EmailSignUp> postEmailSignUp(@Body HashMap<String, Object> param);

    @POST("/user/login/email")
    Call<LoginEmail> postLoginEmail(@Body HashMap<String, Object> param);

    @POST("/user/signup/duplicate")
    Call<SignUpDuplicate> postSignupDuplicate(@Body HashMap<String, Object> param);

    @GET("/user/mypage")
    Call<GetProfile> getProfile(@Header("AccessToken") String accessToken);

    @Multipart
    @POST("/file/profile")
    Call<UploadImage> uploadImage(@Header("AccessToken") String accessToken, @Part MultipartBody.Part postImg);

}
