package Product_v2;

import java.awt.BorderLayout;
import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Pay_Card_View extends JFrame {
	JTextField jtf1;
	JTextField jtf2;
	JTextField jtf3;
	JTextField jtf4;
	JTextField jtf5 ;
	
	  Pay_Card_View() {
	        setTitle("카드 결제");
	        setBounds(200, 200, 500, 500);

	        // 패널 설정
	        JPanel panel1 = new JPanel(new GridLayout(5, 2, 10, 10)); // 5행 2열의 레이아웃, 간격 설정
	        JPanel panel2 = new JPanel();

	        // 필드 및 텍스트 필드 설정
	        JLabel jlb1 = new JLabel("결제 금액:");
	         jtf1 = new JTextField(10);

	        JLabel jlb2 = new JLabel("카드 번호:");
	         jtf2 = new JTextField(10);

	        JLabel jlb3 = new JLabel("카드 유효기간 [yyyy-0m]:");
	         jtf3 = new JTextField(10);

	        JLabel jlb4 = new JLabel("생년월일 [yymmdd]:");
	         jtf4 = new JTextField(10);

	        JLabel jlb5 = new JLabel("비밀번호 앞 두자리:");
	         jtf5 = new JTextField(5);

	        // 필드와 텍스트 필드 추가
	        panel1.add(jlb1);
	        panel1.add(jtf1);
	        panel1.add(jlb2);
	        panel1.add(jtf2);
	        panel1.add(jlb3);
	        panel1.add(jtf3);
	        panel1.add(jlb4);
	        panel1.add(jtf4);
	        panel1.add(jlb5);
	        panel1.add(jtf5);

	        // 버튼 추가
	        JButton jbt1 = new JButton("결제");
	        panel2.add(jbt1);

	        // 프레임에 패널 추가
	        add(panel1, BorderLayout.CENTER);
	        add(panel2, BorderLayout.SOUTH);

	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setVisible(true);
	        
	        jbt1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					 IamportAPI3 payCard = new IamportAPI3();
					
					 int amount =  Integer.parseInt(jtf1.getText());
					 String cardNum =  jtf2.getText();
					 String exp = jtf3.getText();
					 String birth = jtf4.getText();
					 String no = jtf5.getText();
					 
					 String msg = payCard.PayCard(amount,cardNum,exp,birth,no);
					 dispose();
					 new ConfirmDialogExample(msg);
					 
					//int amount, String cardNumber,  String expiry,  String birth, String no
					
				}
			});
	       
	    }

	    public static void main(String[] args) {
	        new Pay_Card_View();
	    }
	}