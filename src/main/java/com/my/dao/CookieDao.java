package com.my.dao;

import com.my.entity.Cookie;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:28
 */
public interface CookieDao {
    //查所以
    List<Cookie> selectAll();
    //根据id查
    Cookie selectByid(String id);
    //添加
    void insertCookie(Cookie cookie);
    //修改
    void updateCookie(Cookie cookie);
    //删除
    void deleteCookie(String id);
}
