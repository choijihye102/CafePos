package Admin_management;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Admin_order.SelectOrder_View2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class Admin_managementView3 implements ActionListener {
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
		frame.setTitle("관리자 시스템");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);

		// 탭 패널 생성
		JTabbedPane tabbedPane = new JTabbedPane();

		// 정산 탭
		JPanel employeeTab = createEmployeeTab();
		tabbedPane.addTab("직원 관리", employeeTab);

		JPanel accountingTab = createAccountingTab();
		tabbedPane.addTab("정산 관리", accountingTab);

		// 직원 관리 탭

		// 메인 프레임에 탭 추가
		frame.add(tabbedPane);
		frame.pack();
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
			container3.add(buttons[i]);

			buttons[i].setFont(new Font("sansSerif", Font.BOLD, 24));

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

	
	

	private JPanel createEmployeeTab() {
		JPanel panel = new JPanel(new BorderLayout());
		
		  // 직원 테이블 생성
        String[] columnNames = {"직원 ID", "직원 PW", "직원 이름"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable employeeTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(employeeTable);

        // 데이터베이스에서 직원 데이터 로드
      //  loadEmployeeData(tableModel);

        JButton addButton = new JButton("직원 추가");
     //   addButton.addActionListener(e -> showAddEmployeeDialog(tableModel));

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.SOUTH);
        
        
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
	                tableModel.addRow(new Object[]{id, pw, name});
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

	//        int result = JOptionPane.showInternalConfirmDialog(frame, inputPanel, "직원 추가", JOptionPane.OK_CANCEL_OPTION);
	//        if (result == JOptionPane.OK_OPTION) {
	//            String id = idField.getText();
	 //           String pw = pwField.getText();
	//            String name = nameField.getText();

	         //   addEmployeeToDatabase(id, pw, name);
	  //          tableModel.addRow(new Object[]{id, pw, name});
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
		connect();

	}
	// DB를 연동하는 메서드
	void connect() {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "1234";
		
		System.out.println("연결1");
		try {
			// 1. 접속할 오라클 데이터베이스 드라이버를 메모리에 올리자. - 동적 작업
			System.out.println("연결2");
			Class.forName(driver);

			// 2. 오라클 데이터베이스와 연결을 시도.
			con = DriverManager.getConnection(url, user, password);
			System.out.println("연결3");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // connect() 메서드 end

	String select(String date1) {
		connect();
		
		String str="";
		try {
			// 1. 오라클 데이터베이스로 전송할 SQL문 작성.
			//sql = "SELECT SUM(cus_point) AS totalV FROM customer  WHERE created_date = ?";
			sql = " SELECT SUM(cus_point) AS totalV FROM customer WHERE TRUNC(created_date) = TO_DATE(?, 'YYYY-MM-DD')";

			pstmt = con.prepareStatement(sql);
			String date2 = year+"/"+month+"/"+date1;  //24/11/26
			System.out.println("date2:" +  date2);
		//	pstmt.setString(1, "2024-11-26");
			pstmt.setString(1, date2);

			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int totalV = rs.getInt("totalV");

				str ="<html>" +
	                     "<div style='text-align: left;'>" + // 왼쪽 정렬
	                     "<span style='font-size: 10px; font-family: 고딕;'>" + date1+ "일</span><br>" + // 큰 글씨
	                     "<span style='font-size: 8px; font-family: 고딕;'>총 금액 : 0원</span><br>" + // 작은 글씨
	                     "<span style='font-size: 8px; font-family: 고딕;'>현금 : " + totalV + "</span><br>" + // 작은 글씨
	                     "<span style='font-size: 8px; font-family: 고딕;'>카드 : " + totalV + "</span>" + // 작은 글씨
	                     "</div>" +
	                     "</html>";
						
				System.out.println("str : " +str);
				
			 return str;

			}

			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
