package LogInPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogIn_View {
    public static void main(String[] args) {
        // 메인 프레임 생성
        JFrame frame = new JFrame("Fancy Login Window");
        frame.setSize(743, 567); // 창 크기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        // 메인 패널 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // 중앙 정렬을 위해 GridBagLayout 사용
        panel.setBounds(50, 50, 543, 367); // setBounds로 크기 및 위치 설정
        frame.add(panel);

        // 컴포넌트 추가
        JLabel messageLabel = new JLabel("오늘도 화이팅하세요!");
        messageLabel.setFont(new Font("나눔고딕", Font.BOLD | Font.ITALIC, 20)); // Fancy 글꼴 설정
        messageLabel.setForeground(new Color(75, 0, 130)); // 보라색 계열 컬러로 설정

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(15); // 입력 필드 크기 설정
        JLabel pwLabel = new JLabel("PW:");
        JPasswordField pwField = new JPasswordField(15);
        JButton openButton = new JButton("개점"); // 버튼 텍스트 설정

        // GridBagConstraints를 통해 배치
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // 메시지 라벨
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2; // 두 칸을 차지
        gbc.anchor = GridBagConstraints.CENTER; // 중앙 정렬
        panel.add(messageLabel, gbc);

        // ID 라벨
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        panel.add(idLabel, gbc);

        // ID 입력 필드
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(idField, gbc);

        // PW 라벨
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(pwLabel, gbc);

        // PW 입력 필드
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(pwField, gbc);

        // 개점 버튼
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2; // 버튼이 중앙에 오도록 설정
        panel.add(openButton, gbc);

        // 버튼 동작 설정
        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String pw = new String(pwField.getPassword());
                JOptionPane.showMessageDialog(frame, "ID: " + id + "\nPW: " + pw);
            }
        });

        // 프레임 표시
        frame.setVisible(true);
    }
}

