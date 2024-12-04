package CommunityPage;


import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.text.SimpleDateFormat;

public class Community_View {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tabbed Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // 탭 패널 생성
        JTabbedPane tabbedPane = new JTabbedPane();

        // 공지사항 패널 생성
        JPanel noticePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"글쓴이", "글제목", "날짜"};
        Object[][] data = {
                {"나다", "첫 번째 공지사항", "24-11-12"},
                {"나다", "두 번째 공지사항", "24-02-25"}
        };
        DefaultTableModel noticeModel = new DefaultTableModel(data, columnNames);
        JTable noticeTable = new JTable(noticeModel);

        noticeTable.getColumnModel().getColumn(0).setPreferredWidth(30);
        noticeTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        noticeTable.getColumnModel().getColumn(2).setPreferredWidth(30);

        // 공지사항 테이블 셀 높이 2배로 설정
        noticeTable.setRowHeight(noticeTable.getRowHeight() * 2);

        JButton writeButton = new JButton("글쓰기");
        JPanel noticeButtonPanel = new JPanel();
        noticeButtonPanel.add(writeButton);

        noticePanel.add(new JScrollPane(noticeTable), BorderLayout.CENTER);
        noticePanel.add(noticeButtonPanel, BorderLayout.SOUTH);

        // 대타구하기 패널 생성
        JPanel substitutePanel = new JPanel(new BorderLayout());

        // 상단 섹션
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 25));
        JLabel dateLabel = new JLabel("날짜:");
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yy-MM-dd");

        JLabel startTimeLabel = new JLabel("시작 시간:");
        JLabel endTimeLabel = new JLabel("마감 시간:");

        String[] times = {
                "AM 12:00", "AM 12:30", "AM 1:00", "AM 1:30", "AM 2:00", "AM 2:30",
                "PM 12:00", "PM 12:30", "PM 1:00", "PM 1:30", "PM 2:00", "PM 2:30"
        };

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
        String[] substituteColumnNames = {"날짜", "시작 시간", "마감 시간", "글쓴이", "공란"};
        DefaultTableModel substituteModel = new DefaultTableModel(null, substituteColumnNames);
        JTable substituteTable = new JTable(substituteModel);

        substituteTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        substituteTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        substituteTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        substituteTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        substituteTable.getColumnModel().getColumn(4).setPreferredWidth(60);

        substituteTable.setRowHeight(substituteTable.getRowHeight() * 2);

        substituteTable.getColumn("공란").setCellRenderer(new ButtonRenderer());
        substituteTable.getColumn("공란").setCellEditor(new ButtonEditor(new JCheckBox(), substituteModel));

        JScrollPane tableScrollPane = new JScrollPane(substituteTable);
        bottomPanel.add(tableScrollPane, BorderLayout.CENTER);

        selectButton.addActionListener(e -> {
            if (dateChooser.getDate() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
                String date = sdf.format(dateChooser.getDate());
                String startTime = (String) startTimeBox.getSelectedItem();
                String endTime = (String) endTimeBox.getSelectedItem();
                substituteModel.addRow(new Object[]{date, startTime, endTime, "글쓴이", "찜"});
            } else {
                JOptionPane.showMessageDialog(frame, "날짜를 선택해주세요.");
            }
        });

        substitutePanel.add(topPanel, BorderLayout.NORTH);
        substitutePanel.add(bottomPanel, BorderLayout.CENTER);

        tabbedPane.addTab("공지사항", noticePanel);
        tabbedPane.addTab("대타구하기", substitutePanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    static class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value == null ? "" : value.toString());
            return this;
        }
    }

    static class ButtonEditor extends DefaultCellEditor {
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
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText((value == null) ? "" : value.toString());
            clicked = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (clicked) {
                int row = tableModel.getRowCount() - 1; // 마지막 행 가져오기
                String date = (String) tableModel.getValueAt(row, 0);
                JOptionPane.showMessageDialog(button, date + "에 대타 연결이 완료되었습니다!");
                showTransparentImage("/spark.png");
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