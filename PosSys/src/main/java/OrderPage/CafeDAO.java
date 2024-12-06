package OrderPage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import javax.swing.*;

import OrderPage.*;



public class CafeDAO {
	Connection con = null;                  // DB와 연결하는 객체
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;   
	
	CustomerDTO cusDTO = new CustomerDTO();
	
	/* private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "system";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    */
	void connect() {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "1234";
		
		
		try {
			// 1. 접속할 오라클 데이터베이스 드라이버를 메모리에 올리자. - 동적 작업
			Class.forName(driver);
			
			// 2. 오라클 데이터베이스와 연결을 시도.
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  // connect() 메서드 end
	
	// 고객 마일리지 조회
    public CustomerDTO getCus_point(String cus_phone) throws SQLException {
    	connect();
    	String sql = "SELECT cus_id, cus_point FROM customer WHERE cus_phon = ?";
        
        try { pstmt = con.prepareStatement(sql);
        		pstmt.setString(1, cus_phone);
        		
        		// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
        		rs = pstmt.executeQuery();

        		   if (rs.next()) { // 조회된 데이터가 있는 경우
        	            cusDTO.setCus_id(rs.getString("cus_id"));
        	            cusDTO.setCus_point(rs.getInt("cus_point"));
        	            System.out.println("조회된 마일리지: " + cusDTO.getCus_point());
        	        } else { // 조회된 데이터가 없는 경우
        	          /*  cusDTO.setCus_id(null); // 또는 기본값으로 설정
        	            cusDTO.setCus_point(0); // 기본 마일리지*/
        	        	
        	        	cusDTO = new CustomerDTO();
        	            System.out.println("조회된 데이터가 없습니다. 기본값으로 초기화합니다.");
        	        }

        	        // 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
        	        rs.close();
        	        pstmt.close();
        	        con.close();

        	        return cusDTO;
           
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
        return cusDTO; // 고객이 없으면 기본 마일리지는 0
    }

    
    //주문조회해서 id 얻기 
    public Integer selectOrder() throws SQLException {
    	int id= 0;
    	connect();
    	String sql = "SELECT * FROM orders WHERE order_num = (SELECT MAX(order_num) FROM orders)";
        
        try { pstmt = con.prepareStatement(sql);
        		
        		// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
        		rs = pstmt.executeQuery();

        		while(rs.next()) {
        			id = rs.getInt("order_num");
        
        			System.out.println("조회된 주문id : "+ id );
        		}
        		// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
        		rs.close(); pstmt.close(); con.close();
        		
        		return id;
        		
           
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
        return id; // 고객이 없으면 기본 마일리지는 0
    }
    
    
    //상품조회해서 id 얻기 
    public Integer selectProId(String ItemName) throws SQLException {
    	int id= 0;
    	connect();
    	String sql = "SELECT pro_id FROM product WHERE pro_name = ?";
        
        try { 
        		pstmt = con.prepareStatement(sql);
        		pstmt.setString(1, ItemName);
        		// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
        		rs = pstmt.executeQuery();

        		while(rs.next()) {
        			id = rs.getInt("pro_id");
        
        			System.out.println("조회된 상품id : "+ id );
        		}
        		// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
        		rs.close(); pstmt.close(); con.close();
        		
        		return id;
        		
           
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
        return id; // 고객이 없으면 기본 마일리지는 0
    }
    
    
	public CustomerDTO insertCus(String phone) {
		connect();
		
		try {
			// 1. 오라클 데이터베이스에 전송할 SQL문 작성.
			sql = "INSERT INTO customer (cus_phon, cus_point) VALUES (?, 0)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, phone);
			
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res < 0) {
				JOptionPane.showMessageDialog(null, "고객 등록에 문제가 발생하였습니다.");
			}
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close();  con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cusDTO.setCus_point(0);
		cusDTO.setCus_phone(phone);
		
		return cusDTO;
	}
	
	
	public void insertOrder(CustomerDTO cusDTO, int  finalPrice, int cus_pointUsed  ) {
		connect();
		
		try {
			// 1. 오라클 데이터베이스에 전송할 SQL문 작성.
			
			sql = "insert INTO orders (order_num, cus_id, total_price, card_payment, used_point) VALUES (order_seq.NEXTVAL,?,?,'0',?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cusDTO.getCus_id());
			pstmt.setInt(2, finalPrice);
			pstmt.setInt(3, cus_pointUsed);
			System.out.println("insert주문에 빠진 인자 찾자 => 1 : "+ cusDTO.getCus_id()+ " 2: "+finalPrice +" 3 : "+ cus_pointUsed);
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res < 0) {
				JOptionPane.showMessageDialog(null, "주문등록에 문제가 발생하였습니다.(jdbc)");
			}
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close();  con.close();
		 }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void insertOrder2(CustomerDTO cusDTO, int  finalPrice, int cus_pointUsed  ) {
		connect();
		
		try {
			// 1. 오라클 데이터베이스에 전송할 SQL문 작성.
			
			sql = "insert INTO orders (order_num, cus_id, total_price, card_payment, used_point) VALUES (order_seq.NEXTVAL,?,?,'1',?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, cusDTO.getCus_id());
			pstmt.setInt(2, finalPrice);
			pstmt.setInt(3, cus_pointUsed);
			System.out.println("insert주문에 빠진 인자 찾자 => 1 : "+ cusDTO.getCus_id()+ " 2: "+finalPrice +" 3 : "+ cus_pointUsed);
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res < 0) {
				JOptionPane.showMessageDialog(null, "주문등록에 문제가 발생하였습니다.(jdbc)");
			}
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close();  con.close();
		 }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	public void insertDetail(int orderId, int pro_id, int vol  ) {
		connect();
		
		try {
			// 1. 오라클 데이터베이스에 전송할 SQL문 작성.
			
			sql = "INSERT INTO mirotic (detail_id, order_num, pro_id, quantity) VALUES (mirotic_seq.NEXTVAL, ?, ?, ?)";
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, orderId);
			pstmt.setInt(2, pro_id);
			pstmt.setInt(3, vol);
			
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res < 0) {
				JOptionPane.showMessageDialog(null, "주문상세 등록에 문제가 발생하였습니다.(jdbc)");
			}
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close();  con.close();
		 }catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public void updateCus(CustomerDTO cusD, int cus_pointUsed) {
		connect();
		try {
			// 1. 오라클 데이터베이스로 전송할 SQL문을 작성.
			sql = "UPDATE customer SET cus_point = ? WHERE cus_id  = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, (cusD.getCus_point() - cus_pointUsed));
			pstmt.setString(2, cusD.getCus_id());
		
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res < 0) {
				JOptionPane.showMessageDialog(null, "고객 마일리지 차감에 문제가 발생하였습니다.(jdbc)");
			}
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			pstmt.close();  con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}  // update() 메서드 end
	
	
	public void updatePoint(CustomerDTO cusD, int rewardPoints) {
		connect();
		
		try {
			// 1. 오라클 데이터베이스로 전송할 SQL문을 작성.
			sql = "UPDATE customer SET cus_point = ? WHERE cus_id  = ?";
			
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, rewardPoints);
			pstmt.setString(2, cusD.getCus_id());
		
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();
			
			if(res < 0) {
				JOptionPane.showMessageDialog(null, "고객 마일리지 차감에 문제가 발생하였습니다.(jdbc)");
			}
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			pstmt.close();  con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
}
}


    
    