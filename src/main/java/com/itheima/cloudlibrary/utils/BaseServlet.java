package com.itheima.cloudlibrary.utils;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * 通用的Servlet的编写
 * @author jt
 *
 */
public class BaseServlet extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try{

			req.setCharacterEncoding("UTF-8");
			resp.setContentType("text/html;charset=UTF-8");
			// 接收参数:
			String methodName = req.getParameter("method");
			if(methodName == null){
				methodName = "execute";
			}
			// 获得Class:
			// this如果创建的是父类的对象，this指代的是父类引用，如果创建的是子类的对象，this指代的是子类的对象。
			Class clazz = this.getClass();
			// 反射获得该方法:
			Method method = clazz.getMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			// 执行该方法:
			String path = (String) method.invoke(this, req,resp);
			// 这个路径用于转发:
			if(path != null){
				// 有返回值，完成转发的操作:
				req.getRequestDispatcher(path).forward(req, resp);
			}
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
