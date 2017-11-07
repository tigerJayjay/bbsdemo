package com.biz;

import java.util.List;

import com.entity.User;

public interface UserBiz {
	/**
	 * 登录
	 */
	User login(User user);
	
	/**
	 * 注册
	 */
	Integer regist(User user);
	
	/**
	 * 显示除登录用户的其他用户
	 */
	List<User> showUsers(String username);
	
	/**
	 * 检查用户名是否已经注册
	 */
	Integer isRegistUname(String userName);
}
