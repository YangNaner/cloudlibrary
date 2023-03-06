package com.itheima.cloudlibrary.service;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;

import java.sql.SQLException;


/**
 * 借阅记录接口
 */
public interface RecordService {
    //新增借阅记录
    Integer addRecord(Record record) throws SQLException;
//查询借阅记录
PageBean<Record> searchRecords(Record record, User user,
                               Integer pageNum) throws SQLException;
}
