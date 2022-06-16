package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.MemberVo;

public class MemberDao {
//Dao : data access object
	
	private Connection conn;//멤버변수가 전역변수로 사용할 수 있기때문에 함수의 파라미터로 있었던 것은 삭제.
	private PreparedStatement pstmt ;//각 함수 안에 있었던 공통된 지역변수이기 때문에 전역변수로 바꿔줌. 지역변수에 있었던 것은 삭제

	
	
	public MemberDao()
	{
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	
	
	
	public int insertMember(String memberId, String memberPwd, String memberName, String memberGender, String memberAddr, String memberJumin, String memberPhone, String hobby, String memberEmail, String ip)
	 {
		int value=0;
		 
		
		
		//sql injection '"+  +"'... 문제발생!	
		//-->staement 사용해서 변수사용하는 대신 preparestatement 사용해서 메소드(get,set..)사용하기
		String sql = "insert into b_member(MEMBERID,MEMBERPWD,MEMBERNAME,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP) "
				+"values(?,?,?,?,?,?,?,?,?,?)";
		
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
		}finally {
			
			//try를 거치든 catch를 거치든 실행
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		 return value;
		 
	 }
	 
	 
	 public ArrayList<MemberVo> memberSelectAll()
	 {
		 
		 //디비에서 데이터를 꺼내와서 담는 클래스 : MemberVo 
		 //어레이리스트는 클래스를 담는 클래스임.
		 //어레이리스트에 MemberVo객체를 담을겨
		 
		 //여러 memberVo 객체를 담는 ArrayList  클래스를 객체생성한다.
		ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
		ResultSet rs = null;//select 에서 나오는 값을 담는 전용 클래스...여러 줄로...
			
			
		 String sql = "select * from b_member where delyn = 'N' order by midx desc";
		 
		 //연결객체에 있는 prepareStatement 메소드를 실행해서 sql문자열을 담아 구문개체를 만든다.
		 
		 try{
			 
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next())//다음 데이터가 존재하면 true 반환, 커서 다음 행으로 이동
			 {				System.out.println("rs에 잘 담겼다");

				 //반복할 때마다 객체 생성
				 MemberVo mv = new MemberVo();
				 
				 //옮겨담기
				 //rs에 담아놓은 컬럼값들을 다시 mv에 옮겨 담는다.
				 mv.setMidx(rs.getInt("midx"));
				 mv.setMembername(rs.getString("memberName"));
				 mv.setMemberphone(rs.getString("memberphone"));
				 mv.setWriteday(rs.getString("writeday"));
				
				 //alist에 값을 담은 객체를 추가한다.
				 alist.add(mv);
				 
				 
			 }
			 
			 
		 
		 
		 }catch(Exception e){
			 e.printStackTrace();
		 }finally {
			 try {
				 rs.close();
				pstmt.close();
				 conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			 
		 }
		// System.out.println("alist : "+alist);
		 
		 return alist;
	 }
	 
	 public MemberVo memberLogin(String memberId, String memberPwd)
	 {
		 MemberVo mv = null;
		 ResultSet rs = null;
		 
		 String sql = "select * from b_member where delyn = 'N' and memberid = ? and memberpwd = ? ";
		 
		 try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPwd);
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				mv = new MemberVo();
				mv.setMidx(rs.getInt("midx"));
				mv.setMemberid(rs.getString("memberid"));
				mv.setMembername(rs.getString("membername"));
				
				
				
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		 
		 
		 
		 return mv;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}
