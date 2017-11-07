package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.dao.BaseDao;
import com.dao.UserDao;
import com.entity.User;
import com.util.SqlSessionFactoryUtil;

public class UserDaoImpl extends BaseDao implements UserDao{

	@Override
	public Integer addUser(User user) {
		// TODO Auto-generated method stub
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
		Integer a = session.insert("com.dao.UserDao.addUser", user);
		session.commit();
		return a;
	}

	
    /**
     * 优化用户查询
     */
	@Override
	public List<User> getUser(Map<String, String> contitions) {
		// TODO Auto-generated method stub
		//使用mybatis动态sql查询
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
		List<User> list = session.selectList("com.dao.UserDao.getUser", contitions);
		return list;
	}
}
