package com.biz;

import java.util.List;

import com.entity.User;

public interface UserBiz {
	/**
	 * ��¼
	 */
	User login(User user);
	
	/**
	 * ע��
	 */
	Integer regist(User user);
	
	/**
	 * ��ʾ����¼�û��������û�
	 */
	List<User> showUsers(String username);
	
	/**
	 * ����û����Ƿ��Ѿ�ע��
	 */
	Integer isRegistUname(String userName);
}
