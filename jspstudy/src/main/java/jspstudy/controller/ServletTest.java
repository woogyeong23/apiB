package jspstudy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletTest")
public class ServletTest extends HttpServlet {//http����� �ϱ� ���� �ڹٿ��� �����ϴ� Ŭ������. ����� �ް� �Ǹ� ���� �����ϱ� ���� Ŭ������ ��.(���������� ��)
	//������ �ڹٷ� ���� ����������.
	//������ �ڹ��ڵ忡�� html�ڵ���
	//jsp�� html�ڵ忡�� �ڹ��ڵ�(<%   out.println... )���
	
	private static final long serialVersionUID = 1L;
/*
    public ServletTest() {
        super();
    }
*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//http ���������� ������ ������ �����͸� �ѱ� �� ������
		//�� �������� ����� ó�� ����Ǵ� �޼ҵ���
		//response.getWriter().append("Served at: ").append(request.getContextPath());//�̸����
		
		//�ѱ� ���� �� �ذ�
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html>"
				+"<head>"
				+"<title>����</title>"
				+"</head>"
				+"<body>"
				+"<h1>�ȳ��ϼ���.</h1>"
				+"<h2>�ݰ����ϴ�.</h2>"
				+"</body>"
				+"</html>");
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//post��� ���缭 �Ѿ��.
		
		doGet(request, response);
	}

}
