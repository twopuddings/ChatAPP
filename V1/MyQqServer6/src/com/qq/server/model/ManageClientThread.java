/**
 * �������߿ͻ��˵���(�Ѿ���¼�Ŀͻ���)
 */
package com.qq.server.model;

import java.util.*;
import com.qq.server.model.*;
public class ManageClientThread {

	public static HashMap hm=new HashMap<String,SerConnClientThread>();
	
	//��hm�����һ���ͻ���ͨ���߳�
	public static void addClientThread(String uid,SerConnClientThread ct)
	{
		hm.put(uid,ct);
	}
	public static SerConnClientThread getClientThread(String uid)
	{
		return (SerConnClientThread)hm.get(uid);
	}
	
	//���ص�ǰ����qq�û�
	public static String getAllOnLineUserNo()
	{
		//ʹ�õ����������Ѿ���¼��QQ�û�
		Iterator it=hm.keySet().iterator();
		String response="";
		while(it.hasNext()){
			response=response+it.next().toString()+" ";
		}
		return response;
	}
}
