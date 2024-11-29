package jys;

import java.awt.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import jys.BaeckCafeDAO;
import jys.ProductDTO;
import jys.CustomerDTO;
import jys.MiroticDTO;
import jys.OrdersDTO;

public class BaeckCafeMain extends JFrame {
	
	
	private JPanel menuPanel;			// 메뉴가 올라가는?? 패널.
    private JButton orderButton;		// 주문하기 버튼.
    private JButton calculateButton;	// 계산하기 버튼.
    private JButton mileageButton;	    // 마일리지 조회 및 사용 버튼.
    private JLabel totalPriceLabel;		// 총 가격 라벨? 필요한건지 의문.
    private JLabel totalQuantityLabel;   // 총 수량??? 라벨. 필요한지 의문.
    private JTextArea orderSummaryArea;  // JtextArea orderSummaryArea 임.
    private Map<String, JTextField> quantityFields;		// 수량을 Map<String, 입력받는 툴. 오른쪽 숫자 뜨는칸인듯.
    
    
    private String cus_phone;		// cus_phone을 이걸로 받아야함.
    private int cus_pointUsed;		// 마일리지 사용할 때 사용.
    private int totalPrice;			// 총 가격.
    private int finalPrice;			// 마일리지 차감 후 지불해야 하는 가격.
    private int totalQuantity;		// 총 수량??
	
	
	
    public BaeckCafeMain() {
        setTitle("백 카페 POS 시스템 Jys");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 800);
        setLocationRelativeTo(null);
        
        initUI();
        
