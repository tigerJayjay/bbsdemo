package com.listener;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.entity.User;

public class SesListener implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {
		// TODO Auto-generated method stub
		HttpSession session = arg0.getSession();
		ServletContext context = session.getServletContext();
		List<User> list = (List<User>)context.getAttribute("onlineList");
		User user = (User)session.getAttribute("loginuser");
		Iterator<User> it = list.iterator();
		while(it.hasNext()){
			if(it.next().getUserName().equals(user.getUserName())){
				it.remove();
			}
		}
		System.out.println("É¾³ýÓÃ»§");
	}


}
