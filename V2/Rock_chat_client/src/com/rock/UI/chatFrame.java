package com.rock.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Calendar;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import com.rock.Thread.ClientThread;
import com.rock.Util.*;
import com.rock.entity.Message;
import com.rock.entity.User;
import com.rock.method.CThreadMag;
import com.rock.method.MainVMag;

/**
 * 用户私聊页面
 * 
 * @author 林智杰
 *
 */
public class chatFrame extends JFrame implements ActionListener {
	JTextArea textarea;// 聊天界面
	JTextArea text;// 输入文字的文本框
	JButton sendBtn, cancelBtn, shakeBtn;// 发送按钮
	private JScrollPane msgRecordSP;
	private JScrollPane msgToSendSP;
	JPanel panel, mainPanel;// 文本框和按钮共同的容器
	PrintWriter out;// 输出流

	String owner;// 本人
	String friend;// 聊天的好友

	public chatFrame(String owner, String friend) {
		this.owner = owner;
		this.friend = friend;
		init();
	}

	public void init() {
		JToolBar toolBar = new JToolBar();
		toolBar.setBorderPainted(false);

		textarea = new JTextArea();
		textarea.setLineWrap(true);// 自动换行
		textarea.setEditable(false);
		msgRecordSP = new JScrollPane(textarea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		text = new JTextArea();
		text.setRows(5);
		text.setLineWrap(true);// 自动换行
		text.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMsg();
				}
			}
		});
		msgToSendSP = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel south = new JPanel(new BorderLayout());
		JPanel sthBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		sendBtn = new JButton("发送", UIUtils.getImageIcon("images/send.png"));
		cancelBtn = new JButton("关闭", UIUtils.getImageIcon("images/cancel.png"));
		sthBtnPanel.add(sendBtn);
		sthBtnPanel.add(cancelBtn);
		JToolBar toolBar2 = new JToolBar();
		toolBar2.setBorderPainted(false);
		shakeBtn = new JButton(UIUtils.getImageIcon("images/bell24.png"));
		shakeBtn.setToolTipText("抖动");
		shakeBtn.setFocusable(false);

		toolBar2.add(shakeBtn);
		south.add(toolBar2, BorderLayout.NORTH);
		south.add(msgToSendSP, BorderLayout.CENTER);
		south.add(sthBtnPanel, BorderLayout.SOUTH);

		mainPanel = new JPanel(new BorderLayout(0, 2));
		mainPanel.add(msgRecordSP, BorderLayout.CENTER);
		mainPanel.add(south, BorderLayout.SOUTH);

		sendBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		shakeBtn.addActionListener(this);

		Container content = this.getContentPane();
		content.setLayout(new BorderLayout());
		content.add(toolBar, BorderLayout.NORTH);
		content.add(mainPanel, BorderLayout.CENTER);
		this.setIconImage(UIUtils.getIconImage("images/user_24.png"));
		this.setTitle(owner + "和" + friend + "聊天");// 这个要改成实时的聊天界面
		this.setSize(500, 500);
		this.setResizable(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}

	public static void main(String[] args) {
		// new chatFrame();

	}

	public synchronized void showMessage(Message msg) {

		String datetime = Calendar.getInstance().getTime().toLocaleString();
		String head = msg.getUserName() + "对我说:";
		String chatmsg = msg.getData().toString() + "\n" + datetime + "\r\n";
		// textarea.append(chatmsg);
		updateMsgInfo(head, Color.blue, true, 14);
		updateMsgInfo(chatmsg, Color.BLACK, false, 14);
		textarea.setCaretPosition(textarea.getDocument().getLength()); // 设置自动滚动到最后
	}

	public void addmsg(String msg) {
		textarea.append(msg);
	}

	/**
	 * 用来更新消息区域的普通格式消息的方法
	 * 
	 * @param str
	 *            设置消息文字内容
	 * @param col
	 *            设置字体颜色
	 * @param isBold
	 *            设置是否为粗体
	 * @param fontSize
	 *            设置字体大小
	 * @param icon
	 *            设置在消息首部要插入的图标，如果为null则表示不插入图标
	 */
	public void updateMsgInfo(String str, Color col, boolean isBold, int fontSize) {
		SimpleAttributeSet attrSet = new SimpleAttributeSet();
		StyleConstants.setForeground(attrSet, col); // 颜色
		if (isBold) {
			StyleConstants.setBold(attrSet, true);
		} // 字体类型
		StyleConstants.setFontSize(attrSet, fontSize); // 字体大小
		Document doc = textarea.getDocument();
		try {
			doc.insertString(doc.getLength(), str, attrSet);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void sendShake() {
		Message msg = new Message();
		msg.setUserName(owner);
		msg.setRuserName(friend);
		msg.setMsgtype(DefaultUtil.MSG_SHACK);
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					CThreadMag.getCThread(owner).getsocket().getOutputStream());
			output.writeObject(msg);
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String datetime = Calendar.getInstance().getTime().toLocaleString();
		String str = "==============我发送了一个抖动给好友" + datetime + "================\r\n";
		updateMsgInfo(str, Color.BLACK, false, 14);
		textarea.setCaretPosition(textarea.getDocument().getLength()); // 设置自动滚动到最后
	}

	private void sendMsg() {
		Message msg = new Message();
		String data = text.getText().trim();
		msg.setUserName(owner);
		msg.setRuserName(friend);
		msg.setData(text.getText().trim());
		msg.setMsgtype(DefaultUtil.MSG_CHAT_COM);
		try {
			ObjectOutputStream output = new ObjectOutputStream(
					CThreadMag.getCThread(owner).getsocket().getOutputStream());
			output.writeObject(msg);
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		String datetime = Calendar.getInstance().getTime().toLocaleString();
		String head = "我对" + msg.getRuserName() + "说:";
		String chatmsg = data + "\n" + datetime + "\r\n";
		updateMsgInfo(head, Color.blue, true, 14);
		updateMsgInfo(chatmsg, Color.BLACK, false, 14);
		textarea.setCaretPosition(textarea.getDocument().getLength()); // 设置自动滚动到最后

		text.setText("");// 文本框置空
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == sendBtn) {
			sendMsg();
		} // 发送聊天信息
		else if (e.getSource() == cancelBtn) {
			this.dispose();
		} else if (e.getSource() == shakeBtn) {
			sendShake();
		}
	}

	public void shake() {
		this.requestFocus(); // 先获得焦点
		UIUtils.setLocationCenter(this);// 然后放置到屏幕正中
		try {
			UIUtils.playSound("sounds/alarm.au"); // 播放抖动音效
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 最后实现在屏幕中央的抖动效果
		Point origiLocation = this.getLocationOnScreen();
		for (int i = 0; i < 15; i++) {
			int shakeX = (int) ((Math.random() - 0.5) * 50);
			int shakeY = (int) ((Math.random() - 0.5) * 50);
			this.setLocation(origiLocation.x + shakeX, origiLocation.y + shakeY);
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		this.setLocation(origiLocation);
	}
}
