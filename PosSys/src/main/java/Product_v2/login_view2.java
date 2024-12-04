package Product_v2;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class login_view2 {
    public static void main(String[] args) {
        // 메인 프레임 생성
        JFrame frame = new JFrame("Fancy Login Window");
        frame.setSize(743, 567); // 창 크기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

     // 리소스를 통해 배경 이미지 로드
        ImageIcon backgroundImage = new ImageIcon(login_view2.class.getResource("/login/로그인배경.png"));
        if (backgroundImage.getIconWidth() == -1) {
            System.out.println("이미지를 로드할 수 없습니다.");
        }

        // 배경 이미지 크기 조정 (프레임 크기에 맞춰 비율 유지)
        Image img = backgroundImage.getImage();
        Image scaledImg = img.getScaledInstance(frame.getWidth(), frame.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledBackgroundImage = new ImageIcon(scaledImg);

        // 배경 이미지 JLabel에 설정
        JLabel backgroundLabel = new JLabel(scaledBackgroundImage);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight()); // 배경 이미지 크기 설정
        frame.add(backgroundLabel);


        // 메인 패널 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // 중앙 정렬을 위해 GridBagLayout 사용
        panel.setBounds(50, 50, 630, 410); // setBounds로 크기 및 위치 설정
        panel.setOpaque(false); // 배경을 투명하게 설정
        backgroundLabel.add(panel); // 배경 레이블에 패널 추가

        JLabel idLabel = new JLabel("ID:");
        idLabel.setForeground(Color.WHITE); // ID 레이블 텍스트 색상 흰색으로 설정
        
        JLabel pwLabel = new JLabel("PW:");
        pwLabel.setForeground(Color.WHITE); // PW 레이블 텍스트 색상 흰색으로 설정
        
        JTextField idField = new JTextField(15); // 입력 필드 크기 설정
        JPasswordField pwField = new JPasswordField(15);

        // 커스텀 둥근 테두리 Border 생성 (흰색 테두리)
        Border roundedWhiteBorder = new Border() {
            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(5, 5, 5, 5); // 테두리 안쪽 여백 설정
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }

            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 안티앨리어싱 설정
                g2d.setColor(Color.WHITE); // 테두리 색상 흰색 설정
                g2d.setStroke(new BasicStroke(2)); // 테두리 두께 설정
                g2d.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, 30, 30)); // 둥근 테두리 설정
            }
        };

        // 텍스트 필드 스타일 적용
        idField.setBorder(roundedWhiteBorder);
        idField.setOpaque(false);  // 배경을 투명하게 설정
        idField.setBackground(new Color(255, 255, 255, 100)); // 배경 투명도 설정 (100은 약간의 투명도)

        pwField.setBorder(roundedWhiteBorder);
        pwField.setOpaque(false);  // 배경을 투명하게 설정
        pwField.setBackground(new Color(255, 255, 255, 100)); // 배경 투명도 설정

        // Custom JButton 클래스 생성
        class CustomButton extends JButton {
            public CustomButton(String text) {
                super(text);
                // 폰트 설정 (굵기를 설정하기 위해 Font.BOLD 사용)
                setFont(new Font("Arial", Font.BOLD, 16)); // 굵은 폰트로 설정
                setBorder(roundedWhiteBorder); // 버튼 테두리 설정
                setFocusPainted(false); // 버튼 클릭 시 테두리 강조 제거
                setContentAreaFilled(false); // 버튼 배경을 비우고, paintComponent에서 설정
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Graphics2D로 캐스팅하여 안티앨리어싱 적용
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 안티앨리어싱 설정

                if (getModel().isPressed()) {
                    g.setColor(Color.GRAY); // 눌렸을 때 색상 변경
                } else {
                    g.setColor(Color.WHITE); // 기본 배경 흰색
                }
                g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // 둥근 모서리

                // 버튼 텍스트 색상 설정
                g2d.setColor(Color.BLACK); // 텍스트 색상 검정색으로 설정
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2; // 텍스트의 X 좌표
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent(); // 텍스트의 Y 좌표
                g2d.drawString(getText(), x, y); // 안티앨리어싱 처리된 텍스트 그리기
                super.paintBorder(g);
            }
        }

        // 커스텀 버튼 생성
        CustomButton openButton = new CustomButton("L O G I N");

        // GridBagConstraints를 통해 배치
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID 라벨
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(idLabel, gbc);

        // ID 입력 필드
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(idField, gbc);

        // PW 라벨
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(pwLabel, gbc);

        // PW 입력 필드
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(pwField, gbc);

        // 개점 버튼
        gbc.gridx = 0;
        gbc.gridy = 3;
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
