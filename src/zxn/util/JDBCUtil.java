package zxn.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtil {
	private String dbUrl = "jdbc:mysql://localhost:3306/zxn";
	private String dbUserName = "root";
	private String dbPassWord = "root";
	private String jdbcName = "com.mysql.jdbc.Driver";

	public  Connection getCon() throws Exception {
		Class.forName(jdbcName);
		Connection con = DriverManager.getConnection(dbUrl, dbUserName,
				dbPassWord);
		return con;
	}

	public void closeCon(Connection con) throws Exception {
		if (con != null) {
			con.close();
		}
	}
}
