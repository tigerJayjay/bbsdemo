package com.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.User;

public class LoginFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		System.out.println("Login过滤");
		// TODO Auto-generated method stub
		//获取Cookie
		User user = null;
		
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpSession session = request.getSession();
		HttpServletResponse response = (HttpServletResponse)arg1;
		Cookie[] cookies = request.getCookies();
		//遍历Cookie
		if(cookies!=null ){
			for(Cookie c:cookies){
				//有登录信息才可以跳转到main.jsp
				if("username".equals(c.getName())){
					user = new User();
					//中文编码
					String value = URLDecoder.decode(c.getValue(),"utf-8");
					user.setUserName(value);
					session.setAttribute("loginuser", user);
					response.sendRedirect("/bbsdemo/jsp/main.jsp");
					return;
				}
			}
		}
		arg2.doFilter(request, response);
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
