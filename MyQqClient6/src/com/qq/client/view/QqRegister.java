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
	// 定义北部需要的组件
	JLabel jbl1;

	// 定义中部需要的组件
	JPanel p;
	JLabel p_lab1, p_lab2;
	JTextField p_txtQQNum;
	JPasswordField p_txtQQPass;

	// 定义南部需要的组件
	JPanel p1;
	JButton p_btn1;

	public static void main(String[] args) {
		new QqRegister();
	}

	public QqRegister() {

		// 处理北部
		jbl1 = new JLabel(new ImageIcon("image/tou.gif"));

		// 处理中部
		p = new JPanel(new GridLayout(4, 1));
		p_lab1 = new JLabel("QQ号码", JLabel.CENTER);
		p_lab2 = new JLabel("QQ密码", JLabel.CENTER);
		p_txtQQNum = new JTextField();
		p_txtQQPass = new JPasswordField();
		p.add(p_lab1);
		p.add(p_txtQQNum);
		p.add(p_lab2);
		p.add(p_txtQQPass);

		// 处理南部
		p1 = new JPanel(new GridLayout(1, 2));
		p_btn1 = new JButton("注册");
		p_btn1.addActionListener(this);
		p1.add(p_btn1);

		// 将北部、中部、南部控件加入到框架中
		this.add(jbl1, "North");
		this.add(p, "Center");
		this.add(p1, "South");

		// 对框架进行设置
		this.setLocation(800, 350);
		this.setSize(350, 240);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// 如果用户点击了注册
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
