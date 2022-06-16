package jspstudy.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jspstudy.dbconn.Dbconn;
import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.MemberVo;
import jspstudy.domain.SearchCriteria;

public class BoardDao {

	private Connection conn;
	private PreparedStatement pstmt;
	
	public BoardDao()
	{
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	
	public int insertBoard(String subject, String content, String writer, String ip, int midx, String fileName) 
	{
		//쿼리 실행하기 위한 메소드임 
		int value = 0;
		
		String sql = "INSERT INTO B_BOARD(ORIGINBIDX,DEPTH,LEVEL_,SUBJECT,CONTENT,WRITER,IP,MIDX,filename)"
					+" select max(bidx)+1,0,0,?,?,?,?,?,? from b_board";

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, ip);
			pstmt.setInt(5, midx);
			pstmt.setString(6, fileName);
			value = pstmt.executeUpdate();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
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
	
	public ArrayList<BoardVo> boardSelectAll(SearchCriteria scri)
	 {
		 
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();//디비에서 셀렉트한 데이터를 모두 가져오는 역할
		ResultSet rs = null;//select 에서 나오는 값을 담는 전용 클래스...여러 줄로...
		String str="";
			
		 //String sql = "select * from b_board where delyn = 'N' order by originbidx desc, depth asc";
		
		if(scri.getSearchType().equals("subject"))
		{
			str = "and subject like ?";
		}
		else
		{
			str = "and writer like ?";
		}
		
		String sql =  "SELECT * FROM ("
				+ "	SELECT ROW_NUMBER() OVER (ORDER BY originbidx DESC) AS rnum, A.* FROM ("
				+ "	select * from b_board where delyn = 'N' "+str+" order by originbidx desc, depth asc) A"
				+ "	) B order by rnum limit ?, ?";
		 //연결객체에 있는 prepareStatement 메소드를 실행해서 sql문자열을 담아 구문개체를 만든다.
		 
		 try{
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, "%"+scri.getKeyword()+"%");
			 pstmt.setInt(2, (scri.getPage()-1)*15);
			 pstmt.setInt(3, 15);

			 rs = pstmt.executeQuery();
			 
			 while(rs.next())//rs.next() : 다음 데이터가 존재하면 true 반환하고 그 행으로 커서가 이동하는 메소드
			 {
				 //반복할 때마다 객체 생성
				 BoardVo bv = new BoardVo();
				 
				 //옮겨담기
				 //rs에 담아놓은 컬럼값들을 mv에 옮겨 담는다.
				 bv.setBidx(rs.getInt("BIDX"));
				 bv.setSubject(rs.getString("subject"));
				 bv.setWriter(rs.getString("writer"));
				 bv.setWriteday(rs.getString("writeday"));
				 bv.setLevel_(rs.getInt("level_"));
				 bv.setFilename(rs.getString("filename"));
				 //alist에 값을 담은 객체를 추가한다.
				 alist.add(bv);
				 
				 
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
		 
		 
		 return alist;
	 }
	 
	public BoardVo boardSelectOn(int bidx)
	{
		BoardVo bv = null;
		ResultSet rs = null;
		//쿼리문 실행하기
		String sql = "select * from b_board where bidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);		//쿼리화 시킴
			pstmt.setInt(1, bidx);					//첫번째 ?에 bidx값을 담아라.
			rs = pstmt.executeQuery();				//실행이된 값을 rs에 복사해서 담기
			
			//rs를 bv에 담기
			if(rs.next())//다음 값이 존재하면 true, 커서는 다음 행으로 이동한다.
			{
				bv = new BoardVo();	//객체생성을 해서
				bv.setBidx(rs.getInt("bidx"));	//rs에 담겨져 있는 데이터를 bv에 옮겨담는다.
				bv.setOriginbidx(rs.getInt("originbidx"));
				bv.setDepth(rs.getInt("depth"));
				bv.setLevel_(rs.getInt("level_"));
				
				bv.setSubject(rs.getString("subject"));
				bv.setContent(rs.getString("content"));
				bv.setWriter(rs.getString("writer"));
				bv.setWriteday(rs.getString("writeday"));
				
				bv.setFilename(rs.getString("filename"));
				
				
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		
		return bv;
	}
	

	public int updateBoard(String subject, String content, String writer, String ip, int midx, int bidx) 
	{
		//쿼리 실행하기 위한 메소드임 
		int value = 0;
		
		String sql = "UPDATE b_board SET subject = ?, content = ? , writer = ?, ip = ?, midx = ?, writeday=NOW() WHERE bidx=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, subject);
			pstmt.setString(2, content);
			pstmt.setString(3, writer);
			pstmt.setString(4, ip);
			pstmt.setInt(5, midx);
			pstmt.setInt(6,bidx );
			value = pstmt.executeUpdate();

			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return value;
	}
	
	public int deleteBoard(int bidx)
	{
		int value=0;
		
		String sql = "UPDATE B_BOARD SET DELYN='Y', WRITEDAY=NOW() WHERE BIDX=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bidx);
			value = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	public int replyBoard(BoardVo bv) 
	{
		//쿼리 실행하기 위한 메소드임 
		int value = 0;
		
		
		String sql1 = "update b_board set depth = depth + 1 where originbidx = ? and depth > ?";
				
		
		String sql2 = "INSERT INTO B_BOARD(ORIGINBIDX,DEPTH,LEVEL_,SUBJECT,CONTENT,WRITER,IP,MIDX)"
					+" values(?,?,?,?,?,?,?,?)";

		try {
			//원래 자바에선 쿼리문 실행시 자동 커밋이었음.
			

			//sql1 과 sql2가 동시에 발생해야함! --> 한그룹 작업단위로 묶음 . 트랜젝션
			//둘이 함께 처리해야 커밋, 처리 안 되면 롤백
			
			conn.setAutoCommit(false);//자동 커밋 기능 끔
			pstmt = conn.prepareStatement(sql1);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth());
			pstmt.executeUpdate();
			
			
			pstmt = conn.prepareStatement(sql2);
			pstmt.setInt(1, bv.getOriginbidx());
			pstmt.setInt(2, bv.getDepth()+1);
			pstmt.setInt(3, bv.getLevel_()+1);
			pstmt.setString(4, bv.getSubject());
			pstmt.setString(5, bv.getContent());
			pstmt.setString(6, bv.getWriter());
			pstmt.setString(7, bv.getIp());
			pstmt.setInt(8, bv.getMidx());
			value = pstmt.executeUpdate();

			conn.commit();
			
			
		} catch (SQLException e) {
			//try문 중간에 오류가 발생해서 멈췄다면 롤백처리를 해야함.
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return value;
	}
	
	public int boardTotal(SearchCriteria scri)//전체 개수 뽑기
	{
		int cnt=0;
		ResultSet rs = null;
		String str="";
		
		if(scri.getSearchType().equals("subject"))
		{
			str = "and subject like ?";
		}
		else
		{
			str = "and writer like ?";
		}
		
		String sql = "select count(*) as cnt from b_board where delyn='N' "+str+"  ";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+scri.getKeyword()+"%");
			rs = pstmt.executeQuery();
			
			if(rs.next())
			{
				cnt = rs.getInt("cnt");
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
		
		
		return cnt;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	 
	 
	 
	 
	 
}
