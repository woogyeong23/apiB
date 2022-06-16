<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import = "java.sql.*" %>

<%@ page import = "java.util.*" %>

<%@ page import = "jspstudy.domain.*" %>


 <%!
 public int insertMember(Connection conn, String memberId, String memberPwd, String memberName, String memberGender, String memberAddr, String memberJumin, String memberPhone, String hobby, String memberEmail, String ip)
 {
	int value=0;
	 
	//String sql = "insert into b_member(MIDX,MEMBERID,MEMBERPWD,MEMBERNAME,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP) "
	//		+"values(MIDX_B_SEQ.nextval,'"+memberId+"','"+memberPwd+"','"+memberName+"','"+memberGender+"','"+memberAddr+"','"+memberJumin+"','"+memberPhone+"','"+ hobby +"','"+memberEmail+"','"+ ip +"')";
	//midx_b_seq 시퀀스가 디비에서 생성되어 있어야 함.
	
	
	/*
	try//예외처리
	{

		//구문을 만들어..?
		//연결객체를 통해서 Statement 구문객체를 생성해서 stmt에 담는다
		Statement stmt = conn.createStatement();//구문을 실행하는 객체.
		//statement 구문을 사용?생성?할 수 있는 클래스 생성
				
		//구문객체가 해당되는 쿼리문 실행하게 함..
		//입력(db) insert
		 value = stmt.executeUpdate(sql);	//sql문장을 집어넣어주기 리턴값이 int형임.

		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	*/
	 
	
	
	
	
	
	
	//sql injection '"+  +"'... 문제발생!	
	//-->staement 사용해서 변수사용하는 대신 preparestatement 사용해서 메소드(get,set..)사용하기
	PreparedStatement pstmt = null;
	String sql = "insert into b_member(MIDX,MEMBERID,MEMBERPWD,MEMBERNAME,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP) "
			+"values(MIDX_B_SEQ.nextval,?,?,?,?,?,?,?,?,?,?)";
	
	//?은 메소드를 받는다.간단하고 보안성 강함
	try{
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,memberId);
		pstmt.setString(2,memberPwd);
		pstmt.setString(3,memberName);
		pstmt.setString(4,memberGender);
		pstmt.setString(5,memberAddr);
		pstmt.setString(6,memberJumin);
		pstmt.setString(7,memberPhone);
		pstmt.setString(8,hobby);
		pstmt.setString(9,memberEmail);
		pstmt.setString(10,ip);

		value=pstmt.executeUpdate();
		
		//sql문장을 집어넣어주기 리턴값이 int형임.

		
		
	}catch(Exception e){
		e.printStackTrace();
	}
	
	
	
	 return value;
	 
 }
 
 
 public ArrayList<MemberVo> memberSelectAll(Connection conn)
 {
	 
	 //디비에서 데이터를 꺼내와서 담는 클래스 : MemberVo 
	 //어레이리스트는 클래스를 담는 클래스임.
	 //어레이리스트에 MemberVo객체를 담을겨
	 
	 //memberVo 여러 객체를 다는 ArrayList  클래스를 객체생성한다.
	ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
	PreparedStatement pstmt = null;
	ResultSet rs = null;//select 에서 나오는 값을 담는 전용 클래스...여러 줄로...
		
		
	 String sql = "select * from b_member where delyn = 'N' order by midx desc";
	 
	 //연결객체에 있는 prepareStatement 메소드를 실행해서 sql문자열을 담아 구문개체를 만든다.
	 
	 try{
		 
		 pstmt = conn.prepareStatement(sql);
		 rs = pstmt.executeQuery();
		 
		 while(rs.next())//다음 데이터가 존재하면 true 반환
		 {
			 //반복할 때마다 객체 생성
			 MemberVo mv = new MemberVo();
			 
			 //옮겨담기
			 //rs에 담아놓은 컬럼값들을 mv에 옮겨 담는다.
			 mv.setMidx(rs.getInt("midx"));
			 mv.setMembername(rs.getString("memberName"));
			 mv.setMemberphone(rs.getString("memberphone"));
			 mv.setWriteday(rs.getString("writeday"));
			
			 //alist에 값을 담은 객체를 추가한다.
			 alist.add(mv);
			 
			 
		 }
		 
		 
	 
	 
	 }catch(Exception e){
		 e.printStackTrace();
	 }
	 
	 
	 return alist;
 }
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 %>