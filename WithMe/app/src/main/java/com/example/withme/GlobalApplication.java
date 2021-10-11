package com.example.withme;

import android.app.Application;
import android.content.Context;
import android.provider.Settings;

import androidx.annotation.Nullable;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;
import com.kakao.auth.KakaoSDK;

public class GlobalApplication extends Application {

    private static volatile GlobalApplication instance = null;

    private static class KakaoSDKAdapter extends KakaoAdapter {

        // 카카오 로그인 세션을 불러올 때의 설정값을 설정하는 부분
        @Override
        public ISessionConfig getSessionConfig() {

            return new ISessionConfig() {
                @Override
                public AuthType[] getAuthTypes() {
                    return new AuthType[] {AuthType.KAKAO_ACCOUNT};
                }

                @Override
                public boolean isUsingWebviewTimer() {
                    return false;
                    // SDK 로그인 시 사용되는 WebView 에서 pause 와 resume 시에 Timer 를 설정하여 CPU 소모를 절약한다.
                    // true 를 리턴할 경우 webView 로그인을 사용하는 화면에서 모든 webView 에 onPause, onResume 시에 Time r를 설정해주어야 한다.
                    // 지정하지 않을 시 false 로 설정된다.
                }

                @Override
                public boolean isSecureMode() {
                    return false;
                    // 로그인 시 access token과 refresh 토큰을 저장할 때의 암호화 여부를 결정한다.
                }

                @Override
                public ApprovalType getApprovalType() {
                    return ApprovalType.INDIVIDUAL;
                    // 일반 사용자가 아닌 kakao 와 제휴된 앱에서만 사용하는 값으로, 값을 채워주지 않을 경우 ApprovalType.INDIVIDUAL 값을 사용하게 된다.
                }

                @Override
                public boolean isSaveFormData() {
                    return false;
                    // Kakao SDK 에서 사용되는 WebView 에서 email 입력폼의 데이터를 저장할지 여부를 결정한다.
                    // true일 경우, 다음번에 다시 로그인 시 email 폼을 누르면 이전에 입력했던 이메일이 나타난다.
                }
            };
        }

        @Override
        public IApplicationConfig getApplicationConfig() {
            return new IApplicationConfig() {
                @Override
                public Context getApplicationContext() {
                    return GlobalApplication.getGlobalApplicationContext();
                }
            };
        }
    }

    public static GlobalApplication getGlobalApplicationContext() {
        if(instance == null) {
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        KakaoSDK.init(new KakaoSDKAdapter());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        instance = null;
    }
}