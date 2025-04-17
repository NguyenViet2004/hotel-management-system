package GUI;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.ChiTietDonDatPhong_Dao;
import dao.DonDatPhong_Dao;
import dao.KhachHang_Dao;
import dao.Phong_Dao;
import entity.KhachHang;
import entity.Phong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DatPhong_GUI extends JDialog {
	// Giao diện
	private JPanel contentPane;
	private JPanel mainPanel;
	private JPanel centerPanel;
	private CardLayout cardLayout;

	private JDatePickerImpl datePickerCheckIn;
	private JDatePickerImpl datePickerCheckOut;

	// Kích thước màn hình các trang
	private int screenWidthTrang1;
	private int screenHeightTrang1;
	private int screenWidthTrang2;
	private int screenHeightTrang2;

	// DAO
	private Phong_Dao phongdao;
	private KhachHang_Dao khachhangdao;
	private DonDatPhong_Dao dondatphongdao;
	private ChiTietDonDatPhong_Dao chitietdondatphongdao;

	// Dữ liệu đặt phòng
	String[] tienIchPhong = {"Ban công", "Phòng hút thuốc", "View biển"};
	private int[] roomQuantities;
	private String[] danhSachSoPhongDuocChon;
	private String maNhanVien = "2025LT001";
	private String maDon;
	private String loaiDon = null; // "GIO", "NGAY", hoặc "DEM"
	private String trangThai = "Chưa thanh toán";
	private int soKhach;

	// Ngày giờ
	private Timestamp tuNgay; // ngày nhận phòng
	private Timestamp denNgay; // ngày trả phòng
	private Date ngayNhan;
	private Date ngayTra;


	//==============TRANG CHÍNH GUI==============
	public DatPhong_GUI(JFrame parentFrame) {
		super(parentFrame, "Form Đặt Phòng", true); // true = modal dialog

		// Giao diện không viền nếu muốn tùy biến
		setUndecorated(true);

		// Lấy kích thước màn hình
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidthTrang1 = (int) (screenSize.width * 0.65);
		screenHeightTrang1 = (int) (screenSize.height * 0.65);

		// Đặt kích thước dialog
		setSize(screenWidthTrang1, screenHeightTrang1);
		setLocationRelativeTo(parentFrame); // Canh giữa theo cửa sổ cha

		// Khởi tạo CardLayout
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);

		// Thêm các trang
		mainPanel.add(taoTrangDatPhong(), "Trang1");

		cardLayout.show(mainPanel, "Trang1");

		add(mainPanel);
	}
	
	//==============TAO TRANG ĐẶT PHÒNG(TRANG 1)==============
	private JPanel taoTrangDatPhong() {
		// tạo biến cho chi tiet don dat phòng
		chitietdondatphongdao = new ChiTietDonDatPhong_Dao();
		khachhangdao = new KhachHang_Dao();
		phongdao = new Phong_Dao();
		dondatphongdao = new DonDatPhong_Dao();
		// Tính toán kích thước các phần
		int headerHeight = (int) (screenHeightTrang1 * 0.3);
		int centerHeight = (int) (screenHeightTrang1 * 0.6);
		int footerHeight = (int) (screenHeightTrang1 * 0.1);
		// Tạo nội dung form (panel con)
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.WHITE); // Màu nền cho contentPane
		contentPane.setBorder(new LineBorder(Color.BLACK, 2));

		// ====== Panel chọn phòng ======
		JPanel chonPhongPanel = new JPanel(new BorderLayout());
		chonPhongPanel.setBackground(Color.WHITE);
		chonPhongPanel.setPreferredSize(new Dimension(800, 300));

		// =========================================== Header==============================================
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.WHITE); // Màu xám nhạt
		headerPanel.setPreferredSize(new Dimension(screenWidthTrang1, headerHeight));

		// ============== Panel chứa tiêu đề và nút đóng=================phần 1 của header===================
		JPanel titleClosePanel = new JPanel(new BorderLayout());
		titleClosePanel.setBackground(Color.CYAN);
		titleClosePanel.setPreferredSize(new Dimension(screenWidthTrang1, (int) (headerHeight * 0.22)));
		// Tiêu đề "Chọn phòng"
		JLabel titleLabel = new JLabel("Chọn phòng");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Nút đóng
		JButton closeButton = new JButton("X");
		closeButton.setFont(new Font("Arial", Font.BOLD, 20));
		closeButton.setForeground(Color.BLACK);
		closeButton.setBackground(Color.WHITE);
		closeButton.setFocusPainted(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		closeButton.addActionListener(e -> dispose());

		titleClosePanel.add(titleLabel, BorderLayout.WEST);
		titleClosePanel.add(closeButton, BorderLayout.EAST);
		// tạo nút chưa 3 nút==================================phần 2 của header==============================
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
		buttonPanel.setBackground(Color.ORANGE);
		buttonPanel.setPreferredSize(new Dimension((int) (screenWidthTrang1* 0.2), (int) (headerHeight * 0.78) ));

		// ========== Tạo 3 nút với viền
		// Tạo kích thước chuẩn cho cả 3 nút
		Dimension panelSize = buttonPanel.getPreferredSize();
		Dimension buttonSize = new Dimension((int) (panelSize.width * 0.8), (int) (headerHeight * 0.2));


		// Nút theo ngày
		JButton theoNgayButton = new JButton("Theo ngày");
		theoNgayButton.setFont(new Font("Arial", Font.BOLD, 14));
		theoNgayButton.setBackground(Color.WHITE);
		theoNgayButton.setFocusPainted(false);
		theoNgayButton.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(5, 15, 5, 15)));
		theoNgayButton.setPreferredSize(buttonSize);
		theoNgayButton.setMaximumSize(buttonSize); // Quan trọng để BoxLayout áp đúng size
		theoNgayButton.setAlignmentX(Component.CENTER_ALIGNMENT); // căn giữa ngang

		// Nút theo giờ
		JButton theoGioButton = new JButton("Theo giờ");
		theoGioButton.setFont(new Font("Arial", Font.BOLD, 14));
		theoGioButton.setBackground(Color.WHITE);
		theoGioButton.setFocusPainted(false);
		theoGioButton.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(5, 15, 5, 15)));
		theoGioButton.setPreferredSize(buttonSize);
		theoGioButton.setMaximumSize(buttonSize);
		theoGioButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		// Nút theo đêm
		JButton theoDemButton = new JButton("Theo đêm");
		theoDemButton.setFont(new Font("Arial", Font.BOLD, 14));
		theoDemButton.setBackground(Color.WHITE);
		theoDemButton.setFocusPainted(false);
		theoDemButton.setBorder(new CompoundBorder(new LineBorder(Color.BLACK, 2), new EmptyBorder(5, 15, 5, 15)));
		theoDemButton.setPreferredSize(buttonSize);
		theoDemButton.setMaximumSize(buttonSize);
		theoDemButton.setAlignmentX(Component.CENTER_ALIGNMENT);

		buttonPanel.add(Box.createVerticalStrut(10)); // khoảng cách giữa các nút
		buttonPanel.add(theoGioButton);
		buttonPanel.add(theoNgayButton);
		buttonPanel.add(theoDemButton);
		theoGioButton.addActionListener(e -> {
			loadGiaoDienTheoGio();
			updateButtonStyles(theoGioButton, theoNgayButton, theoDemButton);
			loaiDon = "Theo giờ";
			
		});

		theoNgayButton.addActionListener(e -> {
			try {
				loadGiaoDienTheoNgay();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			updateButtonStyles(theoNgayButton, theoGioButton, theoDemButton);
			loaiDon = "Theo ngày";
		});

		theoDemButton.addActionListener(e -> {
			try {
				loadGiaoDienTheoDem();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			updateButtonStyles(theoDemButton, theoGioButton, theoNgayButton);
			loaiDon = "Theo đêm";
		});

		// =============Panel chứa các thông tin ngày và khách==========phần 3 của header=====================
		// infoPanel chính
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		infoPanel.setPreferredSize(new Dimension((int)(screenWidthTrang1* 0.8),  (int) (headerHeight * 0.78)));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		infoPanel.setBackground(Color.BLUE);

		// ========== Panel trên: chứa ngày nhận, ngày trả và số khách ==========
		JPanel topInfoPanel = new JPanel(new GridLayout(1, 3, 5, 5));
		topInfoPanel.setPreferredSize(new Dimension((int)(screenWidthTrang1* 0.8),  (int) (headerHeight * 0.39))); // Chiếm khoảng nửa infoPanel
		topInfoPanel.setBackground(Color.BLUE);

		// Ngày nhận phòng
		JPanel checkInPanel = new JPanel(new BorderLayout());
		checkInPanel.setBackground(Color.WHITE);
		JLabel checkInLabel = new JLabel("Ngày nhận phòng");
		checkInLabel.setFont(new Font("Arial", Font.BOLD, 14));
		checkInPanel.add(checkInLabel, BorderLayout.NORTH);
		JPanel checkInWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		checkInWrapper.setBackground(Color.WHITE);
		datePickerCheckIn = createDatePicker(LocalDate.now());
		checkInWrapper.add(datePickerCheckIn);
		checkInPanel.add(checkInWrapper, BorderLayout.CENTER);

		// Ngày trả phòng
		JPanel checkOutPanel = new JPanel(new BorderLayout());
		checkOutPanel.setBackground(Color.WHITE);
		JLabel checkOutLabel = new JLabel("Ngày trả phòng");
		checkOutLabel.setFont(new Font("Arial", Font.BOLD, 14));
		checkOutPanel.add(checkOutLabel, BorderLayout.NORTH);
		JPanel checkOutWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		checkOutWrapper.setBackground(Color.WHITE);
		datePickerCheckOut = createDatePicker(LocalDate.now().plusDays(1));
		checkOutWrapper.add(datePickerCheckOut);
		checkOutPanel.add(checkOutWrapper, BorderLayout.CENTER);

		// Số lượng khách
		JPanel guestPanel = new JPanel(new BorderLayout());
		guestPanel.setBackground(Color.WHITE);
		JLabel guestLabel = new JLabel("Số lượng khách");
		guestLabel.setFont(new Font("Arial", Font.BOLD, 14));
		guestLabel.setHorizontalAlignment(SwingConstants.CENTER);
		guestPanel.add(guestLabel, BorderLayout.NORTH);
		JPanel guestControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
		guestControlPanel.setBackground(Color.WHITE);
		JButton minusButton = new JButton("-");
		minusButton.setFont(new Font("Arial", Font.BOLD, 14));
		minusButton.setPreferredSize(new Dimension(45, 25));
		JLabel guestCountLabel = new JLabel("0");
		guestCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
		guestCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		guestCountLabel.setPreferredSize(new Dimension(30, 25));
		JButton plusButton = new JButton("+");
		plusButton.setFont(new Font("Arial", Font.BOLD, 14));
		plusButton.setPreferredSize(new Dimension(45, 25));
		guestControlPanel.add(minusButton);
		guestControlPanel.add(guestCountLabel);
		guestControlPanel.add(plusButton);
		guestPanel.add(guestControlPanel, BorderLayout.CENTER);

		// Thêm các panel vào topInfoPanel
		topInfoPanel.add(checkInPanel);
		topInfoPanel.add(checkOutPanel);
		topInfoPanel.add(guestPanel);

		// ========== Panel dưới: chứa 6 checkbox ==========
		JPanel bottomInfoPanel = new JPanel();
		bottomInfoPanel.setLayout(new GridLayout(2, 3, 10, 10)); // 2 dòng 3 cột, cách nhau 10px
		bottomInfoPanel.setBackground(Color.CYAN);
		bottomInfoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		
		// Tạo 6 checkbox
		for (int i = 0; i < tienIchPhong.length; i++) {
		    JCheckBox checkBox = new JCheckBox(tienIchPhong[i]);
		    checkBox.setFont(new Font("Arial", Font.PLAIN, 14));
		    bottomInfoPanel.add(checkBox);
		}

		// Thêm top và bottom vào infoPanel
		infoPanel.add(topInfoPanel, BorderLayout.NORTH);
		infoPanel.add(bottomInfoPanel, BorderLayout.CENTER);


		// Panel chứa tất cả nội dung header
		JPanel headerContentPanel = new JPanel();
		headerContentPanel.setLayout(new BorderLayout());
		headerContentPanel.setBackground(new Color(220, 220, 220));

		headerContentPanel.add(titleClosePanel, BorderLayout.NORTH);
		headerContentPanel.add(buttonPanel, BorderLayout.WEST);
		headerContentPanel.add(infoPanel, BorderLayout.CENTER);

		headerPanel.add(headerContentPanel, BorderLayout.CENTER);

		// =============================== Center=========================================

		centerPanel = new JPanel(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setPreferredSize(new Dimension(screenWidthTrang1, centerHeight));

//        // Load mặc định khi khởi tạo
//        try {
//			loadGiaoDienTheoNgay();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

		// =============================== Footer=============================================
		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footerPanel.setBackground(Color.WHITE);
		footerPanel.setPreferredSize(new Dimension(screenWidthTrang1, footerHeight));

		JButton confirmButton = new JButton("Xác nhận");
		confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
		confirmButton.setForeground(Color.BLACK);
		confirmButton.setBackground(new Color(0, 180, 0)); // Màu xanh lá
		confirmButton.setFocusPainted(false);
		confirmButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		confirmButton.addActionListener(e -> {
            ngayNhan = (Date) datePickerCheckIn.getModel().getValue();
            ngayTra = (Date) datePickerCheckOut.getModel().getValue();

            if (ngayNhan == null || ngayTra == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn cả ngày nhận và ngày trả phòng.", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (!ngayNhan.before(ngayTra)) {
                JOptionPane.showMessageDialog(null, "Ngày nhận phòng phải trước ngày trả phòng.", "Lỗi ngày tháng", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int soLuongKhach = Integer.parseInt(guestCountLabel.getText());
            if (soLuongKhach <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng khách phải lớn hơn 0.", "Lỗi số lượng", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (loaiDon == null) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn kiểu đặt phòng (Giờ / Ngày / Đêm).", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // Kiểm tra tổng số lượng các loại phòng
            int tongPhong = 0;
            for (int qty : roomQuantities) {
                tongPhong += qty;
            }

            if (tongPhong == 0) {
                JOptionPane.showMessageDialog(
                    null,
                    "Vui lòng chọn ít nhất một loại phòng.",
                    "Thông báo",
                    JOptionPane.WARNING_MESSAGE
                );
                return; // Không cho chuyển trang nếu chưa chọn phòng
            }

            // Ngày hợp lệ, gán giá trị
            tuNgay = new Timestamp(ngayNhan.getTime());
            denNgay = new Timestamp(ngayTra.getTime());

			// Chuyển sang trang xác nhận
			JPanel trang2 = null;
			try {
				trang2 = taoTrangXacNhanPhong();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			mainPanel.add(trang2, "Trang2");
			cardLayout.show(mainPanel, "Trang2");
		});

		footerPanel.add(confirmButton);

		// ============end===============Thêm các phần vào panel chọn phòng========================
		chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
		chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
		chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

		// Thêm panel chọn phòng vào contentPane
		contentPane.add(chonPhongPanel, BorderLayout.CENTER);

		return contentPane;
	}

	//==============Hàm cập nhật màu khi ấn nút==============
	private void updateButtonStyles(JButton selected, JButton... others) {
		selected.setBackground(Color.GREEN); // Nút được chọn sẽ tô màu xanh

		for (JButton btn : others) {
			btn.setBackground(Color.WHITE); // Các nút còn lại sẽ về màu trắng
		}
	}

	//==============Hàm tạo DatePicker==============
	private JDatePickerImpl createDatePicker(LocalDate defaultDate) {

		UtilDateModel model = new UtilDateModel();
	    model.setDate(defaultDate.getYear(), defaultDate.getMonthValue() - 1, defaultDate.getDayOfMonth());
	    model.setSelected(true);
	    
	    Properties p = new Properties();
	    p.put("text.today", "Today");
	    p.put("text.month", "Month");
	    p.put("text.year", "Year");

	    JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
	    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

		// Xóa viền của DatePicker
		datePicker.setBorder(BorderFactory.createEmptyBorder());
		
		
		// Tùy chỉnh giao diện
		try {
			// Lấy JTextField (thường là component thứ 1)
			if (datePicker.getComponentCount() > 1) {
				Component textFieldComponent = datePicker.getComponent(1);
				if (textFieldComponent instanceof JTextField) {
					JTextField textField = (JTextField) textFieldComponent;
					textField.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5)); // Xóa khoảng trắng dưới
					textField.setBackground(Color.WHITE);
					textField.setEditable(false); // Ngăn chỉnh sửa trực tiếp
				}
			}

			// Lấy JButton (thường là component thứ 0)
			if (datePicker.getComponentCount() > 0) {
				Component buttonComponent = datePicker.getComponent(1);
				if (buttonComponent instanceof JButton) {
					JButton button = (JButton) buttonComponent;
					button.setText("");
					// kích thước của button chọn time
					int ngang = 25;
					int cao = 30;
					button.setPreferredSize(new Dimension(cao, ngang));
					ImageIcon icon = new ImageIcon("img/HinhAnhGiaoDienChinh/lich.png");
					Image scaledImage = icon.getImage().getScaledInstance(cao, ngang, Image.SCALE_SMOOTH);
					ImageIcon resizedIcon = new ImageIcon(scaledImage);
					button.setIcon(resizedIcon);

				}
			}

		} catch (Exception e) {
			System.err.println("Không thể tùy chỉnh date picker: " + e.getMessage());
		}

		return datePicker;
	}
	
	//==============Hàm tạo fomat lại Ngày nhận trả==============
	public class DateLabelFormatter extends AbstractFormatter {
	    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd 'thg' M, yyyy");

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return LocalDate.parse(text, formatter);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            LocalDate date = cal.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            return date.format(formatter);
	        }
	        return "";
	    }
	}
	
	//==============Hàm tạo từng row phòng==============
	private JPanel createRoomRow(String roomName, int roomPrice, int soPhongTrong, JLabel[] totalLabels,
			int[] roomQuantities, int index) {
		JPanel rowPanel = new JPanel(new GridLayout(1, 4));
		rowPanel.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.95), (int) (screenHeightTrang2 * 0.17)));
		rowPanel.setBackground(Color.decode("#EEEEEE"));
		rowPanel.setBorder(new LineBorder(Color.decode("#D9D9D9"), 2));

		// === Tên phòng + số lượng trống ===
		JPanel namePanel = new JPanel(new BorderLayout());
		namePanel.setBackground(Color.decode("#EEEEEE"));

		JLabel nameLabel = new JLabel(roomName, SwingConstants.CENTER);
		nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

		JLabel availabilityLabel = new JLabel("Còn " + soPhongTrong + " phòng trống", SwingConstants.CENTER);
		availabilityLabel.setFont(new Font("Arial", Font.ITALIC, 12));
		availabilityLabel.setForeground(Color.RED);

		namePanel.add(nameLabel, BorderLayout.NORTH);
		namePanel.add(availabilityLabel, BorderLayout.SOUTH);

		rowPanel.add(namePanel);

		// === Giá phòng ===
		JLabel priceLabel = new JLabel(formatCurrency(roomPrice), SwingConstants.CENTER);

		priceLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		rowPanel.add(priceLabel);

		// === Số lượng (textbox + nút) ===
		JPanel quantityPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		JButton decreaseButton = new JButton("-");
		JTextField quantityField = new JTextField("0", 3);
		quantityField.setHorizontalAlignment(JTextField.CENTER);
		JButton increaseButton = new JButton("+");

		quantityPanel.add(decreaseButton);
		quantityPanel.add(quantityField);
		quantityPanel.add(increaseButton);
		rowPanel.add(quantityPanel);

		// === Tổng cộng ===
		JLabel totalLabel = new JLabel("0 VND", SwingConstants.CENTER);
		totalLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		totalLabels[index] = totalLabel;
		rowPanel.add(totalLabel);

		// Xử lý tăng giảm
		decreaseButton.addActionListener(e -> {
			int qty = Integer.parseInt(quantityField.getText());
			if (qty > 0) {
				qty--;
				quantityField.setText(String.valueOf(qty));
				roomQuantities[index] = qty; // Lưu số lượng đã chọn
				updateTotal(totalLabel, qty, roomPrice);
			}
		});

		increaseButton.addActionListener(e -> {
			int qty = Integer.parseInt(quantityField.getText());
			if (qty < soPhongTrong) {
				qty++;
				quantityField.setText(String.valueOf(qty));
				roomQuantities[index] = qty; // Lưu số lượng đã chọn
				updateTotal(totalLabel, qty, roomPrice);
			}
		});

		return rowPanel;
	}

	//==============Hàm cập nhật tiền sau khi chọn phòng==============
	private void updateTotal(JLabel totalLabel, int qty, int roomPrice) {
		int total = qty * roomPrice;
		totalLabel.setText(formatCurrency(total));
	}

	//==============Hàm định dạng tiền (1.000.000)==============
	private String formatCurrency(int amount) {
		DecimalFormat df = new DecimalFormat("#,###");
		return df.format(amount).replace(",", ".") + " VND";
	}

	//==============LOAD TRANG THEO NGÀY(TRANG 1)==============
	private void loadGiaoDienTheoNgay() throws SQLException {
		System.out.println("mở giao diện theo ngày");
		centerPanel.removeAll();

		// ======= Header Gợi ý phòng ========
		JPanel headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		headerContainer.setPreferredSize(new Dimension(screenWidthTrang1, 60));
		headerContainer.setBackground(Color.WHITE);

		JPanel headerPanel1 = new JPanel(new GridLayout(1, 4));
		headerPanel1.setBackground(new Color(144, 238, 144));
		headerPanel1.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.95), 40));
		headerContainer.add(headerPanel1);
		String[] headers = { "Gợi ý phòng", "Giá", "Số lượng", "Tổng cộng" };
		for (String text : headers) {
			JLabel label = new JLabel(text, SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.BOLD, 14));
			headerPanel1.add(label);
		}

		// Danh sách loại phòng
		// ======== Tạo bảng nội dung bên dưới ========
		String[] roomNames = { "Double Room", "Single Room", "Twin Room", "Triple Room" };

		// ==== Tạo mảng lưu số lượng phòng được chọn ====
		roomQuantities = new int[roomNames.length]; // <- Mảng này sẽ được cập nhật trong createRoomRow()

		// Khai báo mảng JLabel để lưu "Tổng cộng" cho từng loại phòng
		JLabel[] totalLabels = new JLabel[roomNames.length];

		// Panel chứa các hàng
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // top, left, bottom, right
		contentPanel.setBackground(Color.WHITE);
		// -----------------------------------------------------------------------------------------------------------
		// ===== Double Room =====}
		JPanel rowPanelDouble = createRoomRow("Double Room", chitietdondatphongdao.GetPriceToDay(roomNames[0]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "double"), totalLabels, roomQuantities, 0);
		contentPanel.add(rowPanelDouble);
		contentPanel.add(Box.createVerticalStrut(10));

		// ===== Single Room =====
		JPanel rowPanelSingle = createRoomRow("Single Room", chitietdondatphongdao.GetPriceToDay(roomNames[1]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "single"), totalLabels, roomQuantities, 1);
		contentPanel.add(rowPanelSingle);
		contentPanel.add(Box.createVerticalStrut(10));

		// ===== Twin Room =====
		JPanel rowPanelTwin = createRoomRow("Twin Room", chitietdondatphongdao.GetPriceToDay(roomNames[2]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "twin"), totalLabels, roomQuantities, 2);
		contentPanel.add(rowPanelTwin);
		contentPanel.add(Box.createVerticalStrut(10));

		// ===== Triple Room =====
		JPanel rowPanelTriple = createRoomRow("Triple Room", chitietdondatphongdao.GetPriceToDay(roomNames[3]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "triple"), totalLabels, roomQuantities, 3);
		contentPanel.add(rowPanelTriple);

		// Thêm header và nội dung vào centerPanel
		centerPanel.add(headerContainer, BorderLayout.NORTH);
		centerPanel.add(contentPanel, BorderLayout.CENTER);

		// Cập nhật lại UI
		centerPanel.revalidate();
		centerPanel.repaint();
	}

	//==============LOAD TRANG THEO GIỜ(TRANG 2)==============
	private void loadGiaoDienTheoGio() {
		centerPanel.removeAll();

		JLabel label = new JLabel("Giao diện đặt theo GIỜ", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 24));
		centerPanel.add(label, BorderLayout.CENTER);

		centerPanel.revalidate();
		centerPanel.repaint();
	}

	//==============LOAD TRANG THEO ĐÊM(TRANG 3)==============
	private void loadGiaoDienTheoDem() throws SQLException {
		System.out.println("mở giao diện theo đêm");
		centerPanel.removeAll();

		// ======= Header Gợi ý phòng ========
		JPanel headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
		headerContainer.setPreferredSize(new Dimension(screenWidthTrang1, 60));
		headerContainer.setBackground(Color.WHITE);

		JPanel headerPanel1 = new JPanel(new GridLayout(1, 4));
		headerPanel1.setBackground(new Color(144, 238, 144));
		headerPanel1.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.95), 40));
		headerContainer.add(headerPanel1);
		String[] headers = { "Gợi ý phòng", "Giá", "Số lượng", "Tổng cộng" };
		for (String text : headers) {
			JLabel label = new JLabel(text, SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.BOLD, 14));
			headerPanel1.add(label);
		}

		// Danh sách loại phòng
		// ======== Tạo bảng nội dung bên dưới ========
		String[] roomNames = { "Double Room", "Single Room", "Twin Room", "Triple Room" };

		// ==== Tạo mảng lưu số lượng phòng được chọn ====
		roomQuantities = new int[roomNames.length]; // <- Mảng này sẽ được cập nhật trong createRoomRow()

		// Khai báo mảng JLabel để lưu "Tổng cộng" cho từng loại phòng
		JLabel[] totalLabels = new JLabel[roomNames.length];

		// Panel chứa các hàng
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // top, left, bottom, right
		contentPanel.setBackground(Color.WHITE);
		// -----------------------------------------------------------------------------------------------------------
		// ===== Double Room =====}
		JPanel rowPanelDouble = createRoomRow("Double Room", chitietdondatphongdao.GetPriceToDay(roomNames[0]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "double"), totalLabels, roomQuantities, 0);
		contentPanel.add(rowPanelDouble);
		contentPanel.add(Box.createVerticalStrut(10));

		// ===== Single Room =====
		JPanel rowPanelSingle = createRoomRow("Single Room", chitietdondatphongdao.GetPriceToDay(roomNames[1]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "single"), totalLabels, roomQuantities, 1);
		contentPanel.add(rowPanelSingle);
		contentPanel.add(Box.createVerticalStrut(10));

		// ===== Twin Room =====
		JPanel rowPanelTwin = createRoomRow("Twin Room", chitietdondatphongdao.GetPriceToDay(roomNames[2]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "twin"), totalLabels, roomQuantities, 2);
		contentPanel.add(rowPanelTwin);
		contentPanel.add(Box.createVerticalStrut(10));

		// ===== Triple Room =====
		JPanel rowPanelTriple = createRoomRow("Triple Room", chitietdondatphongdao.GetPriceToDay(roomNames[3]),
				chitietdondatphongdao.countSoPhongTrong(tuNgay, denNgay, "triple"), totalLabels, roomQuantities, 3);
		contentPanel.add(rowPanelTriple);

		// Thêm header và nội dung vào centerPanel
		centerPanel.add(headerContainer, BorderLayout.NORTH);
		centerPanel.add(contentPanel, BorderLayout.CENTER);

		// Cập nhật lại UI
		centerPanel.revalidate();
		centerPanel.repaint();
	}

	//==============TAO TRANG XÁC NHẬN PHÒNG(TRANG 2)==============
	private JPanel taoTrangXacNhanPhong() throws SQLException {
		// Tính toán kích thước các phần
		int headerHeight = (int) (screenHeightTrang1 * 0.1);
		int centerHeight = (int) (screenHeightTrang1 * 0.8);
		int footerHeight = (int) (screenHeightTrang1 * 0.1);
		// Tạo nội dung form (panel con)
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.WHITE); // Màu nền cho contentPane
		contentPane.setBorder(new LineBorder(Color.BLACK, 2));

		// ====== Panel chia panel thành 3 phần ======
		JPanel chonPhongPanel = new JPanel(new BorderLayout());
		chonPhongPanel.setBackground(Color.BLUE);
		chonPhongPanel.setPreferredSize(new Dimension(300, 800));

		// =========================================== Header
		// ========================================================
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.WHITE); // Màu xám nhạt
		headerPanel.setPreferredSize(new Dimension(screenWidthTrang1, headerHeight));
		// ============== Panel chứa tiêu đề và nút đóng=================phần 1 của
		// header=============================
		JPanel titleClosePanel = new JPanel(new BorderLayout());
		titleClosePanel.setBackground(Color.WHITE);
		titleClosePanel.setPreferredSize(new Dimension(screenWidthTrang1, (int) (headerHeight * 0.65)));
		// Tiêu đề "Chọn phòng"
		JLabel titleLabel = new JLabel("Xác nhận đặt phòng");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Giả sử đã có mã nhân viên "LT001" và số thứ tự từ hàm đếm đơn (sử dụng hàm
		// demSoLuongDonTrongNgay)

		int soThuTu = chitietdondatphongdao.demSoLuongDonTrongNgay() + 1; // Tăng số thứ tự đơn lên một

		// Gọi hàm tạo mã đơn
		maDon = taoMaDonDatPhong(maNhanVien, soThuTu);

		// Tạo JLabel với mã đơn
		JLabel donDatPhongLabel = new JLabel("<html><span style='background-color: #D9D9D9;'>" + maDon + "</span></html>");
		donDatPhongLabel.setFont(new Font("Arial", Font.BOLD, 16));

		// Nút đóng
		JButton closeButton = new JButton("X");
		closeButton.setFont(new Font("Arial", Font.BOLD, 20));
		closeButton.setForeground(Color.BLACK);
		closeButton.setBackground(Color.WHITE);
		closeButton.setFocusPainted(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		closeButton.addActionListener(e -> dispose());

		titleClosePanel.add(titleLabel, BorderLayout.WEST);
		titleClosePanel.add(donDatPhongLabel, BorderLayout.CENTER);
		titleClosePanel.add(closeButton, BorderLayout.EAST);
		headerPanel.add(titleClosePanel, BorderLayout.NORTH);
		// =============================== Center
		// =============================================================
		int[] soLuongPhong = { roomQuantities[0], roomQuantities[1], roomQuantities[2], roomQuantities[3] };
		String[] roomTypes = { "Double Room", "Single Room", "Twin Room", "Triple Room" };
		
		// Format ngày
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		// Lấy ngày từ DatePicker
		Date ngayNhanDate = (Date) datePickerCheckIn.getModel().getValue();
		Date ngayTraDate = (Date) datePickerCheckOut.getModel().getValue();

		String strNgayNhan = (ngayNhanDate != null) ? sdf.format(ngayNhanDate) : "dd/MM/yyyy";
		String strNgayTra = (ngayTraDate != null) ? sdf.format(ngayTraDate) : "dd/MM/yyyy";

		// Tính tổng ngày
		long soNgay = 1;
		if (ngayNhanDate != null && ngayTraDate != null) {
			long diffMillis = ngayTraDate.getTime() - ngayNhanDate.getTime();
			soNgay = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
			if (soNgay <= 0)
				soNgay = 1;
		}

		JPanel centerPanel = new JPanel();
		centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		centerPanel.setBackground(Color.white);
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

		// Header
		JPanel headerPanel1 = new JPanel(new GridLayout(1, 5));
		headerPanel1.setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.BLACK));
		headerPanel1.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.85), 40));
		headerPanel1.setBackground(new Color(144, 238, 144));
		String[] headers = { "Hạng phòng", "Phòng", "Ngày nhận", "Tổng ngày", "Ngày trả" };
		for (String header : headers) {
			JLabel label = new JLabel(header, SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.BOLD, 14));
			headerPanel1.add(label);
		}
		centerPanel.add(headerPanel1, BorderLayout.NORTH);

		// Dòng phòng
		JPanel rowsPanel = new JPanel();
		rowsPanel.setLayout(new BoxLayout(rowsPanel, BoxLayout.Y_AXIS));
		rowsPanel.setBackground(Color.WHITE);

		List<JComboBox<String>> danhSachComboBox = new ArrayList<>();
		
		int tongSoLuongPhong = 0;
		for (int soLuong : soLuongPhong) {
		    tongSoLuongPhong += soLuong;
		}
		danhSachSoPhongDuocChon = new String[ tongSoLuongPhong]; // tongSoDong là tổng số hàng bạn có

		int index = 0;
		for (int i = 0; i < soLuongPhong.length; i++) {
		    String loaiPhong = roomTypes[i];

		    for (int j = 0; j < soLuongPhong[i]; j++) {
		        JPanel row = new JPanel(new GridLayout(1, 5, 10, 10));
		        row.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
		        row.setBackground(Color.WHITE);
		        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

		        // Hạng phòng
		        JLabel roomTypeLabel = new JLabel(loaiPhong, SwingConstants.CENTER);
		        roomTypeLabel.setFont(new Font("Arial", Font.BOLD, 13));
		        row.add(roomTypeLabel);

		        // Phòng
		        JComboBox<String> phongComboBox = new JComboBox<>();
		        danhSachComboBox.add(phongComboBox);

		        try {
		            String loaiPhongDB = loaiPhong.toLowerCase().split(" ")[0];
		            List<String> danhSachPhong = chitietdondatphongdao.layDanhSachPhongTrong(tuNgay, denNgay, loaiPhongDB);

		            for (String soPhong : danhSachPhong) {
		                phongComboBox.addItem(soPhong);
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Lỗi khi lấy danh sách phòng!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		        }

		        // Gán ActionListener để lưu số phòng vào mảng tại vị trí index
		        int currentIndex = index;
		        phongComboBox.addActionListener(e -> {
		            String soPhongChon = (String) phongComboBox.getSelectedItem();
		            danhSachSoPhongDuocChon[currentIndex] = soPhongChon;
		        });

		        // Gán luôn giá trị mặc định ban đầu
		        if (phongComboBox.getItemCount() > 0) {
		            danhSachSoPhongDuocChon[currentIndex] = (String) phongComboBox.getSelectedItem();
		        }


		        row.add(phongComboBox);

		        // Ngày nhận
		        JTextField ngayNhanField = new JTextField(strNgayNhan);
		        ngayNhanField.setEditable(false);
		        row.add(ngayNhanField);

		        // Tổng ngày
		        JTextField tongNgayField = new JTextField(String.valueOf(soNgay));
		        tongNgayField.setEditable(false);
		        row.add(tongNgayField);

		        // Ngày trả
		        JTextField ngayTraField = new JTextField(strNgayTra);
		        ngayTraField.setEditable(false);
		        row.add(ngayTraField);

		        rowsPanel.add(row);
		        index++;
		    }
		}


		JScrollPane scrollPane = new JScrollPane(rowsPanel);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		centerPanel.add(scrollPane, BorderLayout.CENTER);

		// Footer
		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footerPanel.setBackground(Color.WHITE);
		footerPanel.setPreferredSize(new Dimension(screenWidthTrang1, footerHeight));

		JButton confirmButton = new JButton("Xác nhận");
		confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
		confirmButton.setForeground(Color.BLACK);
		confirmButton.setBackground(new Color(0, 180, 0));
		confirmButton.setFocusPainted(false);
		confirmButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		confirmButton.addActionListener(e -> {
			Set<String> daChon = new HashSet<>();
			for (JComboBox<String> comboBox : danhSachComboBox) {
				String selected = (String) comboBox.getSelectedItem();
				if (selected != null) {
					if (daChon.contains(selected)) {
						JOptionPane.showMessageDialog(null,
								"Vui lòng chọn các phòng khác nhau, không được trùng số phòng!", "Trùng số phòng",
								JOptionPane.WARNING_MESSAGE);
						return; // không chuyển trang
					}
					daChon.add(selected);
				}
			}

			// Nếu không trùng phòng thì tiếp tục chuyển trang
			JPanel trang3 = taoTrangNhapThongTin(); // gọi trang tiếp theo
			mainPanel.add(trang3, "Trang3");
			cardLayout.show(mainPanel, "Trang3");
		});
		footerPanel.add(confirmButton);

		// Thêm các phần vào main panel nếu cần (nếu bạn chưa thêm vào main trước đó)
		chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
		chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
		chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

		// Thêm panel chọn phòng vào contentPane
		contentPane.add(chonPhongPanel, BorderLayout.CENTER);

		return contentPane;
	}

	//==============Hàm tạo mã đơn đặt phòng==============
	public String taoMaDonDatPhong(String maNhanVien, int soThuTu) {
	    // Lấy ngày hiện tại
	    LocalDate ngayHienTai = LocalDate.now();
	    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyy");
	    String ngayStr = ngayHienTai.format(dtf);

	    // Bỏ 4 ký tự đầu của mã nhân viên
	    String maNhanVienRutGon = maNhanVien.substring(4);

	    String sttStr = String.format("%03d", soThuTu);

	    // Tạo mã
	    return ngayStr + maNhanVienRutGon + sttStr;
	}

	//==============TAO TRANG TẠO THÔNG TIN KHÁCH HÀNG(TRANG 3)==============
	private JPanel taoTrangNhapThongTin() {
		// Tính toán kích thước các phần
		int headerHeight = (int) (screenHeightTrang1 * 0.1);
		int centerHeight = (int) (screenHeightTrang1 * 0.8);
		int footerHeight = (int) (screenHeightTrang1 * 0.1);
		// Tạo nội dung form (panel con)
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBackground(Color.WHITE); // Màu nền cho contentPane
		contentPane.setBorder(new LineBorder(Color.BLACK, 2));

		// ====== Panel chia panel thành 3 phần ======
		JPanel chonPhongPanel = new JPanel(new BorderLayout());
		chonPhongPanel.setBackground(Color.WHITE);
		chonPhongPanel.setPreferredSize(new Dimension(300, 800));

		// =========================================== Header
		// ========================================================
		JPanel headerPanel = new JPanel(new BorderLayout());
		headerPanel.setBackground(Color.GREEN); // Màu xám nhạt
		headerPanel.setPreferredSize(new Dimension(screenWidthTrang1, headerHeight));

		// ============== Panel chứa tiêu đề và nút đóng=================phần 1 của
		// header=============================
		JButton closeButton = new JButton("X");
		closeButton.setFont(new Font("Arial", Font.BOLD, 20));
		closeButton.setForeground(Color.BLACK);
		closeButton.setBackground(Color.WHITE);
		closeButton.setFocusPainted(false);
		closeButton.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		closeButton.setPreferredSize(new Dimension(45, 30)); // Chiều rộng và chiều cao cố định
		closeButton.addActionListener(e -> dispose());

		// Tạo panel chứa nút để kiểm soát vị trí
		JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
		closePanel.setOpaque(false); // Trong suốt để không ảnh hưởng nền
		closePanel.setPreferredSize(new Dimension(60, (int) (headerHeight * 0.5))); // Chỉ chiếm 50% chiều cao
		closePanel.add(closeButton);

		// ======= Panel chứa tiêu đề căn giữa =======
		JLabel titleLabel = new JLabel("Thông tin khách hàng", JLabel.LEFT);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel centerTitlePanel = new JPanel(new BorderLayout());
		centerTitlePanel.setOpaque(false);
		centerTitlePanel.add(titleLabel, BorderLayout.CENTER);

		// ======= Panel tiêu đề chính =======
		JPanel titleClosePanel = new JPanel(new BorderLayout());
		titleClosePanel.setBackground(Color.WHITE);
		titleClosePanel.setPreferredSize(new Dimension(screenWidthTrang1, (int) (headerHeight * 0.25)));

		titleClosePanel.add(centerTitlePanel, BorderLayout.CENTER);
		titleClosePanel.add(closePanel, BorderLayout.EAST); // Gắn panel nhỏ chứa nút "X"

		headerPanel.add(titleClosePanel);

		// =============================== Center
		// =============================================================
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		centerPanel.setBackground(Color.WHITE);
		centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
		centerPanel.setPreferredSize(new Dimension(screenWidthTrang1, centerHeight));

		// ======================= Top Panel: Thông tin khách hàng mới
		// =======================================
		JLabel lblKhachMoi = new JLabel("Nhập thông tin khách hàng");
		lblKhachMoi.setFont(new Font("Arial", Font.BOLD, 20));
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.WHITE);

		GroupLayout layout = new GroupLayout(topPanel);
		topPanel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		int cochu = 14;

		JLabel lblHoTen = new JLabel("<html>Họ và tên <font color='red'>*</font></html>");
		lblHoTen.setFont(new Font("Arial", Font.BOLD, cochu));
		JTextField txtHoTen = new JTextField();

		JLabel lblSDT = new JLabel("<html>Số điện thoại <font color='red'>*</font></html>");
		lblSDT.setFont(new Font("Arial", Font.BOLD, cochu));
		JTextField txtSDT = new JTextField();

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Arial", Font.BOLD, cochu));
		JTextField txtEmail = new JTextField();

		JLabel lblCccd = new JLabel("<html>Căn cước/hộ chiếu<font color='red'>*</font></html>");
		lblCccd.setFont(new Font("Arial", Font.BOLD, cochu));
		JTextField txtCccd = new JTextField();

		JLabel lblYeuCau = new JLabel("Yêu cầu đặc biệt");
		lblYeuCau.setFont(new Font("Arial", Font.BOLD, cochu));
		JCheckBox chkNoi = new JCheckBox("Thuê nôi");
		chkNoi.setFont(new Font("Arial", Font.BOLD, cochu));
		JCheckBox chkBaoMau = new JCheckBox("Thuê bảo mẫu");
		chkBaoMau.setFont(new Font("Arial", Font.BOLD, cochu));
		JCheckBox chkXeDay = new JCheckBox("Thuê xe đẩy");
		chkXeDay.setFont(new Font("Arial", Font.BOLD, cochu));

		JPanel chkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		chkPanel.setMinimumSize(new Dimension((int) (screenWidthTrang1 * 0.5), 40));
		chkPanel.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.5), 40));
		chkPanel.setMaximumSize(new Dimension((int) (screenWidthTrang1 * 0.5), 40));

		chkPanel.setBackground(Color.WHITE);
		chkPanel.add(chkNoi);
		chkPanel.add(chkBaoMau);
		chkPanel.add(chkXeDay);

		// GroupLayout setup
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(lblKhachMoi, GroupLayout.Alignment.CENTER)
				.addGroup(layout.createSequentialGroup()
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(lblSDT)
								.addComponent(lblHoTen).addComponent(lblEmail).addComponent(lblCccd)
								.addComponent(lblYeuCau))
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(txtSDT)
								.addComponent(txtHoTen).addComponent(txtEmail).addComponent(txtCccd)
								.addComponent(chkPanel))));

		int khoangcach = 20;
		layout.setVerticalGroup(layout.createSequentialGroup().addComponent(lblKhachMoi).addGap(khoangcach)
				.addGroup(layout
						.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblSDT).addComponent(txtSDT))
				.addGap(khoangcach)
				.addGroup(layout
						.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblHoTen).addComponent(txtHoTen))
				.addGap(khoangcach)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblEmail)
						.addComponent(txtEmail))
				.addGap(khoangcach)
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(lblCccd)
						.addComponent(txtCccd))
				.addGap(khoangcach).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addComponent(lblYeuCau).addComponent(chkPanel)));
		setTextFieldHeight(txtHoTen);
		setTextFieldHeight(txtSDT);
		setTextFieldHeight(txtEmail);
		setTextFieldHeight(txtCccd);
		
		txtSDT.getDocument().addDocumentListener(new DocumentListener() {
		    private void timKhach() {
		        String sdt = txtSDT.getText().trim();
		        if (sdt.matches("\\d{10}")) {
		            KhachHang kh = khachhangdao.timKhachHangTheoSoDienThoai(sdt);
		            if (kh != null) {
		                txtHoTen.setText(kh.getHoTen());
		                txtCccd.setText(kh.getSoCCCD());
		                txtEmail.setText(kh.getEmail());
		            } else {
		                txtHoTen.setText("");
		                txtCccd.setText("");
		                txtEmail.setText("");
		            }
		        } else {
		            // Nếu chưa đủ 10 số thì clear
		            txtHoTen.setText("");
		            txtCccd.setText("");
		            txtEmail.setText("");
		        }
		    }

		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        timKhach();
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		        timKhach();
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		        timKhach();
		    }
		});

		centerPanel.add(topPanel, BorderLayout.CENTER);


		// =============================== Footer========================================================================
		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		footerPanel.setBackground(Color.WHITE);
		footerPanel.setPreferredSize(new Dimension(screenWidthTrang1, footerHeight));

		JButton confirmButton = new JButton("Xác nhận đặt phòng");
		confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
		confirmButton.setForeground(Color.BLACK);
		confirmButton.setBackground(new Color(0, 180, 0)); // Màu xanh lá
		confirmButton.setFocusPainted(false);
		confirmButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
		confirmButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String regexSDT = "^(032|033|034|035|036|037|038|039|096|097|098|086|083|084|085|081|082|088|091|094|070|079|077|076|078|090|093|089|056|058|092|059|099)[0-9]{7}$";
				String regexEmail = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
				String regexHoTen = "^([A-Za-zÀ-ỹà-ỹ]+)( [A-Za-zÀ-ỹà-ỹ]+)*$";
				String regexCccd = "^(001|002|004|006|008|010|011|012|014|015|017|019|020|022|023|025|026|027|030|031|033|034|035|036|037|038|040|042|044|045|046|048|049|051|052|054|056|058|060|062|064|066|067|068|070|072|074|075|077|079|080|082|083|084|086|087|089|091|092|093|094|095|096)([0-9])([0-9]{2})([0-9]{6})$";
				// Lấy giá trị nhập vào
				String hoTen = txtHoTen.getText().trim();
				String sdtMoi = txtSDT.getText().trim();
				String email = txtEmail.getText().trim();
				String soCccd = txtCccd.getText().trim();

				// Kiểm tra đủ thông tin
				if (hoTen.isEmpty() || sdtMoi.isEmpty() || soCccd.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đầy đủ thông tin khách hàng mới!");
					return;
				}else {
					// Kiểm tra regex
					if (!hoTen.matches(regexHoTen)) {
						JOptionPane.showMessageDialog(null, "Họ tên không hợp lệ!");
						return;
					}
					if (!sdtMoi.matches(regexSDT)) {
						JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ!");
						return;
					}
					if (!email.isEmpty() && !email.matches(regexEmail)) {
						JOptionPane.showMessageDialog(null, "Email không hợp lệ!");
						return;
					}
					if (!soCccd.matches(regexCccd)) {
						JOptionPane.showMessageDialog(null, "Căn cước công dăn hoặc hộ chiếu không hợp lệ!");
						return;
					}
				}
				

				// Thực hiện xử lý khách hàng mới
				LocalDate ngayHienTai = LocalDate.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");
				String ngayHienTaiStr = ngayHienTai.format(formatter);


				KhachHang kh = chitietdondatphongdao.timKhachHangTheoSDT(sdtMoi);
				if (kh != null) {
					System.out.println("Mã KH vừa mới thêm: " + kh.getMaKH());
					System.out.println("Họ tên vừa mới thêm: " + kh.getHoTen());
				} else {
					System.out.println("Không tìm thấy khách hàng.");
					System.out.println("bắt đầu thêm khách hàng.");
					if (khachhangdao.themThongTinKhachHang(hoTen, sdtMoi, soCccd, ngayHienTaiStr,email)) {
						System.out.println("thêm thông tin khách hàng thành công");
					} else {
						System.out.println("thêm thông tin khách hàng không thành công");
					}
				}
				kh = chitietdondatphongdao.timKhachHangTheoSDT(sdtMoi);
		        double tienCoc = 0;
				//thêm đơn đặt phòng
		        boolean themDonThanhCong = chitietdondatphongdao.themDonDatPhong(maDon, kh.getMaKH(), tuNgay, denNgay, soKhach, tienCoc, maNhanVien, loaiDon, trangThai);
		        if (themDonThanhCong) {
		            System.out.println("Thêm đơn đặt phòng thành công");
		            boolean coLoi = false;

		            for (int i = 0; i < danhSachSoPhongDuocChon.length; i++) {
		                boolean themChiTiet = chitietdondatphongdao.themChiTietDonDatPhong(maDon, danhSachSoPhongDuocChon[i]);
		                if (!themChiTiet) {
		                    coLoi = true;
		                    break;
		                }
		                phongdao.setTrangThaiPhong(danhSachSoPhongDuocChon[i], "Đã đặt");
		            }

		            if (coLoi) {
		                // Nếu có lỗi, xóa DonDatPhong đã thêm
		                dondatphongdao.xoaDonDatPhong(maDon);
		                System.out.println("Có lỗi khi thêm chi tiết. Đã rollback đơn đặt phòng.");
		            }
		            JOptionPane.showMessageDialog(null, "Đặt phòng thành công!");
		        } else {
		            System.out.println("Thêm đơn đặt phòng không thành công");
		        }


			}
			// Nếu nhập phần 2
	});

		footerPanel.add(confirmButton);

		// ============end===============Thêm các phần vào panel chọn phòng=======================
		chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
		chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
		chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

		// Thêm panel chọn phòng vào contentPane
		contentPane.add(chonPhongPanel, BorderLayout.CENTER);

		return contentPane;
	}

	//==============Hàm set chiều cao cho textfield==============
	private void setTextFieldHeight(JTextField field) {
		Dimension size = new Dimension(500, 30); // width có thể để 0 hoặc linh hoạt
		field.setMinimumSize(size);
		field.setPreferredSize(size);
		field.setMaximumSize(size);
	}

	//==============HÀM MAIN==============
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame parentFrame = new JFrame("Cửa sổ chính");
			parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			parentFrame.setSize(400, 400);
			parentFrame.setLocationRelativeTo(null);
			parentFrame.setVisible(true);

			// Mở dialog đặt phòng
			DatPhong_GUI datPhongDialog = new DatPhong_GUI(parentFrame);
			datPhongDialog.setVisible(true);
		});
	}
}
