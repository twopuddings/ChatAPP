package com.qq.server.tools;

import com.qq.common.User;
import com.qq.server.db.SqlHelper;

public class AddQqUser {
	public boolean AddQqUser(User u) throws Exception{
		return new SqlHelper().save(u);
	}
}
