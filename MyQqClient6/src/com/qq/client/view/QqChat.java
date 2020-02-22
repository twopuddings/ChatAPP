/**
 *�ͺ���������� 
 *V3.3��Ϊ�ͻ���Ҫ���ڶ�ȡ״̬��������ǰ�������һ���̡߳�
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
		btn=new JButton("����");
		btn.addActionListener(this);//V3.2--�����Ͱ�ť����¼�������
		p.add(txt);
		p.add(btn);
		
		//������ܣ���������л�������
		this.setSize(350,200);
		this.setLocation(1100,700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.setTitle(userNo+"���ں�"+friendNo+"����");
		this.setIconImage(new ImageIcon("image/qq.gif").getImage());
		
		//�������ӽ����
		this.add(txtArea,"Center");
		this.add(p,"South");
		
	}

	//V3.2
	@Override
	public void actionPerformed(ActionEvent e) {
		//����û�����ˡ����͡���ť
		if(e.getSource()==btn){
			//����һ��������Ϣ��
			Message m=new Message();
			m.setMsType(MessageType.message_comm_mess);
			m.setSender(this.userNo);
			m.setReceiver(this.friendNo);
			m.setContent(txt.getText());
			m.setSendTime(new Date().toString());
			//�����������Ϣ�����͸�������(��Ҫһ��socket���󣬶�socket����ֻ��QqClientConnServer����)
			try {
				//V4--- �޸Ļ�ȡsocket�����;��
				//ObjectOutputStream oos=new ObjectOutputStream(QqClientConnServer.s.getOutputStream());//V4--���»�ȡs
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConnServerThread.getClientConnServerThread(userNo).getS().getOutputStream());
				oos.writeObject(m);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	//V4-9дһ��������������ʾ��Ϣ
	public void showMessage(Message m)
	{
		String info=m.getSender()+" �� "+m.getReceiver()+"˵��"+m.getContent()+"\r\n";
    	this.txtArea.append(info);
	}
	
	
//V3.3 V4--ȥ��QqChat��Ϊ�߳��࣬��Ӧ�ؾ�ȥ�����run������
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
//		while(true){
//			// ��ȡ������������͵ȴ���
//			try {
//				ObjectInputStream ois=new ObjectInputStream(QqClientConnServer.s.getInputStream());
//				Message m=(Message)ois.readObject();
//				//��ʾ
//				String info=m.getSender()+" �� "+m.getReceiver()+"˵��"+m.getContent()+"\r\n";
//				this.txtArea.append(info);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
}
