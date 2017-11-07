package com.web;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.biz.MessageBiz;
import com.biz.bizimpl.MessageBizImpl;
import com.entity.Message;
import com.entity.Page;
import com.entity.User;


public class MessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private MessageBiz mb = new MessageBizImpl();;  
    public MessageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		if("listMsg".equals(action)){
			listMsg(request,response,session);
		}
		if("send".equals(action)){
			send(request,response,session);
		}
		if("backMsg".equals(action)){
			backMsg(request,response,session);
		}
		if("showMsg".equals(action)){
			showMsg(request,response,session);
		}
		if("delMsg".equals(action)){
			delMsg(request,response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	/**
	 * 显示当前用户message
	 */
	private void listMsg(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException{
//		String username = request.getParameter("username");
//		//message操作类
//		MessageBiz mb = new MessageBizImpl();
//		//获取所有当前用户的message
//		List<Message> listMsg = mb.showAllMessage(username);
//		//保存到request中
//		session.setAttribute("listMsg", listMsg);
//		//返回主页面
//		response.sendRedirect("/bbsdemo/main.jsp");
		String pageIndex = request.getParameter("pageIndex");
		String username = request.getParameter("username");
		int pageSize = 5;
		if(pageIndex==null){
			pageIndex = "1";
		}
		Page<Message> page = mb.showMessageByPage(Integer.valueOf(pageIndex), pageSize, username);
		session.setAttribute("page", page);
		response.sendRedirect("/bbsdemo/jsp/main.jsp");
	}
	
	/**
	 * 发送信息
	 * @throws IOException 
	 */
	private void send(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws IOException{
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String toUser = request.getParameter("toUser");
		String username = ((User)session.getAttribute("loginuser")).getUserName();
		Message msg = new Message();
		msg.setTitle(title);
		msg.setMsgcontent(content);
		msg.setSendto(toUser);
		msg.setUsername(username);
		msg.setMsg_create_date(new Date(new java.util.Date().getTime()));
		msg.setState(0);
		int result = mb.sendMessage(msg);
		if(result>0){
			response.getWriter().println("<script>alert('发送成功!');location.href='/bbsdemo/jsp/newMsg.jsp'</script>");
		}else{
			session.setAttribute("msg", msg);
			response.getWriter().println("<script>alert('发送失败!');location.href='/bbsdemo/jsp/newMsg.jsp'</script>");
		}
	}
	
	/**
	 * 回复信息
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void backMsg(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException{
		//获取信息id
		String msgid = request.getParameter("msgid");
		
		Message msg = null;
		if(msgid!=null){
			//更新状态
			updState(request, msg,msgid);
		}
		//获取回复对象
		String sendto = request.getParameter("sendto");
		//保存到request中
		session.setAttribute("sendto", sendto);
		//request.getRequestDispatcher("/newMsg.jsp").forward(request, response);
		response.sendRedirect("/bbsdemo/jsp/newMsg.jsp");
	}
	
	/**
	 * 查看信息
	 */
	private void showMsg(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException{
		Message msg = null;
		//获取信息id
		String msgId = request.getParameter("msgid");
		msg = updState(request,msg,msgId);
		session.setAttribute("msgRead", msg);
		response.sendRedirect("/bbsdemo/jsp/readMsg.jsp");
	}
	
	/**
	 * 删除信息
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void delMsg(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		//获取信息id
		String msgId = request.getParameter("msgid");
		//删除信息
		int res = mb.deleteMessage(Integer.valueOf(msgId));
		if(res>0){
			//删除成功
			response.getWriter().println("<script>alert('删除成功!');location.href='/bbsdemo/jsp/main.jsp'</script>");

		}else{
			//删除失败
			response.getWriter().println("<script>alert('删除失败!');location.href='/bbsdemo/jsp/main.jsp'</script>");
		}
	}
	
	/**
	 * 更新状态
	 * @param request
	 * @param msg
	 */
	private Message updState(HttpServletRequest request,Message msg,String msgid){
		
		//获取该信息
		msg = mb.showMessage(Integer.valueOf(msgid));
		//把信息状态改为已读
		msg.setState(1);
		//更新数据
		mb.updMsg(msg);
		return msg;
	}

}
