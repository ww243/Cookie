package com.my;

import com.my.entity.Cookie;
import com.my.service.QcookieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/08 12:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationCookie.class)
public class TestQCookie {

    @Autowired
    private QcookieService qcookieService;

    @Test
    public void text(){
        List<Cookie> list = qcookieService.QselectKey("西红柿", 1, 2);
        list.forEach(a-> System.out.println(a));

    }
    @Test
    public void text1(){
        List<Cookie> list = qcookieService.QselectAll( 1, 2);

        list.forEach(a-> System.out.println(a));

    }
}