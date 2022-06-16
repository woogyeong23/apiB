<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jspstudy.domain.*" %>

<%
	BoardVo bv = (BoardVo)request.getAttribute("bv");
	
%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>삭제하기</title>


<script>
function check()
{
	var fm = document.frm ;
  	

  	fm.action = "<%=request.getContextPath()%>/board/boardDeleteAction.do";
  	fm.method = "post";
  	fm.submit();
	
	
	return;	
}



</script>
  



</head>
<body>


<table  style="width: 300px">
	<form name = "frm">
	
	<tr>
		<td style = "text-align: center ;"><h1>삭제하시겠습니까?</h1></td>
	</tr>
	<tr >
			<td  style = "text-align: center ; width:300px">
			<input type="button" name="delete" value="삭제" onclick="check();">
			<input type="button" name="cancle" value="취소" onclick="location.href='<%=request.getContextPath() %>/board/boardContent.do?bidx=<%=bv.getBidx()%>'">
			</td>
			
	</tr>
					<input type="hidden" name="bidx" value="<%=bv.getBidx()%>"><!-- bidx값 히든타입으로 넘기기(파라미터로 받게..) -->
	
	</form>
</table>

</body>
</html>