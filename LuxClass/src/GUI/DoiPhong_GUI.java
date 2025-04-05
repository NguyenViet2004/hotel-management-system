package GUI;

import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.geom.GeneralPath;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.*;
import java.awt.*;

public class DoiPhong_GUI extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    DoiPhong_GUI frame = new DoiPhong_GUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public DoiPhong_GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        // Thiết lập cửa sổ khi đóng
        setExtendedState(JFrame.MAXIMIZED_BOTH);          // Thiết lập kích thước cửa sổ để chiếm toàn bộ màn hình
        setResizable(false);        // Không cho phép thay đổi kích thước cửa sổ bằng kéo thả
        
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout(10, 10)); // 20px khoảng cách ngang và dọc giữa các panel
        setContentPane(contentPane);
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = (int) screenSize.getWidth();
        int screenHeight = (int) screenSize.getHeight();

     // Tạo RoundedPanel thay vì JPanel thường
        CustomRoundedPanel panelNorth = new CustomRoundedPanel(0, 0, 15, 15);
        panelNorth.setBackground(Color.BLACK);
        panelNorth.setPreferredSize(new Dimension(screenWidth, (int) (screenHeight * 0.15)));

        JLabel titleLabel = new JLabel("QUẢN LÝ ĐẶT PHÒNG", JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE); // Đặt màu chữ
        panelNorth.add(titleLabel);

        add(panelNorth, BorderLayout.NORTH);
        
        CustomRoundedPanel panelWest = new CustomRoundedPanel(15, 15, 15, 15);
        panelWest.setPreferredSize(new Dimension((int) (screenWidth * 0.15), (int) (screenHeight * 0.9)));
        panelWest.setBackground(Color.LIGHT_GRAY);
        panelWest.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Khoảng cách xung quanh panelWest
        add(panelWest, BorderLayout.WEST);
        
        // Panel phòng (Center)
        JPanel panelCen= new JPanel();
        panelCen.setPreferredSize(new Dimension((int)(screenWidth*0.70), (screenHeight)));
        
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

//jhkjhkjkhk
}
