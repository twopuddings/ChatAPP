package com.qq.client.model;

import com.qq.client.tools.ClientConnServerThread;
import com.qq.client.tools.ManageClientConnServerThread;
import com.qq.common.*;
import java.net.*;
import java.io.*;
public class QqClientConnServer {

	public  Socket s; //V3.2����Ϊ ��̬����  //V4ȥ����̬����
	//�ͻ��˷��͵�һ������
	public boolean sendLoginInfoToServer(Object o)
	{
		boolean b=false;
		try{
			s=new Socket("127.0.0.1",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			//����һ����Ӧ
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//��֤�û���¼�ĵط�
			if(ms.getMsType().equals("1")){
				//V4--����һ����QQ�źͷ������˱���ͨѶ���ӵ��߳�
				ClientConnServerThread ccst=new ClientConnServerThread(s);
				ccst.start();//�������߳�
				ManageClientConnServerThread .addClientConnServerThread(((User)o).getUser(), ccst);//������̱߳��浽�̹߳����ϣ����ȥ
				
				b=true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
		}
		return b;
	}
}
