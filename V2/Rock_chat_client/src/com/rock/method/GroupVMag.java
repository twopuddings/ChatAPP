package com.rock.method;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.rock.UI.GroupFrame;
import com.rock.UI.mainFrame;

/**
 * ����Ⱥ��ҳ�����
 * 
 * @author ���ǽ�
 *
 */
public class GroupVMag {
	public static HashMap<String, GroupFrame> userGroup = new HashMap<String, GroupFrame>();

	public static void addGroupFrame(String user, GroupFrame main) {
		userGroup.put(user, main);
	}

	public static void delGroupFrame(String user) {
		userGroup.remove(user);
	}

	public static GroupFrame getGroupFrame(String user) {
		return (GroupFrame) userGroup.get(user);
	}

	/**
	 * �����Ժ���
	 */
	public static void vist() {
		System.out.println("����");
		Set<String> set = userGroup.keySet();
		// �õ����� key ��ѡ����
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key:" + key + ",value:");

		}
	}
}
