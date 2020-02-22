/**
 * �ͻ��������������ͨ�ŵ��߳��࣬�������շ�����ת����������Ϣ��
 */
package com.qq.client.tools;

import java.net.*;
import java.io.*;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;
public class ClientConnServerThread extends Thread{
	private Socket s;

	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public ClientConnServerThread(Socket s){
		this.s=s;
	}
	public void run(){
		while(true){
			try {
				//��ͣ�ض�ȡ�ӷ�������������Ϣ
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				System.out.println("��ȡ���ģ�"+m.getSender()+" �� "+m.getReceiver()+" ˵��"+m.getContent()+"\r\n");
				
				//V5--3 �жϴӷ��������ܵİ������ͣ��ֱ���
				if(m.getMsType().equals(MessageType.message_comm_mess))
				{
					//������������ص�����ͨ�����������ʾ����Ӧ�������
					//V4--7  �Ѵӷ�������õ���Ϣ����ʾ������ʾ���������
					QqChat qqChat=ManageQqChat.getQqChat(m.getReceiver()+" "+m.getSender());
					//V4--7 ��ʾ
					qqChat.showMessage(m);
				}else if(m.getMsType().equals(MessageType.message_response_onLineFriend))
				{
					//������������ص����������ߺ��������Ӧ�������ȡ�����ߺ��ѣ�������ʾ
					System.out.println("�ͻ��˽��յ�"+m.getContent());
					//V5--����ӷ��������յİ������ߺ����б�������Ļ��Ͱ�����ȡ����Ӧ��Ϣ
					String content=m.getContent();
					String friends[]=content.split(" ");
					//��Ӧ����Ľ����ߣ������������������б��QQ�û���
					String receiver=m.getReceiver();
					//ȡ����ӦQQ�ŵĺ����б���棨��ΪҪ���ô˽���ĸ�����ʾ���ߺ��ѷ�����
					QqFriendList qqFriendList=ManageQqFriendList.getQqFriendList(receiver);
					//�������ߺ���--����QqFriendList��ĸ�����ʾ���ѷ���
					qqFriendList.HightLightOnLineFriend(m);
					
				}
			
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
