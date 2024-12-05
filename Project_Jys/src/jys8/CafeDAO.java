package jys;

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

	public boolean selectCustomer(String cus_phon) throws SQLException { // 고객 존재 여부 확인
		String sql = "SELECT COUNT(*) FROM customer WHERE cus_phon = ?";
		return executeQuery(sql, pstmt -> pstmt.setString(1, cus_phon), rs -> rs.next() && rs.getInt(1) > 0);
	}

	public String getCustomerId(String cus_phon) throws SQLException {
		String sql = "SELECT cus_id FROM customer WHERE cus_phon = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cus_phon);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getString("cus_id");
				}
			}
		}
		return null; // 고객 ID가 없으면 null 반환
	}

	@FunctionalInterface
	public interface SQLConsumer<T> {
		void accept(T t) throws SQLException;
	}

	@FunctionalInterface
	public interface SQLFunction<T, R> {
		R apply(T t) throws SQLException;
	}

	private <T> T executeQuery(String sql, SQLConsumer<PreparedStatement> paramSetter, // 재사용 가능한 executeQuery 메서드
			SQLFunction<ResultSet, T> resultHandler) throws SQLException {
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

			paramSetter.accept(pstmt); // Set parameters

			try (ResultSet rs = pstmt.executeQuery()) { // Process the result

				return resultHandler.apply(rs);
			}
		}
	}

	public List<ProductDTO> getMenuList() throws SQLException { // 메뉴 리스트 가져오기
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

	public void insertCustomer(CustomerDTO customer) throws SQLException { // 새로운 고객 등록
		String sql = "INSERT INTO customer (cus_phon, cus_point, created_date) VALUES (?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, customer.getCus_phon());
			pstmt.setInt(2, customer.getCus_point());
			pstmt.setDate(3, customer.getCus_date());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("고객 생성 중 오류 발생", e);
		}
	}

	public int insertMirotic(MiroticDTO mirotic) throws SQLException {
		String sql = "INSERT INTO mirotic (detail_id, order_num, pro_id, quantity) VALUES (mirotic_seq.NEXTVAL, ?, ?, ?)";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, mirotic.getOrder_num());
			pstmt.setInt(2, mirotic.getPro_id());
			pstmt.setInt(3, mirotic.getQuantity());
			return pstmt.executeUpdate();
		}
	}

	public int getCus_point(String cus_phon) throws SQLException { // 고객 마일리지 조회
		String sql = "SELECT cus_point FROM customer WHERE cus_phon = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cus_phon);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				return rs.getInt("cus_point");
			}
		}
		return 0; // 고객이 없으면 기본 마일리지는 0
	}

	public int updateCus_point(String cus_phon, int cus_point) throws SQLException {
		String sql = "UPDATE customer SET cus_point = cus_point + ? WHERE cus_phon = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, cus_point);
			pstmt.setString(2, cus_phon);
			return pstmt.executeUpdate();
		}
	}

	public ProductDTO getProductByName(String pro_name) throws SQLException {
		String sql = "SELECT * FROM product WHERE pro_name = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, pro_name);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new ProductDTO(rs.getInt("pro_id"), rs.getString("pro_name"), rs.getInt("pro_price"),
							rs.getString("pro_type"));
				} else {
					throw new SQLException("상품을 찾을 수 없습니다: " + pro_name);
				}
			}
		}
	}

	public void insertOrderByPhon(OrdersDTO order, String cus_phon) throws SQLException {
		String sql = "INSERT INTO orders (order_num, cus_id, total_price, card_payment, order_date, used_point) "
				+ "SELECT order_seq.NEXTVAL, cus_id, ?, ?, SYSDATE, ? " + "FROM customer WHERE cus_phon = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, order.getTotal_price());
			pstmt.setString(2, order.getCard_payment());
			pstmt.setInt(3, order.getUsed_point());
			pstmt.setString(4, cus_phon);
			pstmt.executeUpdate();
		}
	}

	public int getOrderNumByPhon(String cus_phon) throws SQLException {
		String sql = "SELECT MAX(order_num) FROM orders o " + "JOIN customer c ON o.cus_id = c.cus_id "
				+ "WHERE c.cus_phon = ?";
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, cus_phon);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		}
		return -1;
	}

}