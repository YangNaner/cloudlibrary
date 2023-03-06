package com.itheima.cloudlibrary.service;

import com.itheima.cloudlibrary.domain.User;

import java.sql.SQLException;

/**
 *用户接口
 */
public interface UserService{
    //通过User的用户账号和用户密码查询用户信息
    User login(User user) throws SQLException;
   //根据username查询用户信息
    User findByUsername(String username) throws SQLException;
}
