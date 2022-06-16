package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jspstudy.domain.MemberVo;
import jspstudy.service.MemberDao;

@WebServlet("/MemberController")
//�����ΰ� .do�� ������ ��� ���� MemberController(����)���� �޾Ƽ� ó���ϵ��� �� ����
//���� frontcontroller�� �ް� �����ٰ���
public class MemberController extends HttpServlet {
	//ó�� �� �̵��ϴ� �κ��� (��1)jsp�� �ƴ� (��2)����(��Ʈ�ѷ�����)��,�� jsp�� ȭ�鸸�ϰ�.
	private static final long serialVersionUID = 1L;
	
	//�������� ��ܿ� ��ġ�� �����.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//�������� �̸� �����ϱ�
		String uri = request.getRequestURI();
		System.out.println("MemberController uri: " + uri);//	uri: /jspstudy/member/memberJoinAction.do
		
		String pj = request.getContextPath();//������Ʈ�� ���
		System.out.println("MemberController pj: "+pj);//	pj: /jspstudy
		
		String command = uri.substring(pj.length());// ������Ʈ�� ���� ��θ� �տ��� ������Ʈ������� �߶��ֱ� 
		System.out.println("MemberController command: " + command);//	command: /member/memberJoinAction.do
		
		
		
		//memberJoin.jsp���� ���� ��δ� /jspstudy/member/memberJoinAction.do�� 
		//���⼭ ���� �� �´��� Ȯ���غ��� ó���غ���
		if(command.equals("/member/memberJoinAction.do"))
		{

			//memberjoinOk.jsp�� �����ʰ� �����..������ �Ȱ���
			
			
			String memberId = request.getParameter("memberId");//����� ���� memberId ��Ʈ�� ������ ����/����.
			String memberPwd = request.getParameter("memberPwd");
			String memberName = request.getParameter("memberName");
			String memberEmail = request.getParameter("memberEmail");
			String memberPhone = request.getParameter("memberPhone");
			String memberJumin = request.getParameter("memberJumin");
			String memberGender = request.getParameter("memberGender");
			String memberAddr = request.getParameter("memberAddr");
			String[] memberHobby = request.getParameterValues("memberHobby");

			
			
			String hobby = "";
			for(int i=0;i<memberHobby.length;i++)
			{
				hobby = hobby + "," + memberHobby[i];
			}
			
			hobby=hobby.substring(1);//1�� �ε������� ������ �ڸ���
	
			
			
			
			MemberDao md = new MemberDao();
			
			String ip = InetAddress.getLocalHost().getHostAddress();//ip����

			int value = md.insertMember(memberId,memberPwd,memberName,memberGender,memberAddr,memberJumin,memberPhone,hobby,memberEmail,ip);
			System.out.println(value);
		//	PrintWriter out = response.getWriter();
			//�Է��� �Ǿ����� ȸ����ϸ���Ʈ�� �̵��� ����
			if(value==1){
				//<�ڹٽ�ũ��Ʈ ���>
				//out.println("<script>alert('ȸ�����Լ���');  location.href='"+request.getContextPath()+"/'</script>");
				//   /jspstudy/ : jspstudy��Ű���� ��Ʈ�� �̵�. �ٵ� web.xml�� ���� welcome ������ index.Ȯ���� �� �Ǿ��ֱ⶧����
				//					jspstudy��Ű���� ��Ʈ�� index.jsp�� �̵��϶�� ����
				//	/jspstudy/   ==    /jspstudy/index.jsp
				
				//<SendRedirect>
				//��� �׼��� ������ ���ο� ������ �̵�(���ܺ� �ּҹٲ�)
				response.sendRedirect(request.getContextPath()+"/member/memberList.do");//�����ּҷ�
				//						/������Ʈ��/member/memberList.do
								
			}else{
				//<�ڹٽ�ũ��Ʈ ���>
				//out.println("<script>alert('ȸ�����Խ���');  location.href='./memberJoin.html'</script>");
				response.sendRedirect(request.getContextPath()+"/member/memberJoin.do");//�����ּҷ�

			}
			
		}
		else if(command.equals("/member/memberJoin.do"))//ȸ������ ȭ�� �����ֱ�
		{
			RequestDispatcher rd =request.getRequestDispatcher("/member/memberJoin.jsp");
			//�����η� �Դٸ� ������Ʈ����ó�� ��Ƽ� ������ ������� �ѱ��...
			rd.forward(request, response);
			
			
			
		//���帮����Ʈ vs ������Ʈ����ó
			//������: �ܺ������� Ŭ���̾�Ʈ�� �����θ� Ŭ�� -> ���������� ���� �������� ������ - >�ּ�â���� ��¥��θ� ������
			//���������δ� ���� ������, �ܺ������� ��¥ �ּҸ� ������.
			//�������� �ּҸ� �ּ�â���� �����ָ� �����=���� ��� ==> �����θ� �������
			//���� ������Ʈ�� ��������� ����
			//�ڿ��� ����
			
			//���帮���̷�Ʈ
			//�����ּҷ� �ٲ� �� 
			
			
			
			//action ó���ϴ� �κ��� ���帮���̷�Ʈ
			// ��� ������ �̵��ϴ� ����� ������ ������!
			//������Ʈ ����Ʈ ��� ó���� ������ ���Ӱ� ������ �̵������� ���� ���� ������Ʈ
			//�׼��� �ѱ�� ����Ʈ�ƴٸ� �ٽ� �̵������η� �Ѱ��� (���帮���̷�Ʈ)
			
		}
		else if(command.equals("/member/memberList.do"))//ȸ�� ����Ʈ �����ֱ�
		{
			MemberDao md = new MemberDao();
			ArrayList<MemberVo> alist = md.memberSelectAll();
			
			 request.setAttribute("alist", alist);//1�̸����� 2�� ��Ƽ�...
			 //�Ķ���Ϳ� �޸� ���������� �ڿ��� �����ؼ� �� �� ���� ����...
			
			//������ ���
			RequestDispatcher rd =request.getRequestDispatcher("/member/memberList.jsp");
			//�����η� �Դٸ� ������Ʈ����ó�� ��Ƽ� ������ ������� �ѱ��...
			rd.forward(request, response);
			
			//�ܺ��ּҴ� ������ �״�� �����ּҴ� jsp��..
		}
		else if(command.equals("/member/memberLogin.do"))
		{
			

			//������ ���
			RequestDispatcher rd =request.getRequestDispatcher("/member/memberLogin.jsp");
			//�����η� �Դٸ� ������Ʈ����ó�� ��Ƽ� ������ ������� �ѱ��...
			rd.forward(request, response);
			
		}
		else if(command.equals("/member/memberLoginAction.do"))
		{
			//1. �Ѿ�� ���� �ޱ�
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			
			
			//2. ó��
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(memberId, memberPwd);
			System.out.println("mv : "+mv);
			HttpSession session = request.getSession();
			
			
			
			
			
			//3. �̵�
			if (mv != null)//ȸ�������� �����Ѵٸ�
			{
				session.setAttribute("midx", mv.getMidx());
				session.setAttribute("memberId", mv.getMemberid());
				session.setAttribute("memberName", mv.getMembername());
				//������â�� �ݱ� �� �α׾ƿ� �ϱ� ������ ���Ǿȿ� ��� ������ �󸶵��� ���� �� �� �ִ�.
				
				if(session.getAttribute("saveUrl") !=null)
				{
					response.sendRedirect((String)session.getAttribute("saveUrl"));
				}
				else {
					response.sendRedirect(request.getContextPath()+"/member/memberList.do");
				}
			}
			else//ȸ�� ������ ���� ���� �ʴٸ�
			{
				System.out.println("ȸ�� ����");
				response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
			}
			
			
			
			
			
		}
		else if(command.equals("/member/memberLogout.do"))
		{
			//�α׾ƿ���
			HttpSession session = request.getSession();
			session.invalidate();//����  �ʱ�ȭ
			response.sendRedirect(request.getContextPath()+"/");//�������� ����(������ indext.jsp��)
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * ������ ���������� http����� Ŭ���̾�Ʈ�� request�ϰ� ������ response�ϰ� ���� ����� �����.
		 *  
		 * RequestDispatcher �������̽��� �޼��� �� �ϳ��� forward()�� ������� ��û�� ����
		 * �����̳ʿ��� ������ request�� response�� �ٸ� ���ҽ�(����,jsp,html)�� �Ѱ��ִ� ������ �Ѵ�.
		 * �� response�� �����Ͽ� ����� ������ ������ request��ü�� �̸����� �Űܾ� �� �ִ�.
		 * 
		 * forward : �ٸ� ���ҽ��� �н�
		 * sendRedirect : response��ü�� �Բ� �����ϸ� ����� ����
		 * 
		 * 
		 * */
		
		
		//forword : ������ ���, ������ ��ȯ
		
		//sendRedirect: Ư�� url�� ���û, Action.do���� ���..
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);//�����ϸ� �ȵſ�
	}

}
