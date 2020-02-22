package com.rock.method;

import java.util.HashMap;

import com.rock.Thread.ClientThread;
import com.rock.entity.User;

/**
 * ����ͻ������ӷ��������̵߳���
 * 
 * @author ���ǽ�
 *
 */
public class CThreadMag {
	public static HashMap<String, ClientThread> cThreadmap = new HashMap<String, ClientThread>();

	/*
	 * ������ߵ��û����Լ����û���Ӧ ���������ڼ������߳�
	 * 
	 */
	public static void addCThread(String user, ClientThread thread) {
		cThreadmap.put(user, thread);
	}

	/**
	 * �����û���ɾ���ͻ����߳�
	 * 
	 * @param user
	 */
	public static void delCThread(String user) {
		cThreadmap.remove(user);
	}

	/**
	 * �����û������ؿͻ����߳�
	 * 
	 * @param user
	 * @return
	 */
	public static ClientThread getCThread(String user) {
		return (ClientThread) cThreadmap.get(user);
	}

}
