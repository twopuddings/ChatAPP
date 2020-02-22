/**
 * 定义包的种类
 */
package com.qq.common;

public interface MessageType {

	String message_login_succeed="1";//表示登录成功
	String message_login_fail="2";//表示登录失败
	String message_comm_mess="3";//表示普通信息包
	String message_request_onLineFriend="4";//表示要求在线好友包
	String message_response_onLineFriend="5";//表示返回在线好友包
	String message_register_succeed="6";
	String message_register_fail="7";
}
