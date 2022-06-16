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
//가상경로가 .do로 들어오는 모든 것은 MemberController(여기)에서 받아서 처리하도록 할 거임
//이젠 frontcontroller가 받게 봐꿔줄거임
public class MemberController extends HttpServlet {
	//처리 및 이동하는 부분을 (모델1)jsp가 아닌 (모델2)서블릿(컨트롤러역할)으,로 jsp는 화면만하게.
	private static final long serialVersionUID = 1L;
	
	//웹페이지 상단에 위치가 노출됨.
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		//가상경로의 이름 추출하기
		String uri = request.getRequestURI();
		System.out.println("MemberController uri: " + uri);//	uri: /jspstudy/member/memberJoinAction.do
		
		String pj = request.getContextPath();//프로젝트명 출력
		System.out.println("MemberController pj: "+pj);//	pj: /jspstudy
		
		String command = uri.substring(pj.length());// 리퀘스트한 곳의 경로를 앞에서 프로젝트명까지를 잘라주기 
		System.out.println("MemberController command: " + command);//	command: /member/memberJoinAction.do
		
		
		
		//memberJoin.jsp에서 보낸 경로는 /jspstudy/member/memberJoinAction.do임 
		//여기서 보낸 게 맞는지 확인해보고 처리해보쟝
		if(command.equals("/member/memberJoinAction.do"))
		{

			//memberjoinOk.jsp로 가지않고 여기로..내용은 똑같이
			
			
			String memberId = request.getParameter("memberId");//출력한 것을 memberId 스트링 변수로 받음/대입.
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
			
			hobby=hobby.substring(1);//1번 인덱스부터 끝까지 자르기
	
			
			
			
			MemberDao md = new MemberDao();
			
			String ip = InetAddress.getLocalHost().getHostAddress();//ip추출

			int value = md.insertMember(memberId,memberPwd,memberName,memberGender,memberAddr,memberJumin,memberPhone,hobby,memberEmail,ip);
			System.out.println(value);
		//	PrintWriter out = response.getWriter();
			//입력이 되었으면 회원목록리스트로 이동을 하자
			if(value==1){
				//<자바스크립트 방식>
				//out.println("<script>alert('회원가입성공');  location.href='"+request.getContextPath()+"/'</script>");
				//   /jspstudy/ : jspstudy패키지의 루트로 이동. 근데 web.xml을 보면 welcome 파일이 index.확장자 로 되어있기때문에
				//					jspstudy패키지의 루트인 index.jsp로 이동하라는 뜻임
				//	/jspstudy/   ==    /jspstudy/index.jsp
				
				//<SendRedirect>
				//모든 액션을 끝내고 새로운 곳으로 이동(내외부 주소바뀜)
				response.sendRedirect(request.getContextPath()+"/member/memberList.do");//가상주소로
				//						/프로젝트명/member/memberList.do
								
			}else{
				//<자바스크립트 방식>
				//out.println("<script>alert('회원가입실패');  location.href='./memberJoin.html'</script>");
				response.sendRedirect(request.getContextPath()+"/member/memberJoin.do");//가상주소로

			}
			
		}
		else if(command.equals("/member/memberJoin.do"))//회원가입 화면 보여주기
		{
			RequestDispatcher rd =request.getRequestDispatcher("/member/memberJoin.jsp");
			//가상경로로 왔다면 리퀘스트디스페처에 담아서 포워드 방식으로 넘기기...
			rd.forward(request, response);
			
			
			
		//샌드리퀘스트 vs 리퀘스트디스패처
			//포워드: 외부적으로 클라이언트가 가상경로를 클릭 -> 내부적으로 실제 페이지를 보여줌 - >주소창에는 가짜경로를 보여줌
			//내부적으로는 실제 페이지, 외부적으론 가짜 주소를 보여줌.
			//물리적인 주소를 주소창으로 보여주면 노출됨=보안 우려 ==> 가상경로를 사용하자
			//기존 리퀘스트와 리스폰즈는 유지
			//자원은 공유
			
			//샌드리다이렉트
			//내부주소로 바꿀 때 
			
			
			
			//action 처리하는 부분은 샌드리다이렉트
			// 모든 페이지 이동하는 방식은 가상경로 포워드!
			//업데이트 딜리트 모든 처리를 끝내고 새롭게 페이지 이동시작할 때는 샌드 리퀘스트
			//액션을 넘기고 인절트됐다면 다시 이동가상경로로 넘겨줌 (샌드리다이랙트)
			
		}
		else if(command.equals("/member/memberList.do"))//회원 리스트 보여주기
		{
			MemberDao md = new MemberDao();
			ArrayList<MemberVo> alist = md.memberSelectAll();
			
			 request.setAttribute("alist", alist);//1이름으로 2를 담아서...
			 //파라미터와 달리 내부적으로 자원을 공유해서 쓸 때 값을 공유...
			
			//포워드 방식
			RequestDispatcher rd =request.getRequestDispatcher("/member/memberList.jsp");
			//가상경로로 왔다면 리퀘스트디스페처에 담아서 포워드 방식으로 넘기기...
			rd.forward(request, response);
			
			//외부주소는 가상경로 그대로 내부주소는 jsp로..
		}
		else if(command.equals("/member/memberLogin.do"))
		{
			

			//포워드 방식
			RequestDispatcher rd =request.getRequestDispatcher("/member/memberLogin.jsp");
			//가상경로로 왔다면 리퀘스트디스페처에 담아서 포워드 방식으로 넘기기...
			rd.forward(request, response);
			
		}
		else if(command.equals("/member/memberLoginAction.do"))
		{
			//1. 넘어온 값을 받기
			String memberId = request.getParameter("memberId");
			String memberPwd = request.getParameter("memberPwd");
			
			
			//2. 처리
			MemberDao md = new MemberDao();
			MemberVo mv = md.memberLogin(memberId, memberPwd);
			System.out.println("mv : "+mv);
			HttpSession session = request.getSession();
			
			
			
			
			
			//3. 이동
			if (mv != null)//회원정보가 존재한다면
			{
				session.setAttribute("midx", mv.getMidx());
				session.setAttribute("memberId", mv.getMemberid());
				session.setAttribute("memberName", mv.getMembername());
				//브라우저창을 닫기 전 로그아웃 하기 전까지 세션안에 담긴 정보는 얼마든지 꺼내 쓸 수 있다.
				
				if(session.getAttribute("saveUrl") !=null)
				{
					response.sendRedirect((String)session.getAttribute("saveUrl"));
				}
				else {
					response.sendRedirect(request.getContextPath()+"/member/memberList.do");
				}
			}
			else//회원 정보가 존재 아지 않다면
			{
				System.out.println("회원 없다");
				response.sendRedirect(request.getContextPath()+"/member/memberLogin.do");
			}
			
			
			
			
			
		}
		else if(command.equals("/member/memberLogout.do"))
		{
			//로그아웃시
			HttpSession session = request.getSession();
			session.invalidate();//세션  초기화
			response.sendRedirect(request.getContextPath()+"/");//메인으로 가기(웰컴이 indext.jsp임)
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		/*
		 * 웹서비스 프로토콜인 http방식은 클라이언트가 request하고 서버가 response하고 나면 통신이 끊긴다.
		 *  
		 * RequestDispatcher 인터페이스의 메서드 중 하나인 forward()는 사용자의 요청에 의해
		 * 컨테이너에서 생성된 request와 response를 다른 리소스(서블릿,jsp,html)로 넘겨주는 역할을 한다.
		 * 즉 response로 응답하여 통신을 끝내기 전까지 request객체를 이리저리 옮겨쓸 수 있다.
		 * 
		 * forward : 다른 리소스로 패스
		 * sendRedirect : response객체와 함께 응답하며 통신을 끊음
		 * 
		 * 
		 * */
		
		
		//forword : 페이지 출력, 페이지 전환
		
		//sendRedirect: 특정 url로 재요청, Action.do에서 사용..
		
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);//삭제하면 안돼여
	}

}
