/**
 * 管理在线客户端的类(已经登录的客户端)
 */
package com.qq.server.model;

import java.util.*;
import com.qq.server.model.*;
public class ManageClientThread {

	public static HashMap hm=new HashMap<String,SerConnClientThread>();
	
	//向hm中添加一个客户端通信线程
	public static void addClientThread(String uid,SerConnClientThread ct)
	{
		hm.put(uid,ct);
	}
	public static SerConnClientThread getClientThread(String uid)
	{
		return (SerConnClientThread)hm.get(uid);
	}
	
	//返回当前在线qq用户
	public static String getAllOnLineUserNo()
	{
		//使用迭代器遍历已经登录的QQ用户
		Iterator it=hm.keySet().iterator();
		String response="";
		while(it.hasNext()){
			response=response+it.next().toString()+" ";
		}
		return response;
	}
}
