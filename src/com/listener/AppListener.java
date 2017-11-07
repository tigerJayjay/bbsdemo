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
		//移除list
		arg0.getServletContext().removeAttribute("onlineList");
		System.out.println("删除列表");
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		//创建List集合保存在线对象
		List<User> list = new LinkedList<>();
		//放到application中
		arg0.getServletContext().setAttribute("onlineList", list);
		System.out.println("创建列表");
	}

}
