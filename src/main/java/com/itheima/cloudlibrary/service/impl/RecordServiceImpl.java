package com.itheima.cloudlibrary.service.impl;


import com.itheima.cloudlibrary.dao.BookDao;
import com.itheima.cloudlibrary.dao.RecordDao;
import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.RecordService;
import com.itheima.cloudlibrary.utils.BeanFactory;

import java.sql.SQLException;
import java.util.List;


public class RecordServiceImpl implements RecordService {


    /**
     * 新增借阅记录
     *
     * @param record 新增的借阅记录
     */
    @Override
    public Integer addRecord(Record record) throws SQLException {
        RecordDao recordDao =
                (RecordDao) BeanFactory.getBean("recordDao");
        return recordDao.addRecord(record);
    }


/**
 * 查询借阅记录
 *  record 借阅记录的查询条件
 *  user 当前的登录用户
 *  pageNum 当前页码
 *   每页显示数量
 */
@Override
public PageBean<Record> searchRecords(Record record,
                     User user, Integer pagenum) throws SQLException {
    PageBean<Record> pageBean = new PageBean<Record>();
    // 设置当前页码
    pageBean.setCurrPage(pagenum);
    // 设置每页显示记录数:
    Integer pageSize = 10;
    pageBean.setPageSize(pageSize);
    RecordDao recordDao = (RecordDao) BeanFactory.getBean("recordDao");
    //查询符合条件的借阅记录数量
    Integer totalCount = recordDao.searchRecordCount(record,user);
    pageBean.setTotalCount(totalCount);
    // 设置符合条件的总页数:
    Double tc = totalCount.doubleValue();
    Double num = Math.ceil(tc / pageSize);
    pageBean.setTotalPage(num.intValue());
    Integer begin = (pagenum - 1)*pageSize;
    //查询符合条件的数据
    List<Record> list = recordDao.searchRecordsByPage(record,
            user,begin,pageSize);
    pageBean.setList(list);
    return pageBean;
}
}
