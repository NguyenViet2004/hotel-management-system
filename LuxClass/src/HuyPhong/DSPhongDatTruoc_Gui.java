package HuyPhong;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import dao.DonDatPhong_Dao;
import dao.KhachHang_Dao;
import entity.DonDatPhong;
import entity.KhachHang;

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
	private KhachHang_Dao khachhangdao;

	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

	public DSPhongDatTruoc_Gui() {
		khachhangdao = new KhachHang_Dao();
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
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		// Áp dụng căn giữa cho tất cả cột (trừ cột "Chức năng")
		for (int i = 0; i < table.getColumnCount(); i++) {
		    if (i != 6) { // Không căn giữa cột nút chức năng
		        table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		    }
		}

		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		// Cố định độ rộng cho cột "Chức năng"
		table.getColumnModel().getColumn(6).setMinWidth(200);
		table.getColumnModel().getColumn(6).setMaxWidth(200);
		table.getColumnModel().getColumn(6).setPreferredWidth(200);

		table.setRowHeight(40);

		table.getColumnModel().getColumn(6).setCellRenderer((tbl, value, isSelected, hasFocus, row, col) -> {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			panel.setPreferredSize(new Dimension(200, 40));

			JButton btnNhan = new JButton("Nhận phòng2");
			JButton btnHuy = new JButton("Hủy");

			btnNhan.setBackground(new Color(76, 175, 80));
			btnNhan.setForeground(Color.WHITE);

			btnHuy.setBackground(new Color(244, 67, 54));
			btnHuy.setForeground(Color.WHITE);

			panel.add(btnNhan);
			panel.add(btnHuy);
			return panel;
		});


		table.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JCheckBox()) {
			JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			JButton btnNhan = new JButton("Nhận phòng1");
			JButton btnHuy = new JButton("Hủy");

			{
				panel.setPreferredSize(new Dimension(200, 40));
				btnNhan.setFont(new Font("Arial", Font.BOLD, 12));
				btnHuy.setFont(new Font("Arial", Font.BOLD, 12));

				btnNhan.setBackground(new Color(76, 175, 80));
				btnNhan.setForeground(Color.WHITE);
				btnHuy.setBackground(new Color(244, 67, 54));
				btnHuy.setForeground(Color.WHITE);

				panel.add(btnNhan);
				panel.add(btnHuy);

				// Xử lý sự kiện
				btnNhan.addActionListener(e -> {
				    int row = table.getSelectedRow();
				    int tabIndex = tabbedPane.getSelectedIndex();
				    String maDon = (String) table.getValueAt(row, 0);
				    String sdt = (String) table.getValueAt(row, 1);

				    String regexCccd = "^(001|002|004|006|008|010|011|012|014|015|017|019|020|022|023|025|026|027|030|031|033|034|035|036|037|038|040|042|044|045|046|048|049|051|052|054|056|058|060|062|064|066|067|068|070|072|074|075|077|079|080|082|083|084|086|087|089|091|092|093|094|095|096)([0-9])([0-9]{2})([0-9]{6})$";

				    KhachHang kh = khachhangdao.timKhachHangTheoSoDienThoai(sdt);

				    if (kh.getSoCCCD() == null || kh.getSoCCCD().trim().isEmpty()) {
				        boolean daNhapDung = false;
				        while (!daNhapDung) {
				            String nhapCCCD = JOptionPane.showInputDialog(null,
				                "Khách hàng chưa có CCCD.\nVui lòng nhập CCCD (12 số, đúng định dạng):",
				                "Nhập CCCD", JOptionPane.WARNING_MESSAGE);

				            if (nhapCCCD == null || nhapCCCD.trim().isEmpty()) {
				                JOptionPane.showMessageDialog(null,
				                    "Không thể nhận phòng khi chưa có CCCD!",
				                    "Lỗi", JOptionPane.ERROR_MESSAGE);
				                return;
				            }

				            nhapCCCD = nhapCCCD.trim();
				            if (!nhapCCCD.matches(regexCccd)) {
				                JOptionPane.showMessageDialog(null,
				                    "CCCD không đúng định dạng! Vui lòng nhập lại.\n(12 số, bắt đầu bằng mã tỉnh hợp lệ)",
				                    "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
				            } else {
				                // Hợp lệ → cập nhật
				                kh.setSoCCCD(nhapCCCD);
				                khachhangdao.suaCCCD(kh);
				                daNhapDung = true;
				            }
				        }
				    }

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

		String[] columnNames = { "Mã đơn", "SĐT", "Tên KH", "Ngày nhận", "Ngày trả","tiền cọc", "Chức năng" };

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

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");

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
			System.out.println("Số đơn 'Đã đặt': " + modelDaDat.getRowCount());
			System.out.println("Số đơn 'Đơn tạm': " + modelTamThoi.getRowCount());


		}
	}


	private void loadTatCaDonDatPhong() {
		DonDatPhong_Dao dao = new DonDatPhong_Dao();
		ArrayList<DonDatPhong> danhSach = dao.getAllDonDatPhong();
		System.out.println("Tổng số đơn đặt phòng: " + danhSach.size());
		modelDaDat.setRowCount(0);
		modelTamThoi.setRowCount(0);

		for (DonDatPhong ddp : danhSach) {
			String[] row = {
				ddp.getMaDonDatPhong(),
				ddp.getKhachHang().getSdt(),
				ddp.getKhachHang().getHoTen(),
		        ddp.getNgayNhanPhong().format(dtf),
		        ddp.getNgayTraPhong().format(dtf),
		        String.valueOf(ddp.getTienCoc()),
		        ""
			};
			System.out.println(ddp.getThoiGianCoc());
			
			if ("Đã đặt".equals(ddp.getTrangThai())) {
				modelDaDat.addRow(row);}
			else if ("Đơn tạm".equals(ddp.getTrangThai())) {
				modelTamThoi.addRow(row);
		}
		System.out.println("Số đơn 'Đã đặt': " + modelDaDat.getRowCount());
		System.out.println("Số đơn 'Đơn tạm': " + modelTamThoi.getRowCount());}
	}
	
	private void capNhatVaReload(Runnable capNhatTacVu) {
	    capNhatTacVu.run();              // chạy cập nhật
	    loadTatCaDonDatPhong();         // reload toàn bộ dữ liệu
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
