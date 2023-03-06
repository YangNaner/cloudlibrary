package com.itheima.cloudlibrary.dao;


import com.itheima.cloudlibrary.domain.User;
import java.sql.SQLException;

/**
 * 用户操作接口
 */
public interface UserDao {
    //用户登录
    User login(User user) throws SQLException;

    User findByUsername(String username) throws SQLException;

}
