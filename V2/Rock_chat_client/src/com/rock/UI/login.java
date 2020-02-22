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
 * @author ���ǽ� ����ǿͻ��˵ĵ�¼ҳ��
 */
public class login extends JFrame implements ActionListener {
	JLabel label_north;// ����
	JPanel panel_center;// �в�
	JLabel Label_text, Label_pass;
	JTextField text;// �û����ı���
	JPasswordField password;// �����
	JPanel panel_south;// �ϲ�
	JButton submit, cancel;// ��¼,ȡ����ť
	public static String TAG = "Client:";

	public static void main(String[] args) {

		login mylogin = new login();
	}

	/**
	 * ��¼ҳ��Ĳ���
	 */
	public login() {
		label_north = new JLabel("�û���¼", JLabel.CENTER);
		label_north.setFont(new java.awt.Font("����", 1, 25));
		label_north.setBackground(Color.BLUE);
		panel_center = new JPanel();
		panel_center.setBounds(20, 70, 250, 150);
		Label_text = new JLabel("�û���:", JLabel.CENTER);
		Label_pass = new JLabel("��  ��:", JLabel.CENTER);
		Label_text.setFont(new java.awt.Font("����", 1, 18));
		Label_pass.setFont(new java.awt.Font("����", 1, 18));
		text = new JTextField(15);
		password = new JPasswordField(15);
		panel_center.add(Label_text);
		panel_center.add(text);
		panel_center.add(Label_pass);
		panel_center.add(password);

		panel_south = new JPanel();
		submit = new JButton("��¼");
		submit.addActionListener(this);
		cancel = new JButton("ȡ��");
		cancel.addActionListener(this);
		panel_south.add(submit);
		panel_south.add(cancel);
		this.add(label_north, BorderLayout.NORTH);
		this.add(panel_center, BorderLayout.CENTER);
		this.add(panel_south, BorderLayout.SOUTH);

		this.setTitle("��¼����");
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
			// ���û������User����Ϣ���͸�������������֤
			if (com.rock.method.login_check.logincheck(user)) {
				System.out.println(TAG.toString() + user.getUserName() + "�û���¼�ɹ���");
				try {
					mainFrame main = new mainFrame(user.getUserName());
					MainVMag.addmainFrame(user.getUserName(), main);
					System.out.println(TAG.toString() + "�½���ҳ��");
					ObjectOutputStream output = new ObjectOutputStream(
							CThreadMag.getCThread(user.getUserName()).getsocket().getOutputStream());
					Message msg = new Message();
					msg.setMsgtype(DefaultUtil.MSG_USER_LIST);
					msg.setUserName(user.getUserName());
					msg.setData("�����������û��б�");
					output.writeObject(msg);
					System.out.println(TAG.toString() + "�����ʼ��ҳ��");

				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // �½���ҳ��
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "�û��������������û��Ѿ��������ط���¼");
				System.out.println(TAG.toString() + user.getUserName() + "�û���¼ʧ�ܣ�");
			}

		} else if (e.getSource() == cancel) {

			this.dispose();
		}
	}
}
