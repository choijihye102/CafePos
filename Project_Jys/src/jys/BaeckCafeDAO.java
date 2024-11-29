package jys;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jys.MiroticDTO;
import jys.OrdersDTO;
import jys.ProductDTO;
import jys.CustomerDTO;

public class BaeckCafeDAO {
	
	private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
	
	private static final String USER = "jys";
	
	private static final String PASSWORD = "pass";
	
	
	static {
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Connection getConnection() throws SQLException {
		
		return DriverManager.getConnection(URL, USER, PASSWORD);
		
	}
	
public List<ProductDTO> getMenuList() throws SQLException {
		
		String sql = "SELECT pro_id, pro_name, pro_price, pro_type FROM product";
		List<ProductDTO> menuList = new ArrayList<>();
		
		try (Connection conn = getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	            	
	                int pro_id = rs.getInt("pro_id"); 
	                String pro_name = rs.getString("pro_name");
	                int pro_price = rs.getInt("pro_price");
	                String pro_type = rs.getString("pro_type");
	                ProductDTO menu = new ProductDTO();
	                menu.setPro_id(pro_id);
	                menu.setPro_name(pro_name);
	                menu.setPro_price(pro_price);
	                menu.setPro_type(pro_type);
	                menuList.add(menu);
	                
	            }
	        }
		return menuList;
		
	}

public boolean selectCustomer(String cus_phone) throws SQLException {
    String sql = "SELECT COUNT(*) FROM customer WHERE cus_phone = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, cus_phone);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1) > 0;
        }
    }
    return false;
}

public int insertCustomer(CustomerDTO customer) throws SQLException {
    String sql = "INSERT INTO customer (cus_phone, cus_point, cus_date) VALUES (?, ?, ?)";
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // pstmt.setInt(1, customer.getCus_id());	// 시퀀스 트리거로 생략 해도 됨.
        pstmt.setString(1, customer.getCus_phone());
        pstmt.setInt(2, customer.getCus_point());
        pstmt.setDate(3, customer.getCus_date());
        return pstmt.executeUpdate();
    }
}

public int insertOrder(OrdersDTO order) throws SQLException {
    String sql = "INSERT INTO orders (order_num, cus_id, total_price, card_payment, order_date) VALUES (order_seq.NEXTVAL, ?, ?, ?, SYSDATE)";
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, order.getCus_id());
        pstmt.setInt(2, order.getTotal_price());
        pstmt.setString(3, order.getCard_payment());
        return pstmt.executeUpdate();
    }
}

public int insertMirotic(MiroticDTO mirotic) throws SQLException {
    String sql = "INSERT INTO mirotic (detail_id, order_num, pro_id, quantity, unit_price) VALUES (detail_id_seq.NEXTVAL, ?, ?, ?, ?)";
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        // pstmt.setInt(1, mirotic.getDetail_id());
        pstmt.setInt(1, mirotic.getOrder_num());
        pstmt.setInt(2, mirotic.getPro_id());
        pstmt.setInt(3, mirotic.getQuantity());
        pstmt.setInt(4, mirotic.getUnit_price());
        return pstmt.executeUpdate();
    }
}









public int getOrderNum(String cus_id) throws SQLException {
    String sql = "SELECT MAX(order_num) FROM orders WHERE cus_id = ?";
    int ordernum = 0;

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, cus_id);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                ordernum = rs.getInt(1);
                
            }
        }
    }

    return ordernum;
}

public int getCus_point(String cus_phone) throws SQLException {
    String sql = "SELECT cus_point FROM customer WHERE cus_phone = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setString(1, cus_phone);
        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("cus_point");
        }
    }
    return 0; // 고객이 없을 경우 기본값 0 반환
}
public int updateCus_point(String cus_phone, int cus_point) throws SQLException {
    String sql = "UPDATE customer SET cus_point = cus_point + ? WHERE cus_phone = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setInt(1, cus_point); // 마일리지 변경 값 (추가/차감)
        pstmt.setString(2, cus_phone);  // 고객 전화번호

        return pstmt.executeUpdate(); // 업데이트된 행 수 반환
    }
}

public String getCustomerId(String cus_phone) throws SQLException {
    String sql = "SELECT cus_id FROM customer WHERE cus_phone = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, cus_phone);
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("cus_id");
            }
        }
    }
    return null;
}






	

}







    // 주문 항목에서 수량 가져오기
    

    




	
