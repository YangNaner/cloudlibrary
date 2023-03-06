package com.itheima.cloudlibrary.web;


import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Result;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.BookService;
import com.itheima.cloudlibrary.utils.BaseServlet;
import com.itheima.cloudlibrary.utils.BeanFactory;
import com.itheima.cloudlibrary.utils.JsonUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/*
图书信息Controller
 */

@WebServlet("/bookServlet")
public class BookServlet extends BaseServlet {

    /**
     * 查询最新上架的图书
     */
    public String selectNewbooks(HttpServletRequest req, HttpServletResponse resp) {
        //设置一页展示的图书信息为5条
        int pageSize = 5;
        //获取BookService对象
        BookService bookService = (BookService) BeanFactory.getBean("bookService");
        try {
            List<Book> newBooks = bookService.selectNewBooks(pageSize);
            //将查询到的图书信息放在Request中返回
            req.setAttribute("newBooks", newBooks);
            return "/admin/books_new.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据图书id查询图书信息
     */

    public void findById(HttpServletRequest req, HttpServletResponse resp) {
        //获取BookService对象
        BookService bookService =
                (BookService) BeanFactory.getBean("bookService");
        //获取请求中的图书id
        String id = req.getParameter("id");
        try {
            //根据图书id查询图书信息
            Book book = bookService.findById(id);
            //如果为null,说明没有查询到对应id的图书信息
            if (book == null) {
                //将查询的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(false, "获取图书失败"));
                resp.getWriter().write(result);
            } else {
                //将查询的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(true, "获取图书成功", book));
                resp.getWriter().write(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 借阅图书
     */

    public void borrowBook(HttpServletRequest req, HttpServletResponse resp) {
        // 接收提交的参数信息
        Map<String, String[]> map = req.getParameterMap();
        // 封装数据
        Book book = new Book();
        try {
            //将提交的图书信息封装在book对象中
            BeanUtils.populate(book, map);
            //获取当前登录的用户姓名
            String pname = ((User) req.getSession().
                    getAttribute("USER_SESSION")).getName();
            book.setBorrower(pname);
            //根据图书的id和用户进行图书借阅
            BookService bookService =
                    (BookService) BeanFactory.getBean("bookService");
            Integer count = bookService.borrowBook(book);
            //如果没有修改图书信息，说明借阅失败
            if (count == 0) {
                //将借阅失败的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(false, "借阅图书失败"));
                resp.getWriter().write(result);
            } else {
                //将借阅成功的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(true, "借阅成功，请到行政中心取书!"));
                resp.getWriter().write(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 分页查询符合条件且未下架图书信息
     * book 查询的条件封装到book中
     * pageNum  数据列表的当前页码
     * pageSize 数据列表1页展示多少条数据
     */

    public String search(HttpServletRequest req, HttpServletResponse resp) {
        // 接收参数
        Map<String, String[]> map = req.getParameterMap();
        String currPage = req.getParameter("currPage");
        Integer pagenum;
        if (currPage == null) {
            pagenum = 1;

        } else {
            pagenum = Integer.parseInt(currPage);
        }

        Book book = new Book();
        try {
            // 封装数据
            BeanUtils.populate(book, map);
            BookService bookService = (BookService) BeanFactory.getBean("bookService");
            //查询图书
            PageBean<Book> bookPageBean = bookService.search(book, pagenum);
           //将查询到的分页图书信息存入Request中
            req.setAttribute("pageBean", bookPageBean);
            //回显搜索框中的信息
            req.setAttribute("search", book);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "/admin/books.jsp";
    }

    /**
     * 新增图书
     * book 页面表单提交的图书信息
     * 将新增的结果和向页面传递信息封装到String对象中返回
     */

    public void addBook(HttpServletRequest req, HttpServletResponse resp) {
        // 接收参数
        Map<String, String[]> map = req.getParameterMap();
        Book book = new Book();
        try {
            // 封装数据
            BeanUtils.populate(book, map);
            BookService bookService =
                    (BookService) BeanFactory.getBean("bookService");
            //添加图书
            Integer count = bookService.addBook(book);
            //如果数据库没有插入图书信息
            if (count == 0) {
                //将新增失败的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(false, "添加图书失败"));
                resp.getWriter().write(result);
            }
            //将新增成功的结果信息返回
            String result = JsonUtils.objectToJson(
                    new Result(true, "添加图书成功"));
            resp.getWriter().write(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 编辑图书信息
     * book 编辑的图书信息
     */

    public void editBook(HttpServletRequest req, HttpServletResponse resp) {
        // 接收参数
        Map<String, String[]> map = req.getParameterMap();
        Book book = new Book();
        try {
            // 封装参数
            BeanUtils.populate(book, map);
            BookService bookService =
                    (BookService) BeanFactory.getBean("bookService");
            Integer count = bookService.editBook(book);
            //编辑失败
            if (count == 0) {
                //将编辑失败的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(false, "编辑图书失败"));
                resp.getWriter().write(result);
            }
           else {
                //将编辑成功的结果信息返回
                String result = JsonUtils.objectToJson(
                        new Result(true, "编辑图书成功"));
                resp.getWriter().write(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

/**
 *分页查询当前被借阅且未归还的图书信息
 * currPage  数据列表的当前页码
 */

        public String searchBorrowed (HttpServletRequest req, HttpServletResponse resp){
            // 接收参数
            Map<String, String[]> map = req.getParameterMap();
            String currPage = req.getParameter("currPage");
            Integer pagenum;
            //如果当前页码为null，则设置当前页码为1
            if (currPage == null) {
                pagenum = 1;

            } else {
                pagenum = Integer.parseInt(currPage);
            }
            Book book = new Book();
            User user = (User) req.getSession().
                    getAttribute("USER_SESSION");
            BookService bookService =
                    (BookService) BeanFactory.getBean("bookService");
            try {
                // 封装数据
               BeanUtils.populate(book, map);
               PageBean<Book> bookPageBean=
                       bookService.searchBorrowed(book,user, pagenum);
               //将查询到的结果存入到Request找那个
                req.setAttribute("pageBean", bookPageBean);
                //回显搜索框中的信息
                req.setAttribute("search", book);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "/admin/book_borrowed.jsp";
        }
/**
 * 归还图书
 *  id 归还的图书的id
 */

        public void returnBook (HttpServletRequest req, HttpServletResponse resp)
                throws IOException {
            //获取当前登录的用户信息
            User user = (User) req.getSession().getAttribute("USER_SESSION");
            try {
                BookService bookService =
                        (BookService) BeanFactory.getBean("bookService");
                //获取归还图书的id
                String id = req.getParameter("id");
                Integer count = bookService.returnBook(id, user);
                //根据还书结果返回对应的信息
                if (count==0) {
                    //将还书失败的结果信息返回
                    String result = JsonUtils.objectToJson(
                            new Result(false, "还书失败"));
                    resp.getWriter().write(result);
                }
                else {
                    //将还书成功的结果信息返回
                    String result = JsonUtils.objectToJson(
                            new Result(true, "还书确认中，请先到行政中心还书!"));
                    resp.getWriter().write(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

/**
 * 确认图书归还
 *  id 确认归还的图书的id
 */

        public void returnConfirm (HttpServletRequest req, HttpServletResponse resp){
            try {
                BookService bookService =
                        (BookService) BeanFactory.getBean("bookService");
                //获取页面提交的图书id
                String id = req.getParameter("id");
                //归还图书
                Integer count = bookService.returnConfirm(id);
                if (count == 0) {
                    //将还书确认失败的结果信息返回
                    String result = JsonUtils.objectToJson(
                            new Result(false, "还书确认失败!"));
                    resp.getWriter().write(result);
                }
                else {
                    //将还书确认成功的结果信息返回
                    String result = JsonUtils.objectToJson(
                            new Result(true, "还书确认成功！"));
                    resp.getWriter().write(result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

