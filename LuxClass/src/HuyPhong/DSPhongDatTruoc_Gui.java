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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DSPhongDatTruoc_Gui extends JPanel {
	private JTextField txtSoDienThoai;
	private JTable tableDaDat, tableTamThoi;
	private DefaultTableModel modelDaDat, modelTamThoi;
	private JTabbedPane tabbedPane;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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

		btnTimKiem.addActionListener(e -> timKiemDonDatPhongTheoSDT());

		panelTimKiem.add(lblSDT);
		panelTimKiem.add(txtSoDienThoai);
		panelTimKiem.add(btnTimKiem);
		return panelTimKiem;
	}

	private JTable createTableWithButtons(DefaultTableModel model) {
		JTable table = new JTable(model) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 5; // chỉ cột chức năng
			}
		};

		table.setRowHeight(40);

		table.getColumnModel().getColumn(5).setCellRenderer((tbl, value, isSelected, hasFocus, row, col) -> {
			JPanel panel = new JPanel(null);
			JButton btnNhan = new JButton("Nhận phòng");
			JButton btnHuy = new JButton("Hủy");

			btnNhan.setBounds(5, 5, 120, 30);
			btnNhan.setBackground(new Color(76, 175, 80));
			btnNhan.setForeground(Color.WHITE);

			btnHuy.setBounds(130, 5, 60, 30);
			btnHuy.setBackground(new Color(244, 67, 54));
			btnHuy.setForeground(Color.WHITE);

			panel.setPreferredSize(new Dimension(195, 40));
			panel.add(btnNhan);
			panel.add(btnHuy);

			return panel;
		});

		table.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
			JPanel panel = new JPanel(null);
			JButton btnNhan = new JButton("Nhận phòng");
			JButton btnHuy = new JButton("Hủy");

			{
				panel.setPreferredSize(new Dimension(160, 40));
				panel.setLayout(null);

				btnNhan.setBounds(5, 5, 100, 30);
				btnHuy.setBounds(110, 5, 45, 30);

				btnNhan.setFont(new Font("Arial", Font.BOLD, 12));
				btnHuy.setFont(new Font("Arial", Font.BOLD, 12));

				btnNhan.setBackground(new Color(76, 175, 80));
				btnNhan.setForeground(Color.WHITE);
				btnHuy.setBackground(new Color(244, 67, 54));
				btnHuy.setForeground(Color.WHITE);

				panel.add(btnNhan);
				panel.add(btnHuy);

				btnNhan.addActionListener(e -> {
					int row = table.getSelectedRow();
					int tabIndex = tabbedPane.getSelectedIndex();
					String maDon = (String) table.getValueAt(row, 0);
					DonDatPhong_Dao dao = new DonDatPhong_Dao();

				    capNhatVaReload(() -> {
				        if (tabIndex == 0) {
				            dao.setTrangThaiDonDatPhong(maDon, "Nhận phòng");
				            JOptionPane.showMessageDialog(null, "Đã nhận phòng cho đơn đã đặt.");
				        } else if (tabIndex == 1) {
				            dao.setTrangThaiDonDatPhong(maDon, "Đã đặt");
				            JOptionPane.showMessageDialog(null, "Đã xác nhận đặt phòng từ đơn tạm.");
				        }
				    });
				});

				btnHuy.addActionListener(e -> {
				    int row = table.getSelectedRow();
				    int tabIndex = tabbedPane.getSelectedIndex();
				    String maDon = (String) table.getValueAt(row, 0);
				    DonDatPhong_Dao dao = new DonDatPhong_Dao();

				    if (tabIndex == 0) {
				        DonDatPhong donDatPhong = dao.timDonTheoMa(maDon);
				        SwingUtilities.invokeLater(() -> {
				            new HuyPhong_GUI().taoDonHuyPhong(donDatPhong, () -> {
				                // Sau khi hủy thành công thì reload
				                timKiemDonDatPhongTheoSDT();
				            });
				        });
				    } else if (tabIndex == 1) {
				        int chon = JOptionPane.showConfirmDialog(null, "Bạn có chắc muốn hủy đơn tạm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
				        if (chon == JOptionPane.YES_OPTION) {
				            capNhatVaReload(() -> {
				                dao.setTrangThaiDonDatPhong(maDon, "Đã hủy");
				                JOptionPane.showMessageDialog(null, "Đã hủy đơn tạm thành công.");
				            });
				        }
				    }
				});
			}

			@Override
			public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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

		String[] columnNames = { "Mã đơn", "SĐT", "Tên KH", "Ngày nhận", "Ngày trả", "Chức năng" };

		modelDaDat = new DefaultTableModel(new Object[][] {}, columnNames);
		modelTamThoi = new DefaultTableModel(new Object[][] {}, columnNames);

		tableDaDat = createTableWithButtons(modelDaDat);
		tableTamThoi = createTableWithButtons(modelTamThoi);

		tabbedPane.add("Đơn đã đặt", new JScrollPane(tableDaDat));
		tabbedPane.add("Đơn tạm thời", new JScrollPane(tableTamThoi));

		return tabbedPane;
	}

	private void timKiemDonDatPhongTheoSDT() {
		String sdt = txtSoDienThoai.getText().trim();
		
		// Luôn clear bảng trước
		modelDaDat.setRowCount(0);
		modelTamThoi.setRowCount(0);

		if (!sdt.isEmpty()) {
			DonDatPhong_Dao dao = new DonDatPhong_Dao();
			ArrayList<DonDatPhong> danhSach = dao.getDonDatPhongTheoSDT(sdt);

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			for (DonDatPhong ddp : danhSach) {
				String[] row = {
					ddp.getMaDonDatPhong(),
					ddp.getKhachHang().getSdt(),
					ddp.getKhachHang().getHoTen(),
					ddp.getNgayNhanPhong().format(dtf),
					ddp.getNgayTraPhong().format(dtf),
					""
				};
				if ("Đã đặt".equals(ddp.getTrangThai()))
					modelDaDat.addRow(row);
				else if ("Đơn tạm".equals(ddp.getTrangThai()))
					modelTamThoi.addRow(row);
			}
		}
	}


	private void loadTatCaDonDatPhong() {
		DonDatPhong_Dao dao = new DonDatPhong_Dao();
		ArrayList<DonDatPhong> danhSach = dao.getAllDonDatPhong();
		modelDaDat.setRowCount(0);
		modelTamThoi.setRowCount(0);

		for (DonDatPhong ddp : danhSach) {
			String[] row = {
				ddp.getMaDonDatPhong(),
				ddp.getKhachHang().getSdt(),
				ddp.getKhachHang().getHoTen(),
		        ddp.getNgayNhanPhong().format(dtf),
		        ddp.getNgayTraPhong().format(dtf),
				""
			};
			if ("Đã đặt".equals(ddp.getTrangThai()))
				modelDaDat.addRow(row);
			else if ("Đơn tạm".equals(ddp.getTrangThai()))
				modelTamThoi.addRow(row);
		}
	}
	
	private void capNhatVaReload(Runnable capNhatTacVu) {
	    capNhatTacVu.run(); // chạy hành động cập nhật (hủy, nhận...)
	    timKiemDonDatPhongTheoSDT(); // gọi lại sau khi cập nhật
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			JFrame frame = new JFrame("Tìm đơn đặt phòng");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1250, 700);
			frame.setContentPane(new DSPhongDatTruoc_Gui());
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
