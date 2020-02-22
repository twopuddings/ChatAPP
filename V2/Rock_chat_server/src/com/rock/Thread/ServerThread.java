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
 * ����Ƿ���������ͻ��˵��߳�
 * 
 * @author ���ǽ�
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
	 * Ⱥ���ĺ���
	 * 
	 * @param MSG
	 * @param type
	 */
	public static void sendToAll(Message MSG, String type) {
		HashMap<String, ServerThread> SThreadmap = SThreadMag.SThreadmap;
		Iterator it = SThreadmap.keySet().iterator();
		while (it.hasNext()) {
			String user_line = it.next().toString();
			// ����Ⱥ����Ϣ
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
					System.out.println(TAG.toString() + "֪ͨ�����û�Ⱥ����Ϣ��" + msg1.getData());
				} catch (Exception e) {
				}
			}

			else {
				// �����û������ߵ���Ϣ
				String user = MSG.getUserName();
				Message msg = new Message();
				msg.setData(user);
				msg.setUserName(user);
				msg.setRuserName(user_line);
				// �û����ߵ�ʱ��֪ͨ���������������û�
				if (type == DefaultUtil.MSG_LOGIN_SUCCESS) {
					msg.setMsgtype(DefaultUtil.MSG_UPDATE_USER);// ���û���¼����Ϣ
					System.out.println(TAG.toString() + "֪ͨ�����û�" + user_line + "�û�" + user + "�����ˣ�");
					try {
						ObjectOutputStream output = new ObjectOutputStream(
								SThreadMag.getSThread(user_line).socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
					} catch (Exception e) {
					}
				}
				// �û����ߵ�ʱ��֪ͨ�����������û�
				else if (type == DefaultUtil.MSG_LOGIN_OUT) {
					msg.setMsgtype(DefaultUtil.MSG_LOGIN_OUT);// ���û��˳���¼����Ϣ
					System.out.println(TAG.toString() + "֪ͨ�����û�" + user_line + "�û�" + user + "�˳���¼�ˣ�");
					try {
						ObjectOutputStream output = new ObjectOutputStream(
								SThreadMag.getSThread(user_line).socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
					} catch (Exception e) {
					}

				} // �û�����
			}

		}
	}

	public void run() {
		while (true) {
			try {
				ObjectInputStream input;
				input = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) input.readObject();
				System.out.println(TAG.toString() + "��������" + msg.getMsgtype());
				// ���ݲ�ͬ����Ϣ���ͽ��в�ͬ�Ĵ���
				// ��ʼ���û����б�
				if (msg.getMsgtype().equals(DefaultUtil.MSG_USER_LIST)) {

					Message msg2 = new Message();
					msg2.setMsgtype(DefaultUtil.MSG_UPDATE_USER);
					msg2.setData(SThreadMag.getOnlineUsers());
					msg2.setRuserName(msg.getUserName());
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					output.writeObject(msg2);
					output.flush();
					SThreadMag.visit();
					System.out.println(TAG.toString() + msg.getUserName() + "�����ʼ�������û����б�");
				}
				// �����û��������Ϣ
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CHAT_COM)) {
					ServerThread server = SThreadMag.getSThread(msg.getRuserName());// ��������Ϣ�ķ����߽�����Ϣ����ķ�����߳�
					if (server != null) {
						ObjectOutputStream output = new ObjectOutputStream(server.socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
						System.out.println(TAG.toString() + msg.getUserName() + "�����" + msg.getRuserName() + "��������");
					} else {

						Message msg2 = new Message();
						msg2.setMsgtype(DefaultUtil.MSG_CHAT_FAIL);
						msg2.setRuserName(msg.getUserName());
						ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
						output.writeObject(msg2);
						output.flush();
					}
				} else if (msg.getMsgtype().equals(DefaultUtil.MSG_SHACK)) {
					ServerThread server = SThreadMag.getSThread(msg.getRuserName());// ��������Ϣ�ķ����߽�����Ϣ����ķ�����߳�
					if (server != null) {
						ObjectOutputStream output = new ObjectOutputStream(server.socket.getOutputStream());
						output.writeObject(msg);
						output.flush();
						System.out.println(TAG.toString() + msg.getUserName() + "���Ͷ�����" + msg.getRuserName());
					}
				}
				// �յ��ͻ��˹ر�socket��ʱ��Ĵ�����
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CLOSE)) {
					ServerThread server = SThreadMag.getSThread(msg.getUserName());
					SThreadMag.delCThread(msg.getUserName());
					sendToAll(msg, DefaultUtil.MSG_LOGIN_OUT);// �������������������û����û�������
					System.out.println(TAG.toString() + msg.getUserName() + "�˳�����ϵͳ!");
					break;

				}
				// �û�Ⱥ�ĵ���Ϣ
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
