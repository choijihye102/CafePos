package jys;

import java.awt.*;

import javax.swing.*;

import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent; // ChangeEvent도 필요

import javax.swing.event.TableModelListener;
import javax.swing.event.TableModelEvent;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.net.URL;

import javax.swing.table.*;
import javax.swing.text.JTextComponent;

import java.sql.*;
import java.util.Vector;

public class P2 implements ActionListener {
	private static final String SQL_SELECT_PRODUCT = // 상수추가 코드의 가독성 및 유지보수성 Up
			"SELECT * FROM product WHERE pro_name = ? ORDER BY pro_type";

	Connection con = null; // DB와 연결하는 객체
	PreparedStatement pstmt = null; // SQL문을 DB에 전송하는 객체
	ResultSet rs = null; // SQL문 실행 결과를 가지고 있는 객체
	String sql = null; // SQL문을 저장하는 문자열 변수.

	DefaultTableModel model;
	JTable table;
	DefaultTableModel model2;
	JTable table2;

	private JFrame frame;
	private JTextArea textArea;

	private JPanel coffeePanel;
	private JPanel pastaPanel;
	private JPanel drinkPanel;
	private JPanel dessertPanel;
	private JPanel sidedishPanel;

	JButton btnCard;

	private String cus_phon;
	private int cus_point;
	private int totalPrice;
	private int finalPrice;

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
	}

	public P2() { // Create the application.
		initialize();
	}

	private void initialize() { // Initialize the contents of the frame.
		frame = new JFrame();
		frame.setTitle("메뉴판");
		frame.setBounds(100, 100, 743, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(new GridLayout(1, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP); // 매우중요한 역활. 이놈을 일단 지금은 지울 수 없음

		tabbedPane.setBounds(0, 0, 349, 528);
		frame.getContentPane().add(tabbedPane);

		createNamedPanel(tabbedPane, "커피", "coffeePanel", Color.WHITE);
		createNamedPanel(tabbedPane, "파스타", "pastaPanel", Color.WHITE);
		createNamedPanel(tabbedPane, "음료", "drinkPanel", Color.WHITE);
		createNamedPanel(tabbedPane, "디저트", "dessertPanel", Color.WHITE);
		createNamedPanel(tabbedPane, "사이드", "sidedishPanel", new Color(240, 255, 255));

		addTextPane(coffeePanel, "  아이스 아메리카노\r\n        2,500원", 12, 182, 120, 42);
		addTextPane(coffeePanel, "    바닐라 라떼\r\n       3,500원", 176, 182, 120, 42);
		addTextPane(coffeePanel, "       카페라떼\r\n        3,500원", 12, 385, 120, 42);
		addTextPane(coffeePanel, "   카라멜 마끼아또\r\n        4,000원", 176, 385, 120, 42);

		addTextPane(pastaPanel, "    토마토 파스타\r\n        7,000원", 29, 171, 120, 42);
		addTextPane(pastaPanel, "     라구 파스타\r\n        8,500원", 186, 171, 120, 42);
		addTextPane(pastaPanel, "  바질페스토 파스타\r\n        8,000원", 29, 395, 120, 42);
		addTextPane(pastaPanel, "     알리오올리오\r\n         8,500원", 186, 395, 120, 42);

		addTextPane(drinkPanel, "     레몬 에이드\r\n        4,000원", 34, 165, 120, 42);
		addTextPane(drinkPanel, "    블루 에이드\r\n        4,000원", 193, 165, 120, 42);
		addTextPane(drinkPanel, "        사이다\r\n        2,000원", 34, 355, 120, 42);
		addTextPane(drinkPanel, "         콜라\r\n        2,000원", 193, 355, 120, 42);

		addTextPane(dessertPanel, "     초코 케이크\r\n        4,000원", 183, 163, 120, 42);
		addTextPane(dessertPanel, "     에그 타르트\r\n        2,500원", 29, 363, 120, 42);
		addTextPane(dessertPanel, "      휘낭시에\r\n        2,000원", 183, 363, 120, 42);
		addTextPane(dessertPanel, "     치즈 케이크\r\n        4,000원", 29, 163, 120, 42);

		addTextPane(sidedishPanel, "      리코타샐러드\r\n          8,000원", 36, 174, 120, 42);
		addTextPane(sidedishPanel, "     케이준치킨샐러드\r\n          9,000원", 179, 174, 120, 42);
		addTextPane(sidedishPanel, "     양송이 크림스프\r\n         4,000원", 36, 367, 120, 42);
		addTextPane(sidedishPanel, "       감자튀김\r\n        2,500원원", 192, 367, 120, 42);

		JPanel panel1 = new JPanel(); // 좌측 그리드 세팅
		JPanel panel2 = new JPanel();
		JPanel panel0 = new JPanel();

		String[] header = { "상 품 명", "단 가", "수 량", "총 액" };

		model = new DefaultTableModel(header, 0);
		table = new JTable(model);

		JScrollPane jsp = new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		String[] header2 = { "총 금 액", "사용 마일리지" };

		model2 = new DefaultTableModel(header2, 0);
		table2 = new JTable(model2);

		JScrollPane jsp2 = new JScrollPane(table2, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		jsp2.setPreferredSize(new Dimension(245, 80));

		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); // 수량조절
		spinner.setBounds(588, 369, 42, 69);
		frame.getContentPane().add(spinner);

		table.getSelectionModel().addListSelectionListener(e -> {
			int selectedRow = table.getSelectedRow(); // 선택된 행이 있으면 JSpinner 값 업데이트
			if (selectedRow >= 0) {
				int quantity = (Integer) model.getValueAt(selectedRow, 2); // 선택된 행의 수량
				spinner.setValue(quantity); // JSpinner 값 업데이트
			}
		});
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int selectedRow = table.getSelectedRow();
				if (selectedRow >= 0) {
					int quantity = (Integer) spinner.getValue();
					model.setValueAt(quantity, selectedRow, 2);

					Object unitPriceObj = model.getValueAt(selectedRow, 1); // 총액 계산
					if (unitPriceObj instanceof Number) {
						int unitPrice = ((Number) unitPriceObj).intValue();
						int totalPrice = unitPrice * quantity;
						model.setValueAt(totalPrice, selectedRow, 3);
					}
					updateTotalNum(); // 총 금액 계산
					System.out.println("handleCustomerMileage - 총 금액: " + totalPrice);

				}
			}
		});
		JButton btnNewButton_6 = new JButton("상품삭제"); // 상품삭제버튼
		btnNewButton_6.setBounds(630, 369, 85, 69);
		frame.getContentPane().add(btnNewButton_6);

		btnNewButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow(); // 선택된 행 인덱스
				if (selectedRow != -1) { // JTable에서 행 삭제 전 해당 행의 총액 값 가져오기

					Object totalObj = model.getValueAt(selectedRow, 3); // 총액 컬럼 값
					if (totalObj instanceof Number) {
						int totalValue = ((Number) totalObj).intValue();
						totalnum -= totalValue; // 삭제된 상품의 총액만큼 totalnum에서 빼기
					}
					model.removeRow(selectedRow); // 모델에서 해당 행 삭제

					updateTotalNum(); // updateTotalNum()을 호출하여 totalnum을 다시 계산 // 총액 갱신

					model.fireTableDataChanged(); // model 갱신
					model2.fireTableDataChanged(); // model2 갱신

				} else {
					JOptionPane.showMessageDialog(frame, "행을 선택하세요.");
				}
			}
		});

		spinner.setPreferredSize(new Dimension(40, 80));

		panel0.add(jsp2);
		panel0.add(spinner);
		panel0.add(btnNewButton_6);

		JButton btnNewButton = new JButton("현금결제");
		btnNewButton.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnNewButton.setBounds(479, 448, 97, 46);

		JButton btnCard = new JButton("카드결제");
		btnCard.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnCard.setBounds(361, 448, 97, 46);
		btnCard.addActionListener(e -> handleCardPayment());

		JButton btnCus_point = new JButton("할인조회");
		btnCus_point.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnCus_point.setBounds(596, 448, 97, 46);
		btnCus_point.addActionListener(e -> handleCustomerMileage());

		panel1.add(btnNewButton);
		panel1.add(btnCard);
		panel1.add(btnCus_point);

		panel2.add(jsp, BorderLayout.NORTH);
		panel2.add(panel0, BorderLayout.CENTER);
		panel2.add(panel1, BorderLayout.SOUTH);

		frame.getContentPane().add(panel2);

		createButton(coffeePanel, "아이스 아메리카노", "/coffee/아이스 아메리카노.jpg", 12, 52, 120, 120); // 동적버튼 생성
		createButton(coffeePanel, "카페라떼", "/coffee/카페라떼.jpg", 12, 255, 120, 120);
		createButton(coffeePanel, "바닐라 라떼", "/coffee/바닐라 라떼.jpg", 176, 52, 120, 120);
		createButton(coffeePanel, "카라멜 마끼아또", "/coffee/카라멜 마끼아또.jpg", 176, 255, 120, 120);

		createButton(pastaPanel, "토마토 파스타", "/pasta/토마토 파스타.jpg", 29, 41, 120, 120);
		createButton(pastaPanel, "라구 파스타", "/pasta/라구 파스타.jpg", 186, 41, 120, 120);
		createButton(pastaPanel, "바질페스토 파스타", "/pasta/바질페스토 파스타.jpg", 29, 265, 120, 120);
		createButton(pastaPanel, "알리오올리오", "/pasta/알리오올리오.jpg", 186, 265, 120, 120);

		createButton(drinkPanel, "레몬에이드", "/drink/레몬에이드.jpg", 34, 35, 120, 120);
		createButton(drinkPanel, "블루에이드", "/drink/블루에이드.jpg", 193, 35, 120, 120);
		createButton(drinkPanel, "레몬에이드", "/drink/레몬에이드.jpg", 34, 225, 120, 120);
		createButton(drinkPanel, "콜라", "/drink/레몬에이드.jpg", 193, 225, 120, 120);

		createButton(dessertPanel, "치즈 케이크", "/dessert/치즈케이크.jpg", 29, 33, 120, 120);
		createButton(dessertPanel, "초코 케이크", "/dessert/초코케이크.jpg", 183, 33, 120, 120);
		createButton(dessertPanel, "에그타르트", "/dessert/에그타르트.jpg", 29, 233, 120, 120);
		createButton(dessertPanel, "휘낭시에", "/dessert/휘낭시에.jpg", 183, 233, 120, 120);

		createButton(sidedishPanel, "리코타샐러드", "/side/리코타샐러드.jpg", 36, 44, 120, 120);
		createButton(sidedishPanel, "케이준치킨샐러드", "/side/케이준치킨샐러드.jpg", 192, 44, 120, 120);
		createButton(sidedishPanel, "양송이 크림스프", "/side/양송이 크림스프.jpg", 36, 237, 120, 120);
		createButton(sidedishPanel, "감자튀김", "/side/감자튀김.jpg", 192, 237, 120, 120);
	}

	private int calculateTotalAmount(DefaultTableModel model3) {
		return 0;
	}

	private void updateTotal() {
		System.out.println("updateTotalNum 호출 - 총 금액 계산: " + totalPrice);

	}

	protected void updateTotalPrice(DefaultTableModel model3, int selectedRow) {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		connect();

		JButton target = (JButton) e.getSource();
		String ori = target.getText();
		System.out.println();
		select(ori);
	}

	void connect() { // DB를 연동하는 메서드

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "jys";
		String password = "pass";

		try {
			Class.forName(driver); // 1. 접속할 오라클 데이터베이스 드라이버를 메모리에 올리자. - 동적 작업
			con = DriverManager.getConnection(url, user, password); // 2. 오라클 데이터베이스와 연결을 시도.
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // connect() 메서드 end

	private int totalnum = 0;

	void select(String name) {
		if (con == null) {
			connect(); // 연결이 없으면 새로 연결 설정
		}
		try {

			pstmt = con.prepareStatement(SQL_SELECT_PRODUCT);
			pstmt.setString(1, name);

			rs = pstmt.executeQuery(); // 2. SQL 실행

			while (rs.next()) { // 3. 결과 처리
				String pro_name = rs.getString("pro_name");
				int pro_price = rs.getInt("pro_price");
				int quantity = 1;
				int total = pro_price * quantity;

				totalnum += total;

				Object[] data = { pro_name, pro_price, quantity, total };
				model.addRow(data);
			}

			System.out.println("Data added to model. Rows: " + model.getRowCount()); // 디버깅 로그

			updateTotalNum(); // UI 업데이트

			SwingUtilities.invokeLater(() -> {
				table2.invalidate();
				table2.validate();
				table2.repaint();
			});

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "SQL 실행 중 오류가 발생했습니다: " + e.getMessage());
		} finally {
			// 4. 자원 해제
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close(); // 이후 재사용을 위해 connect()로 재연결 가능
				con = null; // 해제 후 con 초기화
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateTotalNum() {
		totalnum = 0; // 총합 초기화
		for (int i = 0; i < model.getRowCount(); i++) {
			Object totalObj = model.getValueAt(i, 3);
			if (totalObj instanceof Number) {
				totalnum += ((Number) totalObj).intValue();
			}
		}

		DefaultTableModel newModel2 = new DefaultTableModel(new String[] { "총 금 액", "사용 마일리지" }, 0);
		newModel2.addRow(new Object[] { totalnum, cus_point > 0 ? cus_point : 0 });

		table2.setModel(newModel2); // 테이블에 새 모델 설정
	}

	private void createButton(JPanel panel, String text, String imagePath, int x, int y, int width, int height) {
		JButton button = new JButton("New button");

		try {
			URL imageUrl = P2.class.getResource(imagePath); // 리소스 경로 설정
			if (imageUrl != null) {
				ImageIcon icon = new ImageIcon(imageUrl);
				button.setIcon(icon);
			} else {
				System.out.println("이미지를 찾을 수 없습니다: " + imagePath);
			}
		} catch (Exception e) {
			System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
		}

		button.setText(text);
		button.setBounds(x, y, width, height);

		button.addActionListener(e -> { // 버튼 동작 추가 (예: 콘솔 출력)
			System.out.println(text + " 버튼이 클릭되었습니다!"); // 원하는 동작 추가
			select(text); // 예: 상품명 기반으로 데이터 조회
		});
		panel.add(button); // 생성된 버튼을 패널에 추가
	} // 여기에 메서드 추가.

	private void addTextPane(JPanel panel, String text, int x, int y, int width, int height) {
		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setFont(new Font("굴림", Font.BOLD, 12));
		textPane.setText(text);
		textPane.setBounds(x, y, width, height);
		panel.add(textPane);
	}

	private JPanel createNamedPanel(JTabbedPane tabbedPane, String tabTitle, String panelName, Color backgroundColor) {
		JPanel panel = new JPanel();
		panel.setBackground(backgroundColor);
		panel.setLayout(null);
		tabbedPane.addTab(tabTitle, null, panel, null);

		switch (panelName) { // 동적으로 패널 이름 지정
		case "coffeePanel":
			coffeePanel = panel;
			break;
		case "pastaPanel":
			pastaPanel = panel;
			break;
		case "drinkPanel":
			drinkPanel = panel;
			break;
		case "dessertPanel":
			dessertPanel = panel;
			break;
		case "sidedishPanel":
			sidedishPanel = panel;
			break;
		}
		return panel;
	}

	private void handleCustomerMileage() {
		String inputCusPhon = JOptionPane.showInputDialog(frame, "휴대폰 번호를 입력하세요:");

		if (inputCusPhon == null || inputCusPhon.trim().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "휴대폰 번호를 입력해주세요.");
			return;
		}

		CafeDAO dao = new CafeDAO();

		try {
			// 고객 존재 여부 확인
			boolean isCustomer = dao.selectCustomer(inputCusPhon);

			if (!isCustomer) {
				int registerOption = JOptionPane.showConfirmDialog(frame, "등록된 고객이 아닙니다. 등록하시겠습니까?", "고객 등록",
						JOptionPane.YES_NO_OPTION);
				if (registerOption == JOptionPane.YES_OPTION) {
					CustomerDTO newCustomer = new CustomerDTO();
					newCustomer.setCus_phon(inputCusPhon);
					newCustomer.setCus_point(0);
					newCustomer.setCus_date(new java.sql.Date(System.currentTimeMillis()));

					dao.insertCustomer(newCustomer);
					JOptionPane.showMessageDialog(frame, "고객이 등록되었습니다.");
				} else {
					return;
				}
			}

			// 고객 전화번호 저장
			cus_phon = inputCusPhon;

			// 현재 마일리지 확인
			int currentMileage = dao.getCus_point(cus_phon);
			JOptionPane.showMessageDialog(frame, "현재 마일리지: " + currentMileage + "점");

			// 총 금액 업데이트
			updateTotalNum(); // totalPrice 값 계산

			// 디버깅 로그 추가
			System.out.println("총 금액: " + totalPrice + ", 현재 마일리지: " + currentMileage);

			// 마일리지 사용 여부 묻기
			int useMileageOption = JOptionPane.showConfirmDialog(frame, "마일리지를 사용하시겠습니까?", "마일리지 사용",
					JOptionPane.YES_NO_OPTION);

			if (useMileageOption == JOptionPane.YES_OPTION) {
				String mileageInput = JOptionPane.showInputDialog(frame,
						"사용할 마일리지 점수를 입력하세요 (최대 " + currentMileage + "점):");

				try {
					int requestedMileage = Integer.parseInt(mileageInput);

					if (requestedMileage > currentMileage) {
						JOptionPane.showMessageDialog(frame, "보유한 마일리지보다 많이 입력하셨습니다.");
						return;
					} else if (requestedMileage > totalPrice) {
						JOptionPane.showMessageDialog(frame, "총 결제 금액보다 많은 마일리지를 사용할 수 없습니다.");
						return;
					} else if (requestedMileage < 0) {
						JOptionPane.showMessageDialog(frame, "0 이상의 값을 입력해주세요.");
						return;
					} else {
						cus_point = requestedMileage; // 사용 마일리지 설정
						finalPrice = totalPrice - cus_point; // 최종 결제 금액 계산
					}

					JOptionPane.showMessageDialog(frame,
							"마일리지 " + cus_point + "점이 사용으로 설정되었습니다. 최종 결제 금액: " + finalPrice + "원");

				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(frame, "숫자를 입력해주세요.");
				}
			} else {
				// 마일리지 사용 안 함
				cus_point = 0;
				finalPrice = totalPrice; // 최종 결제 금액은 총 금액 그대로
				JOptionPane.showMessageDialog(frame, "마일리지를 사용하지 않습니다. 최종 결제 금액: " + finalPrice + "원");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "마일리지 조회 중 오류가 발생했습니다.");
		}
	}

	private void handleCardPayment() {
		if (cus_phon == null || cus_phon.trim().isEmpty()) {
			JOptionPane.showMessageDialog(frame, "먼저 고객 정보를 입력하세요.");
			return;
		}

		CafeDAO dao = new CafeDAO();

		try {
			OrdersDTO order = new OrdersDTO();
			order.setCus_id(dao.getCustomerId(cus_phon)); // 고객 ID 가져오기
			order.setTotal_price(finalPrice);
			order.setCard_payment("1"); // 카드 결제

			dao.insertOrderByPhon(order, cus_phon); // OracleSQL에 주문 추가
			JOptionPane.showMessageDialog(frame, "카드 결제가 완료되었습니다!");

			// 마일리지 사용 여부에 따라 `used_point` 업데이트
			if (cus_point > 0) {
				JOptionPane.showMessageDialog(frame, "사용된 마일리지: " + cus_point + "점");
			} else {
				JOptionPane.showMessageDialog(frame, "마일리지를 사용하지 않았습니다.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(frame, "결제 중 오류가 발생했습니다.");
		}
	}

	private void resetOrderData() { // 주문 데이터를 초기화하는 메서드
		totalnum = 0; // 총 금액 초기화
		finalPrice = 0; // 최종 결제 금액 초기화
		cus_point = 0; // 사용된 마일리지 초기화
		model.setRowCount(0); // 주문 테이블 초기화
		updateTotalNum(); // 총 금액 UI 갱신
	}
}
