package jys;

import java.awt.*;

import javax.swing.*;


import java.awt.BorderLayout;
import java.awt.event.*;
import java.net.URL;

import javax.swing.table.*;

import test.CafeDAO;
import test.CustomerDTO;
import test.MiroticDTO;
import test.OrdersDTO;
import test.ProductDTO;

import java.sql.*;
import java.util.HashMap;

public class P2 implements ActionListener {
	Connection con = null;                  // DB와 연결하는 객체
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;                      // SQL문을 저장하는 문자열 변수.
	String[] header = {"상품 ID", "상품명", "단가", "수량"};
	
	
	DefaultTableModel model;
	JTable table;
	DefaultTableModel model2;
	JTable table2;
	
	private JButton btnCard;  	// 카드결제.
	private JButton btnCash;	// 현금결제.
	private JButton btnDelete;	// 삭제버튼.
    private JButton calculateButton;	// 계산하기 버튼.
    private JButton btnPoint;	    // 마일리지 조회 및 사용 버튼.
    
    private JLabel totalPriceLabel = new JLabel("총 금액: 0원");
    private JLabel totalQuantityLabel = new JLabel("총 수량: 0개");
    private JTextArea orderSummaryArea = new JTextArea();
    
	
	private String cus_phone;		// cus_phone을 이걸로 받아야함.
    private int cus_pointUsed;		// 마일리지 사용할 때 사용.
    private int totalPrice;			// 총 가격.
    private int finalPrice;			// 마일리지 차감 후 지불해야 하는 가격.
    private int totalQuantity;
	
	private JFrame frame;
	private JTextArea textArea;
	
	private HashMap<String, JTextField> quantityFields = new HashMap<>();

