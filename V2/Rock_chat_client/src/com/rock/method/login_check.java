package com.rock.method;

import com.rock.entity.*;;

/**
 * @author 林智杰 这个yemia是为了验证登录是否成功，成功为true,否则为false
 */
public class login_check {
	public login_check() {
	};

	public static boolean logincheck(User user) {
		// 客户端首次和服务器进行连接，把用户输入的登录信息发送给服务器
		return new client_server().send(user);
	}

}
