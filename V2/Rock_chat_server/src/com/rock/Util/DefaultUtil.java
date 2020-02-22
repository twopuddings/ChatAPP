package com.rock.Util;
/*这是一个定义默认数据的工具类
 * 
 * */

import java.util.ArrayList;

import com.rock.entity.User;

/**
 * 定义一些默认的值的工具类
 * 
 * @author 林智杰
 *
 */
public class DefaultUtil {
	// 窗体宽高默认值
	public static final int CHAT_FRAME_WIDTH = 500;// 聊天页面的宽
	public static final int CHAT_FRAME_HEIGHT = 550;// 聊天页面的高
	public static final int MAIN_FRAME_HEIGHT = 600;// 主页面的高
	public static final int MAIN_FRAME_WIDTH = 280;// 主页面的宽

	public static final int MAIN_USER_HEIGHT = 30;
	public static final int MAIN_USER_WIDTH = 250;

	// 默认的端口
	public static final int SERVER_TCP_PORT = 8888;

	// 默认的消息的类型
	public static final String MSG_LOGIN_SUCCESS = "1";// 用户登录成功
	public static final String MSG_LOGIN_FAIL = "2";// 用户登录失败
	public static final String MSG_USER_LIST = "3";// 向服务器申请在线用户列表
	public static final String MSG_CHAT_COM = "4";// 普通的聊天信息
	public static final String MSG_UPDATE_USER = "5";// 更新在线用户
	public static final String MSG_CLOSE = "6";// 关闭socket连接
	public static final String MSG_LOGIN_OUT = "7";// 用户退出登录
	public static final String MSG_CHAT_FAIL = "8";// 发送聊天的对象已经下线
	public static final String MSG_GROUP = "9";// 群聊
	public static final String MSG_SHACK = "10";// 抖动
	public static final String MSG_GROUP_SHACK = "11";// 群发抖动

}
