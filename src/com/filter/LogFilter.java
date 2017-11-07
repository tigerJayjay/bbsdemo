package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Servlet Filter implementation class LogFilter
 */
public class LogFilter implements Filter {
	private Log log = LogFactory.getLog(LogFilter.class);
    /**
     * Default constructor. 
     */
    public LogFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		log.info("过滤器关闭");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		
		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest)request;
		//请求ip
		String ip = req.getRemoteAddr();
		//请求的地址
		String addr = req.getRequestURI();
		//请求栏信息
		String allUrl =req.getQueryString()==null?addr:addr+"?"+req.getQueryString();
		log.info("requestUrl"+addr);
		log.info("requestUri:"+allUrl);
		log.info("requestip:"+ip);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		String filterName = fConfig.getFilterName();
		log.info("打开了过滤器:"+filterName);
	}

}
