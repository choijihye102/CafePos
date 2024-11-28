package project;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import java.awt.Color;

public class Menu {
	String ori ="";
    private JFrame frame;
    private JTextArea textArea;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Menu window = new Menu();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Menu() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("메뉴판");
        frame.setBounds(100, 100, 743, 567);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.getContentPane().setLayout(null);

        // Create the JTabbedPane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(0, 0, 349, 528);
        frame.getContentPane().add(tabbedPane);

        // Create Coffee panel
        JPanel panelCoffee = new JPanel();
        panelCoffee.setBackground(Color.WHITE);
        tabbedPane.addTab("커피", null, panelCoffee, null);
        panelCoffee.setLayout(null);

        // Create buttons for coffee items
        JButton btnCoffee1 = new JButton("아이스 아메리카노");
        btnCoffee1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\아이스 아메리카노.jpg"));
        btnCoffee1.setBounds(12, 52, 120, 120);
        panelCoffee.add(btnCoffee1);

        JButton btnCoffee2 = new JButton("바닐라 라떼");
        btnCoffee2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\바닐라라떼.jpg"));
        btnCoffee2.setBounds(176, 52, 120, 120);
        panelCoffee.add(btnCoffee2);

        JButton btnCoffee3 = new JButton("카페라떼");
        btnCoffee3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\카페라떼.jpg"));
        btnCoffee3.setBounds(12, 255, 120, 120);
        panelCoffee.add(btnCoffee3);

        JButton btnCoffee4 = new JButton("카라멜 마끼아또");
        btnCoffee4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\coffee\\카라멜 마끼아또.jpg"));
        btnCoffee4.setBounds(176, 255, 120, 120);
        panelCoffee.add(btnCoffee4);

        // Create Pasta panel
        JPanel panelPasta = new JPanel();
        panelPasta.setBackground(Color.WHITE);
        tabbedPane.addTab("파스타", null, panelPasta, null);
        panelPasta.setLayout(null);

        // Create buttons for pasta items
        JButton btnPasta1 = new JButton("알리오 올리오");
        btnPasta1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\알리오올리오.jpg"));
        btnPasta1.setBounds(12, 52, 120, 120);
        panelPasta.add(btnPasta1);

        JButton btnPasta2 = new JButton("까르보나라");
        btnPasta2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\까르보나라.jpg"));
        btnPasta2.setBounds(176, 52, 120, 120);
        panelPasta.add(btnPasta2);

        JButton btnPasta3 = new JButton("봉골레");
        btnPasta3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\봉골레.jpg"));
        btnPasta3.setBounds(12, 255, 120, 120);
        panelPasta.add(btnPasta3);

        JButton btnPasta4 = new JButton("로제 파스타");
        btnPasta4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\pasta\\로제파스타.jpg"));
        btnPasta4.setBounds(176, 255, 120, 120);
        panelPasta.add(btnPasta4);

        // Create Drink panel
        JPanel panelDrink = new JPanel();
        panelDrink.setBackground(Color.WHITE);
        tabbedPane.addTab("음료", null, panelDrink, null);
        panelDrink.setLayout(null);

        // Create buttons for drink items
        JButton btnDrink1 = new JButton("레몬 에이드");
        btnDrink1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\레몬에이드.jpg"));
        btnDrink1.setBounds(12, 52, 120, 120);
        panelDrink.add(btnDrink1);

        JButton btnDrink2 = new JButton("자몽 에이드");
        btnDrink2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\자몽에이드.jpg"));
        btnDrink2.setBounds(176, 52, 120, 120);
        panelDrink.add(btnDrink2);

        JButton btnDrink3 = new JButton("딸기 주스");
        btnDrink3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\딸기주스.jpg"));
        btnDrink3.setBounds(12, 255, 120, 120);
        panelDrink.add(btnDrink3);

        JButton btnDrink4 = new JButton("피치 티");
        btnDrink4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\drink\\피치티.jpg"));
        btnDrink4.setBounds(176, 255, 120, 120);
        panelDrink.add(btnDrink4);

        // Create Dessert panel
        JPanel panelDessert = new JPanel();
        panelDessert.setBackground(Color.WHITE);
        tabbedPane.addTab("디저트", null, panelDessert, null);
        panelDessert.setLayout(null);

        // Create buttons for dessert items
        JButton btnDessert1 = new JButton("티라미수");
        btnDessert1.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\티라미수.jpg"));
        btnDessert1.setBounds(12, 52, 120, 120);
        panelDessert.add(btnDessert1);

        JButton btnDessert2 = new JButton("마카롱");
        btnDessert2.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\마카롱.jpg"));
        btnDessert2.setBounds(176, 52, 120, 120);
        panelDessert.add(btnDessert2);

        JButton btnDessert3 = new JButton("케이크");
        btnDessert3.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\케이크.jpg"));
        btnDessert3.setBounds(12, 255, 120, 120);
        panelDessert.add(btnDessert3);

        JButton btnDessert4 = new JButton("브라우니");
        btnDessert4.setIcon(new ImageIcon("D:\\ncs\\workspace(java)\\Ch20_project\\dessert\\브라우니.jpg"));
        btnDessert4.setBounds(176, 255, 120, 120);
        panelDessert.add(btnDessert4);

        // Create TextArea for displaying selected item
        textArea = new JTextArea();
        textArea.setBounds(361, 24, 332, 336);
        frame.getContentPane().add(textArea);

        // Set action listeners for each button
       
        String str1 ="아이스 아메리카노\\n2,500원\n";
        String str2 ="바닐라 라떼\n3,500원\n";
        
        btnCoffee1.addActionListener(e ->{
        	ori +=str1;
        	textArea.setText(ori);
        	
        });
        btnCoffee2.addActionListener(e -> {
        	ori +=str2;
        	textArea.setText(ori);
    	
    });
        		
  //    btnCoffee1.addActionListener(e -> textArea.setText("아이스 아메리카노\n2,500원"));
  //     btnCoffee2.addActionListener(e -> textArea.setText("바닐라 라떼\n3,500원"));
        btnCoffee3.addActionListener(e -> textArea.setText("카페라떼\n3,500원"));
        btnCoffee4.addActionListener(e -> textArea.setText("카라멜 마끼아또\n4,000원"));

        btnPasta1.addActionListener(e -> textArea.setText("알리오 올리오\n9,500원"));
        btnPasta2.addActionListener(e -> textArea.setText("까르보나라\n11,000원"));
        btnPasta3.addActionListener(e -> textArea.setText("봉골레\n12,000원"));
        btnPasta4.addActionListener(e -> textArea.setText("로제 파스타\n13,000원"));

        btnDrink1.addActionListener(e -> textArea.setText("레몬 에이드\n3,000원"));
        btnDrink2.addActionListener(e -> textArea.setText("자몽 에이드\n3,500원"));
        btnDrink3.addActionListener(e -> textArea.setText("딸기 주스\n4,000원"));
        btnDrink4.addActionListener(e -> textArea.setText("피치 티\n3,500원"));

        btnDessert1.addActionListener(e -> textArea.setText("티라미수\n5,000원"));
        btnDessert2.addActionListener(e -> textArea.setText("마카롱\n2,500원"));
        btnDessert3.addActionListener(e -> textArea.setText("케이크\n4,500원"));
        btnDessert4.addActionListener(e -> textArea.setText("브라우니\n3,500원"));
    }
}
