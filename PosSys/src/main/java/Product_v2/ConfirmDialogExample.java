package Product_v2;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

public class ConfirmDialogExample extends JFrame {

    ConfirmDialogExample(String msg) {
        setTitle("결제 결과 조회");
        setBounds(200, 200, 300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel p1= new JPanel();
        JPanel p2= new JPanel();
        
        JLabel label = new JLabel(msg);
        // 버튼 생성
        JButton button = new JButton("확인 ");
        

        // 버튼 클릭 이벤트 추가
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	dispose();  // 프로그램 종료
            }
        });
        p1.add(label);
        p2.add(button);
        // 프레임에 버튼 추가
        add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.SOUTH);

        setVisible(true);
    }
  /*  public static void main(String[] args) {
    	new ConfirmDialogExample("야");
	}*/
}
