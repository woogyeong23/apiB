package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconn {

	
	/*
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";	//jdbc:자바와 디비와의 커넥션
	private String user="system";
	private String password="1234";
	*/

	private String url = "jdbc:mysql://127.0.0.1:3306/mysql?serverTimezone=UTC&characterEncoding=UTF-8";
	private String user = "root";
	private String password = "1234";
	
	
	public Connection getConnection()
	{
		
		Connection conn=null;
		
		try {
			//등록한 드라이버중에 사용가능한 클래스 찾아서 생성
			//Class.forName("oracle.jdbc.driver.OracleDriver");//클래스 클래스
			Class.forName("com.mysql.cj.jdbc.Driver");

			//연결정보를 통해서 연결객체를 참조변수 conn에 담는다
			conn = DriverManager.getConnection(url, user, password);//오라클의 주소,아이디,비밀번호

		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return conn;
	}
	
	
	
}
