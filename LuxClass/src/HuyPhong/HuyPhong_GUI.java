package HuyPhong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietDonDatPhong_Dao;
import dao.DonDatPhong_Dao;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import entity.ChiTietDonDatPhong;
import entity.DonDatPhong;
import entity.LoaiPhong;
import entity.Phong;

public class HuyPhong_GUI {
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	DonDatPhong_Dao donDatPhongDao = new DonDatPhong_Dao();
	public static void main(String[] args) {

		String maDonDatPhong = "23052025LT001001";
		DonDatPhong_Dao donDatPhong_Dao = new DonDatPhong_Dao();
		DonDatPhong donDatPhong = donDatPhong_Dao.timDonTheoMa(maDonDatPhong);
		SwingUtilities.invokeLater(() -> {
			new HuyPhong_GUI().taoDonHuyPhong(donDatPhong);
		});
	}

	public void hienThiDonDatPhong(DonDatPhong don, ArrayList<LoaiPhong> dsLoaiPhong, JPanel roomPanel) {
		LoaiPhong_Dao loaiPhongDao = new LoaiPhong_Dao();
		dsLoaiPhong = loaiPhongDao.getAllLoaiPhong();
		Phong_Dao phongDao = new Phong_Dao();

		roomPanel.removeAll(); // Xoá sạch để làm mới giao diện
		roomPanel.setLayout(new BorderLayout());
		roomPanel.setBorder(BorderFactory.createTitledBorder("Thông tin phòng"));
		ChiTietDonDatPhong_Dao dao = new ChiTietDonDatPhong_Dao();
		ArrayList<ChiTietDonDatPhong> chiTietList = dao.getChiTietDonDatPhongTheoMaDon(don.getMaDonDatPhong());
		Object[][] roomData = new Object[chiTietList.size()][4];

		ZoneId zone = ZoneId.systemDefault();
		long soGio = Duration.between(don.getNgayNhanPhong().atZone(zone).toInstant(),
				don.getNgayTraPhong().atZone(zone).toInstant()).toHours();
		long soNgay = Duration.between(don.getNgayNhanPhong().atZone(zone).toInstant(),
				don.getNgayTraPhong().atZone(zone).toInstant()).toHours(); // Lấy số giờ giữa hai thời điểm

		// Kiểm tra nếu số giờ >= 12 thì tính thành 1 ngày, nếu không thì tính là 0 ngày
		long soNgayLamTron = (soNgay >= 12) ? 1 : 0;

		for (int i = 0; i < chiTietList.size(); i++) {
			ChiTietDonDatPhong ct = chiTietList.get(i);
			Phong phong = ct.getPhong();
			phong = phongDao.getPhongTheoMa(phong.getSoPhong());
			LoaiPhong loai = loaiPhongDao.getLoaiPhongTheoMa(phong.getLoaiPhong().getMaLoaiPhong());

			roomData[i][0] = phong.getSoPhong();
			roomData[i][1] = loai.getTenLoai();

			switch (don.getLoaiDon()) {
			case "Theo giờ":
				roomData[i][2] = soGio;
				roomData[i][3] = String.format("%,.0f VND", loai.getGiaTheoGio());
				break;
			case "Theo ngày":
				roomData[i][2] = soNgayLamTron;
				roomData[i][3] = String.format("%,.0f VND", loai.getGiaTheoNgay());
				break;
			case "Theo đêm":
				roomData[i][2] = soNgay;
				roomData[i][3] = String.format("%,.0f VND", loai.getGiaTheoDem());
				break;
			}
		}

		String[] roomHeaders = { "Mã Phòng", "Loại phòng", "Số " + (don.getLoaiDon().equals("GIO") ? "giờ" : "ngày"),
				"Giá phòng" };
		DefaultTableModel model = new DefaultTableModel(roomData, roomHeaders);
		JTable roomTable = new JTable(model);

		roomTable.setEnabled(false);
		roomTable.setRowHeight(25);
		roomTable.setFont(new Font("Arial", Font.PLAIN, 13));
		roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		JScrollPane scroll = new JScrollPane(roomTable);
		scroll.setPreferredSize(new Dimension(400, 60));

		double tongTien = don.tinhTienPhong();
		JLabel tongTienLabel = new JLabel(String.format("Tổng tiền thuê: %,.0f VND", tongTien));
		tongTienLabel.setFont(new Font("Arial", Font.BOLD, 13));
		tongTienLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 5));

		roomPanel.add(scroll, BorderLayout.CENTER);
		roomPanel.add(tongTienLabel, BorderLayout.SOUTH);

		roomPanel.revalidate();
		roomPanel.repaint();
	}

	public static JPanel taoCancelFeePanel(DonDatPhong don) {
		JPanel cancelFeePanel = new JPanel(new BorderLayout());
		cancelFeePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
				"Chi phí hủy phòng", TitledBorder.LEFT, TitledBorder.TOP));

		// Định dạng ngày giờ hiện tại
		LocalDateTime now = LocalDateTime.now();
		String ngayHuy = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		String thoiGianHuy = now.format(DateTimeFormatter.ofPattern("HH:mm"));

		// Tạo dữ liệu bảng
		String[] headers = { "Ngày hủy phòng", "Thời gian hủy", "Phí hủy", "Số tiền hoàn cọc" };
		Object[][] data = { { ngayHuy, thoiGianHuy, don.phiHuyPhong(now, now), String.format("%,.0f VND", don.tinhTienHoanCoc()) } };

		// Tạo bảng bằng DefaultTableModel
		DefaultTableModel model = new DefaultTableModel(data, headers);
		JTable feeTable = new JTable(model);
		feeTable.setEnabled(false);
		feeTable.setRowHeight(25);
		feeTable.setFont(new Font("Arial", Font.PLAIN, 13));
		feeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));

		// Đặt bảng vào scroll pane
		JScrollPane scrollCancel = new JScrollPane(feeTable);
		scrollCancel.setPreferredSize(new Dimension(400, 60));

		cancelFeePanel.add(scrollCancel, BorderLayout.CENTER);
		return cancelFeePanel;
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

		String[] roomHeaders = { "Mã Phòng", "Loại phòng", "Số ngày thuê", "Giá phòng" };
		Object[][] roomData = { { "P102", "Single room", 3, "800.000VND" } };
		JTable roomTable = new JTable(roomData, roomHeaders);
