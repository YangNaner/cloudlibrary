package com.itheima.cloudlibrary.dao.impl;

import cn.hutool.core.util.HashUtil;
import cn.hutool.core.util.StrUtil;
import com.itheima.cloudlibrary.dao.BookDao;
import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    public List<Book> selectNewBooks(Integer pageSize)
            throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM book WHERE status!=3 " +
                "ORDER BY uploadtime DESC  LIMIT ?";
        List<Book> newBooks = qr.query(sql,
                new BeanListHandler<Book>(Book.class), pageSize);
        return newBooks;
    }
    public Book findById(String id) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT * FROM book WHERE id=?";
        Book book = qr.query(sql, new BeanHandler<Book>(Book.class), id);
        return book;
    }
    public Integer editBook(Book book) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "UPDATE book SET name=?,press=?,author=?,pagination=?," +
                "price=?,status=?,borrower=?,borrowtime=?,returntime=? WHERE id=?";
        Object[] params={book.getName(),book.getPress(),book.getAuthor(),
                book.getPagination(),book.getPrice(),book.getStatus(),
                book.getBorrower(),book.getBorrowTime(),
                book.getReturnTime(),book.getId()};
        Integer count = qr.update(sql, params);
        return count.intValue();
    }
    public Integer addBook(Book book) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "INSERT INTO book (name,press,author,pagination," +
                "price,uploadtime,status) VALUES(?,?,?,?,?,?,?)";
        Object[] params={book.getName(),book.getPress(),book.getAuthor(),
                book.getPagination(),book.getPrice(),book.getUploadTime(),book.getStatus()};
        Integer count = qr.update(sql, params);
        return count.intValue();
    }


    public Integer findBorrowedCount(String name) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT count(*) FROM book WHERE borrower=? AND status IN(1,2) ";
        Long count = (Long)queryRunner.query(sql, new ScalarHandler());
        return count.intValue();
    }

    //查询借阅但未归还的图书和待归还确认的图书
    public List<Book> findPageByBorrower(String name, Integer begin, Integer pageSize) throws SQLException {
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        String sql = "SELECT count(*) FROM book WHERE borrower=? AND status IN(1,2) ORDER BY borrowtime ASC  LIMIT ?,?";
        List<Book> borrowedBooks = queryRunner.query(sql, new BeanListHandler<Book>(Book.class),name,begin,pageSize);
        return borrowedBooks;
    }

    public Integer findBorrowedCounts(Book book,User user) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        //查询已借阅和归还中的图书
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM book " +
                "WHERE status IN(1,2) ");
        List<Object> params = new ArrayList<Object>();
        //如果当前登录用户不是管理员，则只查询借阅人为当前登录用户的信息
        if(!"ADMIN".equals(user.getRole())){
            sql.append(" AND borrower=?");
            params.add(user.getName());
        }
        //如果在搜索框中输入了图书名称进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getName())){
            sql.append(" AND name LIKE ?");
            params.add("%"+book.getName()+"%");
        }
        //如果在搜索框中输入了作者进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getAuthor())){
            sql.append(" AND author LIKE ?");
            params.add("%"+book.getAuthor()+"%");
        }
        //如果在搜索框中输入了出版社进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getPress())){
            sql.append(" AND press LIKE ?");
            params.add("%"+book.getPress()+"%");
        }
        Long count = (Long)qr.query(sql.toString(),
                new ScalarHandler(),params.toArray());
        return count.intValue();
    }

    public List<Book> findAllBorrowedByPage(Book book,
                 User user,Integer begin, Integer pageSize) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        //查询已借阅和归还中的图书
        StringBuilder sql = new StringBuilder("SELECT * FROM book " +
                "WHERE status IN (1,2)  ");
        List<Object> params = new ArrayList<Object>();
        //如果当前登录用户不是管理员，则只查询借阅人为当前登录用户的信息
        if(!"ADMIN".equals(user.getRole())){
            sql.append(" AND borrower=?");
            params.add(user.getName());
        }
        //如果在搜索框中输入了图书名称进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getName())){
            sql.append(" AND name LIKE ?");
            params.add("%"+book.getName()+"%");
        }
        //如果在搜索框中输入了作者进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getAuthor())){
            sql.append(" AND author LIKE ?");
            params.add("%"+book.getAuthor()+"%");
        }
        //如果在搜索框中输入了出版社进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getPress())){
            sql.append(" AND press LIKE ?");
            params.add("%"+book.getPress()+"%");
        }
        //根据当前页码和每页显示的数量进行限量查询，并按借阅时间顺序排序
        sql.append(" ORDER BY borrowtime ASC  LIMIT ?,?");
        params.add(begin);
        params.add(pageSize);
        List<Book> borrowedBooks = qr.query(sql.toString(),
                new BeanListHandler<Book>(Book.class),params.toArray());
        return borrowedBooks;
    }

    public Integer findBookCounts(Book book) throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        //查询未下架的图书信息
        StringBuilder sql = new StringBuilder("SELECT count(*) FROM " +
                "book WHERE status IN(0,1,2) ");
        List<Object> params = new ArrayList<Object>();
        //如果在搜索框中输入了图书名称进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getName())){
            sql.append(" AND name LIKE ?");
            params.add("%"+book.getName()+"%");
        }
        //如果在搜索框中输入了作者进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getAuthor())){
            sql.append(" AND author LIKE ?");
            params.add("%"+book.getAuthor()+"%");
        }
        //如果在搜索框中输入了出版社进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getPress())){
            sql.append(" AND press LIKE ?");
            params.add("%"+book.getPress()+"%");
        }
        Long count = (Long)qr.query(sql.toString(),
                new ScalarHandler(),params.toArray());
        return count.intValue();
    }

    public List<Book> findBooksByPage(Book book,Integer begin, int pageSize)
            throws SQLException {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
        //查询未下架的图书信息
       StringBuilder sql = new StringBuilder("SELECT * FROM book " +
               "WHERE status in(0,1,2)");
        List<Object> params = new ArrayList<Object>();
        //如果在搜索框中输入了图书名称进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getName())){
            sql.append(" AND name LIKE ?");
            params.add("%"+book.getName()+"%");
        }
        //如果在搜索框中输入了作者进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getAuthor())){
            sql.append(" AND author LIKE ?");
            params.add("%"+book.getAuthor()+"%");
        }
        //如果在搜索框中输入了出版社进行查询，则追加查询条件
        if(!StrUtil.isBlankIfStr(book.getPress())){
            sql.append(" AND press LIKE ?");
            params.add("%"+book.getPress()+"%");
        }
        //根据当前页码和每页显示的数量进行限量查询，并按上架时间顺序排序
        sql.append(" ORDER BY uploadtime ASC  LIMIT ?,?");
        params.add(begin);
        params.add(pageSize);
        List<Book> bookList = qr.query(sql.toString(),
                new BeanListHandler<Book>(Book.class),params.toArray());
        return bookList;
    }
}
