package com.rock.method;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.rock.Thread.ServerThread;
import com.rock.entity.User;

/**
 * ����һ����������������Ϳͻ��˶�Ӧ���̵߳���
 * 
 * @author ���ǽ�
 *
 */
public class SThreadMag {
	public static HashMap<String, ServerThread> SThreadmap = new HashMap<String, ServerThread>();

	/**
	 * ������ߵ��û����Լ����û���Ӧ ���������ڼ������߳�
	 * 
	 * @param user
	 * @param thread
	 */
	public static void addCThread(String user, ServerThread thread) {
		SThreadmap.put(user, thread);
	}

	/**
	 * ɾ�����ߵ��û����Լ����û���Ӧ ���������ڼ������߳�
	 * 
	 * @param user
	 */
	public static void delCThread(String user) {
		SThreadmap.remove(user);
	}

	/**
	 * �����û����������Ӧ�ķ������߳�
	 * 
	 * @param user
	 * @return
	 */
	public static ServerThread getSThread(String user) {
		return (ServerThread) SThreadmap.get(user);
	}

	/**
	 * @return �������������û����б�
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
	 * ���Ժ����������û���
	 */
	public static void visit() {
		String[] users = getOnlineUsers().split(" ");
		for (int i = 0; i < users.length; i++) {
			System.out.println("��" + i + "���û���" + users[i]);
		}
	}
}
