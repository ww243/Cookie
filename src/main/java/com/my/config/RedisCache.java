package com.my.config;

import com.my.util.ApplicationContextUtil;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.DigestUtils;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/09 16:13
 */
public class RedisCache implements Cache {

    private final String id;

    public RedisCache(String id) {
        this.id = id;
    }
    //用来创建对象绑定
    @Override
    public String getId() {
        return this.id;
    }

    @Override     //放入缓存
    public void putObject(Object key, Object value) {
        //将key做MD5
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(key.toString().getBytes());
        //redisTemplate.opsForValue().set(md5DigestAsHex,o1);
        redisTemplate().opsForHash().put(this.id,md5DigestAsHex,value);
    }

    @Override     //从缓存中获取
    public Object getObject(Object key) {
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(key.toString().getBytes());
        return redisTemplate().opsForHash().get(this.id,md5DigestAsHex);
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override        //namespace key
    public void clear() {
        redisTemplate().delete(this.id);
    }

    @Override
    public int getSize() {

        return redisTemplate().opsForHash().size(this.id).intValue();
    }

    @Override
    public ReadWriteLock getReadWriteLock() {
        return new ReentrantReadWriteLock();
    }


    //提供获取redisTemplate方法
    private RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = (RedisTemplate) ApplicationContextUtil.getBean("redisTemplate");
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
