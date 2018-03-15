package com.sariel.mvp.presenter;

import android.os.Handler;

import com.sariel.mvp.model.User;
import com.sariel.mvp.model.UserModel;
import com.sariel.mvp.view.LoginView;

/**
 * Created by LiangCheng on 2018/3/15.
 */

public class LoginPresenterImpl implements LoginPresenter {
    User user;
    LoginView loginView;
    Handler handler = new Handler();

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        initUser();
    }

    private void initUser() {
        user = new UserModel("123", "123");
    }

    @Override
    public void login(String userName, String passWord) {
        Boolean isLoginSuccess = false;
        final int code = user.checkUser(userName, passWord);
        if (code != -1) {
            isLoginSuccess = true;
        }
        final boolean result = isLoginSuccess;
        handler.postAtTime(new Runnable() {
            @Override
            public void run() {
                loginView.loginResult(result, code);
            }
        }, 5000);
    }

    @Override
    public void clear() {
        loginView.clear();
    }

    @Override
    public void loadingVis(boolean isVis) {
        loginView.loadingVis(isVis);
    }
}
