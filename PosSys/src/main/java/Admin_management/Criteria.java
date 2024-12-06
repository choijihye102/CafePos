package Admin_management;

public class Criteria {
	//페이징처리 관련 필드 
	private int pageNum;
	private int amount;
	
	
	//기본값을 1페이지에 10개로 지정 처리 
	public Criteria() {
		this(1,20);
	}
	
	public Criteria(int pageNum, int amount) {  
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	//속성별 게터, 세터 메소드 설정 
	public int getPageNum() {
		return pageNum;
	}


	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}


	public int getAmount() {
		return amount;
	}


	public void setAmount(int amount) {
		this.amount = amount;
	}


	
}
