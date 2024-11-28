import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class InsertProducts {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:xe"; // Oracle 연결 URL
    private static final String DB_USER = "your_username"; // 사용자 이름
    private static final String DB_PASSWORD = "your_password"; // 비밀번호

    public static void main(String[] args) {
        String sql = "INSERT INTO product (pro_id, pro_name, pro_price, pro_type) VALUES (?, ?, ?, ?)";
        Object[][] products = {
            {1, "아이스 아메리카노", 2500, "1"},
            {2, "바닐라 라떼", 3500, "1"},
            {3, "카페라떼", 3500, "1"},
            {4, "카라멜 마끼아또", 4000, "1"},
            {5, "토마토 파스타", 7000, "2"},
            {6, "라구 파스타", 8500, "2"},
            {7, "파질페스토 파스타", 8000, "2"},
            {8, "알리오올리오", 8500, "2"},
            {9, "레몬에이드", 4000, "3"},
            {10, "블루에이드", 4000, "3"},
            {11, "사이다", 2000, "3"},
            {12, "콜라", 2000, "3"},
            {13, "치즈 케이크", 4000, "4"},
            {14, "초코 케이크", 4000, "4"},
            {15, "에그타르트", 2500, "4"},
            {16, "휘낭시에", 2000, "4"},
            {17, "리코타샐러드", 2500, "5"},
            {18, "케이준치킨샐러드", 2500, "5"},
            {19, "양송이 크림스프", 2500, "5"},
            {20, "감자튀김", 2500, "5"}
        };

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = con.prepareStatement(sql)) {

            for (Object[] product : products) {
                pstmt.setInt(1, (int) product[0]);
                pstmt.setString(2, (String) product[1]);
                pstmt.setInt(3, (int) product[2]);
                pstmt.setString(4, (String) product[3]);
                pstmt.executeUpdate();
            }

            System.out.println("Data inserted successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

