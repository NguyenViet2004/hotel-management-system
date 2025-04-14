package HuyPhong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import dao.DonDatPhong_Dao;
import entity.DonDatPhong;

public class HuyPhong_GUI {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public static void main(String[] args) {
		
		String maDonDatPhong = "140420255001";
		DonDatPhong_Dao donDatPhong_Dao = new DonDatPhong_Dao();
		DonDatPhong donDatPhong = donDatPhong_Dao.timDonTheoMa(maDonDatPhong);
        SwingUtilities.invokeLater(() -> {
            new HuyPhong_GUI().taoDonHuyPhong(donDatPhong);
        });
	}
	public void taoDonHuyPhong(DonDatPhong donDatPhong) {
	    JFrame frame = new JFrame("Đơn Hủy Phòng");
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    frame.setSize(600, 700);
	    frame.setLocationRelativeTo(null);
	    frame.setLayout(new BorderLayout());

	    JPanel mainPanel = new JPanel();
	    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

	    // I. Thông tin khách hàng
	    JPanel customerPanel = new JPanel(new GridLayout(6, 2, 5, 5));
	    customerPanel.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

	    customerPanel.add(new JLabel("👤 Khách hàng:"));
	    JTextField txtCustomer = new JTextField(donDatPhong.getKhachHang().getHoTen());
	    customerPanel.add(txtCustomer);
	    txtCustomer.setEditable(false);

	    customerPanel.add(new JLabel("📞 Điện thoại:"));
	    JTextField txtPhone = new JTextField(donDatPhong.getKhachHang().getSdt());
	    customerPanel.add(txtPhone);
	    txtPhone.setEditable(false);

	    customerPanel.add(new JLabel("📅 Ngày nhận phòng:"));
	    
	    JTextField txtCheckIn = new JTextField(donDatPhong.getNgayNhanPhong().format(formatter));
	    customerPanel.add(txtCheckIn);
	    txtCheckIn.setEditable(false);

	    customerPanel.add(new JLabel("📅 Ngày trả phòng:"));
	    JTextField txtCheckOut = new JTextField(donDatPhong.getNgayTraPhong().format(formatter));
	    customerPanel.add(txtCheckOut);
	    txtCheckOut.setEditable(false);

	    mainPanel.add(customerPanel);

	    // II. Thông tin phòng
	    JPanel roomPanel = new JPanel(new BorderLayout());
	    roomPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phòng"));

	    String[] roomHeaders = {"Mã Phòng", "Loại phòng", "Số ngày thuê", "Giá phòng"};
	    Object[][] roomData = {
	        {"P102", "Single room", 3, "800.000VND"}
	    };
	    JTable roomTable = new JTable(roomData, roomHeaders);
	    roomPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
	    roomPanel.add(new JLabel("Tổng tiền thuê: 2.400.000 VND"), BorderLayout.SOUTH);

	    mainPanel.add(roomPanel);
	    
	    roomTable.setEnabled(false);
	    roomTable.setRowHeight(25); // chỉnh độ cao dòng
	    roomTable.setFont(new Font("Arial", Font.PLAIN, 13));
	    roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

	    JScrollPane scrollInfo = new JScrollPane(roomTable);
	    scrollInfo.setPreferredSize(new Dimension(400, 60)); // chỉnh kích thước
	    roomPanel.add(scrollInfo);
	    // III. Tiền cọc
	    JPanel depositPanel = new JPanel(new GridLayout(2, 2));
	    depositPanel.setBorder(BorderFactory.createTitledBorder("Tiền cọc"));

	    depositPanel.add(new JLabel("Số tiền khách hàng cọc:"));
	    depositPanel.add(new JLabel("1.200.000 VND"));

	    mainPanel.add(depositPanel);

	    // IV. Chi phí hủy phòng
	    JPanel cancelFeePanel = new JPanel(new BorderLayout());
	    cancelFeePanel.setBorder(BorderFactory.createTitledBorder("Chi phí hủy phòng"));

	    String[] feeHeaders = {"Ngày hủy phòng", "Thời gian hủy", "Phí hủy", "Số tiền hoàn cọc"};
	    Object[][] feeData = {
	        {"18/03/2025", "11:33", "Miễn phí", "1.200.000 VND"}
	    };
	    JTable feeTable = new JTable(feeData, feeHeaders);
	    cancelFeePanel.add(new JScrollPane(feeTable), BorderLayout.CENTER);

	    mainPanel.add(cancelFeePanel);

	    feeTable.setEnabled(false);
	    feeTable.setRowHeight(25); // chỉnh độ cao dòng
	    feeTable.setFont(new Font("Arial", Font.PLAIN, 13));
	    feeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

	    JScrollPane scrollCancel = new JScrollPane(feeTable);
	    scrollCancel.setPreferredSize(new Dimension(400, 60)); // chỉnh kích thước
	    cancelFeePanel.add(scrollCancel);
	    // Nút xác nhận
	    JPanel buttonHuy = new JPanel((LayoutManager) new FlowLayout(FlowLayout.RIGHT));
	    JButton btnConfirm = new JButton("Xác nhận hủy");
	    btnConfirm.setBackground(Color.RED);
	    btnConfirm.setForeground(Color.WHITE);
	    btnConfirm.setFont(new Font("Arial", Font.BOLD, 14));
	    buttonHuy.add(btnConfirm);
	    mainPanel.add(Box.createVerticalStrut(10));
	    mainPanel.add(buttonHuy);

	    frame.add(new JScrollPane(mainPanel), BorderLayout.CENTER);
	    frame.setVisible(true);
	}

}
