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


/**
 * @author tigerJay
 * Servlet Filter implementation class AuthorityFilter
 */
public class AuthorityFilter implements Filter {
    /**
     * Default constructor. 
     */
    public AuthorityFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
    @Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
    @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		System.out.println("Authority过滤");
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		User user = null;
		Cookie[] cookies = req.getCookies();
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
						}
					}
				}
		//先判断session中有没有登录信息,有直接通过
		if(session.getAttribute("loginuser")==null){
			//获取访问uri
			String uri = req.getRequestURI();
			String url =req.getContextPath();
			uri = uri.substring(url.length());
			session.setAttribute("uri", uri);
			res.sendRedirect("/bbsdemo/index.jsp");
			return;
		}
		// pass the request along the filter chain
		chain.doFilter(req, res);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
    @Override
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
