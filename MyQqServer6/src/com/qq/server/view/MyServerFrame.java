/**
 * ������������
 */
package com.qq.server.view;

import javax.swing.*;

import com.qq.server.model.MyQqServer;

import java.awt.*;
import java.awt.event.*;

public class MyServerFrame extends JFrame implements ActionListener{//

	JPanel p;
	JButton btn1,btn2;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyServerFrame myServerFrame=new MyServerFrame();
	}

	public MyServerFrame()
	{
		p=new JPanel();
		btn1=new JButton("����������");
		btn1.addActionListener(this);//V3.2--��������������������¼�������
		btn2=new JButton("�رշ�����");
		p.add(btn1);
		p.add(btn2);
		
		//�Կ�ܽ�������
		this.setLocation(1000,700);
		this.setSize(500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//�������ӵ����
		this.add(p);
	}

	//V3.2--������������"�¼�����Ŀ��Ϊ���տͻ��˵�������Ϣ��
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn1){
			new MyQqServer();
		}
	}
}
