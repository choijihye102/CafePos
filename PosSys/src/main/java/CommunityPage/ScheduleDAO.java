package CommunityPage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {

	Connection con = null; // DB와 연결하는 객체
	PreparedStatement pstmt = null; // SQL문을 DB에 전송하는 객체
	ResultSet rs = null; // SQL문 실행 결과를 가지고 있는 객체
	String sql = null;

	void connect() {

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String password = "1234";

		try {
			// 1. 접속할 오라클 데이터베이스 드라이버를 메모리에 올리자. - 동적 작업
			Class.forName(driver);

			// 2. 오라클 데이터베이스와 연결을 시도.
			con = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // connect() 메서드 end

	// INSERT 메서드
	public void insertSchedule(String selectedDate, String startTime, String endTime, String applicant) {
		connect();
		String sql = "INSERT INTO schedules (selected_date, start_time, end_time, applicant) VALUES (TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";
		try (PreparedStatement pstmt = con.prepareStatement(sql)) {

			pstmt.setString(1, selectedDate);
			pstmt.setString(2, startTime);
			pstmt.setString(3, endTime);
			pstmt.setString(4, applicant);

			pstmt.executeUpdate();
			System.out.println("스케줄이 성공적으로 추가되었습니다.");

			
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("스케줄 추가 중 오류 발생: " + e.getMessage());
		}
	}

	// SELECT 메서드
	public List<ScheduleDTO> getAllSchedules() {
		connect();
		List<ScheduleDTO> schedules = new ArrayList<>();
		String sql = "SELECT schedule_id, selected_date, start_time, end_time, applicant, confirmer FROM schedules";

		try (

				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {

			while (rs.next()) {
				ScheduleDTO schedule = new ScheduleDTO();
				schedule.setScheduleId(rs.getInt("schedule_id"));
				schedule.setSelectedDate(rs.getDate("selected_date"));
				schedule.setStartTime(rs.getString("start_time"));
				schedule.setEndTime(rs.getString("end_time"));
				schedule.setApplicant(rs.getString("applicant"));
				schedule.setConfirmer(rs.getString("confirmer"));

				schedules.add(schedule);
			}

			rs.close();
			pstmt.close();
			con.close();

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("스케줄 조회 중 오류 발생: " + e.getMessage());
		}

		return schedules;
	}
	
	public void updateConfirmer(int scheduleId, String confirmer) {
	    connect(); // 데이터베이스 연결 메서드 호출
	    String sql = "UPDATE schedules SET confirmer = ? WHERE schedule_id = ?";

	    try (PreparedStatement pstmt = con.prepareStatement(sql)) {

	        // 파라미터 설정
	        pstmt.setString(1, confirmer);
	        pstmt.setInt(2, scheduleId);

	        // 업데이트 실행
	        int rowsUpdated = pstmt.executeUpdate();

	        if (rowsUpdated > 0) {
	            System.out.println("스케줄 ID " + scheduleId + "의 confirmer가 성공적으로 업데이트되었습니다.");
	        } else {
	            System.out.println("스케줄 ID " + scheduleId + "에 해당하는 데이터가 없습니다.");
	        }

	        pstmt.close();
	        con.close();

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.out.println("스케줄 업데이트 중 오류 발생: " + e.getMessage());
	    }
	}
}
