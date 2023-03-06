package com.itheima.cloudlibrary.service.impl;


import com.itheima.cloudlibrary.dao.BookDao;
import com.itheima.cloudlibrary.dao.RecordDao;
import com.itheima.cloudlibrary.dao.UserDao;
import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.BookService;
import com.itheima.cloudlibrary.service.RecordService;
import com.itheima.cloudlibrary.service.UserService;
import com.itheima.cloudlibrary.utils.BeanFactory;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class BookServiceImpl implements BookService {
    /**
     * 根据当前页码和每页需要展示的数据条数，查询最新上架的图书信息
     *
     * @param pageSize 每页显示数量
     */
    @Override
    public List<Book> selectNewBooks(Integer pageSize) throws SQLException {
        // 获取BookDao实例对象
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        return bookDao.selectNewBooks(pageSize);
    }

    /**
     * 根据id查询图书信息
     *
     * @param id 图书id
     */
    public Book findById(String id) throws SQLException {
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        return bookDao.findById(id);
    }

    /**
     * 借阅图书
     */
    public Integer borrowBook(Book book) throws SQLException {
        //根据id查询出需要借阅的完整图书信息
        Book b = this.findById(book.getId() + "");
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置当天为借阅时间
        book.setBorrowTime(dateFormat.format(new Date()));
        //设置所借阅的图书状态为借阅中
        book.setStatus("1");
        //将图书的价格设置在book对象中
        book.setPrice(b.getPrice());
        //将图书的上架时间设置在book对象中
        book.setUploadTime(b.getUploadTime());
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        return bookDao.editBook(book);
    }

    /**
     * @param book    封装查询条件的对象
     * @param pageNum 当前页码
     *                pageSize 每页显示数量
     */
    @Override
    public PageBean<Book> search(Book book, Integer pageNum)
            throws SQLException {
        // 创建分页对象
        PageBean<Book> pageBean = new PageBean<Book>();
        // 设置当前页数
        pageBean.setCurrPage(pageNum);
        int pageSize = 10;
        // 设置每页显示记录数:
        pageBean.setPageSize(pageSize);
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        //获取符合条件的图书信息数量
        Integer totalCount = bookDao.findBookCounts(book);
        //设置查询到的总数
        pageBean.setTotalCount(totalCount);
        Double tc = totalCount.doubleValue();
        // 设置分页的总页数:
        Double num = Math.ceil(tc / pageSize);
        pageBean.setTotalPage(num.intValue());
        // 每页显示数据集合:
        Integer begin = (pageNum - 1) * pageSize;
        //查询符合条件的图书信息
        List<Book> list = bookDao.findBooksByPage(book, begin, pageSize);
        pageBean.setList(list);
        return pageBean;
    }

    /**
     * 新增图书
     *
     * @param book 页面提交的新增图书信息
     */
    public Integer addBook(Book book) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置新增图书的上架时间
        book.setUploadTime(dateFormat.format(new Date()));
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        return bookDao.addBook(book);
    }

    /**
     * 编辑图书信息
     *
     * @param book 图书信息
     */
    public Integer editBook(Book book) throws SQLException {
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        return bookDao.editBook(book);
    }

    /**
     * 查询用户当前借阅的图书
     */
    @Override
    public PageBean<Book> searchBorrowed(Book book, User user, Integer currPage)
            throws SQLException {
        PageBean<Book> pageBean = new PageBean<Book>();
        // 设置当前页数
        pageBean.setCurrPage(currPage);
        // 设置每页显示记录数:
        Integer pageSize = 10;
        pageBean.setPageSize(pageSize);
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        Integer totalCount = bookDao.findBorrowedCounts(book, user);
        pageBean.setTotalCount(totalCount);
        // 设置符合条件的总页数:
        Double tc = totalCount.doubleValue();
        Double num = Math.ceil(tc / pageSize);
        pageBean.setTotalPage(num.intValue());
        // 每页显示数据集合:
        Integer begin = (currPage - 1) * pageSize;
        List<Book> list = bookDao.findAllBorrowedByPage(book, user, begin, pageSize);
        pageBean.setList(list);
        return pageBean;
    }

    /**
     * 归还图书
     *
     * @param id   归还的图书的id
     * @param user 归还的人员，也就是当前图书的借阅者
     */
    @Override
    public Integer returnBook(String id, User user) throws SQLException {
        //根据图书id查询出图书的完整信息
        Book book = this.findById(id);
        //再次核验当前登录人员和图书借阅者是不是同一个人
        boolean rb = book.getBorrower().equals(user.getName());
        //如果是同一个人，允许归还
        if (rb || "ADMIN".equals(user.getRole())) {
            //将图书借阅状态修改为归还中
            book.setStatus("2");
            BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
            return bookDao.editBook(book);
        }
        return 0;
    }

    /**
     * 归还确认
     *
     * @param id 待归还确认的图书id
     */
    @Override
    public Integer returnConfirm(String id) throws SQLException {
        //根据图书id查询图书的完整信息
        Book book = this.findById(id);
        //根据归还确认的图书信息，设置借阅记录
        Record record = this.setRecord(book);
        //将图书的借阅状态修改为可借阅
        book.setStatus("0");
        //清除当前图书的借阅人信息
        book.setBorrower("");
        //清除当前图书的借阅时间信息
        book.setBorrowTime(null);
        //清除当亲图书的预计归还时间信息
        book.setReturnTime(null);
        BookDao bookDao = (BookDao) BeanFactory.getBean("bookDao");
        Integer count = bookDao.editBook(book);
        //如果归还确认成功，则新增借阅记录
        if (count == 1) {
            RecordService recordService =
                    (RecordService) BeanFactory.getBean("recordService");
            return recordService.addRecord(record);
        }
        return count;
    }


    /**
     * 根据图书信息设置借阅记录的信息
     *
     * @param book 借阅的图书信息
     */
    private Record setRecord(Book book) {
        Record record = new Record();
        //设置借阅记录的图书名称
        record.setBookname(book.getName());
        //设置借阅记录的借阅人
        record.setBorrower(book.getBorrower());
        //设置借阅记录的借阅时间
        record.setBorrowTime(book.getBorrowTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //设置图书归还确认的当天为图书归还时间
        record.setRemandTime(dateFormat.format(new Date()));
        return record;
    }
}
