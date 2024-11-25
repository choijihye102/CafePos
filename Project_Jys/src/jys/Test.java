package jys;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.table.DefaultTableModel;

public class Test extends JFrame{
	
	
	Connection con = null;
	
	PreparedStatement pstmt = null;
	
	ResultSet rs = null;
	
	String sql = null;
	
	JTextField menuIdField, quantityfield;
	
	JTextField jtf1, jtf2, jtf3;
	
	JPasswordField pass1;
	
	JTable table;
	
	DefaultTableModel model;
	
	public DEPT() {
		
		setTitle("BAEK Brunch Cafe");
		
		
		JPanel container1 = new JPanel();
		JPanel container2 = new JPanel();
		
		
		
		
		
		
		JLabel jl1 = new JLabel("로그인 아이디 : ");
		jtf1 = new JTextField(5);
		
		JLabel jl2 = new JLabel("패스워드 입력 (문자와 숫자 포함): ");
		JPasswordField pass1 = new JPasswordField(10);
		pass1.setEchoChar('*');
		
		
		
		JLabel menuIdLabel = new JLabel("메뉴 아이디");
		menuIdField = new JTextField(10);
		
		JLabel quantitylaJLabel = new JLabel("수량 : ");
		quantityfield = new JTextField(5);
		
		
		
		String[] header = {"menu_id", "name", "price"};
		
		
		
		model = new DefaultTableModel(header, 0);
		
		table = new JTable(model);
		
		
		JScrollPane jsp =new JScrollPane(
				table,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				
				);
		
		
	
		
		JButton btn1 = new JButton("로그인");
		// 로그인 후 정산페이지를 볼 수 있는 사장님 전용 정산페이지와 직원 커뮤니티를 분리할 것.
		
		
		JButton btn2 = new JButton("주문하기.");
		JButton btn3 = new JButton("주문목록 불러오기");
		
		container1.add(jl1); container1.add(jtf1);
		container1.add(jl2); container1.add(pass1);
		container1.add(menuIdLabel); container1.add(menuIdField);
		container1.add(quantitylaJLabel); container1.add(quantityfield);
		
		
		container2.add(btn1); container2.add(btn2);
		
		container2.add(btn3);
		
		add(container1, BorderLayout.NORTH);
		
		add(container2, BorderLayout.CENTER);
		
		add(jsp, BorderLayout.SOUTH);
		
		
		setBounds(300, 300, 500, 250);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		
		// 이벤트 처리
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				connect();
				
				model.setRowCount(0);
				
				select();
				
				
			}
		});
		
		
		
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
				
				
				
			
				
				
				
				try {
					
					
					int menuId = Integer.parseInt(menuIdField.getText());
					
					int quantity = Integer.parseInt(quantityfield.getText());
					
					
					String orderSql = "insert into orders(order_id, order_date, customer_name, total_price)" + "values (order_sq.NEXTVAL, sysdate, ?, ?)";
					pstmt = con.prepareStatement(orderSql);
					pstmt.setInt(5, 0);
					pstmt.setInt(2, 0);
					pstmt.executeUpdate();
					
					
					String detailSql = "insert into order_detail (detail_id, order_id, menu_id, quantity, price" +
								"values (detail_seq.NEXTVAL, ?, ?, ?, ?)";
					
					pstmt = con.prepareStatement(detailSql);
					
					
					
					String getOrderIdSql = "select max(order_id) from orders";
					pstmt = con.prepareStatement(getOrderIdSql);
					rs = pstmt.executeQuery();
					int orderId = 0;
					
					
					if (rs.next()) {
						orderId = rs.getInt(1);
						
					}
					
					
					
					pstmt.setInt(1, orderId);
					pstmt.setInt(2, menuId);
					pstmt.setInt(3, quantity);
					
					
					String priceSql = "select price from menu where menu_id = ?";
					
					pstmt = con.prepareStatement(priceSql);
					pstmt.setInt(1, menuId);
					
					rs = pstmt.executeQuery();
					
					
					if(rs.next()) {
						
						int price = rs.getInt("price");
						
						pstmt.setInt(4, price);
						
						pstmt.executeUpdate();
						
						
					}
					
					
					String updateTotalPriceSql = "update orders set total_price = (select sum(quantity * price)" +
									"from order_detail where order_id = ?) where order_id = ?";
					
					
					pstmt = con.prepareStatement(updateTotalPriceSql);
					pstmt.setInt(1, orderId);
					pstmt.setInt(2, orderId);
					pstmt.executeUpdate();
					
					
					System.out.println("주문이 성공적으로 저장되었습니다. ");
					
					
					
				} catch (SQLException | NumberFormatException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
					System.out.println("주문저장에 실패하였습니다. ");
				}
				
				
				
			}
		});
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	

	public static void main(String[] args) {
		new DEPT();

	}	// main() 메서드 end
	
	
	
	// 데이터베이스 연결 메서드.
	void connect() {
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		
		String user = "jys";
		
		String password = "pass";
		
		
		
		try {
			Class.forName(driver);
			
			con = DriverManager.getConnection(url, user, password);
			
			if(con != null) {
				System.out.println("오라클 데이터베이스와 연결 성공!!!");
				
			}else {
				System.out.println("연결 실패 !!!");
				
			}
						
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	}	// connect() 메서드 end
	
	
	
	// 1. menu 테이블의 전체 목록을 조회하는 메서드.
	void select() {
		
		
		try {
			sql = "select * from menu order by menu_id";
			
			pstmt = con.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			
			while(rs.next()) {
				
				int menu = rs.getInt("menu_id");
				
				String name = rs.getString("name");
				
				int price = rs.getInt("price");
				
				Object[] data = {menu, name, price};
				
				
				model.addRow(data);
	
			}
			System.out.println("메뉴목록 조회 성공!");
			
						
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
	
				try {
					if(rs != null) rs.close();
					if(pstmt != null) pstmt.close();
					if(con != null) con.close();
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		}
		
	}	// select() end
	
	
	
	
	

}