        setVisible(true);
    }
    
    private void initUI() {
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 3, 10, 10));
        quantityFields = new HashMap<>();
        
        BaeckCafeDAO dao = new BaeckCafeDAO();
        List<ProductDTO> menuList;
        
        try {
            menuList = dao.getMenuList();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "메뉴 정보를 가져오는 중 오류가 발생했습니다.");
            return;
        }
        
        // ProductDTO 사용으로 변경
        for (ProductDTO product : menuList) {
            JLabel nameLabel = new JLabel(product.getPro_name());
            JLabel priceLabel = new JLabel(product.getPro_price() + "원");
            JTextField quantityField = new JTextField("0", 5);

            quantityFields.put(product.getPro_name(), quantityField);

            menuPanel.add(nameLabel);
            menuPanel.add(priceLabel);
            menuPanel.add(quantityField);
        }
        
        orderSummaryArea = new JTextArea(10, 30);
        orderSummaryArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(orderSummaryArea);

        JPanel summaryPanel = new JPanel(new BorderLayout());
        JPanel totalPanel = new JPanel(new GridLayout(2, 2, 10, 10));

        totalPriceLabel = new JLabel("총 금액: 0원");
        totalQuantityLabel = new JLabel("총 수량: 0개");

        totalPanel.add(totalPriceLabel);
        totalPanel.add(new JLabel());
        totalPanel.add(totalQuantityLabel);
        totalPanel.add(new JLabel());

        summaryPanel.add(totalPanel, BorderLayout.NORTH);
        summaryPanel.add(scrollPane, BorderLayout.CENTER);

        calculateButton = new JButton("계산하기");
        calculateButton.addActionListener(e -> calculateTotal());

        orderButton = new JButton("주문하기");
        orderButton.addActionListener(e -> placeOrder());

        mileageButton = new JButton("마일리지 조회 및 사용");
        mileageButton.addActionListener(e -> checkMileage());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(calculateButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(mileageButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(menuPanel), BorderLayout.NORTH);
        mainPanel.add(summaryPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void calculateTotal() {
        totalQuantity = 0;
        totalPrice = 0;

        try {
            for (String productName : quantityFields.keySet()) {
                JTextField quantityField = quantityFields.get(productName);
                int quantity = Integer.parseInt(quantityField.getText());

                if (quantity > 0) {
                    BaeckCafeDAO dao = new BaeckCafeDAO();
                    int price = dao.getMenuList()
                                   .stream()
                                   .filter(p -> p.getPro_name().equals(productName))
                                   .findFirst()
                                   .orElseThrow()
                                   .getPro_price();

                    totalQuantity += quantity;
                    totalPrice += price * quantity;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "수량은 숫자로 입력해주세요.");
            return;
        }

        totalPriceLabel.setText("총 금액: " + totalPrice + "원");
        totalQuantityLabel.setText("총 수량: " + totalQuantity + "개");

        StringBuilder orderSummary = new StringBuilder("주문 내역:\n");
        for (String productName : quantityFields.keySet()) {
            JTextField quantityField = quantityFields.get(productName);
            int quantity = Integer.parseInt(quantityField.getText());
            if (quantity > 0) {
                orderSummary.append(productName)
                            .append(": ")
                            .append(quantity)
                            .append("개\n");
            }
        }
        orderSummary.append("총 금액: ").append(totalPrice).append("원");
        orderSummaryArea.setText(orderSummary.toString());
    }

    private void placeOrder() {
        if (cus_phone == null || finalPrice == 0) {
            JOptionPane.showMessageDialog(this, "마일리지 조회 및 계산을 완료해주세요.");
            return;
        }

        BaeckCafeDAO dao = new BaeckCafeDAO();

        try {
            // 1. 고객 등록 확인 및 삽입
            if (!dao.selectCustomer(cus_phone)) {
                CustomerDTO newCustomer = new CustomerDTO();
                newCustomer.setCus_phone(cus_phone);
                newCustomer.setCus_point(0);
                newCustomer.setCus_date(new java.sql.Date(System.currentTimeMillis()));

                dao.insertCustomer(newCustomer);

                JOptionPane.showMessageDialog(this, "새 고객이 등록되었습니다.");
            }

            // 2. 고객 ID 확인
            String cus_id = dao.getCustomerId(cus_phone);
            if (cus_id == null) {
                JOptionPane.showMessageDialog(this, "고객 ID를 가져오는 데 실패했습니다.");
                return;
            }

            // 3. 주문 정보 저장
            OrdersDTO order = new OrdersDTO();
            order.setCus_id(cus_id);
            order.setTotalprice(finalPrice);
            order.setCard_payment("Y");
            order.setOrder_date(new java.sql.Date(System.currentTimeMillis()));

            dao.insertOrder(order);

            // 4. 상세 주문 정보 저장
            int orderNum = dao.getOrderNum(cus_id);
            for (String productName : quantityFields.keySet()) {
                JTextField quantityField = quantityFields.get(productName);
                int quantity = Integer.parseInt(quantityField.getText());

                if (quantity > 0) {
                    MiroticDTO detail = new MiroticDTO();
                    detail.setOrder_num(orderNum);

                    ProductDTO product = dao.getMenuList()
                                            .stream()
                                            .filter(p -> p.getPro_name().equals(productName))
                                            .findFirst()
                                            .orElseThrow();

                    detail.setPro_id(product.getPro_id());
                    detail.setQuantity(quantity);
                    detail.setUnit_price(product.getPro_price() * quantity);

                    dao.insertMirotic(detail);
                }
            }

            // 5. 마일리지 업데이트
            if (cus_pointUsed > 0) {
                dao.updateCus_point(cus_phone, -cus_pointUsed);
            } else {
                int mileageToAdd = (int) (finalPrice * 0.05);
                dao.updateCus_point(cus_phone, mileageToAdd);
                JOptionPane.showMessageDialog(this, "마일리지 " + mileageToAdd + "점이 적립되었습니다.");
            }

            JOptionPane.showMessageDialog(this, "주문이 완료되었습니다!");

            // UI 초기화
            for (JTextField field : quantityFields.values()) {
                field.setText("0");
            }
            totalPriceLabel.setText("총 금액: 0원");
            totalQuantityLabel.setText("총 수량: 0개");
            orderSummaryArea.setText("");
            cus_phone = null;
            cus_pointUsed = 0;
            totalPrice = 0;
            finalPrice = 0;
            totalQuantity = 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "주문 처리 중 오류가 발생했습니다: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "수량 입력이 잘못되었습니다.");
        }
    }



    private void checkMileage() {
        if (totalPrice == 0) {
            JOptionPane.showMessageDialog(this, "먼저 계산하기를 눌러주세요.");
            return;
        }

        String inputCusPhone = JOptionPane.showInputDialog(this, "휴대폰 번호를 입력하세요:");

        if (inputCusPhone == null || inputCusPhone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "휴대폰 번호를 입력해주세요.");
            return;
        }

        BaeckCafeDAO dao = new BaeckCafeDAO();

        try {
            // 고객 조회
            boolean isCustomer = dao.selectCustomer(inputCusPhone);

            if (!isCustomer) {
                int registerOption = JOptionPane.showConfirmDialog(this, "등록된 고객이 아닙니다. 등록하시겠습니까?", "고객 등록", JOptionPane.YES_NO_OPTION);
                if (registerOption == JOptionPane.YES_OPTION) {
                    CustomerDTO newCustomer = new CustomerDTO();
                    newCustomer.setCus_phone(inputCusPhone);
                    newCustomer.setCus_point(0);
                    newCustomer.setCus_date(new java.sql.Date(System.currentTimeMillis()));

                    dao.insertCustomer(newCustomer);
                    JOptionPane.showMessageDialog(this, "고객이 등록되었습니다.");
                } else {
                    return;
                }
            }

            cus_phone = inputCusPhone; // 전역 변수에 저장
            int currentMileage = dao.getCus_point(cus_phone);
            JOptionPane.showMessageDialog(this, "현재 마일리지: " + currentMileage + "점");

            // 마일리지 사용 여부 확인
            int useMileageOption = JOptionPane.showConfirmDialog(this, "마일리지를 사용하시겠습니까?", "마일리지 사용", JOptionPane.YES_NO_OPTION);

            if (useMileageOption == JOptionPane.YES_OPTION) {
                String mileageInput = JOptionPane.showInputDialog(this, "사용할 마일리지 점수를 입력하세요 (최대 " + currentMileage + "점):");

                try {
                    int requestedMileage = Integer.parseInt(mileageInput);

                    if (requestedMileage > currentMileage) {
                        JOptionPane.showMessageDialog(this, "보유한 마일리지보다 많이 입력하셨습니다.");
                        return;
                    } else if (requestedMileage > totalPrice) {
                        JOptionPane.showMessageDialog(this, "총 금액보다 많이 입력하셨습니다.");
                        cus_pointUsed = totalPrice;
                    } else if (requestedMileage < 0) {
                        JOptionPane.showMessageDialog(this, "0 이상의 값을 입력해주세요.");
                        return;
                    } else {
                        cus_pointUsed = requestedMileage;
                    }

                    finalPrice = totalPrice - cus_pointUsed; // 마일리지 차감 후 결제 금액

                    // 주문 내역 업데이트
                    StringBuilder orderSummary = new StringBuilder("주문 내역:\n");

                    for (String productName : quantityFields.keySet()) {
                        JTextField quantityField = quantityFields.get(productName);
                        int quantity = Integer.parseInt(quantityField.getText());
                        if (quantity > 0) {
                            int price = dao.getMenuList()
                                           .stream()
                                           .filter(p -> p.getPro_name().equals(productName))
                                           .findFirst()
                                           .orElseThrow()
                                           .getPro_price();
                            orderSummary.append(productName)
                                        .append(" x ")
                                        .append(quantity)
                                        .append("개 = ")
                                        .append(price * quantity)
                                        .append("원\n");
                        }
                    }

                    orderSummary.append("\n사용한 마일리지: ").append(cus_pointUsed).append("점");
                    orderSummary.append("\n마일리지 적용 후 결제 금액: ").append(finalPrice).append("원");

                    orderSummaryArea.setText(orderSummary.toString());
                    totalPriceLabel.setText("총 금액: " + finalPrice + "원");

                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "숫자를 입력해주세요.");
                    return;
                }
            } else {
                // 마일리지 사용하지 않음
                cus_pointUsed = 0;
                finalPrice = totalPrice;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "마일리지 조회 중 오류가 발생했습니다.");
        }
    }


    

	
	
	

	
	

	public static void main(String[] args) {
		new BaeckCafeMain();

	}
	}

