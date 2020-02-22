package com.rock.Thread;

import java.awt.Color;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Calendar;

import com.rock.UI.GroupFrame;
import com.rock.UI.chatFrame;
import com.rock.UI.mainFrame;
import com.rock.Util.DefaultUtil;
import com.rock.Util.UIUtils;
import com.rock.entity.*;
import com.rock.method.GroupVMag;
import com.rock.method.MainVMag;

/**
 * @author 林智杰 客户端用来和服务器进行连接的线程
 */
public class ClientThread extends Thread {
	private Socket socket;
	private boolean exit = false;

	public void setExit(boolean bl) {
		this.exit = bl;
	}

	public static String TAG = "Client:";

	public Socket getsocket() {
		return socket;
	}

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	public ClientThread() {
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	public void run() {
		try {
			System.out.println(TAG.toString() + "exit的：" + exit);
			while (!exit) {
				ObjectInputStream input;
				input = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) input.readObject();
				// 根据不同的消息类型进行不同的处理
				// 用户信息更新
				if (msg.getMsgtype().equals(DefaultUtil.MSG_UPDATE_USER)) {
					String recevice = msg.getRuserName();
					mainFrame main = (mainFrame) MainVMag.getmainFrame(recevice);
					if (main != null) {
						main.updateUser(msg);
						System.out.println(TAG.toString() + "更新用户列表");
					} else {
						System.out.println(TAG.toString() + "MAIN为空" + recevice);
					}
					System.out.println(TAG.toString() + "服务器传回" + msg.getData() + "用户上线的信息！");
				}
				// 用户聊天信息
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CHAT_COM)) {
					mainFrame main = (mainFrame) MainVMag.getmainFrame(msg.getRuserName());
					UIUtils.playSound("sounds/msg.wav");
					chatFrame chat = main.getchatFrame(msg.getUserName());
					if (chat != null) {
						chat.showMessage(msg);// 聊天内容加载到相应的聊天面板
					} else {
						// 不存在和该用户的聊天页面，新建聊天页面
						chat = new chatFrame(msg.getRuserName(), msg.getUserName());
						main.addchatFrame(msg.getUserName(), chat);
						main.vist();
						System.out.println(
								TAG.toString() + "新建" + msg.getRuserName() + "和" + msg.getUserName() + "的聊天页面");
						chat.showMessage(msg);

					}

				} else if (msg.getMsgtype().equals(DefaultUtil.MSG_SHACK)) {
					mainFrame main = (mainFrame) MainVMag.getmainFrame(msg.getRuserName());
					chatFrame chat = main.getchatFrame(msg.getUserName());
					if (chat != null) {
						String datetime = Calendar.getInstance().getTime().toLocaleString();
						String str = "==============好友发送了一个抖动给你！" + datetime + "================\r\n";
						chat.updateMsgInfo(str, Color.BLACK, false, 14);
						chat.shake();
					} else {
						// 不存在和该用户的聊天页面，新建聊天页面
						chat = new chatFrame(msg.getRuserName(), msg.getUserName());
						main.addchatFrame(msg.getUserName(), chat);
						System.out.println(
								TAG.toString() + "新建" + msg.getRuserName() + "和" + msg.getUserName() + "的聊天页面");
						String datetime = Calendar.getInstance().getTime().toLocaleString();
						String str = "==============好友发送了一个抖动给你！" + datetime + "================\r\n";
						chat.updateMsgInfo(str, Color.BLACK, false, 14);
						chat.shake();

					}
				}
				// 要聊天的对象已经下线
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CHAT_FAIL)) {
					String recevice = msg.getRuserName();
					mainFrame main = (mainFrame) MainVMag.getmainFrame(recevice);
					if (main != null) {
						chatFrame chat = main.getchatFrame(msg.getUserName());
						if (chat != null) {
							String str = "=============用户" + msg.getUserName() + "下线了，请等对方上线之后再联系！=============="
									+ "\r\n";
							chat.updateMsgInfo(str, Color.red, false, 14);
							// main.delchatFrame(msg.getUserName());
						}

					}
				}
				// 用户下线的处理函数
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_LOGIN_OUT)) {
					String recevice = msg.getRuserName();
					mainFrame main = (mainFrame) MainVMag.getmainFrame(recevice);
					if (main != null) {
						main.updateUserOut(msg);// 更新用户列表
						chatFrame chat = main.getchatFrame(msg.getUserName());
						if (chat != null) {
							String str = "=============用户" + msg.getUserName() + "下线了，请等对方上线之后再联系！=============="
									+ "\r\n";
							chat.updateMsgInfo(str, Color.red, false, 14);

						}
						System.out.println(TAG.toString() + "更新用户列表");
					} else {
						System.out.println(TAG.toString() + "MAIN为空" + recevice);
					}
					System.out.println(TAG.toString() + "服务器传回" + msg.getData() + "用户下线的信息！");

				}

				// 收到群聊的消息
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_GROUP)) {
					String recevice = msg.getRuserName();
					GroupFrame group = GroupVMag.getGroupFrame(recevice);
					if (group != null) {
						group.updateMsgInfo(msg.getData(), Color.red, false, 14);
						System.out.println(TAG.toString() + "服务器传回群聊信息！" + msg.getData());
					}

				}
			}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			close();
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
