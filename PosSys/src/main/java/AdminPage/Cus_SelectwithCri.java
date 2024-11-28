package AdminPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Cus_SelectwithCri {
	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

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


	Connection con = null;
	 int pageNum;
	 int amount;
	
	
	Cus_SelectwithCri(Criteria cri){
		this.pageNum = cri.getPageNum();
		this.amount = cri.getAmount();
		connect();
	}
	

	
	ArrayList<Object[]> selectCustomer() {
		ArrayList<Object[]> list =  new ArrayList<>();
		
		PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
		ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
		
		String sql =
	    "select rownum rn, cus_phon, cus_point, created_date from (select /*Index_desc(customer pk_customer)*/rownum rn, cus_phon, cus_point, created_date from customer where rownum <=  ? ) where rn > ?"; 
		
		
		
		
		try {
			// 오라클 데이터베이스에 전송할 sql문 작성
			int startRow =  getPageNum() * getAmount();// 시작 행 번호
            int endRow =   (getPageNum() - 1) * getAmount();      // 끝 행 번호
            
            pstmt = con.prepareStatement(sql);
            		
            pstmt.setInt(1, startRow);
            pstmt.setInt(2, endRow);
			
         
            
			// 오라클 데이터베이스에 sql문 전송 및 sql문 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int rn = rs.getInt("rn");
				int cus_phon  = rs.getInt("cus_phon");
				int cus_point  = rs.getInt("cus_point");
				
				System.out.println(rn+" "+ cus_phon+" " +cus_point);
				  Object[]  data = {rn, cus_phon, cus_point};
				
				
				System.out.println("data"+ data[2]);
			
				list.add(data);
				
				
			} // select() 메서드 끝
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	ArrayList<Object[]> selectCustomerWithSerch(int number) throws Exception  {
		ArrayList<Object[]> list =  new ArrayList<>();
		
		PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
		ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
		
		
	
		String sql =
	    "select rownum rn, cus_phon, cus_point, created_date from customer where cus_phon = ?"; 
		
		
		
		
		try {
			// 오라클 데이터베이스에 전송할 sql문 작성
			
            
            pstmt = con.prepareStatement(sql);
            		
            pstmt.setInt(1, number);
         
			
         
            
			// 오라클 데이터베이스에 sql문 전송 및 sql문 실행
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				int rn = rs.getInt("rn");
				int cus_phon  = rs.getInt("cus_phon");
				int cus_point  = rs.getInt("cus_point");
				
				System.out.println(rn+" "+ cus_phon+" " +cus_point);
				  Object[]  data = {rn, cus_phon, cus_point};
				
				
				System.out.println("data"+ data[2]);
			
				list.add(data);
				
				
			} // select() 메서드 끝
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			
		}catch(SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	void connect() {
		String driver ="oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "1234";
		
		
		System.out.println("연결됐어요~1");
		try {
			//접속할 오라클 데이터베이스 드라이버를 메모리에 올리자. - 동적 작업
			System.out.println("연결됐어요~2");
			Class.forName(driver);
			
			//오라클 데이터베이스와 연결 시도
			con = DriverManager.getConnection(url, user, password);
			System.out.println("연결됐어요~");
			
		}catch(Exception e) {
			e.getMessage();
		}
	} // connect() 메서드 끝.
	
	
	
	
}
