package com.rock.Util;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import com.mysql.jdbc.Field;
import com.mysql.jdbc.Statement;
import com.rock.entity.User;

/**
 * 关于数据库操作的类
 * 
 * @author 林智杰
 *
 */
public class JdbcUtil {

	/**
	 * @return 数据库连接
	 * @throws Exception
	 */
	public static Connection getConnection() throws Exception {
		Connection con;
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/chat";
		String User = "root";
		String Password = "123456";
		Class.forName(driver);
		con = DriverManager.getConnection(url, User, Password);
		return con;
	}

	/**
	 * 关闭从数据库服务器等索取的资源:先关闭后获取的
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	public static void closeResource(Connection conn, PreparedStatement stmt, ResultSet rs) {
		// 由于PreparedStatement是Statement的子接口，所以该方法也适合传入PreparedStatement的对象

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 验证登录是否成功的函数
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public static boolean mysql_login(User user) throws Exception {
		boolean flag = false;
		Connection con = null;
		ResultSet rs = null;
		PreparedStatement ps = null;
		String userName, password;
		userName = user.getUserName();
		password = user.getPassword();
		String sql = "select * from User where User_Name ='" + userName + "' and User_Password='" + password + "'";

		con = JdbcUtil.getConnection();
		ps = con.prepareStatement(sql);
		rs = ps.executeQuery();

		rs.last();
		int count = rs.getRow();
		if (count > 0) {
			flag = true;
		}
		JdbcUtil.closeResource(con, ps, rs);
		return flag;
	}

}
