/**
 *  服务器连接某个客户端的线程,保持与某个客户端的通信
 */
package com.qq.server.model;

import java.net.*;
import java.io.*;
import java.util.*;

import com.qq.common.Message;
import com.qq.common.MessageType;
import java.util.*;
public class SerConnClientThread extends Thread{
	Socket s;
	
	public SerConnClientThread(Socket s){
		//把服务器和该客户端的连接赋给S
		this.s=s;
	}
	
	 //V6--让该线程去通知其他用户我上线了
	public void notifyOthers(String myself)
	{
		//得到所有在线人的线程
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext()){
			Message m=new Message();
			m.setContent(myself);
			m.setMsType(MessageType.message_response_onLineFriend);
			//取出在线人的id
			String onLineUserId=it.next().toString();
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setReceiver(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run(){
		while(true){
			//该线程就可以接收客户端的聊天信息
			try{
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				
				//System.out.println(m.getSender()+"对"+m.getReceiver()+"说："+m.getContent());
				
				//V5--根据从客户端接收的包类型（聊天内容包？请求好友列表包？）分别处理：
				if(m.getMsType().equals(MessageType.message_comm_mess))
				{
					//V3.3如果是普通聊天包--转发
				//取得接收人的通信线程
				SerConnClientThread sc=ManageClientThread.getClientThread(m.getReceiver());
				// 创建输出流转发
				ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
				oos.writeObject(m);
				}else if(m.getMsType().equals(MessageType.message_request_onLineFriend))
				{
					//如果是请求在线好友包
					//--则从管理登录用户的类中取出在线用户response，封装进一个包返回给客户端。
					System.out.println("从客户端接收到"+m.getSender()+"的请求好友包");
					String res_content=ManageClientThread.getAllOnLineUserNo();
					Message m_res=new Message();
					m_res.setMsType(MessageType.message_response_onLineFriend);
					m_res.setContent(res_content);
					m_res.setReceiver(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					System.out.println(m_res.getContent());
					oos.writeObject(m_res);
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
