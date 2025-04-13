package GiaoDienChinh;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.GeneralPath;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.awt.Toolkit;

public class GiaoDienChinh {

    private JFrame frame;
    private JPanel hinhKhachSanpannel;
    private JLabel HinhAnhNen;
    private JButton btnPrev, btnNext;
    private Timer timerSlideShow;
    private ArrayList<String> imagePaths;
    private int currentIndex = 0;
    private final String imageFolderPath = "HinhNen";
    private JPopupMenu quanLyCaPopupMenu;
    private Color defaultMenuItemBackground;
    private Color hoverBackgroundColor = new Color(91, 249, 33); // Màu xanh lá
    private Color hoverForegroundColor = Color.black;
    private JLabel ngaythangnam;
    private JLabel giophutgiay;
    private Timer timerClock; // Timer cho đồng hồ

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GiaoDienChinh window = new GiaoDienChinh();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     */
    public GiaoDienChinh() {
        initialize();
        startClock(); // Khởi động đồng hồ khi ứng dụng chạy
    }

    /**
     * Khởi động đồng hồ thời gian thực.
     */
    private void startClock() {
        timerClock = new Timer(1000, new ActionListener() { // Cập nhật mỗi 1 giây
            SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar now = Calendar.getInstance();
                ngaythangnam.setText(sdfDate.format(now.getTime()));
                giophutgiay.setText(sdfTime.format(now.getTime()));
            }
        });
        timerClock.start();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        // Lấy kích thước màn hình thực tế
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        // Lấy kích thước màn hình thực sự (bao gồm cả taskbar)
        Rectangle screenBounds = gc.getBounds();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        // Tính kích thước thực tế trừ đi taskbar
        int screenWidth = screenBounds.width - (screenInsets.left + screenInsets.right);
        int screenHeight = screenBounds.height - (screenInsets.top + screenInsets.bottom);

        // Tính toán kích thước JFrame
        int frameWidth = (int) (screenWidth);  
        int frameHeight = (int) (screenHeight); 
        frame = new JFrame();
        frame.setBackground(new Color(255, 255, 255));
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\logo.png"));
        frame.setBounds(100, 100, frameWidth, frameHeight); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình (nếu không phải toàn màn hình)
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Đặt trạng thái phóng to toàn màn hình


        JPanel Header = new JPanel();
        Header.setBounds(0, 0, (int) frameWidth, (int) (frameHeight*0.12));
        Header.setBackground(new Color(255, 255, 255));
        frame.getContentPane().add(Header);
        Header.setLayout(null);
        Header.setBorder(new LineBorder(Color.black));

