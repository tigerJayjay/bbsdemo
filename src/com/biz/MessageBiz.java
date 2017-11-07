package com.biz;

import com.entity.Message;
import com.entity.Page;

public interface MessageBiz {

	/**
	 * ɾ��ĳ����ǰ�û���Ϣ
	 */
	Integer deleteMessage(Integer msgId);
	/**
	 * ��ʾ��Ϣ����
	 */
	Message showMessage(Integer msgId);
	/**
	 * ������Ϣ
	 */
	Integer sendMessage(Message message);
	/**
	 * ������Ϣ״̬
	 */
	Integer updMsg(Message msg);
	/**
	 * ��ҳ��ѯ��ǰ �û���Ϣ
	 */
	Page<Message> showMessageByPage(int pageIndex,int pageSize,String username);
}
