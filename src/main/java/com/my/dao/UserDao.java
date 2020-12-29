package com.my.dao;

import com.my.entity.User;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:27
 */
public interface UserDao {
    //denglu
    User login(String username);

    void insert(User user);
}
