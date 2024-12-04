package jys3;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;

public class MainMenu {
    private JFrame frame;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainMenu window = new MainMenu();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainMenu() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 749, 572);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // 관리자 버튼
        JButton btnAdmin = new JButton("관리자");
        btnAdmin.setBackground(new Color(175, 238, 238));
        btnAdmin.setFont(new Font("굴림", Font.BOLD, 20));
        btnAdmin.setBounds(176, 235, 97, 63);
        frame.getContentPane().add(btnAdmin);

        // 주문 버튼
        JButton btnOrder = new JButton("주문");
        btnOrder.setBackground(new Color(175, 238, 238));
        btnOrder.setFont(new Font("굴림", Font.BOLD, 20));
        btnOrder.setBounds(399, 235, 97, 63);
        frame.getContentPane().add(btnOrder);

        // 배경 이미지 설정
        JLabel lblBackground = new JLabel("");
        try {
            URL imageUrl = getClass().getResource("/main/main.jpg");
            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image image = icon.getImage();
                Image scaledImage = image.getScaledInstance(733, 523, Image.SCALE_SMOOTH);
                lblBackground.setIcon(new ImageIcon(scaledImage));
            } else {
                System.out.println("이미지를 찾을 수 없습니다!");
            }
        } catch (Exception e) {
            System.out.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
        }
        lblBackground.setBounds(0, 0, 733, 523);
        frame.getContentPane().add(lblBackground);

        // 관리자 버튼 동작
        btnAdmin.addActionListener(e -> {
           System.out.println("관리자 버튼 클릭");
            frame.dispose();
        });

        
        btnOrder.addActionListener(e -> {	// 주문 버튼 동작
            new OrderPanel(); // 주문 화면으로 이동
            frame.dispose();
        });
    }
}
