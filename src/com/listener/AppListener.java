package com.listener;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.entity.User;

public class AppListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		//�Ƴ�list
		arg0.getServletContext().removeAttribute("onlineList");
		System.out.println("ɾ���б�");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		//����List���ϱ������߶���
		List<User> list = new LinkedList<>();
		//�ŵ�application��
		arg0.getServletContext().setAttribute("onlineList", list);
		System.out.println("�����б�");
	}

}
