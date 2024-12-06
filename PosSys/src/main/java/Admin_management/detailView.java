package Admin_management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class detailView {
	JFrame frame2;
	Orders2DTO orderD = new Orders2DTO();
	OrderDAO dao = new OrderDAO();
	  private JFrame frame;
	    private JTextField orderNumField;
	    private JButton refundButton;
	    private JLabel statusLabel;

	 
	
	public void show(Orders2DTO orderD) {
		SwingUtilities.invokeLater(() -> {
			// Create the frame
			JFrame frame = new JFrame("Receipt Example");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setSize(600, 800); // Frame size

			// Create the main panel
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			panel.setBackground(Color.WHITE); // Set background color

			// Example variables for receipt data
			//환불테이블 조회
			int totalReturn = orderD.gettotalReturn();
			//주문테이블 조회
		    String d = orderD.getDate();
			int total_card_payment = orderD.getTotal_card_payment();
			int total_cash_payment = orderD.getTotal_cash_payment();
			int total_price_sum = orderD.getTotal_price_sum();
			int total_used_points = orderD.getTotal_used_points();
			int total_orders = orderD.getTotal_orders();
			
			int total1 = total_price_sum+total_used_points;
			int total2 = (total_price_sum+total_used_points)-totalReturn-total_used_points;

			// Custom font for the labels
			Font font = new Font("돋움", Font.BOLD, 23);
			Font font2 = new Font("돋움", Font.BOLD, 28);

			// Add labels with styled text
			panel.add(createStyledLabel("-------"+d+"일 결제 완료된 주문------", font2, Color.BLUE));
			panel.add(createStyledLabel("현금결제 금액 : " + total_cash_payment+ "원", font, Color.DARK_GRAY));
			panel.add(createStyledLabel("카드결제 금액 : " + total_card_payment+ "원", font, Color.DARK_GRAY));
			panel.add(createStyledLabel("판매 금액: " + total1 + "원", font, Color.DARK_GRAY));
			panel.add(createStyledLabel("할 인 : " + total_used_points + "원", font, Color.RED));
			panel.add(createStyledLabel("반 품 : " + totalReturn + "원", font, Color.RED));
			panel.add(createStyledLabel("--------------------------------------------", font, Color.DARK_GRAY));
			panel.add(createStyledLabel("순매출액: " + total2 + "원", font, Color.BLUE));
			panel.add(createStyledLabel("손님수: " + total_orders +"분", font, Color.DARK_GRAY));
			panel.add(createStyledLabel("--------------------------------------------", font, Color.DARK_GRAY));


			// Add spacing for readability
			for (int i = 0; i < 5; i++) {
				panel.add(Box.createRigidArea(new Dimension(0, 10)));
			}

			// Wrap panel in a JScrollPane
			JScrollPane scrollPane = new JScrollPane(panel);
			scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

			// Create the button panel
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(1, 2, 10, 10)); // Two buttons side by side
			buttonPanel.setBackground(Color.WHITE);

			// Button height as 1/6 of the frame height
			int buttonHeight = frame.getHeight() / 6;

			// Create "확인" button
			JButton confirmButton = new JButton("확인");
			confirmButton.setFont(new Font("돋움", Font.BOLD, 24));
			confirmButton.setBackground(Color.LIGHT_GRAY);
			confirmButton.setForeground(Color.BLACK);
			confirmButton.setPreferredSize(new Dimension(frame.getWidth() / 2 - 20, buttonHeight));
			confirmButton.addActionListener(e -> frame.dispose()); // Close the frame

			// Create "반품" button
			JButton returnButton = new JButton("반품");
			returnButton.setFont(new Font("돋움", Font.BOLD, 24));
			returnButton.setBackground(Color.PINK);
			returnButton.setForeground(Color.BLACK);
			returnButton.setPreferredSize(new Dimension(frame.getWidth() / 2 - 20, buttonHeight));
			returnButton.addActionListener(e -> show2());

			// Add buttons to the button panel
			buttonPanel.add(returnButton);
			buttonPanel.add(confirmButton);

			// Add components to the frame
			frame.setLayout(new BorderLayout());
			frame.add(scrollPane, BorderLayout.CENTER);
			frame.add(buttonPanel, BorderLayout.SOUTH);

			// Make the frame visible
			frame.setVisible(true);
		});
	}

	// Helper method to create styled labels
	private static JLabel createStyledLabel(String text, Font font, Color color) {
		JLabel label = new JLabel(text);
		label.setFont(font);
		label.setForeground(color);
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding
		return label;
	}
	
	
	public void show2() {
		
		    frame2 = new JFrame("환불 시스템");
		    frame2.setSize(400, 200);
		    frame2.setDefaultCloseOperation(frame2.DISPOSE_ON_CLOSE);
		    frame2.setLayout(new BorderLayout());

		    // 주문 번호 입력란
		    JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		    inputPanel.add(new JLabel("주문 번호: "));
		    orderNumField = new JTextField(15);
		    inputPanel.add(orderNumField);

		    // 환불 버튼 패널
		    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		    refundButton = new JButton("환불하기");
		    refundButton.setPreferredSize(new Dimension(200, 40)); // 버튼 크기 조정
		    refundButton.addActionListener(new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		            dao.insertR(orderNumField.getText());
		            JOptionPane.showMessageDialog(frame2, "환불이 완료되었습니다.");
		        }
		    });

		    // 버튼 패널에 추가
		    buttonPanel.add(refundButton);

		    // 상태 표시 라벨
		    statusLabel = new JLabel(" ");
		    statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

		    // 컴포넌트 추가
		    frame2.add(inputPanel, BorderLayout.NORTH);
		    frame2.add(buttonPanel, BorderLayout.CENTER);
		    frame2.add(statusLabel, BorderLayout.SOUTH);

		    frame2.setVisible(true);
		
	}

}	
