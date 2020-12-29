package com.my;

import com.my.entity.User;
import com.my.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/07 14:24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes =ApplicationCookie.class)
public class TestUser {
    @Autowired
    private UserService userService;

    @Test
    public void text(){
        User ljn = userService.login("ljn");
        System.out.println(ljn);
    }
}
