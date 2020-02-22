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
 * 服务器的主页面
 * 
 * @author 林智杰
 *
 */
public class server_view extends JFrame implements ActionListener, MouseListener {
	JPanel main;
	JButton start;// 打开服务器的按钮
	ServerSocket serverSocket;
	JTextArea messageBox;// 显示服务器消息的面板
	JScrollPane scroll;// 主面板的滚动条
	public static String TAG = "Server:";

	public static void main(String[] args) {
		server_view server_view = new server_view();
	}

	public server_view() {
		main = new JPanel();
		start = new JButton("启动服务器");
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
			System.out.println("===服务器已启动，在" + DefaultUtil.SERVER_TCP_PORT + "端口监听！");
			while (true) {
				Socket socket = serverSocket.accept();
				ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
				ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
				User user = (User) input.readObject();
				Message msg = new Message();
				System.out.println(TAG.toString() + user.getUserName() + "用户请求登录");
				if (JdbcUtil.mysql_login(user)) {

					msg.setMsgtype(DefaultUtil.MSG_LOGIN_SUCCESS);// 登录成功
					output.writeObject(msg);
					// 登录成功的同时服务器应该建立相应的线程处理服务器和客户端的数据传输
					ServerThread server = new ServerThread(socket);
					server.start();
					SThreadMag.addCThread(user.getUserName(), server);// 添加到线程hashmap中
					Message msg1 = new Message();// 通知其他在线用户该用户上线的消息
					msg1.setUserName(user.getUserName());
					server.sendToAll(msg1, DefaultUtil.MSG_LOGIN_SUCCESS);// 通知所有在线用户该用户已经上线了
					System.out.println(TAG.toString() + user.getUserName() + "用户登录成功！");

				} else {
					msg.setMsgtype(DefaultUtil.MSG_LOGIN_FAIL);// 登录失败
					System.out.println(TAG.toString() + user.getUserName() + "用户登录失败！");
					output.writeObject(msg);
					socket.close();
				}
			}

		} catch (Exception e) {
		}

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			// 启动服务器
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
