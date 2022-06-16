<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE HTML>
<HTML>
 <HEAD>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <TITLE> 로그인 </TITLE> 
  
  <script>
  
  //모델2를 위해서 html파일이었던 것을 jsp파일로 바꿨음.(mvc와 흡사)
  //1.유효성검사 후 2.전송
  function check()
  {
  	//alert("테스트입니다.");
  	var fm = document.frm ;
  	
  	if(fm.memberId.value=="")
  	{
  		alert("아이디를 입력하세요");
  		fm.memberId.focus();
  		return ;  		
  	}
  	else if (fm.memberPwd.value=="")
  	{
  		alert("비밀번호를 입력하세요");
  		fm.memberPwd.focus();
  		return ; 
  	}
  	
  	
  	
  	
  	alert("전송합니다");
  	//fm.action = "./memberJoinOk.jsp";
  	//처리하고 넘기는 페이지를 서블릿으로 할 거임.
  	
  	fm.action = "<%=request.getContextPath()%>/member/memberLoginAction.do";//경로이름.   물리적인 경로가 아닌 가상경로를 만들거임.
  	// /프로젝트이름추출/member/memberJoinAction.do    ==   /jspstudy/member/memberJoinAction.do
  	fm.method = "post";
  	fm.submit();
  	//html은 넘긴 걸 받을 수 없다..? 스크립트로 받아야함..?
  	return;
  	
  }
  
  
  </script>
  
  
  
  
 </HEAD>
 
 <BODY>
<center><h1>로그인</h1></center>
<hr></hr>
<!--<form name="frm" action="./memberJoinOk.jsp" method="post"> 여기서 바로 넘겨주지 않음 체크하고(check()사용해서) 넘겨줄거임-->
<form name="frm" >
 <table border="1" style="text-align:left;width:800px;height:300px">
<tr>
<td>아이디</td>
<td><input type="text" name="memberId" size="30"></td>
</tr>
<tr>
<td>비밀번호</td>
<td><input type="password" name="memberPwd" size="30"></td>
</tr>

<tr>
<td>버튼</td>
<td>
<input type="button" value="확인" onclick="check();"> <!-- test중임 submit 타입은 다 넘겨서 초기화되니까 button타입으로 하면 내가 원하는 그.. -->
<input type="reset" value="다시작성"> 
</td>
</tr>
 </table>
 </form>
 </BODY>
</HTML>
