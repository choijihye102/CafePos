package Admin_management;

public class ProductDTO {
	
	private int pro_id;
	private String pro_name;
	private int pro_price;
	private String pro_type;
	
	public ProductDTO() {}
	
	public ProductDTO(int pro_id, String pro_name, int pro_price, String pro_type ) {
		
		this.pro_id = pro_id;
		this.pro_name = pro_name;
		this.pro_price = pro_price;
		this.pro_type = pro_type;
		
	}
	

	public int getPro_id() {
		return pro_id;
	}

	public void setPro_id(int pro_id) {
		this.pro_id = pro_id;
	}

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public int getPro_price() {
		return pro_price;
	}

	public void setPro_price(int pro_price) {
		this.pro_price = pro_price;
	}

	public String getPro_type() {
		return pro_type;
	}

	public void setPro_type(String pro_type) {
		this.pro_type = pro_type;
	}
	
	
	
	
	

}
