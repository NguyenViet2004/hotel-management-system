package HuyPhong;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import dao.DonDatPhong_Dao;
import entity.DonDatPhong;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DSPhongDatTruoc_Gui extends JPanel {
	private JTextField txtSoDienThoai;
	private JTable tableDaDat, tableTamThoi;
	private DefaultTableModel modelDaDat, modelTamThoi;
	private JTabbedPane tabbedPane;

	public DSPhongDatTruoc_Gui() {
		setLayout(new BorderLayout(10, 10));
		setBackground(Color.WHITE);
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		add(createTitlePanel(), BorderLayout.NORTH);
		add(createSearchPanel(), BorderLayout.CENTER);
		add(createTabbedPane(), BorderLayout.SOUTH);
		
		loadTatCaDonDatPhong();
	}

	private JPanel createTitlePanel() {
		JLabel lblTieuDe = new JLabel("Tìm kiếm đơn đặt phòng theo SĐT", SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 28));

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.WHITE);
		panel.add(lblTieuDe, BorderLayout.CENTER);
		return panel;
	}

	private JPanel createSearchPanel() {
		JPanel panelTimKiem = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
		panelTimKiem.setBackground(Color.WHITE);

		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		txtSoDienThoai = new JTextField(20);
		txtSoDienThoai.setFont(new Font("Times New Roman", Font.PLAIN, 20));

		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setFont(new Font("Times New Roman", Font.BOLD, 20));

		btnTimKiem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timKiemDonDatPhongTheoSDT();
			}
		});

		panelTimKiem.add(lblSDT);
		panelTimKiem.add(txtSoDienThoai);
		panelTimKiem.add(btnTimKiem);
		return panelTimKiem;
	}

	private JTable createTableWithButtons(DefaultTableModel model) {
		JTable table = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 4; // Chỉ cột cuối cùng (Chức năng) mới có thể tương tác
			}
		};

		table.setRowHeight(40);

		// Renderer cho nút
		table.getColumnModel().getColumn(4).setCellRenderer(new TableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JPanel panel = new JPanel(null);

				JButton btnNhan = new JButton("Nhận phòng");
				JButton btnHuy = new JButton("Hủy");

				// Cài đặt giống như phần Editor
				btnNhan.setBackground(new Color(76, 175, 80));
				btnNhan.setForeground(Color.WHITE);
				btnNhan.setBounds(5, 5, 120, 30); 

				btnHuy.setBackground(new Color(244, 67, 54));
				btnHuy.setForeground(Color.WHITE);
				btnHuy.setBounds(130, 5, 60, 30);

				panel.setLayout(null);
				panel.setPreferredSize(new Dimension(195, 40));
				panel.add(btnNhan);
				panel.add(btnHuy);

				return panel;
			}
		});

		// Editor cho nút
		table.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
			JPanel panel = new JPanel(null); // dùng layout null để set kích thước thủ công
			JButton btnNhan = new JButton("Nhận phòng");
			JButton btnHuy = new JButton("Hủy");

			{
				panel.setLayout(null);
				panel.setOpaque(true);

				// Cài đặt màu sắc
				btnNhan.setBackground(new Color(76, 175, 80)); // Xanh lá
				btnNhan.setForeground(Color.WHITE);

				btnHuy.setBackground(new Color(244, 67, 54)); // Đỏ
				btnHuy.setForeground(Color.WHITE);

				// Kích thước nút (width lớn hơn cho btnNhan)
				btnNhan.setBounds(5, 5, 120, 30);   // Giữ nguyên nút nhận phòng
				btnHuy.setBounds(130, 5, 60, 30);  // Tăng lên 60 để đủ chỗ hiện chữ "Hủy"
				panel.setPreferredSize(new Dimension(195, 40)); // Cập nhật chiều rộng tổng thể
				panel.add(btnNhan);
				panel.add(btnHuy);

				// Sự kiện bấm nút
				btnNhan.addActionListener(e -> {
					int row = table.getSelectedRow();
					JOptionPane.showMessageDialog(null, "Đã nhận phòng cho dòng " + row);
				});

				btnHuy.addActionListener(e -> {
					int row = table.getSelectedRow();
					JOptionPane.showMessageDialog(null, "Đã hủy phòng cho dòng " + row);
				});
			}

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
					int column) {
				return panel;
			}

			@Override
			public Object getCellEditorValue() {
				return "";
			}
		});

		return table;
	}

	private JTabbedPane createTabbedPane() {
		tabbedPane = new JTabbedPane();
		tabbedPane.setFont(new Font("Times New Roman", Font.BOLD, 18));

		String[] columnNames = { "SĐT", "Tên khách hàng", "Ngày nhận phòng", "Ngày trả phòng", "Chức năng" };

		modelDaDat = new DefaultTableModel(new Object[][] {}, columnNames);
		modelTamThoi = new DefaultTableModel(new Object[][] {}, columnNames);

		tableDaDat = createTableWithButtons(modelDaDat);
		JScrollPane scrollPane1 = new JScrollPane(tableDaDat);

		tableTamThoi = createTableWithButtons(modelTamThoi);
		JScrollPane scrollPane2 = new JScrollPane(tableTamThoi);

		tabbedPane.add("Đơn đã đặt", scrollPane1);
		tabbedPane.add("Đơn tạm thời", scrollPane2);

		return tabbedPane;
	}

	private JTable createTable(DefaultTableModel model) {
		JTable tbl = new JTable(model);
		tbl.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		tbl.setRowHeight(25);

		JTableHeader header = tbl.getTableHeader();
		header.setFont(new Font("Times New Roman", Font.BOLD, 20));
		header.setBackground(new Color(22, 160, 133));
		header.setForeground(Color.WHITE);

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		for (int i = 0; i < tbl.getColumnCount(); i++) {
			tbl.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		return tbl;
	}

	private void timKiemDonDatPhongTheoSDT() {
		String sdt = txtSoDienThoai.getText().trim();
		if (!sdt.isEmpty()) {
			DonDatPhong_Dao dao = new DonDatPhong_Dao();
			ArrayList<DonDatPhong> danhSach = dao.getDonDatPhongTheoSDT(sdt);

			modelDaDat.setRowCount(0);
			modelTamThoi.setRowCount(0);

			for (DonDatPhong ddp : danhSach) {
				String soDienThoai = ddp.getKhachHang().getSdt();
				String tenKhach = ddp.getKhachHang().getHoTen();
				String ngayNhan = ddp.getNgayNhanPhong().toString(); // Cần format đẹp nếu muốn
				String ngayTra = ddp.getNgayTraPhong().toString(); // Cần format đẹp nếu muốn
				String chucNang = ""; // ô này sẽ bị ghi đè bởi nút trong renderer

				Object[] row = new Object[] { soDienThoai, tenKhach, ngayNhan, ngayTra, chucNang // giữ trống, renderer
																									// sẽ gán nút vào
																									// đây
				};

				if ("Đã đặt".equals(ddp.getTrangThai())) {
					modelDaDat.addRow(row);
				} else if ("Đơn tạm".equals(ddp.getTrangThai())) {
					modelTamThoi.addRow(row);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Cảnh báo",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void loadTatCaDonDatPhong() {
	    dao.DonDatPhong_Dao dao = new dao.DonDatPhong_Dao();
	    ArrayList<DonDatPhong> danhSach = dao.getAllDonDatPhong(); // Viết hàm này trong DAO nếu chưa có

	    modelDaDat.setRowCount(0);
	    modelTamThoi.setRowCount(0);

	    for (DonDatPhong ddp : danhSach) {
	        String soDienThoai = ddp.getKhachHang().getSdt();
	        String tenKhach = ddp.getKhachHang().getHoTen();
	        String ngayNhan = ddp.getNgayNhanPhong().toString();
	        String ngayTra = ddp.getNgayTraPhong().toString();
	        String chucNang = "";

	        Object[] row = new Object[] {
	            soDienThoai,
	            tenKhach,
	            ngayNhan,
	            ngayTra,
	            chucNang
	        };

	        if ("Đã đặt".equals(ddp.getTrangThai())) {
	            modelDaDat.addRow(row);
	        } else if ("Đơn tạm".equals(ddp.getTrangThai())) {
	            modelTamThoi.addRow(row);
	        }
	    }
	}


	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Tìm đơn đặt phòng");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1000, 700); // tuỳ chỉnh theo kích thước panel

			DSPhongDatTruoc_Gui panel = new DSPhongDatTruoc_Gui();
			frame.setContentPane(panel);
			frame.setLocationRelativeTo(null); // căn giữa màn hình
			frame.setVisible(true);
		});
	}
}
