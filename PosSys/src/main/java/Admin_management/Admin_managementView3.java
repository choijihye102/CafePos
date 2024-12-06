package Admin_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.text.NumberFormat;
import java.util.Locale;

public class Admin_managementView3 implements ActionListener {
	
	DefaultTableModel model9;
	JTable table9;
	
	JButton jb9;
	JButton jb8;
	JButton jb3;
	JButton jb4;
	JButton jb5;
	JButton jb6;
	JButton jb7;
	JTextField jtfd;
	JButton jbt;
	JButton jbt2;
	
	Criteria cri = new Criteria(1,20);
	Cus_SelectwithCri cusDB = new Cus_SelectwithCri(cri);;  	//Cus_Selectwit
	
	
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	String sql;

	DefaultTableModel model;
	JTable table;
	DefaultTableModel model2;
	JTable table2;

	JFrame frame;
	JTextArea textArea;

	JComboBox jcb1;
	JButton jb1;

	JButton buttonBefore;
	JButton buttonAfter;

	JButton[] buttons;
	JButton[] jb2;

	Calendar cal = Calendar.getInstance();;
	int year = cal.get(Calendar.YEAR);

	int month = cal.get(Calendar.MONDAY);

	OrderDAO orderDAO = new OrderDAO();
	detailView view = new detailView();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					Admin_managementView3 window = new Admin_managementView3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Admin_managementView3() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		

