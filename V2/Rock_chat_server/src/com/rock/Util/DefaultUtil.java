package com.rock.Util;
/*����һ������Ĭ�����ݵĹ�����
 * 
 * */

import java.util.ArrayList;

import com.rock.entity.User;

/**
 * ����һЩĬ�ϵ�ֵ�Ĺ�����
 * 
 * @author ���ǽ�
 *
 */
public class DefaultUtil {
	// ������Ĭ��ֵ
	public static final int CHAT_FRAME_WIDTH = 500;// ����ҳ��Ŀ�
	public static final int CHAT_FRAME_HEIGHT = 550;// ����ҳ��ĸ�
	public static final int MAIN_FRAME_HEIGHT = 600;// ��ҳ��ĸ�
	public static final int MAIN_FRAME_WIDTH = 280;// ��ҳ��Ŀ�

	public static final int MAIN_USER_HEIGHT = 30;
	public static final int MAIN_USER_WIDTH = 250;

	// Ĭ�ϵĶ˿�
	public static final int SERVER_TCP_PORT = 8888;

	// Ĭ�ϵ���Ϣ������
	public static final String MSG_LOGIN_SUCCESS = "1";// �û���¼�ɹ�
	public static final String MSG_LOGIN_FAIL = "2";// �û���¼ʧ��
	public static final String MSG_USER_LIST = "3";// ����������������û��б�
	public static final String MSG_CHAT_COM = "4";// ��ͨ��������Ϣ
	public static final String MSG_UPDATE_USER = "5";// ���������û�
	public static final String MSG_CLOSE = "6";// �ر�socket����
	public static final String MSG_LOGIN_OUT = "7";// �û��˳���¼
	public static final String MSG_CHAT_FAIL = "8";// ��������Ķ����Ѿ�����
	public static final String MSG_GROUP = "9";// Ⱥ��
	public static final String MSG_SHACK = "10";// ����
	public static final String MSG_GROUP_SHACK = "11";// Ⱥ������

}