        JLabel lblLoGo = new JLabel("");
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\logo.png");
        Image image = originalIcon.getImage().getScaledInstance(88, 88, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(image);
        lblLoGo.setIcon(logoIcon);
        lblLoGo.setBounds(5, 5, 88, 88);
        Header.add(lblLoGo);

        JButton help = new JButton("help");
        help.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\help.png"));
        help.setBounds(1342, 20, 50, 50);
        help.setContentAreaFilled(false); // Tắt nền
        help.setBorderPainted(false);
        Header.add(help);

        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(1466, 29, 45, 13);
        Header.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(1466, 74, 45, 13);
        Header.add(lblNewLabel_2);

        JPanel Body = new JPanel();
        Body.setBounds(0, 96,(int) frameWidth,(int) (frameHeight*0.86));
        Body.setBackground(new Color(226, 219, 219));
        frame.getContentPane().add(Body);
        Body.setLayout(null);

        JPanel Menupanel = new CustomRoundedPanel(15, 15, 15, 15);
        Menupanel.setBackground(new Color(255, 255, 255));
        Menupanel.setBounds(5, 5,(int) (frameWidth*0.216), (int) (frameHeight*0.74));
        Menupanel.setBorder(new RoundedBorder(20));
        Body.add(Menupanel);
        Menupanel.setLayout(null);

        // Tạo và thêm các JMenuItem và JLabel (cho "Quản lý ca")
        JMenuItem QuanLyDatPhong = createMenuItem("Quản lý đặt phòng", 20, 10, 285, 60);
        Menupanel.add(QuanLyDatPhong);

        JMenuItem QuanLyKhachHang = createMenuItem("Quản lý khách hàng", 20, 70, 285, 60);
        Menupanel.add(QuanLyKhachHang);

        JMenuItem QuanLyDatDichVu = createMenuItem("Quản lý đặt dịch vụ", 20, 140, 285, 60);
        Menupanel.add(QuanLyDatDichVu);

        JLabel lblQuanLyCa = createMenuLabel("Quản lý ca", 30, 210, 248, 60);
        Menupanel.add(lblQuanLyCa);

        JPanel DongHoPannel = new JPanel();
        DongHoPannel.setBackground(new Color(255, 255, 255));
        DongHoPannel.setBounds(10, 483, 305, 99);
        Menupanel.add(DongHoPannel);
        DongHoPannel.setLayout(null);

        JLabel Calendar = new JLabel("");
        Calendar.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\lich.png"));
        Calendar.setBounds(28, 10, 45, 34);
        DongHoPannel.add(Calendar);

        JLabel DongHo = new JLabel("");
        DongHo.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\o'clock.png"));
        DongHo.setBounds(30, 54, 45, 35);
        DongHoPannel.add(DongHo);

        ngaythangnam = new JLabel("20/05/2004"); // Khởi tạo JLabel
        ngaythangnam.setFont(new Font("Times New Roman", Font.BOLD, 28));
        ngaythangnam.setBounds(94, 10, 173, 34);
        DongHoPannel.add(ngaythangnam);

        giophutgiay = new JLabel("10:21:13"); // Khởi tạo JLabel
        giophutgiay.setFont(new Font("Times New Roman", Font.BOLD, 28));
        giophutgiay.setBounds(94, 54, 183, 35);
        DongHoPannel.add(giophutgiay);

        // Tạo JPopupMenu cho "Quản lý ca"
        quanLyCaPopupMenu = new JPopupMenu();
        JMenuItem NhanCa = createPopupMenuItem("Nhận ca");
        quanLyCaPopupMenu.add(NhanCa);

        JMenuItem GiaoCa = createPopupMenuItem("Giao ca");
        quanLyCaPopupMenu.add(GiaoCa);

        lblQuanLyCa.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                quanLyCaPopupMenu.show(lblQuanLyCa, 130, lblQuanLyCa.getHeight());
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                lblQuanLyCa.setBackground(hoverBackgroundColor);
                lblQuanLyCa.setForeground(hoverForegroundColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                lblQuanLyCa.setBackground(defaultMenuItemBackground);
                lblQuanLyCa.setForeground(Color.BLACK); // Hoặc màu chữ mặc định của bạn
            }
        });
        defaultMenuItemBackground = lblQuanLyCa.getBackground(); // Lưu màu nền mặc định

        hinhKhachSanpannel = new CustomRoundedPanel(15, 15, 15, 15);;
        hinhKhachSanpannel.setBackground(new Color(255, 255, 255));
        hinhKhachSanpannel.setBounds(350, 5, (int) (frameWidth*0.77), (int) (frameHeight*0.841));
        hinhKhachSanpannel.setBorder(new RoundedBorder(20));
        Body.add(hinhKhachSanpannel);

        loadImagesFromFolder();
        hinhKhachSanpannel.setLayout(null);

        HinhAnhNen = new JLabel();
        HinhAnhNen.setIcon(null);
        HinhAnhNen.setBounds(5, 5,(int) (frameWidth*0.763), (int) (frameHeight*0.832));
        hinhKhachSanpannel.add(HinhAnhNen);
        updateImage();

        btnPrev = new JButton("");
        btnPrev.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\prev.png"));
        btnPrev.setBounds(10, 309, 50, 73);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setBorderPainted(false);
        btnPrev.setOpaque(false);
        hinhKhachSanpannel.add(btnPrev);
        hinhKhachSanpannel.setComponentZOrder(btnPrev, 0);
        btnPrev.addActionListener(e -> showPreviousImage());

        btnNext = new JButton("");
        btnNext.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\next .png"));
        btnNext.setBounds(1123, 309, 50, 73);
        btnNext.setContentAreaFilled(false);
        btnNext.setBorderPainted(false);
        btnNext.setOpaque(false);
        hinhKhachSanpannel.add(btnNext);
        hinhKhachSanpannel.setComponentZOrder(btnNext, 0);
     // Ẩn hai nút khi khởi tạo
        btnPrev.setVisible(false);
        btnNext.setVisible(false);

        // Thêm MouseListener cho btnPrev
        btnPrev.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnPrev.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnPrev.setVisible(false);
            }
        });

        // Thêm MouseListener cho btnNext
        btnNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNext.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNext.setVisible(false);
            }
        });
        hinhKhachSanpannel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                // Lấy vị trí chuột tương đối so với panel
                int mouseX = e.getX();
                int mouseY = e.getY();

                // Lấy tọa độ của nút bấm trong panel
                Rectangle boundsPrev = btnPrev.getBounds();
                Rectangle boundsNext = btnNext.getBounds();

                // Kiểm tra nếu chuột nằm trong vùng của button
                boolean isOverPrev = boundsPrev.contains(mouseX, mouseY);
                boolean isOverNext = boundsNext.contains(mouseX, mouseY);

                // Hiển thị hoặc ẩn button
                btnPrev.setVisible(isOverPrev);
                btnNext.setVisible(isOverNext);
            }
        });


        JPanel DangSuatpanel = new CustomRoundedPanel(15, 15, 15, 15);;
        DangSuatpanel.setBackground(new Color(255, 255, 255));
        DangSuatpanel.setBounds(5, 610,(int) (frameWidth*0.215), (int) (frameHeight*0.1));
        DangSuatpanel.setBorder(new RoundedBorder(20));;
        Body.add(DangSuatpanel);
        DangSuatpanel.setLayout(null);
        
        JButton DangSuat = new JButton("Đăng Suất");
        DangSuat.setBounds(30, 10, 285, 59);
        DangSuatpanel.add(DangSuat);
        DangSuat.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\exit.png"));
        DangSuat.setHorizontalAlignment(SwingConstants.LEFT);
        DangSuat.setToolTipText("");
        DangSuat.setFont(new Font("Times New Roman", Font.BOLD, 28));
        DangSuat.setBackground(new Color(255, 255, 255));
        DangSuat.setBorderPainted(false);
        DangSuat.setOpaque(false);


        btnNext.addActionListener(e -> showNextImage());

        timerSlideShow = new Timer(3000, e -> showNextImage());
        timerSlideShow.start();
    }

    // Hàm tạo JMenuItem với thuộc tính và thêm MouseListener cho hiệu ứng hover
    private JMenuItem createMenuItem(String text, int x, int y, int width, int height) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setBounds(x, y, width, height);
        menuItem.setBackground(new Color(255, 255, 255));
        menuItem.setFont(new Font("Times New Roman", Font.BOLD, 28));
        menuItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Đã chọn: " + text));

        menuItem.addMouseListener(new MouseAdapter() {
            private Color defaultBackground;
            private Color defaultForeground;

            @Override
            public void mouseEntered(MouseEvent e) {
                defaultBackground = menuItem.getBackground();
                defaultForeground = menuItem.getForeground();
                menuItem.setBackground(hoverBackgroundColor);
                menuItem.setForeground(hoverForegroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(defaultBackground);
                menuItem.setForeground(defaultForeground);
            }
        });
        return menuItem;
    }

    // Hàm tạo JLabel cho mục menu (ví dụ: "Quản lý ca") với hiệu ứng hover
    private JLabel createMenuLabel(String text, int x, int y, int width, int height) {
        JLabel QuanLyCaLable = new JLabel(text);
        QuanLyCaLable.setBounds(28, 211, width, height);
        QuanLyCaLable.setFont(new Font("Times New Roman", Font.BOLD, 28));
        QuanLyCaLable.setBackground(new Color(255, 255, 255));
        QuanLyCaLable.setForeground(Color.BLACK);
        QuanLyCaLable.setOpaque(true);

        QuanLyCaLable.addMouseListener(new MouseAdapter() {
            private Color defaultBackground;
            private Color defaultForeground;

            @Override
            public void mouseEntered(MouseEvent e) {
                defaultBackground = QuanLyCaLable.getBackground();
                defaultForeground = QuanLyCaLable.getForeground();
                QuanLyCaLable.setBackground(hoverBackgroundColor);
                QuanLyCaLable.setForeground(hoverForegroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                QuanLyCaLable.setBackground(defaultBackground);
                QuanLyCaLable.setForeground(defaultForeground);
            }
        });
        return QuanLyCaLable;
    }

    // Hàm tạo JMenuItem cho popup menu
    private JMenuItem createPopupMenuItem(String text) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setFont(new Font("Times New Roman", Font.BOLD, 25));
        menuItem.setBackground(new Color(255, 255, 255));
        menuItem.addActionListener(e -> JOptionPane.showMessageDialog(frame, "Đã chọn: " + text));
        return menuItem;
    }

    private void loadImagesFromFolder() {
        imagePaths = new ArrayList<>();
        File folder = new File(imageFolderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles((dir, name) -> name.toLowerCase().matches(".*\\.(png|jpg|jpeg|gif)$"));
            if (files != null) {
                for (File file : files) {
                    imagePaths.add(file.getAbsolutePath());
                }
            }
        }
        if (imagePaths.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Không tìm thấy hình ảnh trong thư mục!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateImage() {
        if (!imagePaths.isEmpty()) {
            HinhAnhNen.setIcon(new ImageIcon(imagePaths.get(currentIndex)));
        }
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + imagePaths.size()) % imagePaths.size();
        updateImage();
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % imagePaths.size();
        updateImage();
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

            // Tạo một GeneralPath để vẽ hình dạng tùy chỉnh
            GeneralPath path = new GeneralPath();

            // Bắt đầu từ góc trên trái
            path.moveTo(topLeft, 0);
            
            // Đường đến góc trên phải
            path.lineTo(width - topRight, 0);
            if (topRight > 0) {
                path.quadTo(width, 0, width, topRight);
            }
            
            // Đường đến góc dưới phải
            path.lineTo(width, height - bottomRight);
            if (bottomRight > 0) {
                path.quadTo(width, height, width - bottomRight, height);
            }
            
            // Đường đến góc dưới trái
            path.lineTo(bottomLeft, height);
            if (bottomLeft > 0) {
                path.quadTo(0, height, 0, height - bottomLeft);
            }
            
            // Đường trở lại góc trên trái
            path.lineTo(0, topLeft);
            if (topLeft > 0) {
                path.quadTo(0, 0, topLeft, 0);
            }

            path.closePath();
            g2.fill(path);
        }
        }

}