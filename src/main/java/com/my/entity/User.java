package com.my.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:22
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    private String id;
    private String username;
    private String password;
}
