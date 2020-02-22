/**
 * �ҵĺ����б�(����İ���ˡ�������)
 * V3.3
 */
package com.qq.client.view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.qq.client.tools.ManageQqChat;
import com.qq.common.Message;

public class QqFriendList extends JFrame implements ActionListener,MouseListener{

	CardLayout cl;
	JPanel pFriend,pStranger,pBlack;
	String userNo;
	
	/**
	 * ��һ�ſ�Ƭ�������б�
	 */
	JPanel pFriend_p1,pFriend_p2;
	JButton pFriend_btnFriend,pFriend_btnStranger,pFriend_btnBlack;
	JScrollPane pFriend_jsp;
	JLabel[] labs_p1;
	/**
	 * �ڶ��ſ�Ƭ��İ����
	 */
    JPanel pStranger_p1,pStranger_p2;
    JButton pStranger_btnFriend,pStranger_btnStranger,pStranger_btnBlack;
    JScrollPane pStranger_jsp;
	
	/**
	 * �����ſ�Ƭ��������
	 */
    JPanel pBlack_p1,pBlack_p2;
    JButton pBlack_btnFriend,pBlack_btnStranger,pBlack_btnBlack;
    JScrollPane pBlack_jsp;
	
	public static void main(String[] args) {
		//new QqFriendList("1");

	}
	public QqFriendList(String userNo)
	{
		
		this.userNo=userNo;
		/**
		 * �����һ�ſ�Ƭ(��ʾ�����б�)
		 */
		pFriend=new JPanel(new BorderLayout());
		
		//��������ť���ҵĺ��ѣ�
		pFriend_btnFriend=new JButton("�ҵĺ���");
		//�м䣺�ٶ���50������
		pFriend_p1=new JPanel(new GridLayout(50,1,4,4));
		pFriend_jsp=new JScrollPane(pFriend_p1);
		//��ʼ��50������
		labs_p1=new JLabel[50];
		for(int i=0;i<labs_p1.length;i++)
		{
			labs_p1[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			
			//V5-1--����QQ�û��Լ��������˶�������
			labs_p1[i].setEnabled(false);
			if(labs_p1[i].getText().equals(userNo))
			{
				labs_p1[i].setEnabled(true);
			}
			
			//��ÿ�����������������������Ƶ����������ʾ
			labs_p1[i].addMouseListener(this);
			pFriend_p1.add(labs_p1[i]);
		}
		
		
		//�ϲ���������ť��İ���ˣ���������
		pFriend_p2=new JPanel(new GridLayout(2,1));
		pFriend_btnStranger=new JButton("İ����");
		pFriend_btnStranger.addActionListener(this);
		
		pFriend_btnBlack=new JButton("������");
		pFriend_btnBlack.addActionListener(this);
		
		pFriend_p2.add(pFriend_btnStranger);
		pFriend_p2.add(pFriend_btnBlack);
		
		//���������в����ϲ��ؼ������һ�ſ�Ƭ��
		pFriend.add(pFriend_btnFriend,"North");
		pFriend.add(pFriend_jsp,"Center");
		pFriend.add(pFriend_p2,"South");
		
		/**
		 * ����ڶ��ſ�Ƭ����ʾİ���ˣ�
		 */
		pStranger=new JPanel(new BorderLayout());
		//��������ť���ҵĺ��ѡ�İ���ˣ�
		pStranger_p1=new JPanel(new GridLayout(2,1));
		pStranger_btnFriend=new JButton("�ҵĺ���");
		pStranger_btnFriend.addActionListener(this);
		
		pStranger_btnStranger=new JButton("İ����");
		pStranger_p1.add(pStranger_btnFriend);
		pStranger_p1.add(pStranger_btnStranger);
		
		//�м䣺�ٶ���20��İ����
		pStranger_p2=new JPanel(new GridLayout(50,1,4,4));
		pStranger_jsp=new JScrollPane(pStranger_p2);
		//��ʼ��20��İ����
		JLabel[] pStranger_labs=new JLabel[50];
		for(int i=0;i<pStranger_labs.length;i++)
		{
			pStranger_labs[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			pStranger_p2.add(pStranger_labs[i]);
		}
		
		//�ϲ�����ť����������
		pStranger_btnBlack=new JButton("������");
		pStranger_btnBlack.addActionListener(this);
		
		//���������в����ϲ��ؼ�����ڶ��ſ�Ƭ��
		pStranger.add(pStranger_p1,"North");
		pStranger.add(pStranger_jsp,"Center");
		pStranger.add(pStranger_btnBlack,"South");
		
		
		/**
		 * ��������ſ�Ƭ����ʾ��������
		 */
		pBlack=new JPanel(new BorderLayout());
		//��������ť���ҵĺ��ѡ�İ���ˡ���������
		pBlack_p1=new JPanel(new GridLayout(3,1));
		pBlack_btnFriend=new JButton("�ҵĺ���");
		pBlack_btnFriend.addActionListener(this);
		
		pBlack_btnStranger=new JButton("İ����");
		pBlack_btnStranger.addActionListener(this);
		
		pBlack_btnBlack=new JButton("������");
		pBlack_p1.add(pBlack_btnFriend);
		pBlack_p1.add(pBlack_btnStranger);
		pBlack_p1.add(pBlack_btnBlack);
		
		//�м䣺�ٶ���10��������
		pBlack_p2=new JPanel(new GridLayout(20,1,4,4));
		pBlack_jsp=new JScrollPane(pBlack_p2);
		//��ʼ��10��������
		JLabel[] pBlack_labs=new JLabel[20];
		for(int i=0;i<pBlack_labs.length;i++)
		{
			pBlack_labs[i]=new JLabel(i+1+"",new ImageIcon("image/mm.jpg"),JLabel.LEFT);
			pBlack_p2.add(pBlack_labs[i]);
		}
		
		//���������в��ؼ���������ſ�Ƭ��
		pBlack.add(pBlack_p1,"North");
		pBlack.add(pBlack_p2,"Center");
	
		
		/**
		 * ������ܣ���������
		 */
		this.setLocation(1100,300);
		this.setSize(180,500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		this.setTitle(userNo);
		//this.add(pStranger);
		
        //��ʼ����Ƭ
		cl=new CardLayout();
		//���ÿ��Ϊ��Ƭ����
		this.setLayout(cl);
		//�����ſ�Ƭ��ӵ������
		this.add(pFriend,"1");
		this.add(pStranger,"2");
		this.add(pBlack,"3");
		
	}
	
	//V5�������ߺ��������������ʾ���ߺ���
	public void HightLightOnLineFriend(Message m)
	{
		String onLineFriends[]=m.getContent().split(" ");
		System.out.println(m.getContent());
		for(int i=0;i<onLineFriends.length;i++)
		{
			
			labs_p1[Integer.parseInt(onLineFriends[i])-1].setEnabled(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//���������Ѱ�ť,��Ƭ������ ��ʾ��һ�ſ�Ƭ�����ѿ�Ƭ��������������ʾ������Ƭ������
		if(e.getSource()==pStranger_btnFriend || e.getSource()==pBlack_btnFriend)
		{
			cl.show(this.getContentPane(), "1");
		}else if(e.getSource()==pFriend_btnStranger || e.getSource()==pBlack_btnStranger)
		{
			cl.show(this.getContentPane(), "2");
		}else if(e.getSource()==pFriend_btnBlack || e.getSource()==pStranger_btnBlack)
		{
			cl.show(this.getContentPane(), "3");
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		//��Ӧ˫���¼�
		if(e.getClickCount()==2)
		{
			//�õ����ѵı��
			String friendNo=((JLabel)e.getSource()).getText();
			//System.out.println("�������"+friendNo+"����");
			QqChat qqChat=new QqChat(userNo,friendNo);
			
			//V4-8  �����������뵽���������
			ManageQqChat.addQqChat(userNo+" "+friendNo, qqChat);
			
			
			//V4-Qqchat�Ѿ������߳����ˣ�����Ҫ�����ˡ�
//			//V3.3--���������̣߳����ֽ��շ�����ת��������������
//			Thread t=new Thread(qqChat);
//			t.start();
			
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jlab=(JLabel)e.getSource();
		jlab.setForeground(Color.red);
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		JLabel jlab=(JLabel)e.getSource();
		jlab.setForeground(Color.black);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
