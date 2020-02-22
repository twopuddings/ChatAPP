/**
 * 管理登录了的QQ用户的好友/陌生人/黑名单界面的类
 */
package com.qq.client.tools;
import java.util.*;

import com.qq.client.view.QqFriendList;

public class ManageQqFriendList {

	private static HashMap hm=new HashMap<String,QqFriendList>();
	
	public static void addQqFriendList(String qqId,QqFriendList qqFriendList){
		hm.put(qqId, qqFriendList);
	}
	
	public static QqFriendList getQqFriendList(String qqId){
		return (QqFriendList)hm.get(qqId);
	}
}
