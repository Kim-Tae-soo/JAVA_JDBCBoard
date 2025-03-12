package kr.or.ddit.board.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

// JDBC 드라이버를 로딩하고 Connection 객체를 생성하는 메서드로 구성된 class만들기
// ResourceBundle 객체 이용하기
public class DBUtil3 {
	private static ResourceBundle bundle = null;
	static {
		bundle = ResourceBundle.getBundle("kr.or.ddit.config.db");
		
		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
			Class.forName(bundle.getString("driver"));
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패");
			e.printStackTrace();
		}
	}

	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(bundle.getString("url"),
											   bundle.getString("user"),
											   bundle.getString("pass"));
//			conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1",
//					"KTS98", 
//					"java");

		} catch (SQLException e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
		
		return conn;
	}

}
