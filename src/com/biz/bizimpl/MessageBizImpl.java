package com.biz.bizimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.biz.MessageBiz;
import com.dao.MessageDao;
import com.dao.impl.MessageDaoImpl;
import com.entity.Message;
import com.entity.Page;

public class MessageBizImpl implements MessageBiz{
	private MessageDao messageDao = new MessageDaoImpl();
	private Map<String,Object> map = null;

	@Override
	public Integer deleteMessage(Integer msgId) {
		// TODO Auto-generated method stub
		return messageDao.delMsgById(msgId);
	}

	@Override
	public Message showMessage(Integer msgId) {
		// TODO Auto-generated method stub
		map = new HashMap<String,Object>();
		map.put("msgId", msgId);
		List<Message> list = messageDao.findMessage(map);
		return list.get(0);
	}

	@Override
	public Integer sendMessage(Message message) {
		// TODO Auto-generated method stub
		return messageDao.addMsg(message);
	}

	@Override
	public Integer updMsg(Message msg) {
		// TODO Auto-generated method stub
		return messageDao.updMsg(msg);
	}

	@Override
	public Page<Message> showMessageByPage(int pageIndex, int pageSize, String userName) {
		// TODO Auto-generated method stub
		int begin = (pageIndex-1)*pageSize;
		map = new HashMap<String,Object>();
		map.put("begin", begin);
		map.put("pageSize", pageSize);
		map.put("userName", userName);
		Page<Message> page = new Page<Message>();
		page.setListResult(messageDao.findMessage(map));
		page.setPageSize(pageSize);
		page.setTotalSize(messageDao.getTotalCount(userName));
		page.setPageIndex(pageIndex);
		return page;
	}

}
