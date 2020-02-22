package com.qq.client.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.qq.client.model.QqRegisterUser;
import com.qq.common.User;

public class QqRegister extends JFrame implements ActionListener {
	// ���山����Ҫ�����
	JLabel jbl1;

	// �����в���Ҫ�����
	JPanel p;
	JLabel p_lab1, p_lab2;
	JTextField p_txtQQNum;
	JPasswordField p_txtQQPass;

	// �����ϲ���Ҫ�����
	JPanel p1;
	JButton p_btn1;

	public static void main(String[] args) {
		new QqRegister();
	}

	public QqRegister() {

		// ������
		jbl1 = new JLabel(new ImageIcon("image/tou.gif"));

		// �����в�
		p = new JPanel(new GridLayout(4, 1));
		p_lab1 = new JLabel("QQ����", JLabel.CENTER);
		p_lab2 = new JLabel("QQ����", JLabel.CENTER);
		p_txtQQNum = new JTextField();
		p_txtQQPass = new JPasswordField();
		p.add(p_lab1);
		p.add(p_txtQQNum);
		p.add(p_lab2);
		p.add(p_txtQQPass);

		// �����ϲ�
		p1 = new JPanel(new GridLayout(1, 2));
		p_btn1 = new JButton("ע��");
		p_btn1.addActionListener(this);
		p1.add(p_btn1);

		// ���������в����ϲ��ؼ����뵽�����
		this.add(jbl1, "North");
		this.add(p, "Center");
		this.add(p1, "South");

		// �Կ�ܽ�������
		this.setLocation(800, 350);
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// ����û������ע��
		if (e.getSource() == p_btn1) {
			QqRegisterUser qqRegisterUser = new QqRegisterUser();
			User u = new User();
			u.setUser(p_txtQQNum.getText().trim());
			u.setPass(new String(p_txtQQPass.getPassword()).trim());
			u.setModel("register");
			if (qqRegisterUser.registerUser(u)) {
				new QqRegisterResult(true);
			} else {
				new QqRegisterResult(false);
			}
			this.dispose();
		}
	}
}
