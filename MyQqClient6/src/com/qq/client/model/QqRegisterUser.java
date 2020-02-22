package com.qq.client.model;

import com.qq.common.User;

public class QqRegisterUser {
	public boolean registerUser(User u) {
		boolean b = false;
		return new QqRegisterConnServer().sendRegisterInfoToServer(u);
	}
}
