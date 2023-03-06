package com.itheima.cloudlibrary.dao.impl;

import cn.hutool.core.util.StrUtil;
import com.itheima.cloudlibrary.dao.RecordDao;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecordDaoImpl implements RecordDao {
    @Override
    public Integer addRecord(Record record) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO record (bookname,borrower,borrowtime," +"remandtime) VALUES(?,?,?,?)";
        Object[] params = {record.getBookname(), record.getBorrower(),record.getBorrowTime(), record.getRemandTime()};
        Integer count = qr.update(sql, params);
        return count.intValue();
    }

    @Override
    public Integer searchRecordCount(Record record, User user) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM record WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        //如果是管理员并且搜索框中指定了查询的借阅人
        if("ADMIN".equals(user.getRole()) &&
                !StrUtil.isBlankIfStr(record.getBorrower())){
            sql.append(" AND borrower LIKE ?");
            params.add("%"+record.getBorrower()+"%");
        }
        //如果不是管理员，就查询当前登录用户的借阅记录
        if(!"ADMIN".equals(user.getRole())){
            sql.append(" AND borrower=?");
            params.add(user.getName());
        }
        //如果搜索框中指定了查询的图书名称
        if(!StrUtil.isBlankIfStr(record.getBookname())){
            sql.append(" AND bookname LIKE ?");
            params.add("%"+record.getBookname()+"%");
        }
        //查询符合条件的借阅记录数量
        Long count = (Long)qr.query(sql.toString(),
                new ScalarHandler(),params.toArray());
        return count.intValue();
    }

    @Override
    public List<Record> searchRecordsByPage(Record record, User user,
            Integer begin, Integer pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        StringBuilder sql = new StringBuilder("SELECT * FROM record WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        //如果是管理员并且搜索框中指定了查询的借阅人
        if("ADMIN".equals(user.getRole()) &&
                !StrUtil.isBlankIfStr(record.getBorrower())){
            sql.append(" AND borrower LIKE ?");
            params.add("%"+record.getBorrower()+"%");
        }
        //如果不是管理员，就查询当前登录用户的借阅记录
        if(!"ADMIN".equals(user.getRole()) ){
            sql.append(" AND borrower=?");
            params.add(user.getName());
        }
        //如果搜索框中指定了查询的图书名称
        if(!StrUtil.isBlankIfStr(record.getBookname())){
            sql.append(" AND bookname LIKE ?");
            params.add("%"+record.getBookname()+"%");
        }
        sql.append(" ORDER BY borrowtime ASC  LIMIT ?,?");
        params.add(begin);
        params.add(pageSize);
        List<Record> recordBooks = queryRunner.query(sql.toString(),
                new BeanListHandler<Record>(Record.class),params.toArray());
        return recordBooks;
    }

}
