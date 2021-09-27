package com.example.withme;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kakao.auth.ISessionCallback;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

public class KakaoLogin_SignUp1 extends Activity {

    public static class SessionCallback implements ISessionCallback {
        private Context mContext;
        private SignUpActivity1 signUpActivity1;

        public SessionCallback(Context context, SignUpActivity1 activity) {
            this.mContext = context;
            this.signUpActivity1 = activity;
        }

        @Override
        public void onSessionOpened() {
            requestMe();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(mContext, "KaKao 로그인 오류가 발생했습니다. " + exception.toString(), Toast.LENGTH_SHORT).show();
        }

        protected void requestMe() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    signUpActivity1.directToSecondActivitySignUp(false);
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Log.e("uid", String.valueOf(result.getId()));
                    Log.e("nickname", result.getKakaoAccount().getProfile().getNickname());
                    Log.e("email", result.getKakaoAccount().getEmail());
                    signUpActivity1.directToSecondActivitySignUp(true);
                }
            });
        }
    }
}
