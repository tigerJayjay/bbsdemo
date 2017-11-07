package com.biz.bizimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biz.UserBiz;
import com.dao.impl.UserDaoImpl;
import com.entity.User;

public class UserBizImpl implements UserBiz{
	private UserDaoImpl udi = new UserDaoImpl();
	private Map<String,String> contitions = new HashMap<String,String>();
	private List<User> list = new ArrayList<User>();
	/**
	 * 登录实现
	 */
	@Override
	public User login(User user) {
		// TODO Auto-generated method stub
		contitions.put("username", user.getUserName());
		contitions.put("password", user.getPassword());
		list = udi.getUser(contitions);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	/**
	 * 注册实现
	 */
	@Override
	public Integer regist(User user) {
		// TODO Auto-generated method stub
		contitions.put("username", user.getUserName());
		if(udi.getUser(contitions).size()>0){
			return -1;
		}
		return udi.addUser(user);
	}
	/**
	 * 显示实现
	 */
	@Override
	public List<User> showUsers(String username) {
		// TODO Auto-generated method stub
		contitions.put("username", username);
		contitions.put("action", "except");
		list = udi.getUser(contitions);
		return list;
	}
	
	@Override
	public Integer isRegistUname(String userName) {
		// TODO Auto-generated method stub
		contitions.put("username", userName);
		return udi.getUser(contitions).size();
	}
}
