package com.my.service;

import com.my.entity.User;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:31
 */
public interface UserService {
    //登录
    User login(String username);

    void insertUser(User user);
}
