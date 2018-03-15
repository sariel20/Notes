package com.sariel.mvp.presenter;

/**
 * Created by LiangCheng on 2018/3/15.
 */

public interface LoginPresenter {

    void login(String userName, String passWord);

    void clear();

    void loadingVis(boolean isVis);
}
