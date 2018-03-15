package com.sariel.mvp.model;

/**
 * Created by LiangCheng on 2018/3/15.
 */

public class UserModel implements User {
    private String name;
    private String pwd;

    public UserModel(String name, String pwd) {
        this.name = name;
        this.pwd = pwd;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public int checkUser(String name, String pwd) {
        if (getName().isEmpty() || getPwd().isEmpty()) {
            return -1;
        }
        return 0;
    }
}
