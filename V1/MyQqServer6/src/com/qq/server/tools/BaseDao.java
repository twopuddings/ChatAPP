package com.qq.server.tools;

import java.sql.Connection;
import java.sql.DriverManager;


public class BaseDao {
	public static final String Driver="com.mysql.jdbc.Driver";
	public static final String url="jdbc:mysql://localhost:3306/qqdb";
	public static final String dbname="root";
	public static final String pwd="123456";
	//获取数据库连接
	public static Connection getCon(){
		Connection conn=null;
		try {
			Class.forName(Driver);
			conn=
				DriverManager.getConnection(url,dbname,pwd);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
	//释放资源
	public static void close(Connection conn){
		
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void main(String[] args) {
		System.out.println(BaseDao.getCon());

	}

}
