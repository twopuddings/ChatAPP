package com.rock.entity;

/**
 * ��Ϣ�࣬�����û������շ��û������������ͺʹ��������
 * 
 * @author ���ǽ�
 *
 */
public class Message implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String msgtype;// �ж��շ���Ϣ��������
	private String userName;// �û���
	private String RuserName;// Ŀ�ĵ��û���
	private String data; // ��Ϣ����,��Ϊ��ʱ����Ҫ����һЩ���������

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
