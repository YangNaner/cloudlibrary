package com.itheima.cloudlibrary.web;

import com.itheima.cloudlibrary.domain.Book;
import com.itheima.cloudlibrary.domain.PageBean;
import com.itheima.cloudlibrary.domain.Record;
import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.BookService;
import com.itheima.cloudlibrary.service.RecordService;
import com.itheima.cloudlibrary.utils.BaseServlet;
import com.itheima.cloudlibrary.utils.BeanFactory;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/recordServlet")
public class RecordServlet extends BaseServlet {
/**
 * 查询借阅记录
 */
public String searchRecords(HttpServletRequest req, HttpServletResponse resp){
    // 接收数据
    Map<String, String[]> map = req.getParameterMap();
    //获取当前页码
    String currPage = req.getParameter("currPage");
    Integer pagenum;
    //如果当前页码为null,则设置为1
    if (currPage == null) {
        pagenum = 1;

    } else {
        pagenum = Integer.parseInt(currPage);
    }
    Record record = new Record();
    //获取当前登录用户
    User user = (User) req.getSession().getAttribute("USER_SESSION");
    RecordService recordService =
            (RecordService) BeanFactory.getBean("recordService");
    try {
        // 封装搜索框的参数信息
        BeanUtils.populate(record, map);
        PageBean<Record> bookPageBean=
                recordService.searchRecords(record,user, pagenum);
        //将查询到的借阅记录信息 进行响应
        req.setAttribute("pageBean", bookPageBean);
        //将搜索栏中的信息返回
        req.setAttribute("search", record);
    } catch (Exception e) {
        e.printStackTrace();
    }
    return "/admin/record.jsp";
}
}

