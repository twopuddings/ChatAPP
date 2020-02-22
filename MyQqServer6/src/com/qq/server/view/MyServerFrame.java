/**
 * 服务器主界面
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
		btn1=new JButton("启动服务器");
		btn1.addActionListener(this);//V3.2--给“启动服务器”添加事件监听器
		btn2=new JButton("关闭服务器");
		p.add(btn1);
		p.add(btn2);
		
		//对框架进行设置
		this.setLocation(1000,700);
		this.setSize(500,400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		//将组件添加到框架
		this.add(p);
	}

	//V3.2--“启动服务器"事件处理（目的为接收客户端的聊天信息）
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==btn1){
			new MyQqServer();
		}
	}
}
