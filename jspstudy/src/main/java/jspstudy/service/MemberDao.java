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
	
	private Connection conn;//��������� ���������� ����� �� �ֱ⶧���� �Լ��� �Ķ���ͷ� �־��� ���� ����.
	private PreparedStatement pstmt ;//�� �Լ� �ȿ� �־��� ����� ���������̱� ������ ���������� �ٲ���. ���������� �־��� ���� ����

	
	
	public MemberDao()
	{
		Dbconn db = new Dbconn();
		this.conn = db.getConnection();
	}
	
	
	
	
	public int insertMember(String memberId, String memberPwd, String memberName, String memberGender, String memberAddr, String memberJumin, String memberPhone, String hobby, String memberEmail, String ip)
	 {
		int value=0;
		 
		
		
		//sql injection '"+  +"'... �����߻�!	
		//-->staement ����ؼ� ��������ϴ� ��� preparestatement ����ؼ� �޼ҵ�(get,set..)����ϱ�
		String sql = "insert into b_member(MEMBERID,MEMBERPWD,MEMBERNAME,MEMBERGENDER,MEMBERADDR,MEMBERJUMIN,MEMBERPHONE,MEMBERHOBBY,MEMBEREMAIL,MEMBERIP) "
				+"values(?,?,?,?,?,?,?,?,?,?)";
		
		//?�� �޼ҵ带 �޴´�.�����ϰ� ���ȼ� ����
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
			
			//sql������ ����־��ֱ� ���ϰ��� int����.

			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			
			//try�� ��ġ�� catch�� ��ġ�� ����
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
		 
		 //��񿡼� �����͸� �����ͼ� ��� Ŭ���� : MemberVo 
		 //��̸���Ʈ�� Ŭ������ ��� Ŭ������.
		 //��̸���Ʈ�� MemberVo��ü�� ������
		 
		 //���� memberVo ��ü�� ��� ArrayList  Ŭ������ ��ü�����Ѵ�.
		ArrayList<MemberVo> alist = new ArrayList<MemberVo>();
		ResultSet rs = null;//select ���� ������ ���� ��� ���� Ŭ����...���� �ٷ�...
			
			
		 String sql = "select * from b_member where delyn = 'N' order by midx desc";
		 
		 //���ᰴü�� �ִ� prepareStatement �޼ҵ带 �����ؼ� sql���ڿ��� ��� ������ü�� �����.
		 
		 try{
			 
			 pstmt = conn.prepareStatement(sql);
			 rs = pstmt.executeQuery();
			 
			 while(rs.next())//���� �����Ͱ� �����ϸ� true ��ȯ, Ŀ�� ���� ������ �̵�
			 {				System.out.println("rs�� �� ����");

				 //�ݺ��� ������ ��ü ����
				 MemberVo mv = new MemberVo();
				 
				 //�Űܴ��
				 //rs�� ��Ƴ��� �÷������� �ٽ� mv�� �Ű� ��´�.
				 mv.setMidx(rs.getInt("midx"));
				 mv.setMembername(rs.getString("memberName"));
				 mv.setMemberphone(rs.getString("memberphone"));
				 mv.setWriteday(rs.getString("writeday"));
				
				 //alist�� ���� ���� ��ü�� �߰��Ѵ�.
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
