/**
 * ������QQ���������������ȴ�ĳ��QQ�ͻ��˵���������
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
			// ��9999�˿ڼ���
			System.out.println("���Ƿ���������9999����������");
			ServerSocket ss = new ServerSocket(9999);
			// �������ȴ�����
			while (true) {
				Socket s = ss.accept();

				// ���տͻ��˷�������Ϣ
				ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
				User u = (User) ois.readObject();
				System.out.println("���������յ��û�" + u.getUser() + " ���룺" + u.getPass());

				ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
				Message m = new Message();

				if (u.getModel().equals("login")) {
						if (new SqlHelper().findByUser(u.getUser())) {
							m.setMsType("1");
							oos.writeObject(m);

							// V3.2-- ����һ���̣߳����߳�ʹ�ÿͻ��������������ͨѶ
							SerConnClientThread scThread = new SerConnClientThread(s);
							// V3.3 �����߳���ӽ���ϣ����
							ManageClientThread.addClientThread(u.getUser(), scThread);
							// V3.2-- �������߳�
							scThread.start();

							// V6--��֪ͨ���������û����������ˡ�
							scThread.notifyOthers(u.getUser());
						} else {
							m.setMsType("2");
							oos.writeObject(m);
							// �ر�����
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
				// //V3.2-- ����һ���̣߳����߳�ʹ�ÿͻ��������������ͨѶ
				// SerConnClientThread scThread=new SerConnClientThread(s);
				// //V3.3 �����߳���ӽ���ϣ����
				// ManageClientThread.addClientThread(u.getUser(), scThread);
				// //V3.2-- �������߳�
				// scThread.start();
				//
				// //V6--��֪ͨ���������û����������ˡ�
				// scThread.notifyOthers(u.getUser());
				//
				// }else{
				// m.setMsType("2");
				// oos.writeObject(m);
				// //�ر�����
				// s.close();
				// }
			} // end of while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
	}

}
