package Admin_management;

public class PageVO {
	public int getStartPge() {
		return startPge;
	}

	public void setStartPge(int startPge) {
		this.startPge = startPge;
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

	public Criteria getCri() {
		return cri;
	}

	public void setCri(Criteria cri) {
		this.cri = cri;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	private int startPge;
	private int endPage;
	private boolean prev, next;
	
	private Criteria cri;
	private int total;

	//생성자로 전체 페이지 수와, criteria객체에 페이지당 표시되는 데이터 수, 현재 페이지 번호를 인자로 받음 
	public PageVO(Criteria cri,int total ) {
		this.cri = cri;
		this.total = total;
		
		endPage = (int)Math.ceil(cri.getPageNum()/5.0)*5;
		startPge = endPage-4;
		
		int realPage = (int)Math.ceil((total*1.0)/cri.getAmount());
		if(realPage < endPage) {
			this.endPage =  realPage;
		}
		
		this.prev = this.startPge >1;
		this.next = this.endPage < realPage;
		System.out.println("endPage"+endPage);
	}
	
/*	public static void main(String[] args) {
		PageVO p = new PageVO(new Criteria(50,10),120);
	}*/
	
	

}