		 // 프레임이 닫힐 때 새 클래스 호출
	    frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent e) {
	            // 새 클래스 생성자 호출
	            Product_v2.main window = new Product_v2.main();
	            window.frame.setVisible(true);
	            frame.dispose(); // 현재 창 종료
	        }
	    });
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension screenSize = toolkit.getScreenSize();
		frame.setSize(screenSize.width, screenSize.height);
		
		
		frame.setTitle("관리자 시스템");
		


		// 탭 패널 생성
		JTabbedPane tabbedPane = new JTabbedPane();

		// 정산 탭
		JPanel employeeTab = createEmployeeTab();
		tabbedPane.addTab("고객 조회", employeeTab);

		JPanel accountingTab = createAccountingTab();
		tabbedPane.addTab("정산 조회", accountingTab);

		// 직원 관리 탭

		// 메인 프레임에 탭 추가
		frame.add(tabbedPane);
		frame.setVisible(true);
	}

	private JPanel createAccountingTab() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel container2 = new JPanel();
		JPanel container3 = new JPanel();

		// Dimension dim = new Dimension(400,150);
		// 센터 세팅

		JButton buttonBefore = new JButton("Before");
		JButton buttonAfter = new JButton("After");

		JLabel label = new JLabel("00년 0월");

		JButton[] buttons = new JButton[49];
		String[] dayNames = { "일", "월", "화", "수", "목", "금", "토" };

		container2.setLayout(new FlowLayout());
		container2.add(buttonBefore);
		container2.add(label);
		container2.add(buttonAfter);

		Font font = new Font("sansSerif", Font.BOLD, 20);
		buttonAfter.setFont(font);
		buttonBefore.setFont(font);
		label.setFont(font);

		label.setText(getCalText());

		container3.setLayout(new GridLayout(7, 7, 5, 5));
		for (int i = 0; i < buttons.length; i++) {
		    buttons[i] = new JButton();
		    buttons[i].setPreferredSize(new Dimension(80, 60)); // 버튼 크기 고정
		    buttons[i].addActionListener(this);

		    container3.add(buttons[i]);

		    buttons[i].setFont(new Font("sansSerif", Font.BOLD, 12)); // 버튼 폰트 크기 조정

		    if (i < 7)
		        buttons[i].setText(dayNames[i]);

		    if (i % 7 == 0)
		        buttons[i].setForeground(Color.red);
		    if (i % 7 == 0)
		        buttons[i].setForeground(Color.blue);
		}
		setButton(buttons);
		calSet();

		// 프레임 세팅

		panel.add(container2, BorderLayout.NORTH);
		panel.add(container3, BorderLayout.CENTER);
		// add(jsp, BorderLayout.CENTER);

		CalendarFunction();

		buttonAfter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int gap = 0;
				if (e.getSource() == buttonBefore) {
					gap = -1;
				} else if (e.getSource() == buttonAfter) {
					gap = +1;
				}

				allInit(gap);
				label.setText(getCalText());
			}

		});

		buttonBefore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int gap = 0;
				if (e.getSource() == buttonBefore) {
					gap = -1;
				} else if (e.getSource() == buttonAfter) {
					gap = +1;
				}

				allInit(gap);
				label.setText(getCalText());
			}

		});

		return panel;
	}

	private void updateCalendar(Calendar cal, JButton[] buttons, JLabel label) {
		label.setText(cal.get(Calendar.YEAR) + "년 " + (cal.get(Calendar.MONTH) + 1) + "월");

		// Clear previous dates
		for (int i = 7; i < buttons.length; i++) {
			buttons[i].setText("");
		}

		cal.set(Calendar.DAY_OF_MONTH, 1);
		int firstDayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

		for (int day = 1; day <= daysInMonth; day++) {
			int index = 6 + firstDayOfWeek + day;
			buttons[index].setText(String.valueOf(day));
		}
	}

	//고객 조회 페이지 추가... 여기부터 
	private JPanel createEmployeeTab() {
		JPanel panel = new JPanel(new BorderLayout());

		JPanel container1 = new JPanel();
		JPanel container2 = new JPanel();
		
		//상단 세팅
		jtfd = new JTextField(20);
		
		//jtfd.setSize(130,30);
		
		jbt = new JButton("번호 조회");
		jbt2 = new JButton("전체 조회");
		container1.add(jtfd);
		container1.add(jbt);
		container1.add(jbt2);
		
		//센터 세팅
		
		Object[] header = {"n o", "번 호", "마일리지"};
		
		model9 = new DefaultTableModel(header,0);
		table9 = new JTable(model9);
		
		JScrollPane jsp = new JScrollPane(
				table9,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		
		//하단 세팅
		 jb9 = new JButton("<");
		 jb8 = new JButton(">");
		 jb3 = new JButton();
		 jb4 = new JButton();
		 jb5 = new JButton();
		 jb6 = new JButton();
		 jb7 = new JButton();

		container2.add(jb9);
	
		container2.add(jb3);
		container2.add(jb4);
		container2.add(jb5);
		container2.add(jb6);
		container2.add(jb7);
		container2.add(jb8);

		//프레임에 컨테이너 올리기
		panel.add(container1,BorderLayout.NORTH);
		panel.add(jsp,BorderLayout.CENTER);
		panel.add(container2,BorderLayout.SOUTH);

		//프레임 세팅 
		panel.setBounds(100,100,743,567);
		panel.setVisible(true);
		
		SetPageNum2(1);
		
		
		selectCus(1);
		
		jb3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);   // 전체 테이블 화면을 지워주는 메서드.
				selectCus(Integer.parseInt(jb3.getText().toString()));
			}
		});
		
		jb4.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
				selectCus(Integer.parseInt(jb4.getText().toString()));
			}
		});

		jb5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
				selectCus(Integer.parseInt(jb5.getText().toString()));
			}
		});

		jb6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
				selectCus(Integer.parseInt(jb6.getText().toString()));
			}
		});

		jb7.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
				selectCus(Integer.parseInt(jb7.getText().toString()));
			}
		});
		
		jb9.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
				cri.setPageNum(Integer.parseInt(jb3.getText().toString())-1);
				selectCus(Integer.parseInt(jb3.getText().toString())-1);
				SetPageNum2(Integer.parseInt(jb3.getText().toString())-1);
			}
		});
		
		jb8.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
				cri.setPageNum(Integer.parseInt(jb7.getText().toString())+1);
				selectCus(Integer.parseInt(jb7.getText().toString())+1);
				SetPageNum2(Integer.parseInt(jb7.getText().toString())+1);
			
			}
		});
		
		
		
		jbt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
			
				ArrayList<Object[]> list;
				
				try {
					list = cusDB.selectCustomerWithSerch(Integer.valueOf(jtfd.getText().toString()));
					for(Object[] arr :list) {
						model9.addRow(arr);
					}	
					
				} catch (NumberFormatException e1) {
					model9.setRowCount(0);
					
					SetPageNum2(1);
					selectCus(1);;
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
					
				
				jb8.setVisible(false);
				jb3.setText("1");
				jb4.setVisible(false);
				jb5.setVisible(false);
				jb6.setVisible(false);
				jb7.setVisible(false);
			}
			});
		
		
		jbt2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model9.setRowCount(0);
			
				SetPageNum2(1);
				selectCus(1);;
			}
			});


		return panel;
	}

	private void loadEmployeeData(DefaultTableModel tableModel) {
		connect();
		sql = "SELECT emp_id, emp_pw, emp_name FROM employee";

		try {
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("emp_id");
				String pw = rs.getString("emp_pw");
				String name = rs.getString("emp_name");
				tableModel.addRow(new Object[] { id, pw, name });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void showAddEmployeeDialog(DefaultTableModel tableModel) {
		JTextField idField = new JTextField(10);
		JTextField pwField = new JTextField(10);
		JTextField nameField = new JTextField(10);

		JPanel inputPanel = new JPanel();
		inputPanel.add(new JLabel("ID:"));
		inputPanel.add(idField);
		inputPanel.add(new JLabel("PW:"));
		inputPanel.add(pwField);
		inputPanel.add(new JLabel("Name:"));
		inputPanel.add(nameField);

		// int result = JOptionPane.showInternalConfirmDialog(frame, inputPanel, "직원
		// 추가", JOptionPane.OK_CANCEL_OPTION);
		// if (result == JOptionPane.OK_OPTION) {
		// String id = idField.getText();
		// String pw = pwField.getText();
		// String name = nameField.getText();

		// addEmployeeToDatabase(id, pw, name);
		// tableModel.addRow(new Object[]{id, pw, name});
	}

	private void addEmployeeToDatabase(String id, String pw, String name) {
		connect();
		sql = "INSERT INTO employee (emp_id, emp_pw, emp_name) VALUES (?, ?, ?)";

		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			pstmt.setString(3, name);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void CalendarFunction() {

		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONDAY);
		System.out.println("CalendarFunction year" + this.year);
	}

	public void setButton(JButton[] buttons) {
		this.buttons = buttons;
	}

	// Lable -> 0000년 00월 문자열 리턴
	public String getCalText() {
		System.out.println("getCalText year" + this.year);
		return year + "년" + month + "월";

	}

	// 버튼 날자 출력
	public void calSet() {
		// calendar 객체 날짜 1일 설정
		cal.set(year, month - 1, 1);

		// 그 달의 1일 요일 수
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		// 요일 수 1일 시작, 배열 0부터 시작
		firstDay--;

		for (int i = 1; i <= cal.getActualMaximum(cal.DATE); i++) {
			// buttons[1]~[6] : 일~ 토
			// buttons[7] ~ : 날짜 출력
			String srt = select(String.valueOf(i));

			// 이차 수정 buttons[6 + firstDay + i].setText(String.valueOf(i));
			buttons[6 + firstDay + i].setText(String.valueOf(srt));
		}
	}

	// 달력 새로운 년월 출력
	public void allInit(int gap) {
		// 버튼 날짜 지우기
		for (int i = 7; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		month += gap;
		if (month <= 0) {
			year--;
			month = 12;
		} else if (month >= 13) {
			year++;
			month = 1;
		}
		calSet();
	}

	// implements ActionListener에 의한 메소드 .
	@Override
	public void actionPerformed(ActionEvent e) {
		String day = null;
		orderDAO = new OrderDAO();
		JButton b = (JButton) e.getSource();
		String str = b.getText();
		System.out.println(str);
		if (str == null || str.isEmpty()) {
			return;
		} else {
			 String startTag = "<span style='font-size: 12px; font-family: 고딕;'>";
		        int start = str.indexOf(startTag) + startTag.length(); // 시작 위치
		        int end = str.indexOf("일", start); // "일" 위치

		        // 숫자 추출
		         day = str.substring(start, end).trim(); // 공백 제거
		        System.out.println(day); // 출력: 4
			}
		
		try {
			Orders2DTO dto2  = orderDAO.getOrder(String.valueOf(year), String.valueOf(month), day);
			view.show(dto2);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	// DB를 연동하는 메서드
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

	
	private String formatCurrency(int amount) {
	    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
	    return currencyFormatter.format(amount).replace("₩", ""); // ₩ 제거 후 반환
	}
	
	
	String select(String date1) {
	    connect();

	    String str = "";
	    try {
	        sql = "SELECT " +
	              "SUM(CASE WHEN card_payment = '0' THEN total_price ELSE 0 END) AS total_cash_payment, " +
	              "SUM(CASE WHEN card_payment = '1' THEN total_price ELSE 0 END) AS total_card_payment, " +
	              "SUM(total_price) AS total_price_sum " +
	              "FROM orders " +
	              "WHERE TRUNC(order_date) = TO_DATE(?, 'YYYY-MM-DD')";

	        pstmt = con.prepareStatement(sql);
	        String date2 = year + "/" + month + "/" + date1;
	        System.out.println("date2:" + date2);
	        pstmt.setString(1, date2);

	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int total_cash_payment = rs.getInt("total_cash_payment");
	            int total_card_payment = rs.getInt("total_card_payment");
	            int total_price_sum = rs.getInt("total_price_sum");

	            str = "<html>" +
	                  "<div style='text-align: center;'>" +
	                  "<span style='font-size: 12px; font-family: 고딕;'>" + date1 + "일</span><br>" +
	                  "<span style='font-size: 10px; font-family: 고딕;'>총: " + formatCurrency(total_price_sum) + "</span><br>" +
	                  "<span style='font-size: 9px; font-family: 고딕;'>현금: " + formatCurrency(total_cash_payment) + "</span><br>" +
	                  "<span style='font-size: 9px; font-family: 고딕;'>카드: " + formatCurrency(total_card_payment) + "</span>" +
	                  "</div></html>";

	           
	            return str;
	        }

	        rs.close();
	        pstmt.close();
	        con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return str;
	}

	//직원 조회 관련 메소드 추가 ..
	
	public int selectTotal() {
		int total=0;
	
		try {
			sql = "select count(cus_id) as a from customer" ;
			pstmt = cusDB.con.prepareStatement(sql);
			rs = pstmt.executeQuery();

		
			
			while(rs.next()) {
				total = rs.getInt("a");
				System.out.println(total);
				
			}
		
			rs.close(); pstmt.close(); 
			
		}catch(Exception e) {
			e.getMessage();
		}
		return total;
	}
	
	
	public void SetPageNum2(int pagenum) {
		int total = selectTotal();
		cri.setPageNum(pagenum);
		PageVO page = new PageVO(cri,total);
		
		//page.getCri().setPageNum(pagenum);
		
		if(page.isPrev() != true) {
			jb9.setVisible(false);
		}else {
			jb9.setVisible(true);
		}
		
		if(page.isNext() != true) {
			jb8.setVisible(false);
		}else {
			jb8.setVisible(true);
		}
		
		jb4.setVisible(true);
		jb5.setVisible(true);
		jb6.setVisible(true);
		jb7.setVisible(true);
		
		//버튼 번호 세팅
		int start =  page.getStartPge();
		jb3.setText(String.valueOf(start));
		
		int j =4;
		
		if(start == page.getEndPage()) {
			jb4.setVisible(false);
			jb5.setVisible(false);
			jb6.setVisible(false);
			jb7.setVisible(false);
		}else {
		
		for(int i=1; i<=((page.getEndPage()-start)+1);i++) {
			String name = "jb";
			name += j;	
			System.out.println(name);
			
			
		if(start+i == page.getEndPage()) {
		
			switch(name){
			case "jb4" :
				jb4.setText(String.valueOf(start+i));
				jb5.setVisible(false);
				jb6.setVisible(false);
				jb7.setVisible(false);
				break;
			case "jb5" :
				jb5.setText(String.valueOf(start+i));
				jb6.setVisible(false);
				jb7.setVisible(false);
				break;	
			case "jb6" :
				jb6.setText(String.valueOf(start+i));
				jb7.setVisible(false);
				break;
			case "jb7" :
				jb7.setText(String.valueOf(start+i));
				break;
				}
		}else {
		
		switch(name){
		case "jb4" :
			jb4.setText(String.valueOf(start+i));
			break;
		case "jb5" :
			jb5.setText(String.valueOf(start+i));
			break;	
		case "jb6" :
			jb6.setText(String.valueOf(start+i));
			break;
		case "jb7" :
			jb7.setText(String.valueOf(start+i));
			break;
			}
		}
		name =null;
		j++;
		
		
		}		
		}
	}
	
	public void selectCus(int pagenum) {
		cusDB.setPageNum(pagenum);
		
	
		ArrayList<Object[]> list  = cusDB.selectCustomer();
;
		
		for(Object[] arr :list) {
			model9.addRow(arr);
		}		
		
		//SetPageNum(cri.getPageNum());
	}
}
