<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<%



String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe";	//jdbc:자바와 디비와의 커넥션
String user="system";
String password="1234";


//등록한 드라이버중에 사용가능한 클래스 찾아서 생성
Class.forName("oracle.jdbc.driver.OracleDriver");	//클래스의 클래스

//java.sql.Connection conn = java.sql.DriverManager.getConnection(url, user, password);	//오라클의 주소,아이디,비밀번호
//객체 통해서 접속... 접속한 정보를 conn객체에 담음...
//-->java.sql을 import해서 생략해주기.

//연결정보를 통해서 연결객체를 참조변수 conn에 담는다
Connection conn = DriverManager.getConnection(url, user, password);	//오라클의 주소,아이디,비밀번호



%>