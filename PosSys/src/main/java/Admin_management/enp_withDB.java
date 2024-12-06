package Admin_management;

import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;

import javax.swing.JLabel;

public class enp_withDB extends JFrame {

	Connection con = null;                  // DB와 연결하는 객체
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;                      // SQL문을 저장하는 문자열 변수.
	
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	DefaultTableModel model;
	JTable table;
	private JTable table_1;
	JTextField jtf1, jtf2, jtf3, jtf4;
	JComboBox<String> jcb1, jcb2, jcb3;
	private JTable table_2;
	/**
	 * Launch the application.
	 */
	
	//1. DB 연결 메소드
		void connect() {
			String driver ="oracle.jdbc.driver.OracleDriver";
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String user = "system";
			String password = "1234";
			
			
			System.out.println("연결됐어요~1");
			try {
				//접속할 오라클 데이터베이스 드라이버를 메모리에 올리자. - 동적 작업
				System.out.println("연결됐어요~2");
				Class.forName(driver);
				
				//오라클 데이터베이스와 연결 시도
				con = DriverManager.getConnection(url, user, password);
				System.out.println("연결됐어요~");
				
			}catch(Exception e) {
				e.getMessage();
			}
		} // connect() 메서드 끝.
		
		
		//2. 고객 테이블 조회 메서드
		void selectCustomer() {
			try {
				// 오라클 데이터베이스에 전송할 sql문 작성
				
				sql =  "SELECT  ROW_NUMBER() OVER (ORDER BY created_date, cus_id) AS seq_num, cus_id,cus_phon,cus_point,created_date FROM customer";
						
				pstmt = con.prepareStatement(sql);		
				// 오라클 데이터베이스에 sql문 전송 및 sql문 실행
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					int seq_num = rs.getInt("seq_num");
					int cus_phon  = rs.getInt("cus_phon");
					int cus_point  = rs.getInt("cus_point");
					
				
					Object[] data = 
						{seq_num, cus_phon, cus_point};
					
					// 저장된 한 개의 레코드(데이터)를 model에 추가해 주면 됨.
					model.addRow(data);
				} // select() 메서드 끝
				
				// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
				rs.close(); pstmt.close(); con.close();
			}catch(SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//3. 정렬메서드
		//4. 페이징처리 포함된 조회 메서드 
		
		
		
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					enp_withDB frame = new enp_withDB();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public enp_withDB() {  //생성자 라인
		
		JPanel container1 = new JPanel();    // 상단-1 컨테이너
		JPanel container2 = new JPanel();    // 상단-2 컨테이너
		JPanel container3 = new JPanel();    // 하단 컨테이너
		
		// 1. 컴포넌트들을 만들어 보자.
		// 1-1. 상단-1 컨테이너에 들어갈 컴포넌트들을 만들어 보자.
		JLabel jl1 = new JLabel("사 번 : ");
		jtf1 = new JTextField(4);
		
		JLabel jl2 = new JLabel("이 름 : ");
		jtf2 = new JTextField(10);
		
		JLabel jl3 = new JLabel("담당업무 : ");
		jcb1 = new JComboBox<String>();
		
		// 1-2. 상단-2 컴테이너에 들어갈 컴포넌트들을 만들어 보자.
		JLabel jl4 = new JLabel("관리자 No. : ");
		jcb2 = new JComboBox<String>();
		
		JLabel jl5 = new JLabel("급 여 : ");
		jtf3 = new JTextField(5);
		
		JLabel jl6 = new JLabel("보너스 : ");
		jtf4 = new JTextField(5);
		
		JLabel jl7 = new JLabel("부서번호 : ");
		jcb3 = new JComboBox<String>();
		
		jcb1.addItem("선택"); jcb2.addItem("선택"); jcb3.addItem("선택");
		
		// 1-3. 중앙에 위치할 컴포넌트들을 만들어 보자.
		String[] header = 
			{"순번","번호","마일리지"};
		
		model = new DefaultTableModel(header, 0);
		
		table = new JTable(model);
		
		JScrollPane jsp = new JScrollPane(
				table, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		// 1-4. 하단 컨테이너에 올려질 컴포넌트들을 만들어 보자.
		JButton jb1 = new JButton("사원 목록");
		JButton jb2 = new JButton("사원 등록");
		JButton jb3 = new JButton("사원 수정");
		JButton jb4 = new JButton("사원 삭제");
		
		// 2. 컴포넌트들을 컨테이너에 올려주어야 한다.
		// 2-1. 상단-1 컨테이너에 1-1 컴포넌트들을 올려야 한다.
		container1.add(jl1); container1.add(jtf1);
		container1.add(jl2); container1.add(jtf2);
		container1.add(jl3); container1.add(jcb1);
		
		// 2-2. 상단-2 컨테이너에 1-2 컴포넌트들을 올려야 한다.
		container2.add(jl4); container2.add(jcb2);
		container2.add(jl5); container2.add(jtf3);
		container2.add(jl6); container2.add(jtf4);
		container2.add(jl7); container2.add(jcb3);
		
		// 2-3. 하단 컨테이너에 1-4 컴포넌트들을 올려야 한다.
		container3.add(jb1); container3.add(jb2);
		container3.add(jb3); container3.add(jb4);
		
		// 새로운 컨테이너 하나를 만들어서 기존의 컨테이너들을 올려 주자.
		JPanel group = new JPanel(new BorderLayout());
		
		group.add(container2, BorderLayout.NORTH);
		group.add(jsp, BorderLayout.CENTER);
		group.add(container3, BorderLayout.SOUTH);
		
		// 3. 컨테이너를 프레임에 올려주어야 한다.
		add(container1, BorderLayout.NORTH);
		add(group, BorderLayout.CENTER);
		
		setBounds(200, 200, 650, 350);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		jb1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				connect();              // 데이터베이스와 연결하는 메서드 호출.
			//	model.setRowCount(0);   // 전체 테이블 화면을 지워주는 메서드.
				selectCustomer();               // EMP 테이블의 전체 사원 목록을 조회하는 메서드.
				
			}
		});
				
	}
}
