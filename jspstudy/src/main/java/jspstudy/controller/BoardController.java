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

		
		//가상경로로 온 request가 있으면 처리

		//한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		//
		//가상경로의 이름 추출하기
		String uri = request.getRequestURI();
		String pj = request.getContextPath();//프로젝트명 출력
		String command = uri.substring(pj.length());// 리퀘스트한 곳의 경로를 앞에서 프로젝트명까지를 잘라주기 

		int sizeLimit = 1024*1024*15;
		String uploadPath="D:\\openapi(B)\\dev\\jspstudy\\src\\main\\webapp\\";
		String saveFolder="images";
		String saveFullPath = uploadPath+saveFolder;
		
		if(command.equals("/board/boardWrite.do"))
		{
			System.out.println("글쓰기 화면에 들어왔음");
			
			//실제페이지로 이동(포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardWrite.jsp");
			rd.forward(request, response);
			//-->가상경로에 들어가게 되면 실제 경로로 들어가게끔 만들어줌
			
		}
		else if (command.equals("/board/boardWriteAction.do"))
		{
			System.out.println("글쓰기 처리 화면으로 들어왔음");
			
			//드라이버를 사용해서 넘겨주는 것을 받을 꺼임 (이미지형태로 받기...)
			//드라이버 안의 클래스 찾기
			MultipartRequest multipartRequest = null;
			multipartRequest = new MultipartRequest(request,saveFullPath,sizeLimit,"UTF-8",new DefaultFileRenamePolicy());//리케스트 업그레이드 하기 
			//실제 파일은 saveFullPath에 저장됨.
			
			
			
			//객체받기
			/*
			String subject = request.getParameter("subject");
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			*/
			
			//이제 리퀘스트로 받지 않음
			String subject = multipartRequest.getParameter("subject");
			String content = multipartRequest.getParameter("content");
			String writer = multipartRequest.getParameter("writer");
			
			System.out.println(subject+" ; "+content+" ; "+writer);
			
			//열거자인 저장될 파일을 담는 객체생성
			Enumeration files = multipartRequest.getFileNames();
			//담긴 객체의 파일 이름을 얻는다.
			String file = (String)files.nextElement();
			//넘어오는 객체중에 해당되는 파일이름으로 되어있는 파일이름을 추출한다.(저장되는 파일이름)
			String fileName = multipartRequest.getFilesystemName(file);
			//원본의 파일이름
			String originFileName = multipartRequest.getOriginalFileName(file);
			
			
			
			String ip = InetAddress.getLocalHost().getHostAddress();
			
			//int midx = 2;//일단 임시로 하드코딩
			//원래는 로그인한 정보로 하는 것이지만 
			
			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			BoardDao bd = new BoardDao();
			int value = bd.insertBoard(subject, content, writer, ip, midx, fileName);
			
			
			if(value == 1)//insert 잘 됐을 때
			{
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");//클라이언트가 원하는 거 다 했네 이제 통신 끝!
				
			}
			else if (value == 0)//insert 잘 안 됨.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardWrite.do");//어쨋든 클라이언트가 원하는 건 했네 이제 통신 끝!

				
			}
			
			
			
			
		}
		else if (command.equals("/board/boardList.do"))
		{
			System.out.println("게시판 리스트 화면에 들어왔음");

			String page = request.getParameter("page");
			if(page == null) page = "1";//파라미터로 넘긴 게 없으면 그냥 1로 처리..
			int pagex = Integer.parseInt(page);
			
			
			String keyword = request.getParameter("keyword");
			if (keyword == null) keyword="";
			
			String searchType = request.getParameter("searchType");
			if (searchType == null) searchType ="subject";
			

			SearchCriteria scri = new SearchCriteria();
			scri.setPage(pagex);
			scri.setSearchType(searchType);
			scri.setKeyword(keyword);
			
			
			
			
			//처리
			BoardDao bd = new BoardDao();
			int cnt = bd.boardTotal(scri);
			
			//Criteria cri = new Criteria();
			//cri.setPage(pagex);
			

			
			PageMaker pm = new PageMaker();
			pm.setScri(scri);
			pm.setTotalCount(cnt);
			
			
			
			
			ArrayList<BoardVo> alist = bd.boardSelectAll(scri);//alist반환
			
			
			request.setAttribute("alist", alist);//alist라는 데이터를 화면까지 가져가야 하기때문에
			request.setAttribute("pm", pm);
			
			//blist에한 정보를 담아서 포워드
			
			
			
			
			//실제페이지로 이동(포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardList.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
			
			
			
		}
		else if (command.equals("/board/boardContent.do"))
		{
			System.out.println("내용보기 화면에 들어왔음");

			
			//1.넘어온 값을 받는다.
			String bidx = request.getParameter("bidx");//파라미터로 받는 형은 모두 다 String형임
			int bidx_ = Integer.parseInt(bidx);//int형이로 바꿔줌
			System.out.println(bidx);
			//2.처리한다.
			BoardDao bd = new BoardDao();
			//bd.boardSelectOn(bidx);//매개변수는 int여야 하는데 현재 bidx는 String임.
			BoardVo bv = bd.boardSelectOn(bidx_);
			//bv에 담긴 값을 화면에 가져가야함.
			request.setAttribute("bv", bv);//	내부에 있는 같은 위치에서 자원을 공유한다.
			
			
			
			//3.이동한다.
			//실제페이지로 이동(포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardContent.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
			
		}
		
		else if (command.equals("/board/boardModify.do"))
		{
			System.out.println("수정하기 화면에 들어왔음");

			
			//1.넘어온 값을 받는다.
			String bidx = request.getParameter("bidx");//파라미터로 받는 형은 모두 다 String형임
			int bidx_ = Integer.parseInt(bidx);//int형이로 바꿔줌
			System.out.println(bidx);

			//2.처리한다.
			BoardDao bd = new BoardDao();
			//bd.boardSelectOn(bidx);//매개변수는 int여야 하는데 현재 bidx는 String임.
			BoardVo bv = bd.boardSelectOn(bidx_);
			//bv에 담긴 값을 화면에 가져가야함.
			request.setAttribute("bv", bv);//	내부에 있는 같은 위치에서 자원을 공유한다.
			
			
			
			//3.이동한다.
			//실제페이지로 이동(포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardModify.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
			
		}
		
		else if (command.equals("/board/boardModifyAction.do"))//액션처리 이후는 sendredirect 방식으로 보내야함!
		{
			System.out.println("글수정 처리 화면으로 들어왔음");

			//객체받기
			String bidx = request.getParameter("bidx");//히든타입으로 받은 bidx값 받기! 파라미터로 받는 형은 모두 다 String형임
			int bidx_ = Integer.parseInt(bidx);//int형이로 바꿔줌
			System.out.println(bidx);
			String subject = request.getParameter("subject");//인풋타입으로 받은 것들... name이름임...
			String content = request.getParameter("content");
			String writer = request.getParameter("writer");
			System.out.println(subject+" ; "+content+" ; "+writer+" ; "+bidx);

			String ip = InetAddress.getLocalHost().getHostAddress();
			//int midx=2;

			HttpSession session = request.getSession();
			int midx = (int)session.getAttribute("midx");
			
			BoardDao bd = new BoardDao();
			int value = bd.updateBoard(subject, content, writer, ip, midx, bidx_);
			
			
			if(value == 1)//insert 잘 됐을 때
			{
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx_);//클라이언트가 원하는 거 다 했네 이제 통신 끝!
				
			}
			else if (value == 0)//insert 잘 안 됨.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardModify.do?bidx="+bidx);//어쨋든 클라이언트가 원하는 건 했네 이제 통신 끝!

				
			}
			
			
			
			
		}
		else if(command.equals("/board/boardDelete.do"))
		{
			System.out.println("삭제하기 화면에 들어왔음");

			//1.넘어온 값을 받는다.
			String bidx = request.getParameter("bidx");//파라미터로 받는 형은 모두 다 String형임
			int bidx_ = Integer.parseInt(bidx);//int형이로 바꿔줌
			System.out.println(bidx);

			//2.처리한다.
			BoardDao bd = new BoardDao();
			//bd.boardSelectOn(bidx);//매개변수는 int여야 하는데 현재 bidx는 String임.
			BoardVo bv = bd.boardSelectOn(bidx_);
			//bv에 담긴 값을 화면에 가져가야함.
			request.setAttribute("bv", bv);//	내부에 있는 같은 위치에서 자원을 공유한다.
			
			
			
			//3.이동한다.
			//실제페이지로 이동(포워드)
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardDelete.jsp");
			rd.forward(request, response);
			
			
			
			
			
			
		}
		else if(command.equals("/board/boardDeleteAction.do"))
		{
			System.out.println("글삭제 처리 화면으로 들어왔음");

			//객체받기
			String bidx = request.getParameter("bidx");//히든타입으로 받은 bidx값 받기! 파라미터로 받는 형은 모두 다 String형임
			int bidx_ = Integer.parseInt(bidx);//int형이로 바꿔줌
			System.out.println(bidx_);
			
			
			BoardDao bd = new BoardDao();
			int value = bd.deleteBoard(bidx_);//delyn을 Y로 바꾸기
			
			if(value == 1)//insert 잘 됐을 때
			{
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");//클라이언트가 원하는 거 다 했네 이제 통신 끝!
				
			}
			else if (value == 0)//insert 잘 안 됨.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx_);//어쨋든 클라이언트가 원하는 건 했네 이제 통신 끝!

				
			}
			
			
			
			
		}
		else if(command.equals("/board/boardReply.do"))
		{
			System.out.println("답변하기 화면으로 들어왔음");
			
			//파라미터로 넘기는(받는) 형은 다 문자열임
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");

			
			
			
			BoardVo bv = new BoardVo();
			bv.setBidx(Integer.parseInt(bidx));
			bv.setOriginbidx(Integer.parseInt(originbidx));
			bv.setDepth(Integer.parseInt(depth));
			bv.setLevel_(Integer.parseInt(level_));

			request.setAttribute("bv", bv);//1의 이름으로 2를 담아서 화면에 넘겨줌 
			
			
			RequestDispatcher rd = request.getRequestDispatcher("/board/boardReply.jsp");
			rd.forward(request, response);
						
			
			
			
			
			
			
			
		}
		else if(command.equals("/board/boardReplyAction.do"))
		{
			
			String bidx = request.getParameter("bidx");
			String originbidx = request.getParameter("originbidx");
			String depth = request.getParameter("depth");
			String level_ = request.getParameter("level_");
			String subject = request.getParameter("subject");//인풋타입으로 받은 것들... name이름임...
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

			if(value == 1)//insert 잘 됐을 때
			{
				response.sendRedirect(request.getContextPath()+"/board/boardList.do");//클라이언트가 원하는 거 다 했네 이제 통신 끝!
				
			}
			else if (value == 0)//insert 잘 안 됨.
			{
				response.sendRedirect(request.getContextPath()+"/board/boardContent.do?bidx="+bidx);//어쨋든 클라이언트가 원하는 건 했네 이제 통신 끝!

				
			}
			
			
			
			
			
		}
		else if(command.equals("/board/fileDownload.do"))
		{
			String filename = request.getParameter("filename");
			//파일의 전체경로
			String filePath = saveFullPath + File.separator + filename;
			Path source = Paths.get(filePath);
			
			String mimeType = Files.probeContentType(source);
			//파일형식을 헤더정보에 담는다
			response.setContentType(mimeType);
			
			String EncodingFileName = new String(filename.getBytes("UTF-8"));
			//첨부해서 다운로드되는 파일을 헤더정보에 담는다.
			response.setHeader("Content-Disposition", "attachment;fileName="+EncodingFileName);
			
			//해당위치에 있는 파일을 읽어드린다.
			FileInputStream fileInputStream = new FileInputStream(filePath);
			//파일쓰기위한 스트림
			ServletOutputStream servletOutStream = response.getOutputStream();
			
			byte[] b = new byte[4096];
			
			int read = 0;
			
			while( (read = fileInputStream.read(b, 0, b.length)) != -1)
			{
				//파일쓰기
				servletOutStream.write(b, 0, read);
			}
			
			//출력
			servletOutStream.flush();
			servletOutStream.close();
			fileInputStream.close();
			
			
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		
		doGet(request, response);
	}

}
