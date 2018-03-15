package com.sariel.mvp.view;

/**
 * Created by LiangCheng on 2018/3/15.
 */

public interface LoginView {
    void clear();

    void loginResult(Boolean result, int code);

    void loadingVis(Boolean isVis);
}
