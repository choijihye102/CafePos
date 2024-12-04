package OrderPage;

public class MiroticDTO {

    private int detail_id;  // 세부 ID
    private int order_num;  // 주문 번호
    private int pro_id;     // 제품 ID
    private int quantity;   // 수량


    // 기본 생성자
    public MiroticDTO() {}

    // 매개변수 있는 생성자
    public MiroticDTO(int detail_id, int order_num, int pro_id, int quantity, String cus_phone, int used_point) {
        this.detail_id = detail_id;
        this.order_num = order_num;
        this.pro_id = pro_id;
        this.quantity = quantity;

    }

    // Getter 및 Setter 메서드
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

  

    @Override
    public String toString() {
        return "MiroticDTO [detail_id=" + detail_id + ", order_num=" + order_num + ", pro_id=" + pro_id
                + ", quantity=" + quantity +  "]";
    }
}
