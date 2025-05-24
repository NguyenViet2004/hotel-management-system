package Duong;

import javax.swing.*;

import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.sql.Connection;
import connectDB.ConnectDB; // Đảm bảo import lớp ConnectDB của bạn
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException; // ADDED: For date parsing
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import dao.sampledata_Dao;
import entity.sampledata;

public class ThongKeDoanhThuGUI extends JFrame {

    // Định nghĩa các màu sắc sử dụng trong giao diện
    private static final Color COLOR_BACKGROUND = new Color(248, 249, 250);
    private static final Color COLOR_HEADER_FOOTER_SIDEBAR_BG = Color.WHITE;
    private static final Color COLOR_PRIMARY_BUTTON_BG = new Color(76, 175, 80);
    private static final Color COLOR_PRIMARY_BUTTON_FG = Color.WHITE;
    private static final Color COLOR_DEFAULT_BUTTON_BG = new Color(245, 245, 245);
    private static final Color COLOR_DEFAULT_BUTTON_FG = new Color(33, 33, 33);
    private static final Color COLOR_DARK_TEXT = new Color(33, 33, 33);
    private static final Color COLOR_MEDIUM_TEXT = new Color(85, 85, 85);
    private static final Color COLOR_LIGHT_TEXT = new Color(117, 117, 117);
    private static final Color COLOR_BORDER = new Color(222, 226, 230);
    private static final Color COLOR_TABLE_HEADER_BG = new Color(245, 245, 245);
    private static final Color COLOR_CHART_BAR_BLUE = new Color(66, 133, 244);
    private static final Color COLOR_CHART_BAR_ORANGE = new Color(251, 188, 5);
    private static final Color COLOR_CONTENT_AREA_TITLE_BG = new Color(230, 230, 230);
    public sampledata_Dao dao = new sampledata_Dao();

    // Định nghĩa các font chữ sử dụng trong giao diện
    private static final Font FONT_LOGO_LUX = new Font("Arial", Font.BOLD, 28);
    private static final Font FONT_LOGO_HOTEL = new Font("Arial", Font.PLAIN, 10);
    private static final Font FONT_SIDEBAR_SECTION_TITLE = new Font("Arial", Font.BOLD, 15);
    private static final Font FONT_SIDEBAR_ITEM = new Font("Arial", Font.BOLD, 14);
    private static final Font FONT_BUTTON_GROUP = new Font("Arial", Font.BOLD, 13);
    private static final Font FONT_TABLE_HEADER = new Font("Arial", Font.BOLD, 13);
    private static final Font FONT_TABLE_CELL = new Font("Arial", Font.PLAIN, 13);
    private static final Font FONT_LABEL = new Font("Arial", Font.PLAIN, 13);
    private static final Font FONT_PLACEHOLDER = new Font("Arial", Font.ITALIC, 13);
    private static final Font FONT_CONTENT_AREA_TITLE = new Font("Arial", Font.BOLD, 16);
    private static final Font FONT_CHART_TITLE_TEXT = new Font("Arial", Font.BOLD, 15);
    private static final Font FONT_CHART_AXIS = new Font("Arial", Font.PLAIN, 10);
    private static final Font FONT_SUMMARY_TOTAL_TITLE = new Font("Arial", Font.PLAIN, 13);
    private static final Font FONT_SUMMARY_TOTAL_VALUE = new Font("Arial", Font.BOLD, 18);
    private static final Font FONT_SUMMARY_SUB = new Font("Arial", Font.PLAIN, 11);

    // Các thành phần giao diện chính
    private JPanel mainContentPanel;
    private CardLayout cardLayout;
    private JPanel tableViewPanel;
    private JPanel chartViewPanel;
    private JTable revenueTable;
    private DefaultTableModel tableModel;
    private JButton btnSoLieuTable, btnBieuDoTable, btnSoLieuChart, btnBieuDoChart;
    private JLabel lblTableTitle, lblChartTitleText;
    private JTextField txtChonNgayTable, txtChonNgayChart;
    private JLabel lblTongDoanhThuValue;
    private JLabel lblSoSanh;

    // ADDED: Class members for sidebar buttons and panel
    private JToggleButton btnTheoNgay, btnTheoThang, btnTheoNam;
    private JPanel sidebarPanel; // To update toggle button styles

    // Dữ liệu mẫu cho biểu đồ
    private int[] dailyChartData = {12000, 12500, 12200, 12800, 14500}; // This might be for a range
    private String[] dailyChartLabels = {"01/03", "02/03", "03/03", "04/03", "05/03"};
    private int[] monthlyChartData = {150000, 180000, 165000, 200000, 190000, 210000};
    private String[] monthlyChartLabels = {"Thg 1", "Thg 2", "Thg 3", "Thg 4", "Thg 5", "Thg 6"};
    private int[] yearlyChartData = {2000000, 2200000, 2100000};
    private String[] yearlyChartLabels = {"2023", "2024", "2025"};
    private int[] currentChartData = {};
    private String[] currentChartLabels = {};

