/**
 *  ����������ĳ���ͻ��˵��߳�,������ĳ���ͻ��˵�ͨ��
 */
package com.qq.server.model;

import java.net.*;
import java.io.*;
import java.util.*;

import com.qq.common.Message;
import com.qq.common.MessageType;
import java.util.*;
public class SerConnClientThread extends Thread{
	Socket s;
	
	public SerConnClientThread(Socket s){
		//�ѷ������͸ÿͻ��˵����Ӹ���S
		this.s=s;
	}
	
	 //V6--�ø��߳�ȥ֪ͨ�����û���������
	public void notifyOthers(String myself)
	{
		//�õ����������˵��߳�
		HashMap hm=ManageClientThread.hm;
		Iterator it=hm.keySet().iterator();
		
		while(it.hasNext()){
			Message m=new Message();
			m.setContent(myself);
			m.setMsType(MessageType.message_response_onLineFriend);
			//ȡ�������˵�id
			String onLineUserId=it.next().toString();
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientThread.getClientThread(onLineUserId).s.getOutputStream());
				m.setReceiver(onLineUserId);
				oos.writeObject(m);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void run(){
		while(true){
			//���߳̾Ϳ��Խ��տͻ��˵�������Ϣ
			try{
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				
				//System.out.println(m.getSender()+"��"+m.getReceiver()+"˵��"+m.getContent());
				
				//V5--���ݴӿͻ��˽��յİ����ͣ��������ݰ�����������б�������ֱ���
				if(m.getMsType().equals(MessageType.message_comm_mess))
				{
					//V3.3�������ͨ�����--ת��
				//ȡ�ý����˵�ͨ���߳�
				SerConnClientThread sc=ManageClientThread.getClientThread(m.getReceiver());
				// ���������ת��
				ObjectOutputStream oos=new ObjectOutputStream(sc.s.getOutputStream());
				oos.writeObject(m);
				}else if(m.getMsType().equals(MessageType.message_request_onLineFriend))
				{
					//������������ߺ��Ѱ�
					//--��ӹ����¼�û�������ȡ�������û�response����װ��һ�������ظ��ͻ��ˡ�
					System.out.println("�ӿͻ��˽��յ�"+m.getSender()+"��������Ѱ�");
					String res_content=ManageClientThread.getAllOnLineUserNo();
					Message m_res=new Message();
					m_res.setMsType(MessageType.message_response_onLineFriend);
					m_res.setContent(res_content);
					m_res.setReceiver(m.getSender());
					ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
					System.out.println(m_res.getContent());
					oos.writeObject(m_res);
				}
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
}
