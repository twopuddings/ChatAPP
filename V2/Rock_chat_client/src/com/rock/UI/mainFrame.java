package com.rock.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.rock.Thread.ClientThread;
import com.rock.Util.DefaultUtil;
import com.rock.Util.UIUtils;
import com.rock.entity.Message;
import com.rock.entity.User;
import com.rock.method.CThreadMag;
import com.rock.method.GroupVMag;

/**
 * 登陆之后的主页面
 * 
 * @author 林智杰
 *
 */
public class mainFrame extends JFrame implements ActionListener, MouseListener, ListSelectionListener {
	private JTextField label;// 显示在线人数的控件
	private JList userList;// 用户列表
	private DefaultListModel<String> dlm;
	private JButton group;// 群聊按钮
	public static String TAG = "Client:";
	private String user;// 拥有这个页面的用户
	private int count = 0;// 在线的人数
	private HashMap<String, chatFrame> userchat;// 储存该用户的所有的聊天页面
	final ImageIcon userIcon = UIUtils.getImageIcon("images/user_48.png");

	/**
	 * @param user
	 *            用于主页面的用户的用户名
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public mainFrame(String user) throws UnknownHostException, IOException {
		this.setUser(user);
		userchat = new HashMap<String, chatFrame>();
		this.user = user;
		GroupFrame group = new GroupFrame(user);// 新建群聊页面
		GroupVMag.addGroupFrame(user, group);
		userList = new JList();
		dlm = new DefaultListModel();
		userList.setModel(dlm);
		init();
	}

	/**
	 * 添加用户聊天页面
	 * 
	 * @param friend
	 *            聊天的对象的用户名
	 * @param chat
	 *            对应的聊天的页面
	 */
	public void addchatFrame(String friend, chatFrame chat) {
		userchat.put(friend, chat);
	}

	/**
	 * @param friend
	 * @return 根据好友的用户名返回对应的聊天框
	 */
	public chatFrame getchatFrame(String friend) {
		return (chatFrame) userchat.get(friend);
	}

	/**
	 * 用户下线的时候删除该用户聊天页面
	 * 
	 * @param user
	 */
	public void delchatFrame(String user) {
		userchat.remove(user);
	}

	/**
	 * 测试性的函数，遍历聊天页面
	 */
	public void vist() {
		System.out.println("遍历聊天列表");
		Set<String> set = userchat.keySet();
		// 得到遍历 key 的选代器
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key:" + key);
		}
	}

	/**
	 * 根据服务器的信息更新用户列表
	 * 
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void updateUser(Message msg) throws UnknownHostException, IOException {
		UIUtils.playSound("sounds/Online.wav");
		System.out.println(TAG.toString() + "函数更新用户列表");
		String[] users1 = msg.getData().split(" ");
		for (int i = 0; i < users1.length; i++) {
			dlm.addElement(users1[i]);
			count++;
			System.out.println(TAG.toString() + "函数用户:" + users1[i]);
		}
		label.setText("在线人数为：" + count);// 更新在线人数

	}

	/**
	 * 根据服务器的信息更新用户下线列表
	 * 
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void updateUserOut(Message msg) throws UnknownHostException, IOException {
		System.out.println(TAG.toString() + "函数用户下线");
		String users1 = msg.getData();
		dlm.removeElement(users1);
		count--;
		label.setText("在线人数为：" + count);// 更新在线人数
	}

	/**
	 * 初始化主页面组件的函数 列出所有在线的用户
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void init() throws UnknownHostException, IOException {
		label = new JTextField();
		label.setText("在线人数为：" + count);
		label.setEditable(false);
		userList.addMouseListener(this);
		group = new JButton("群聊", UIUtils.getImageIcon("images/user2_48.png"));
		group.setFocusable(false);
		group.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		group.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		group.addActionListener(this);
		JToolBar toolBar = new JToolBar();
		toolBar.setBorderPainted(false);
		toolBar.add(group);
		JPanel center = new JPanel(new BorderLayout(0, 5));
		center.add(label, BorderLayout.NORTH);
		JScrollPane userScrollPane = new JScrollPane(userList);
		center.add(userScrollPane, BorderLayout.CENTER);

		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(0, 5));
		contentPane.add(toolBar, BorderLayout.NORTH);
		contentPane.add(center, BorderLayout.CENTER);
		this.setTitle(user + "聊天页表");
		this.setIconImage(UIUtils.getIconImage("images/app64.png"));
		this.setSize(new Dimension(DefaultUtil.MAIN_FRAME_WIDTH, DefaultUtil.MAIN_FRAME_HEIGHT));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			/*
			 * 关闭主页面的时候执行的函数
			 * 
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				// 关闭时，退出软件，此时需发送一个退出广播，通知其他在线用户
				System.out.println("closing...");
				Message msg = new Message();
				msg.setMsgtype(DefaultUtil.MSG_CLOSE);
				msg.setUserName(user);
				Socket s = CThreadMag.getCThread(user).getsocket();
				ObjectOutputStream output;
				try {
					output = new ObjectOutputStream(s.getOutputStream());
					output.writeObject(msg);// 通知服务器本用户下线了
					output.flush();
					ClientThread client = CThreadMag.getCThread(user);
					client.setExit(true);// 关闭该用户的客户端的线程
					CThreadMag.delCThread(user);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);// 安全退出
			}
		});
		this.setResizable(false);
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == group) {
			GroupFrame group = GroupVMag.getGroupFrame(user);
			if (group != null) {
				group.setVisible(true);
			}
			System.out.println(TAG.toString() + "打开群聊页面");
		}

	}

	/**
	 * 测试性的主函数
	 * 
	 * @param args
	 * @throws UnknownHostException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {
		mainFrame main = new mainFrame("uu");
		main.init();

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == userList) {
			int index;
			// 双击主页面的好有列表执行的函数
			if (e.getClickCount() == 2) {

				index = userList.locationToIndex(e.getPoint());
				String friend = (String) dlm.getElementAt(index);
				if (friend != null) {
					chatFrame chat = this.getchatFrame(friend);
					// 判断是否存在和选中的对象的聊天框
					if (chat == null) {
						chatFrame newchat = new chatFrame(user, friend);
						this.addchatFrame(friend, newchat);
						newchat.setVisible(true);
					} else {
						chat.setVisible(true);// 该聊天页面已存在则显示出来
					}
				}
			}
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub

	}
}
