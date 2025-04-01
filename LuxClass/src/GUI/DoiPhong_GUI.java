package GUI;

import java.awt.EventQueue;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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

        // Panel tiêu đề (North)
        JPanel panelNorth = new JPanel();
        panelNorth.setBackground(Color.BLACK);
        panelNorth.setPreferredSize(new Dimension(screenWidth, (int) (screenHeight * 0.15)));
        panelNorth.add(new JLabel("QUẢN LÝ ĐẶT PHÒNG", JLabel.CENTER));
        panelNorth.setForeground(Color.WHITE);
        add(panelNorth, BorderLayout.NORTH);
        
        // Panel danh mục (West)
        JPanel panelWest = new JPanel();
        panelWest.setPreferredSize(new Dimension((int) (screenWidth * 0.15), (int) (screenHeight * 0.9)));
        panelWest.setBackground(Color.LIGHT_GRAY);
        panelWest.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Khoảng cách xung quanh panelWest
        add(panelWest, BorderLayout.WEST);
        
        // Panel phòng (Center)
        JPanel panelCen= new JPanel();
        panelCen.setPreferredSize(new Dimension((int) (screenWidth*0.8), (screenHeight)));
        panelCen.setBackground(Color.GREEN);
        add(panelCen,BorderLayout.CENTER);
        
        
    }
}
