package com.my.service;

import com.my.entity.Cookie;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:32
 */
public interface CookieService {
    //查所有
    List<Cookie> selectAll();
    //根据id获取
    Cookie selectByid(String id);
    //添加
    void insertCookie(Cookie cookie);
    //修改
    void updateCookie(Cookie cookie);
    //删除
    void deleteCookie(String id);
    //清空索引
    void closeIndex();
    //重建索引
    void addIndex();
}