	@Override
	public void actionPerformed(ActionEvent e) {
	    JButton button = (JButton) e.getSource();
	    String productName = button.getText(); // 버튼 텍스트를 상품명으로 사용

	    CafeDAO dao = new CafeDAO();
	    try {
	        // 상품 정보를 데이터베이스에서 가져옴
	        ProductDTO product = dao.getProductByName(productName);

	        // JTable에 추가
	        addToTable(product.getPro_name(), product.getPro_price(), 1); // 기본 수량은 1
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "상품 정보를 가져오는 중 오류 발생: " + ex.getMessage());
	    }
	}


	void connect() {	// DB를 연동하는 메서드
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "jys";
		String password = "pass";
		
		
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
	
	private void updateTotalPriceAndQuantity() {
	    totalPrice = 0;
	    totalQuantity = 0;

	    for (int i = 0; i < model.getRowCount(); i++) {
	        int price = (int) model.getValueAt(i, 1); // 상품 가격
	        int quantity = (int) model.getValueAt(i, 2); // 상품 수량
	        totalPrice += price * quantity;
	        totalQuantity += quantity;
	    }

	    totalPriceLabel.setText("총 금액: " + totalPrice + "원");
	    totalQuantityLabel.setText("총 수량: " + totalQuantity + "개");
	}


	private void resetUI() {
	    // JTable 초기화
	    int rowCount = model.getRowCount();
	    for (int i = rowCount - 1; i >= 0; i--) {
	        model.removeRow(i); // 테이블의 모든 행 제거
	    }

	    // 총 금액 및 총 수량 라벨 초기화
	    totalPriceLabel.setText("총 금액: 0원");
	    totalQuantityLabel.setText("총 수량: 0개");

	    // 주문 요약 영역 초기화
	    orderSummaryArea.setText("");

	    // 상태 변수 초기화
	    cus_phone = null;
	    cus_pointUsed = 0;
	    totalPrice = 0;
	    finalPrice = 0;
	    totalQuantity = 0;
	}

	
	void select(String name) {	// 전체조회.
		
		try {
			// 1. 오라클 데이터베이스로 전송할 SQL문 작성.
			sql = "select * from product where pro_name = ? order by pro_type";
			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);
			
			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String pro_name  = rs.getString("pro_name");
				int pro_price  = rs.getInt("pro_price");
				
				Object[] data = 
					{pro_name, pro_price, 1};
				
				// 저장된 한 개의 레코드(데이터)를 model에 추가해 주면 됨.
				model.addRow(data);
			}
			
			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			rs.close(); pstmt.close(); con.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}  // select() 메서드 end
	
	private void addToTable(String name, int price, int quantity) {
	    // JTable에 이미 동일한 상품이 존재하는지 확인
	    for (int i = 0; i < model.getRowCount(); i++) {
	        String existingName = (String) model.getValueAt(i, 0); // 첫 번째 컬럼이 상품명
	        if (existingName.equals(name)) {
	            // 이미 존재하면 수량만 증가
	            int existingQuantity = (int) model.getValueAt(i, 2); // 세 번째 컬럼이 수량
	            model.setValueAt(existingQuantity + quantity, i, 2);
	            updateTotalPriceAndQuantity(); // 총합 및 수량 업데이트
	            return; // 추가 작업 중단
	        }
	    }

	    // 새 데이터 추가
	    model.addRow(new Object[]{name, price, quantity});
	    updateTotalPriceAndQuantity(); // 총합 및 수량 업데이트
	}


	private void selectProduct(String productName) throws SQLException {
	    CafeDAO dao = new CafeDAO();
	    ProductDTO product = dao.getProductByName(productName); // 제품 정보 조회
	    
	    boolean productExists = false;

	    for (int i = 0; i < model.getRowCount(); i++) {
	        if (model.getValueAt(i, 0).equals(product.getPro_name())) {
	            int currentQuantity = (int) model.getValueAt(i, 2);
	            model.setValueAt(currentQuantity + 1, i, 2); // 수량 증가
	            productExists = true;
	            break;
	        }
	    }

	    if (!productExists) {
	        // 상품이 테이블에 없으면 추가
	        Object[] rowData = {product.getPro_name(), product.getPro_price(), 1};
	        model.addRow(rowData);
	    }
	}

	
	
	
	private int calculateTotalPrice() {
	    int rowCount = table.getRowCount();
	    int totalPrice = 0;

	    for (int i = 0; i < rowCount; i++) {
	        int price = (int) table.getValueAt(i, 1); // 단가
	        int quantity = (int) table.getValueAt(i, 2); // 수량
	        totalPrice += price * quantity;
	    }
	    return totalPrice;
	}	// calcu end.
	
	private void checkCus_point() {
	    String inputCusPhone = JOptionPane.showInputDialog(frame, "휴대폰 번호를 입력하세요:");

	    if (inputCusPhone == null || inputCusPhone.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(frame, "휴대폰 번호를 입력해주세요.");
	        return;
	    }

	    CafeDAO dao = new CafeDAO();

	    try {
	        // 고객 존재 여부 확인
	        boolean isCustomer = dao.selectCustomer(inputCusPhone);

	        if (!isCustomer) {
	            int registerOption = JOptionPane.showConfirmDialog(frame, "등록된 고객이 아닙니다. 등록하시겠습니까?", "고객 등록", JOptionPane.YES_NO_OPTION);
	            if (registerOption == JOptionPane.YES_OPTION) {
	                CustomerDTO newCustomer = new CustomerDTO();
	                newCustomer.setCus_phone(inputCusPhone);
	                newCustomer.setCus_point(0);
	                newCustomer.setCus_date(new java.sql.Date(System.currentTimeMillis()));

	                dao.insertCustomer(newCustomer);
	                JOptionPane.showMessageDialog(frame, "고객이 등록되었습니다.");
	            } else {
	                return;
	            }
	        }

	        // 고객 전화번호 저장
	        cus_phone = inputCusPhone;

	        // 현재 마일리지 확인
	        int currentMileage = dao.getCus_point(cus_phone);
	        JOptionPane.showMessageDialog(frame, "현재 마일리지: " + currentMileage + "점");

	        // 마일리지 사용 여부 묻기
	        int useMileageOption = JOptionPane.showConfirmDialog(frame, "마일리지를 사용하시겠습니까?", "마일리지 사용", JOptionPane.YES_NO_OPTION);

	        if (useMileageOption == JOptionPane.YES_OPTION) {
	            String mileageInput = JOptionPane.showInputDialog(frame, "사용할 마일리지 점수를 입력하세요 (최대 " + currentMileage + "점):");

	            try {
	                int requestedMileage = Integer.parseInt(mileageInput);

	                if (requestedMileage > currentMileage) {
	                    JOptionPane.showMessageDialog(frame, "보유한 마일리지보다 많이 입력하셨습니다.");
	                    return;
	                } else if (requestedMileage < 0) {
	                    JOptionPane.showMessageDialog(frame, "0 이상의 값을 입력해주세요.");
	                    return;
	                } else {
	                    cus_pointUsed = requestedMileage; // 사용 마일리지 설정
	                    finalPrice = totalPrice - cus_pointUsed; // 최종 결제 금액 계산
	                }

	                JOptionPane.showMessageDialog(frame, "마일리지 " + cus_pointUsed + "점이 사용으로 설정되었습니다. 최종 결제 금액: " + finalPrice + "원");

	            } catch (NumberFormatException e) {
	                JOptionPane.showMessageDialog(frame, "숫자를 입력해주세요.");
	            }
	        } else {
	            // 마일리지 사용 안 함
	            cus_pointUsed = 0;
	            finalPrice = totalPrice; // 최종 결제 금액은 총 금액 그대로
	            JOptionPane.showMessageDialog(frame, "마일리지를 사용하지 않습니다. 최종 결제 금액: " + finalPrice + "원");
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "마일리지 조회 중 오류가 발생했습니다.");
	    }
	}

	
	private void placeOrder() {
		JOptionPane.showMessageDialog(frame, finalPrice);
		
		if (cus_phone == null || finalPrice <= 0) {
	        // JOptionPane.showMessageDialog(frame, "마일리지 조회 및 계산을 완료해주세요.");
	        return;
	    }
		
	    CafeDAO dao = new CafeDAO();

	    try {
	        // 1. 고객 정보가 이미 확인되었으므로 주문 정보 저장
	    	boolean isCustomer = dao.selectCustomer(cus_phone);
	        if (!isCustomer) {
	            CustomerDTO newCustomer = new CustomerDTO();
	            newCustomer.setCus_phone(cus_phone);
	            newCustomer.setCus_point(0);
	            newCustomer.setCus_date(new java.sql.Date(System.currentTimeMillis()));

	            dao.insertCustomer(newCustomer);
	            JOptionPane.showMessageDialog(frame, "새 고객이 등록되었습니다.");
	        }
	    	
	    	
	        OrdersDTO order = new OrdersDTO();
	        order.setCus_id(null); // cus_id는 자동 생성
	        order.setTotal_price(finalPrice); // 총 결제 금액
	        order.setCard_payment("Y");
	        order.setOrder_date(new java.sql.Date(System.currentTimeMillis()));
	        dao.insertOrderByPhone(order, cus_phone);	// cus_phone 기반연결.

	        // 2. 방금 삽입된 주문 번호 가져오기
	        int orderNum = dao.getOrderNumByPhone(cus_phone);

	        // 3. 주문 상세 저장
	        int rowCount = table.getRowCount(); // JTable의 행 수
	        for (int i = 0; i < rowCount; i++) {
	            String productName = table.getValueAt(i, 0).toString(); // 상품명
	            int quantity = Integer.parseInt(table.getValueAt(i, 2).toString()); // 수량

	            if (quantity > 0) {
	                MiroticDTO detail = new MiroticDTO();
	                detail.setOrder_num(orderNum);

	                ProductDTO product = dao.getMenuList()
	                                        .stream()
	                                        .filter(p -> p.getPro_name().equals(productName))
	                                        .findFirst()
	                                        .orElseThrow(() -> new IllegalArgumentException("상품 정보가 없습니다: " + productName));

	                detail.setPro_id(product.getPro_id());
	                detail.setQuantity(quantity);
	                detail.setCus_phone(cus_phone);
	                detail.setUsed_point(cus_pointUsed);

	                dao.insertMirotic(detail); // 상세 정보 삽입
	            }
	        }

	        // 4. 마일리지 업데이트
	        if (cus_pointUsed > 0) {
	            dao.updateCus_point(cus_phone, -cus_pointUsed); // 사용한 마일리지 차감
	        }
	        // 6. 마일리지 적립
	        int mileageToAdd = (int) (finalPrice * 0.05); // 결제 금액의 5% 적립
	        if (mileageToAdd > 0) {
	            dao.updateCus_point(cus_phone, mileageToAdd);
	            JOptionPane.showMessageDialog(frame, "마일리지 " + mileageToAdd + "점이 적립되었습니다.");
	        } else {
	            JOptionPane.showMessageDialog(frame, "적립할 마일리지가 없습니다.");
	        }

	        JOptionPane.showMessageDialog(frame, "주문이 완료되었습니다!");	// 주문완료.

	        // UI 초기화
	        resetUI();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "주문 처리 중 오류가 발생했습니다.");
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(frame, "수량 입력이 잘못되었습니다.");
	    }
	}
	
	



    
    
		
	
	//	Main Start
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					P2 window = new P2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	// main end.

	public P2() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("메뉴판");
		frame.setBounds(100, 100, 743, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(1,0));
		
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 349, 528);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("커피", null, panel, null);
		panel.setLayout(null);
		
		JButton btnNewButton_3 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/coffee/카페라떼.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_3.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
        } catch (Exception e) {
            System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
        }
		 btnNewButton_3.setText("카페라떼");
		 
		btnNewButton_3.setBounds(12, 255, 120, 120);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/coffee/카라멜 마끼아또.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_4.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
		 btnNewButton_4.setText("카라멜 마끼아또");
		
		btnNewButton_4.setBounds(176, 255, 120, 120);
		panel.add(btnNewButton_4);
		
		// 버튼2 시작.
		
		JButton btnNewButton_2 = new JButton("New button");
		try {
		    URL imageUrl = P2.class.getResource(""); // 리소스 경로 설정
		    if (imageUrl != null) {
		        ImageIcon icon = new ImageIcon(imageUrl);
		        btnNewButton_2.setIcon(icon);
		    } else {
		        System.out.println("아메리카노 이미지. ");
		    }
		} catch (Exception e) {
		    System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
		}
		btnNewButton_2.setText("아이스 아메리카노"); // 버튼 텍스트 설정

		// ActionListener 추가
		btnNewButton_2.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        //1 JButton button = (JButton) e.getSource();
		        //2 String productName = button.getText(); // 버튼 텍스트를 상품명으로 사용

		        CafeDAO dao = new CafeDAO();
		        try {
		            // 데이터베이스에서 상품 정보 가져오기
		            ProductDTO product = dao.getProductByName("아이스 아메리카노");

		            // JTable에 데이터 추가
		            addToTable(product.getPro_name(), product.getPro_price(), 1); // 기본 수량 1
		        } catch (SQLException ex) {
		            ex.printStackTrace();
		            JOptionPane.showMessageDialog(frame, "상품 정보를 가져오는 중 오류 발생: " + ex.getMessage());
		        }
		    }
		});


		// 버튼 위치 설정 및 패널에 추가
		btnNewButton_2.setBounds(12, 52, 120, 120);
		panel.add(btnNewButton_2);

		
		
		// 버튼2 끝.
		
		JButton btnNewButton_2_1 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/coffee/카페라떼.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_1.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_1.setText("바닐라 라떼");
		
       btnNewButton_2_1.setBounds(176, 52, 120, 120);
		panel.add(btnNewButton_2_1);
		
		
		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setFont(new Font("굴림", Font.BOLD, 12));
		textPane.setText("  아이스 아메리카노\r\n        2,500원");
		textPane.setBounds(12, 182, 120, 42);
		 panel.add(textPane);
		
	
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("    바닐라 라떼\r\n       3,500원");
		textPane_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_1.setBounds(176, 182, 120, 42);
		panel.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("       카페라떼\r\n        3,500원");
		textPane_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_2.setBounds(12, 385, 120, 42);
		panel.add(textPane_2);
		
		JTextPane textPane_2_1 = new JTextPane();
		textPane_2_1.setText("   카라멜 마끼아또\r\n        4,000원");
		textPane_2_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_2_1.setBounds(176, 385, 120, 42);
		panel.add(textPane_2_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("파스타", null, panel_1, null);
		panel_1.setLayout(null);
		
		JButton btnNewButton_2_2 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/pasta/토마토 파스타.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_2.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_2.setText("토마토 파스타");
       btnNewButton_2_2.setBounds(29, 41, 120, 120);
		panel_1.add(btnNewButton_2_2);
		
		JButton btnNewButton_2_3 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/pasta/라구 파스타.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_3.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_3.setText("라구 파스타");
		btnNewButton_2_3.setBounds(186, 41, 120, 120);
		panel_1.add(btnNewButton_2_3);
		
		JButton btnNewButton_2_4 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/pasta/바질페스토 파스타.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_4.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_4.setText("바질페스토 파스타");
		btnNewButton_2_4.setBounds(29, 265, 120, 120);
		panel_1.add(btnNewButton_2_4);
		
		JButton btnNewButton_2_5 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/pasta/알리오올리오.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_5.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_5.setText("알리오올리오");
		btnNewButton_2_5.setBounds(186, 265, 120, 120);
		panel_1.add(btnNewButton_2_5);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText("    토마토 파스타\r\n        7,000원");
		textPane_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3.setBounds(29, 171, 120, 42);
		panel_1.add(textPane_3);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setText("     라구 파스타\r\n        8,500원");
		textPane_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_4.setBounds(186, 171, 120, 42);
		panel_1.add(textPane_4);
		
		JTextPane textPane_5 = new JTextPane();
		textPane_5.setText("  바질페스토 파스타\r\n        8,000원");
		textPane_5.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_5.setBounds(29, 395, 120, 42);
		panel_1.add(textPane_5);
		
		JTextPane textPane_6 = new JTextPane();
		textPane_6.setText("     알리오올리오\r\n         8,500원");
		textPane_6.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_6.setBounds(186, 395, 120, 42);
		panel_1.add(textPane_6);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("음료", null, panel_2, null);
		panel_2.setLayout(null);
		
		JButton btnNewButton_2_6 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/drink/레몬에이드.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6.setText("레몬에이드");
		btnNewButton_2_6.setBounds(34, 35, 120, 120);
		panel_2.add(btnNewButton_2_6);
		
		JButton btnNewButton_2_7 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/drink/블루에이드.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_7.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_7.setText("블루에이드");
		btnNewButton_2_7.setBounds(193, 35, 120, 120);
		panel_2.add(btnNewButton_2_7);
		
		JButton btnNewButton_2_8 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/drink/사이다.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_8.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_8.setText("사이다");
		btnNewButton_2_8.setBounds(34, 225, 120, 120);
		panel_2.add(btnNewButton_2_8);
		
		JButton btnNewButton_2_9 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/drink/콜라.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_9.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_9.setText("콜라");
		btnNewButton_2_9.setBounds(193, 225, 120, 120);
		panel_2.add(btnNewButton_2_9);
		
		JTextPane textPane_3_1 = new JTextPane();
		textPane_3_1.setText("     레몬 에이드\r\n        4,000원");
		textPane_3_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1.setBounds(34, 165, 120, 42);
		panel_2.add(textPane_3_1);
		
		JTextPane textPane_3_2 = new JTextPane();
		textPane_3_2.setText("    블루 에이드\r\n        4,000원");
		textPane_3_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_2.setBounds(193, 165, 120, 42);
		panel_2.add(textPane_3_2);
		
		JTextPane textPane_3_3 = new JTextPane();
		textPane_3_3.setText("        사이다\r\n        2,000원");
		textPane_3_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_3.setBounds(34, 355, 120, 42);
		panel_2.add(textPane_3_3);
		
		JTextPane textPane_3_4 = new JTextPane();
		textPane_3_4.setText("          콜라\r\n        2,000원");
		textPane_3_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_4.setBounds(193, 355, 120, 42);
		panel_2.add(textPane_3_4);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		tabbedPane.addTab("디저트", null, panel_3, null);
		panel_3.setLayout(null);
		
		JButton btnNewButton_2_6_1 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/dessert/치즈 케이크.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_1.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_1.setText("치즈 케이크");
		btnNewButton_2_6_1.setBounds(29, 33, 120, 120);
		panel_3.add(btnNewButton_2_6_1);
		
		JButton btnNewButton_2_6_2 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/dessert/초코 케이크.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_2.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_2.setText("초코 케이크");
		btnNewButton_2_6_2.setBounds(183, 33, 120, 120);
		panel_3.add(btnNewButton_2_6_2);
		
		JButton btnNewButton_2_6_3 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/dessert/에그타르트.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_3.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_3.setText("에그타르트");
		btnNewButton_2_6_3.setBounds(29, 233, 120, 120);
		panel_3.add(btnNewButton_2_6_3);
		
		JButton btnNewButton_2_6_4 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/dessert/휘낭시에.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_4.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_4.setText("휘낭시에");
		btnNewButton_2_6_4.setBounds(183, 233, 120, 120);
		panel_3.add(btnNewButton_2_6_4);
		
		JTextPane textPane_3_1_2 = new JTextPane();
		textPane_3_1_2.setText("     초코 케이크\r\n        4,000원");
		textPane_3_1_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2.setBounds(183, 163, 120, 42);
		panel_3.add(textPane_3_1_2);
		
		JTextPane textPane_3_1_3 = new JTextPane();
		textPane_3_1_3.setText("     에그 타르트\r\n        2,500원");
		textPane_3_1_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_3.setBounds(29, 363, 120, 42);
		panel_3.add(textPane_3_1_3);
		
		JTextPane textPane_3_1_4 = new JTextPane();
		textPane_3_1_4.setText("      휘낭시에\r\n        2,000원");
		textPane_3_1_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_4.setBounds(183, 363, 120, 42);
		panel_3.add(textPane_3_1_4);
		
		JTextPane textPane_3_1_2_1 = new JTextPane();
		textPane_3_1_2_1.setText("     치즈 케이크\r\n        4,000원");
		textPane_3_1_2_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_1.setBounds(29, 163, 120, 42);
		panel_3.add(textPane_3_1_2_1);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(240, 255, 255));
		tabbedPane.addTab("사이드", null, panel_4, null);
		panel_4.setLayout(null);
		
		JButton btnNewButton_2_6_1_1 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/side/리코타샐러드.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_1_1.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_1_1.setText("리코타샐러드");
		btnNewButton_2_6_1_1.setBounds(36, 44, 120, 120);
		panel_4.add(btnNewButton_2_6_1_1);
		
		JButton btnNewButton_2_6_1_2 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/side/케이준치킨샐러드.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_1_2.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_1_2.setText("케이준치킨샐러드");
		btnNewButton_2_6_1_2.setBounds(192, 44, 120, 120);
		panel_4.add(btnNewButton_2_6_1_2);
		
		JButton btnNewButton_2_6_1_3 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/side/양송이 크림스프.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_1_3.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_1_3.setText("양송이 크림스프");
		btnNewButton_2_6_1_3.setBounds(36, 237, 120, 120);
		panel_4.add(btnNewButton_2_6_1_3);
		
		JButton btnNewButton_2_6_1_4 = new JButton("New button");
		 try {
	            URL imageUrl = P2.class.getResource("/side/감자튀김.jpg"); // 리소스 경로 설정
	            if (imageUrl != null) {
	                ImageIcon icon = new ImageIcon(imageUrl);
	                btnNewButton_2_6_1_4.setIcon(icon);
	            } else {
	                System.out.println("이미지를 찾을 수 없습니다!");
	            }
       } catch (Exception e) {
           System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
       }
       btnNewButton_2_6_1_4.setText("감자튀김");
		btnNewButton_2_6_1_4.setBounds(192, 237, 120, 120);
		panel_4.add(btnNewButton_2_6_1_4);
		
		JTextPane textPane_3_1_2_2 = new JTextPane();
		textPane_3_1_2_2.setText("      리코타샐러드\r\n          8,000원");
		textPane_3_1_2_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_2.setBounds(36, 174, 120, 42);
		panel_4.add(textPane_3_1_2_2);
		
		JTextPane textPane_3_1_2_3 = new JTextPane();
		textPane_3_1_2_3.setText("     케이준치킨샐러드\r\n          9,000원");
		textPane_3_1_2_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_3.setBounds(179, 174, 140, 42);
		panel_4.add(textPane_3_1_2_3);
		
		JTextPane textPane_3_1_2_4 = new JTextPane();
		textPane_3_1_2_4.setText("     양송이 크림스프\r\n         4,000원");
		textPane_3_1_2_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_4.setBounds(36, 367, 120, 42);
		panel_4.add(textPane_3_1_2_4);
		
		JTextPane textPane_3_1_2_5 = new JTextPane();
		textPane_3_1_2_5.setText("       감자튀김\r\n        2,500원");
		textPane_3_1_2_5.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_5.setBounds(192, 367, 120, 42);
		panel_4.add(textPane_3_1_2_5);

		//좌측 그리드 세팅
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel0 = new JPanel();
		
		String[] header = 
			{"상 품 명","단 가","수 량"};
		
		model = new DefaultTableModel(header, 0);
		
		table = new JTable(model);
		
		JScrollPane jsp = new JScrollPane(
				table, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		String[] header2 = 
			{"총 금 액","사용 마일리지"};
		
		model2 = new DefaultTableModel(header2, 0);
		
		table2 = new JTable(model2);
		
		
		JScrollPane jsp2 = new JScrollPane(
				table2, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		jsp2.setPreferredSize(new Dimension(245,80));
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(588, 369, 42, 69);
		frame.getContentPane().add(spinner);
		
		JButton btnDelete = new JButton("상품삭제");
		btnDelete.setBounds(630, 369, 85, 69);
		frame.getContentPane().add(btnDelete);
		
		spinner.setPreferredSize(new Dimension(40, 80));
		
		panel0.add(jsp2);
		panel0.add(spinner);
		panel0.add(btnDelete);
		
		JButton btnCash = new JButton("현금결제");
		btnCash.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnCash.setBounds(479, 448, 97, 46);
	
		JButton btnCard = new JButton("카드결제");
		btnCard.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnCard.setBounds(361, 448, 97, 46);
		btnCard.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        placeOrder(); // 카드 결제 로직 호출
		    }
		});
		
		  
		JButton btnPoint = new JButton("할인조회");
		btnPoint.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnPoint.setBounds(596, 448, 97, 46);
		btnPoint.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        checkCus_point();
		    }
		});
	
		panel1.add(btnCash);
		panel1.add(btnCard);
		panel1.add(btnPoint);
		
		panel2.add(jsp,BorderLayout.NORTH);
		panel2.add(panel0,BorderLayout.CENTER);
		panel2.add(panel1,BorderLayout.SOUTH);
		
		frame.getContentPane().add(panel2);
				
		
		//버튼에 이벤스 리스너 세팅(커피)
		btnNewButton_3.addActionListener(this);
		btnNewButton_4.addActionListener(this);
		
		
		// btnNewButton_2.addActionListener(this);
		
		
		
		btnNewButton_2_1.addActionListener(this);
		
		//버튼에 이벤스 리스너 세팅(파스타)
		btnNewButton_2_2.addActionListener(this);
		btnNewButton_2_3.addActionListener(this);
		btnNewButton_2_4.addActionListener(this);
		btnNewButton_2_5.addActionListener(this);
		
		//버튼에 이벤스 리스너 세팅(음료)
		btnNewButton_2_6.addActionListener(this);
		btnNewButton_2_7.addActionListener(this);
		btnNewButton_2_8.addActionListener(this);
		btnNewButton_2_9.addActionListener(this);
		
		//버튼에 이벤스 리스너 세팅(디저트)
		btnNewButton_2_6_1.addActionListener(this);
		btnNewButton_2_6_2.addActionListener(this);
		btnNewButton_2_6_3.addActionListener(this);
		btnNewButton_2_6_4.addActionListener(this);
				
		//버튼에 이벤스 리스너 세팅(사이드)
		btnNewButton_2_6_1_1.addActionListener(this);
		btnNewButton_2_6_1_2.addActionListener(this);
		btnNewButton_2_6_1_3.addActionListener(this);
		btnNewButton_2_6_1_4.addActionListener(this);
	}
	}
	

	