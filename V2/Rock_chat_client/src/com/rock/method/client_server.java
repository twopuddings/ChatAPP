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
 * @author ���ǽ� ���ǿͻ����״κͷ������������ӵ���
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
				msg = (Message) input.readObject();// ��ȡ�������������Ĺ��ڵ�¼�������
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (msg.getMsgtype().equals(DefaultUtil.MSG_LOGIN_SUCCESS)) {
				// ��¼�ɹ�������һ��ר�źͷ��������ӵ��̸߳����û�
				ClientThread client = new ClientThread(socket);
				// �����̴߳洢����
				CThreadMag.addCThread(((User) obj).getUserName(), client);// ���û�����Ӧ�Ŀͻ��˵��߳�һ����ӵ���̬��map
				client.start();// �߳̿�ʼ����
				bool = true;

			} // ��¼�ɹ�
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
