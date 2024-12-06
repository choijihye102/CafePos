package Admin_management;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class SelectCustomer_view extends JFrame {
	//필드 세팅 
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
	
	PreparedStatement pstmt = null;         // SQL문을 DB에 전송하는 객체
	ResultSet rs = null;                    // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;                      // SQL문을 저장하는 문자열 변수.

	Criteria cri = new Criteria(1,20);
	Cus_SelectwithCri cusDB = new Cus_SelectwithCri(cri);;  	//Cus_SelectwithCri 객체 생성과 동시에 con필드로 db연결 완료. (생성자 메소드)
	
	
	SelectCustomer_view(){
		setTitle("고객 조회");
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
		add(container1,BorderLayout.NORTH);
		add(jsp,BorderLayout.CENTER);
		add(container2,BorderLayout.SOUTH);

		//프레임 세팅 
		setBounds(100,100,743,567);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
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


	}
	
	

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

	
	
	
	
	public static void main(String[] args) {
	new SelectCustomer_view().selectTotal();
		
}
}
