package com.dao;

import java.util.List;
import java.util.Map;

import com.entity.Message;

public interface MessageDao {

	/**
	 * ����msgidɾ��message
	 */
	Integer delMsgById(Integer msgId);
	
	/**
	 * ���message
	 */
	Integer addMsg(Message message);
	
	/**
	 * ����msgid����message
	 */
	Integer updMsg(Message msg);
	
	/**
	 * ��ѯ��ǰ�û���Ϣ����
	 */
	Integer getTotalCount(String username);
	
	/**
	 * mybatis�Ż�ͨ�ò�ѯ
	 */
	List<Message> findMessage(Map<String,Object> map);
}
