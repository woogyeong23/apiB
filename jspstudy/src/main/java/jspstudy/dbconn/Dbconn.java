package jspstudy.dbconn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbconn {

	
	/*
	private String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";	//jdbc:�ڹٿ� ������ Ŀ�ؼ�
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
			//����� ����̹��߿� ��밡���� Ŭ���� ã�Ƽ� ����
			//Class.forName("oracle.jdbc.driver.OracleDriver");//Ŭ���� Ŭ����
			Class.forName("com.mysql.cj.jdbc.Driver");

			//���������� ���ؼ� ���ᰴü�� �������� conn�� ��´�
			conn = DriverManager.getConnection(url, user, password);//����Ŭ�� �ּ�,���̵�,��й�ȣ

		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		return conn;
	}
	
	
	
}
