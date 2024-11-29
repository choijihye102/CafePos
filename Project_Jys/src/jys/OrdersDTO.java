package jys;

import java.sql.Date;

public class OrdersDTO {
	
	private int order_num;
	private String cus_id;
	private int total_price;
	private String card_payment;
	private Date order_date;

	public OrdersDTO() {}

	public int getOrder_num() {
		return order_num;
	}

	public void setOrder_num(int order_num) {
		this.order_num = order_num;
	}

	public String getCus_id() {
		return cus_id;
	}

	public void setCus_id(String cus_id) {
		this.cus_id = cus_id;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotalprice(int totalprice) {
		this.total_price = totalprice;
	}

	public String getCard_payment() {
		return card_payment;
	}

	public void setCard_payment(String card_payment) {
		this.card_payment = card_payment;
	}

	public Date getOrder_date() {
		return order_date;
	}

	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	
	
	
	
	
}
