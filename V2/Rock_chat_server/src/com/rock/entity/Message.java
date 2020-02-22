package com.rock.entity;

/**
 * ��Ϣ��
 * 
 * @author ���ǽ�
 *
 */
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	// ������Ϣ��һ��ÿһ�����������е����ԣ����������
	private String msgtype;// �ж��շ���Ϣ��������
	private String userName;// �û���
	private String RuserName;// Ŀ�ĵ��û���
	private String data; // ��Ϣ����,��Ϊ��ʱ����Ҫ����һЩ���������

	public Message(String userName, String msgtype, String message) {
		this.msgtype = msgtype;
		this.data = message;
		this.userName = userName;
		System.out.println("====������Ϣ����Ϣ����" + this.msgtype + "�����û���:" + this.userName + "��Ϣ����:" + this.data);
	}

	public Message() {
		// TODO Auto-generated constructor stub
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
