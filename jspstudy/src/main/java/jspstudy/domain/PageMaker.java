package jspstudy.domain;

public class PageMaker {//�ϴ��� ������ �׺���̼� ��..

	private int totalCount;		//��ü ������ ����
	private int startPage;		//ù��° ��ȣ
	private int endPage;		//������ ��ȣ
	private boolean prev;		//���� ��ư
	private boolean next;		//������ư
	private int displayPageNum = 10;	//�׺���̼ǿ� ������ ����
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
	
	
	
	//���������� �������� ���� ���� ��ư ���� �����ϴ� �޼ҵ�
	public void calcData()
	{
		//ceil:�ø�ó��
		endPage = (int)(Math.ceil(scri.getPage()/(double)displayPageNum)*displayPageNum) ;
		
		startPage = (endPage-displayPageNum)+1;
		
		int tempEndPage = (int)Math.ceil(totalCount/(double)scri.getPerPageNum()); //perPageNum 15�� ����. ���� ������ ����
	
		if(endPage > tempEndPage)
		{
			endPage = tempEndPage;	//11~20�� ��������� ������ 17���� �ۿ� ���ٸ� 11~17�� ���������.
		}
		
		prev = startPage == 1 ? false : true;//ù��° �������� �� ������ �� �� ����.
		next = endPage*scri.getPerPageNum() >= totalCount ? false : true;//�� �̻��� ���̻� ��Ÿ�� �����Ͱ� �����ϱ� ��ư�� ��Ÿ���� �ʰ�..
		
		
		
		
	}
	
}
