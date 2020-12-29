package com.my;

import com.my.entity.Cookie;
import com.my.service.CookieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/07 15:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationCookie.class)
public class TestCookie {
    @Autowired
    private CookieService cookieService;

    @Test
    public void  text(){
        cookieService.selectAll().forEach(a-> System.out.println(a));
        System.out.println("----------------------------------------");
        cookieService.selectAll().forEach(a-> System.out.println(a));
        System.out.println("----------------------------------------");
        cookieService.selectAll().forEach(a-> System.out.println(a));

    }
    @Test
    public void  byid(){
        System.out.println(cookieService.selectByid("1"));
    }
    @Test
    public void  insert(){
        Cookie cookie = new Cookie();
        cookie.setName("西红柿");
        cookie.setEnterName("ljn");
        cookie.setAbout("!");
        cookie.setSteps("!");
        cookie.setSrc("1");
        cookieService.insertCookie(cookie);
    }
    @Test
    public void  update(){
        Cookie cookie = cookieService.selectByid("13d59e09-bc9f-4d73-a75d-ad5a514d8de2");
        cookie.setName("鸡蛋");
        cookieService.updateCookie(cookie);
    }
    @Test
    public void  delete(){
        cookieService.deleteCookie("13d59e09-bc9f-4d73-a75d-ad5a514d8de2");
    }
    @Test
    public void  text1(){
        cookieService.closeIndex();
    }
    @Test
    public void  text2(){
        cookieService.addIndex();
    }
}
