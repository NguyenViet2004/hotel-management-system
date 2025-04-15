package Demo;

import javax.swing.*;
import GUI.DatPhong_GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class giaodien extends JFrame implements ActionListener {
    private JButton btnDatPhong;
    private JButton btnDatPhong2;

    public giaodien() {
        // Hiển thị tối đa
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Kích thước màn hình
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.width * 1.0);
        int screenHeight = (int) (screenSize.height * 1.0);

        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());
        add(contentPane);
        contentPane.setBackground(Color.BLACK);

        // Header
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 128, 128));
        panelHeader.setPreferredSize(new Dimension(0, (int) (screenHeight * 0.15)));

        // Main Panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(200, 200, 200));

        // Thêm vào layout
        contentPane.add(panelHeader, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Cỡ chữ
        int fontSize = Math.min(screenWidth, screenHeight) / 20;

        // Header label
        JLabel headerLabel = new JLabel("Đây là gì");
        headerLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        headerLabel.setForeground(Color.WHITE);
        panelHeader.add(headerLabel);

        // Tạo panel chứa nút
        JPanel panelButtons = new JPanel();
        panelButtons.setBackground(new Color(200, 200, 200)); // giống mainPanel

        // Nút "Đặt phòng 1"
        btnDatPhong = new JButton("Đặt phòng");
        btnDatPhong.setPreferredSize(new Dimension(200, 50));
        btnDatPhong.setFocusPainted(false);
        btnDatPhong.addActionListener(this);
        
        ImageIcon icon = new ImageIcon(getClass().getResource("img/HinhAnhGiaoDienChinh/lich.png"));
		Image scaledImage = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon resizedIcon = new ImageIcon(scaledImage);
		btnDatPhong.setIcon(resizedIcon);

        // Nút "Đặt phòng 2"
        btnDatPhong2 = new JButton("đổi phòng");
        btnDatPhong2.setPreferredSize(new Dimension(200, 50));
        btnDatPhong2.setFocusPainted(false);
        btnDatPhong2.addActionListener(this);

        // Thêm nút vào panelButtons
        panelButtons.add(btnDatPhong);
        panelButtons.add(btnDatPhong2);

        // Thêm panel chứa nút vào mainPanel
        mainPanel.add(panelButtons);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDatPhong ) {
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            giaodien frame = new giaodien();
            frame.setVisible(true);
        });
    }
}