    private static final String ICON_PATH_PREFIX = "img/";
    private final DecimalFormat currencyFormatter = new DecimalFormat("#,### VND");
    private final SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public ThongKeDoanhThuGUI() {
        setTitle("Thống kê doanh thu - LUX HOTEL");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1366, 768));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(0, 0));
        getContentPane().setBackground(COLOR_BACKGROUND);

        add(createHeaderPanel(), BorderLayout.NORTH);
        // MODIFIED: Assign sidebarPanel
        sidebarPanel = createSidebarPanel();
        add(sidebarPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        mainContentPanel.setBackground(COLOR_BACKGROUND);
        mainContentPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

        tableViewPanel = createTableViewPanel();
        chartViewPanel = createChartViewPanel();

        mainContentPanel.add(tableViewPanel, "TABLE_VIEW");
        mainContentPanel.add(chartViewPanel, "CHART_VIEW");

        add(mainContentPanel, BorderLayout.CENTER);
        cardLayout.show(mainContentPanel, "TABLE_VIEW");
        // MODIFIED: Pass null for specificDate initially
        updateTitlesAndData("NGAY", null);
    }

    private ImageIcon loadIcon(String fileName, int width, int height) {
        URL imgURL = getClass().getClassLoader().getResource(ICON_PATH_PREFIX + fileName);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } else {
            try {
                 ImageIcon originalIcon = new ImageIcon(ICON_PATH_PREFIX + fileName);
                 if (originalIcon.getImageLoadStatus() == MediaTracker.ERRORED || originalIcon.getIconWidth() == -1) {
                    System.err.println("Không tìm thấy icon (filesystem): " + ICON_PATH_PREFIX + fileName);
                    return null;
                 }
                 Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                 return new ImageIcon(scaledImage);
            } catch (Exception e) {
                System.err.println("Lỗi khi tải icon (filesystem): " + ICON_PATH_PREFIX + fileName);
                return null;
            }
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, COLOR_BORDER),
                new EmptyBorder(10, 25, 10, 25)
        ));
        GridBagConstraints gbc = new GridBagConstraints();

		JLabel ilogo = new JLabel(loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/logoLux.png", 60, 60));
		JPanel logoPanel = new JPanel();
		logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
		logoPanel.setBackground(Color.WHITE);
		logoPanel.add(ilogo);
        gbc.gridx = 0; 
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        headerPanel.add(ilogo, gbc);


        gbc.gridx = 1; gbc.weightx = 0.2;
        headerPanel.add(Box.createHorizontalGlue(), gbc);

        JPanel searchContainer = new JPanel(new BorderLayout(5,0));
        searchContainer.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        searchContainer.setBorder(new RoundedBorder(25, COLOR_BORDER, true));
        searchContainer.setPreferredSize(new Dimension(450, 38));

        JLabel searchIconLabel = new JLabel(loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/search.png", 18, 18));
        searchIconLabel.setBorder(new EmptyBorder(0,10,0,0));

        JTextField txtSearch = new JTextField("Tìm kiếm hóa đơn");
        txtSearch.setForeground(COLOR_LIGHT_TEXT);
        txtSearch.setFont(FONT_LABEL);
        txtSearch.setBorder(new EmptyBorder(0, 5, 0, 5));
        txtSearch.setOpaque(false);
        txtSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Tìm kiếm hóa đơn")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(COLOR_DARK_TEXT);
                }
            }
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setText("Tìm kiếm hóa đơn");
                    txtSearch.setForeground(COLOR_LIGHT_TEXT);
                }
            }
        });
        searchContainer.add(searchIconLabel != null ? searchIconLabel : new JLabel("🔍"), BorderLayout.WEST);
        searchContainer.add(txtSearch, BorderLayout.CENTER);
        JButton btnSearchAction = createIconButtonWithImage("HinhAnhGiaoDienChinh/AnhTraPhong/tim.png", 18, 18);
        if (btnSearchAction.getIcon() == null) btnSearchAction.setText("➢");
        btnSearchAction.setForeground(COLOR_MEDIUM_TEXT);
        btnSearchAction.setBorder(new EmptyBorder(0,0,0,10));
        searchContainer.add(btnSearchAction, BorderLayout.EAST);

        gbc.gridx = 2; gbc.weightx = 0.6; gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;
        headerPanel.add(searchContainer, gbc);

        gbc.gridx = 3; gbc.weightx = 0.2; gbc.fill = GridBagConstraints.NONE;
        headerPanel.add(Box.createHorizontalGlue(), gbc);

        JPanel rightControls = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        rightControls.setOpaque(false);
        JButton btnHelp = createIconButtonWithImage("HinhAnhGiaoDienChinh/AnhTraPhong/help.png", 22, 22);
        if (btnHelp.getIcon() == null) btnHelp.setText("❓");
        rightControls.add(btnHelp);
        JPanel userPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5,0));
        userPanel.setOpaque(false);
        JLabel lblUserIcon = new JLabel(loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/user.png", 28, 28));
        JLabel lblUserName = new JLabel("Kế toán");
        lblUserName.setFont(FONT_LABEL);
        lblUserName.setForeground(COLOR_DARK_TEXT);
        userPanel.add(lblUserIcon != null ? lblUserIcon : new JLabel("👤"));
        userPanel.add(lblUserName);
        rightControls.add(userPanel);
        JButton btnClose = createIconButtonWithImage("HinhAnhGiaoDienChinh/exit.png", 20, 20);
        if (btnClose.getIcon() == null) btnClose.setText("✕");
        btnClose.setForeground(Color.GRAY);
        rightControls.add(btnClose);
        gbc.gridx = 4; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        headerPanel.add(rightControls, gbc);

        return headerPanel;
    }

    private JPanel createSidebarPanel() {
        // MODIFIED: Use class member sidebarPanel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, COLOR_BORDER),
                new EmptyBorder(25, 20, 25, 20)
        ));
        sidebarPanel.setPreferredSize(new Dimension(280, 0));

        JLabel lblThongKeTitle = new JLabel("Thống kê doanh thu");
        lblThongKeTitle.setFont(FONT_SIDEBAR_SECTION_TITLE);
        lblThongKeTitle.setForeground(COLOR_DARK_TEXT);
        lblThongKeTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblThongKeTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        sidebarPanel.add(lblThongKeTitle);

        ButtonGroup statsGroup = new ButtonGroup();
        // MODIFIED: Assign to class members
        btnTheoNgay = createSidebarToggleButton("Thống kê theo ngày", "HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png", true);
        btnTheoThang = createSidebarToggleButton("Thống kê theo tháng", "HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png", false);
        btnTheoNam = createSidebarToggleButton("Thống kê theo năm", "HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png", false);

        // MODIFIED: Pass null for specificDate
        btnTheoNgay.addActionListener(e -> updateTitlesAndData("NGAY", null));
        btnTheoThang.addActionListener(e -> updateTitlesAndData("THANG", null));
        btnTheoNam.addActionListener(e -> updateTitlesAndData("NAM", null));

        statsGroup.add(btnTheoNgay);
        statsGroup.add(btnTheoThang);
        statsGroup.add(btnTheoNam);
        sidebarPanel.add(btnTheoNgay);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0,5)));
        sidebarPanel.add(btnTheoThang);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0,5)));
        sidebarPanel.add(btnTheoNam);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton btnBaoCao = createSidebarButton("Báo cáo doanh thu", "HinhAnhGiaoDienChinh/AnhTraPhong/report_icon.png", false);
        sidebarPanel.add(btnBaoCao);
        sidebarPanel.add(Box.createVerticalGlue());

        JPanel timePanel = new JPanel(new GridLayout(2, 1, 0, 0));
        timePanel.setOpaque(false);
        timePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timePanel.setBorder(new EmptyBorder(0,5,0,0));

        JPanel dateLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,0));
        dateLine.setOpaque(false);
        JLabel lblDateIcon = new JLabel(loadIcon("HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png", 16,16));
        JLabel lblDateText = new JLabel("dd/MM/yyyy");
        lblDateText.setFont(FONT_LABEL);
        lblDateText.setForeground(COLOR_MEDIUM_TEXT);
        if(lblDateIcon.getIcon() != null) dateLine.add(lblDateIcon); else dateLine.add(new JLabel("📅"));
        dateLine.add(lblDateText);
        timePanel.add(dateLine);

        JPanel timeLine = new JPanel(new FlowLayout(FlowLayout.LEFT, 5,0));
        timeLine.setOpaque(false);
        JLabel lblTimeIcon = new JLabel(loadIcon("HinhAnhGiaoDienChinh/o'clock.png", 16,16));
        JLabel lblTimeText = new JLabel("HH:mm:ss");
        lblTimeText.setFont(FONT_LABEL);
        lblTimeText.setForeground(COLOR_MEDIUM_TEXT);
        if(lblTimeIcon.getIcon() != null) timeLine.add(lblTimeIcon); else timeLine.add(new JLabel("🕒"));
        timeLine.add(lblTimeText);
        timePanel.add(timeLine);

        Timer dateTimeTimer = new Timer(1000, new ActionListener() {
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            @Override
            public void actionPerformed(ActionEvent e) {
                lblDateText.setText(sdfDate.format(new Date()));
                lblTimeText.setText(sdfTime.format(new Date()));
            }
        });
        dateTimeTimer.start();

        sidebarPanel.add(timePanel);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        JButton btnDangXuat = new JButton("Đăng xuất");
        btnDangXuat.setFont(FONT_SIDEBAR_ITEM);
        btnDangXuat.setForeground(COLOR_MEDIUM_TEXT);
        btnDangXuat.setIcon(loadIcon("HinhAnhGiaoDienChinh/exit.png", 16,16));
        btnDangXuat.setHorizontalTextPosition(SwingConstants.RIGHT);
        btnDangXuat.setIconTextGap(10);
        btnDangXuat.setBorder(new EmptyBorder(10, 5, 10, 5));
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.setContentAreaFilled(false);
        btnDangXuat.setOpaque(true);
        btnDangXuat.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        btnDangXuat.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnDangXuat.setAlignmentX(Component.LEFT_ALIGNMENT);
        sidebarPanel.add(btnDangXuat);

        return sidebarPanel;
    }

    // ADDED: Method to update styles of all sidebar toggle buttons
    private void updateSidebarToggleButtonsStyle(Container parent) {
        if (parent != null) {
            for (Component comp : parent.getComponents()) {
                if (comp instanceof JToggleButton) {
                    JToggleButton tb = (JToggleButton) comp;
                    if (tb.isSelected()) {
                        tb.setBackground(COLOR_PRIMARY_BUTTON_BG);
                        tb.setForeground(COLOR_PRIMARY_BUTTON_FG);
                        tb.setOpaque(true);
                    } else {
                        tb.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
                        tb.setForeground(COLOR_DARK_TEXT);
                        tb.setOpaque(false);
                        tb.setContentAreaFilled(false);
                    }
                }
            }
        }
    }


    private JToggleButton createSidebarToggleButton(String text, String iconFileName, boolean isSelected) {
        JToggleButton button = new JToggleButton(text);
        ImageIcon icon = loadIcon(iconFileName, 18, 18);
        if (icon != null) button.setIcon(icon);
        else button.setText("•  " + text);

        button.setFont(FONT_SIDEBAR_ITEM);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(12);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(100, 42));
        button.setMargin(new Insets(0,0,0,0));
        button.setBorder(new EmptyBorder(0, 15, 0, 15));

        if (isSelected) {
            button.setSelected(true);
            button.setBackground(COLOR_PRIMARY_BUTTON_BG);
            button.setForeground(COLOR_PRIMARY_BUTTON_FG);
            button.setOpaque(true);
        } else {
            button.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
            button.setForeground(COLOR_DARK_TEXT);
            button.setContentAreaFilled(false);
            button.setOpaque(false);
        }

        button.addActionListener(e -> {
            // MODIFIED: Use the new method for styling
            updateSidebarToggleButtonsStyle(((JToggleButton)e.getSource()).getParent());
        });
        return button;
    }

     private JButton createSidebarButton(String text, String iconFileName, boolean isActive) {
        JButton button = new JButton(text);
        ImageIcon icon = loadIcon(iconFileName, 18, 18);
        if (icon != null) button.setIcon(icon);
        else button.setText("•  " + text);

        button.setFont(FONT_SIDEBAR_ITEM);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setIconTextGap(12);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        button.setPreferredSize(new Dimension(100, 42));
        button.setMargin(new Insets(0,0,0,0));
        button.setBorder(new EmptyBorder(0, 15, 0, 15));
        button.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        button.setForeground(COLOR_DARK_TEXT);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    private JPanel createTopControlsForContentArea(String defaultDate, String viewType) {
        JPanel topControlsPanel = new JPanel(new BorderLayout(20,0));
        topControlsPanel.setOpaque(false);
        topControlsPanel.setBorder(new EmptyBorder(0,0,15,0));

        JPanel dateChooserPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8,0));
        dateChooserPanel.setOpaque(false);
        JLabel lblChonNgay = new JLabel("Chọn ngày");
        lblChonNgay.setFont(FONT_LABEL);
        lblChonNgay.setForeground(COLOR_MEDIUM_TEXT);

        JTextField currentTxtChonNgay;
        if (viewType.equals("TABLE")) {
            if (txtChonNgayTable == null) {
                txtChonNgayTable = new JTextField(defaultDate, 10);
                txtChonNgayTable.setFont(FONT_LABEL);
                txtChonNgayTable.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(COLOR_BORDER), new EmptyBorder(6,8,6,8)));
            }
            currentTxtChonNgay = txtChonNgayTable;
        } else { // CHART
            if (txtChonNgayChart == null) {
                txtChonNgayChart = new JTextField(defaultDate, 10);
                txtChonNgayChart.setFont(FONT_LABEL);
                txtChonNgayChart.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(COLOR_BORDER), new EmptyBorder(6,8,6,8)));
            }
            currentTxtChonNgay = txtChonNgayChart;
        }
        currentTxtChonNgay.setText(defaultDate);


        JButton btnCalendar = createIconButtonWithImage("HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png", 20, 20);
        if(btnCalendar.getIcon() == null) btnCalendar.setText("📅");
        dateChooserPanel.add(lblChonNgay);
        dateChooserPanel.add(currentTxtChonNgay);
        dateChooserPanel.add(btnCalendar);
        topControlsPanel.add(dateChooserPanel, BorderLayout.WEST);

        // MODIFIED: Add ActionListener for date search
        ActionListener dateSearchListener = event -> {
            String dateText = currentTxtChonNgay.getText();
            Date parsedDate = null;
            try {
                parsedDate = displayDateFormat.parse(dateText);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(ThongKeDoanhThuGUI.this,
                        "Định dạng ngày không hợp lệ. Vui lòng sử dụng dd/MM/yyyy (ví dụ: 23/05/2025).",
                        "Lỗi định dạng ngày", JOptionPane.ERROR_MESSAGE);
                // Optionally, reset to current date if invalid
                // currentTxtChonNgay.setText(displayDateFormat.format(new Date()));
                // try { parsedDate = displayDateFormat.parse(currentTxtChonNgay.getText()); } catch (ParseException ignored) {}
                return; // Stop if date is invalid
            }

            if (btnTheoNgay != null && !btnTheoNgay.isSelected()) {
                btnTheoNgay.setSelected(true);
                updateSidebarToggleButtonsStyle(sidebarPanel); // Update styles after programmatic selection
            }
            updateTitlesAndData("NGAY", parsedDate);
        };

        currentTxtChonNgay.addActionListener(dateSearchListener);
        btnCalendar.addActionListener(dateSearchListener);


        JPanel titleAndTogglePanel = new JPanel(new BorderLayout(15,0));
        titleAndTogglePanel.setOpaque(false);

        JLabel lblContentTitle = new JLabel("Doanh thu tiền phòng");
        lblContentTitle.setFont(FONT_CONTENT_AREA_TITLE);
        lblContentTitle.setForeground(COLOR_DARK_TEXT);
        lblContentTitle.setOpaque(true);
        lblContentTitle.setBackground(COLOR_CONTENT_AREA_TITLE_BG);
        lblContentTitle.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, COLOR_CONTENT_AREA_TITLE_BG, false),
            new EmptyBorder(8,15,8,15)
        ));
        titleAndTogglePanel.add(lblContentTitle, BorderLayout.WEST);

        JPanel viewTogglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        viewTogglePanel.setOpaque(false);
        viewTogglePanel.setBorder(new RoundedBorder(8, COLOR_BORDER, true));

        if(btnSoLieuTable == null) {
            btnSoLieuTable = new JButton("Số liệu");
            btnBieuDoTable = new JButton("Biểu đồ");
            btnSoLieuChart = new JButton("Số liệu");
            btnBieuDoChart = new JButton("Biểu đồ");

            btnSoLieuTable.addActionListener(e -> {
                setActiveToggleButtonContent(btnSoLieuTable, btnBieuDoTable);
                cardLayout.show(mainContentPanel, "TABLE_VIEW");
            });
            btnBieuDoTable.addActionListener(e -> {
                setActiveToggleButtonContent(btnBieuDoTable, btnSoLieuTable);
                cardLayout.show(mainContentPanel, "CHART_VIEW");
            });
            btnSoLieuChart.addActionListener(e -> {
                setActiveToggleButtonContent(btnSoLieuChart, btnBieuDoChart);
                cardLayout.show(mainContentPanel, "TABLE_VIEW");
            });
            btnBieuDoChart.addActionListener(e -> {
                setActiveToggleButtonContent(btnBieuDoChart, btnSoLieuChart);
                cardLayout.show(mainContentPanel, "CHART_VIEW");
            });
        }

        viewTogglePanel.removeAll();
        if (viewType.equals("TABLE")) {
            configureToggleButton(btnSoLieuTable, true);
            configureToggleButton(btnBieuDoTable, false);
            viewTogglePanel.add(btnSoLieuTable);
            viewTogglePanel.add(btnBieuDoTable);
        } else { // CHART
            configureToggleButton(btnSoLieuChart, false);
            configureToggleButton(btnBieuDoChart, true);
            viewTogglePanel.add(btnSoLieuChart);
            viewTogglePanel.add(btnBieuDoChart);
        }

        titleAndTogglePanel.add(viewTogglePanel, BorderLayout.EAST);
        topControlsPanel.add(titleAndTogglePanel, BorderLayout.CENTER);
        return topControlsPanel;
    }

    private void configureToggleButton(JButton button, boolean isActive){
        button.setFont(FONT_BUTTON_GROUP);
        button.setBorder(new EmptyBorder(8,18,8,18));
        button.setFocusPainted(false);
        button.setOpaque(true);
        if(isActive){
            button.setBackground(COLOR_PRIMARY_BUTTON_BG);
            button.setForeground(COLOR_PRIMARY_BUTTON_FG);
        } else {
            button.setBackground(COLOR_DEFAULT_BUTTON_BG);
            button.setForeground(COLOR_DEFAULT_BUTTON_FG);
        }
    }

     private void setActiveToggleButtonContent(JButton activeButton, JButton inactiveButton) {
        activeButton.setBackground(COLOR_PRIMARY_BUTTON_BG);
        activeButton.setForeground(COLOR_PRIMARY_BUTTON_FG);
        inactiveButton.setBackground(COLOR_DEFAULT_BUTTON_BG);
        inactiveButton.setForeground(COLOR_DEFAULT_BUTTON_FG);
    }

    // MODIFIED: Signature changed to accept specificDate
    private void updateTitlesAndData(String filterType, Date specificDate) {
        String datePrefix = "";
        String forDate = ""; // Ngày/tháng/năm cụ thể cho tiêu đề
        Calendar cal = Calendar.getInstance();
        // SimpleDateFormat sdfDay = new SimpleDateFormat("dd/MM/yyyy"); // displayDateFormat can be used
        // SimpleDateFormat sdfMonth = new SimpleDateFormat("MM/yyyy");
        // SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
        String newChartTitle = "Biểu đồ doanh thu ";
        // String newTableTitle = "Bảng thống kê doanh thu tiền phòng "; // Will be set more specifically
        List<Vector<Object>> tableDataFromDB = new ArrayList<>();
        long totalRevenueFromDB = 0;
        long comparisonRevenueFromDB = 0;

        switch (filterType) {
            case "NGAY":
                datePrefix = "ngày ";
                Date dateToUse;

                if (specificDate != null) {
                    dateToUse = specificDate;
                } else {
                    // If specificDate is null (e.g. btnTheoNgay clicked or initial load)
                    // Try to parse date from the relevant text field
                    String dateFromFieldText = "";
                    // Determine which text field is currently relevant based on active view or a default
                    // For simplicity, let's assume txtChonNgayTable is the primary one if available
                    // or if we know we are in table view.
                    // A better approach might be to know which view is active when btnTheoNgay is clicked.
                    // However, txtChonNgayTable and txtChonNgayChart should ideally be synchronized.
                    if (txtChonNgayTable != null && txtChonNgayTable.isEditable()) { // Check if editable (usually is for NGAY)
                         dateFromFieldText = txtChonNgayTable.getText();
                    } else if (txtChonNgayChart != null && txtChonNgayChart.isEditable()){
                         dateFromFieldText = txtChonNgayChart.getText();
                    } else {
                        dateFromFieldText = displayDateFormat.format(cal.getTime()); // Fallback if fields not ready
                    }

                    try {
                        dateToUse = displayDateFormat.parse(dateFromFieldText);
                    } catch (ParseException e) {
                        dateToUse = cal.getTime(); // Fallback to current date if parsing fails
                        System.err.println("Lỗi parse ngày từ trường text, dùng ngày hiện tại: " + e.getMessage());
                    }
                }
                forDate = displayDateFormat.format(dateToUse);
                tableDataFromDB = fetchDailyRevenueData(dateToUse);

                // MODIFIED: Chart data for single day
                long revenueForDay = calculateTotalRevenue(tableDataFromDB, 5); // Col 5 is TongTien
                this.currentChartData = new int[]{(int) revenueForDay};
                this.currentChartLabels = new String[]{forDate};
                newChartTitle = "Biểu đồ doanh thu " + datePrefix + forDate;

                totalRevenueFromDB = revenueForDay;
                comparisonRevenueFromDB = fetchPreviousDayRevenue(dateToUse);

                if(txtChonNgayTable != null) {
                    txtChonNgayTable.setText(forDate);
                    txtChonNgayTable.setEditable(true);
                }
                if(txtChonNgayChart != null) {
                    txtChonNgayChart.setText(forDate);
                    txtChonNgayChart.setEditable(true);
                }
                break;
            case "THANG":
                datePrefix = "tháng ";
                // For month/year, specificDate is not used in this logic path, could be enhanced
                int selectedMonth = cal.get(Calendar.MONTH) + 1;
                int selectedYearForMonth = cal.get(Calendar.YEAR);
                forDate = String.format("%02d/%d", selectedMonth, selectedYearForMonth);
                tableDataFromDB = fetchMonthlyRevenueData(selectedMonth, selectedYearForMonth);
                this.currentChartData = this.monthlyChartData;
                this.currentChartLabels = this.monthlyChartLabels;
                newChartTitle = "Biểu đồ doanh thu " + datePrefix + forDate;
                totalRevenueFromDB = calculateTotalRevenue(tableDataFromDB, 5);
                comparisonRevenueFromDB = fetchPreviousMonthRevenue(selectedMonth, selectedYearForMonth);
                if(txtChonNgayTable != null) {
                     txtChonNgayTable.setText(forDate);
                     txtChonNgayTable.setEditable(false);
                }
                if(txtChonNgayChart != null) {
                    txtChonNgayChart.setText(forDate);
                    txtChonNgayChart.setEditable(false);
                }
                break;
            case "NAM":
                datePrefix = "năm ";
                // For year, specificDate is not used
                int selectedYear = cal.get(Calendar.YEAR);
                forDate = String.valueOf(selectedYear);
                tableDataFromDB = fetchYearlyRevenueData(selectedYear);
                this.currentChartData = this.yearlyChartData;
                this.currentChartLabels = this.yearlyChartLabels;
                newChartTitle = "Biểu đồ doanh thu " + datePrefix + forDate;
                totalRevenueFromDB = calculateTotalRevenue(tableDataFromDB, 5);
                comparisonRevenueFromDB = fetchPreviousYearRevenue(selectedYear);
                if(txtChonNgayTable != null) {
                    txtChonNgayTable.setText(forDate);
                    txtChonNgayTable.setEditable(false);
                 }
                if(txtChonNgayChart != null) {
                    txtChonNgayChart.setText(forDate);
                    txtChonNgayChart.setEditable(false);
                }
                break;
        }

        updateTableData(tableDataFromDB);
        updateChartData(this.currentChartData, this.currentChartLabels, newChartTitle); // Pass updated title
        if (lblTableTitle != null) lblTableTitle.setText("Bảng thống kê doanh thu tiền phòng " + datePrefix + forDate);

        if (lblTongDoanhThuValue != null) {
            lblTongDoanhThuValue.setText(currencyFormatter.format(totalRevenueFromDB));
        }
        if (lblSoSanh != null) {
            long difference = totalRevenueFromDB - comparisonRevenueFromDB;
            String comparisonText;
            Color comparisonColor;
            if (difference == 0 && totalRevenueFromDB == 0 && comparisonRevenueFromDB == 0 && filterType.equals("NGAY")) {
                 comparisonText = "Không có dữ liệu cho kỳ trước";
                 comparisonColor = COLOR_MEDIUM_TEXT;
            } else if (difference >= 0) {
                comparisonText = "Tăng " + currencyFormatter.format(difference) + " so với kỳ trước";
                comparisonColor = COLOR_PRIMARY_BUTTON_BG;
            } else {
                comparisonText = "Giảm " + currencyFormatter.format(Math.abs(difference)) + " so với kỳ trước";
                comparisonColor = Color.RED;
            }
            lblSoSanh.setText(comparisonText);
            lblSoSanh.setForeground(comparisonColor);
        }
    }

	private List<Vector<Object>> fetchDailyRevenueData(Date date) {
	    System.out.println("Đang sử dụng dữ liệu mẫu cho NGÀY: " + sqlDateFormat.format(date));
	    return generateFortySampleVectorRows(date); // Luôn dùng dữ liệu mẫu
	}

    // MODIFIED: generateFortySampleVectorRows to accept a date to make sample data more relevant
    private List<Vector<Object>> generateFortySampleVectorRows(Date forDate) {
        List<Vector<Object>> sampleRows = new ArrayList<>();
        Random random = new Random();
        String[] ho = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Huỳnh", "Võ", "Đặng", "Bùi", "Đỗ", "Phan", "Mai"};
        String[] tenDem = {"Văn", "Thị", "Minh", "Ngọc", "Đức", "Hữu", "Thanh", "Xuân", "Bảo", "Gia", "Kim", "Quốc"};
        String[] ten = {"An", "Bình", "Cường", "Dũng", "Em", "Giang", "Hải", "Khánh", "Linh", "My", "Nam", "Oanh", "Phúc", "Quân", "Sơn", "Tâm", "Uyên", "Vinh", "Xuân", "Yến", "Anh", "Hùng"};

        // Use the provided date for NgayLap in sample data
        Calendar cal = Calendar.getInstance();
        cal.setTime(forDate);
        LocalDate ngayLapLocalDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));

        for (int i = 1; i <= 5 + random.nextInt(10) ; i++) { // Generate fewer, more relevant samples
            String maDonDatPhong = String.format("HD%02d%s%04d",
                    cal.get(Calendar.YEAR) % 100, // Use year from forDate
                    String.format("%02d", cal.get(Calendar.MONTH) + 1), // Month from forDate
                    i * (random.nextInt(5) + 1) + random.nextInt(1000)
            );
            String tenKhachHang = ho[random.nextInt(ho.length)] + " " +
                                 tenDem[random.nextInt(tenDem.length)] + " " +
                                 ten[random.nextInt(ten.length)];

            String ngayLapFormatted = ngayLapLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            double tienPhong = (random.nextInt(200) + 50) * 10000.0;
            double tienDichVu = (random.nextInt(100)) * 5000.0;
            double tongTien = tienPhong + tienDichVu;

            Vector<Object> rowVector = new Vector<>();
            rowVector.add(maDonDatPhong);
            rowVector.add(tenKhachHang);
            rowVector.add(ngayLapFormatted); // Formatted date
            rowVector.add(tienPhong);  // Raw number
            rowVector.add(tienDichVu); // Raw number
            rowVector.add(tongTien);   // Raw number
            sampleRows.add(rowVector);
        }
        return sampleRows;
    }


    private List<Vector<Object>> fetchMonthlyRevenueData(int month, int year) {
        System.out.println("Đang tải dữ liệu bảng cho THÁNG: " + month + "/" + year);
        List<Vector<Object>> data = new ArrayList<>();
        // Placeholder: Actual DB logic needed
        Object[][] sample = {
            {"TH" + String.format("%02d", month) + "/" + year + "-001", "Tổng kết " + month + "/" + year, String.format("%02d/%d", month, year), 25000000.0, 5000000.0, 30000000.0},
        };
        for (Object[] rowData : sample) {
            Vector<Object> row = new Vector<>();
            for(Object item : rowData) row.add(item);
            data.add(row);
        }
        return data;
    }

    private List<Vector<Object>> fetchYearlyRevenueData(int year) {
        System.out.println("Đang tải dữ liệu bảng cho NĂM: " + year);
        List<Vector<Object>> data = new ArrayList<>();
        // Placeholder: Actual DB logic needed
        Object[][] sample = {
            {"NAM" + year + "-001", "Tổng kết năm " + year, String.valueOf(year), 300000000.0, 50000000.0, 350000000.0},
        };
        for (Object[] rowData : sample) {
            Vector<Object> row = new Vector<>();
            for(Object item : rowData) row.add(item);
            data.add(row);
        }
        return data;
    }

    // MODIFIED: calculateTotalRevenue to handle Numbers and formatted Strings
    private long calculateTotalRevenue(List<Vector<Object>> data, int totalColumnIndex) {
        long total = 0;
        if (data == null) return 0;
        for (Vector<Object> row : data) {
            if (row.size() > totalColumnIndex) {
                Object amountObj = row.get(totalColumnIndex);
                if (amountObj == null) continue;
                try {
                    if (amountObj instanceof Number) {
                        total += ((Number) amountObj).longValue();
                    } else {
                        String formattedAmount = amountObj.toString()
                            .replace(" VND", "")
                            .replace(",", "");
                        total += Long.parseLong(formattedAmount);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi parse số tiền trong calculateTotalRevenue: '" + amountObj + "' - " + e.getMessage());
                }
            }
        }
        return total;
    }

    // MODIFIED: fetchPreviousDayRevenue
    private long fetchPreviousDayRevenue(Date currentDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date previousDate = cal.getTime();
        System.out.println("Fetching comparison (previous day) revenue for: " + sqlDateFormat.format(previousDate));
        // Fetch actual data for previousDate
        List<Vector<Object>> prevDayData = fetchDailyRevenueData(previousDate); // This might call generateFortySampleVectorRows if no DB data
        if (prevDayData.isEmpty() && !sqlDateFormat.format(previousDate).equals(sqlDateFormat.format(currentDate))) { // Avoid infinite loop with sample data for same day
        }
        return calculateTotalRevenue(prevDayData, 5); // Calculate from actual/sample data for prev day
        // return 10000000; // Old placeholder
    }
    private long fetchPreviousMonthRevenue(int currentMonth, int currentYear) {
        // Placeholder: Actual DB logic needed
        return 25000000;
    }
    private long fetchPreviousYearRevenue(int currentYear) {
        // Placeholder: Actual DB logic needed
        return 280000000L;
    }


    private JPanel createTableViewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setOpaque(false);
        JPanel topControls = createTopControlsForContentArea(displayDateFormat.format(new Date()), "TABLE");
        panel.add(topControls, BorderLayout.NORTH);
        JPanel tableContainer = new JPanel(new BorderLayout());
        tableContainer.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        tableContainer.setBorder(new RoundedBorder(10, COLOR_BORDER, true));
        lblTableTitle = new JLabel("Bảng thống kê doanh thu tiền phòng");
        lblTableTitle.setFont(FONT_CHART_TITLE_TEXT);
        lblTableTitle.setForeground(COLOR_DARK_TEXT);
        lblTableTitle.setBorder(new EmptyBorder(15,20,15,20));
        tableContainer.add(lblTableTitle, BorderLayout.NORTH);
        String[] columnNames = {"Mã đơn đặt phòng", "Tên khách hàng", "Ngày lập", "Tiền phòng", "Tiền dịch vụ", "Tổng tiền"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        revenueTable = new JTable(tableModel);
        setupTableStyle(); // Style setup includes renderers
        JScrollPane scrollPane = new JScrollPane(revenueTable);
        scrollPane.setBorder(new EmptyBorder(0,1,1,1));
        scrollPane.getViewport().setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        tableContainer.add(scrollPane, BorderLayout.CENTER);
        panel.add(tableContainer, BorderLayout.CENTER);
        return panel;
    }

     private JPanel createChartViewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10,10));
        panel.setOpaque(false);
        JPanel topControls = createTopControlsForContentArea(displayDateFormat.format(new Date()), "CHART");
        panel.add(topControls, BorderLayout.NORTH);
        JPanel chartAndSummaryPanel = new JPanel(new BorderLayout(25,0));
        chartAndSummaryPanel.setOpaque(false);
        chartAndSummaryPanel.setBorder(new EmptyBorder(10,0,0,0));
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        summaryPanel.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(10, COLOR_BORDER, true),
                new EmptyBorder(20,25,20,25)
        ));
        summaryPanel.setPreferredSize(new Dimension(300, 0));
        JLabel lblTongDoanhThuTitle = new JLabel("Tổng doanh thu");
        lblTongDoanhThuTitle.setFont(FONT_SUMMARY_TOTAL_TITLE);
        lblTongDoanhThuTitle.setForeground(COLOR_MEDIUM_TEXT);
        lblTongDoanhThuTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        summaryPanel.add(lblTongDoanhThuTitle);
        lblTongDoanhThuValue = new JLabel("0 VND");
        lblTongDoanhThuValue.setFont(FONT_SUMMARY_TOTAL_VALUE);
        lblTongDoanhThuValue.setForeground(COLOR_DARK_TEXT);
        lblTongDoanhThuValue.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblTongDoanhThuValue.setBorder(new EmptyBorder(2,0,0,0));
        summaryPanel.add(lblTongDoanhThuValue);
        summaryPanel.add(Box.createRigidArea(new Dimension(0,12)));
        JPanel comparisonPanel = new JPanel();
        comparisonPanel.setOpaque(false);
        comparisonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        comparisonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblSoSanh = new JLabel("So với kỳ trước...");
        lblSoSanh.setFont(FONT_SUMMARY_SUB);
        lblSoSanh.setForeground(Color.RED); // Default, will be updated
        comparisonPanel.add(lblSoSanh);
        summaryPanel.add(comparisonPanel);
        chartAndSummaryPanel.add(summaryPanel, BorderLayout.WEST);
        JPanel chartContainer = new JPanel(new BorderLayout());
        chartContainer.setBackground(COLOR_HEADER_FOOTER_SIDEBAR_BG);
        chartContainer.setBorder(new RoundedBorder(10, COLOR_BORDER, true));
        lblChartTitleText = new JLabel("Biểu đồ doanh thu theo ngày"); // Default, will be updated
        lblChartTitleText.setFont(FONT_CHART_TITLE_TEXT);
        lblChartTitleText.setForeground(COLOR_DARK_TEXT);
        lblChartTitleText.setHorizontalAlignment(SwingConstants.CENTER);
        lblChartTitleText.setBorder(new EmptyBorder(15,15,15,15));
        chartContainer.add(lblChartTitleText, BorderLayout.NORTH);
        BarChartPanel barChart = new BarChartPanel(currentChartData, currentChartLabels);
        chartContainer.add(barChart, BorderLayout.CENTER);
        chartAndSummaryPanel.add(chartContainer, BorderLayout.CENTER);
        panel.add(chartAndSummaryPanel, BorderLayout.CENTER);
        return panel;
    }

    // Custom Cell Renderer for currency formatting
    private class CurrencyRenderer extends DefaultTableCellRenderer {
        public CurrencyRenderer() {
            super();
            setHorizontalAlignment(JLabel.RIGHT);
            setBorder(new EmptyBorder(0,10,0,15));
        }
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            if (value instanceof Number) {
                value = currencyFormatter.format(((Number) value).doubleValue());
            }
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }


    private void setupTableStyle() {
        revenueTable.setRowHeight(35);
        revenueTable.setGridColor(COLOR_BORDER);
        revenueTable.setShowVerticalLines(false);
        revenueTable.setShowHorizontalLines(true);
        revenueTable.setIntercellSpacing(new Dimension(0, 0));
        JTableHeader header = revenueTable.getTableHeader();
        header.setBackground(COLOR_TABLE_HEADER_BG);
        header.setForeground(COLOR_DARK_TEXT);
        header.setFont(FONT_TABLE_HEADER);
        header.setPreferredSize(new Dimension(0, 40));
        header.setBorder(BorderFactory.createMatteBorder(0,0,1,0, COLOR_BORDER));

        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(new EmptyBorder(0,15,0,10));

        // Use CurrencyRenderer for money columns
        CurrencyRenderer currencyRenderer = new CurrencyRenderer();

        for (int i = 0; i < revenueTable.getColumnCount(); i++) {
            revenueTable.getColumnModel().getColumn(i).setHeaderRenderer(new CustomHeaderRenderer());
            if (i >= 3 && i <= 5) { // Columns: TienPhong, TienDichVu, TongTien
                revenueTable.getColumnModel().getColumn(i).setCellRenderer(currencyRenderer);
            } else {
                revenueTable.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
            }
        }
        revenueTable.setFont(FONT_TABLE_CELL);
        revenueTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    static class CustomHeaderRenderer extends DefaultTableCellRenderer {
        public CustomHeaderRenderer() {
            setHorizontalAlignment(JLabel.LEFT);
            setOpaque(true);
            setBackground(COLOR_TABLE_HEADER_BG);
            setForeground(COLOR_DARK_TEXT);
            setFont(FONT_TABLE_HEADER);
            setBorder(new EmptyBorder(0,15,0,10));
        }
         public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            return this;
        }
    }

    // loadSampleTableData seems for a different purpose (sampledata_Dao), not directly used by revenue stats
    // It's kept for now as it was in the original code.
    private void loadSampleTableData() {
        if (this.dao == null) {
            this.dao = new sampledata_Dao();
        }
        List<sampledata> dataList = null;
        try {
            dataList = dao.layTatCaSampleData();
        } catch (Exception e) {
            System.err.println("Lỗi khi gọi DAO để lấy dữ liệu mẫu: " + e.getMessage());
            e.printStackTrace();
        }

        if (tableModel != null) {
            tableModel.setRowCount(0);
            DateTimeFormatter displayDateFormatterForTable = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            if (dataList == null || dataList.isEmpty()) {
                System.out.println("Không có dữ liệu mẫu từ DAO hoặc có lỗi xảy ra.");
            } else {
                for (sampledata sample : dataList) {
                    String maDonDatPhong = sample.getMaDonDatPhong();
                    String tenKhachHang = sample.getMaKH();
                    String ngayLap = "";
                    if (sample.getNgayDatPhong() != null) {
                        ngayLap = sample.getNgayDatPhong().format(displayDateFormatterForTable);
                    }
                    // Using raw numbers for currency columns if they are numbers in sampledata
                    Object tienPhongVal = sample.getTienCoc() != null ? sample.getTienCoc().doubleValue() : 0.0;
                    Object tienDichVuVal = 0.0; // Assuming no direct field
                    Object tongTienVal = sample.getTienCoc() != null ? sample.getTienCoc().doubleValue() : 0.0;

                    Object[] row = {
                            maDonDatPhong,
                            tenKhachHang,
                            ngayLap,
                            tienPhongVal,
                            tienDichVuVal,
                            tongTienVal
                    };
                    tableModel.addRow(row);
                }
            }
        } else {
            System.err.println("Lỗi: tableModel chưa được khởi tạo trong loadSampleTableData.");
        }
    }

    public void updateTableData(List<Vector<Object>> data) {
        if (tableModel != null) {
            tableModel.setRowCount(0);
            if (data != null) {
                for (Vector<Object> row : data) {
                    tableModel.addRow(row);
                }
            }
        }
    }

    public void updateChartData(int[] newData, String[] newLabels, String chartTitle) {
        this.currentChartData = newData;
        this.currentChartLabels = newLabels;

        if (lblChartTitleText != null) {
            lblChartTitleText.setText(chartTitle);
        }

        if(chartViewPanel != null && chartViewPanel.getComponentCount() > 1 ){
            Component chartAndSummaryComp = chartViewPanel.getComponent(1); // chartAndSummaryPanel
             if(chartAndSummaryComp instanceof JPanel){
                JPanel chartAndSummary = (JPanel) chartAndSummaryComp;
                if(chartAndSummary.getComponentCount() > 1 && chartAndSummary.getComponent(1) instanceof JPanel){
                     JPanel chartContainer = (JPanel) chartAndSummary.getComponent(1);
                     for(Component comp : chartContainer.getComponents()){
                         if(comp instanceof BarChartPanel){
                             ((BarChartPanel) comp).setData(this.currentChartData, this.currentChartLabels);
                             return;
                         }
                     }
                }
            }
        }
         // If BarChartPanel not found above (e.g. structure changed or first time)
         // This part might need a more robust way to find/replace the BarChartPanel
         // For now, assuming the structure is consistent after initialization.
    }


    private JButton createIconButtonWithImage(String iconFileName, int width, int height) {
        JButton button = new JButton();
        ImageIcon icon = loadIcon(iconFileName, width, height);
        if (icon != null) {
            button.setIcon(icon);
        }
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(5,5,5,5));
        return button;
    }

    private static class BarChartPanel extends JPanel {
        private int[] data;
        private String[] labels;
        private final int TOP_PADDING = 30; // Increased for value text
        private final int BOTTOM_PADDING = 40;
        private final int LEFT_PADDING = 60; // Increased for Y-axis labels
        private final int RIGHT_PADDING = 20;

        public BarChartPanel(int[] data, String[] labels) {
            this.data = data;
            this.labels = labels;
            setBackground(Color.WHITE);
            setBorder(new EmptyBorder(10,10,10,10));
        }
         public void setData(int[] newData, String[] newLabels) {
            this.data = newData;
            this.labels = newLabels;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (data == null || data.length == 0 || labels == null || data.length != labels.length) {
                 g.drawString("Không có dữ liệu biểu đồ hoặc dữ liệu không khớp.", 10, 20);
                return;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int panelWidth = getWidth();
            int panelHeight = getHeight();
            int chartWidth = panelWidth - LEFT_PADDING - RIGHT_PADDING;
            int chartHeight = panelHeight - TOP_PADDING - BOTTOM_PADDING;

            g2.setColor(COLOR_LIGHT_TEXT);
            g2.setFont(FONT_CHART_AXIS);
            FontMetrics metrics = g2.getFontMetrics(FONT_CHART_AXIS);

            // Draw Y Axis Line
            g2.drawLine(LEFT_PADDING, panelHeight - BOTTOM_PADDING, LEFT_PADDING, TOP_PADDING);
            // Draw X Axis Line
            g2.drawLine(LEFT_PADDING, panelHeight - BOTTOM_PADDING, panelWidth - RIGHT_PADDING, panelHeight - BOTTOM_PADDING);

            // Y Axis Label
            String yAxisLabel = "Doanh thu (VND)"; // Simplified label
            int yLabelStringWidth = metrics.stringWidth(yAxisLabel);
            g2.rotate(-Math.PI / 2);
            g2.drawString(yAxisLabel, -(TOP_PADDING + chartHeight/2 + yLabelStringWidth/2) , LEFT_PADDING/2 - metrics.getDescent() - 5);
            g2.rotate(Math.PI / 2);

            int maxValue = 0;
            for (int val : data) maxValue = Math.max(maxValue, val);
            if (maxValue == 0 && data.length > 0 && data[0] > 0) maxValue = data[0]; // If only one bar
            
            // Adjust maxValue for better scale, ensure it's not zero
            if (maxValue == 0) maxValue = 10000; // Default max if all data is 0
            else maxValue = (int) (Math.ceil(maxValue / 5000.0 / 5.0) * 5000.0 * 5.0); // Round up to nearest nice number for 5 grid lines

            int numYGridLines = 5;
            for (int i = 0; i <= numYGridLines; i++) {
                int yTick = panelHeight - BOTTOM_PADDING - i * chartHeight / numYGridLines;
                long value = (long)i * maxValue / numYGridLines; // Use long for potentially large currency values
                String tickLabel = new DecimalFormat("#,###").format(value); // Format Y-axis labels
                g2.setColor(COLOR_BORDER);
                g2.drawLine(LEFT_PADDING - 4, yTick, panelWidth - RIGHT_PADDING, yTick); // Grid line
                g2.setColor(COLOR_LIGHT_TEXT);
                g2.drawString(tickLabel, LEFT_PADDING - metrics.stringWidth(tickLabel) - 8 , yTick + metrics.getAscent() / 2);
            }

            double barGap = 15;
            if (data.length == 1) barGap = chartWidth / 3.0; // Adjust gap for single bar
            double barWidth = (double) (chartWidth - (data.length + 1) * barGap) / data.length;
            if (barWidth < 10) barWidth = 10;
            if (data.length == 1) barWidth = chartWidth - 2 * barGap;


            for (int i = 0; i < data.length; i++) {
                int barHeightValue = data[i];
                int barActualHeight = (maxValue == 0) ? 0 : (int) (((double) barHeightValue / maxValue) * chartHeight);
                int x = LEFT_PADDING + (int)(i * (barWidth + barGap) + barGap);
                int y = panelHeight - BOTTOM_PADDING - barActualHeight;

                // Use orange for the last bar or if only one bar, blue otherwise
                if (i == data.length - 1 || data.length == 1) g2.setColor(COLOR_CHART_BAR_ORANGE);
                else g2.setColor(COLOR_CHART_BAR_BLUE);
                g2.fillRect(x, y, (int)barWidth, barActualHeight);

                // Draw value on top of bar
                g2.setColor(COLOR_DARK_TEXT);
                g2.setFont(FONT_CHART_AXIS.deriveFont(Font.BOLD)); // Slightly bolder for value
                String barValueText = new DecimalFormat("#,###").format(barHeightValue);
                int valueTextWidth = g2.getFontMetrics().stringWidth(barValueText);
                if (barActualHeight > 15) { // Only draw if there's space
                     g2.drawString(barValueText, x + (int)barWidth/2 - valueTextWidth/2, y - 5);
                }


                g2.setColor(COLOR_LIGHT_TEXT);
                g2.setFont(FONT_CHART_AXIS); // Reset font for labels
                String label = labels[i];
                int labelWidth = metrics.stringWidth(label);
                g2.drawString(label, x + (int)barWidth / 2 - labelWidth / 2, panelHeight - BOTTOM_PADDING + metrics.getAscent() + 5);
            }
        }
    }

    private static class RoundedBorder implements Border {
        private int radius;
        private Color borderColor;
        private boolean paintBorderLine;

        public RoundedBorder(int radius, Color borderColor, boolean paintBorderLine) {
            this.radius = radius;
            this.borderColor = borderColor;
            this.paintBorderLine = paintBorderLine;
        }
        public Insets getBorderInsets(Component c) {
            int p = this.paintBorderLine ? Math.max(1,radius / 3) : 1;
            if (c instanceof JButton && c.getParent() instanceof JComponent) {
                 JComponent parent = (JComponent) c.getParent();
                 if (parent.getBorder() == this) {
                    return new Insets(0,0,0,0);
                 }
            }
            return new Insets(p, p, p, p);
        }
        public boolean isBorderOpaque() { return true; }
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Shape clip = g2.getClip();
            g2.clipRect(x,y,width,height);
            if (c instanceof JPanel && ((JPanel)c).getBorder() == this) {
                g2.setColor(c.getBackground());
                g2.fillRoundRect(x, y, width, height, radius, radius);
            }
            if(this.paintBorderLine) {
                g2.setColor(borderColor);
                g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
            }
            g2.setClip(clip);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ex) { ex.printStackTrace(); }
        }
        UIManager.put("RadioButton.opaque", false);
        UIManager.put("CheckBox.opaque", false);
        UIManager.put("ToggleButton.border", BorderFactory.createEmptyBorder());
        // UIManager.put("ToggleButton.select", COLOR_PRIMARY_BUTTON_BG); // Handled by custom styling

        SwingUtilities.invokeLater(() -> new ThongKeDoanhThuGUI().setVisible(true));
    }
}
