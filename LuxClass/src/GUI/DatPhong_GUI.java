package GUI;

import javax.swing.*;

import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.ChiTietDonDatPhong_Dao;
import dao.Phong_Dao;
import entity.Phong;

import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DatPhong_GUI extends JDialog {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel contentPane;
    private JPanel centerPanel;
    private int screenWidthTrang1;
    private int screenHeightTrang1;
    private int screenWidthTrang2;
    private int screenHeightTrang2;
    private Phong_Dao phongdao;
    private ChiTietDonDatPhong_Dao chitietdondatphongdao;
    int[] roomQuantities;
    private JDatePickerImpl datePickerCheckIn;
    private JDatePickerImpl datePickerCheckOut;

    

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
//        mainPanel.add(taoTrangXacNhanPhong(), "Trang2");
//        mainPanel.add(taoTrangNhapThongTin(), "Trang3");

        cardLayout.show(mainPanel, "Trang1");

        add(mainPanel);
    }

    private JPanel taoTrangDatPhong() {
    	//tạo biến cho chi tiet don dat phòng 
    	chitietdondatphongdao = new ChiTietDonDatPhong_Dao(); 

        // Tính toán kích thước các phần
        int headerHeight = (int) (screenHeightTrang1 * 0.25);  
        int centerHeight = (int) (screenHeightTrang1 * 0.65); 
        int footerHeight = (int) (screenHeightTrang1 * 0.1);  
        // Tạo nội dung form (panel con)
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);  // Màu nền cho contentPane
        contentPane.setBorder(new LineBorder(Color.BLACK, 2));

     // ====== Panel chọn phòng ======
        JPanel chonPhongPanel = new JPanel(new BorderLayout());
        chonPhongPanel.setBackground(Color.BLUE);
        chonPhongPanel.setPreferredSize(new Dimension(800, 300));
        

        // =========================================== Header ========================================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE); // Màu xám nhạt
        headerPanel.setPreferredSize(new Dimension(screenWidthTrang1, headerHeight));

        //============== Panel chứa tiêu đề và nút đóng=================phần 1 của header=============================
        JPanel titleClosePanel = new JPanel(new BorderLayout());
        titleClosePanel.setBackground(Color.WHITE);
        titleClosePanel.setPreferredSize(new Dimension(screenWidthTrang1, (int)(headerHeight * 0.25)));
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
        //tạo nút chưa 3 nút==================================phần 2 của header====================================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(screenWidthTrang1, (int)(headerHeight * 0.35)));

        //========== Tạo 3 nút với viền
        JButton theoGioButton = new JButton("Theo giờ");
        theoGioButton.setFont(new Font("Arial", Font.BOLD, 14));
        theoGioButton.setBackground(Color.WHITE);
        theoGioButton.setFocusPainted(false);
        theoGioButton.setBorder(new CompoundBorder(
            new LineBorder(Color.BLACK, 2), // Viền ngoài màu đỏ
            new EmptyBorder(5, 15, 5, 15) // Khoảng cách bên trong
        ));

        JButton theoNgayButton = new JButton("Theo ngày");
        theoNgayButton.setFont(new Font("Arial", Font.BOLD, 14));
        theoNgayButton.setBackground(Color.GREEN);
        theoNgayButton.setFocusPainted(false);
        theoNgayButton.setBorder(new CompoundBorder(
            new LineBorder(Color.BLACK, 2), 
            new EmptyBorder(5, 15, 5, 15)
        ));

        JButton theoDemButton = new JButton("Theo đêm");
        theoDemButton.setFont(new Font("Arial", Font.BOLD, 14));
        theoDemButton.setBackground(Color.WHITE);
        theoDemButton.setFocusPainted(false);
        theoDemButton.setBorder(new CompoundBorder(
            new LineBorder(Color.BLACK, 2), 
            new EmptyBorder(5, 15, 5, 15)
        ));

        buttonPanel.add(theoGioButton);
        buttonPanel.add(theoNgayButton);
        buttonPanel.add(theoDemButton);
        theoGioButton.addActionListener(e -> {
            loadGiaoDienTheoGio();
            updateButtonStyles(theoGioButton, theoNgayButton, theoDemButton);
        });

        theoNgayButton.addActionListener(e -> {
            loadGiaoDienTheoNgay();
            updateButtonStyles(theoNgayButton, theoGioButton, theoDemButton);
        });

        theoDemButton.addActionListener(e -> {
            loadGiaoDienTheoDem();
            updateButtonStyles(theoDemButton, theoGioButton, theoNgayButton);
        });



        //=============Panel chứa các thông tin ngày và khách============phần 3 của header=====================
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // Giảm khoảng cách GridLayout
        infoPanel.setPreferredSize(new Dimension(screenWidthTrang1, (int)(headerHeight * 0.40)));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0)); 
        infoPanel.setBackground(Color.WHITE);

        // Ngày nhận phòng
        JPanel checkInPanel = new JPanel(new BorderLayout());
        checkInPanel.setBackground(Color.WHITE);

        JLabel checkInLabel = new JLabel("Ngày nhận phòng");
        checkInLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkInPanel.add(checkInLabel, BorderLayout.NORTH);

        // Đặt DatePicker vào JPanel với FlowLayout để tránh khoảng trắng dư
        JPanel checkInWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkInWrapper.setBackground(Color.WHITE);
        //tạo ngày tháng năm nhận phòng
        datePickerCheckIn = createDatePicker();
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
        //tạo ngay thang năm trả phòng
        datePickerCheckOut = createDatePicker();
        checkOutWrapper.add(datePickerCheckOut);

        checkOutPanel.add(checkOutWrapper, BorderLayout.CENTER);

        // Panel chứa số lượng khách
        JPanel guestPanel = new JPanel(new BorderLayout());
        guestPanel.setBackground(Color.WHITE);

        JLabel guestLabel = new JLabel("                    Số lượng khách");
        guestLabel.setFont(new Font("Arial", Font.BOLD, 14));
        guestPanel.add(guestLabel, BorderLayout.NORTH);

        JPanel guestControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        guestControlPanel.setBackground(Color.WHITE);

        JButton minusButton = new JButton("-");
        minusButton.setFont(new Font("Arial", Font.BOLD, 14));
        minusButton.setPreferredSize(new Dimension(45, 25));

        JLabel guestCountLabel = new JLabel("2"); // Mặc định là 2 như trong hình
        guestCountLabel.setText("0"); 
        guestCountLabel.setFont(new Font("Arial", Font.BOLD, 14));
        guestCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        guestCountLabel.setPreferredSize(new Dimension(30, 25));

        JButton plusButton = new JButton("+");
        plusButton.setFont(new Font("Arial", Font.BOLD, 14));
        plusButton.setPreferredSize(new Dimension(45, 25));

        minusButton.addActionListener(e -> {
            int count = Integer.parseInt(guestCountLabel.getText());
            if (count > 0) guestCountLabel.setText(String.valueOf(count - 1));
        });

        plusButton.addActionListener(e -> {
            int count = Integer.parseInt(guestCountLabel.getText());
            guestCountLabel.setText(String.valueOf(count + 1));
        });

        guestControlPanel.add(minusButton);
        guestControlPanel.add(guestCountLabel);
        guestControlPanel.add(plusButton);
        guestPanel.add(guestControlPanel, BorderLayout.CENTER);

        infoPanel.add(checkInPanel);
        infoPanel.add(checkOutPanel);
        infoPanel.add(guestPanel);


        // Panel chứa tất cả nội dung header
        JPanel headerContentPanel = new JPanel();
        headerContentPanel.setLayout(new BoxLayout(headerContentPanel, BoxLayout.Y_AXIS));
        headerContentPanel.setBackground(new Color(220, 220, 220));

        headerContentPanel.add(titleClosePanel);
        headerContentPanel.add(buttonPanel);
        headerContentPanel.add(infoPanel);

        headerPanel.add(headerContentPanel, BorderLayout.CENTER);


     // =============================== Center =========================================================================

        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(screenWidthTrang1, centerHeight));

        // Load mặc định khi khởi tạo
        loadGiaoDienTheoNgay();

        // =============================== Footer ========================================================================
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
            JPanel trang2 = taoTrangXacNhanPhong(); // tạo lại giao diện Trang 2, lúc này roomQuantities đã có giá trị mới
            mainPanel.add(trang2, "Trang2");
            cardLayout.show(mainPanel, "Trang2");
        });
        footerPanel.add(confirmButton);

        //============end===============Thêm các phần vào panel chọn phòng==========================================
        chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
        chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
        chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

        // Thêm panel chọn phòng vào contentPane
        contentPane.add(chonPhongPanel, BorderLayout.CENTER);
        
        return contentPane;
    }
    // Hàm cập nhật màu cho 3 nút
    private void updateButtonStyles(JButton selected, JButton... others) {
        selected.setBackground(Color.GREEN); // Nút được chọn sẽ tô màu xanh

        for (JButton btn : others) {
            btn.setBackground(Color.WHITE); // Các nút còn lại sẽ về màu trắng
        }
    }

     // Phương thức tạo DatePicker tạo cho ngày nhân phong va ngay trả phòng
     private JDatePickerImpl createDatePicker() {
         UtilDateModel model = new UtilDateModel();
         Properties p = new Properties();
         p.put("text.today", "Today");
         p.put("text.month", "Month");
         p.put("text.year", "Year");

         // Tạo panel chứa lịch
         JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

         // Tạo date picker với formatter mặc định
         JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

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
                     //kích thước của button chọn time
                     int ngang=25;
                     int cao =30;
                     button.setPreferredSize(new Dimension(cao, ngang));
                     ImageIcon icon = new ImageIcon(getClass().getResource("/HinhAnhGiaoDienChinh/lich.png"));
                     Image scaledImage = icon.getImage().getScaledInstance(cao, ngang, Image.SCALE_SMOOTH);
                     ImageIcon resizedIcon = new ImageIcon(scaledImage);
                     button.setIcon(resizedIcon);
//                     System.out.println("Button: " + button.getText());

                 }
             }
             
         } catch (Exception e) {
             System.err.println("Không thể tùy chỉnh date picker: " + e.getMessage());
         }

         return datePicker;
     }
     private JPanel createRoomRow(String roomName, int roomPrice, int soPhongTrong, JLabel[] totalLabels, int[] roomQuantities, int index) {
    	    JPanel rowPanel = new JPanel(new GridLayout(1, 4));
    	    rowPanel.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.95), (int)(screenHeightTrang2 * 0.17)));
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
	// Hàm cập nhật tổng cộng
	private void updateTotal(JLabel totalLabel, int qty, int roomPrice) {
		    int total = qty * roomPrice;
		    totalLabel.setText(formatCurrency(total));
		}
  	private String formatCurrency(int amount) {
  	    DecimalFormat df = new DecimalFormat("#,###");
  	    return df.format(amount).replace(",", ".") + " VND";
  	}


  	private void loadGiaoDienTheoNgay() {
  	    centerPanel.removeAll();

  	    // ======= Header Gợi ý phòng ========
  	    JPanel headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
  	    headerContainer.setPreferredSize(new Dimension(screenWidthTrang1, 60));
  	    headerContainer.setBackground(Color.WHITE);
  	    
  	    JPanel headerPanel1 = new JPanel(new GridLayout(1, 4));
  	    headerPanel1.setBackground(new Color(144, 238, 144));
  	    headerPanel1.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.95), 40));
  	    headerContainer.add(headerPanel1);
  	    String[] headers = {"Gợi ý phòng", "Giá", "Số lượng", "Tổng cộng"};
  	    for (String text : headers) {
  	        JLabel label = new JLabel(text, SwingConstants.CENTER);
  	        label.setFont(new Font("Arial", Font.BOLD, 14));
  	        headerPanel1.add(label);
  	    }

  	    // Danh sách loại phòng
      // ======== Tạo bảng nội dung bên dưới ========
	  String[] roomNames = {"Double Room","Single Room", "Twin Room",  "Triple Room"};
	
	  // ==== Tạo mảng lưu số lượng phòng được chọn ====
	  roomQuantities = new int[roomNames.length]; // <- Mảng này sẽ được cập nhật trong createRoomRow()

	  // Khai báo mảng JLabel để lưu "Tổng cộng" cho từng loại phòng
	  JLabel[] totalLabels = new JLabel[roomNames.length];
	  
	  // Panel chứa các hàng
	  JPanel contentPanel = new JPanel();
	  contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	  contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20)); // top, left, bottom, right
	  contentPanel.setBackground(Color.WHITE);
	  
	  // ===== Double Room =====
	  JPanel rowPanelDouble = createRoomRow("Double Room", chitietdondatphongdao.GetPriceToDay(roomNames[0]), chitietdondatphongdao.CountNumberRoom(roomNames[0]), totalLabels, roomQuantities, 0);
	  contentPanel.add(rowPanelDouble);
	  contentPanel.add(Box.createVerticalStrut(10)); // khoảng cách dọc 10px
	
	  // ===== Single Room =====
	  JPanel rowPanelSingle = createRoomRow("Single Room", chitietdondatphongdao.GetPriceToDay(roomNames[1]), chitietdondatphongdao.CountNumberRoom(roomNames[1]), totalLabels, roomQuantities, 1);
	  contentPanel.add(rowPanelSingle);
	  contentPanel.add(Box.createVerticalStrut(10));
	
	  // ===== Twin Room =====
	  JPanel rowPanelTwin = createRoomRow("Twin Room", chitietdondatphongdao.GetPriceToDay(roomNames[2]), chitietdondatphongdao.CountNumberRoom(roomNames[2]), totalLabels, roomQuantities, 2);
	  contentPanel.add(rowPanelTwin);
	  contentPanel.add(Box.createVerticalStrut(10));
	
	  // ===== Triple Room =====
	  JPanel rowPanelTriple = createRoomRow("Triple Room", chitietdondatphongdao.GetPriceToDay(roomNames[3]), chitietdondatphongdao.CountNumberRoom(roomNames[3]), totalLabels, roomQuantities, 3);
	  contentPanel.add(rowPanelTriple);

  	    // Thêm header và nội dung vào centerPanel
  	    centerPanel.add(headerContainer, BorderLayout.NORTH);
  	    centerPanel.add(contentPanel, BorderLayout.CENTER);

  	    // Cập nhật lại UI
  	    centerPanel.revalidate();
  	    centerPanel.repaint();
  	}
  	
  	private void loadGiaoDienTheoGio() {
  	    centerPanel.removeAll();

  	    JLabel label = new JLabel("Giao diện đặt theo GIỜ", SwingConstants.CENTER);
  	    label.setFont(new Font("Arial", Font.BOLD, 24));
  	    centerPanel.add(label, BorderLayout.CENTER);

  	    centerPanel.revalidate();
  	    centerPanel.repaint();
  	}

  	// Giao diện đặt theo đêm (label thôi)
  	private void loadGiaoDienTheoDem() {
  	    centerPanel.removeAll();

  	    JLabel label = new JLabel("Giao diện đặt theo ĐÊM", SwingConstants.CENTER);
  	    label.setFont(new Font("Arial", Font.BOLD, 24));
  	    centerPanel.add(label, BorderLayout.CENTER);

  	    centerPanel.revalidate();
  	    centerPanel.repaint();
  	}

     //==============================Trang 2========================================================
    private JPanel taoTrangXacNhanPhong() {
    	// Tính toán kích thước các phần
        int headerHeight = (int) (screenHeightTrang1 * 0.1);  
        int centerHeight = (int) (screenHeightTrang1 * 0.8); 
        int footerHeight = (int) (screenHeightTrang1 * 0.1);  
        // Tạo nội dung form (panel con)
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);  // Màu nền cho contentPane
        contentPane.setBorder(new LineBorder(Color.BLACK, 2));
        
        // ====== Panel chia panel thành 3 phần ======
        JPanel chonPhongPanel = new JPanel(new BorderLayout());
        chonPhongPanel.setBackground(Color.BLUE);
        chonPhongPanel.setPreferredSize(new Dimension(300, 800));
        
        // =========================================== Header ========================================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE); // Màu xám nhạt
        headerPanel.setPreferredSize(new Dimension(screenWidthTrang1, headerHeight));
        //============== Panel chứa tiêu đề và nút đóng=================phần 1 của header=============================
        JPanel titleClosePanel = new JPanel(new BorderLayout());
        titleClosePanel.setBackground(Color.WHITE);
        titleClosePanel.setPreferredSize(new Dimension(screenWidthTrang1, (int)(headerHeight * 0.65)));
        // Tiêu đề "Chọn phòng"
        JLabel titleLabel = new JLabel("Xác nhận đặt phòng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        //chứa thông tin mã đơn phòng
        JLabel donDatPhongLabel = new JLabel("<html><span style='background-color: Gray;'>21032025LT0010001</span></html>");
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
        // =============================== Center =============================================================
     // Giả sử bạn có các biến giữ số lượng phòng đã chọn từ giao diện 1
        int[] soLuongPhong = {roomQuantities[0], roomQuantities[1], roomQuantities[2], roomQuantities[3]};
        String[] roomTypes = {"Single Room", "Twin Room", "Double Room", "Triple Room"};

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
            if (soNgay <= 0) soNgay = 1;
        }

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.white);
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // Header
        JPanel headerPanel1 = new JPanel(new GridLayout(1, 5));
        headerPanel1.setPreferredSize(new Dimension((int) (screenWidthTrang1 * 0.85), 40));
        headerPanel1.setBackground(new Color(144, 238, 144));
        String[] headers = {"Hạng phòng", "Phòng", "Ngày nhận", "Tổng ngày", "Ngày trả"};
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

        for (int i = 0; i < soLuongPhong.length; i++) {
            for (int j = 0; j < soLuongPhong[i]; j++) {
                JPanel row = new JPanel(new GridLayout(1, 5, 10, 10));
                row.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                row.setBackground(Color.WHITE);
                row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
                row.setPreferredSize(new Dimension(0, 40));
                row.setMinimumSize(new Dimension(0, 40));

                // Hạng phòng
                JLabel roomTypeLabel = new JLabel(roomTypes[i], SwingConstants.CENTER);
                roomTypeLabel.setFont(new Font("Arial", Font.BOLD, 13));
                row.add(roomTypeLabel);

                // Phòng
                JComboBox<String> phongComboBox = new JComboBox<>(new String[]{"Chọn phòng..."});
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
            }
        }

        JScrollPane scrollPane = new JScrollPane(rowsPanel);
        scrollPane.setBorder(null);
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
            JPanel trang3 = taoTrangNhapThongTin(); // gọi trang tiếp theo
            mainPanel.add(trang3, "Trang3");
            cardLayout.show(mainPanel, "Trang3");
        });
        footerPanel.add(confirmButton);

        // Thêm các phần vào main panel nếu cần (nếu bạn chưa thêm vào main trước đó)


      //============end===============Thêm các phần vào panel chọn phòng==========================================
        chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
        chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
        chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

        // Thêm panel chọn phòng vào contentPane
        contentPane.add(chonPhongPanel, BorderLayout.CENTER);

        return contentPane;
    }
    //========================Trang 3=====================================================
    private JPanel taoTrangNhapThongTin() {
//        // Lấy kích thước màn hình
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        screenWidthTrang2 = (int) (screenSize.width * 0.3);
//        screenHeightTrang2 = (int) (screenSize.height * 0.6);
//
//        // Đặt kích thước dialog
//        setSize(screenWidthTrang2, screenHeightTrang2);

    	// Tính toán kích thước các phần
        int headerHeight = (int) (screenHeightTrang1 * 0.2);  
        int centerHeight = (int) (screenHeightTrang1 * 0.7); 
        int footerHeight = (int) (screenHeightTrang1 * 0.1);  
        // Tạo nội dung form (panel con)
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBackground(Color.WHITE);  // Màu nền cho contentPane
        contentPane.setBorder(new LineBorder(Color.BLACK, 2));
        
        // ====== Panel chia panel thành 3 phần ======
        JPanel chonPhongPanel = new JPanel(new BorderLayout());
        chonPhongPanel.setBackground(Color.BLUE);
        chonPhongPanel.setPreferredSize(new Dimension(300, 800));
        
        // =========================================== Header ========================================================
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.GREEN); // Màu xám nhạt
        headerPanel.setPreferredSize(new Dimension(screenWidthTrang1, headerHeight));
        
        //============== Panel chứa tiêu đề và nút đóng=================phần 1 của header=============================
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
        closePanel.setPreferredSize(new Dimension(60, (int)(headerHeight * 0.5))); // Chỉ chiếm 50% chiều cao
        closePanel.add(closeButton);

        // ======= Panel chứa tiêu đề căn giữa =======
        JLabel titleLabel = new JLabel("Thông tin khách hàng", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel centerTitlePanel = new JPanel(new BorderLayout());
        centerTitlePanel.setOpaque(false);
        centerTitlePanel.add(titleLabel, BorderLayout.CENTER);

        // ======= Panel tiêu đề chính =======
        JPanel titleClosePanel = new JPanel(new BorderLayout());
        titleClosePanel.setBackground(Color.WHITE);
        titleClosePanel.setPreferredSize(new Dimension(screenWidthTrang1, (int)(headerHeight * 0.25)));

        titleClosePanel.add(centerTitlePanel, BorderLayout.CENTER);
        titleClosePanel.add(closePanel, BorderLayout.EAST); // Gắn panel nhỏ chứa nút "X"


        headerPanel.add(titleClosePanel);
        
        // =============================== Center =============================================================
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));

        centerPanel.setPreferredSize(new Dimension(screenWidthTrang1, centerHeight));

        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        // ====== Tạo các thành phần ======
        //cỡ chữ
        int cochu =14;
        JLabel lblHoTen = new JLabel("<html>Họ và tên <font color='red'>*</font></html>");
        lblHoTen.setFont(new Font("Arial", Font.BOLD, cochu));
        JTextField txtHoTen = new JTextField();

        JLabel lblSDT = new JLabel("<html>Số điện thoại <font color='red'>*</font></html>");
        lblSDT.setFont(new Font("Arial", Font.BOLD, cochu));
        JTextField txtSDT = new JTextField();

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Arial", Font.BOLD, cochu));
        JTextField txtEmail = new JTextField();

        JLabel lblTienCoc = new JLabel("Tiền cọc");
        lblTienCoc.setFont(new Font("Arial", Font.BOLD, cochu));
        JTextField txtTienCoc = new JTextField();

        JLabel lblYeuCau = new JLabel("Yêu cầu đặc biệt");
        lblYeuCau.setFont(new Font("Arial", Font.BOLD, cochu));
        JCheckBox chkNoi = new JCheckBox("Thuê nôi");
        chkNoi.setFont(new Font("Arial", Font.BOLD, cochu));
        JCheckBox chkBaoMau = new JCheckBox("Thuê bảo mẫu");
        chkBaoMau.setFont(new Font("Arial", Font.BOLD, cochu));
        JCheckBox chkXeDay = new JCheckBox("Thuê xe đẩy");
        chkXeDay.setFont(new Font("Arial", Font.BOLD, cochu));

        JPanel chkPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chkPanel.setBackground(Color.WHITE);
        chkPanel.add(chkNoi);
        chkPanel.add(chkBaoMau);
        chkPanel.add(chkXeDay);
        
        // ====== Setup layout ======
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(lblHoTen)
                    .addComponent(lblSDT)
                    .addComponent(lblEmail)
                    .addComponent(lblTienCoc)
                    .addComponent(lblYeuCau))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(txtHoTen)
                    .addComponent(txtSDT)
                    .addComponent(txtEmail)
                    .addComponent(txtTienCoc)
                    .addComponent(chkPanel))
        );
        int khoangcach =20;
        layout.setVerticalGroup(
        	    layout.createSequentialGroup()
        	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	            .addComponent(lblHoTen)
        	            .addComponent(txtHoTen))
        	        .addGap(khoangcach)
        	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	            .addComponent(lblSDT)
        	            .addComponent(txtSDT))
        	        .addGap(khoangcach)
        	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	            .addComponent(lblEmail)
        	            .addComponent(txtEmail))
        	        .addGap(khoangcach)
        	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
        	            .addComponent(lblTienCoc)
        	            .addComponent(txtTienCoc))
        	        .addGap(khoangcach)
        	        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
        	            .addComponent(lblYeuCau)
        	            .addComponent(chkPanel))
        	);
        setTextFieldHeight(txtHoTen);
        setTextFieldHeight(txtSDT);
        setTextFieldHeight(txtEmail);
        setTextFieldHeight(txtTienCoc);

        
        // =============================== Footer ========================================================================
