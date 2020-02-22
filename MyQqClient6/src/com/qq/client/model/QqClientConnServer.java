package com.qq.client.model;

import com.qq.client.tools.ClientConnServerThread;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.common.*;
import java.net.*;
import java.io.*;
public class QqClientConnServer {

	public  Socket s; //V3.2设置为 静态变量  //V4去掉静态属性
	//客户端发送第一次请求
	public boolean sendLoginInfoToServer(Object o)
	{
		boolean b=false;
		try{
			s=new Socket("127.0.0.1",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			//接收一个回应
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//验证用户登录的地方
			if(ms.getMsType().equals("1")){
				//V4--创建一个该QQ号和服务器端保持通讯连接的线程
				ClientConnServerThread ccst=new ClientConnServerThread(s);
				ccst.start();//启动该线程
				ManageClientConnServerThread .addClientConnServerThread(((User)o).getUser(), ccst);//将这个线程保存到线程管理哈希表中去
				
				b=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return b;
	}
}
