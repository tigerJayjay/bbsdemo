package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.User;

public interface UserDao {

	/**
	 * ������Ϣ
	 */
	Integer addUser(User user);
	
	/**
	 * ��̬��ѯ
	 */
	List<User> getUser(Map<String,String> contitions); 
}
