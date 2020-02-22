/**
 * 客户端与服务器保持通信的线程类，用来接收服务器转发的聊天信息。
 */
package com.qq.client.tools;

import java.net.*;
import java.io.*;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;
public class ClientConnServerThread extends Thread{
	private Socket s;

	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public ClientConnServerThread(Socket s){
		this.s=s;
	}
	public void run(){
		while(true){
			try {
				//不停地读取从服务器发来的消息
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				System.out.println("读取到的："+m.getSender()+" 和 "+m.getReceiver()+" 说："+m.getContent()+"\r\n");
				
				//V5--3 判断从服务器接受的包的类型，分别处理。
				if(m.getMsType().equals(MessageType.message_comm_mess))
				{
					//如果服务器返回的是普通聊天包，则显示到相应聊天界面
					//V4--7  把从服务器获得的消息，显示到该显示的聊天界面
					QqChat qqChat=ManageQqChat.getQqChat(m.getReceiver()+" "+m.getSender());
					//V4--7 显示
					qqChat.showMessage(m);
				}else if(m.getMsType().equals(MessageType.message_response_onLineFriend))
				{
					//如果服务器返回的是请求在线好友情况的应答包，则取得在线好友，高亮显示
					System.out.println("客户端接收到"+m.getContent());
					//V5--如果从服务器接收的包是在线好友列表请求包的回送包，则取出相应信息
					String content=m.getContent();
					String friends[]=content.split(" ");
					//此应答包的接收者（即向服务器请求好友列表的QQ用户）
					String receiver=m.getReceiver();
					//取得相应QQ号的好友列表界面（因为要调用此界面的高亮显示在线好友方法）
					QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(receiver);
					//更新在线好友--调用QqFriendList里的高亮显示好友方法
					qqFriendList.HightLightOnLineFriend(m);
					
				}
			
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
