package GUI;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import entity.LoaiPhong;
import entity.Phong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyDatPhong_GUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	RoundedButton btnDat;
	private Phong_Dao dsPhong;
	private LoaiPhong_Dao dsLoaiPhong;
//sửa thử
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				QuanLyDatPhong_GUI frame = new QuanLyDatPhong_GUI();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public QuanLyDatPhong_GUI() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout(null);
		setContentPane(contentPane);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();
		// Panel North (Thanh tiêu đề)
		CustomRoundedPanel panelNorth = new CustomRoundedPanel(0, 0, 0, 0);
		panelNorth.setBackground(Color.WHITE);
		panelNorth.setBounds(0, 0, screenWidth, (int) (screenHeight * 0.15));
		panelNorth.setLayout(new BorderLayout());
		contentPane.add(panelNorth);

		// Tạo panel con để chứa logo và tiêu đề
		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(true); // Đảm bảo panel này không trong suốt
		leftPanel.setBackground(Color.LIGHT_GRAY); // Thử thay màu nền để kiểm tra
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Đảm bảo các phần tử được căn chỉnh dọc
		panelNorth.add(leftPanel, BorderLayout.WEST);

		// Logo khách sạn (Tự động điều chỉnh kích thước theo màn hình)
		int logoSize = (int) (screenHeight * 0.15); // Điều chỉnh kích thước logo phù hợp
		ImageIcon logoIcon = new ImageIcon(
				new ImageIcon("img/logoLux.png").getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH));
		JLabel logo = new JLabel(logoIcon);
		leftPanel.add(logo);

		// Panel West
		CustomRoundedPanel panelWest = new CustomRoundedPanel(15, 15, 15, 15);
		int westWidth = (int) (screenWidth * 0.18); // Set width to 18%
		panelWest.setBounds(8, (int) (screenHeight * 0.15) + 5, westWidth - 3,
				screenHeight - (int) (screenHeight * 0.15) - 32);
		panelWest.setBackground(Color.WHITE);
		panelWest.setLayout(null); // Use null layout for custom positioning

		// Font for Titles
		Font ft = new Font("Arial", Font.BOLD, 18);

		// Panel chứa ngày tháng và icon lịch
		CustomRoundedPanel panelDate = new CustomRoundedPanel(10, 10, 10, 10);
		panelDate.setBounds(20, 10, 180, 50);
		panelDate.setBackground(Color.WHITE);
		panelDate.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelDate.setLayout(null);
		panelWest.add(panelDate);

		// Label "Chọn ngày"
		JLabel lblDate = new JLabel("Chọn ngày");
		lblDate.setFont(new Font("Arial", Font.PLAIN, 12));
		lblDate.setBounds(10, 5, 100, 15);
		panelDate.add(lblDate);

		// Label ngày tháng
		JLabel lblSelectedDate = new JLabel("15/3/2025");
		lblSelectedDate.setFont(new Font("Arial", Font.BOLD, 22));
		lblSelectedDate.setBounds(10, 20, 100, 25);
		panelDate.add(lblSelectedDate);

		// Biểu tượng lịch
		ImageIcon calendarIcon = new ImageIcon(
				new ImageIcon("img/lich.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
		JLabel lblCalendarIcon = new JLabel(calendarIcon);
		lblCalendarIcon.setBounds(140, 10, 30, 30);
		panelDate.add(lblCalendarIcon);

		// Title for "Trạng thái"
		JLabel lblTitle = new JLabel("Trạng thái");
		lblTitle.setForeground(Color.decode("#3B9AEE"));
		lblTitle.setBounds(20, 70, 150, 25); // Adjust position and size
		lblTitle.setFont(ft);
		panelWest.add(lblTitle);

		// Radio Buttons for "Trạng thái"
		JRadioButton rbPhongTrong = new JRadioButton("Phòng trống");
		rbPhongTrong.setBounds(35, 100, 150, 25);
		rbPhongTrong.setBackground(Color.WHITE);

		JRadioButton rbPhongDaDat = new JRadioButton("Phòng đã đặt");
		rbPhongDaDat.setBounds(35, 130, 150, 25);
		rbPhongDaDat.setBackground(Color.WHITE);

		// Group the radio buttons
		ButtonGroup groupStatus = new ButtonGroup();
		groupStatus.add(rbPhongTrong);
		groupStatus.add(rbPhongDaDat);
		panelWest.add(rbPhongTrong);
		panelWest.add(rbPhongDaDat);

		// Title for "Loại phòng"
		JLabel lblRoomType = new JLabel("Loại phòng");
		lblRoomType.setBounds(20, 170, 150, 25);
		lblRoomType.setForeground(Color.decode("#3B9AEE"));
		lblRoomType.setFont(ft);
		panelWest.add(lblRoomType);
		
		// Checkboxes for room types
		dsLoaiPhong = new LoaiPhong_Dao();
		ArrayList<LoaiPhong> danhSachLoaiPhong = dsLoaiPhong.getAllLoaiPhong(); // Lấy danh sách loại phòng

		// Tạo JCheckBox cho từng loại phòng
		int yPosition = 200; // Vị trí bắt đầu

		for (LoaiPhong loaiPhong : danhSachLoaiPhong) {
		    JCheckBox cbLoaiPhong = new JCheckBox(loaiPhong.getTenLoai());  // Tạo checkbox cho mỗi loại phòng
		    cbLoaiPhong.setBounds(35, yPosition, 200, 25);  // Đặt vị trí và kích thước
		    cbLoaiPhong.setBackground(Color.WHITE);  // Đặt màu nền
		    
		    // Thêm vào panel (hoặc container phù hợp)
		    panelWest.add(cbLoaiPhong);
		    
		    // Tăng vị trí y để các checkbox không bị chồng lên nhau
		    yPosition += 30;
		}

		// Nếu bạn cần thêm checkbox "Tất cả các loại phòng" nữa
		JCheckBox cbAllRooms = new JCheckBox("Tất cả các loại phòng");
		cbAllRooms.setBounds(35, yPosition, 200, 25);
		cbAllRooms.setBackground(Color.WHITE);
		panelWest.add(cbAllRooms);


		// Add panel to content pane
		contentPane.add(panelWest);
		// Panel Center
		CustomRoundedPanel panelCenter = new CustomRoundedPanel(15, 15, 15, 15);
		int centerWidth = screenWidth - westWidth - 20;
		int centerHeight = screenHeight - (int) (screenHeight * 0.89) - 32;
		panelCenter.setBounds(westWidth + 10, (int) (screenHeight * 0.15), centerWidth, centerHeight);
		panelCenter.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelCenter.setBackground(Color.LIGHT_GRAY);

		// Create a bold font
		Font font = new Font("Arial", Font.BOLD, 16);

		// Create rounded buttons and set their font
		btnDat= new RoundedButton("Đặt phòng", 20);
		btnDat.addActionListener(this);
		btnDat.setPreferredSize(new Dimension(150, 40));
		btnDat.setFont(font); // Set bold font
		panelCenter.add(btnDat);
		

		RoundedButton btnDoi = new RoundedButton("Đổi phòng", 20);
		btnDoi.setPreferredSize(new Dimension(150, 40));
		btnDoi.addActionListener(null);
		btnDoi.setFont(font); // Set bold font
		panelCenter.add(btnDoi);

		RoundedButton btnTra = new RoundedButton("Trả phòng", 20);
		btnTra.setPreferredSize(new Dimension(150, 40));
		btnTra.setFont(font); // Set bold font
		panelCenter.add(btnTra);

		RoundedButton btnHuy = new RoundedButton("Hủy đơn đặt phòng", 20);
		btnHuy.setPreferredSize(new Dimension(230, 40));
		btnHuy.setFont(font); // Set bold font
		panelCenter.add(btnHuy);

		contentPane.add(panelCenter);

		// Khởi tạo panel South
		CustomRoundedPanel panelSouth = new CustomRoundedPanel(15, 15, 15, 15);

		// Ví dụ dữ liệu phòng giả lập theo các loại phòng
		// Giả sử dsPhong là đối tượng của lớp Phong_Dao
		dsPhong = new Phong_Dao();

		// Tạo một Map tổng hợp để lưu trữ các phòng theo loại
		Map<String, ArrayList<Phong>> roomCategories = new HashMap<>();

		// Duyệt qua từng loại phòng trong danh sách danhSachLoaiPhong
		for (LoaiPhong loaiPhong : danhSachLoaiPhong) {
		    // Lấy danh sách phòng theo loại từ phương thức getDanhSachPhongTheoLoai
		    ArrayList<Phong> rooms = dsPhong.getDanhSachPhongTheoLoai(loaiPhong.getMaLoaiPhong());

		    // Thêm danh sách phòng vào Map, với key là tên loại phòng
		    roomCategories.put(loaiPhong.getTenLoai(), rooms);
		}

		// Ví dụ duyệt qua và hiển thị thông tin phòng
		for (Map.Entry<String, ArrayList<Phong>> entry : roomCategories.entrySet()) {
		    String roomType = entry.getKey();  // Tên loại phòng
		    ArrayList<Phong> rooms = entry.getValue();  // Danh sách phòng

		    System.out.println("Loại phòng: " + roomType);
		    for (Phong room : rooms) {
		        // Giả sử Phong có phương thức getTenPhong và getTrangThai
		        System.out.println("  Phòng: " + room.getSoPhong() + " - Trạng thái: " + room.getTrangThai());
		    }
		}



		// Thiết lập các thông số cho ô phòng
		int roomWidth = 200;
		int roomHeight = 150;
		int horizontalSpacing = 20;
		int verticalSpacing = 10;

		panelSouth.setBackground(Color.WHITE);
		panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout để sắp xếp theo chiều dọc

		// Duyệt qua các loại phòng và tạo từng phần tương ứng
		for (Entry<String, ArrayList<Phong>> entry : roomCategories.entrySet()) {
		    String roomType = entry.getKey();
		    List<Phong> rooms = entry.getValue();

		    // Tạo JPanel để chứa tiêu đề và căn lề trái
		    JPanel titlePanel = new JPanel();
		    titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0)); // Căn lề trái, không có khoảng cách

		    // Tạo JLabel cho tiêu đề loại phòng
		    JLabel lblRoomInfo = new JLabel(roomType);
		    lblRoomInfo.setFont(new Font("Arial", Font.BOLD, 20));
		    lblRoomInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Thêm khoảng cách trên và dưới

		    // Thêm JLabel vào titlePanel
		    titlePanel.add(lblRoomInfo);

		    // Thêm titlePanel vào panelSouth
		    panelSouth.add(titlePanel);
		    int roomsPerRow = 4;
		    int numberOfRooms = rooms.size();
		    int numberOfRows = (int) Math.ceil((double) numberOfRooms / roomsPerRow);

		    JPanel roomGridPanel = new JPanel();
		    roomGridPanel.setLayout(new GridLayout(numberOfRows, roomsPerRow, horizontalSpacing, verticalSpacing));
		    roomGridPanel.setBackground(Color.WHITE);

		    for (Phong room : rooms) {
		        RoomPanel roomPanel = new RoomPanel(room, "Phòng trống", 0);
		        roomPanel.setPreferredSize(new Dimension(roomWidth, roomHeight));
		        roomGridPanel.add(roomPanel);
		    }

		    panelSouth.add(roomGridPanel);

		}

		// Tạo JScrollPane để thêm thanh cuộn cho panelSouth
		JScrollPane scrollPane = new JScrollPane(panelSouth);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Hiển thị thanh cuộn dọc luôn
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Hiển thị thanh cuộn
																								// ngang khi cần thiết

		// Thêm scrollPane vào contentPane
		scrollPane.setBounds(westWidth + 10, (int) (screenHeight * 0.15) + centerHeight + 3, centerWidth,
				screenHeight - (int) (screenHeight * 0.15) - 50);
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

	}

	class RoundedButton extends JButton {
		private int radius;

		public RoundedButton(String text, int radius) {
			super(text);
			this.radius = radius;
			setContentAreaFilled(false); // Prevent default button rendering
			setFocusPainted(false);
			setBorderPainted(false);
			setBackground(Color.WHITE); // Set background color
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
			super.paintComponent(g);
		}
	}

	class CustomRoundedPanel extends JPanel {
		private int topLeft, topRight, bottomLeft, bottomRight;

		public CustomRoundedPanel(int topLeft, int topRight, int bottomLeft, int bottomRight) {
			this.topLeft = topLeft;
			this.topRight = topRight;
			this.bottomLeft = bottomLeft;
			this.bottomRight = bottomRight;
			setOpaque(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			int width = getWidth();
			int height = getHeight();
			g2.setColor(getBackground());

			GeneralPath path = new GeneralPath();
			path.moveTo(topLeft, 0);
			path.lineTo(width - topRight, 0);
			if (topRight > 0) {
				path.quadTo(width, 0, width, topRight);
			}
			path.lineTo(width, height - bottomRight);
			if (bottomRight > 0) {
				path.quadTo(width, height, width - bottomRight, height);
			}
			path.lineTo(bottomLeft, height);
			if (bottomLeft > 0) {
				path.quadTo(0, height, 0, height - bottomLeft);
			}
			path.lineTo(0, topLeft);
			if (topLeft > 0) {
				path.quadTo(0, 0, topLeft, 0);
			}
			path.closePath();
			g2.fill(path);
		}

	}

	class RoomPanel extends JPanel {
		private Phong roomId;
		private String status;
		private int days;

		public RoomPanel(Phong roomName, String status, int days) {
			this.roomId = roomName;
			this.status = status;
			this.days = days;
			setPreferredSize(new Dimension(220, 180));
			setBackground(new Color(0, 0, 0, 0));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ hình chữ nhật bo tròn
			g2.setColor(status.equals("Phòng Trống") ? Color.LIGHT_GRAY : Color.green);
			g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));

			// Vẽ thông tin phòng lên trong ô
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("Arial", Font.PLAIN, 14));

			// Tính toán vị trí và chiều dài của các dòng văn bản
			FontMetrics metrics = g2.getFontMetrics();
			int margin = 10; // Khoảng cách lề
			int lineHeight = metrics.getHeight(); // Chiều cao của mỗi dòng văn bản

			// Vẽ thông tin phòng với căn chỉnh phù hợp
			g2.drawString("ID: " + roomId.getSoPhong(), margin, margin + lineHeight); // Dòng 1
			g2.drawString("Trạng thái: " + roomId.getTrangThai(), margin, margin + 2 * lineHeight); // Dòng 2
			g2.drawString("Mô tả: " + roomId.getMoTa(), margin, margin + 3 * lineHeight); // Dòng 3
		}
	}

	@Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDat ) {
            // Mở cửa sổ đặt phòng
            DatPhong_GUI frameDatPhong = new DatPhong_GUI(this);
            frameDatPhong.setVisible(true);
        }
//        if(e.getSource() == btnDatPhong2){
//            // Mở cửa sổ đặt phòng
//            DatPhong_GUI1 frameDatPhong = (DatPhong_GUI1) new DatPhong_GUI1(this);
//            frameDatPhong.setVisible(true);
//        }
    }

}