package com.example.drum.hulixia.model;

import com.example.drum.hulixia.model.inter.IUser;

/**
 * Created by hulixia on 2016/7/1.
 * 用户数据结构
 */
public class UserModel implements IUser {
    private String userName;
    private String userAge;

    public UserModel(String userName, String userAge) {
        this.userName = userName;
        this.userAge = userAge;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    @Override
    public int checkUserValidity(String name, String pas) {
        return 0;
    }
}
