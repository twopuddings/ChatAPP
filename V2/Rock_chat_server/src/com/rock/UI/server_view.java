package com.rock.UI;

import javax.swing.*;

import com.rock.Thread.ServerThread;
import com.rock.Util.DefaultUtil;
import com.rock.Util.JdbcUtil;
import com.rock.entity.Message;
import com.rock.entity.User;
import com.rock.method.SThreadMag;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * ����������ҳ��
 * 
 * @author ���ǽ�
 *
 */
public class server_view extends JFrame implements ActionListener, MouseListener {
	JPanel main;
	JButton start;// �򿪷������İ�ť
	ServerSocket serverSocket;
	JTextArea messageBox;// ��ʾ��������Ϣ�����
	JScrollPane scroll;// �����Ĺ�����
	public static String TAG = "Server:";

	public static void main(String[] args) {
		server_view server_view = new server_view();
	}

	public server_view() {
		main = new JPanel();
		start = new JButton("����������");
		start.addActionListener(this);
		main.add(start);
		messageBox = new JTextArea();
		messageBox.setLineWrap(true);
		messageBox.setEditable(false);
		scroll = new JScrollPane(messageBox);

		this.add(main, BorderLayout.NORTH);
		this.add(scroll, BorderLayout.CENTER);
		this.setSize(200, 100);
		this.setLocation(318, 186);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void startService() throws Exception {
		try {
			ServerSocket serverSocket = new ServerSocket(DefaultUtil.SERVER_TCP_PORT);
			System.out.println("===����������������" + DefaultUtil.SERVER_TCP_PORT + "�˿ڼ�����");
			while (true) {
				Socket socket = serverSocket.accept();
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				User user = (User) input.readObject();
				Message msg = new Message();
				System.out.println(TAG.toString() + user.getUserName() + "�û������¼");
				if (JdbcUtil.mysql_login(user)) {

					msg.setMsgtype(DefaultUtil.MSG_LOGIN_SUCCESS);// ��¼�ɹ�
					output.writeObject(msg);
					// ��¼�ɹ���ͬʱ������Ӧ�ý�����Ӧ���̴߳���������Ϳͻ��˵����ݴ���
					ServerThread server = new ServerThread(socket);
					server.start();
					SThreadMag.addCThread(user.getUserName(), server);// ��ӵ��߳�hashmap��
					Message msg1 = new Message();// ֪ͨ���������û����û����ߵ���Ϣ
					msg1.setUserName(user.getUserName());
					server.sendToAll(msg1, DefaultUtil.MSG_LOGIN_SUCCESS);// ֪ͨ���������û����û��Ѿ�������
					System.out.println(TAG.toString() + user.getUserName() + "�û���¼�ɹ���");

				} else {
					msg.setMsgtype(DefaultUtil.MSG_LOGIN_FAIL);// ��¼ʧ��
					System.out.println(TAG.toString() + user.getUserName() + "�û���¼ʧ�ܣ�");
					output.writeObject(msg);
					socket.close();
				}
			}

		} catch (Exception e) {
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			// ����������
			try {
				startService();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getClickCount() == 2) {
			// openGroup();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
