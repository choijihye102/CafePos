package Product_v2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import javax.swing.*;
import java.awt.geom.RoundRectangle2D;
public class main {

    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                main window = new main();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 749, 572 );
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        

        // 관리자 버튼 생성
        RoundedButton btnAdmin = new RoundedButton("", 20);
        btnAdmin.setBounds(430, 320, 80, 63);
        btnAdmin.setFont(new Font("굴림", Font.BOLD, 20));
        btnAdmin.setBackground(new Color(175, 238, 238));
        btnAdmin.setForeground(Color.BLACK);

        // 이미지 추가
        addImageToButton(btnAdmin, "/main/관리자.png");
        frame.getContentPane().add(btnAdmin);

        // 주문 버튼 생성
        RoundedButton btnOrder = new RoundedButton("",20);
        btnOrder.setBounds(230, 320, 80, 63);
        btnOrder.setFont(new Font("굴림", Font.BOLD, 20));
        btnOrder.setBackground(new Color(175, 238, 238));
        btnOrder.setForeground(Color.BLACK);

        // 이미지 추가
        addImageToButton(btnOrder, "/main/주문.png");
        frame.getContentPane().add(btnOrder);
        
        // 커뮤니티 버튼 생성
        RoundedButton btncommunity = new RoundedButton("", 20);
        btncommunity.setBounds(330, 320, 80, 63);
        btncommunity.setFont(new Font("굴림", Font.BOLD, 20));
        btncommunity.setBackground(new Color(175, 238, 238));
        btncommunity.setForeground(Color.BLACK);

        // 이미지 추가
        addImageToButton(btncommunity, "/main/커뮤니티.png");
        frame.getContentPane().add(btncommunity);

        // 배경 이미지 추가
        JLabel lblBackground = new JLabel();
        try {
            URL bgImageUrl = main.class.getResource("/main/백브런치.png");
            if (bgImageUrl != null) {
                ImageIcon bgIcon = new ImageIcon(bgImageUrl);
                Image bgImage = bgIcon.getImage().getScaledInstance(733, 523, Image.SCALE_SMOOTH);
                lblBackground.setIcon(new ImageIcon(bgImage));
            } else {
                System.out.println("배경 이미지를 찾을 수 없습니다!");
            }
        } catch (Exception e) {
            System.out.println("배경 이미지를 로드하는 중 오류 발생: " + e.getMessage());
        }

        lblBackground.setBounds(0, 0, 733, 523);
        frame.getContentPane().add(lblBackground);

        // 주문 버튼 클릭 이벤트
        btnOrder.addActionListener((ActionEvent e) -> {
            System.out.println("주문 버튼 클릭");
            p2 p2 = new p2();
            frame.dispose();
        });
        // 관리자 버튼 클릭 이벤트
        btnAdmin.addActionListener((ActionEvent e) -> {
            System.out.println("관리자 버튼 클릭");
        });
        
     // 커뮤니티 버튼 클릭 이벤트
        btncommunity.addActionListener((ActionEvent e) -> {
            System.out.println("커뮤니티 버튼 클릭");
            // login_view2 클래스의 main 메서드 호출
            login_view2.main(new String[]{});  // login_view2의 main 메서드를 호출하여 로그인 화면으로 이동
            frame.dispose();  // 현재 화면을 닫기
        });
    }

    // 버튼에 이미지 추가 메서드
    private void addImageToButton(JButton button, String imagePath) {
        try {
            URL imageUrl = main.class.getResource(imagePath);
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                ((RoundedButton) button).setImageIcon(icon); // 이미지 설정
            } else {
                System.out.println("이미지를 찾을 수 없습니다: " + imagePath);
            }
        } catch (Exception e) {
            System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
        }
    }
// 둥근 버튼 클래스
    class RoundedButton extends JButton {

        private int cornerRadius;
        private ImageIcon buttonIcon;

        public RoundedButton(String text, int cornerRadius) {
            super(text);
            this.cornerRadius = cornerRadius;
            setContentAreaFilled(false); // 배경 기본 그리기 비활성화
            setFocusPainted(false); // 포커스 테두리 제거
            setBorderPainted(false); // 테두리 줄 없애기
        }

        public void setImageIcon(ImageIcon icon) {
            this.buttonIcon = icon;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 안티앨리어싱 설정
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); // 텍스트 안티앨리어싱 설정

            // 버튼 크기 얻기
            int width = getWidth();
            int height = getHeight();

            // 둥근 사각형 영역을 정의하고 그리기
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(0, 0, width, height, cornerRadius, cornerRadius);
            g2d.setClip(roundedRectangle); // 클리핑 영역 설정

            // 배경색 및 둥근 사각형 그리기
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, width, height, cornerRadius, cornerRadius);

            // 버튼 이미지 그리기
            if (buttonIcon != null) {
                Image img = buttonIcon.getImage();
                int imgWidth = getWidth();
                int imgHeight = getHeight();

                // 이미지 크기를 버튼 크기에 맞게 조정
                Image scaledImage = img.getScaledInstance(imgWidth, imgHeight, Image.SCALE_SMOOTH);
                buttonIcon = new ImageIcon(scaledImage);

                // 이미지를 그리기 전에, 클리핑 영역 내에서만 그려지도록 설정
                g2d.setClip(roundedRectangle); // 둥근 모서리 영역으로 클리핑
                buttonIcon.paintIcon(this, g2d, 0, 0); // 이미지를 버튼에 맞게 그리기
            }

            // 텍스트 렌더링
            String text = getText();
            if (text != null && !text.isEmpty()) {
                g2d.setFont(getFont());
                g2d.setColor(getForeground());
                FontMetrics metrics = g2d.getFontMetrics();
                int x = (width - metrics.stringWidth(text)) / 2;
                int y = (height - metrics.getHeight()) / 2 + metrics.getAscent();
                g2d.drawString(text, x, y);
            }

            g2d.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // 테두리 그리지 않음
        }
    }
}