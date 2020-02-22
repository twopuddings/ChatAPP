package com.qq.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.qq.common.User;
import com.qq.server.tools.BaseDao;

public class SqlHelper {
	public boolean save(User u) throws Exception {
		Connection conn=BaseDao.getCon();
		String sql="insert into qquser(user,password) values(?,?)";
		PreparedStatement prep=conn.prepareStatement(sql);
		if(u.getUser().isEmpty() || u.getPass().isEmpty()){
			return false;
		}else{
			prep.setString(1, u.getUser());
			prep.setString(2, u.getPass());
			prep.executeUpdate();
			return true;
		}
		
	}
	
	public boolean findByUser(String user) throws Exception{
		Connection conn=BaseDao.getCon();
		User u = null;
		String sql="select * from qquser where user=?";
		PreparedStatement prep=conn.prepareStatement(sql);
		prep.setString(1, user);
		ResultSet rs = prep.executeQuery();
		if(rs.next()){
			u = new User();
			u.setUser(rs.getString("user"));
			u.setPass(rs.getString("password"));
			return true;
		}else{
			return false;
		}
	}
}
