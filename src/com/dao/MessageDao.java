package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Message;

public interface MessageDao {

	/**
	 * 根据msgid删除message
	 */
	Integer delMsgById(Integer msgId);
	
	/**
	 * 添加message
	 */
	Integer addMsg(Message message);
	
	/**
	 * 根据msgid更新message
	 */
	Integer updMsg(Message msg);
	
	/**
	 * 查询当前用户消息总数
	 */
	Integer getTotalCount(String username);
	
	/**
	 * mybatis优化通用查询
	 */
	List<Message> findMessage(Map<String,Object> map);
}
