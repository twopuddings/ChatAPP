/**
 * 这是一个管理客户端与服务器保持通讯线程的类
 */
package com.qq.client.tools;
import java.util.*;

public class ManageClientConnServerThread {
	private static HashMap hm=new HashMap<String,ClientConnServerThread>();
	//把创建好的ClientConnServerThread放入到hm
	public static void addClientConnServerThread(String qqId,ClientConnServerThread ccst){
		hm.put(qqId,ccst);
	}
	//可以通过qqId取得该线程
	public static ClientConnServerThread getClientConnServerThread(String qqId){
		return (ClientConnServerThread)hm.get(qqId);
	}
	
}
