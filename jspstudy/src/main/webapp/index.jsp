<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import = "jspstudy.dbconn.Dbconn" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메인페이지</title>
</head>
<body>

	<h1>메인 페이지 입니다.</h1>
	
	<!-- <a href="./memberList.jsp">회원 리스트</a> --><!-- 물리적 경로 -->
	<a href="<%=request.getContextPath() %>/member/memberList.do">회원 리스트</a><!-- 가상 경로 -->
	<a href="<%=request.getContextPath() %>/member/memberJoin.do">회원 가입</a><!-- 가상 경로 -->
	<a href="<%=request.getContextPath() %>/member/memberLogin.do">회원 로그인</a><!-- 가상 경로 -->
	<a href="<%=request.getContextPath() %>/board/boardWrite.do">게시판 글쓰기</a><!-- 가상 경로 -->
	<a href="<%=request.getContextPath() %>/board/boardList.do">게시판 리스트</a><!-- 가상 경로 -->

			<%Dbconn dbconn = new Dbconn(); 
				System.out.println("dbconn:"+dbconn);
			%>

</body>
</html>