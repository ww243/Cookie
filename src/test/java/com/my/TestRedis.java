package com.my;

import com.my.service.QcookieService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Set;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/09 17:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationCookie.class)
public class TestRedis {

    @Autowired
    private QcookieService qcookieService;
    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Test
    public void text() {
        qcookieService.redis("西红柿");
    }
    @Test
    public void text1(){
        HashMap<String, Integer> hashMap = new HashMap<>();
        Set<ZSetOperations.TypedTuple<Object>> content = redisTemplate.opsForZSet().rangeWithScores("content", 0, -1);
        for (ZSetOperations.TypedTuple<Object> tuple : content) {
            System.out.println(tuple.getValue());
            System.out.println(tuple.getScore().intValue());
        }
    }
}
