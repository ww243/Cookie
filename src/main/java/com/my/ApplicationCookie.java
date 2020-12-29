package com.my;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/07 14:28
 */
@SpringBootApplication
@MapperScan("com.my.dao")
public class ApplicationCookie {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationCookie.class,args);
    }
}
