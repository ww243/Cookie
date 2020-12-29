package com.my.controller;

import com.my.entity.Cookie;
import com.my.service.CookieService;
import com.my.service.QcookieService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/08 11:21
 */
@Controller
@RequestMapping("qcookie")
public class QCookieController {

    @Autowired
    private QcookieService qcookieService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("QselectAll")
    public String QselectAll(HttpServletRequest request,String content,
                             @RequestParam(defaultValue = "1")Integer pageNow,
                             @RequestParam(defaultValue = "4")Integer size){
        List<Cookie> all=null;
        if(content==null){
            all = qcookieService.QselectAll(pageNow, size);
            request.setAttribute("pageNow",pageNow);
        }else{
            all = qcookieService.QselectKey(content, pageNow, size);
            qcookieService.redis(content);
            request.setAttribute("pageNow",pageNow);
        }
        request.setAttribute("all",all);
        return "front/index";
    }

    @RequestMapping("QselectByid")
    public String QselectByid(HttpServletRequest request,String id){
        Cookie cookie = cookieService.selectByid(id);
        request.setAttribute("cookie",cookie);
        return "front/detail";
    }
    @RequestMapping("Qhotsearch")
    @ResponseBody
    public Map<String,Integer> Qhotsearch(){
        HashMap<String, Integer> map = new LinkedHashMap<>();
        Set<ZSetOperations.TypedTuple<String>> content = redisTemplate.opsForZSet().reverseRangeWithScores("content", 0, -1);
        for (ZSetOperations.TypedTuple<String> tuple : content) {
            map.put(tuple.getValue(),tuple.getScore().intValue());
        }
        return map;
    }

    @RequestMapping("QhotsDic")
    @ResponseBody
    public Map<String,String> QhotsDic(HttpServletRequest request,String content) {
        HashMap<String, String> map = new HashMap<>();
        String path = request.getSession().getServletContext().getRealPath("/ext.txt");
        if(content!=null||content!=""){
            try {
                List<String> strings = FileUtils.readLines(new File(path));
                HashMap<String, String> stringMap = new HashMap<>();
                strings.forEach(a -> stringMap.put(a, "1"));
                if(!stringMap.containsKey(content))
                    FileUtils.writeLines(new File(path), Arrays.asList(content),true);
                map.put("yes","添加成功");
            } catch (IOException e) {
                e.printStackTrace();
                map.put("no", "添加失败");
            }
        }
        return map;
    }

    @RequestMapping("QgetHots")
    @ResponseBody
    public List<String> QgetHots(HttpServletRequest request) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/ext.txt");
        List<String> strings = FileUtils.readLines(new File(path));
        return strings;
    }

    @RequestMapping("QdelHots")
    @ResponseBody
    public List<String> QdelHots(HttpServletRequest request,String content) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/ext.txt");
        //读出
        List<String> strings = FileUtils.readLines(new File(path));
        //用map删除接受后的指定字段
        HashMap<String, String> stringMap = new HashMap<>();
        strings.forEach(a -> stringMap.put(a, "1"));
        stringMap.remove(content);
        Set<Map.Entry<String, String>> set = stringMap.entrySet();
        //使用新集合接受
        ArrayList<String> list = new ArrayList<>();
        for (Map.Entry<String, String> stringStringEntry : set) list.add(stringStringEntry.getKey());
        //重新写入文件
        FileUtils.writeLines(new File(path),list);
        return list;
    }
}
