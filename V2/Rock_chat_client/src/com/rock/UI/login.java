package com.rock.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.rock.Util.DefaultUtil;
import com.rock.entity.Message;
import com.rock.entity.User;
import com.rock.method.CThreadMag;
import com.rock.method.MainVMag;

/**
 * @author 林智杰 这个是客户端的登录页面
 */
public class login extends JFrame implements ActionListener {
	JLabel label_north;// 北部
	JPanel panel_center;// 中部
	JLabel Label_text, Label_pass;
	JTextField text;// 用户名文本框
	JPasswordField password;// 密码框
	JPanel panel_south;// 南部
	JButton submit, cancel;// 登录,取消按钮
	public static String TAG = "Client:";

	public static void main(String[] args) {

		login mylogin = new login();
	}

	/**
	 * 登录页面的布局
	 */
	public login() {
		label_north = new JLabel("用户登录", JLabel.CENTER);
		label_north.setFont(new java.awt.Font("楷体", 1, 25));
		label_north.setBackground(Color.BLUE);
		panel_center = new JPanel();
		panel_center.setBounds(20, 70, 250, 150);
		Label_text = new JLabel("用户名:", JLabel.CENTER);
		Label_pass = new JLabel("密  码:", JLabel.CENTER);
		Label_text.setFont(new java.awt.Font("楷体", 1, 18));
		Label_pass.setFont(new java.awt.Font("楷体", 1, 18));
		text = new JTextField(15);
		password = new JPasswordField(15);
		panel_center.add(Label_text);
		panel_center.add(text);
		panel_center.add(Label_pass);
		panel_center.add(password);

		panel_south = new JPanel();
		submit = new JButton("登录");
		submit.addActionListener(this);
		cancel = new JButton("取消");
		cancel.addActionListener(this);
		panel_south.add(submit);
		panel_south.add(cancel);
		this.add(label_north, BorderLayout.NORTH);
		this.add(panel_center, BorderLayout.CENTER);
		this.add(panel_south, BorderLayout.SOUTH);

		this.setTitle("登录界面");
		this.setSize(300, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	@SuppressWarnings("unused")
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			User user = new User();
			user.setPassword(new String(password.getPassword()));
			user.setUserName(text.getText().trim());
			// 把用户输入的User的信息发送给服务器进行验证
			if (com.rock.method.login_check.logincheck(user)) {
				System.out.println(TAG.toString() + user.getUserName() + "用户登录成功！");
				try {
					mainFrame main = new mainFrame(user.getUserName());
					MainVMag.addmainFrame(user.getUserName(), main);
					System.out.println(TAG.toString() + "新建主页面");
					ObjectOutputStream output = new ObjectOutputStream(
							CThreadMag.getCThread(user.getUserName()).getsocket().getOutputStream());
					Message msg = new Message();
					msg.setMsgtype(DefaultUtil.MSG_USER_LIST);
					msg.setUserName(user.getUserName());
					msg.setData("请求获得在线用户列表");
					output.writeObject(msg);
					System.out.println(TAG.toString() + "请求初始化页面");

				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // 新建主页面
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "用户名密码错误或者用户已经在其他地方登录");
				System.out.println(TAG.toString() + user.getUserName() + "用户登录失败！");
			}

		} else if (e.getSource() == cancel) {

			this.dispose();
		}
	}
}
