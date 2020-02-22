package com.rock.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.rock.Thread.ServerThread;
import com.rock.entity.User;

/**
 * 这是一个用来管理服务器和客户端对应的线程的类
 * 
 * @author 林智杰
 *
 */
public class SThreadMag {
	public static HashMap<String, ServerThread> SThreadmap = new HashMap<String, ServerThread>();

	/**
	 * 添加在线的用户，以及其用户对应 服务器用于监听的线程
	 * 
	 * @param user
	 * @param thread
	 */
	public static void addCThread(String user, ServerThread thread) {
		SThreadmap.put(user, thread);
	}

	/**
	 * 删除下线的用户，以及其用户对应 服务器用于监听的线程
	 * 
	 * @param user
	 */
	public static void delCThread(String user) {
		SThreadmap.remove(user);
	}

	/**
	 * 根据用户名返回其对应的服务器线程
	 * 
	 * @param user
	 * @return
	 */
	public static ServerThread getSThread(String user) {
		return (ServerThread) SThreadmap.get(user);
	}

	/**
	 * @return 返回所有在线用户的列表
	 */
	public static String getOnlineUsers() {
		String users = "";
		Iterator it = SThreadmap.keySet().iterator();

		while (it.hasNext()) {
			users += it.next().toString() + " ";
		}
		return users;
	}

	/**
	 * 测试函数，遍历用户集
	 */
	public static void visit() {
		String[] users = getOnlineUsers().split(" ");
		for (int i = 0; i < users.length; i++) {
			System.out.println("第" + i + "个用户：" + users[i]);
		}
	}
}
