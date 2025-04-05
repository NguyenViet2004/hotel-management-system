package GUI;

import java.awt.*;
import java.awt.event.*;
import java.util.Properties;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

public class DatPhong_GUI extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public DatPhong_GUI(JFrame parentFrame) {
        super(parentFrame, "Form Đặt Phòng", true); // true để làm cho nó modal
        setUndecorated(true);

        // Lấy kích thước màn hình một lần duy nhất
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.width * 0.65);
        int screenHeight = (int) (screenSize.height * 0.65);

        // Đặt kích thước JFrame
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(parentFrame); // Đặt ở giữa cửa sổ chính
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        // Tính toán kích thước các phần
        int headerHeight = (int) (screenHeight * 0.25);  // 30% của 80% màn hình
        int centerHeight = (int) (screenHeight * 0.65);  // 60% của 80% màn hình
        int footerHeight = (int) (screenHeight * 0.1);  // 10% của 80% màn hình
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
        headerPanel.setPreferredSize(new Dimension(screenWidth, headerHeight));

        //============== Panel chứa tiêu đề và nút đóng=================phần 1 của header=============================
        JPanel titleClosePanel = new JPanel(new BorderLayout());
        titleClosePanel.setBackground(Color.WHITE);
        titleClosePanel.setPreferredSize(new Dimension(screenWidth, (int)(headerHeight * 0.25)));
        // Tiêu đề "Chọn phòng"
        JLabel titleLabel = new JLabel("Chọn phòng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Nút đóng
        JButton closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBackground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        closeButton.addActionListener(e -> dispose());

        titleClosePanel.add(titleLabel, BorderLayout.WEST);
        titleClosePanel.add(closeButton, BorderLayout.EAST);
        //tạo nút chưa 3 nút==================================phần 2 của header====================================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setPreferredSize(new Dimension(screenWidth, (int)(headerHeight * 0.35)));

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
        theoNgayButton.setBackground(Color.WHITE);
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

        //=============Panel chứa các thông tin ngày và khách============phần 3 của header=====================
        JPanel infoPanel = new JPanel(new GridLayout(1, 3, 5, 5)); // Giảm khoảng cách GridLayout
        infoPanel.setPreferredSize(new Dimension(screenWidth, (int)(headerHeight * 0.40)));
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
        checkInWrapper.add(createDatePicker());

        checkInPanel.add(checkInWrapper, BorderLayout.CENTER);

        // Ngày trả phòng
        JPanel checkOutPanel = new JPanel(new BorderLayout());
        checkOutPanel.setBackground(Color.WHITE);

        JLabel checkOutLabel = new JLabel("Ngày trả phòng");
        checkOutLabel.setFont(new Font("Arial", Font.BOLD, 14));
        checkOutPanel.add(checkOutLabel, BorderLayout.NORTH);

        JPanel checkOutWrapper = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkOutWrapper.setBackground(Color.WHITE);
        checkOutWrapper.add(createDatePicker());

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
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(screenWidth, centerHeight));

        // ======== Tạo header cho centerPanel ========
        // Tạo JPanel cha để chứa headerPanel1 và tạo khoảng cách
        JPanel headerContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerContainer.setPreferredSize(new Dimension(screenWidth, 60)); // Tăng chiều cao để đảm bảo khoảng cách
        headerContainer.setBackground(Color.WHITE); // Để nền trắng giúp thấy rõ khoảng cách
        
        JPanel headerPanel1 = new JPanel(new GridLayout(1, 4)); // 1 hàng, 4 cột
        headerPanel1.setBackground(new Color(144, 238, 144)); // Màu xanh lá nhạt
        headerPanel1.setPreferredSize(new Dimension((int) (screenWidth * 0.95), 40));
        // Thêm headerPanel1 vào JPanel cha
        headerContainer.add(headerPanel1);

        // Danh sách tiêu đề
        String[] headers = {"Gợi ý phòng", "Giá", "Số lượng", "Tổng cộng"};
        for (String text : headers) {
            JLabel label = new JLabel(text, SwingConstants.CENTER);
            label.setFont(new Font("Arial", Font.BOLD, 14));
            label.setForeground(Color.BLACK);
            headerPanel1.add(label);
        }

        // ======== Tạo bảng nội dung bên dưới ========
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        // Giá từng loại phòng
        int singleRoomPrice = 800000;
        int twinRoomPrice = 1200000;
        int doubleRoomPrice = 1000000;
        int tripleRoomPrice = 1400000;

        // Tên và giá phòng
        String[] roomNames = {"Single Room", "Twin Room", "Double Room", "Triple Room"};
        int[] roomPrices = {singleRoomPrice, twinRoomPrice, doubleRoomPrice, tripleRoomPrice};

        // Danh sách để lưu tổng cộng từng hàng
        JLabel[] totalLabels = new JLabel[roomNames.length];

        for (int i = 0; i < roomNames.length; i++) {
            JPanel rowPanel = new JPanel(new GridLayout(1, 4));
            rowPanel.setPreferredSize(new Dimension((int) (screenWidth * 0.95), (int)(centerHeight * 0.17)));
            rowPanel.setBackground(Color.decode("#EEEEEE"));
            rowPanel.setBorder(new LineBorder(Color.decode("#D9D9D9"), 2));
//            rowPanel.setBorder(BorderFactory.createCompoundBorder(
//            	    new EmptyBorder(0, 0, 10, 0), // top, left, bottom, right
//            	    new LineBorder(Color.decode("#D9D9D9"), 2)
//            	));

            // === Tên phòng + số lượng trống ===
            JPanel namePanel = new JPanel(new BorderLayout());
            namePanel.setBackground(Color.decode("#EEEEEE"));

            JLabel nameLabel = new JLabel(roomNames[i], SwingConstants.CENTER);
            nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            // Đếm số phòng trống
            int finalI = i;
//            long soPhongTrong = danhSachPhong.stream()
//                .filter(p -> p.getLoaiPhong().equals(roomNames[finalI]) && p.getTrangThai().equals("Trống"))
//                .count();
            long soPhongTrong =4;
            JLabel availabilityLabel = new JLabel("Còn " + soPhongTrong + " phòng trống", SwingConstants.CENTER);
            availabilityLabel.setFont(new Font("Arial", Font.ITALIC, 12));
            availabilityLabel.setForeground(Color.RED);

            namePanel.add(nameLabel, BorderLayout.NORTH);
            namePanel.add(availabilityLabel, BorderLayout.SOUTH);

            rowPanel.add(namePanel); // Thêm vào cột 1

            // === Giá phòng ===
            JLabel priceLabel = new JLabel(String.valueOf(roomPrices[i]) + " VND", SwingConstants.CENTER);
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
            totalLabels[i] = totalLabel;
            rowPanel.add(totalLabel);

            // === Xử lý tăng giảm số lượng ===
            int roomIndex = i;
            decreaseButton.addActionListener(e -> {
                int qty = Integer.parseInt(quantityField.getText());
                if (qty > 0) qty--;
                quantityField.setText(String.valueOf(qty));
                updateTotal(totalLabels[roomIndex], qty, roomPrices[roomIndex]);
            });

            increaseButton.addActionListener(e -> {
                int qty = Integer.parseInt(quantityField.getText());
                qty++;
                quantityField.setText(String.valueOf(qty));
                updateTotal(totalLabels[roomIndex], qty, roomPrices[roomIndex]);
            });

            // Thêm hàng vào contentPanel
            contentPanel.add(rowPanel);
        }



        // Thêm header và contentPanel vào centerPanel
        centerPanel.add(headerContainer, BorderLayout.NORTH);
        centerPanel.add(contentPanel, BorderLayout.CENTER);

        // =============================== Footer ========================================================================
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.setBackground(Color.WHITE);
        footerPanel.setPreferredSize(new Dimension(screenWidth, footerHeight));

        JButton confirmButton = new JButton("Xác nhận");
        confirmButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmButton.setForeground(Color.WHITE);
        confirmButton.setBackground(new Color(0, 180, 0)); // Màu xanh lá
        confirmButton.setFocusPainted(false);
        confirmButton.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        footerPanel.add(confirmButton);

        //============end===============Thêm các phần vào panel chọn phòng==========================================
        chonPhongPanel.add(headerPanel, BorderLayout.NORTH);
        chonPhongPanel.add(centerPanel, BorderLayout.CENTER);
        chonPhongPanel.add(footerPanel, BorderLayout.SOUTH);

        // Thêm panel chọn phòng vào contentPane
        contentPane.add(chonPhongPanel, BorderLayout.CENTER);

        // Thêm contentPane vào JFrame
        add(contentPane);
    }

	// Hàm cập nhật tổng cộng
	private void updateTotal(JLabel label, int quantity, int price) {
	    int total = quantity * price;
	    label.setText(total + " VND");
	}
    
    // Phương thức tạo DatePicker
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
//                    System.out.println("Button: " + button.getText());

                }
            }
            
        } catch (Exception e) {
            System.err.println("Không thể tùy chỉnh date picker: " + e.getMessage());
        }

        return datePicker;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // Thêm hành động khi cần thiết
    }

    // Hàm main để khởi tạo cửa sổ DatPhong_GUI
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame parentFrame = new JFrame(); // Cửa sổ chính
                parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                parentFrame.setSize(400, 400); // Cửa sổ chính có kích thước 400x400
                parentFrame.setLocationRelativeTo(null); // Đặt cửa sổ ở giữa màn hình
                parentFrame.setVisible(true);

                // Sau khi cửa sổ chính được hiển thị, mở form Đặt Phòng
                DatPhong_GUI dialog = new DatPhong_GUI(parentFrame);
                dialog.setVisible(true); // Hiển thị form đăng nhập
            }
        });
    }
}
