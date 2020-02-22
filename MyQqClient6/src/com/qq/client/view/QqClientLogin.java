/**
 * qq�ͻ��˵�¼����
 */
package com.qq.client.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.*;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.*;

import com.qq.client.model.QqClientUser;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.client.tools.ManageQqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;

public class QqClientLogin extends JFrame implements ActionListener{

	//���山����Ҫ�����
	JLabel jbl1;
	
	//�����в���Ҫ�����
	//�в�������JPanel,ѡ����ڹ���
	JTabbedPane jtp;
	JPanel p1,p2,p3;
	JLabel p1_lab1,p1_lab2,p1_lab3,p1_lab4;
	JButton p1_btn1;
	JTextField p1_txtQQNum;
	JPasswordField p1_txtQQPass;
	JCheckBox p1_ch1,p1_ch2;
	
	
	//�����ϲ���Ҫ�����
	JPanel p;
	JButton p_jbtn1,p_jbtn2,p_jbtn3;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		QqClientLogin qqClientLogin=new QqClientLogin();
	}
	
	public QqClientLogin()
	{
		/**
		 *  ������
		 */
		
		jbl1=new JLabel(new ImageIcon("image/tou.gif"));
		
		/**
		 * �����в�
		 */
		//P1���
		p1=new JPanel(new GridLayout(3,3));
		p1_lab1=new JLabel("QQ����",JLabel.CENTER);
		p1_lab2=new JLabel("QQ����",JLabel.CENTER);
		p1_lab3=new JLabel("��������",JLabel.CENTER);
		p1_lab3.setForeground(Color.blue);
		p1_lab4=new JLabel("�������뱣��",JLabel.CENTER);
		p1_btn1=new JButton(new ImageIcon("image/clear.gif"));
		p1_txtQQNum=new JTextField();
		p1_txtQQPass=new JPasswordField();
		p1_ch1=new JCheckBox("�����¼");
		p1_ch2=new JCheckBox("��ס����");
		//���ؼ����뵽P1���
		p1.add(p1_lab1);
		p1.add(p1_txtQQNum);
		p1.add(p1_btn1);
		p1.add(p1_lab2);
		p1.add(p1_txtQQPass);
		p1.add(p1_lab3);
		p1.add(p1_ch1);
		p1.add(p1_ch2);
		p1.add(p1_lab4);
		//p2��壨ʡ��ϸ�ڣ�
		p2=new JPanel();
		//p3��壨ʡ��ϸ�ڣ�
		p3=new JPanel();
		
		//����ѡ�����
		jtp=new JTabbedPane();
		jtp.add("QQ����",p1);
		jtp.add("�ֻ�����",p2);
		jtp.add("�����ʼ�",p3);
		
		/**
		 * �����ϲ�
		 */
		p=new JPanel();
		p_jbtn1=new JButton(new ImageIcon("image/denglu.gif"));
		//����¼��ť����¼�������
		p_jbtn1.addActionListener(this);
		p_jbtn2=new JButton(new ImageIcon("image/quxiao.gif"));
		p_jbtn3=new JButton(new ImageIcon("image/xiangdao.gif"));
		//��ע���򵼰�ť����¼�������
		p_jbtn3.addActionListener(this);
		p.add(p_jbtn1);
		p.add(p_jbtn2);
		p.add(p_jbtn3);
		
		
		/**
		 * ���������в����ϲ��ؼ����뵽�����
		 */
		this.add(jbl1,"North");
		this.add(jtp,"Center");
		this.add(p,"South");
		/**
		 * �Կ�ܽ�������
		 */
		this.setLocation(800,350);
		this.setSize(350,240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//����û�����˵�¼
		if(e.getSource()==p_jbtn1){
			QqClientUser qqClientUser=new QqClientUser();
			User u=new User();
			u.setUser(p1_txtQQNum.getText().trim());
			u.setPass(new String(p1_txtQQPass.getPassword()).trim());
			u.setModel("login");
			//�����¼��֤�ɹ�
			if(qqClientUser.checkUser(u)){
				
				//V5-2--�����������һ��Ҫ�󷵻����ߺ��ѵ������
				try {
					
					//��ǰ���򿪺����б����
					QqFriendList qqFriendList=new QqFriendList(u.getUser());
					//��ǰ��V5�ѵ�¼��QQ���Ѽ��뵽ManageQqFriendList
					ManageQqFriendList.addQqFriendList(u.getUser(), qqFriendList);
					
					
					ObjectOutputStream oos=new ObjectOutputStream
					(ManageClientConnServerThread.getClientConnServerThread(u.getUser()).getS().getOutputStream());
					//����һ��Message�����
					Message m=new Message();
					m.setMsType(MessageType.message_request_onLineFriend);
					//���߷�������Ҫ���������QQ����ĺ���
					m.setSender(u.getUser());
					oos.writeObject(m);
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			
				
				//ͬʱ�رյ�¼����
				this.dispose();
			}else{
				JOptionPane.showMessageDialog(this, "�û����������");
			}
			
		}else if(e.getSource()==p_jbtn3){
			//��ע�����
			new QqRegister();
			//ͬʱ�رյ�¼����
			this.dispose();
		}
	}
	
}
