package com.my.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cookie implements Serializable {
    private String id;
    private String name;
    private String src;
    private Date putTime;
    private String enterName;
    private String about;
    private String steps;
}