//        JPanel footerPanel = new JPanel(new BorderLayout());
//        footerPanel.setBackground(Color.WHITE);
//        footerPanel.setPreferredSize(new Dimension(screenWidthTrang1, footerHeight));
//
//        // Nút "Xác nhận"
//        JButton confirmButton = new JButton("Xác nhận đặt phòng");
//        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
//        confirmButton.setForeground(Color.BLACK);
//        confirmButton.setBackground(new Color(0, 180, 0)); // Màu xanh lá
//        confirmButton.setFocusPainted(false);
//        confirmButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
//        confirmButton.addActionListener(e -> cardLayout.show(mainPanel, "Trang1"));
//
//        // Tạo panel phụ để đặt nút sát góc dưới phải và cách 10px
//        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 15));
//        rightPanel.setOpaque(false); // trong suốt để không che màu nền
//        rightPanel.add(confirmButton);
//
//        // Thêm vào footer (ở vị trí Đông - bên phải)
//        footerPanel.add(rightPanel, BorderLayout.EAST);
        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setPreferredSize(new Dimension(screenWidthTrang1, footerHeight));

        JButton confirmButton = new JButton("Xác nhận đặt phòng");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setBackground(new Color(0, 180, 0)); // Màu xanh lá
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        confirmButton.addActionListener(e -> cardLayout.show(mainPanel, "Trang1"));
        footerPanel.add(confirmButton);

        
        //============end===============Thêm các phần vào panel chọn phòng==========================================
        chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
        chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
        chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

        // Thêm panel chọn phòng vào contentPane
        contentPane.add(chonPhongPanel, BorderLayout.CENTER);

        return contentPane;
    }
    private void setTextFieldHeight(JTextField field) {
        Dimension size = new Dimension(500, 30); // width có thể để 0 hoặc linh hoạt
        field.setMinimumSize(size);
        field.setPreferredSize(size);
        field.setMaximumSize(size);
    }

//=====================================================
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
