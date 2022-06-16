package jspstudy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import jspstudy.domain.BoardVo;
import jspstudy.domain.Criteria;
import jspstudy.domain.PageMaker;
import jspstudy.domain.SearchCriteria;
import jspstudy.service.BoardDao;

@WebServlet("/BoardController")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		//�����η� �� request�� ������ ó��

		//�ѱ۱��� ����
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//
		//�������� �̸� �����ϱ�
		String uri = request.getRequestURI();
		String pj = request.getContextPath();//������Ʈ�� ���
		String command = uri.substring(pj.length());// ������Ʈ�� ���� ��θ� �տ��� ������Ʈ������� �߶��ֱ� 

		int sizeLimit = 1024*1024*15;
		String uploadPath="D:\\openapi(B)\\dev\\jspstudy\\src\\main\\webapp\\";
		String saveFolder="images";
		String saveFullPath = uploadPath+saveFolder;
		
		if(command.equals("/board/boardWrite.do"))
		{
			System.out.println("�۾��� ȭ�鿡 ������");
			
			//������������ �̵�(������)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response);
			//-->�����ο� ���� �Ǹ� ���� ��η� ���Բ� �������
			
		}
		else if (command.equals("/board/boardWriteAction.do"))
		{
			System.out.println("�۾��� ó�� ȭ������ ������");
			
			//����̹��� ����ؼ� �Ѱ��ִ� ���� ���� ���� (�̹������·� �ޱ�...)
			//����̹� ���� Ŭ���� ã��
			MultipartRequest multipartRequest = null;
			multipartRequest = new MultipartRequest(request,saveFullPath,sizeLimit,"UTF-8",new DefaultFileRenamePolicy());//���ɽ�Ʈ ���׷��̵� �ϱ� 
			//���� ������ saveFullPath�� �����.
			
			
			
			//��ü�ޱ�
			/*
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			*/
			
			//���� ������Ʈ�� ���� ����
			String subject = multipartRequest.getParameter("subject");
			String content = multipartRequest.getParameter("content");
			String writer = multipartRequest.getParameter("writer");
			
			System.out.println(subject+" ; "+content+" ; "+writer);
			
			//�������� ����� ������ ��� ��ü����
			Enumeration files = multipartRequest.getFileNames();
			//��� ��ü�� ���� �̸��� ��´�.
			String file = (String)files.nextElement();
			//�Ѿ���� ��ü�߿� �ش�Ǵ� �����̸����� �Ǿ��ִ� �����̸��� �����Ѵ�.(����Ǵ� �����̸�)
			String fileName = multipartRequest.getFilesystemName(file);
			//������ �����̸�
			String originFileName = multipartRequest.getOriginalFileName(file);
			
			
			
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			//int midx = 2;//�ϴ� �ӽ÷� �ϵ��ڵ�
			//������ �α����� ������ �ϴ� �������� 
			
			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			BoardDao bd = new BoardDao();
			int value = bd.insertBoard(subject, content, writer, ip, midx, fileName);
			
			
			if(value == 1)//insert �� ���� ��
			{
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");//Ŭ���̾�Ʈ�� ���ϴ� �� �� �߳� ���� ��� ��!
				
			}
			else if (value == 0)//insert �� �� ��.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardWrite.do");//��¶�� Ŭ���̾�Ʈ�� ���ϴ� �� �߳� ���� ��� ��!

				
			}
			
			
			
			
		}
		else if (command.equals("/board/boardList.do"))
		{
			System.out.println("�Խ��� ����Ʈ ȭ�鿡 ������");

			String page = request.getParameter("page");
			if(page == null) page = "1";//�Ķ���ͷ� �ѱ� �� ������ �׳� 1�� ó��..
			int pagex = Integer.parseInt(page);
			
			
			String keyword = request.getParameter("keyword");
			if (keyword == null) keyword="";
			
			String searchType = request.getParameter("searchType");
			if (searchType == null) searchType ="subject";
			

			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			
			
			
			
			//ó��
			BoardDao bd = new BoardDao();
			int cnt = bd.boardTotal(scri);
			
			//Criteria cri = new Criteria();
			//cri.setPage(pagex);
			

			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			
			
			
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);//alist��ȯ
			
			
			request.setAttribute("alist", alist);//alist��� �����͸� ȭ����� �������� �ϱ⶧����
			request.setAttribute("pm", pm);
			
			//blist���� ������ ��Ƽ� ������
			
			
			
			
			//������������ �̵�(������)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
			
			
			
		}
		else if (command.equals("/board/boardContent.do"))
		{
			System.out.println("���뺸�� ȭ�鿡 ������");

			
			//1.�Ѿ�� ���� �޴´�.
			String bidx = request.getParameter("bidx");//�Ķ���ͷ� �޴� ���� ��� �� String����
			int bidx_ = Integer.parseInt(bidx);//int���̷� �ٲ���
			System.out.println(bidx);
			//2.ó���Ѵ�.
			BoardDao bd = new BoardDao();
			//bd.boardSelectOn(bidx);//�Ű������� int���� �ϴµ� ���� bidx�� String��.
			BoardVo bv = bd.boardSelectOn(bidx_);
			//bv�� ��� ���� ȭ�鿡 ����������.
			request.setAttribute("bv", bv);//	���ο� �ִ� ���� ��ġ���� �ڿ��� �����Ѵ�.
			
			
			
			//3.�̵��Ѵ�.
			//������������ �̵�(������)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
			
		}
		
		else if (command.equals("/board/boardModify.do"))
		{
			System.out.println("�����ϱ� ȭ�鿡 ������");

			
			//1.�Ѿ�� ���� �޴´�.
			String bidx = request.getParameter("bidx");//�Ķ���ͷ� �޴� ���� ��� �� String����
			int bidx_ = Integer.parseInt(bidx);//int���̷� �ٲ���
			System.out.println(bidx);

			//2.ó���Ѵ�.
			BoardDao bd = new BoardDao();
			//bd.boardSelectOn(bidx);//�Ű������� int���� �ϴµ� ���� bidx�� String��.
			BoardVo bv = bd.boardSelectOn(bidx_);
			//bv�� ��� ���� ȭ�鿡 ����������.
			request.setAttribute("bv", bv);//	���ο� �ִ� ���� ��ġ���� �ڿ��� �����Ѵ�.
			
			
			
			//3.�̵��Ѵ�.
			//������������ �̵�(������)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
			
		}
		
		else if (command.equals("/board/boardModifyAction.do"))//�׼�ó�� ���Ĵ� sendredirect ������� ��������!
		{
			System.out.println("�ۼ��� ó�� ȭ������ ������");

			//��ü�ޱ�
			String bidx = request.getParameter("bidx");//����Ÿ������ ���� bidx�� �ޱ�! �Ķ���ͷ� �޴� ���� ��� �� String����
			int bidx_ = Integer.parseInt(bidx);//int���̷� �ٲ���
			System.out.println(bidx);
			String subject = request.getParameter("subject");//��ǲŸ������ ���� �͵�... name�̸���...
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			System.out.println(subject+" ; "+content+" ; "+writer+" ; "+bidx);

			String ip = InetAddress.getLocalHost().getHostAddress();
			//int midx=2;

			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			BoardDao bd = new BoardDao();
			int value = bd.updateBoard(subject, content, writer, ip, midx, bidx_);
			
			
			if(value == 1)//insert �� ���� ��
			{
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx_);//Ŭ���̾�Ʈ�� ���ϴ� �� �� �߳� ���� ��� ��!
				
			}
			else if (value == 0)//insert �� �� ��.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardModify.do?bidx="+bidx);//��¶�� Ŭ���̾�Ʈ�� ���ϴ� �� �߳� ���� ��� ��!

				
			}
			
			
			
			
		}
		else if(command.equals("/board/boardDelete.do"))
		{
			System.out.println("�����ϱ� ȭ�鿡 ������");

			//1.�Ѿ�� ���� �޴´�.
			String bidx = request.getParameter("bidx");//�Ķ���ͷ� �޴� ���� ��� �� String����
			int bidx_ = Integer.parseInt(bidx);//int���̷� �ٲ���
			System.out.println(bidx);

			//2.ó���Ѵ�.
			BoardDao bd = new BoardDao();
			//bd.boardSelectOn(bidx);//�Ű������� int���� �ϴµ� ���� bidx�� String��.
			BoardVo bv = bd.boardSelectOn(bidx_);
			//bv�� ��� ���� ȭ�鿡 ����������.
			request.setAttribute("bv", bv);//	���ο� �ִ� ���� ��ġ���� �ڿ��� �����Ѵ�.
			
			
			
			//3.�̵��Ѵ�.
			//������������ �̵�(������)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
		}
		else if(command.equals("/board/boardDeleteAction.do"))
		{
			System.out.println("�ۻ��� ó�� ȭ������ ������");

			//��ü�ޱ�
			String bidx = request.getParameter("bidx");//����Ÿ������ ���� bidx�� �ޱ�! �Ķ���ͷ� �޴� ���� ��� �� String����
			int bidx_ = Integer.parseInt(bidx);//int���̷� �ٲ���
			System.out.println(bidx_);
			
			
			BoardDao bd = new BoardDao();
			int value = bd.deleteBoard(bidx_);//delyn�� Y�� �ٲٱ�
			
			if(value == 1)//insert �� ���� ��
			{
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");//Ŭ���̾�Ʈ�� ���ϴ� �� �� �߳� ���� ��� ��!
				
			}
			else if (value == 0)//insert �� �� ��.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx_);//��¶�� Ŭ���̾�Ʈ�� ���ϴ� �� �߳� ���� ��� ��!

				
			}
			
			
			
			
		}
		else if(command.equals("/board/boardReply.do"))
		{
			System.out.println("�亯�ϱ� ȭ������ ������");
			
			//�Ķ���ͷ� �ѱ��(�޴�) ���� �� ���ڿ���
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");

			
			
			
			BoardVo bv = new BoardVo();
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setDepth(Integer.parseInt(depth));
			bv.setLevel_(Integer.parseInt(level_));

			request.setAttribute("bv", bv);//1�� �̸����� 2�� ��Ƽ� ȭ�鿡 �Ѱ��� 
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp");
			rd.forward(request, response);
						
			
			
			
			
			
			
			
		}
		else if(command.equals("/board/boardReplyAction.do"))
		{
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");
			String subject = request.getParameter("subject");//��ǲŸ������ ���� �͵�... name�̸���...
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			String ip = InetAddress.getLocalHost().getHostAddress();
			//int midx=2;

			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			
			BoardVo bv = new BoardVo();
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setDepth(Integer.parseInt(depth));
			bv.setLevel_(Integer.parseInt(level_));
			bv.setSubject(subject);
			bv.setContent(content);
			bv.setWriter(writer);
			bv.setIp(ip);
			bv.setMidx(midx);
			
			BoardDao bd = new BoardDao();
			int value = bd.replyBoard(bv);

			if(value == 1)//insert �� ���� ��
			{
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");//Ŭ���̾�Ʈ�� ���ϴ� �� �� �߳� ���� ��� ��!
				
			}
			else if (value == 0)//insert �� �� ��.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);//��¶�� Ŭ���̾�Ʈ�� ���ϴ� �� �߳� ���� ��� ��!

				
			}
			
			
			
			
			
		}
		else if(command.equals("/board/fileDownload.do"))
		{
			String filename = request.getParameter("filename");
			//������ ��ü���
			String filePath = saveFullPath + File.separator + filename;
			Path source = Paths.get(filePath);
			
			String mimeType = Files.probeContentType(source);
			//���������� ��������� ��´�
			response.setContentType(mimeType);
			
			String EncodingFileName = new String(filename.getBytes("UTF-8"));
			//÷���ؼ� �ٿ�ε�Ǵ� ������ ��������� ��´�.
			response.setHeader("Content-Disposition", "attachment;fileName="+EncodingFileName);
			
			//�ش���ġ�� �ִ� ������ �о�帰��.
			FileInputStream fileInputStream = new FileInputStream(filePath);
			//���Ͼ������� ��Ʈ��
			ServletOutputStream servletOutStream = response.getOutputStream();
			
			byte[] b = new byte[4096];
			
			int read = 0;
			
			while( (read = fileInputStream.read(b, 0, b.length)) != -1)
			{
				//���Ͼ���
				servletOutStream.write(b, 0, read);
			}
			
			//���
			servletOutStream.flush();
			servletOutStream.close();
			fileInputStream.close();
			
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		doGet(request, response);
	}

}
