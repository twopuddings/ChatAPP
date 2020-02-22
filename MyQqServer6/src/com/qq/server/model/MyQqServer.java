/**
 * 真正的QQ服务器：监听，等待某个QQ客户端的连接请求
 */
package com.qq.server.model;

import java.net.*;
import java.io.*;
import java.util.*;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.server.db.SqlHelper;
import com.qq.server.tools.AddQqUser;

public class MyQqServer {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public MyQqServer() {
		try {
			// 在9999端口监听
			System.out.println("我是服务器，在9999监听。。。");
			ServerSocket ss = new ServerSocket(9999);
			// 阻塞，等待连接
			while (true) {
				Socket s = ss.accept();

				// 接收客户端发来的信息
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("服务器接收到用户" + u.getUser() + " 密码：" + u.getPass());

				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				Message m = new Message();

				if (u.getModel().equals("login")) {
						if (new SqlHelper().findByUser(u.getUser())) {
							m.setMsType("1");
							oos.writeObject(m);

							// V3.2-- 单开一个线程，该线程使该客户端与服务器保持通讯
							SerConnClientThread scThread = new SerConnClientThread(s);
							// V3.3 将此线程添加进哈希表中
							ManageClientThread.addClientThread(u.getUser(), scThread);
							// V3.2-- 启动该线程
							scThread.start();

							// V6--并通知其他在线用户，我在线了。
							scThread.notifyOthers(u.getUser());
						} else {
							m.setMsType("2");
							oos.writeObject(m);
							// 关闭连接
							s.close();
						}

				}else if(u.getModel().equals("register")){
					if(new AddQqUser().AddQqUser(u)){
						m.setMsType("6");
						oos.writeObject(m);
						s.close();
					}else{
						m.setMsType("7");
						oos.writeObject(m);
						s.close();
					}
				}

				// if(u.getPass().equals("123456")){
				// m.setMsType("1");
				// oos.writeObject(m);
				//
				// //V3.2-- 单开一个线程，该线程使该客户端与服务器保持通讯
				// SerConnClientThread scThread=new SerConnClientThread(s);
				// //V3.3 将此线程添加进哈希表中
				// ManageClientThread.addClientThread(u.getUser(), scThread);
				// //V3.2-- 启动该线程
				// scThread.start();
				//
				// //V6--并通知其他在线用户，我在线了。
				// scThread.notifyOthers(u.getUser());
				//
				// }else{
				// m.setMsType("2");
				// oos.writeObject(m);
				// //关闭连接
				// s.close();
				// }
			} // end of while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

}
