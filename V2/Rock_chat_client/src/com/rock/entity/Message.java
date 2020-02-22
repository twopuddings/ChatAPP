package com.rock.entity;

/**
 * 消息类，发方用户名，收方用户名，数据类型和传输的数据
 * 
 * @author 林智杰
 *
 */
public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String msgtype;// 判断收发消息结果的情况
	private String userName;// 用户名
	private String RuserName;// 目的地用户名
	private String data; // 消息正文,因为有时候需要传输一些特殊的类型

	public Message() {
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public String getRuserName() {
		return RuserName;
	}

	public void setRuserName(String ruserName) {
		RuserName = ruserName;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
