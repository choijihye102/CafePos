package jys2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jys.ProductDTO;
import jys.CustomerDTO;
import jys.OrdersDTO;
import jys.MiroticDTO;


public class CafeDAO {

    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String USER = "jys";
    private static final String PASSWORD = "pass";

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
    
    
    
    
    
    
    
    
    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T t) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    // 재사용 가능한 executeQuery 메서드
    private <T> T executeQuery(String sql, SQLConsumer<PreparedStatement> paramSetter, SQLFunction<ResultSet, T> resultHandler) throws SQLException {
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set parameters
            paramSetter.accept(pstmt);

            try (ResultSet rs = pstmt.executeQuery()) {
                // Process the result
                return resultHandler.apply(rs);
            }
        }
    }
    
    
    
    public int getCustomerId(String cus_phone) throws SQLException {
        String sql = "SELECT cus_id FROM customer WHERE cus_phone = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cus_phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("cus_id");
                }
            }
        }
        return 0; // 고객 ID가 없으면 0 반환
    }

    
    
    
    
    

    // 메뉴 리스트 가져오기
    public List<ProductDTO> getMenuList() throws SQLException {
        String sql = "SELECT pro_id, pro_name, pro_price, pro_type FROM product";
        List<ProductDTO> menuList = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ProductDTO product = new ProductDTO();
                product.setPro_id(rs.getInt("pro_id"));
                product.setPro_name(rs.getString("pro_name"));
                product.setPro_price(rs.getInt("pro_price"));
                product.setPro_type(rs.getString("pro_type"));
                menuList.add(product);
            }
        }
        return menuList;
    }
    
    
    

    // 고객 존재 여부 확인
    public boolean selectCustomer(String cus_phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE cus_phone = ?";
        
        return executeQuery(sql, pstmt -> pstmt.setString(1, cus_phone),
                rs -> rs.next() && rs.getInt(1) > 0
        );
        
        
    }
        
    // 1130 수정.
    public Integer getCustomer(String cus_phone) throws SQLException {
        String sql = "SELECT cus_id FROM customer WHERE cus_phone = ?";
        
        
        // 
        return executeQuery(sql, pstmt -> pstmt.setString(1, cus_phone),
                rs -> rs.next() ? rs.getInt("cus_id") : null
        );
    }
        
    // 새로운 고객 등록
    public void insertCustomer(CustomerDTO customer) throws SQLException {
        String sql = "INSERT INTO customer (cus_phone, cus_point, cus_date) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getCus_phone());
            pstmt.setInt(2, customer.getCus_point());
            pstmt.setDate(3, customer.getCus_date());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("고객 생성중 오류 발생", e);
        }
    }

    
    public boolean checkCustomerExists(String cus_phone) throws SQLException {
        String sql = "SELECT COUNT(*) FROM customer WHERE cus_phone = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cus_phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
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
        String sql = "INSERT INTO mirotic (detail_id, order_num, pro_id, quantity, cus_phone, used_point) VALUES (detail_id_seq.NEXTVAL, ?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // pstmt.setInt(1, mirotic.getDetail_id());
            pstmt.setInt(1, mirotic.getOrder_num());
            pstmt.setInt(2, mirotic.getPro_id());
            pstmt.setInt(3, mirotic.getQuantity());
            pstmt.setString(4, mirotic.getCus_phone());
            pstmt.setInt(5, mirotic.getUsed_point());
            
            
            return pstmt.executeUpdate();
        }
    }
    
    
    // 주문 불러오기.
    public int getOrderNum(int cus_id) throws SQLException {
    	String sql = "SELECT MAX(order_num) AS max_order_num FROM orders WHERE cus_id = ?";
        int ordernum = -1;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cus_id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    ordernum = rs.getInt("max_order_num");
                    if (rs.wasNull()) { // 값이 NULL인 경우 확인
                        ordernum = -1; // -1로 설정하여 데이터가 없음을 명확히 알림
                }
            }
        }
        }
        return ordernum;
    }
    	
    	
        
    

    // 고객 마일리지 조회
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
        return 0; // 고객이 없으면 기본 마일리지는 0
    }


    // 고객 마일리지 업데이트
    public int updateCus_point(String cus_phone, int cus_point) throws SQLException {
    		
        String sql = "UPDATE customer SET cus_point = cus_point + ? WHERE cus_phone = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
            pstmt.setInt(1, cus_point);
            pstmt.setString(2, cus_phone);
            return pstmt.executeUpdate();
        }
    }
    
 // 제품 이름으로 제품 ID 조회 메서드
    
    /*
    public int getProductIdByName(String productName) throws SQLException {
        String sql = "SELECT pro_id FROM product WHERE pro_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, productName); // 제품 이름을 SQL에 설정
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("pro_id"); // 조회된 제품 ID 반환
                } else {
                    throw new SQLException("제품 이름에 해당하는 ID를 찾을 수 없습니다: " + productName);
                }
            }
        }
    }
    */
    public ProductDTO getProductByName(String pro_name) throws SQLException {
        String sql = "SELECT * FROM product WHERE pro_name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pro_name);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new ProductDTO(
                        rs.getInt("pro_id"),
                        rs.getString("pro_name"),
                        rs.getInt("pro_price"),
                        rs.getString("pro_type")
                    );
                } else {
                    throw new SQLException("상품을 찾을 수 없습니다: " + pro_name);
                }
            }
        }
    }




    
    
    
    public void insertOrderByPhone(OrdersDTO order, String cus_phone) throws SQLException {
        String sql = "INSERT INTO orders (order_num, cus_id, total_price, card_payment, order_date) " +
                     "SELECT orders_seq.NEXTVAL, cus_id, ?, ?, SYSDATE " +
                     "FROM customer WHERE cus_phone = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, order.getTotal_price());
            pstmt.setString(2, order.getCard_payment());
            pstmt.setString(3, cus_phone);
            pstmt.executeUpdate();
        }
    }


    public int getOrderNumByPhone(String cus_phone) throws SQLException {
        String sql = "SELECT MAX(order_num) FROM orders o " +
                     "JOIN customer c ON o.cus_id = c.cus_id " +
                     "WHERE c.cus_phone = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cus_phone);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    
    
    

    
    
    

    
    
    
    
    
    
    
}
