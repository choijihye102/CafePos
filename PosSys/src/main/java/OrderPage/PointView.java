package OrderPage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class PointView {
	private JFrame frame; 
	
	
	
	
	public String checkCus_point() {
	    String inputCusPhone = null;
	    inputCusPhone = JOptionPane.showInputDialog(frame, "휴대폰 번호를 입력하세요:");
	    System.out.println("입력받은 핸드폰번호 :"+ inputCusPhone);
	   
	    if (inputCusPhone == null || inputCusPhone.trim().isEmpty()||inputCusPhone =="") {
	        JOptionPane.showMessageDialog(frame, "휴대폰 번호를 입력해주세요.");
	        if(inputCusPhone == null ){//return;
	        	JOptionPane.showMessageDialog(frame, "다시 하세요");
	        	return inputCusPhone;
	        }
	    }
	    System.out.println("checkCus_point()이벤트 처리 중: "+ inputCusPhone.toString());
	    return inputCusPhone;
	   
}
	
	public String PayCash() {
	    String inputGet = JOptionPane.showInputDialog(frame, "고객에게 받은 금액을 입력하세요:");
	    System.out.println("IF에서 받은 금액 :"+ inputGet);
	    
	    
	    if (inputGet == null || inputGet.trim().isEmpty()) {
	        JOptionPane.showMessageDialog(frame, "입력되지 않았습니다.");
	        //return;
	    }
	    return inputGet;
	}
	    
	    public void GiveCash(int getCash, int finalPrice)  {
	    	int giveCash = finalPrice - getCash;
	    	
	    	 NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.KOREA);
	         String formattedNumber = formatter.format(giveCash);
	         
	       if(finalPrice>getCash) {
	    	   JOptionPane.showMessageDialog(frame, "결제금액보다 적은 금액이 입력되었습니다. 다시 시작하세요");
	    	   return;
	       }
		   JOptionPane.showMessageDialog(frame, "거스름돈 :"  +formattedNumber + "원");
		 
		   
		
		    System.out.println("거스름돈 :"+ formattedNumber);
	    }
	
}












