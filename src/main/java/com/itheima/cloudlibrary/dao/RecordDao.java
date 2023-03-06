package com.itheima.cloudlibrary.dao;

import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;

import java.sql.SQLException;
import java.util.List;


public interface RecordDao {
    //新增借阅记录
    Integer addRecord(Record record) throws SQLException;
    //查询符合条件的借阅记录数量
    Integer searchRecordCount(Record record, User user) throws SQLException;
    //查询符合条件的借阅记录数据
    List<Record> searchRecordsByPage(Record record, User user,
                                     Integer begin, Integer pageSize) throws SQLException;
}
