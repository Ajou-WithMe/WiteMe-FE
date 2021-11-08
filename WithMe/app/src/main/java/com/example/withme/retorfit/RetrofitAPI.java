package com.example.withme.retorfit;

import android.location.Location;

import com.example.withme.retorfit.EmailSignUp;
import com.example.withme.retorfit.GetProfile;
import com.example.withme.retorfit.PostEmail;
import com.example.withme.retorfit.SignUpDuplicate;
import com.example.withme.retorfit.UploadImage;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetrofitAPI {

    @POST("/location")
    Call<Location> postData(@Body HashMap<String, Object> param);

    @POST("/user/signup/certification")
    Call<PostEmail> postEmail(@Body HashMap<String, Object> param);

    @POST("/user/signup/email")
    Call<EmailSignUp> postEmailSignUp(@Body HashMap<String, Object> param);

    @POST("/user/signup/existUser")
    Call<ResponseBody> postUID(@Body HashMap<String, Object> param);

    @POST("/user/login/kakao")
    Call <ResponseBody> postLoginKakao(@Body HashMap<String, Object> param);

    @POST("/user/login/email")
    Call<ResponseBody> postLoginEmail(@Body HashMap<String, Object> param);

    @POST("/user/signup/kakao")
    Call<ResponseBody> postKakaoSignUp(@Body HashMap<String, Object> param);

    @POST("/user/signup/duplicate")
    Call<SignUpDuplicate> postSignupDuplicate(@Body HashMap<String, Object> param);

    @POST("/user/login/findEmail")
    Call<ResponseBody> postFindEmail(@Body HashMap<String, Object> param);

    @POST("/user/login/findPwd")
    Call<ResponseBody> postFindPwd(@Body HashMap<String, Object> param);

    @POST("/user/login/findPwd/changePwd")
    Call<ResponseBody> postChangePwd(@Body HashMap<String, Object> param);

    @POST("/user/signup/protection")
    Call <ResponseBody> postProtection(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/user/mypage")
    Call<GetProfile> getProfile(@Header("AccessToken") String accessToken);

    @GET("/party/all")
    Call<ResponseBody> getParty(@Header("AccessToken") String accessToken);

    @POST("/partyMember")
    Call<ResponseBody> postPartyMember(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @Multipart
    @POST("/file/profile")
    Call<UploadImage> uploadImage(@Header("AccessToken") String accessToken, @Part MultipartBody.Part postImg);

    @POST("/party")
    Call<ResponseBody> postCreateParty(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

}
