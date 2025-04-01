package Demo;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class giaodien extends JFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					giaodien frame = new giaodien();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public giaodien() {
        // Lấy kích thước màn hình
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) (screenSize.width * 0.8);
        int screenHeight = (int) (screenSize.height * 0.8);

        // Đặt kích thước JFrame
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Sử dụng BorderLayout
        JPanel contentPane = new JPanel(new BorderLayout());
        setContentPane(contentPane);

        // Tạo header panel
        JPanel panelHeader = new JPanel();
        panelHeader.setBackground(new Color(0, 128, 128));
        panelHeader.setPreferredSize(new Dimension(0, (int) (screenHeight * 0.15))); // 20% chiều cao

        // Tạo panel chính
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(200, 200, 200));

        // Thêm vào BorderLayout
        contentPane.add(panelHeader, BorderLayout.NORTH);
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // Thêm nội dung vào header
        JLabel headerLabel = new JLabel("Đây là gì");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        headerLabel.setForeground(Color.WHITE);
        panelHeader.add(headerLabel);
    }
	
	
	
}
