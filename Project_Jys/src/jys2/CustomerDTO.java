package jys2;

import java.sql.Date;

public class CustomerDTO {
	
	private int cus_id;
	private String cus_phone;
	private int cus_point;
	private Date cus_date;
	
	public CustomerDTO() {}

	public int getCus_id() {
		return cus_id;
	}

	public void setCus_id(int cus_id) {
		this.cus_id = cus_id;
	}

	public String getCus_phone() {
		return cus_phone;
	}

	public void setCus_phone(String cus_phone) {
		this.cus_phone = cus_phone;
	}

	public int getCus_point() {
		return cus_point;
	}

	public void setCus_point(int cus_point) {
		this.cus_point = cus_point;
	}

	public Date getCus_date() {
		return cus_date;
	}

	public void setCus_date(Date cus_date) {
		this.cus_date = cus_date;
	}

	@Override
    public String toString() {
        return "CustomerDTO [cus_id=" + cus_id + ", cus_phone=" + cus_phone +
               ", cus_point=" + cus_point + ", cus_date=" + cus_date + "]";
        
	}
	
	
	

}
