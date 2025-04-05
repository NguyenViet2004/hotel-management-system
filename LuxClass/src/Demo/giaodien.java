package Demo;

import javax.swing.*;
import javax.swing.border.LineBorder;

import GUI.DatPhong_GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class giaodien extends JFrame implements ActionListener {
    private JButton btnDatPhong;
    
    public giaodien() {
        // Đặt chế độ hiển thị tối đa (vẫn giữ thanh tiêu đề)
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        // Lấy kích thước màn hình một lần duy nhất
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.width * 1.0);
        int screenHeight = (int) (screenSize.height * 1.0);

        // Đặt kích thước JFrame
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sử dụng BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());
        add(contentPane);
        contentPane.setBackground(Color.BLACK);

        // Tạo header panel
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 128, 128));
        panelHeader.setPreferredSize(new Dimension(0, (int) (screenHeight * 0.15))); // 15% chiều cao

        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(200, 200, 200));

        // Thêm vào BorderLayout
        contentPane.add(panelHeader, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Tính toán cỡ chữ linh hoạt dựa trên kích thước màn hình
        int fontSize = Math.min(screenWidth, screenHeight) / 20; // Điều chỉnh hệ số chia

        // Thêm nội dung vào header
        JLabel headerLabel = new JLabel("Đây là gì");
        headerLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        headerLabel.setForeground(Color.WHITE);
        panelHeader.add(headerLabel);

        // Thêm nút "Đặt phòng"
        btnDatPhong = new JButton("");
        btnDatPhong.setPreferredSize(new Dimension(30, 30));
        ImageIcon icon = new ImageIcon(getClass().getResource("/HinhAnhGiaoDienChinh/lich.png"));

        btnDatPhong.setIcon(icon);
//        btnDatPhong.setFont(new Font("Arial", Font.BOLD, fontSize / 2)); // Cỡ chữ nhỏ hơn header
//        btnDatPhong.setBackground(new Color(0, 102, 204)); // Màu xanh
//        btnDatPhong.setForeground(Color.WHITE);
//        btnDatPhong.setFocusPainted(false);
//        btnDatPhong.setPreferredSize(new Dimension(200, 50));
        btnDatPhong.addActionListener(this);
        
        // Thêm nút vào mainPanel
        mainPanel.add(btnDatPhong);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDatPhong) {
            // Hiển thị cửa sổ đặt phòng như một cửa sổ modal
            DatPhong_GUI frameDatPhong = new DatPhong_GUI(this);
            frameDatPhong.setVisible(true); // Hiển thị cửa sổ
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            giaodien frame = new giaodien();
            frame.setVisible(true);
        });
    }
}
