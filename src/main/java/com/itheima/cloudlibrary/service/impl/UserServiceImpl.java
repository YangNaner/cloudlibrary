package com.itheima.cloudlibrary.service.impl;

import com.itheima.cloudlibrary.dao.UserDao;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.UserService;
import com.itheima.cloudlibrary.utils.BeanFactory;


import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *用户接口实现类
 */
public class UserServiceImpl  implements UserService {
    //通过User的用户账号和用户密码查询用户信息
    @Override
    public User login(User user) throws SQLException {
        UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
        return userDao.login(user);
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
        return userDao.findByUsername(username);
    }
}
