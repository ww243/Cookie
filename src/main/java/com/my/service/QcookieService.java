package com.my.service;

import com.my.entity.Cookie;

import javax.swing.*;
import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/08 11:23
 */
public interface QcookieService {
    //模糊查询
    List<Cookie> QselectKey(String content,Integer pageNow,Integer size);
    //查询所有
    List<Cookie> QselectAll(Integer pageNow,Integer size);

    void redis(String content);

}
