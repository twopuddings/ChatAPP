package com.rock.method;

import com.rock.entity.*;;

/**
 * @author ���ǽ� ���yemia��Ϊ����֤��¼�Ƿ�ɹ����ɹ�Ϊtrue,����Ϊfalse
 */
public class login_check {
	public login_check() {
	};

	public static boolean logincheck(User user) {
		// �ͻ����״κͷ������������ӣ����û�����ĵ�¼��Ϣ���͸�������
		return new client_server().send(user);
	}

}
