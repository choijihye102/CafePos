package Admin_management;

import java.sql.Date;

public class Orders2DTO {
	

	private int total_cash_payment;
	public int getTotal_cash_payment() {
		return total_cash_payment;
	}
	public void setTotal_cash_payment(int total_cash_payment) {
		this.total_cash_payment = total_cash_payment;
	}
	public int getTotal_card_payment() {
		return total_card_payment;
	}
	public void setTotal_card_payment(int total_card_payment) {
		this.total_card_payment = total_card_payment;
	}
	public int getTotal_price_sum() {
		return total_price_sum;
	}
	public void setTotal_price_sum(int total_price_sum) {
		this.total_price_sum = total_price_sum;
	}
	public int getTotal_orders() {
		return total_orders;
	}
	public void setTotal_orders(int total_orders) {
		this.total_orders = total_orders;
	}
	private int total_card_payment;
	private int total_price_sum;
	private int total_orders;
	private int total_used_points;
	public int getTotal_used_points() {
		return total_used_points;
	}
	public void setTotal_used_points(int total_used_points) {
		this.total_used_points = total_used_points;
	}
	String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	int totalReturn;
	public int gettotalReturn() {
		return totalReturn;
	}
	public void settotalReturn(int date) {
		this.totalReturn = date;
	}
}
	