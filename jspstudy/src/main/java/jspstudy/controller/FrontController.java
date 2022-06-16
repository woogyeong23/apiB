package jspstudy.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FrontController")
//
//web.xml설정파일에서 등록하기:웹설정파일의 용도를 알아야 하기 때문에 매핑해보자
//서블릿 등록 서블릿 매핑
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//
		String uri = request.getRequestURI();
		System.out.println("FrontController uri: " + uri);//	uri: /jspstudy/member/memberJoinAction.do
		
		String pj = request.getContextPath();//프로젝트명 출력
		System.out.println("FrontController pj: "+pj);//	pj: /jspstudy
		
		String command = uri.substring(pj.length());// 리퀘스트한 곳의 경로를 앞에서 프로젝트명까지를 잘라주기 
		System.out.println("FrontController command: " + command);//	command: /member/memberJoinAction.do
		
		//ex)    /member/memberList.do
		
		String[] subpath = command.split("/");
		String location = subpath[1];	//두번째 마디에 있는 member 문자열이 추출
		System.out.println(subpath[0]);
		if(location.equals("member"))
		{
			MemberController mc = new MemberController();//서블릿 객체생성
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
