package CommunityPage;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.toedter.calendar.JDateChooser;

import Product_v2.UserDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Community_View {
	DefaultTableModel substituteModel;
	JTable substituteTable;
	ScheduleDAO dao = new ScheduleDAO();
	ScheduleDTO dto = new ScheduleDTO();
	List<ScheduleDTO> list = new ArrayList<ScheduleDTO>();
	String name = UserDTO.getInstance().getLoggedInUserName();
//	String name ="test";
	public static void main(String[] args) {

		Community_View c = new Community_View();
		c.show();
	}

	public void show() {
		JFrame frame = new JFrame("Tabbed Application");
		
		 // 프레임이 닫힐 때 새 클래스 호출
	    frame.addWindowListener(new java.awt.event.WindowAdapter() {
	        @Override
	        public void windowClosing(java.awt.event.WindowEvent e) {
	            // 새 클래스 생성자 호출
	            Product_v2.main window = new Product_v2.main();
	            window.frame.setVisible(true);
	            frame.dispose(); // 현재 창 종료
	        }
	    });
	    
	
		frame.setSize(800, 600);

		// 탭 패널 생성
		JTabbedPane tabbedPane = new JTabbedPane();

		// 대타구하기 패널 생성
		JPanel substitutePanel = new JPanel(new BorderLayout());

		// 상단 섹션
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 25));
		JLabel dateLabel = new JLabel("날짜:");
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setDateFormatString("yy-MM-dd");

		JLabel startTimeLabel = new JLabel("시작 시간:");
		JLabel endTimeLabel = new JLabel("마감 시간:");

		String[] times = { "AM 12:00", "AM 12:30", "AM 1:00", "AM 1:30", "AM 2:00", "AM 2:30", "PM 12:00", "PM 12:30",
				"PM 1:00", "PM 1:30", "PM 2:00", "PM 2:30" };

		JComboBox<String> startTimeBox = new JComboBox<>(times);
		JComboBox<String> endTimeBox = new JComboBox<>(times);
		JButton selectButton = new JButton("선택하기");

		topPanel.add(dateLabel);
		topPanel.add(dateChooser);
		topPanel.add(startTimeLabel);
		topPanel.add(startTimeBox);
		topPanel.add(endTimeLabel);
		topPanel.add(endTimeBox);
		topPanel.add(selectButton);

		// 하단 섹션
		JPanel bottomPanel = new JPanel(new BorderLayout());
		String[] substituteColumnNames = { "글 id","날짜", "시작 시간", "마감 시간", "신청자","대체자","찜하기" };
		substituteModel = new DefaultTableModel(null, substituteColumnNames);
		substituteTable = new JTable(substituteModel);
		
		substituteTable.getColumnModel().getColumn(0).setPreferredWidth(60);
		substituteTable.getColumnModel().getColumn(1).setPreferredWidth(60);
		substituteTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		substituteTable.getColumnModel().getColumn(3).setPreferredWidth(150);
		substituteTable.getColumnModel().getColumn(4).setPreferredWidth(100);
		substituteTable.getColumnModel().getColumn(5).setPreferredWidth(100);
		substituteTable.getColumnModel().getColumn(6).setPreferredWidth(60);

		substituteTable.setRowHeight(substituteTable.getRowHeight() * 2);
		

		substituteTable.getColumn("찜하기").setCellRenderer(new ButtonRenderer());
		substituteTable.getColumn("찜하기").setCellEditor(new ButtonEditor(new JCheckBox(), substituteModel));

		// DB에서 테이블가져와서 열 세팅

		populateTable(substituteModel);

		JScrollPane tableScrollPane = new JScrollPane(substituteTable);
		bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

		selectButton.addActionListener(e -> {
			if (dateChooser.getDate() != null) {
				SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
				String date = sdf.format(dateChooser.getDate());
				String startTime = (String) startTimeBox.getSelectedItem();
				String endTime = (String) endTimeBox.getSelectedItem();
				
				dao.insertSchedule(date ,startTime, endTime, name); 
				
				//UserDTO.getInstance().getLoggedInUserName().toString()) 파라미터 추가되어야함 
				populateTable(substituteModel);
				
			} else {
				JOptionPane.showMessageDialog(frame, "날짜를 선택해주세요.");
			}
		});

		substitutePanel.add(topPanel, BorderLayout.NORTH);
		substitutePanel.add(bottomPanel, BorderLayout.CENTER);

		tabbedPane.addTab("대타구하기", substitutePanel);

		frame.add(tabbedPane);
		frame.setVisible(true);
	}

	public void populateTable(DefaultTableModel tableModel) {
		ScheduleDAO dao = new ScheduleDAO();

		// DAO를 통해 스케줄 데이터를 가져옴
		List<ScheduleDTO> list = dao.getAllSchedules();

		// 테이블 모델 초기화 (기존 데이터 제거)
		tableModel.setRowCount(0);

		// 리스트 데이터를 테이블 모델에 추가
		for (ScheduleDTO schedule : list) {
			String s = schedule.getConfirmer();
			String b = null;
			
	
			if(schedule.getConfirmer()== null) {
				s = "없음";
				b = "찜하기";
			}else {
				b = "연결완료";
			}
			Object[] rowData = { schedule.getScheduleId(), schedule.getSelectedDate(), schedule.getStartTime(),
					schedule.getEndTime(),schedule.getApplicant(), s,b};

			// 테이블 모델에 행 추가
			tableModel.addRow(rowData);
		}

	}

	// 새로운 버튼 클래스 시작

	class ButtonRenderer extends JButton implements TableCellRenderer {
		public ButtonRenderer() {
			setOpaque(true);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			setText(value == null ? "" : value.toString());
			return this;
		}
	}

	class ButtonEditor extends DefaultCellEditor {
		private JButton button;
		private boolean clicked;
		private DefaultTableModel tableModel;

		public ButtonEditor(JCheckBox checkBox, DefaultTableModel tableModel) {
			super(checkBox);
			this.tableModel = tableModel;
			button = new JButton();
			button.setOpaque(true);

			button.addActionListener(e -> fireEditingStopped());
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			button.setText((value == null) ? "" : value.toString());
			clicked = true;
			return button;
		}

		//찜 버튼 이벤트 .. 누른 셀 가져오긴하는데... return은 버튼에 set text됨 
		@Override 
		public Object getCellEditorValue() {
			if (clicked) {
				int row = substituteTable.getSelectedRow(); //선택된 행 가져오기
				String date = (String) tableModel.getValueAt(row, 4);
				int mine =  (int) tableModel.getValueAt(row, 0);
				 System.out.println("date :" + date );
				 System.out.println("mine :" + mine );
				// DB처리
				
				if (name.equals(date)) {
					JOptionPane.showMessageDialog(button, "본인이 작성한 건에 대한 대타신청은 불가합니다.");
				}else {
					dao.updateConfirmer(mine,name);
					JOptionPane.showMessageDialog(button, "대타신청 완료되었습니다.");
					populateTable(substituteModel);
				}
				
			}
			clicked = false;
			return "찜 완료";
		}

		private void showTransparentImage(String imagePath) {
			JWindow window = new JWindow();
			window.setBackground(new Color(0, 0, 0, 0));

			JLabel label = new JLabel();
			label.setHorizontalAlignment(SwingConstants.CENTER);

			try {
				URL imageUrl = Community_View.class.getResource(imagePath);
				if (imageUrl != null) {
					label.setIcon(new ImageIcon(imageUrl));
				} else {
					System.err.println("이미지를 찾을 수 없습니다.");
					return;
				}
			} catch (Exception e) {
				System.err.println("이미지를 로드하는 중 오류 발생: " + e.getMessage());
				return;
			}

			window.add(label);

			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			int width = 400;
			int height = 300;
			int x = (screenSize.width - width) / 2;
			int y = (screenSize.height - height) / 2;
			window.setBounds(x, y, width, height);

			window.setVisible(true);

			Timer timer = new Timer(3000, e -> window.dispose());
			timer.setRepeats(false);
			timer.start();
		}

		@Override
		public boolean stopCellEditing() {
			clicked = false;
			return super.stopCellEditing();
		}

	}

}
