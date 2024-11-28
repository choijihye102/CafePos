package Admin_order;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SelectOrder_View2 extends JFrame {
	JComboBox jcb1;
	JButton jb1; 
	JTable table;
	DefaultTableModel model;
	
	JButton buttonBefore ;
	JButton buttonAfter ;
	
	
	JButton[] buttons; 
	JButton[] jb2; 
	Calendar cal = Calendar.getInstance(); ;
	int year = cal.get(Calendar.YEAR); 

	int month = cal.get(Calendar.MONDAY);
	
	SelectOrder_View2(){
		setTitle("월별 정산 조회");
	
	
		JPanel container2 = new JPanel();
		JPanel container3 = new JPanel();
		
	
		
		//Dimension dim = new Dimension(400,150);
		//센터 세팅
		
		JButton buttonBefore = new JButton("Before");
		JButton buttonAfter = new JButton("After");
		
		JLabel label = new JLabel("00년 0월");
		
		JButton[] buttons = new JButton[49];
		String[] dayNames = {"일","월","화","수","목","금","토"};
		
		container2.setLayout(new FlowLayout());
		container2.add(buttonBefore);
		container2.add(label);
		container2.add(buttonAfter);
		
		Font font = new Font("sansSerif", Font.BOLD, 20);
		buttonAfter.setFont(font);
		buttonBefore.setFont(font);
		label.setFont(font);
		
		label.setText(getCalText());
		
		container3.setLayout(new GridLayout(7,7,5,5));
		for(int i =0; i < buttons.length; i++) {
			buttons[i] = new JButton();
			container3.add(buttons[i]);
			
			buttons[i].setFont(new Font("sansSerif", Font.BOLD, 24));
			
			if(i < 7) buttons[i].setText(dayNames[i]);
			
			if(i%7 == 0) buttons[i].setForeground(Color.red);
			if(i%7 == 0) buttons[i].setForeground(Color.blue);
		}
		setButton(buttons);
		calSet();
		
		
	
		
		//프레임 세팅

		add(container2, BorderLayout.NORTH);
		add(container3, BorderLayout.CENTER);
		//add(jsp, BorderLayout.CENTER);
		
		pack();
		setBounds(100,100,743,567);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
		
		CalendarFunction();
		
		buttonAfter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int gap =0;
				if(e.getSource() == buttonBefore) {
					gap = -1;
				}else if(e.getSource() == buttonAfter) {
					gap = +1;
				}
				
				allInit(gap);
				label.setText(getCalText());
			}
			
		
		});
		
		buttonBefore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int gap =0;
				if(e.getSource() == buttonBefore) {
					gap = -1;
				}else if(e.getSource() == buttonAfter) {
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
		System.out.println("CalendarFunction year"+this.year);
	}
	
	public void setButton(JButton[] buttons) {
		this.buttons = buttons; 
	}
	
	//Lable -> 0000년 00월 문자열 리턴
	public String getCalText() {
		System.out.println("getCalText year"+this.year);
		return year+"년" +month +"월";
		
	}
	
	//버튼 날자 출력
	public void calSet() {
		//calendar 객체 날짜 1일 설정
		cal.set(year, month-1,1);
		
		//그 달의 1일 요일 수 
		int firstDay = cal.get(Calendar.DAY_OF_WEEK);
		//요일 수 1일 시작, 배열 0부터 시작
		firstDay--;
		
		for(int i =1; i <= cal.getActualMaximum(cal.DATE); i++) {
			//buttons[1]~[6] : 일~ 토
			//buttons[7] ~ : 날짜 출력
			buttons[6 + firstDay +i].setText(String.valueOf(i));
		}
	}
	
	//달력 새로운 년월 출력
	public void allInit(int gap) {
		//버튼 날짜 지우기 
		for(int i =7; i < buttons.length; i++) {
			buttons[i].setText("");
		}
		month += gap;
		if(month <=0) {
			year--;
			month =12;
		}else if(month >= 13) {
			year++;
			month =1;
		}
		calSet();
	}
	
	public static void main(String[] args) {
		new SelectOrder_View2();

	}

}
