package Duong;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder; // Added import
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;
import javax.swing.JFormattedTextField.AbstractFormatter;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import entity.LoaiPhong;
import entity.Phong;
import java.util.Map;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints; // Added import
import java.awt.GridBagLayout; // Added import
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets; // Added import
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class QuanLyDatPhong_GUI extends JFrame implements ActionListener, MouseListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CustomRoundedPanel panelWest, panelSouth; // Made CustomRoundedPanel
	RoundedButton btnDat;
	private Phong_Dao dsPhong;
	private LoaiPhong_Dao dsLoaiPhong;
	private Map<String, ArrayList<Phong>> roomCategoriesMap; // Renamed to avoid conflict
	private RoundedButton btnTra , btnDoi;
	// Filter components as class members
	private JRadioButton rbPhongTrong;
	private JRadioButton rbPhongDaDat;
	private JRadioButton rbTatCaPhong;
	private JCheckBox cbAllRooms;
	private List<JCheckBox> loaiPhongCheckBoxes = new ArrayList<>();
	private List<RoomPanel> allRoomPanelsList = new ArrayList<>();
	private static final String ICON_PATH_PREFIX = "img/";
	// Define Colors and Fonts (adjust as needed)
	private static final Color COLOR_MEDIUM_GRAY_BORDER = new Color(200, 200, 200);
	private static final Color COLOR_DARK_TEXT = Color.BLACK;
	private static final Font FONT_MAIN_TITLE = new Font("Arial", Font.BOLD, 18);

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
		dsPhong = new Phong_Dao();
		dsLoaiPhong = new LoaiPhong_Dao();
		roomCategoriesMap = new HashMap<>();

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
		// Changed to use the new createHeaderPanel method
		JPanel headerPanel = createHeaderPanel();
		CustomRoundedPanel panelNorth = new CustomRoundedPanel(0, 0, 0, 0);
		panelNorth.setBackground(Color.WHITE);
		panelNorth.setBounds(0, 0, screenWidth, (int) (screenHeight * 0.10)); // Adjusted height slightly, review if
																				// needed
		panelNorth.setLayout(new BorderLayout());
		panelNorth.add(headerPanel, BorderLayout.CENTER); // Add the new header
		contentPane.add(panelNorth);

		// Panel West - Initialize class member
		panelWest = new CustomRoundedPanel(15, 15, 15, 15);
		int westWidth = (int) (screenWidth * 0.18);
		// Adjust Y position of panelWest to be below the new panelNorth height
		panelWest.setBounds(8, (int) (screenHeight * 0.10) + 5, westWidth - 3,
				screenHeight - (int) (screenHeight * 0.10) - 50); // Adjusted height calculation
		panelWest.setBackground(Color.WHITE);
		panelWest.setLayout(null);
		contentPane.add(panelWest);

		Font ft = new Font("Arial", Font.BOLD, 18);

		CustomRoundedPanel panelDate = new CustomRoundedPanel(10, 10, 10, 10);
		panelDate.setBounds(20, 10, 180, 60);
		panelDate.setBackground(Color.WHITE);
		panelDate.setLayout(null);

		JLabel lblDate = new JLabel("Chọn ngày");
		lblDate.setFont(new Font("Arial", Font.BOLD, 12));
		lblDate.setBounds(10, 5, 100, 20); // Adjusted bounds for label
		panelDate.add(lblDate);

		UtilDateModel model = new UtilDateModel();
		Calendar cal = Calendar.getInstance();
		model.setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		model.setSelected(true);

		Properties p = new Properties();
		p.put("text.today", "Hôm nay");
		p.put("text.month", "Tháng");
		p.put("text.year", "Năm");

		JDatePanelImpl datePanelImpl = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanelImpl, new DateLabelFormatter());
		datePicker.setBounds(10, 30, 160, 26);
		panelDate.add(datePicker);

		datePicker.addActionListener(e -> {
			java.util.Date selectedDate = (java.util.Date) datePicker.getModel().getValue();
			if (selectedDate != null) {
				loadData(selectedDate);
			}
		});
		panelWest.add(panelDate);

		ImageIcon calendarIcon = new ImageIcon(
				new ImageIcon("calendar.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
		JLabel lblCalendarIcon = new JLabel(calendarIcon);
		lblCalendarIcon.setBounds(145, 5, 30, 30);
		panelDate.add(lblCalendarIcon);

		JLabel lblTitleWest = new JLabel("Trạng thái"); // Renamed to avoid conflict with lblTitle in header
		lblTitleWest.setForeground(Color.decode("#3B9AEE"));
		lblTitleWest.setBounds(20, 80, 150, 25);
		lblTitleWest.setFont(ft);
		panelWest.add(lblTitleWest);

		rbPhongTrong = new JRadioButton("Phòng trống");
		rbPhongTrong.setBounds(35, 110, 150, 25);
		rbPhongTrong.setBackground(Color.WHITE);
		rbPhongTrong.addActionListener(e -> applyFilters());

		rbPhongDaDat = new JRadioButton("Phòng đã đặt");
		rbPhongDaDat.setBounds(35, 140, 150, 25);
		rbPhongDaDat.setBackground(Color.WHITE);
		rbPhongDaDat.addActionListener(e -> applyFilters());

		rbTatCaPhong = new JRadioButton("Tất Cả");
		rbTatCaPhong.setBounds(35, 170, 150, 25);
		rbTatCaPhong.setBackground(Color.WHITE);
		rbTatCaPhong.setSelected(true); // Default to "Tất Cả"
		rbTatCaPhong.addActionListener(e -> applyFilters());

		ButtonGroup groupStatus = new ButtonGroup();
		groupStatus.add(rbPhongTrong);
		groupStatus.add(rbPhongDaDat);
		groupStatus.add(rbTatCaPhong);
		panelWest.add(rbPhongTrong);
		panelWest.add(rbPhongDaDat);
		panelWest.add(rbTatCaPhong);

		JLabel lblRoomType = new JLabel("Loại phòng");
		lblRoomType.setBounds(20, 200, 150, 25);
		lblRoomType.setForeground(Color.decode("#3B9AEE"));
		lblRoomType.setFont(ft);
		panelWest.add(lblRoomType);

		ArrayList<LoaiPhong> danhSachLoaiPhong = dsLoaiPhong.getAllLoaiPhong();
		int yPosition = 230;

		ItemListener loaiPhongItemListener = e -> applyFilters();

		for (LoaiPhong loaiPhong : danhSachLoaiPhong) {
			JCheckBox cbLoaiPhong = new JCheckBox(loaiPhong.getTenLoai());
			cbLoaiPhong.setBounds(35, yPosition, 200, 25);
			cbLoaiPhong.setBackground(Color.WHITE);
			cbLoaiPhong.setActionCommand(loaiPhong.getMaLoaiPhong());
			cbLoaiPhong.addItemListener(loaiPhongItemListener);
			panelWest.add(cbLoaiPhong);
			loaiPhongCheckBoxes.add(cbLoaiPhong);
			yPosition += 30;
		}

		cbAllRooms = new JCheckBox("Tất cả các loại phòng");
		cbAllRooms.setBounds(35, yPosition, 200, 25);
		cbAllRooms.setBackground(Color.WHITE);
		cbAllRooms.addItemListener(loaiPhongItemListener);
		panelWest.add(cbAllRooms);

		// Panel Center (for buttons like Đặt phòng, Đổi phòng, etc.)
		CustomRoundedPanel panelCenterButtons = new CustomRoundedPanel(15, 15, 15, 15);
		int centerButtonsWidth = screenWidth - westWidth - 20;
		int centerButtonsHeight = (int) (screenHeight * 0.08);
		// Adjust Y position of panelCenterButtons to be below panelNorth
		panelCenterButtons.setBounds(westWidth + 10, (int) (screenHeight * 0.10) + 5, centerButtonsWidth,
				centerButtonsHeight - 10);
		panelCenterButtons.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		panelCenterButtons.setBackground(Color.lightGray);
		contentPane.add(panelCenterButtons);

		Font buttonFont = new Font("Arial", Font.BOLD, 16);

		btnDat = new RoundedButton("Đặt phòng", 20);
		btnDat.addActionListener(this);
		btnDat.setPreferredSize(new Dimension(150, 40));
		btnDat.setFont(buttonFont);
		panelCenterButtons.add(btnDat);

		btnDoi = new RoundedButton("Đổi phòng", 20);
		btnDoi.addActionListener(this);
		btnDoi.setPreferredSize(new Dimension(150, 40));
		btnDoi.setFont(buttonFont);
		panelCenterButtons.add(btnDoi);

		btnTra = new RoundedButton("Trả phòng", 20);
		btnTra.setPreferredSize(new Dimension(150, 40));
		btnTra.addActionListener(this);
		btnTra.setFont(buttonFont);
		panelCenterButtons.add(btnTra);

		RoundedButton btnHuy = new RoundedButton("Hủy đơn đặt phòng", 20);
		btnHuy.setPreferredSize(new Dimension(230, 40));
		btnHuy.setFont(buttonFont);
		panelCenterButtons.add(btnHuy);

		// Panel South - Room display area
		panelSouth = new CustomRoundedPanel(15, 15, 15, 15);
		panelSouth.setBackground(Color.WHITE);
		panelSouth.setLayout(new BoxLayout(panelSouth, BoxLayout.Y_AXIS));

		for (LoaiPhong loaiPhong : danhSachLoaiPhong) {
			ArrayList<Phong> rooms = dsPhong.getDanhSachPhongTheoLoai(loaiPhong.getMaLoaiPhong());
			if (rooms != null && !rooms.isEmpty()) {
				for (Phong phong : rooms) {
					if (phong.getLoaiPhong() == null) {
						phong.setLoaiPhong(loaiPhong);
					}
				}
				roomCategoriesMap.put(loaiPhong.getTenLoai(), rooms);
			}
		}

		allRoomPanelsList.clear();

		int roomWidth = 200;
		int roomHeight = 150;
		int horizontalSpacing = 20;
		int verticalSpacing = 10;

		for (Entry<String, ArrayList<Phong>> entry : roomCategoriesMap.entrySet()) {
			String roomType = entry.getKey();
			List<Phong> rooms = entry.getValue();

			JPanel titlePanel = new JPanel();
			titlePanel.setOpaque(false);
			titlePanel.setBackground(Color.WHITE);
			titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 0));

			JLabel lblRoomInfo = new JLabel(roomType);
			lblRoomInfo.setFont(new Font("Arial", Font.BOLD, 20));
			lblRoomInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
			titlePanel.add(lblRoomInfo);
			panelSouth.add(titlePanel);

			int roomsPerRow = 4;
			if (centerButtonsWidth > 0) {
				roomsPerRow = Math.max(1, (centerButtonsWidth - horizontalSpacing) / (roomWidth + horizontalSpacing));
			}
			int numberOfRooms = rooms.size();
			int numberOfRows = (int) Math.ceil((double) numberOfRooms / roomsPerRow);

			JPanel roomGridPanel = new JPanel();
			roomGridPanel.setOpaque(false);
			roomGridPanel.setBackground(Color.WHITE);
			roomGridPanel.setLayout(new GridLayout(numberOfRows, roomsPerRow, horizontalSpacing, verticalSpacing));

			for (Phong room : rooms) {
				int days = 0;
				RoomPanel roomPanel = new RoomPanel(room, days);
				roomPanel.setPreferredSize(new Dimension(roomWidth, roomHeight));
				roomGridPanel.add(roomPanel);
				allRoomPanelsList.add(roomPanel);
			}
			panelSouth.add(roomGridPanel);
		}

		JScrollPane scrollPane = new JScrollPane(panelSouth);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// Adjust Y and Height of scrollPane
		scrollPane.setBounds(westWidth + 10, (int) (screenHeight * 0.10) + centerButtonsHeight + 8, centerButtonsWidth,
				screenHeight - ((int) (screenHeight * 0.10) + centerButtonsHeight) - 60); // Adjusted bounds
		scrollPane.setBorder(null);
		contentPane.add(scrollPane);

		cbAllRooms.setSelected(true);
		applyFilters();
	}

	private JPanel createHeaderPanel() {
		JPanel headerPanel = new JPanel(new GridBagLayout());
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_MEDIUM_GRAY_BORDER), new EmptyBorder(8, 15, 8, 15)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 8, 0, 8);
		gbc.fill = GridBagConstraints.NONE;

		// Cột 0: Logo bên trái
		JLabel ilogo = new JLabel(loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/logoLux.png", 60, 60));
		JPanel logoPanel = new JPanel();
		logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
		logoPanel.setBackground(Color.WHITE);
		logoPanel.add(ilogo);

		gbc.gridx = 0;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		headerPanel.add(logoPanel, gbc);

		// Cột 1: Panel chứa label và nút Home sát bên phải logo
		JPanel homePanel = new JPanel();
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
		homePanel.setBackground(Color.WHITE);

		JLabel lblTitleHeader = new JLabel("Quản lý đặt phòng");
		lblTitleHeader.setFont(FONT_MAIN_TITLE);
		lblTitleHeader.setForeground(COLOR_DARK_TEXT);
		lblTitleHeader.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnHome = new JButton(loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/home.png", 30, 30));
		btnHome.setBackground(Color.white);
		btnHome.setBorder(null);

		homePanel.add(lblTitleHeader);
		homePanel.add(Box.createVerticalStrut(5));
		homePanel.add(btnHome);

		gbc.gridx = 1;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.LINE_START; // Căn trái sát logo
		headerPanel.add(homePanel, gbc);

		// Cột 2: dùng để đẩy khoảng cách (cột "rỗng")
		gbc.gridx = 2;
		gbc.weightx = 1; // Giãn hết khoảng trống
		headerPanel.add(Box.createHorizontalGlue(), gbc);

		// Cột 5: Nút Help
		JButton btnHelp = createIconButton("?", 18);
		gbc.gridx = 5;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		headerPanel.add(btnHelp, gbc);

		// Cột 6: Panel chứa ảnh và tên lễ tân
		JPanel userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.Y_AXIS));
		userPanel.setBackground(Color.WHITE);

		ImageIcon userIcon = loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/anhdaidien.jpg", 42, 42);
		JButton btnUserIcon = (userIcon != null) ? new JButton(userIcon) : new JButton("Ảnh lỗi");

		btnUserIcon.setPreferredSize(new Dimension(40, 40));
		btnUserIcon.setBackground(Color.WHITE);
		btnUserIcon.setBorder(null);
		btnUserIcon.setFocusable(false);
		btnUserIcon.setContentAreaFilled(false);
		btnUserIcon.setFocusPainted(false);
		btnUserIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

		JLabel lblUserName = new JLabel("Lễ tân");
		lblUserName.setFont(new Font("Times New Roman", Font.BOLD, 14));
		lblUserName.setForeground(COLOR_DARK_TEXT);
		lblUserName.setAlignmentX(Component.CENTER_ALIGNMENT);

		userPanel.add(Box.createVerticalStrut(5));
		userPanel.add(btnUserIcon);
		userPanel.add(Box.createVerticalStrut(5));
		userPanel.add(lblUserName);

		gbc.gridx = 6;
		gbc.weightx = 0;
		gbc.anchor = GridBagConstraints.LINE_END;
		headerPanel.add(userPanel, gbc);

		// Cột 7: Nút Close (X) góc phải trên
		JButton btnClose = new JButton("X");
		btnClose.setFont(new Font("Arial", Font.BOLD, 15));
		btnClose.setForeground(Color.WHITE);
		btnClose.setBackground(Color.RED);
		btnClose.setFocusPainted(false);
		btnClose.setBorderPainted(false);
		btnClose.setPreferredSize(new Dimension(20, 20));
		btnClose.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnClose.addActionListener(e -> {
			int result = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát?", "Xác nhận thoát",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
			if (result == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		});

		gbc.gridx = 7;
		gbc.anchor = GridBagConstraints.NORTHEAST;
		gbc.insets = new Insets(5, 10, 5, 5);
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.fill = GridBagConstraints.NONE;
		headerPanel.add(btnClose, gbc);

		return headerPanel;
	}

	private ImageIcon loadIcon(String fileName, int width, int height) {
		URL imgURL = getClass().getClassLoader().getResource(ICON_PATH_PREFIX + fileName);
		if (imgURL != null) {
			try {
				BufferedImage originalImage = ImageIO.read(imgURL); // đọc BufferedImage từ classpath
				Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaledImage);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			// fallback file system
			try {
				BufferedImage originalImage = ImageIO.read(new File(ICON_PATH_PREFIX + fileName));
				Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
				return new ImageIcon(scaledImage);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	private JButton createIconButton(String text, int fontSize) {
		JButton button = new JButton(text);
		button.setFont(new Font("Arial", Font.PLAIN, fontSize));
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setOpaque(false);
		// You might want to add more styling or an ActionListener here
		return button;
	}

	private void applyFilters() {
		if (panelSouth == null || allRoomPanelsList.isEmpty()) {
			return;
		}

		String statusFilter = "";
		if (rbPhongTrong.isSelected()) {
			statusFilter = "TRONG";
		} else if (rbPhongDaDat.isSelected()) {
			statusFilter = "DADAT";
		} else if (rbTatCaPhong.isSelected()) {
			statusFilter = ""; // Show all
		}

		List<String> selectedRoomTypeCodes = new ArrayList<>();
		boolean isCbAllRoomsSelected = cbAllRooms.isSelected();
		boolean specificTypeSelected = false;

		for (JCheckBox checkBox : loaiPhongCheckBoxes) {
			if (checkBox.isSelected()) {
				selectedRoomTypeCodes.add(checkBox.getActionCommand());
				specificTypeSelected = true;
			}
		}

		for (RoomPanel rp : allRoomPanelsList) {
			Phong phong = rp.getRoom();
			if (phong == null) {
				rp.setVisible(false);
				continue;
			}

			boolean matchesStatus = false;
			if (statusFilter.isEmpty()) {
				matchesStatus = true;
			} else if (statusFilter.equals("TRONG") && "Trống".equalsIgnoreCase(phong.getTrangThai())) {
				matchesStatus = true;
			} else if (statusFilter.equals("DADAT") && "Đã đặt".equalsIgnoreCase(phong.getTrangThai())) {
				matchesStatus = true;
			}

			boolean matchesType = false;
			if (isCbAllRoomsSelected || !specificTypeSelected) {
				matchesType = true;
			} else {
				if (phong.getLoaiPhong() != null
						&& selectedRoomTypeCodes.contains(phong.getLoaiPhong().getMaLoaiPhong())) {
					matchesType = true;
				}
			}

			boolean shouldBeVisible = matchesStatus && matchesType;
			rp.setVisible(shouldBeVisible);
		}

		panelSouth.revalidate();
		panelSouth.repaint();

		Component parent = panelSouth.getParent();
		if (parent instanceof javax.swing.JViewport) {
			parent.revalidate();
			parent.repaint();
		} else {
			if (panelSouth.getParent() instanceof JScrollPane) {
				((JScrollPane) panelSouth.getParent()).revalidate();
				((JScrollPane) panelSouth.getParent()).repaint();
			}
		}
	}

	class RoundedButton extends JButton {
		private int radius;

		public RoundedButton(String text, int radius) {
			super(text);
			this.radius = radius;
			setContentAreaFilled(false);
			setFocusPainted(false);
			setBorderPainted(false);
			setBackground(Color.WHITE); // Màu nền mặc định
			setForeground(Color.BLACK); // Màu chữ

			// Thêm hiệu ứng màu khi tương tác chuột
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setBackground(Color.GREEN); // xanh lá nhạt khi hover
				}

				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(Color.WHITE); // trở lại màu nền mặc định
				}

				@Override
				public void mousePressed(MouseEvent e) {
					setBackground(new Color(0, 153, 0)); // xanh lá đậm khi nhấn
					setForeground(Color.WHITE); // chữ trắng khi nhấn
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					setBackground(new Color(200, 255, 200)); // giữ lại màu hover
					setForeground(Color.BLACK); // chữ về đen
				}
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			// Vẽ nền bo tròn
			g2.setColor(getBackground());
			g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

			// Nếu có border, vẽ border bo tròn
			if (getBorder() != null && isBorderPainted()) {
				g2.setColor(getForeground());
				g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
			}

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
			if (topRight > 0)
				path.quadTo(width, 0, width, topRight);
			path.lineTo(width, height - bottomRight);
			if (bottomRight > 0)
				path.quadTo(width, height, width - bottomRight, height);
			path.lineTo(bottomLeft, height);
			if (bottomLeft > 0)
				path.quadTo(0, height, 0, height - bottomLeft);
			path.lineTo(0, topLeft);
			if (topLeft > 0)
				path.quadTo(0, 0, topLeft, 0);
			path.closePath();
			g2.fill(path);
		}
	}

	class RoomPanel extends JPanel {
		private Phong room;
		private int days;

		public RoomPanel(Phong room, int days) {
			this.room = room;
			this.days = days;
			setPreferredSize(new Dimension(220, 180));
			setOpaque(false);
			// Bỏ phần loại phòng trong tooltip
			setToolTipText("<html><b>Phòng:</b> " + room.getSoPhong() + "<br><b>Trạng thái:</b> " + room.getTrangThai()
					+ "<br><b>Số ngày ở dự kiến:</b> " + days + "</html>");
		}

		public Phong getRoom() {
			return room;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			Color bgColor;
			String currentStatus = room.getTrangThai();
			if ("Trống".equalsIgnoreCase(currentStatus)) {
				bgColor = Color.green;
			} else if ("Đã đặt".equalsIgnoreCase(currentStatus)) {
				bgColor = new Color(211, 211, 211);
			} else if ("Phòng đang sửa chữa".equalsIgnoreCase(currentStatus)) {
				bgColor = new Color(255, 228, 181);
			} else {
				bgColor = Color.LIGHT_GRAY;
			}

			g2.setColor(bgColor);
			g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));

			g2.setColor(new Color(180, 180, 180));
			g2.setStroke(new BasicStroke(1));
			g2.draw(new RoundRectangle2D.Double(0, 0, getWidth() - 1, getHeight() - 1, 25, 25));

			int margin = 15;
			int y = margin + 15;
			g2.setColor(Color.DARK_GRAY);

			g2.setFont(new Font("Arial", Font.BOLD, 16));
			g2.drawString("Phòng: " + room.getSoPhong(), margin, y);

			y += 25;
			// Bỏ phần hiển thị loại phòng

			g2.setFont(new Font("Arial", Font.PLAIN, 14));
			g2.drawString("Trạng thái: " + room.getTrangThai(), margin, y);

			y += 20;
			Font font = new Font("Arial", Font.PLAIN, 12);
			g2.setFont(font);
			FontMetrics fm = g2.getFontMetrics(font);
			String moTa = room.getMoTa() != null ? room.getMoTa() : "";
			List<String> lines = wrapText("Mô tả: " + moTa, fm, getWidth() - 2 * margin);
			for (String line : lines) {
				if (y + fm.getHeight() < getHeight() - margin - 20) {
					g2.drawString(line, margin, y);
					y += fm.getHeight();
				} else {
					break;
				}
			}

			if (y + 20 < getHeight() - margin) {
				y += 5;
				int iconSize = 16;
				g2.setColor(Color.DARK_GRAY);
				g2.setStroke(new BasicStroke(1.5f));
				g2.drawOval(margin, y, iconSize, iconSize);
				g2.drawLine(margin + 4, y + 8, margin + 7, y + 11);
				g2.drawLine(margin + 7, y + 11, margin + 12, y + 6);
				g2.setFont(new Font("Arial", Font.PLAIN, 14));
				g2.drawString("Ở: " + days + " ngày", margin + iconSize + 5, y + iconSize - 2);
			}
			g2.dispose();
		}

		private List<String> wrapText(String text, FontMetrics fm, int maxWidth) {
			List<String> lines = new ArrayList<>();
			if (text == null || text.isEmpty()) {
				lines.add("");
				return lines;
			}
			String[] words = text.split(" ");
			if (words.length == 0) {
				lines.add("");
				return lines;
			}
			StringBuilder currentLine = new StringBuilder(words[0]);

			for (int i = 1; i < words.length; i++) {
				if (fm.stringWidth(currentLine.toString() + " " + words[i]) < maxWidth) {
					currentLine.append(" ").append(words[i]);
				} else {
					lines.add(currentLine.toString());
					currentLine = new StringBuilder(words[i]);
				}
			}
			if (currentLine.length() > 0) {
				lines.add(currentLine.toString());
			}
			if (lines.isEmpty() && text.length() > 0) {
				lines.add(text);
			}
			return lines;
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btnDat) {
			DatPhong_GUI frameDatPhong = new DatPhong_GUI(this);
			frameDatPhong.setVisible(true);
		}
		if (e.getSource() == btnTra) {
			timKiemDialog dialog = new timKiemDialog(this);
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setUndecorated(true);
			dialog.setLocationRelativeTo(null);
			dialog.setSize(929, 629);
			dialog.setResizable(false);
			dialog.setVisible(true);
		}
//		if(e.getSource() == btnDoi) {
////			timKiemDialog dialog = new timKiemDialog(this);
////			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
////			dialog.setUndecorated(true);
////			dialog.setLocationRelativeTo(null);
////			dialog.setSize(929, 629);
////			dialog.setResizable(false);
////			dialog.setVisible(true);
////			}
	}

	private void loadData(java.util.Date selectedDate) {
		String formattedDate = new SimpleDateFormat("dd/MM/yyyy").format(selectedDate);
		System.out.println("Ngày được chọn: " + formattedDate);
		// TODO: Implement logic to reload room data from the database based on the
		// selected date.
		System.out.println("loadData function called. Room display should be updated based on " + formattedDate);
	}

	public class DateLabelFormatter extends AbstractFormatter {
		private static final String DATE_PATTERN = "dd/MM/yyyy";
		private final SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_PATTERN);

		@Override
		public Object stringToValue(String text) throws ParseException {
			return dateFormatter.parseObject(text);
		}

		@Override
		public String valueToString(Object value) throws ParseException {
			if (value != null) {
				Calendar cal = (Calendar) value;
				return dateFormatter.format(cal.getTime());
			}
			return "";
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}
