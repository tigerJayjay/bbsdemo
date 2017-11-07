package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.User;

public interface UserDao {

	/**
	 * 插入信息
	 */
	Integer addUser(User user);
	
	/**
	 * 动态查询
	 */
	List<User> getUser(Map<String,String> contitions); 
}
