package com.rock.method;

import java.util.HashMap;

import com.rock.Thread.ClientThread;
import com.rock.entity.User;

/**
 * 管理客户端连接服务器的线程的类
 * 
 * @author 林智杰
 *
 */
public class CThreadMag {
	public static HashMap<String, ClientThread> cThreadmap = new HashMap<String, ClientThread>();

	/*
	 * 添加在线的用户，以及其用户对应 服务器用于监听的线程
	 * 
	 */
	public static void addCThread(String user, ClientThread thread) {
		cThreadmap.put(user, thread);
	}

	/**
	 * 根据用户名删除客户端线程
	 * 
	 * @param user
	 */
	public static void delCThread(String user) {
		cThreadmap.remove(user);
	}

	/**
	 * 根据用户名返回客户端线程
	 * 
	 * @param user
	 * @return
	 */
	public static ClientThread getCThread(String user) {
		return (ClientThread) cThreadmap.get(user);
	}

}
