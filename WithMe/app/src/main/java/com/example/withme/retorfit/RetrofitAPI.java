package com.example.withme.retorfit;

import android.location.Location;

import org.json.JSONArray;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
    Call<ResponseBody> getProfile(@Header("AccessToken") String accessToken);

    @PUT("/user/mypage")
    Call<ResponseBody> changeProfile(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/party/all")
    Call<ResponseBody> getAllParty(@Header("AccessToken") String accessToken);

    @GET("/party")
    Call<ResponseBody> getParty(@Header("AccessToken") String accessToken, @Query("code") String code);

    @PUT("/party")
    Call<ResponseBody> updateParty(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @POST("/party")
    Call<ResponseBody> postCreateParty(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/party/detail")
    Call<ResponseBody> getPartyDetail(@Header("AccessToken") String accessToken, @Query("code") String code);

    @POST("/partyMember")
    Call<ResponseBody> postPartyMember(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @DELETE("/partyMember")
    Call<ResponseBody> exitPartyMember(@Header("AccessToken") String accessToken, @Query("code") String code);

    @GET("/partyMember/allProtection")
    Call<ResponseBody> getAllprotection(@Header("AccessToken") String accessToken);

    @POST("/partyMember/approval")
    Call<ResponseBody> approvalPartyMember(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/protection")
    Call<ResponseBody> getProtectionDetail(@Header("AccessToken") String accessToken, @Query("uid") String uid);

    @PUT("/protection")
    Call<ResponseBody> updateProtectionProfile(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @DELETE("/protection/quit")
    Call<ResponseBody> deleteProtection(@Header("AccessToken") String accessToken, @Query("uid") String uid);

    @POST("/protection/safemove")
    Call<ResponseBody> changeSafeMove(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @POST("/notification/out")
    Call<ResponseBody> outOfSafeZoneNotification(@Header("AccessToken") String accessToken);

    @Multipart
    @POST("/file/profile")
    Call<UploadImage> uploadImage(@Header("AccessToken") String accessToken, @Part MultipartBody.Part postImg);

    @Multipart
    @POST("/file/post")
    Call<ResponseBody> uploadPostFile(@Header("AccessToken") String accessToken, @Part MultipartBody.Part postImg);

    @POST("/zone_manage/certify_zone/")
    Call<ResponseBody> safeZoneVerification(@Header("AccessToken")String accessToken, @Body HashMap<String, Object> param);

    @POST("/zone_manage/certify_put/")
    Call<ResponseBody> safeZoneInsert(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @POST("/zone_manage/certify_gps/")
    Call<ResponseBody> newDataGPS(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @POST("/location")
    Call<ResponseBody> saveLocation(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/location/current")
    Call<ResponseBody> getUserCurrentLocation(@Header("AccessToken") String accessToken, @Query("uid") String uid);

    @GET("/safeZone")
    Call<ResponseBody> findSafeZone(@Header("AccessToken") String accessToken, @Query("uid") String uid);

    @GET("safeZone/missing")
    Call<ResponseBody> findVisitOftenAndDistanceAfterMissing (@Header("AccessToken") String accessToken, @Query("uid") String uid);

    @POST("/board")
    Call<ResponseBody> savePost(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/board")
    Call<ResponseBody> getPostDetail(@Header("AccessToken") String accessToken, @Query("id") long id);

    @DELETE("/board")
    Call<ResponseBody> deletePostById(@Header("AccessToken") String accessToken, @Query("id") long id);

    @GET("/board/page")
    Call<ResponseBody> postPaging(@Header("AccessToken") String accessToken,
                                  @Query("page") long page, @Query("location") String location);

    @PUT("/board")
    Call<ResponseBody> updatePost(@Header("AccessToken") String accessToken, @Query("id") long id, @Body HashMap<String, Object> param);

    @GET("/board/my")
    Call<ResponseBody> getMyPost(@Header("AccessToken") String accessToken);

    @POST("/board/state")
    Call<ResponseBody> updatePostState(@Header("AccessToken") String accessToken, @Body HashMap<String, Object> param);

    @GET("/board/update")
    Call<ResponseBody> getPostUpdateBase(@Header("AccessToken") String accessToken, @Query("id") long id);
}
