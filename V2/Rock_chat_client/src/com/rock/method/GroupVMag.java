package com.rock.method;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.rock.UI.GroupFrame;
import com.rock.UI.mainFrame;

/**
 * 管理群聊页面的类
 * 
 * @author 林智杰
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
	 * 测试性函数
	 */
	public static void vist() {
		System.out.println("遍历");
		Set<String> set = userGroup.keySet();
		// 得到遍历 key 的选代器
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String key = (String) it.next();
			System.out.println("key:" + key + ",value:");

		}
	}
}
