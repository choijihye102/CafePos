package Admin_management;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import OrderPage.CustomerDTO;

public class OrderDAO {
	Connection con = null; // DB와 연결하는 객체
	PreparedStatement pstmt = null; // SQL문을 DB에 전송하는 객체
	ResultSet rs = null; // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;

	Orders2DTO cusDTO = new Orders2DTO(); // 주문 상세에 표시될 요약 정보

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
	} // connect() 메서드 end

	// 달력 클릭시 상세 정상 내역 조회
	public Orders2DTO getOrder(String y, String m, String d) throws SQLException {
	    connect();
	    cusDTO.setDate(d);

	    String sql = "SELECT " +
	                 "SUM(CASE WHEN card_payment = '0' THEN total_price ELSE 0 END) AS total_cash_payment, " +
	                 "SUM(CASE WHEN card_payment = '1' THEN total_price ELSE 0 END) AS total_card_payment, " +
	                 "SUM(total_price) AS total_price_sum, " +
	                 "SUM(used_point) AS total_used_points, " +
	                 "COUNT(order_num) AS total_orders " +
	                 "FROM orders " +
	                 "WHERE TRUNC(order_date) = TO_DATE(?, 'YYYY-MM-DD')";

	    try {
	        pstmt = con.prepareStatement(sql);
	        String date2 = y + "-" + m + "-" + d;
	        System.out.println("date2:" + date2);

	        pstmt.setString(1, date2);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            cusDTO.setTotal_card_payment(rs.getInt("total_card_payment"));
	            cusDTO.setTotal_cash_payment(rs.getInt("total_cash_payment"));
	            cusDTO.setTotal_orders(rs.getInt("total_orders"));
	            cusDTO.setTotal_price_sum(rs.getInt("total_price_sum"));
	            cusDTO.setTotal_used_points(rs.getInt("total_used_points"));
	        } else {
	            cusDTO = new Orders2DTO();
	            System.out.println("조회된 데이터가 없습니다. 기본값으로 초기화합니다.");
	        }

	        sql = "SELECT " +
	              "SUM(o.total_price) AS total_return_amount " +  // 공백 추가
	              "FROM returns r " +
	              "JOIN orders o ON r.order_num = o.order_num " +
	              "WHERE TRUNC(r.return_date) = TO_DATE(?, 'YYYY-MM-DD')";

	        pstmt = con.prepareStatement(sql);
	        pstmt.setString(1, date2);

	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            cusDTO.settotalReturn(rs.getInt("total_return_amount"));
	        }
	        return cusDTO;

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return cusDTO;
	}

	public void insertR(String id) {
		connect();

		try {
			// 1. 오라클 데이터베이스에 전송할 SQL문 작성.
			sql = "INSERT INTO returns (order_num)" + "VALUES (?)";

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, Integer.parseInt(id));

			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			int res = pstmt.executeUpdate();

			if (res < 0) {
				JOptionPane.showMessageDialog(null, "환불에 문제가 발생하였습니다.");
			}

			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료.
			pstmt.close();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			JOptionPane.showMessageDialog(null, "이미 환불된 주문번호입니다.");
			System.out.println("이미 삭제된 주문번호다 ");
			e.printStackTrace();
		}
	}

	public Orders2DTO getRe(String y, String m, String d) throws SQLException {
		connect();
		cusDTO.setDate(d);

		String sql = "SELECT SUM(o.total_price) AS total_return_amount" + "FROM returns"
				+ "JOIN orders o ON r.order_num = o.order_num"
				+ "WHERE TRUNC(r.return_date) = TO_DATE('?', 'YYYY-MM-DD')";

		try {
			pstmt = con.prepareStatement(sql);
			String date2 = y + "-" + m + "-" + d; // 24/11/26
			System.out.println("date2:" + date2);

			pstmt.setString(1, date2);

			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			rs = pstmt.executeQuery();

			if (rs.next()) {
				cusDTO.settotalReturn(rs.getInt("total_return_amount"));

			} else { // 조회된 데이터가 없는 경우
				cusDTO = new Orders2DTO();
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

}