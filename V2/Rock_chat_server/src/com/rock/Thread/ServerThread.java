package com.rock.Thread;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import com.rock.Util.DefaultUtil;
import com.rock.entity.Message;
import com.rock.entity.User;
import com.rock.method.SThreadMag;

/**
 * 这个是服务器处理客户端的线程
 * 
 * @author 林智杰
 *
 */
public class ServerThread extends Thread {
	private Socket socket;
	public static String TAG = "Server:";

	public Socket getsocket() {
		return socket;
	}

	public ServerThread(Socket socket) {
		this.socket = socket;
	}

	public ServerThread() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 群发的函数
	 * 
	 * @param MSG
	 * @param type
	 */
	public static void sendToAll(Message MSG, String type) {
		HashMap<String, ServerThread> SThreadmap = SThreadMag.SThreadmap;
		Iterator it = SThreadmap.keySet().iterator();
		while (it.hasNext()) {
			String user_line = it.next().toString();
			// 发送群聊信息
			if (type == DefaultUtil.MSG_GROUP) {
				try {
					ObjectOutputStream output = new ObjectOutputStream(
							SThreadMag.getSThread(user_line).socket.getOutputStream());
					Message msg1 = new Message();
					msg1.setMsgtype(DefaultUtil.MSG_GROUP);
					msg1.setData(MSG.getData());
					msg1.setRuserName(user_line);
					output.writeObject(msg1);
					output.flush();
					System.out.println(TAG.toString() + "通知在线用户群聊消息！" + msg1.getData());
				} catch (Exception e) {
				}
			}

			else {
				// 处理用户上下线的消息
				String user = MSG.getUserName();
				Message msg = new Message();
				msg.setData(user);
				msg.setUserName(user);
				msg.setRuserName(user_line);
				// 用户上线的时候通知所有其他的在线用户
				if (type == DefaultUtil.MSG_LOGIN_SUCCESS) {
					msg.setMsgtype(DefaultUtil.MSG_UPDATE_USER);// 有用户登录的信息
					System.out.println(TAG.toString() + "通知在线用户" + user_line + "用户" + user + "上线了！");
					try {
						ObjectOutputStream output = new ObjectOutputStream(
								SThreadMag.getSThread(user_line).socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
					} catch (Exception e) {
					}
				}
				// 用户下线的时候通知其他的在线用户
				else if (type == DefaultUtil.MSG_LOGIN_OUT) {
					msg.setMsgtype(DefaultUtil.MSG_LOGIN_OUT);// 有用户退出登录的信息
					System.out.println(TAG.toString() + "通知在线用户" + user_line + "用户" + user + "退出登录了！");
					try {
						ObjectOutputStream output = new ObjectOutputStream(
								SThreadMag.getSThread(user_line).socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
					} catch (Exception e) {
					}

				} // 用户下线
			}

		}
	}

	public void run() {
		while (true) {
			try {
				ObjectInputStream input;
				input = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) input.readObject();
				System.out.println(TAG.toString() + "请求类型" + msg.getMsgtype());
				// 根据不同的消息类型进行不同的处理
				// 初始化用户了列表
				if (msg.getMsgtype().equals(DefaultUtil.MSG_USER_LIST)) {

					Message msg2 = new Message();
					msg2.setMsgtype(DefaultUtil.MSG_UPDATE_USER);
					msg2.setData(SThreadMag.getOnlineUsers());
					msg2.setRuserName(msg.getUserName());
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(msg2);
					output.flush();
					SThreadMag.visit();
					System.out.println(TAG.toString() + msg.getUserName() + "请求初始化在线用户的列表！");
				}
				// 处理用户聊天的信息
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CHAT_COM)) {
					ServerThread server = SThreadMag.getSThread(msg.getRuserName());// 和这条消息的发送者进行消息传输的服务端线程
					if (server != null) {
						ObjectOutputStream output = new ObjectOutputStream(server.socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
						System.out.println(TAG.toString() + msg.getUserName() + "请求和" + msg.getRuserName() + "进行聊天");
					} else {

						Message msg2 = new Message();
						msg2.setMsgtype(DefaultUtil.MSG_CHAT_FAIL);
						msg2.setRuserName(msg.getUserName());
						ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
						output.writeObject(msg2);
						output.flush();
					}
				} else if (msg.getMsgtype().equals(DefaultUtil.MSG_SHACK)) {
					ServerThread server = SThreadMag.getSThread(msg.getRuserName());// 和这条消息的发送者进行消息传输的服务端线程
					if (server != null) {
						ObjectOutputStream output = new ObjectOutputStream(server.socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
						System.out.println(TAG.toString() + msg.getUserName() + "发送抖动给" + msg.getRuserName());
					}
				}
				// 收到客户端关闭socket的时候的处理函数
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CLOSE)) {
					ServerThread server = SThreadMag.getSThread(msg.getUserName());
					SThreadMag.delCThread(msg.getUserName());
					sendToAll(msg, DefaultUtil.MSG_LOGIN_OUT);// 告诉所以其他的在线用户该用户下线了
					System.out.println(TAG.toString() + msg.getUserName() + "退出聊天系统!");
					break;

				}
				// 用户群聊的消息
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_GROUP)) {
					sendToAll(msg, DefaultUtil.MSG_GROUP);
				}
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
