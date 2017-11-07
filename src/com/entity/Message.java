package com.entity;

import java.sql.Date;

public class Message {
	private Integer msgid;
	private String username;
	private String title;
	private String msgcontent;
	private Integer state;
	private String sendto;
	private Date msg_create_date;
	public Integer getMsgid() {
		return msgid;
	}
	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getSendto() {
		return sendto;
	}
	public void setSendto(String sendto) {
		this.sendto = sendto;
	}
	public Date getMsg_create_date() {
		return msg_create_date;
	}
	public void setMsg_create_date(Date msg_create_date) {
		this.msg_create_date = msg_create_date;
	}
	
	
}
