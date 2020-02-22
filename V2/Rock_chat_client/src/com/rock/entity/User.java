package com.rock.entity;

/**
 * 用户类，储存了用户名和密码
 * 
 * @author 林智杰
 *
 */
public class User implements java.io.Serializable {
	private static final long serialVersionUID = 2375880261555570884L;

	private String userName;
	private String password;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}