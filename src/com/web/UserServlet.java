package com.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.biz.UserBiz;
import com.biz.bizimpl.UserBizImpl;
import com.entity.User;

public class UserServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		if("login".equals(action)){
			login(request,response,session);
		}
		if("regist".equals(action)){
			regist(request,response,session);
		}
		if("logout".equals(action)){
			logout(request, response, session);
		}
		if("findUsers".equals(action)){
			findUsers(request,response,session);
		}
	}

	//注销
	private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		Cookie[] cookies = request.getCookies();
		//遍历Cookie
		if(cookies!=null){
			for(Cookie c:cookies){
				if("username".equals(c.getName())){
					c.setMaxAge(0);
					response.addCookie(c);
				}
			}
		}
		session.invalidate();
		response.sendRedirect("/bbsdemo/index.jsp");
	}
	
	/**
	 * 登录
	 * @throws ServletException 
	 */
	private void login(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException, ServletException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User Uuser = new User();
		Uuser.setUserName(username);
		Uuser.setPassword(password);
		User user = null;
		UserBizImpl ubi = new UserBizImpl();
		user = ubi.login(Uuser);
		
		//登陆成功
		if(user!=null){
			//将登录信息保存到客户端
			//获取sessionId
			String sessionId = session.getId();
			//中文编码
			username = URLEncoder.encode(username, "utf-8");
			//创建Cookie对象
			Cookie cookie = new Cookie("username",username);
			//设置超时时间
			cookie.setMaxAge(30*60);
			response.addCookie(cookie);
			

			session.setAttribute("loginuser", user);
			ServletContext context = session.getServletContext();
			List<User> list = (List<User>)context.getAttribute("onlineList");
			Iterator<User> it = list.iterator();
			while(it.hasNext()){
				if(it.next().getUserName().equals(user.getUserName())){
					it.remove();
					break;
				}
			}
			list.add(user);
			response.sendRedirect("/bbsdemo/jsp/main.jsp");
			return;
			
//			//获取登录前uri
//			String uri = (String)session.getAttribute("uri");
//			if(uri!=null){
//				response.sendRedirect("/bbsdemo"+uri);
//				return;
//			}
			//uri为空转到首页
//			response.sendRedirect("/bbsdemo/jsp/main.jsp");
		}else{
			session.setAttribute("error", "账号或密码错误!");
			session.setAttribute("errorUser", Uuser);
			response.sendRedirect("/bbsdemo/index.jsp");
		}
	}
	
	/**
	 * 注册
	 * @throws IOException 
	 */
	public void regist(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		User user = new User();
		//获取表单值
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		user.setUserName(username);
		user.setPassword(password);
		user.setEmail(email);
		
		UserBizImpl ubi = new UserBizImpl();
		
		int result = ubi.regist(user);
		
		//用户被注册
		if(result==-1){
			session.setAttribute("error", "用户名已被注册");
			session.setAttribute("registuser", user);
			response.sendRedirect("/bbsdemo/register.jsp");
			return;
		}
		//注册成功
		if(result>0){
			PrintWriter out = response.getWriter();
			out.print("<script>alert('注册成功!');location.href="
					+ "'/bbsdemo/index.jsp';</script>");
		}else{
			session.setAttribute("error", "注册失败!");
			response.sendRedirect("/bbsdemo/register.jsp");
		}
	}
	
	/**
	 * 显示所有用户
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void findUsers(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException, ServletException{
        if(session.getAttribute("loginuser")==null){
        	response.sendRedirect("/bbsdemo/index.jsp");
        	return;
        }
		String sendto = request.getParameter("sendto");
        String username = ((User)session.getAttribute("loginuser")).getUserName();
		UserBiz ub = new UserBizImpl();
		//获取所有User
		List<User> userList = ub.showUsers(username);
		//放到session中
		session.setAttribute("userList",userList);
		//跳转页面
		if(sendto!=null){
			session.setAttribute("sendto", sendto);
			request.getRequestDispatcher("/MessageServlet?action=backMsg").forward(request, response);
			return;
		}
		session.removeAttribute("sendto");
		response.sendRedirect("/bbsdemo/jsp/newMsg.jsp");
		
	}
}
