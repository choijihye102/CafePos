package project;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JSpinner;

public class p2 {
	

	private JFrame frame;
	private JTextArea textArea;
	private JTable table;
	private JTable table_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					 
					p2 window = new p2();
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
	public p2() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("메뉴판");
		frame.setBounds(100, 100, 743, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 349, 528);
		frame.getContentPane().add(tabbedPane);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		tabbedPane.addTab("커피", null, panel, null);
		panel.setLayout(null);
		
		JButton btnNewButton_3 = new JButton("New button");
		btnNewButton_3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\카페라떼.jpg"));
		btnNewButton_3.setBounds(12, 255, 120, 120);
		panel.add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		btnNewButton_4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\카라멜 마끼아또.jpg"));
		btnNewButton_4.setBounds(176, 255, 120, 120);
		panel.add(btnNewButton_4);
		
		JButton btnNewButton_2 = new JButton("New button");
		btnNewButton_2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\아이스 아메리카노.jpg"));
		btnNewButton_2.setBounds(12, 52, 120, 120);
		panel.add(btnNewButton_2);
		
		
		
		

		
		JButton btnNewButton_2_1 = new JButton("New button");
		btnNewButton_2_1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\바닐라라떼.jpg"));
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
		btnNewButton_2_2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\토마토파스타.jpg"));
		btnNewButton_2_2.setBounds(29, 41, 120, 120);
		panel_1.add(btnNewButton_2_2);
		
		JButton btnNewButton_2_3 = new JButton("New button");
		btnNewButton_2_3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\라구파스타.jpg"));
		btnNewButton_2_3.setBounds(186, 41, 120, 120);
		panel_1.add(btnNewButton_2_3);
		
		JButton btnNewButton_2_4 = new JButton("New button");
		btnNewButton_2_4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\바질페스토파스타.jpg"));
		btnNewButton_2_4.setBounds(29, 265, 120, 120);
		panel_1.add(btnNewButton_2_4);
		
		JButton btnNewButton_2_5 = new JButton("New button");
		btnNewButton_2_5.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\알리오올리오 파스타.jpg"));
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
		btnNewButton_2_6.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\레몬에이드.jpg"));
		btnNewButton_2_6.setBounds(34, 35, 120, 120);
		panel_2.add(btnNewButton_2_6);
		
		JButton btnNewButton_2_7 = new JButton("New button");
		btnNewButton_2_7.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\블루에이드.jpg"));
		btnNewButton_2_7.setBounds(193, 35, 120, 120);
		panel_2.add(btnNewButton_2_7);
		
		JButton btnNewButton_2_8 = new JButton("New button");
		btnNewButton_2_8.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\사이다.jpg"));
		btnNewButton_2_8.setBounds(34, 225, 120, 120);
		panel_2.add(btnNewButton_2_8);
		
		JButton btnNewButton_2_9 = new JButton("New button");
		btnNewButton_2_9.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\콜라.jpg"));
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
		btnNewButton_2_6_1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\치즈케이크.jpg"));
		btnNewButton_2_6_1.setBounds(29, 33, 120, 120);
		panel_3.add(btnNewButton_2_6_1);
		
		JButton btnNewButton_2_6_2 = new JButton("New button");
		btnNewButton_2_6_2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\초코케이크.jpg"));
		btnNewButton_2_6_2.setBounds(183, 33, 120, 120);
		panel_3.add(btnNewButton_2_6_2);
		
		JButton btnNewButton_2_6_3 = new JButton("New button");
		btnNewButton_2_6_3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\에그타르트.jpg"));
		btnNewButton_2_6_3.setBounds(29, 233, 120, 120);
		panel_3.add(btnNewButton_2_6_3);
		
		JButton btnNewButton_2_6_4 = new JButton("New button");
		btnNewButton_2_6_4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\휘낭시에.jpg"));
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
		btnNewButton_2_6_1_1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\side\\리코타 치즈 샐러드.jpg"));
		btnNewButton_2_6_1_1.setBounds(36, 44, 120, 120);
		panel_4.add(btnNewButton_2_6_1_1);
		
		JButton btnNewButton_2_6_1_2 = new JButton("New button");
		btnNewButton_2_6_1_2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\side\\케이준 치킨 샐러드.jpg"));
		btnNewButton_2_6_1_2.setBounds(192, 44, 120, 120);
		panel_4.add(btnNewButton_2_6_1_2);
		
		JButton btnNewButton_2_6_1_3 = new JButton("New button");
		btnNewButton_2_6_1_3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\side\\양송이 크림스프.jpg"));
		btnNewButton_2_6_1_3.setBounds(36, 237, 120, 120);
		panel_4.add(btnNewButton_2_6_1_3);
		
		JButton btnNewButton_2_6_1_4 = new JButton("New button");
		btnNewButton_2_6_1_4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\side\\감자튀김.jpg"));
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
		
		JButton btnNewButton = new JButton("현금결제");
		btnNewButton.setBounds(479, 448, 97, 46);
		btnNewButton.setFont(new Font("나눔고딕", Font.BOLD, 16));
		frame.getContentPane().add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("카드결제");
		btnNewButton_1.setBounds(361, 448, 97, 46);
		btnNewButton_1.setFont(new Font("나눔고딕", Font.BOLD, 16));
		frame.getContentPane().add(btnNewButton_1);
		
		JButton btnNewButton_5 = new JButton("할인조회");
		btnNewButton_5.setBounds(596, 448, 97, 46);
		btnNewButton_5.setFont(new Font("나눔고딕", Font.BOLD, 16));
		frame.getContentPane().add(btnNewButton_5);
		
		
	    
	    
		table = new JTable();
		table.setBounds(361, 21, 332, 338);
		frame.getContentPane().add(table);
		
		
		
		table_1 = new JTable();
		table_1.setBounds(361, 369, 215, 69);
		frame.getContentPane().add(table_1);
		
		JSpinner spinner = new JSpinner();
		spinner.setBounds(588, 369, 42, 69);
		frame.getContentPane().add(spinner);
		
		JButton btnNewButton_6 = new JButton("상품삭제");
		btnNewButton_6.setBounds(630, 369, 85, 69);
		frame.getContentPane().add(btnNewButton_6);
	}
	}
	

	