package com.itheima.cloudlibrary.web.filter;

import com.itheima.cloudlibrary.domain.User;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;


@WebFilter({"/bookServlet/*","/userServlet/*","/recordServlet/*","/admin/*"})
public class PrivilegeFilter implements Filter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		String path=request.getServletPath();

	if("/index.jsp".equals(path)||"/admin/login.jsp".equals(path)||path.startsWith("/userServlet")){
		chain.doFilter(request, resp);
		return;
	}
		// 判断是否已经登录:

		User existUser = (User) request.getSession().getAttribute("USER_SESSION");
		if(existUser == null){
			request.setAttribute("msg", "您还没有登录！没有权限访问！");
			request.getRequestDispatcher("/admin/login.jsp").forward(request, resp);
			return;
		}
		chain.doFilter(request, resp);
	}

	public void init(FilterConfig arg0) throws ServletException {
		
	}

}