//	    roomPanel.add(new JScrollPane(roomTable), BorderLayout.CENTER);
//	    roomPanel.add(new JLabel("Tổng tiền thuê: 2.400.000 VND"), BorderLayout.SOUTH);
//
//	    
//	    
//	    roomTable.setEnabled(false);
//	    roomTable.setRowHeight(25); // chỉnh độ cao dòng
//	    roomTable.setFont(new Font("Arial", Font.PLAIN, 13));
//	    roomTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
//
//	    JScrollPane scrollInfo = new JScrollPane(roomTable);
//	    scrollInfo.setPreferredSize(new Dimension(400, 60)); // chỉnh kích thước
//	    roomPanel.add(scrollInfo);\
		LoaiPhong_Dao loaiPhongDao = new LoaiPhong_Dao();
		ArrayList<LoaiPhong> listLoaiPhong = loaiPhongDao.getAllLoaiPhong();

		// Đưa vào Map để gọi hàm
		hienThiDonDatPhong(donDatPhong, listLoaiPhong, roomPanel);
		mainPanel.add(roomPanel);
		// III. Tiền cọc
		JPanel depositPanel = new JPanel(new GridLayout(2, 2));
		depositPanel.setBorder(BorderFactory.createTitledBorder("Tiền cọc"));

		depositPanel.add(new JLabel(String.format("Số tiền khách hàng cọc: %, .0f VND", donDatPhong.getTienCoc())));

		mainPanel.add(depositPanel);

		// IV. Chi phí hủy phòng
		JPanel cancelFeePanel = new JPanel(new BorderLayout());
//		cancelFeePanel.setBorder(BorderFactory.createTitledBorder("Chi phí hủy phòng"));
//
//		String[] feeHeaders = { "Ngày hủy phòng", "Thời gian hủy", "Phí hủy", "Số tiền hoàn cọc" };
//		Object[][] feeData = { { "18/03/2025", "11:33", "Miễn phí", "1.200.000 VND" } };
//		JTable feeTable = new JTable(feeData, feeHeaders);
//		cancelFeePanel.add(new JScrollPane(feeTable), BorderLayout.CENTER);
//		feeTable.setEnabled(false);
//		feeTable.setRowHeight(25); // chỉnh độ cao dòng
//		feeTable.setFont(new Font("Arial", Font.PLAIN, 13));
//		feeTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
		cancelFeePanel = taoCancelFeePanel(donDatPhong);
		mainPanel.add(cancelFeePanel);
//		JScrollPane scrollCancel = new JScrollPane(feeTable);
//		scrollCancel.setPreferredSize(new Dimension(400, 60)); // chỉnh kích thước
//		cancelFeePanel.add(scrollCancel);
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
	
//	btnConfirm.addActionListener((ActionListener) new ActionListener() {
//	    @Override
//	    public void actionPerformed(ActionEvent e) {
//	        // Giả sử bạn có mã đơn đặt phòng cần hủy
//	        String maDonDatPhong = JOptionPane.showInputDialog(null, "Nhập mã đơn đặt phòng cần hủy:");
//
//	        if (maDonDatPhong != null && !maDonDatPhong.trim().isEmpty()) {
//	            boolean huyThanhCong = donDatPhongDao.huyDonDatPhong(maDonDatPhong.trim());
//
//	            if (huyThanhCong) {
//	                JOptionPane.showMessageDialog(null, "Hủy đơn đặt phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//	            } else {
//	                JOptionPane.showMessageDialog(null, "Hủy đơn đặt phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//	            }
//	        }
//	    }
//	});
}
