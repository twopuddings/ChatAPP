/**
 * ����һ������ͻ��������������ͨѶ�̵߳���
 */
package com.qq.client.tools;
import java.util.*;

public class ManageClientConnServerThread {
	private static HashMap hm=new HashMap<String,ClientConnServerThread>();
	//�Ѵ����õ�ClientConnServerThread���뵽hm
	public static void addClientConnServerThread(String qqId,ClientConnServerThread ccst){
		hm.put(qqId,ccst);
	}
	//����ͨ��qqIdȡ�ø��߳�
	public static ClientConnServerThread getClientConnServerThread(String qqId){
		return (ClientConnServerThread)hm.get(qqId);
	}
	
}
