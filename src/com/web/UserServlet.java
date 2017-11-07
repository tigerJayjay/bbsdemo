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

	//ע��
	private void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws IOException {
		Cookie[] cookies = request.getCookies();
		//����Cookie
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
	 * ��¼
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
		
		//��½�ɹ�
		if(user!=null){
			//����¼��Ϣ���浽�ͻ���
			//��ȡsessionId
			String sessionId = session.getId();
			//���ı���
			username = URLEncoder.encode(username, "utf-8");
			//����Cookie����
			Cookie cookie = new Cookie("username",username);
			//���ó�ʱʱ��
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
			
//			//��ȡ��¼ǰuri
//			String uri = (String)session.getAttribute("uri");
//			if(uri!=null){
//				response.sendRedirect("/bbsdemo"+uri);
//				return;
//			}
			//uriΪ��ת����ҳ
//			response.sendRedirect("/bbsdemo/jsp/main.jsp");
		}else{
			session.setAttribute("error", "�˺Ż��������!");
			session.setAttribute("errorUser", Uuser);
			response.sendRedirect("/bbsdemo/index.jsp");
		}
	}
	
	/**
	 * ע��
	 * @throws IOException 
	 */
	public void regist(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException{
		User user = new User();
		//��ȡ��ֵ
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		
		user.setUserName(username);
		user.setPassword(password);
		user.setEmail(email);
		
		UserBizImpl ubi = new UserBizImpl();
		
		int result = ubi.regist(user);
		
		//�û���ע��
		if(result==-1){
			session.setAttribute("error", "�û����ѱ�ע��");
			session.setAttribute("registuser", user);
			response.sendRedirect("/bbsdemo/register.jsp");
			return;
		}
		//ע��ɹ�
		if(result>0){
			PrintWriter out = response.getWriter();
			out.print("<script>alert('ע��ɹ�!');location.href="
					+ "'/bbsdemo/index.jsp';</script>");
		}else{
			session.setAttribute("error", "ע��ʧ��!");
			response.sendRedirect("/bbsdemo/register.jsp");
		}
	}
	
	/**
	 * ��ʾ�����û�
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
		//��ȡ����User
		List<User> userList = ub.showUsers(username);
		//�ŵ�session��
		session.setAttribute("userList",userList);
		//��תҳ��
		if(sendto!=null){
			session.setAttribute("sendto", sendto);
			request.getRequestDispatcher("/MessageServlet?action=backMsg").forward(request, response);
			return;
		}
		session.removeAttribute("sendto");
		response.sendRedirect("/bbsdemo/jsp/newMsg.jsp");
		
	}
}
