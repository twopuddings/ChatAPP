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
 * @author ���ǽ� �ͻ��������ͷ������������ӵ��߳�
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
			System.out.println(TAG.toString() + "exit�ģ�" + exit);
			while (!exit) {
				ObjectInputStream input;
				input = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) input.readObject();
				// ���ݲ�ͬ����Ϣ���ͽ��в�ͬ�Ĵ���
				// �û���Ϣ����
				if (msg.getMsgtype().equals(DefaultUtil.MSG_UPDATE_USER)) {
					String recevice = msg.getRuserName();
					mainFrame main = (mainFrame) MainVMag.getmainFrame(recevice);
					if (main != null) {
						main.updateUser(msg);
						System.out.println(TAG.toString() + "�����û��б�");
					} else {
						System.out.println(TAG.toString() + "MAINΪ��" + recevice);
					}
					System.out.println(TAG.toString() + "����������" + msg.getData() + "�û����ߵ���Ϣ��");
				}
				// �û�������Ϣ
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CHAT_COM)) {
					mainFrame main = (mainFrame) MainVMag.getmainFrame(msg.getRuserName());
					UIUtils.playSound("sounds/msg.wav");
					chatFrame chat = main.getchatFrame(msg.getUserName());
					if (chat != null) {
						chat.showMessage(msg);// �������ݼ��ص���Ӧ���������
					} else {
						// �����ں͸��û�������ҳ�棬�½�����ҳ��
						chat = new chatFrame(msg.getRuserName(), msg.getUserName());
						main.addchatFrame(msg.getUserName(), chat);
						main.vist();
						System.out.println(
								TAG.toString() + "�½�" + msg.getRuserName() + "��" + msg.getUserName() + "������ҳ��");
						chat.showMessage(msg);

					}

				} else if (msg.getMsgtype().equals(DefaultUtil.MSG_SHACK)) {
					mainFrame main = (mainFrame) MainVMag.getmainFrame(msg.getRuserName());
					chatFrame chat = main.getchatFrame(msg.getUserName());
					if (chat != null) {
						String datetime = Calendar.getInstance().getTime().toLocaleString();
						String str = "==============���ѷ�����һ���������㣡" + datetime + "================\r\n";
						chat.updateMsgInfo(str, Color.BLACK, false, 14);
						chat.shake();
					} else {
						// �����ں͸��û�������ҳ�棬�½�����ҳ��
						chat = new chatFrame(msg.getRuserName(), msg.getUserName());
						main.addchatFrame(msg.getUserName(), chat);
						System.out.println(
								TAG.toString() + "�½�" + msg.getRuserName() + "��" + msg.getUserName() + "������ҳ��");
						String datetime = Calendar.getInstance().getTime().toLocaleString();
						String str = "==============���ѷ�����һ���������㣡" + datetime + "================\r\n";
						chat.updateMsgInfo(str, Color.BLACK, false, 14);
						chat.shake();

					}
				}
				// Ҫ����Ķ����Ѿ�����
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_CHAT_FAIL)) {
					String recevice = msg.getRuserName();
					mainFrame main = (mainFrame) MainVMag.getmainFrame(recevice);
					if (main != null) {
						chatFrame chat = main.getchatFrame(msg.getUserName());
						if (chat != null) {
							String str = "=============�û�" + msg.getUserName() + "�����ˣ���ȶԷ�����֮������ϵ��=============="
									+ "\r\n";
							chat.updateMsgInfo(str, Color.red, false, 14);
							// main.delchatFrame(msg.getUserName());
						}

					}
				}
				// �û����ߵĴ�����
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_LOGIN_OUT)) {
					String recevice = msg.getRuserName();
					mainFrame main = (mainFrame) MainVMag.getmainFrame(recevice);
					if (main != null) {
						main.updateUserOut(msg);// �����û��б�
						chatFrame chat = main.getchatFrame(msg.getUserName());
						if (chat != null) {
							String str = "=============�û�" + msg.getUserName() + "�����ˣ���ȶԷ�����֮������ϵ��=============="
									+ "\r\n";
							chat.updateMsgInfo(str, Color.red, false, 14);

						}
						System.out.println(TAG.toString() + "�����û��б�");
					} else {
						System.out.println(TAG.toString() + "MAINΪ��" + recevice);
					}
					System.out.println(TAG.toString() + "����������" + msg.getData() + "�û����ߵ���Ϣ��");

				}

				// �յ�Ⱥ�ĵ���Ϣ
				else if (msg.getMsgtype().equals(DefaultUtil.MSG_GROUP)) {
					String recevice = msg.getRuserName();
					GroupFrame group = GroupVMag.getGroupFrame(recevice);
					if (group != null) {
						group.updateMsgInfo(msg.getData(), Color.red, false, 14);
						System.out.println(TAG.toString() + "����������Ⱥ����Ϣ��" + msg.getData());
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
