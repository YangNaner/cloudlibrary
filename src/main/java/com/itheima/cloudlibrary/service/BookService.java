package com.itheima.cloudlibrary.service;

import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 图书接口
 */
public interface BookService {
//查询最新上架的图书
List<Book> selectNewBooks( Integer pageSize)
        throws SQLException;
//根据id查询图书信息
Book findById(String id) throws SQLException;
//借阅图书
Integer borrowBook(Book book) throws SQLException;
//分页查询图书
PageBean<Book> search(Book book, Integer pageNum) throws SQLException;
//新增图书
Integer addBook(Book book) throws SQLException;
//编辑图书信息
Integer editBook(Book book) throws SQLException;
//查询当前借阅的图书
PageBean<Book> searchBorrowed(Book book,User user, Integer currPage)
        throws SQLException;
//归还图书
Integer returnBook(String  id,User user) throws SQLException;
//归还确认
Integer returnConfirm(String id) throws SQLException;

}
