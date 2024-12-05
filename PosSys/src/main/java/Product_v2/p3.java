package Product_v2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.management.StringValueExp;
import javax.swing.*;
import javax.swing.event.*;
import java.net.*;
import javax.swing.table.*;
import javax.swing.text.*;
import OrderPage.*;
import java.sql.*;
import java.util.*;

public class p3 implements ActionListener {
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

	private int cus_pointUsed; // 마일리지 사용할 때 사용.
	private int totalPrice; // 총 가격.
	private int finalPrice; // 마일리지 차감 후 지불해야 하는 가격.
	DefaultTableModel newModel2;
	
	CafeDAO dao = new CafeDAO();
	PointView pointView = new PointView();// 적립, 결제 관련 view 메소드 클래스.

	CustomerDTO cusDTO = new CustomerDTO();
	OrdersDTO orderDTO = new OrdersDTO();
	MiroticDTO miroticDTO = new MiroticDTO();
	CardDTO cardDTO = new CardDTO();

	//Pay_Card_View pay = new Pay_Card_View();
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					p3 window = new p3();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public p3() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
	    frame = new JFrame();
	    frame.setTitle("메뉴판");
	    frame.setBounds(100, 100, 980, 630);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    frame.getContentPane().setLayout(new GridLayout(1, 0));

	    JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	    frame.getContentPane().add(tabbedPane);

