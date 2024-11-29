package jys;

import java.util.Map;

public class MiroticDTO {
	
	private int detail_id;
	private int order_num;
	private int pro_id;
	private int quantity;
	private int unit_price;
	
	private Map<String, Integer> orderItems; // 메뉴 이름과 수량
	
public MiroticDTO() {}

public Map<String, Integer> getOrderItems() {
    return orderItems;
}

public void setOrderItems(Map<String, Integer> orderItems) {
    this.orderItems = orderItems;
}


public int getDetail_id() {
	return detail_id;
}

public void setDetail_id(int detail_id) {
	this.detail_id = detail_id;
}

public int getOrder_num() {
	return order_num;
}

public void setOrder_num(int order_num) {
	this.order_num = order_num;
}

public int getPro_id() {
	return pro_id;
}

public void setPro_id(int pro_id) {
	this.pro_id = pro_id;
}

public int getQuantity() {
	return quantity;
}

public void setQuantity(int quantity) {
	this.quantity = quantity;
}

public int getUnit_price() {
	return unit_price;
}

public void setUnit_price(int unit_price) {
	this.unit_price = unit_price;
}	
	
	




}
