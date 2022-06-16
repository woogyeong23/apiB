<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ page import = "java.util.*" %>   
<%@ page import = "jspstudy.domain.*" %>  

<%

	ArrayList<BoardVo> alist = (ArrayList<BoardVo>)request.getAttribute("alist");//set 으로 담았으니 get으로 꺼내기 & 형 맞춰주기

	PageMaker pm = (PageMaker)request.getAttribute("pm");
	
	
%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 목록</title>
<style>

ul {
  list-style-type: none;
  padding: 0px;
  margin: 0px;
  width: 100px;
  background: #FF6347;
  height: 100%;
  overflow: auto;
  position: fixed;
}

li a {
  text-decoration: none;
  padding: 10px;
  display: block;
  color: #000;
  font-weight: bold;
}

li a:hover {
  background: #333;
  color: #fff;
}

li a.home {
  background : #333;
  color: #fff;
}

</style>


</head>
<body>
<h1>게시판 리스트</h1>

<form name = "frm" action="<%=request.getContextPath() %>/board/boardList.do" method="post">
<table border=0 style="width:800px; text-align:right">
	<tr>
		<td style="width:620px;">
			<select name="searchType">
				<option value="subject">제목</option>
				<option value="writer">작성자</option>
			</select>
		</td>
		<td>
			<input type="text" name="keyword" size="10">
		</td>
		<td>
			<input type="submit" name="submit" value="검색">
		</td>
	</tr>
</table>
</form>






<table border="1" style="width: 800px">
	
	<tr style="color:green;">
		<td style="width:100px;">bidx번호</td>
		<td style="width:300px;">제목</td>
		<td style="width:100px;">작성자</td>
		<td style="width:100px;">작성일</td>
	</tr>
	<% for (BoardVo bv : alist)  {%>
	<tr>
		<td><%=bv.getBidx() %></td>
		
		
		<td>
		<%
		for(int i=1;i<=bv.getLevel_();i++) 
		{
			out.println("&nbsp;&nbsp;");
			if(i==bv.getLevel_())
			{
				out.println("ㄴ");
			}
		}
		
		
		%>
		<a href="<%=request.getContextPath()%>/board/boardContent.do?bidx=<%=bv.getBidx()%>"> <%=bv.getSubject() %></a>
		<!--get방식으로 링크를 넘김:  주소 ? 파라미터=값 & 파라미터=값 -->
		</td>
		
		
		<td><%=bv.getWriter() %></td>
		<td><%=bv.getWriteday() %></td>
	</tr>
	
	<% } %>	

</table>

<table style="width: 800px; text-align:center;">
	<tr>
		<td style="width: 200px;  text-align:right;">
		
		<% 
			String keyword = pm.getScri().getKeyword();
		
			String searchType = pm.getScri().getSearchType();
		
		
		
			if(pm.isPrev() == true)
				out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+(pm.getStartPage()-1)+"&keyword="+keyword+"&searchType="+searchType+"'>◀</a>");
		%>
		</td>
		<td>
		<%
			for(int i = pm.getStartPage(); i <= pm.getEndPage(); i++)
			{
				out.println("<a href = '"+request.getContextPath()+"/board/boardList.do?page="+i+"&keyword="+keyword+"&searchType="+searchType+"'>"+i+"</a>");
			}
		
		%>
		</td>
		<td style="width: 200px;  text-align:left;">
		<%
			if(pm.isNext() && pm.getEndPage()>0)
			{
				out.println("<a href='"+request.getContextPath()+"/board/boardList.do?page="+(pm.getEndPage()+1)+"&keyword="+keyword+"&searchType="+searchType+"'>▶</a>");
			}
		%>
		</td>
	</tr>
	
	
	
</table>




</body>
</html>