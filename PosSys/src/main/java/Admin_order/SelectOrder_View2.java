package Admin_order;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Product_v2.p2;

public class SelectOrder_View2 implements ActionListener {
	Connection con = null; // DB와 연결하는 객체
	PreparedStatement pstmt = null; // SQL문을 DB에 전송하는 객체
	ResultSet rs = null; // SQL문 실행 결과를 가지고 있는 객체
	String sql = null; // SQL문을 저장하는 문자열 변수.

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

					SelectOrder_View2 window = new SelectOrder_View2();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SelectOrder_View2() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("월별 정산 조회");
		frame.setVisible(true);

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

		frame.getContentPane().add(container2, BorderLayout.NORTH);
		frame.getContentPane().add(container3, BorderLayout.CENTER);
		// add(jsp, BorderLayout.CENTER);

		frame.pack();

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

	@Override
	public void actionPerformed(ActionEvent e) {
		connect();
	//....

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
