package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test_01 {

    public static void main(String[] args) {
        // 데이터베이스 연결 정보
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String user = "jys";
        String password = "pass";
        
        Connection con = null;
        
        try {
            // JDBC 드라이버 로딩
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("드라이버 로딩 성공!");
            
            // 데이터베이스 연결
            con = DriverManager.getConnection(url, user, password);
            System.out.println("데이터베이스 연결 성공!");
            
            // 데이터 삽입 메서드 호출
            insertProduct(con, 6, "새로운상품", 20000, "일반");
            
            // 모든 제품 조회 메서드 호출
            selectAllProducts(con);
            
        } catch (ClassNotFoundException e) {
            System.out.println("드라이버 로딩 실패!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("데이터베이스 연결 실패!");
            e.printStackTrace();
        } finally {
            // 연결 해제
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                    System.out.println("데이터베이스 연결 해제 성공!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 제품 삽입 메서드
    public static void insertProduct(Connection con, int id, String name, int price, String type) {
        PreparedStatement pstmt = null;
        String sql = "INSERT INTO product (pro_id, pro_name, pro_price, pro_type) VALUES (?, ?, ?, ?)";
        
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setInt(3, price);
            pstmt.setString(4, type);
            
            int result = pstmt.executeUpdate();
            if (result > 0) {
                System.out.println("데이터 삽입 성공!");
            } else {
                System.out.println("데이터 삽입 실패!");
            }
        } catch (SQLException e) {
            System.out.println("데이터 삽입 중 오류 발생!");
            e.printStackTrace();
        } finally {
            // PreparedStatement 해제
            try {
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // 모든 제품 조회 메서드
    public static void selectAllProducts(Connection con) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "SELECT * FROM product";
        
        try {
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("pro_id");
                String name = rs.getString("pro_name");
                int price = rs.getInt("pro_price");
                String type = rs.getString("pro_type");
                
                System.out.println(id + "\t" + name + "\t" + price + "\t" + type);
                System.out.println("--------------------------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("데이터 조회 중 오류 발생!");
            e.printStackTrace();
        } finally {
            // ResultSet 및 PreparedStatement 해제
            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
                
                if (pstmt != null && !pstmt.isClosed()) {
                    pstmt.close();
                }
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