	    // 첫 번째 탭: 커피
	    JPanel panel = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            try {
	                ImageIcon icon = new ImageIcon(getClass().getResource("/coffee/커피배경.png"));
	                Image img = icon.getImage();
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            } catch (Exception e) {
	                System.out.println("이미지를 로드할 수 없습니다: " + e.getMessage());
	            }
	        }
	    };
	    panel.setBackground(Color.WHITE);
	    tabbedPane.addTab("커피", null, panel, null);

	    // 두 번째 탭: 파스타
	    JPanel panel_1 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            try {
	                ImageIcon icon = new ImageIcon(getClass().getResource("/pasta/파스타배경.png"));
	                Image img = icon.getImage();
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            } catch (Exception e) {
	                System.out.println("이미지를 로드할 수 없습니다: " + e.getMessage());
	            }
	        }
	    };
	    panel_1.setBackground(Color.WHITE);
	    tabbedPane.addTab("파스타", null, panel_1, null);

	    // 세 번째 탭: 음료
	    JPanel panel_2 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            try {
	                ImageIcon icon = new ImageIcon(getClass().getResource("/drink/음료배경.png"));
	                Image img = icon.getImage();
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            } catch (Exception e) {
	                System.out.println("이미지를 로드할 수 없습니다: " + e.getMessage());
	            }
	        }
	    };
	    panel_2.setBackground(Color.WHITE);
	    tabbedPane.addTab("음료", null, panel_2, null);

	    // 네 번째 탭: 디저트
	    JPanel panel_3 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            try {
	                ImageIcon icon = new ImageIcon(getClass().getResource("/dessert/디저트배경.png"));
	                Image img = icon.getImage();
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            } catch (Exception e) {
	                System.out.println("이미지를 로드할 수 없습니다: " + e.getMessage());
	            }
	        }
	    };
	    panel_3.setBackground(Color.WHITE);
	    tabbedPane.addTab("디저트", null, panel_3, null);

	    // 다섯 번째 탭: 추가 탭 (예시)
	    JPanel panel_4 = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            try {
	                ImageIcon icon = new ImageIcon(getClass().getResource("/side/사이드배경.png"));
	                Image img = icon.getImage();
	                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	            } catch (Exception e) {
	                System.out.println("이미지를 로드할 수 없습니다: " + e.getMessage());
	            }
	        }
	    };
	    panel_4.setBackground(Color.WHITE);
	    tabbedPane.addTab("사이드", null, panel_4, null);
	    
	    panel.setBackground(Color.WHITE);
	    tabbedPane.addTab("커피", null, panel, null);
	    panel.setLayout(null);
		
		// 라떼 동적으로 올리기.(시작 부분)
		JButton btnNewButton_3 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/coffee/카페라떼.jpg"); // 리소스 경로 설정
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
		// 라떼 동적으로 올리기. (끝 부분)

		btnNewButton_3.setBounds(70, 300, 120, 120);
		panel.add(btnNewButton_3);

		JButton btnNewButton_4 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/coffee/카라멜 마끼아또.jpg"); // 리소스 경로 설정
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

		btnNewButton_4.setBounds(280, 300, 120, 120);
		panel.add(btnNewButton_4);

		JButton btnNewButton_2 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/coffee/아이스 아메리카노.jpg"); // 리소스 경로 설정
			if (imageUrl != null) {
				ImageIcon icon = new ImageIcon(imageUrl);
				btnNewButton_2.setIcon(icon);
			} else {
				System.out.println("이미지를 찾을 수 없습니다!");
			}
		} catch (Exception e) {
			System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
		}
		btnNewButton_2.setText("아이스 아메리카노");

		btnNewButton_2.setBounds(70, 130, 120, 120);
		panel.add(btnNewButton_2);

		JButton btnNewButton_2_1 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/coffee/카페라떼.jpg"); // 리소스 경로 설정
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

		btnNewButton_2_1.setBounds(280, 130, 120, 120);
		panel.add(btnNewButton_2_1);

		JTextPane textPane = new JTextPane();
		textPane.setBackground(Color.WHITE);
		textPane.setFont(new Font("굴림", Font.BOLD, 12));
		textPane.setText("  아이스 아메리카노\r\n        2,500원");
		textPane.setBounds(70, 250, 120, 42);
		panel.add(textPane);

		JTextPane textPane_1 = new JTextPane();
		textPane_1.setText("    바닐라 라떼\r\n       3,500원");
		textPane_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_1.setBounds(280, 250, 120, 42);
		panel.add(textPane_1);

		JTextPane textPane_2 = new JTextPane();
		textPane_2.setText("       카페라떼\r\n        3,500원");
		textPane_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_2.setBounds(70, 420, 120, 42);
		panel.add(textPane_2);

		JTextPane textPane_2_1 = new JTextPane();
		textPane_2_1.setText("   카라멜 마끼아또\r\n        4,000원");
		textPane_2_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_2_1.setBounds(280, 420, 120, 42);
		panel.add(textPane_2_1);
		
		panel_1.setBackground(Color.WHITE);
		tabbedPane.addTab("파스타", null, panel_1, null);
		panel_1.setLayout(null);

		JButton btnNewButton_2_2 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/pasta/토마토 파스타.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_2.setBounds(70, 130, 120, 120);
		panel_1.add(btnNewButton_2_2);

		JButton btnNewButton_2_3 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/pasta/라구 파스타.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_3.setBounds(280, 130, 120, 120);
		panel_1.add(btnNewButton_2_3);

		JButton btnNewButton_2_4 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/pasta/바질페스토 파스타.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_4.setBounds(70, 300, 120, 120);
		panel_1.add(btnNewButton_2_4);

		JButton btnNewButton_2_5 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/pasta/알리오올리오.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_5.setBounds(280, 300, 120, 120);
		panel_1.add(btnNewButton_2_5);

		JTextPane textPane_3 = new JTextPane();
		textPane_3.setText("    토마토 파스타\r\n        7,000원");
		textPane_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3.setBounds(70, 250, 120, 42);
		panel_1.add(textPane_3);

		JTextPane textPane_4 = new JTextPane();
		textPane_4.setText("     라구 파스타\r\n        8,500원");
		textPane_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_4.setBounds(280, 250, 120, 42);
		panel_1.add(textPane_4);

		JTextPane textPane_5 = new JTextPane();
		textPane_5.setText("  바질페스토 파스타\r\n        8,000원");
		textPane_5.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_5.setBounds(70, 420, 120, 42);
		panel_1.add(textPane_5);

		JTextPane textPane_6 = new JTextPane();
		textPane_6.setText("     알리오올리오\r\n         8,500원");
		textPane_6.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_6.setBounds(280, 420, 120, 42);
		panel_1.add(textPane_6);

		
		panel_2.setBackground(Color.WHITE);
		tabbedPane.addTab("음료", null, panel_2, null);
		panel_2.setLayout(null);

		JButton btnNewButton_2_6 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/drink/레몬에이드.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6.setBounds(70, 130, 120, 120);
		panel_2.add(btnNewButton_2_6);

		JButton btnNewButton_2_7 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/drink/블루에이드.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_7.setBounds(280, 130, 120, 120);
		panel_2.add(btnNewButton_2_7);

		JButton btnNewButton_2_8 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/drink/사이다.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_8.setBounds(70, 300, 120, 120);
		panel_2.add(btnNewButton_2_8);

		JButton btnNewButton_2_9 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/drink/콜라.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_9.setBounds(280, 300, 120, 120);
		panel_2.add(btnNewButton_2_9);

		JTextPane textPane_3_1 = new JTextPane();
		textPane_3_1.setText("     레몬 에이드\r\n        4,000원");
		textPane_3_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1.setBounds(70, 250, 120, 42);
		panel_2.add(textPane_3_1);

		JTextPane textPane_3_2 = new JTextPane();
		textPane_3_2.setText("    블루 에이드\r\n        4,000원");
		textPane_3_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_2.setBounds(280, 250, 120, 42);
		panel_2.add(textPane_3_2);

		JTextPane textPane_3_3 = new JTextPane();
		textPane_3_3.setText("        사이다\r\n        2,000원");
		textPane_3_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_3.setBounds(70, 420, 120, 42);
		panel_2.add(textPane_3_3);

		JTextPane textPane_3_4 = new JTextPane();
		textPane_3_4.setText("          콜라\r\n        2,000원");
		textPane_3_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_4.setBounds(280, 420, 120, 42);
		panel_2.add(textPane_3_4);

		
		panel_3.setBackground(Color.WHITE);
		tabbedPane.addTab("디저트", null, panel_3, null);
		panel_3.setLayout(null);

		JButton btnNewButton_2_6_1 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/dessert/치즈 케이크.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_1.setBounds(70, 130, 120, 120);
		panel_3.add(btnNewButton_2_6_1);

		JButton btnNewButton_2_6_2 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/dessert/초코 케이크.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_2.setBounds(280, 130, 120, 120);
		panel_3.add(btnNewButton_2_6_2);

		JButton btnNewButton_2_6_3 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/dessert/에그타르트.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_3.setBounds(70, 300, 120, 120);
		panel_3.add(btnNewButton_2_6_3);

		JButton btnNewButton_2_6_4 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/dessert/휘낭시에.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_4.setBounds(280, 300, 120, 120);
		panel_3.add(btnNewButton_2_6_4);

		JTextPane textPane_3_1_2 = new JTextPane();
		textPane_3_1_2.setText("     초코 케이크\r\n        4,000원");
		textPane_3_1_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2.setBounds(280, 250, 120, 42);
		panel_3.add(textPane_3_1_2);

		JTextPane textPane_3_1_3 = new JTextPane();
		textPane_3_1_3.setText("     에그 타르트\r\n        2,500원");
		textPane_3_1_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_3.setBounds(70, 420, 120, 42);
		panel_3.add(textPane_3_1_3);

		JTextPane textPane_3_1_4 = new JTextPane();
		textPane_3_1_4.setText("      휘낭시에\r\n        2,000원");
		textPane_3_1_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_4.setBounds(280, 420, 120, 42);
		panel_3.add(textPane_3_1_4);

		JTextPane textPane_3_1_2_1 = new JTextPane();
		textPane_3_1_2_1.setText("     치즈 케이크\r\n        4,000원");
		textPane_3_1_2_1.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_1.setBounds(70, 250, 120, 42);
		panel_3.add(textPane_3_1_2_1);

		
		panel_4.setBackground(new Color(240, 255, 255));
		tabbedPane.addTab("사이드", null, panel_4, null);
		panel_4.setLayout(null);

		JButton btnNewButton_2_6_1_1 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/side/리코타샐러드.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_1_1.setBounds(70, 130, 120, 120);
		panel_4.add(btnNewButton_2_6_1_1);

		JButton btnNewButton_2_6_1_2 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/side/케이준치킨샐러드.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_1_2.setBounds(280, 130, 120, 120);
		panel_4.add(btnNewButton_2_6_1_2);

		JButton btnNewButton_2_6_1_3 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/side/양송이 크림스프.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_1_3.setBounds(70, 300, 120, 120);
		panel_4.add(btnNewButton_2_6_1_3);

		JButton btnNewButton_2_6_1_4 = new JButton("New button");
		try {
			URL imageUrl = p3.class.getResource("/side/감자튀김.jpg"); // 리소스 경로 설정
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
		btnNewButton_2_6_1_4.setBounds(280, 300, 120, 120);
		panel_4.add(btnNewButton_2_6_1_4);

		JTextPane textPane_3_1_2_2 = new JTextPane();
		textPane_3_1_2_2.setText("     리코타샐러드\r\n          8,000원");
		textPane_3_1_2_2.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_2.setBounds(70, 250, 120, 42);
		panel_4.add(textPane_3_1_2_2);

		JTextPane textPane_3_1_2_3 = new JTextPane();
		textPane_3_1_2_3.setText("  케이준치킨샐러드\r\n          9,000원");
		textPane_3_1_2_3.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_3.setBounds(280, 250, 120, 42);
		panel_4.add(textPane_3_1_2_3);

		JTextPane textPane_3_1_2_4 = new JTextPane();
		textPane_3_1_2_4.setText("   양송이 크림스프\r\n         4,000원");
		textPane_3_1_2_4.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_4.setBounds(70, 420, 120, 42);
		panel_4.add(textPane_3_1_2_4);

		JTextPane textPane_3_1_2_5 = new JTextPane();
		textPane_3_1_2_5.setText("       감자튀김\r\n        2,500원");
		textPane_3_1_2_5.setFont(new Font("굴림", Font.BOLD, 12));
		textPane_3_1_2_5.setBounds(280, 420, 120, 42);
		panel_4.add(textPane_3_1_2_5);

		// 좌측 그리드 세팅
		JPanel panel1 = new JPanel();
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

		// 수량조절
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
		spinner.setBounds(588, 369, 42, 69);
		frame.getContentPane().add(spinner);

		// 테이블에서 행이 선택될 때, 선택된 상품의 수량을 JSpinner에 업데이트
		table.getSelectionModel().addListSelectionListener(e -> {
			// 선택된 행이 있으면 JSpinner 값 업데이트
			int selectedRow = table.getSelectedRow();
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

					// 총액 계산
					Object unitPriceObj = model.getValueAt(selectedRow, 1);
					if (unitPriceObj instanceof Number) {
						int unitPrice = ((Number) unitPriceObj).intValue();
						int totalPrice = unitPrice * quantity;
						model.setValueAt(totalPrice, selectedRow, 3);

					}
					updateTotalNum();

				}

			}
		});

		JButton btnNewButton_6 = new JButton("상품삭제");// 상품삭제버튼
		btnNewButton_6.setBounds(630, 369, 85, 69);
		frame.getContentPane().add(btnNewButton_6);

		btnNewButton_6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = table.getSelectedRow(); // 선택된 행 인덱스
				if (selectedRow != -1) {
					// JTable에서 행 삭제 전 해당 행의 총액 값 가져오기
					Object totalObj = model.getValueAt(selectedRow, 3); // 총액 컬럼 값
					if (totalObj instanceof Number) {
						int totalValue = ((Number) totalObj).intValue();
						totalnum -= totalValue; // 삭제된 상품의 총액만큼 totalnum에서 빼기
					}

					// 모델에서 해당 행 삭제
					model.removeRow(selectedRow);

					// 총액 갱신
					updateTotalNum(); // updateTotalNum()을 호출하여 totalnum을 다시 계산

					// 테이블을 새로 고침
					model.fireTableDataChanged(); // model 갱신
					newModel2.fireTableDataChanged(); // model2 갱신

				} else {
					JOptionPane.showMessageDialog(frame, "행을 선택하세요.");
				}
			}
		});

		// spinner.setSize(new Dimension(40, 80));
		spinner.setPreferredSize(new Dimension(40, 80));

		panel0.add(jsp2);
		panel0.add(spinner);
		panel0.add(btnNewButton_6);

		JButton btnNewButton = new JButton("현금결제");
		btnNewButton.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnNewButton.setBounds(479, 448, 97, 46);
		// frame.getContentPane().add(btnNewButton);

		JButton btnNewButton_1 = new JButton("카드결제");
		btnNewButton_1.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnNewButton_1.setBounds(361, 448, 97, 46);
		// frame.getContentPane().add(btnNewButton_1);

		JButton btnNewButton_5 = new JButton("할인조회");
		btnNewButton_5.setFont(new Font("나눔고딕", Font.BOLD, 16));
		btnNewButton_5.setBounds(596, 448, 97, 46);
		// frame.getContentPane().add(btnNewButton_5);

		panel1.add(btnNewButton);
		panel1.add(btnNewButton_1);
		panel1.add(btnNewButton_5);

		panel2.add(jsp, BorderLayout.NORTH);
		panel2.add(panel0, BorderLayout.CENTER);
		panel2.add(panel1, BorderLayout.SOUTH);

		frame.getContentPane().add(panel2);

		// 카드결제 리스너
		btnNewButton_1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
			//	int orderId = 0;
				Pay_Card_View pay = new Pay_Card_View();
				pay.Pay((totalnum - cus_pointUsed));
				
				System.out.println("마지막 j : " + pay.j );
				
				cardStep(pay);
				


			}
		});

		// 버튼에 이벤스 리스너 세팅(커피)
		btnNewButton_3.addActionListener(this);
		btnNewButton_4.addActionListener(this);
		btnNewButton_2.addActionListener(this);
		btnNewButton_2_1.addActionListener(this);

		// 버튼에 이벤스 리스너 세팅(파스타)
		btnNewButton_2_2.addActionListener(this);
		btnNewButton_2_3.addActionListener(this);
		btnNewButton_2_4.addActionListener(this);
		btnNewButton_2_5.addActionListener(this);

		// 버튼에 이벤스 리스너 세팅(음료)
		btnNewButton_2_6.addActionListener(this);
		btnNewButton_2_7.addActionListener(this);
		btnNewButton_2_8.addActionListener(this);
		btnNewButton_2_9.addActionListener(this);

		// 버튼에 이벤스 리스너 세팅(디저트)
		btnNewButton_2_6_1.addActionListener(this);
		btnNewButton_2_6_2.addActionListener(this);
		btnNewButton_2_6_3.addActionListener(this);
		btnNewButton_2_6_4.addActionListener(this);

		// 버튼에 이벤스 리스너 세팅(사이드)
		btnNewButton_2_6_1_1.addActionListener(this);
		btnNewButton_2_6_1_2.addActionListener(this);
		btnNewButton_2_6_1_3.addActionListener(this);
		btnNewButton_2_6_1_4.addActionListener(this);

		// 할인조회버튼 이벤트 리스너 세팅
		btnNewButton_5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 고객 번호 받은 인자를 쿼리문 메서드로 넘겨서 조회된 포인트 리턴,id (CustomerDTO) 받음.
				try {
					String phone = null;
					phone = pointView.checkCus_point();
					
					if(phone == null || phone.trim().isEmpty()) {
					    JOptionPane.showMessageDialog(frame, "유효한 전화번호를 입력해주세요.");
					    return;
					
					}else {
					cusDTO = dao.getCus_point(phone);
					// cusDTO에 조회한 고객 id, 마일리지 저장되어 있음.
					System.out.println();
					
					if (cusDTO.getCus_id() == null && phone != null && !phone.trim().isEmpty()) {
						System.out.println("타냐1? ");
						dao.insertCus(phone);
						JOptionPane.showMessageDialog(frame, "등록되지 않는 고객으로 가입작업 완료하였습니다.");
						cusDTO = dao.getCus_point(phone);
					}
					}
					// 조회된 고객 정보 cusDTO로 담아둠

					JOptionPane.showMessageDialog(frame, "현재 마일리지: " + cusDTO.getCus_point() + "점");

					// 마일리지 사용 여부 묻기
					int useMileageOption = JOptionPane.showConfirmDialog(frame, "마일리지를 사용하시겠습니까?", "마일리지 사용",
							JOptionPane.YES_NO_OPTION);

					if (useMileageOption == JOptionPane.YES_OPTION) {
						String mileageInput = JOptionPane.showInputDialog(frame,
								"사용할 마일리지 점수를 입력하세요 (최대 " + cusDTO.getCus_point() + "점):");

						try {
							int requestedMileage = Integer.parseInt(mileageInput);

							if (requestedMileage > cusDTO.getCus_point()) {
								JOptionPane.showMessageDialog(frame, "보유한 마일리지보다 많이 입력하셨습니다.");
								return;
							} else if (requestedMileage < 0) {
								JOptionPane.showMessageDialog(frame, "0 이상의 값을 입력해주세요.");
								return;
							} else {
								cus_pointUsed = requestedMileage; // 사용 마일리지 설정
								System.err.println("계산전 finalPrice : " + finalPrice);
								finalPrice = totalnum - cus_pointUsed; // 최종 결제 금액 계산
								System.err.println("계산후 finalPrice : " + finalPrice);
							}

							JOptionPane.showMessageDialog(frame, "마일리지 차감된 최종 결제 금액: " + finalPrice + "원");
							updateTotalNum();
						} catch (NumberFormatException e2) {
							JOptionPane.showMessageDialog(frame, "숫자를 입력해주세요.");
						}
					} else {
						// 마일리지 사용 안 함
						cus_pointUsed = 0;
						finalPrice = totalPrice; // 최종 결제 금액은 총 금액 그대로
						JOptionPane.showMessageDialog(frame, "마일리지를 사용하지 않습니다.");
					}

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		// 현금 결제 버튼 이벤트 리스너 세팅
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int orderId = 0;

				int getCash = Integer.parseInt(pointView.PayCash()); // 거스름돈 표시에 사용: 총금액 - 받은금액

				pointView.GiveCash(getCash, (totalnum - cus_pointUsed)); // 화면표시 : 거스름돈

				dao.insertOrder(cusDTO, (totalnum - cus_pointUsed), cus_pointUsed);

				try {
					orderId = dao.selectOrder();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// model1에서 셀마다 데이터 받아오는 걸로. for문돌려서 아래 메서드 호출해야 겠다.
				// dao.insertOrderDetail(orderId,수량.., 상품id );
				try {
					callDatail(orderId);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} // 해당 클래스에 메소드 추가.

				
				model.setRowCount(0);
				newModel2.setRowCount(0);
			
				System.out.println("cusDTO.getCus_id(): "+ cusDTO.getCus_id()+", cus_pointUsed :" + cus_pointUsed);
				if (cus_pointUsed == 0 && cusDTO.getCus_id() != null) {
				    System.out.println("조건 충족: 결제 후 적립 프로세스 실행");
				    System.out.println("cus_pointUsed: " + cus_pointUsed);
				    System.out.println("cusDTO.getCus_id(): " + cusDTO.getCus_id());

				    int rewardPoints = (int) ((totalnum - cus_pointUsed) * 0.1);
				    System.out.println("적립된 포인트: " + rewardPoints + "점");

				    dao.updatePoint(cusDTO, rewardPoints);
				} else {
				    System.out.println("조건 미충족: 포인트 적립 미실행");
				    System.out.println("cus_pointUsed: " + cus_pointUsed);
				    System.out.println("cusDTO.getCus_id(): " + cusDTO.getCus_id());

				    dao.updateCus(cusDTO, cus_pointUsed); // 사용한 마일리지 차감.
				}
			
				cusDTO = new CustomerDTO(); //주문 단위 끝날때 고객 정보 초기화 
			
			}
		});

	}

	public void callDatail(int orderId) throws SQLException {
		Object itemName = null;
		int proId = 0;
		int vol = 0;

		for (int i = 0; i < model.getRowCount(); i++) {
			itemName = model.getValueAt(i, 0); // 첫 번째 열에서 아이템 이름 가져오기

			try {
				// 상품 ID 가져오기
				proId = dao.selectProId((String) itemName);
			} catch (SQLException e) {
				System.err.println("Error fetching product ID for item: " + itemName);
				e.printStackTrace();
				continue; // 다음 반복으로 이동
			}

			// 수량 가져오기
			try {
				vol = (int) model.getValueAt(i, 2); // 세 번째 열에서 수량 가져오기
			} catch (ClassCastException e) {
				System.err.println("Error casting quantity to int for item: " + itemName);
				e.printStackTrace();
				continue; // 다음 반복으로 이동
			}

			// 세부 정보 삽입
			dao.insertDetail(orderId, proId, vol);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		connect();
		/*
		 * String ori = e.getSource().toString(); System.out.println("ori"+ ori);
		 * 
		 * int jpgIndex = ori.indexOf(".jpg"); // .jpg의 시작 위치 찾기 String beforeJpg =
		 * ori.substring(0, jpgIndex); // .jpg 앞부분 추출
		 * 
		 * int lastBackslashIndex = beforeJpg.lastIndexOf("\\"); // 마지막 \의 위치 찾기
		 * 
		 * String result = beforeJpg.substring(lastBackslashIndex + 1); // 마지막 \ 이후의 문자열
		 * 반환 System.out.println(result); select(result);
		 */

		JButton target = (JButton) e.getSource();
		String ori = target.getText();
		System.out.println("ori" +ori);

		boolean value = IsNamed(ori);
		if (!value ) {
			select(ori);
		} 

	}

	boolean IsNamed(String ori) {
		String name= null;
		for (int i = 0; i < model.getRowCount(); i++) {
			Object totalObj = model.getValueAt(i, 0);
			System.out.println("totalObj"+ totalObj);
			if (totalObj instanceof String) {
				 name = ((String) totalObj).toString();
				System.out.println("name"+ name.toString());
				System.out.println("ori"+ ori.toString());
				
				if (name.equals(ori)) {
					int vol = (Integer)model.getValueAt(i, 2);
					System.out.println("1");
					model.setValueAt(vol+1, i, 2);
					model.fireTableDataChanged();
					System.out.println("2");
					
					int unit = (Integer)model.getValueAt(i, 1);
					model.setValueAt((vol+1)*unit, i, 3);
					updateTotalNum();

					return true;
				}
			}
		}
		return false;

	}

	// DB를 연동하는 메서드
	void connect() {

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
	} // connect() 메서드 end

	private int totalnum = 0;

	// EMP 테이블의 전체 사원 목록을 조회하는 메서드.
	void select(String name) {

		try {
			// 1. 오라클 데이터베이스로 전송할 SQL문 작성.
			sql = "select * from product where pro_name = ? order by pro_type";

			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, name);

			// 2. 오라클 데이터베이스에 SQL문 전송 및 SQL문 실행.
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String pro_name = rs.getString("pro_name");
				int pro_price = rs.getInt("pro_price");
				int quantity = 1;
				int total = pro_price * quantity;

				totalnum += total;

				Object[] data = { pro_name, pro_price, quantity, total };

				// 저장된 한 개의 레코드(데이터)를 model에 추가해 주면 됨.
				model.addRow(data);

			}

			// 디버깅 로그 추가
			System.out.println("Data added to model. Rows: " + model.getRowCount());

			updateTotalNum();

			SwingUtilities.invokeLater(() -> {
				table2.invalidate();
				table2.validate();
				table2.repaint();
			});

			// 3. 오라클 데이터베이스에 연결되어 있던 자원 종료
			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} // select() 메서드 end

	private void updateTotalNum() {
		totalnum = 0; // 총합 초기화
		for (int i = 0; i < model.getRowCount(); i++) {
			Object totalObj = model.getValueAt(i, 3);
			if (totalObj instanceof Number) {
				totalnum += ((Number) totalObj).intValue();
				finalPrice = totalnum; // 마일리지 적립 버튼 클릭 안할 경우) 최종결제금액 변수 세팅 해둠
			}
		}

		// 기존 모델 데이터를 완전히 교체
		newModel2 = new DefaultTableModel(new String[] { "총 금 액", "사용 마일리지" }, 0);
		newModel2.addRow(new Object[] { totalnum, cus_pointUsed }); // 총합 추가

		table2.setModel(newModel2); // 테이블에 새 모델 설정

	}

	public void cardStep(Pay_Card_View pay) {
		   new Thread(() -> {
		        try {
		            // pay.plung이 true가 될 때까지 대기
		            while (!pay.plug) {
		                Thread.sleep(100); // 100ms 간격으로 확인
		            }
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }

		        // pay.plung이 true가 된 이후 실행
		        if (pay.j == 0) { // 결제 성공
		            dao.insertOrder(cusDTO, (totalnum - cus_pointUsed), cus_pointUsed);

		            int orderId = 0;
		            try {
		                orderId = dao.selectOrder();
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }

		            try {
		                callDatail(orderId);
		            } catch (SQLException e1) {
		                e1.printStackTrace();
		            }

		            model.setRowCount(0);
					newModel2.setRowCount(0);
				
					System.out.println("cusDTO.getCus_id(): "+ cusDTO.getCus_id()+", cus_pointUsed :" + cus_pointUsed);
					if (cus_pointUsed == 0 && cusDTO.getCus_id() != null) {
					    System.out.println("조건 충족: 결제 후 적립 프로세스 실행");
					    System.out.println("cus_pointUsed: " + cus_pointUsed);
					    System.out.println("cusDTO.getCus_id(): " + cusDTO.getCus_id());

					    int rewardPoints = (int) ((totalnum - cus_pointUsed) * 0.1);
					    System.out.println("적립된 포인트: " + rewardPoints + "점");

					    dao.updatePoint(cusDTO, rewardPoints);
					} else {
					    System.out.println("조건 미충족: 포인트 적립 미실행");
					    System.out.println("cus_pointUsed: " + cus_pointUsed);
					    System.out.println("cusDTO.getCus_id(): " + cusDTO.getCus_id());

					    dao.updateCus(cusDTO, cus_pointUsed); // 사용한 마일리지 차감.
					}
				
					cusDTO = new CustomerDTO(); //주문 단위 끝날때 고객 정보 초기화 
		            System.out.println("결제 및 적립 프로세스 완료");
		        } else { // 결제 실패
		            System.out.println("카드결제 실패");
		        }
		    }).start();
		}


	
	
}
