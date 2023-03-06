package com.itheima.cloudlibrary.web;

import com.itheima.cloudlibrary.domain.User;
import com.itheima.cloudlibrary.service.UserService;
import com.itheima.cloudlibrary.utils.BaseServlet;
import com.itheima.cloudlibrary.utils.BeanFactory;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户登录和注销
 */
@WebServlet("/userServlet")
public class UserServlet extends BaseServlet {
    /*
   用户登录
    */
    public String login(HttpServletRequest req,HttpServletResponse resp) throws Exception {
        // 接收数据
        Map<String, String[]> map = req.getParameterMap();
        // 封装数据
        User user = new User();
        BeanUtils.populate(user, map);
        // 调用业务层的登录方法
        UserService userService =
                (UserService) BeanFactory.getBean("userService");
        User existUser = userService.login(user);
        // 根据登录结果进行页面跳转
        if (existUser == null) {
            // 登录失败
            req.setAttribute("msg", "用户名或密码错误!");
            return "/admin/login.jsp";
        } else {
            // 登录成功
            req.getSession().setAttribute("USER_SESSION", existUser);
            return "/admin/index.jsp";
        }
    }

    /*
    注销登录
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp)
            throws Exception {
        // 销毁session
        req.getSession().invalidate();
        // 页面跳转:
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
