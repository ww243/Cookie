package com.my.controller;

import com.my.entity.User;
import com.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/07 15:01
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public String login(String username, String password, HttpServletRequest request,String message){
        try {
            User user = userService.login(username);
            if (user==null) throw new RuntimeException("用户名错误");
            if(!user.getPassword().equals(password)) throw new RuntimeException("密码错误");
            request.getSession().setAttribute("user",user);
            return "redirect:/cookie/selectAll";
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            request.getSession().setAttribute("message",message);
            return "back/login";
        }
    }
}
