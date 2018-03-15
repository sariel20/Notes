package com.sariel.mvp.model;

/**
 * Created by LiangCheng on 2018/3/15.
 */

public interface User {
    String getName();

    String getPwd();

    int checkUser(String name, String pwd);
}
