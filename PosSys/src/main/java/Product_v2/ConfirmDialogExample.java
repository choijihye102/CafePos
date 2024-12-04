package Product_v2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ConfirmDialogExample extends JFrame {
	JLabel label;
	int msg;

	ConfirmDialogExample() {
	}

	public Integer getter(Integer msg) {
		this.msg = msg;

		setTitle("결제 결과 조회");
		setBounds(200, 200, 400, 250);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 패널 생성
		JPanel p1 = new JPanel(new BorderLayout());
		JPanel p2 = new JPanel();

		if (msg == 0) {
			label = new JLabel("결제가 완료되었습니다.", SwingConstants.CENTER); // 가운데 정렬
		} else {
			label = new JLabel("결제가 실패하였습니다", SwingConstants.CENTER); // 가운데 정렬
		}
		// 라벨 생성 및 스타일링

		label.setFont(new Font("맑은 고딕", Font.BOLD, 20)); // 글씨 크기 두 배로 증가
		label.setForeground(new Color(34, 34, 34)); // 글씨 색상 설정

		// 버튼 생성 및 스타일링
		JButton button = new JButton("확인");
		button.setBackground(new Color(128, 0, 128)); // 보라색 배경
		button.setForeground(Color.WHITE); // 버튼 글씨 흰색
		button.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		button.setFocusPainted(false); // 버튼 클릭 테두리 제거

		// 버튼 클릭 이벤트 추가
		button.addActionListener(e -> dispose()); // 창 닫기

		// 패널에 추가
		p1.add(label, BorderLayout.CENTER);
		p2.add(button);

		// 프레임에 패널 추가
		add(p1, BorderLayout.CENTER);
		add(p2, BorderLayout.SOUTH);

		// 프레임 스타일링
		p1.setBackground(new Color(240, 240, 240)); // 배경색 밝은 회색
		p2.setBackground(new Color(240, 240, 240)); // 버튼 패널 배경색

		// 프레임 설정
		setVisible(true);
		return msg;
	}

	}



