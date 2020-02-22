package com.rock.method;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import com.rock.Thread.ClientThread;
import com.rock.Util.DefaultUtil;
import com.rock.entity.*;

/**
 * @author 林智杰 这是客户端首次和服务器进行连接的类
 */
public class client_server {

	Socket socket;

	public client_server(Socket socket) {
		this.socket = socket;
	}

	public client_server() {
		// TODO Auto-generated constructor stub
	}

	public boolean send(Object obj) {
		boolean bool = false;
		try {
			socket = new Socket("127.0.0.1", DefaultUtil.SERVER_TCP_PORT);
			ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
			output.writeObject(obj);
			ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
			Message msg = new Message();
			try {
				msg = (Message) input.readObject();// 读取服务器发过来的关于登录结果的类
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (msg.getMsgtype().equals(DefaultUtil.MSG_LOGIN_SUCCESS)) {
				// 登录成功，分配一个专门和服务器连接的线程给该用户
				ClientThread client = new ClientThread(socket);
				// 讲该线程存储起来
				CThreadMag.addCThread(((User) obj).getUserName(), client);// 把用户和相应的客户端的线程一起添加到静态的map
				client.start();// 线程开始运行
				bool = true;

			} // 登录成功
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bool;

	}

}
