package Admin_order;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class SelectOrder_View extends JFrame {
	JComboBox jcb1;
	JButton jb1; 
	JTable table;
	DefaultTableModel model;
	
	SelectOrder_View(){
		setTitle("월별 정산 조회");

		JPanel container1 = new JPanel();
		JPanel container2 = new JPanel();
		
		//상단 세팅
	    jcb1 = new JComboBox<String>();
		jcb1.addItem("월 선택");
		jcb1.addItem("1 월");
		jcb1.addItem("2 월");
		jcb1.addItem("3 월");
		jcb1.addItem("4 월");
		jcb1.addItem("5 월");
		jcb1.addItem("6 월");
		jcb1.addItem("7 월");
		jcb1.addItem("8 월");
		jcb1.addItem("9 월");
		jcb1.addItem("10 월");
		jcb1.addItem("11 월");
		jcb1.addItem("12 월");
		
		JButton jb1 = new JButton("조회");
		
		container1.add(jcb1);
		container1.add(jb1);
		
		//Dimension dim = new Dimension(400,150);
		//센터 세팅
		String header[] = {"일","월","화","수","목","금","토"};
	/*	String contends[][]= {
				{"","","","","","1","2"},
				{"3","4","5","6","7","8","9"},
				{"10","11","12","13","14","15","16"},
				{"17","18","19","20","21","22","23"},
				{"24","25","26","27","28","29","30"},
				{"31","","","","","",""}
	
		};*/
		
		model = new DefaultTableModel(header, 0);
		
		table = new JTable(model);
		table.setRowHeight(70);
	
		
		
		JScrollPane jsp = new JScrollPane(
				table, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		
	//	table.setBounds(EXIT_ON_CLOSE, ABORT, 730, 380);
		
	//	container2.setBounds(EXIT_ON_CLOSE, ABORT, 740, 400);
		//프레임 세팅 
		add(container1, BorderLayout.NORTH);
		add(jsp, BorderLayout.CENTER);
		
		pack();
		setBounds(200, 200, 743, 567);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new SelectOrder_View();

	}

}
