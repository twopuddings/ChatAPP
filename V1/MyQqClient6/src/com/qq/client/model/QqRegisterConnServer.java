package com.qq.client.model;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import com.qq.common.Message;

public class QqRegisterConnServer {
	
	public Socket s;

	public boolean sendRegisterInfoToServer(Object o) {
		// TODO Auto-generated method stub
		boolean b = false;
		try {
			s=new Socket("127.0.0.1",9999);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			
			if(ms.getMsType().equals("6")){
				b = true;
			}else if(ms.getMsType().equals("7")){
				b = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			
		}
		return b;
	}

}
