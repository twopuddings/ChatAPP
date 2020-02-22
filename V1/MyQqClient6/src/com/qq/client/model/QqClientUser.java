package com.qq.client.model;

import com.qq.common.*;
public class QqClientUser {

	public boolean checkUser(User u)
	{
		boolean b=false;
		return new QqClientConnServer().sendLoginInfoToServer(u);
	}
}
