package Product_v2;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class Pay_Card_View extends JFrame {
	CardDTO cardDTO = new CardDTO();
    private JTextField jtf1, jtf2, jtf3, jtf4, jtf5;
    int j = 5;
    boolean plug = false;


    public Integer Pay(int price) {
        setTitle("카드 결제");
        setBounds(200, 200, 800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 전체 레이아웃 설정
        setLayout(new BorderLayout());

        // 좌측 하단에 이미지 배치
        JLabel imageLabel = new JLabel();
        try {
            URL imageUrl = Pay_Card_View.class.getResource("/card.png");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image scaledImage = icon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));
            } else {
                System.err.println("이미지를 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
        }
        JPanel imagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        imagePanel.add(imageLabel);
        imagePanel.setBackground(Color.WHITE);

        // 메인 패널 (오른쪽)
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 입력 패널 (폼)
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        // "결제 정보 입력" 제목의 글씨 크기 변경
        TitledBorder titledBorder = BorderFactory.createTitledBorder("결제 정보 입력");
        titledBorder.setTitleFont(new Font("맑은 고딕", Font.BOLD, 20)); // 현재의 2.5배 크기
        formPanel.setBorder(titledBorder);

        JLabel jlb1 = createStyledLabel("결제 금액:");
        jtf1 = createStyledTextField();
        jtf1.setText(String.valueOf(price));

        JLabel jlb2 = createStyledLabel("카드 번호:");
        jtf2 = createStyledTextField();

        JLabel jlb3 = createStyledLabel("카드 유효기간 [yyyy-MM]:");
        jtf3 = createStyledTextField();

        JLabel jlb4 = createStyledLabel("생년월일 [yymmdd]:");
        jtf4 = createStyledTextField();

        JLabel jlb5 = createStyledLabel("비밀번호 앞 두자리:");
        jtf5 = createStyledTextField();

        formPanel.add(jlb1);
        formPanel.add(jtf1);
        formPanel.add(jlb2);
        formPanel.add(jtf2);
        formPanel.add(jlb3);
        formPanel.add(jtf3);
        formPanel.add(jlb4);
        formPanel.add(jtf4);
        formPanel.add(jlb5);
        formPanel.add(jtf5);

        // 버튼 패널
        JPanel buttonPanel = new JPanel();
        JButton jbt1 = new JButton("결제");
        jbt1.setBackground(new Color(128, 0, 128)); // 보라색
        jbt1.setForeground(Color.WHITE);
        jbt1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        buttonPanel.add(jbt1);

        // 메인 패널에 추가
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 좌측과 우측 레이아웃 설정
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(imagePanel, BorderLayout.SOUTH); // 좌측 하단에 이미지 배치

        add(leftPanel, BorderLayout.WEST);
        add(mainPanel, BorderLayout.CENTER);

        // 버튼 이벤트 추가
        jbt1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IamportAPI3 payCard = new IamportAPI3();

                int amount = Integer.parseInt(jtf1.getText());
                String cardNum = jtf2.getText();
                String exp = jtf3.getText();
                String birth = jtf4.getText();
                String no = jtf5.getText();

                j = payCard.PayCard(amount, cardNum, exp, birth, no);
                new ConfirmDialogExample().getter(j);
                plug = true;
                
                dispose();
       
            }
        });

       
        setVisible(true);
        return j;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("맑은 고딕", Font.BOLD, 18)); // 현재 크기의 2배
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(0, 20)); // 텍스트 필드 높이를 줄임
        return textField;
    }

 
}
