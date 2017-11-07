package com.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EncodingFilter implements Filter{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest)arg0;
		HttpServletResponse response = (HttpServletResponse)arg1;
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpServletRequest proxy = (HttpServletRequest)Proxy.newProxyInstance(
				request.getClass().getClassLoader(), 
				new Class[]{HttpServletRequest.class}, 
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// TODO Auto-generated method stub
						//���巵��ֵ
						Object obj = null;
						//��ȡ������
						String methodName = method.getName();
						if("getParameter".equals(methodName)){
							//��ȡ����ʽ
							String reqmethod = request.getMethod();
							//��ȡ����ֵ
							String value = request.getParameter(args[0].toString());
							if("GET".equals(reqmethod)){
								//����
								if (value != null && !"".equals(value.trim())){
									// ����GET����
									value = new String(value.getBytes("ISO8859-1"),"UTF-8");
								}

							}
							return value;
						}else{
							obj = method.invoke(request, args);
						}
						return obj;
					}
				});
		arg2.doFilter(proxy, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
