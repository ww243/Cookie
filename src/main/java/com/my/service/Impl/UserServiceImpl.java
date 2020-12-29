package com.my.service.Impl;

import com.my.dao.UserDao;
import com.my.entity.User;
import com.my.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @author:ljn
 * @Description:
 * @Date:2020/11/06 21:32
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User login(String username) {
       return userDao.login(username);
    }

    @Override
    public void insertUser(User user) {
        user.setId(UUID.randomUUID().toString());
        userDao.insert(user);
    }
}
