package com.my.controller;

import com.my.entity.Cookie;
import com.my.entity.User;
import com.my.service.CookieService;
import com.my.service.QcookieService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/07 15:35
 */
@Controller
@RequestMapping("cookie")
public class CookieController {

    @Autowired
    private CookieService cookieService;
    @Autowired
    private QcookieService qcookieService;

    @RequestMapping("selectAll")
    public String selectAll(HttpServletRequest request){
        List<Cookie> cookies = cookieService.selectAll();
        //只显示20个字
        for (Cookie cookie : cookies) {
            if (cookie.getAbout().length()>20) {
                cookie.setAbout(cookie.getAbout().substring(0,20)+"..");
            }
            if (cookie.getSteps().length()>20) {
                cookie.setSteps(cookie.getSteps().substring(0,20)+"..");
            }
        }
        request.setAttribute("cookies",cookies);
        return "back/list";
    }
    @RequestMapping("insertCookie")
    public String insertCookie(MultipartFile photo,HttpServletRequest request, Cookie cookie){
        User user = (User)request.getSession().getAttribute("user");
        try {
            if(user==null) throw new RuntimeException("你好，等登录");
            //获取就名字
            String oidname = photo.getOriginalFilename();
            //获取新名字
            String s = FilenameUtils.getExtension(oidname);
            String newname = UUID.randomUUID().toString().replace("-","")+"."+s;
            //创建时间文件
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            //获取相对路径
            String path = request.getSession().getServletContext().getRealPath("/img");
            //创建username文件
            File file1 = new File(path + "/" + user.getUsername());
            if (!file1.exists()) file1.mkdirs();
            //拼接路径
            String a=user.getUsername()+"/"+date+"/"+newname;
            cookie.setSrc(a);
            //创建
            File file = new File(file1, date);
            cookie.setEnterName(user.getUsername());
            if (!file.exists()) file.mkdirs();
            photo.transferTo(new File(file,newname));
            cookieService.insertCookie(cookie);
            return "redirect:/cookie/selectAll";
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            request.setAttribute("message",message);
            return "back/add";
        }
    }
    @RequestMapping("selectByidCookie")
    public String selectByidCookie(HttpServletRequest request,String id){
        Cookie byid = cookieService.selectByid(id);
        request.setAttribute("byid",byid);
        return "back/update";
    }
    @RequestMapping("updateCookie")
    public String updateCookie(MultipartFile photo,HttpServletRequest request,Cookie cookie) throws Exception {
        User user = (User)request.getSession().getAttribute("user");
        //获取相对路径
        String path = request.getSession().getServletContext().getRealPath("/img");
        try {
            if(user==null) throw new RuntimeException("你好,请登录");
            if (!photo.isEmpty()) {
                Cookie byid = cookieService.selectByid(cookie.getId());
                FileUtils.forceDelete(new File(path+"/"+byid.getSrc()));
                //获取就名字
                String oidname = photo.getOriginalFilename();
                //获取新名字
                String s = FilenameUtils.getExtension(oidname);
                String newname = UUID.randomUUID().toString().replace("-", "") + "." + s;
                //创建时间文件
                String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                //新路径
                File file = new File( path + "/" + user.getUsername()+"/"+date);
                if (!file.exists()) file.mkdirs();
                photo.transferTo(new File(file, newname));
                cookie.setSrc(user.getUsername()+"/"+date+"/"+newname);
            }
            cookieService.updateCookie(cookie);
            return "redirect:/cookie/selectAll";
        } catch (Exception e) {
            e.printStackTrace();
            String message = e.getMessage();
            request.setAttribute("message",message);
            return "back/update";
        }
    }
    @RequestMapping("deleteCookie")
    public String deleteCookie(String id,HttpServletRequest request) throws Exception {
        Cookie byid = cookieService.selectByid(id);
        String path = request.getSession().getServletContext().getRealPath("/img");
        FileUtils.forceDelete(new File(path+"/"+byid.getSrc()));
        cookieService.deleteCookie(id);
        return "redirect:/cookie/selectAll";
    }
    @RequestMapping("closeIndex")
    @ResponseBody
    public Map<String,String> closeIndex(){
        HashMap<String, String> map = new HashMap<>();
        try {
            cookieService.closeIndex();
            map.put("yes","清空成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("no","清空失败,请稍后重试");
        }
        return map;
    }
    @RequestMapping("addIndex")
    @ResponseBody
    public Map<String,String> addIndex(){
        HashMap<String, String> map = new HashMap<>();
        try {
            cookieService.addIndex();
            map.put("yes","创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("no","创建失败,请稍后重试");
        }
        return map;
    }
    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("user");
        request.getSession().invalidate();
        return "back/login";
    }
}
