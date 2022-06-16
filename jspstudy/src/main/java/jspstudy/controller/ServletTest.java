package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {//http통신을 하기 위해 자바에서 제공하는 클래스임. 상속을 받게 되면 웹을 실행하기 위한 클래스가 됨.(웹페이지가 뜸)
	//서블릿은 자바로 만든 웹페이지임.
	//서블릿은 자바코드에서 html코드사용
	//jsp는 html코드에서 자바코드(<%   out.println... )사용
	
	private static final long serialVersionUID = 1L;
/*
    public ServletTest() {
        super();
    }
*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//http 페이지에서 전송을 눌러서 데이터를 넘길 때 노출방식
		//이 페이지를 실행시 처음 실행되는 메소드임
		//response.getWriter().append("Served at: ").append(request.getContextPath());//이름출력
		
		//한글 깨진 것 해결
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+"<head>"
				+"<title>서블릿</title>"
				+"</head>"
				+"<body>"
				+"<h1>안녕하세요.</h1>"
				+"<h2>반갑습니다.</h2>"
				+"</body>"
				+"</html>");
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//post방식 감춰서 넘어옴.
		
		doGet(request, response);
	}

}
