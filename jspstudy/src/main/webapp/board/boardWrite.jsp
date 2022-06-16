<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("midx") == null)
	{
		session.setAttribute("saveUrl",request.getRequestURI());
		out.println("<script>alert('로그인해주세요');location.href='"+request.getContextPath()+"/member/memberLogin.do'</script>");
	}

%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
 
<script>
function check()
{
	var fm = document.frm ;
  	
  	if(fm.subject.value=="")
  	{
  		alert("제목을 입력하세요");
  		fm.subject.focus();
  		return ;  		
  	}else if (fm.content.value=="")
  	{
  		alert("내용을 입력하세요");
  		fm.content.focus();
  		return ; 
  	}
  	else if (fm.writer.value=="")
  	{
  		alert("작성자를 입력하세요");
  		fm.writer.focus();
  		return ; 
  	}
  	
	

  	fm.action = "<%=request.getContextPath()%>/board/boardWriteAction.do";
  	fm.enctype = "multipart/form-data";
  	fm.method = "post";
  	fm.submit();
	
	
	return;	
}



</script>
  



</head>

<body>
<h1>게시판 글쓰기</h1>
	<table border=1 style="width:800px;">
	<form name = "frm">
		<tr>
			<td style="width:100px">제목</td>
			<td><input type="text" name="subject" style="width:100% ; border:0" ></td>
		</tr>
		<tr>
			<td>내용</td>
			<td>
				<textarea name = "content" rows="10" style="width:100% ; border:0" placeholder="내용을 입력해주세요" ></textarea>
			</td>
		</tr>
		<tr>
			<td>작성자</td>
			<td><input type="text" name="writer" style="width:100% ; border:0" size="50" value="<%=session.getAttribute("memberName") %>" readonly="readonly"></td>
		</tr>
		<tr>
			<td>파일 업로드</td>
			<td><input type="file" name="filename"></td>
		</tr>
		<tr >
			<td  colspan=2><center><input type="button" name="btn" value="확인" onclick="check();">
			<input type="reset" name="reset" value="리셋"></td>
		</tr>
	</form>
	</table>




</body>
</html>