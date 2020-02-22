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
 * ��½֮�����ҳ��
 * 
 * @author ���ǽ�
 *
 */
public class mainFrame extends JFrame implements ActionListener, MouseListener, ListSelectionListener {
	private JTextField label;// ��ʾ���������Ŀؼ�
	private JList userList;// �û��б�
	private DefaultListModel<String> dlm;
	private JButton group;// Ⱥ�İ�ť
	public static String TAG = "Client:";
	private String user;// ӵ�����ҳ����û�
	private int count = 0;// ���ߵ�����
	private HashMap<String, chatFrame> userchat;// ������û������е�����ҳ��
	final ImageIcon userIcon = UIUtils.getImageIcon("images/user_48.png");

	/**
	 * @param user
	 *            ������ҳ����û����û���
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public mainFrame(String user) throws UnknownHostException, IOException {
		this.setUser(user);
		userchat = new HashMap<String, chatFrame>();
		this.user = user;
		GroupFrame group = new GroupFrame(user);// �½�Ⱥ��ҳ��
		GroupVMag.addGroupFrame(user, group);
		userList = new JList();
		dlm = new DefaultListModel();
		userList.setModel(dlm);
		init();
	}

	/**
	 * ����û�����ҳ��
	 * 
	 * @param friend
	 *            ����Ķ�����û���
	 * @param chat
	 *            ��Ӧ�������ҳ��
	 */
	public void addchatFrame(String friend, chatFrame chat) {
		userchat.put(friend, chat);
	}

	/**
	 * @param friend
	 * @return ���ݺ��ѵ��û������ض�Ӧ�������
	 */
	public chatFrame getchatFrame(String friend) {
		return (chatFrame) userchat.get(friend);
	}

	/**
	 * �û����ߵ�ʱ��ɾ�����û�����ҳ��
	 * 
	 * @param user
	 */
	public void delchatFrame(String user) {
		userchat.remove(user);
	}

	/**
	 * �����Եĺ�������������ҳ��
	 */
	public void vist() {
		System.out.println("���������б�");
		Set<String> set = userchat.keySet();
		// �õ����� key ��ѡ����
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key:" + key);
		}
	}

	/**
	 * ���ݷ���������Ϣ�����û��б�
	 * 
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void updateUser(Message msg) throws UnknownHostException, IOException {
		UIUtils.playSound("sounds/Online.wav");
		System.out.println(TAG.toString() + "���������û��б�");
		String[] users1 = msg.getData().split(" ");
		for (int i = 0; i < users1.length; i++) {
			dlm.addElement(users1[i]);
			count++;
			System.out.println(TAG.toString() + "�����û�:" + users1[i]);
		}
		label.setText("��������Ϊ��" + count);// ������������

	}

	/**
	 * ���ݷ���������Ϣ�����û������б�
	 * 
	 * @param msg
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void updateUserOut(Message msg) throws UnknownHostException, IOException {
		System.out.println(TAG.toString() + "�����û�����");
		String users1 = msg.getData();
		dlm.removeElement(users1);
		count--;
		label.setText("��������Ϊ��" + count);// ������������
	}

	/**
	 * ��ʼ����ҳ������ĺ��� �г��������ߵ��û�
	 * 
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public void init() throws UnknownHostException, IOException {
		label = new JTextField();
		label.setText("��������Ϊ��" + count);
		label.setEditable(false);
		userList.addMouseListener(this);
		group = new JButton("Ⱥ��", UIUtils.getImageIcon("images/user2_48.png"));
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
		this.setTitle(user + "����ҳ��");
		this.setIconImage(UIUtils.getIconImage("images/app64.png"));
		this.setSize(new Dimension(DefaultUtil.MAIN_FRAME_WIDTH, DefaultUtil.MAIN_FRAME_HEIGHT));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {
			/*
			 * �ر���ҳ���ʱ��ִ�еĺ���
			 * 
			 */
			@Override
			public void windowClosing(WindowEvent e) {
				// �ر�ʱ���˳��������ʱ�跢��һ���˳��㲥��֪ͨ���������û�
				System.out.println("closing...");
				Message msg = new Message();
				msg.setMsgtype(DefaultUtil.MSG_CLOSE);
				msg.setUserName(user);
				Socket s = CThreadMag.getCThread(user).getsocket();
				ObjectOutputStream output;
				try {
					output = new ObjectOutputStream(s.getOutputStream());
					output.writeObject(msg);// ֪ͨ���������û�������
					output.flush();
					ClientThread client = CThreadMag.getCThread(user);
					client.setExit(true);// �رո��û��Ŀͻ��˵��߳�
					CThreadMag.delCThread(user);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.exit(0);// ��ȫ�˳�
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
			System.out.println(TAG.toString() + "��Ⱥ��ҳ��");
		}

	}

	/**
	 * �����Ե�������
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
			// ˫����ҳ��ĺ����б�ִ�еĺ���
			if (e.getClickCount() == 2) {

				index = userList.locationToIndex(e.getPoint());
				String friend = (String) dlm.getElementAt(index);
				if (friend != null) {
					chatFrame chat = this.getchatFrame(friend);
					// �ж��Ƿ���ں�ѡ�еĶ���������
					if (chat == null) {
						chatFrame newchat = new chatFrame(user, friend);
						this.addchatFrame(friend, newchat);
						newchat.setVisible(true);
					} else {
						chat.setVisible(true);// ������ҳ���Ѵ�������ʾ����
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
