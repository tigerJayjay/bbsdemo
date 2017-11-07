package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.dao.MessageDao;
import com.entity.Message;
import com.util.SqlSessionFactoryUtil;

public class MessageDaoImpl implements MessageDao{

	@Override
	public Integer delMsgById(Integer msgId) {
		// TODO Auto-generated method stub
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
		Integer res = session.delete("com.dao.MessageDao.delMsgById", msgId);
		session.commit();
		SqlSessionFactoryUtil.close(session);
		return res;
	}

	@Override
	public Integer addMsg(Message message) {
		// TODO Auto-generated method stub
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
		Integer res = session.insert("com.dao.MessageDao.addMsg", message);
		session.commit();
		SqlSessionFactoryUtil.close(session);
		return res;
	}
	@Override
	public Integer updMsg(Message msg) {
		// TODO Auto-generated method stub
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
		Integer res = session.update("com.dao.MessageDao.updMsg", msg);
		session.commit();
		SqlSessionFactoryUtil.close(session);
		return res;
	}


	@Override
	public Integer getTotalCount(String username) {
		// TODO Auto-generated method stub
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
		Integer res = session.selectOne("com.dao.MessageDao.getTotalCount", username);
		SqlSessionFactoryUtil.close(session);
		return res;
	}

	@Override
	public List<Message> findMessage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		SqlSession session = SqlSessionFactoryUtil.openSqlSession();
//		List<Message> list = session.selectList("com.dao.MessageDao.findMessage", map);
//		SqlSessionFactoryUtil.close(session);
		MessageDao msgDao = session.getMapper(MessageDao.class);
		List<Message> list = msgDao.findMessage(map);
		return list;
	}

}
