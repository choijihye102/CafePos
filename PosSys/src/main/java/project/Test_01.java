package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test_01 {

	public static void main(String[] args) {
		
		Connection con = null;
		
		
		PreparedStatement pstmt = null;
		
		ResultSet rs = null;
				
				String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		String user = "jys";
		
		String password = "pass";
		
		
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("동적 로딩 성공!!!");
			
			con = DriverManager.getConnection(url, user, password);
			
			
			if (con != null) {
				System.out.println("데이터베이스 연결 성공!!!");
				
			}
			
			String sql = "select * from product";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int id = rs.getInt("pro_id");
				String name = rs.getString("pro_name");
				int price = rs.getInt("pro_price");
				String type = rs.getString("pro_type");
				
				System.out.println(id + "\t" + name + "\t" + price + "\t" + type);
				System.out.println("::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
				
				
			}
			
			

		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
			
			try {
			rs.close();
			pstmt.close();
			con.close();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
			
			
		}
		
		

}
}