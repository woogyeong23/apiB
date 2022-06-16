<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ page import = "jspstudy.domain.*" %>    
<%@ page import = "java.util.*" %>    

<%
//디비와 연결해야 하니까 dbconn.jsp사용


//include file = "/dbconn.jsp"안할꺼임
//include file = "/function.jsp"이제



	//컨트롤러에서 처리한 것을 데이터를 넘겨줌 ****데이터는 주고 받기만 함****
	ArrayList<MemberVo> alist = (ArrayList<MemberVo>)request.getAttribute("alist");//키값입력하면 키값에 담겨져 있는 값을 리턴

	
	
	
	
/*
	//모델1방식:function과 dbconn을 클래스로 사용하는 방식(MemberVo는 bean..)
	MemberDao md = new MemberDao();
	ArrayList<MemberVo> alist = md.memberSelectAll();
	//모두 컨트롤러에서 처리 할 꺼임 이제 (포워드...)
	*/
	
	//그 전 방식(function.jsp, dbconn.jsp include 해서 사용하는 방식)
	//연결객체 conn 은 dbconn.jsp에 있음
	//이제 alist를 꺼내기
	//ArrayList<MemberVo> alist = memberSelectAll(conn);
	//out.println(alist.get(0).getMembername());
	
	
	

%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원 목록</title>
</head>
<body>
<%
//세션에 담겨져 있는 값 찍기
	if (session.getAttribute("midx") != null)
	{
		out.println("회원아이디: "+session.getAttribute("memberId")+"<br>");
		out.println("회원이름: "+session.getAttribute("memberName")+"<br>");

		out.println("<a href = '"+request.getContextPath()+"/member/memberLogout.do'>로그아웃</a>"+"<br>");
	}



%>
<h1>회원 목록 만들기</h1>

<table border="1" style="width: 800px">
	
	<tr style="color:green">
		<td>midx번호</td>
		<td>이름</td>
		<td>전화번호</td>
		<td>작성일</td>
	</tr>
	<%   for(MemberVo mv : alist)	{%>
			
			<tr>
			<td><%= mv.getMidx() %></td>
			<td><%= mv.getMembername() %></td>
			<td><%= mv.getMemberphone() %></td>
			<td><%= mv.getWriteday() %></td>
			</tr>
	
	<%	}	%>
	

</table>






</body>
</html>