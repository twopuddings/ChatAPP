package com.rock.method;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.rock.UI.chatFrame;
import com.rock.UI.mainFrame;
import com.rock.entity.User;

/**
 * ������ҳ�����
 * 
 * @author ���ǽ�
 *
 */
public class MainVMag {
	public static HashMap<String, mainFrame> usermain = new HashMap<String, mainFrame>();

	public static void addmainFrame(String user, mainFrame main) {
		usermain.put(user, main);
	}

	public static void delmainFrame(String user) {
		usermain.remove(user);
	}

	public static mainFrame getmainFrame(String user) {
		return (mainFrame) usermain.get(user);
	}

	public static void vist() {
		System.out.println("����");
		Set<String> set = usermain.keySet();
		// �õ����� key ��ѡ����
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key:" + key + ",value:");

		}
	}

}
