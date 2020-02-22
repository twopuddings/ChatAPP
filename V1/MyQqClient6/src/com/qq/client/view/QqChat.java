/**
 *和好友聊天界面 
 *V3.3因为客户端要处于读取状态，因此我们把它做成一个线程。
 */

package com.qq.client.view;

import javax.swing.*;
import com.qq.client.tools.*;
import com.qq.client.model.QqClientConnServer;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
public class QqChat extends JFrame implements ActionListener{//V3.2ActionListenerV3.3Runnalble V4

	JTextArea txtArea;
	JPanel p;
	JTextField txt;
	JButton btn;
	
	String userNo;
	String friendNo;
	/**
	 * 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//QqChat qqChat=new QqChat("1","1");
	}

	public QqChat(String userNo,String friendNo){
		this.userNo=userNo;
		this.friendNo=friendNo;
		txtArea=new JTextArea();
		p=new JPanel();
		txt=new JTextField(20);
		btn=new JButton("发送");
		btn.addActionListener(this);//V3.2--给发送按钮添加事件监听器
		p.add(txt);
		p.add(btn);
		
		//创建框架，并对其进行基本设置
		this.setSize(350,200);
		this.setLocation(1100,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.setTitle(userNo+"正在和"+friendNo+"聊天");
		this.setIconImage(new ImageIcon("image/qq.gif").getImage());
		
		//将组件添加进框架
		this.add(txtArea,"Center");
		this.add(p,"South");
		
	}

	//V3.2
	@Override
	public void actionPerformed(ActionEvent e) {
		//如果用户点击了“发送”按钮
		if(e.getSource()==btn){
			//构造一个聊天消息包
			Message m=new Message();
			m.setMsType(MessageType.message_comm_mess);
			m.setSender(this.userNo);
			m.setReceiver(this.friendNo);
			m.setContent(txt.getText());
			m.setSendTime(new Date().toString());
			//将这个聊天消息包发送给服务器(需要一个socket对象，而socket对象只在QqClientConnServer中有)
			try {
				//V4--- 修改获取socket对象的途径
				//ObjectOutputStream oos=new ObjectOutputStream(QqClientConnServer.s.getOutputStream());//V4--重新获取s
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConnServerThread.getClientConnServerThread(userNo).getS().getOutputStream());
				oos.writeObject(m);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	//V4-9写一个方法，用于显示消息
	public void showMessage(Message m)
	{
		String info=m.getSender()+" 对 "+m.getReceiver()+"说："+m.getContent()+"\r\n";
    	this.txtArea.append(info);
	}
	
	
//V3.3 V4--去掉QqChat类为线程类，相应地就去掉这个run方法。
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		while(true){
//			// 读取（如果读不到就等待）
//			try {
//				ObjectInputStream ois=new ObjectInputStream(QqClientConnServer.s.getInputStream());
//				Message m=(Message)ois.readObject();
//				//显示
//				String info=m.getSender()+" 对 "+m.getReceiver()+"说："+m.getContent()+"\r\n";
//				this.txtArea.append(info);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
