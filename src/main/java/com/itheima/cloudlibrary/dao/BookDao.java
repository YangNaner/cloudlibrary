package com.itheima.cloudlibrary.dao;


import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 图书接口
 */
public interface BookDao {
    //查询最新上架的图书信息
    List<Book> selectNewBooks(Integer pageSize) throws SQLException;
    //根据图书id查询图书
    Book findById(String id) throws SQLException;
    //编辑图书信息
    Integer editBook(Book book) throws SQLException;

    //新增图书
    Integer addBook(Book book) throws SQLException;






    Integer findBorrowedCount(String name) throws SQLException;

    List<Book> findPageByBorrower(String name, Integer begin, Integer pageSize) throws SQLException;
    //查询符合条件的当前借阅图书数量
    Integer findBorrowedCounts(Book book,User user) throws SQLException;
    //查询符合条件的当前借阅图书数据
    List<Book> findAllBorrowedByPage(Book book,User user,Integer begin,
                                     Integer pageSize) throws SQLException;
    //查询符合条件的图书数量
    Integer findBookCounts(Book book) throws SQLException;
    //查询符合条件的图书数据
    List<Book> findBooksByPage(Book book,Integer begin, int pageSize)
            throws SQLException;




}
