package GiaoDienChinh;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private final String imageFolderPath = "C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\HinhNen";
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
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\logo.png"));
        frame.setBounds(100, 100, 1280, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel Header = new JPanel();
        Header.setBackground(new Color(255, 255, 255));
        Header.setBounds(0, 0, 1266, 108);
        frame.getContentPane().add(Header);
        Header.setLayout(null);
        Header.setBorder(new LineBorder(Color.black));

        JLabel lblLoGo = new JLabel("");
        lblLoGo.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\logo.png"));
        lblLoGo.setBounds(10, 0, 90, 98);
        Header.add(lblLoGo);

        JButton help = new JButton("");
        help.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\help.png"));
        help.setBounds(1088, 39, 45, 37);
        help.setContentAreaFilled(false); // Tắt nền
        help.setBorderPainted(false);
        Header.add(help);

        JLabel lblNewLabel_1 = new JLabel("New label");
        lblNewLabel_1.setBounds(1197, 10, 45, 13);
        Header.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("New label");
        lblNewLabel_2.setBounds(1197, 63, 45, 13);
        Header.add(lblNewLabel_2);

        JPanel Body = new JPanel();
        Body.setBackground(new Color(226, 219, 219));
        Body.setBounds(0, 106, 1266, 577);
        frame.getContentPane().add(Body);
        Body.setLayout(null);

        JPanel Menupanel = new JPanel();
        Menupanel.setBackground(new Color(255, 255, 255));
        Menupanel.setBounds(10, 10, 325, 474);
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
        DongHoPannel.setBounds(10, 365, 305, 99);
        Menupanel.add(DongHoPannel);
        DongHoPannel.setLayout(null);

        JLabel Calendar = new JLabel("");
        Calendar.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\Lich.png"));
        Calendar.setBounds(28, 10, 45, 34);
        DongHoPannel.add(Calendar);

        JLabel DongHo = new JLabel("");
        DongHo.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\DongHo.png"));
        DongHo.setBounds(28, 54, 45, 35);
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

        hinhKhachSanpannel = new JPanel();
        hinhKhachSanpannel.setBackground(new Color(255, 255, 255));
        hinhKhachSanpannel.setBounds(343, 10, 913, 557);
        hinhKhachSanpannel.setBorder(new RoundedBorder(20));
        Body.add(hinhKhachSanpannel);

        loadImagesFromFolder();
        hinhKhachSanpannel.setLayout(null);

        HinhAnhNen = new JLabel();
        HinhAnhNen.setIcon(null);
        HinhAnhNen.setBounds(0, 0, 913, 557);
        hinhKhachSanpannel.add(HinhAnhNen);
        updateImage();

        btnPrev = new JButton("");
        btnPrev.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\next - Copy.png"));
        btnPrev.setBounds(10, 250, 50, 40);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setBorderPainted(false);
        btnPrev.setOpaque(false);
        hinhKhachSanpannel.add(btnPrev);
        hinhKhachSanpannel.setComponentZOrder(btnPrev, 0);
        btnPrev.addActionListener(e -> showPreviousImage());

        btnNext = new JButton("");
        btnNext.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\test\\Hinh_Anh\\next.png"));
        btnNext.setBounds(850, 250, 50, 40);
        btnNext.setContentAreaFilled(false);
        btnNext.setBorderPainted(false);
        btnNext.setOpaque(false);
        hinhKhachSanpannel.add(btnNext);
        hinhKhachSanpannel.setComponentZOrder(btnNext, 0);
        JPanel DangSuatpanel = new JPanel();
        DangSuatpanel.setBackground(new Color(255, 255, 255));
        DangSuatpanel.setBounds(10, 494, 325, 73);
        DangSuatpanel.setBorder(new RoundedBorder(20));;
        Body.add(DangSuatpanel);
        DangSuatpanel.setLayout(null);

        JButton DangSuat = new JButton("Đăng Suất");
        DangSuat.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\Downloads\\icons8-exit-50.png"));
        DangSuat.setHorizontalAlignment(SwingConstants.LEFT);
        DangSuat.setToolTipText("");
        DangSuat.setFont(new Font("Times New Roman", Font.BOLD, 28));
        DangSuat.setBackground(new Color(255, 255, 255));
        DangSuat.setBounds(30, 10, 285, 59);
        DangSuat.setBorderPainted(false);
        DangSuat.setOpaque(false);

        DangSuatpanel.add(DangSuat);


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
} 