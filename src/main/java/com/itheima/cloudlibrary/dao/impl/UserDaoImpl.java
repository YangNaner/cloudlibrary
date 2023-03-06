package com.itheima.cloudlibrary.dao.impl;

import com.itheima.cloudlibrary.dao.UserDao;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;


public class UserDaoImpl  implements UserDao {
    // 用户模块DAO的用户登录的方法:
    public User login(User user) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM user WHERE email = ? AND" +" password = ? AND status = ?";
        User existUser = qr.query(sql, new BeanHandler<User>(User.class),user.getEmail(), user.getPassword(), 0);
        return existUser;
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM user WHERE username= ? status = 0";
        User existUser = queryRunner.query(sql, new BeanHandler<User>(User.class), username);
        return existUser;
    }

}
