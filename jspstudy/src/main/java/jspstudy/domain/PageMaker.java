package jspstudy.domain;

public class PageMaker {//하단의 페이지 네비게이션 바..

	private int totalCount;		//전체 데이터 갯수
	private int startPage;		//첫번째 번호
	private int endPage;		//마지막 번호
	private boolean prev;		//이전 버튼
	private boolean next;		//다음버튼
	private int displayPageNum = 10;	//네비게이션에 보여줄 개수
	private SearchCriteria scri;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		calcData();
		
	}
	public int getStartPage() {
		return startPage;
	}
	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public void setPrev(boolean prev) {
		this.prev = prev;
	}
	public boolean isNext() {
		return next;
	}
	public void setNext(boolean next) {
		this.next = next;
	}
	public int getDisplayPageNum() {
		return displayPageNum;
	}
	public void setDisplayPageNum(int displayPageNum) {
		this.displayPageNum = displayPageNum;
	}
	public SearchCriteria getScri() {
		return scri;
	}
	public void setScri(SearchCriteria scri) {
		this.scri = scri;
	}
	
	
	
	//시작페이지 끝페이지 이전 다음 버튼 값을 생성하는 메소드
	public void calcData()
	{
		//ceil:올림처리
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum) ;
		
		startPage = (endPage-displayPageNum)+1;
		
		int tempEndPage = (int)Math.ceil(totalCount/(double)scri.getPerPageNum()); //perPageNum 15로 나눔. 실제 페이지 개수
	
		if(endPage > tempEndPage)
		{
			endPage = tempEndPage;	//11~20이 계산이지만 실제론 17까지 밖에 없다면 11~17을 보여줘야함.
		}
		
		prev = startPage == 1 ? false : true;//첫번째 페이지면 더 앞으로 갈 수 없다.
		next = endPage*scri.getPerPageNum() >= totalCount ? false : true;//그 이상은 더이상 나타낼 데이터가 없으니까 버튼이 나타나지 않게..
		
		
		
		
	}
	
}
