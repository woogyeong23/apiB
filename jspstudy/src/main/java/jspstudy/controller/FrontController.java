package jspstudy.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FrontController")
//
//web.xml�������Ͽ��� ����ϱ�:������������ �뵵�� �˾ƾ� �ϱ� ������ �����غ���
//���� ��� ���� ����
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//
		String uri = request.getRequestURI();
		System.out.println("FrontController uri: " + uri);//	uri: /jspstudy/member/memberJoinAction.do
		
		String pj = request.getContextPath();//������Ʈ�� ���
		System.out.println("FrontController pj: "+pj);//	pj: /jspstudy
		
		String command = uri.substring(pj.length());// ������Ʈ�� ���� ��θ� �տ��� ������Ʈ������� �߶��ֱ� 
		System.out.println("FrontController command: " + command);//	command: /member/memberJoinAction.do
		
		//ex)    /member/memberList.do
		
		String[] subpath = command.split("/");
		String location = subpath[1];	//�ι�° ���� �ִ� member ���ڿ��� ����
		System.out.println(subpath[0]);
		if(location.equals("member"))
		{
			MemberController mc = new MemberController();//���� ��ü����
			mc.doGet(request, response);
			
		}
		else if (location.equals("board"))
		{
			BoardController bc = new BoardController();
			bc.doGet(request, response);
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
		
		doGet(request, response);
	}

}
