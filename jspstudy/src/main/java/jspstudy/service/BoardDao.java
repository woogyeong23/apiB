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
		//���� �����ϱ� ���� �޼ҵ��� 
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
		 
		ArrayList<BoardVo> alist = new ArrayList<BoardVo>();//��񿡼� ����Ʈ�� �����͸� ��� �������� ����
		ResultSet rs = null;//select ���� ������ ���� ��� ���� Ŭ����...���� �ٷ�...
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
		 //���ᰴü�� �ִ� prepareStatement �޼ҵ带 �����ؼ� sql���ڿ��� ��� ������ü�� �����.
		 
		 try{
			 
			 pstmt = conn.prepareStatement(sql);
			 pstmt.setString(1, "%"+scri.getKeyword()+"%");
			 pstmt.setInt(2, (scri.getPage()-1)*15);
			 pstmt.setInt(3, 15);

			 rs = pstmt.executeQuery();
			 
			 while(rs.next())//rs.next() : ���� �����Ͱ� �����ϸ� true ��ȯ�ϰ� �� ������ Ŀ���� �̵��ϴ� �޼ҵ�
			 {
				 //�ݺ��� ������ ��ü ����
				 BoardVo bv = new BoardVo();
				 
				 //�Űܴ��
				 //rs�� ��Ƴ��� �÷������� mv�� �Ű� ��´�.
				 bv.setBidx(rs.getInt("BIDX"));
				 bv.setSubject(rs.getString("subject"));
				 bv.setWriter(rs.getString("writer"));
				 bv.setWriteday(rs.getString("writeday"));
				 bv.setLevel_(rs.getInt("level_"));
				 bv.setFilename(rs.getString("filename"));
				 //alist�� ���� ���� ��ü�� �߰��Ѵ�.
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
		//������ �����ϱ�
		String sql = "select * from b_board where bidx=?";
		
		try {
			pstmt = conn.prepareStatement(sql);		//����ȭ ��Ŵ
			pstmt.setInt(1, bidx);					//ù��° ?�� bidx���� ��ƶ�.
			rs = pstmt.executeQuery();				//�����̵� ���� rs�� �����ؼ� ���
			
			//rs�� bv�� ���
			if(rs.next())//���� ���� �����ϸ� true, Ŀ���� ���� ������ �̵��Ѵ�.
			{
				bv = new BoardVo();	//��ü������ �ؼ�
				bv.setBidx(rs.getInt("bidx"));	//rs�� ����� �ִ� �����͸� bv�� �Űܴ�´�.
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
		//���� �����ϱ� ���� �޼ҵ��� 
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
		//���� �����ϱ� ���� �޼ҵ��� 
		int value = 0;
		
		
		String sql1 = "update b_board set depth = depth + 1 where originbidx = ? and depth > ?";
				
		
		String sql2 = "INSERT INTO B_BOARD(ORIGINBIDX,DEPTH,LEVEL_,SUBJECT,CONTENT,WRITER,IP,MIDX)"
					+" values(?,?,?,?,?,?,?,?)";

		try {
			//���� �ڹٿ��� ������ ����� �ڵ� Ŀ���̾���.
			

			//sql1 �� sql2�� ���ÿ� �߻��ؾ���! --> �ѱ׷� �۾������� ���� . Ʈ������
			//���� �Բ� ó���ؾ� Ŀ��, ó�� �� �Ǹ� �ѹ�
			
			conn.setAutoCommit(false);//�ڵ� Ŀ�� ��� ��
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
			//try�� �߰��� ������ �߻��ؼ� ����ٸ� �ѹ�ó���� �ؾ���.
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
	
	public int boardTotal(SearchCriteria scri)//��ü ���� �̱�
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
