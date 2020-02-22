package com.qq.client.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class QqRegisterResult extends JFrame implements ActionListener{
	//������Ҫ�����
	JPanel p;
	JLabel p_lab1,p_lab2;
	JButton p_btn1,p_btn2;
	
	public static void main(String[] args) {
		new QqRegisterResult(false);
	}
	
	public QqRegisterResult(boolean b){
		if(b){
			p=new JPanel(new GridLayout(2,1));
			p_lab1=new JLabel("ע��ɹ�",JLabel.CENTER);
			p_btn1=new JButton("ȷ��");
			p_btn1.addActionListener(this);
			p.add(p_lab1);
			p.add(p_btn1);
			this.add(p);
		}else{
			p=new JPanel(new GridLayout(2,1));
			p_lab2=new JLabel("ע��ʧ��",JLabel.CENTER);
			p_btn2=new JButton("ȷ��");
			p_btn2.addActionListener(this);
			p.add(p_lab2);
			p.add(p_btn2);
			this.add(p);
		}
		//�Կ�ܽ�������
		this.setLocation(800,350);
		this.setSize(200,150);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==p_btn1){
			new QqClientLogin();
		}else{
			new QqRegister();
		}
		this.dispose();
	}
}
