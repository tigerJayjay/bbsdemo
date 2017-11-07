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
	 * ��ʾ��ǰ�û�message
	 */
	private void listMsg(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws ServletException, IOException{
//		String username = request.getParameter("username");
//		//message������
//		MessageBiz mb = new MessageBizImpl();
//		//��ȡ���е�ǰ�û���message
//		List<Message> listMsg = mb.showAllMessage(username);
//		//���浽request��
//		session.setAttribute("listMsg", listMsg);
//		//������ҳ��
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
	 * ������Ϣ
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
			response.getWriter().println("<script>alert('���ͳɹ�!');location.href='/bbsdemo/jsp/newMsg.jsp'</script>");
		}else{
			session.setAttribute("msg", msg);
			response.getWriter().println("<script>alert('����ʧ��!');location.href='/bbsdemo/jsp/newMsg.jsp'</script>");
		}
	}
	
	/**
	 * �ظ���Ϣ
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void backMsg(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException{
		//��ȡ��Ϣid
		String msgid = request.getParameter("msgid");
		
		Message msg = null;
		if(msgid!=null){
			//����״̬
			updState(request, msg,msgid);
		}
		//��ȡ�ظ�����
		String sendto = request.getParameter("sendto");
		//���浽request��
		session.setAttribute("sendto", sendto);
		//request.getRequestDispatcher("/newMsg.jsp").forward(request, response);
		response.sendRedirect("/bbsdemo/jsp/newMsg.jsp");
	}
	
	/**
	 * �鿴��Ϣ
	 */
	private void showMsg(HttpServletRequest request,HttpServletResponse response,HttpSession session) throws ServletException, IOException{
		Message msg = null;
		//��ȡ��Ϣid
		String msgId = request.getParameter("msgid");
		msg = updState(request,msg,msgId);
		session.setAttribute("msgRead", msg);
		response.sendRedirect("/bbsdemo/jsp/readMsg.jsp");
	}
	
	/**
	 * ɾ����Ϣ
	 * @throws IOException 
	 * @throws ServletException 
	 */
	private void delMsg(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
		//��ȡ��Ϣid
		String msgId = request.getParameter("msgid");
		//ɾ����Ϣ
		int res = mb.deleteMessage(Integer.valueOf(msgId));
		if(res>0){
			//ɾ���ɹ�
			response.getWriter().println("<script>alert('ɾ���ɹ�!');location.href='/bbsdemo/jsp/main.jsp'</script>");

		}else{
			//ɾ��ʧ��
			response.getWriter().println("<script>alert('ɾ��ʧ��!');location.href='/bbsdemo/jsp/main.jsp'</script>");
		}
	}
	
	/**
	 * ����״̬
	 * @param request
	 * @param msg
	 */
	private Message updState(HttpServletRequest request,Message msg,String msgid){
		
		//��ȡ����Ϣ
		msg = mb.showMessage(Integer.valueOf(msgid));
		//����Ϣ״̬��Ϊ�Ѷ�
		msg.setState(1);
		//��������
		mb.updMsg(msg);
		return msg;
	}

}
