package com.biz;

import com.entity.Message;
import com.entity.Page;

public interface MessageBiz {

	/**
	 * 删除某个当前用户消息
	 */
	Integer deleteMessage(Integer msgId);
	/**
	 * 显示消息详情
	 */
	Message showMessage(Integer msgId);
	/**
	 * 发送消息
	 */
	Integer sendMessage(Message message);
	/**
	 * 更新消息状态
	 */
	Integer updMsg(Message msg);
	/**
	 * 分页查询当前 用户消息
	 */
	Page<Message> showMessageByPage(int pageIndex,int pageSize,String username);
}
