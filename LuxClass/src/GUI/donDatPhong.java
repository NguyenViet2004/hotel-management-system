package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import com.google.zxing.*;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import TraPhong_CuaXien.inHoaDon;
import TraPhong_CuaXien.chiPhiPhatSinh_Dialog.ChiPhiPhatSinhListener;
import dao_CuaXien.DichVu_DAO;
import dao_CuaXien.DonDatPhong_DAO;
import dao_CuaXien.KhachHang_DAO;
import dao_CuaXien.LoaiPhong_DAO;
import dao_CuaXien.Phong_DAO;
import dao_CuaXien.phieuDichVu_DAO;
import entity_CuaXien.DichVu;
import entity_CuaXien.DonDatPhong;
import entity_CuaXien.KhachHang;
import entity_CuaXien.LoaiPhong;
import entity_CuaXien.PhieuDichVu;
import entity_CuaXien.Phong;

import java.util.Hashtable;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

public class donDatPhong extends JFrame implements chiPhiPhatSinh_Dialog.ChiPhiPhatSinhListener {
	private JTextField maDon;
	private JTextField hoVaTen;
	private JTextField ngayNhan;
	private JTable table_phongTra;
	private JTable table_dichVu;
	private int frameWidth;
	private int frameHeight;
	private JTextField ngayTra;
	private JTextField soLuongKhach;
	private JTextField tongTienSDDV;
	private JTable table_TinhChiPhiPhatSinh;
	private DonDatPhong currentDonDatPhong;
	private JPanel hoaDonCT;
	private JPanel body;
	private JTextField tongTienPS;
	private List<Object[]> danhSachPhongDuocChon = new ArrayList<>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DonDatPhong ddp = new DonDatPhong();
					donDatPhong window = new donDatPhong(ddp);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public donDatPhong(DonDatPhong ddp) {
		this.currentDonDatPhong = ddp;
		initialize(currentDonDatPhong);
		themSuKienCheckbox();
		themSuKienClickTableChiPhi();
	}

	private void initialize(DonDatPhong ddp) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd = ge.getDefaultScreenDevice();
		GraphicsConfiguration gc = gd.getDefaultConfiguration();

		Rectangle screenBounds = gc.getBounds();
		Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

		int screenWidth = screenBounds.width - (screenInsets.left + screenInsets.right);
		int screenHeight = screenBounds.height - (screenInsets.top + screenInsets.bottom);

		frameWidth = (int) (screenWidth);
		frameHeight = (int) (screenHeight);
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/HinhAnhGiaoDienChinh/logo.png"));
		getContentPane().setBackground(new Color(226, 219, 219));
		setBounds(100, 100, frameWidth, frameHeight);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		JPanel Header = createHeaderPanel();
		getContentPane().add(Header);

		body = createBodyPanel();
		getContentPane().add(body);

		hienThiThongTin(ddp);
		hienThiDanhSachPhong(ddp);
		loadTableDichVu(ddp.getMaDonDatPhong());

		hoaDonCT = hoaDonCT();
		hoaDonCT.setVisible(false);
		getContentPane().add(hoaDonCT);

	}

	private JPanel createHeaderPanel() {
		JPanel Header = new JPanel();
		Header.setBounds(0, 0, frameWidth, (int) (frameHeight * 0.12));
		Header.setBackground(new Color(255, 255, 255));
		Header.setLayout(null);
		Header.setBorder(new LineBorder(Color.black));

		JLabel lblLoGo = new JLabel("");
		ImageIcon originalIcon = new ImageIcon("img/HinhAnhGiaoDienChinh/logo.png");
		Image image = originalIcon.getImage().getScaledInstance(88, 88, Image.SCALE_SMOOTH);
		ImageIcon logoIcon = new ImageIcon(image);
		lblLoGo.setIcon(logoIcon);
		lblLoGo.setBounds(5, 5, 88, 88);
		Header.add(lblLoGo);

		JButton undo = new JButton("");
		undo.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/undo.png"));
		undo.setBounds(103, 55, 45, 38);
		undo.setContentAreaFilled(false);
		undo.setBorderPainted(false);
		undo.setFocusPainted(false);
		Header.add(undo);

		JButton Home = new JButton("");
		Home.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/home.png"));
		Home.setBounds(158, 55, 37, 38);
		Home.setContentAreaFilled(false);
		Home.setBorderPainted(false);
		Home.setFocusPainted(false);
		Header.add(Home);

		JLabel lblNewLabel_7 = new JLabel("Trả phòng");
		lblNewLabel_7.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblNewLabel_7.setBounds(102, 10, 115, 35);
		Header.add(lblNewLabel_7);

		JButton help = new JButton("");
		help.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/help.png"));
		help.setBounds(1348, 36, 37, 32);
		help.setContentAreaFilled(false);
		help.setBorderPainted(false);
		help.setFocusPainted(false);
		Header.add(help);

		JLabel lblNewLabel_8 = new JLabel("New label");
		lblNewLabel_8.setBounds(1464, 20, 45, 13);
		Header.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("New label");
		lblNewLabel_9.setBounds(1464, 62, 45, 13);
		Header.add(lblNewLabel_9);

		return Header;
	}

	private JPanel createBodyPanel() {
		JPanel body = new JPanel();
		body.setBackground(new Color(192, 192, 192));
		body.setBounds(0, (int) (frameHeight * 0.12), frameWidth, (int) (frameHeight * 0.88));
		body.setLayout(null);

		JPanel Traipanel = createTraiPanel();
		body.add(Traipanel);

		JPanel Phaipanel = createPhaiPanel();
		body.add(Phaipanel);

		return body;
	}

	private JPanel createTraiPanel() {
		JPanel Traipanel = new JPanel();
		Traipanel.setBackground(new Color(255, 255, 255));
		Traipanel.setBounds(10, 10, (int) (frameWidth * 0.5), (int) (frameHeight * 0.83));
		Traipanel.setLayout(null);

		JLabel TTDonDat = new JLabel("Thông tin đơn đặt phòng");
		TTDonDat.setBounds(186, 10, 324, 52);
		TTDonDat.setFont(new Font("Times New Roman", Font.BOLD, 30));
		Traipanel.add(TTDonDat);

		JLabel lblNewLabel = new JLabel("Mã đơn đặt phòng:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(50, 72, 194, 32);
		Traipanel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Họ tên khách hàng:");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1.setBounds(50, 118, 175, 24);
		Traipanel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Ngày nhận phòng:");
		lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_2.setBounds(50, 159, 175, 24);
		Traipanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("Ngày trả phòng:");
		lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_3.setBounds(50, 201, 159, 24);
		Traipanel.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Số lượng khách:");
		lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_4.setBounds(50, 243, 159, 24);
		Traipanel.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Các dịch vụ đã sử dụng");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblNewLabel_5.setBounds(205, 284, 270, 32);
		Traipanel.add(lblNewLabel_5);

		maDon = new JTextField();
		maDon.setEditable(false);
		maDon.setFocusable(false);
		maDon.setFont(new Font("Times New Roman", Font.BOLD, 20));
		maDon.setBounds(275, 75, 404, 32);
		Traipanel.add(maDon);
		maDon.setColumns(10);

		hoVaTen = new JTextField();
		hoVaTen.setFont(new Font("Times New Roman", Font.BOLD, 20));
		hoVaTen.setEditable(false);
		hoVaTen.setFocusable(false);
		hoVaTen.setColumns(10);
		hoVaTen.setBounds(275, 117, 404, 32);
		Traipanel.add(hoVaTen);

		ngayNhan = new JTextField();
		ngayNhan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ngayNhan.setEditable(false);
		ngayNhan.setFocusable(false);
		ngayNhan.setColumns(10);
		ngayNhan.setBounds(275, 158, 404, 32);
		Traipanel.add(ngayNhan);

		ngayTra = new JTextField();
		ngayTra.setFont(new Font("Times New Roman", Font.BOLD, 20));
		ngayTra.setEditable(false);
		ngayTra.setFocusable(false);
		ngayTra.setColumns(10);
		ngayTra.setBounds(275, 200, 404, 32);
		Traipanel.add(ngayTra);

		soLuongKhach = new JTextField();
		soLuongKhach.setFont(new Font("Times New Roman", Font.BOLD, 20));
		soLuongKhach.setEditable(false);
		soLuongKhach.setFocusable(false);
		soLuongKhach.setColumns(10);
		soLuongKhach.setBounds(275, 242, 404, 32);
		Traipanel.add(soLuongKhach);

		JScrollPane dichVu = createDichVuScrollPane();
		Traipanel.add(dichVu);

		JLabel lblNewLabel_11 = new JLabel("Tổng tiền sử dụng dịch vụ:");
		lblNewLabel_11.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_11.setBounds(71, 535, 250, 32);
		Traipanel.add(lblNewLabel_11);

		tongTienSDDV = new JTextField();
		tongTienSDDV.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tongTienSDDV.setEditable(false);
		tongTienSDDV.setFocusable(false);
		tongTienSDDV.setBounds(351, 537, 288, 28);
		Traipanel.add(tongTienSDDV);
		tongTienSDDV.setColumns(10);

		return Traipanel;
	}

	private JScrollPane createDichVuScrollPane() {
		JScrollPane dichVu = new JScrollPane();
		dichVu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		dichVu.setBorder(null);
		dichVu.setBackground(new Color(255, 255, 255));
		dichVu.setBounds(25, 345, 707, 159);

		table_dichVu = new JTable();
		DefaultTableModel model_dv = new DefaultTableModel(new Object[][] {},
				new String[] { "Mã phiếu dịch vụ", "Loại dịch vụ", "Ngày lập phiếu", "Thành tiền", "" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, true };

			@Override
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		table_dichVu.setModel(model_dv);
		table_dichVu.setShowVerticalLines(false);
		table_dichVu.setShowHorizontalLines(false);
		table_dichVu.setShowGrid(false);
		table_dichVu.setRowHeight(40);
		table_dichVu.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		table_dichVu.setFillsViewportHeight(true);
		table_dichVu.setBorder(null);
		JTableHeader header_dv = table_dichVu.getTableHeader();
		header_dv.setBackground(new Color(220, 255, 220));
		header_dv.setPreferredSize(new Dimension(header_dv.getWidth(), 30));
		header_dv.setFont(new Font("Times New Roman", Font.BOLD, 16));
		header_dv.setBorder(null);

		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				label.setHorizontalAlignment(JLabel.CENTER);
				label.setFont(new Font("Times New Roman", Font.BOLD, 16));
				label.setBackground(new Color(220, 255, 220));
				label.setBorder(BorderFactory.createEmptyBorder());
				return label;
			}
		};
		header_dv.setDefaultRenderer(headerRenderer);

		table_dichVu.setBackground(Color.WHITE);

		DefaultTableCellRenderer centerRenderer_dv = new DefaultTableCellRenderer();
		centerRenderer_dv.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 0; i < table_dichVu.getColumnCount() - 1; i++) {
			table_dichVu.getColumnModel().getColumn(i).setCellRenderer(centerRenderer_dv);
		}

		ButtonRenderer buttonRenderer = new ButtonRenderer();
		ButtonEditor buttonEditor = new ButtonEditor(table_dichVu);

		int buttonColumnIndex = table_dichVu.getColumnCount() - 1;
		table_dichVu.getColumnModel().getColumn(buttonColumnIndex).setCellRenderer(buttonRenderer);
		table_dichVu.getColumnModel().getColumn(buttonColumnIndex).setCellEditor(buttonEditor);

		ButtonPanel buttonPanelEditor = buttonEditor.getButtonPanel();
		if (buttonPanelEditor != null) {
			buttonPanelEditor.getDeleteButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = table_dichVu.getSelectedRow();
					if (row >= 0 && row <= table_dichVu.getRowCount()) {
						String maPhieuDichVu = (String) table_dichVu.getValueAt(row, 0); // Lấy mã phiếu dịch vụ

						int choice = JOptionPane.showConfirmDialog(null, "Bạn có muốn xóa dịch vụ này không?",
								"Xác nhận xóa", JOptionPane.YES_NO_OPTION);

						if (choice == JOptionPane.YES_OPTION) {
							phieuDichVu_DAO pdv = new phieuDichVu_DAO();
							boolean aBoolean = false; // Khởi tạo aBoolean

							try {
								aBoolean = pdv.xoaPhieuDichVu(maPhieuDichVu);
								if (aBoolean) {
									((DefaultTableModel) table_dichVu.getModel()).removeRow(row);

									table_dichVu.clearSelection();
									table_dichVu.revalidate();
									table_dichVu.repaint();

									if (table_dichVu.getRowCount() == 0) {
										table_dichVu.clearSelection();
										table_dichVu.revalidate();
										table_dichVu.repaint();
									}
									capNhatTongTien(); // Cập nhật tổng tiền sau khi xóa thành công
								} else {
									JOptionPane.showMessageDialog(null, "Xóa dịch vụ thất bại. Vui lòng kiểm tra lại.");
								}
							} catch (SQLException e1) {
								e1.printStackTrace();
								JOptionPane.showMessageDialog(null,
										"Lỗi cơ sở dữ liệu khi xóa dịch vụ: " + e1.getMessage());
							}

						} else {
							JOptionPane.showMessageDialog(null, "Hủy xóa dịch vụ.");
						}
					}
				}
			});

			buttonPanelEditor.getDetailButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int row = table_dichVu.getSelectedRow();
					if (row != -1) {
						String maPhieu = table_dichVu.getValueAt(row, 0).toString();
						String loaiDV = table_dichVu.getValueAt(row, 1).toString();
						String ngayLapPhieu = table_dichVu.getValueAt(row, 2).toString();

						phieuDichVu_Dialog dialog = new phieuDichVu_Dialog(maPhieu, loaiDV, ngayLapPhieu);
						dialog.setLocationRelativeTo(null);
						dialog.hienThiChiTietDichVu(maPhieu, loaiDV);
						dialog.setVisible(true);
					}
				}
			});
		}

		dichVu.setViewportView(table_dichVu);
		return dichVu;
	}

	private JPanel createPhaiPanel() {
		JPanel Phaipanel = new JPanel();
		Phaipanel.setBackground(new Color(255, 255, 255));
		Phaipanel.setBounds(762, 10, (int) (frameWidth * 0.5), (int) (frameHeight * 0.83));
		Phaipanel.setLayout(null);

		JLabel lblNewLabel_6 = new JLabel("Chọn phòng muốn trả");
		lblNewLabel_6.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_6.setBounds(243, 10, 260, 37);
		Phaipanel.add(lblNewLabel_6);

		JButton tiepTuc = new JButton("Tiếp tục");
		tiepTuc.setBackground(new Color(0, 255, 64));
		tiepTuc.setFont(new Font("Times New Roman", Font.BOLD, 25));
		tiepTuc.setBounds(597, 607, 154, 47);
		Phaipanel.add(tiepTuc);
		tiepTuc.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				layDanhSachPhongDuocChon();
				body.setVisible(false);
				capNhatHoaDonCT();
				hoaDonCT.setVisible(true);
				capNhatTongTienThanhToan();
			}
		});
		JPanel scrollPanel = createPhongTraScrollPane();
		Phaipanel.add(scrollPanel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(34, 349, 693, 162);
		scrollPane.setBorder(null);
		scrollPane.setBackground(null);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setOpaque(false);
		Phaipanel.add(scrollPane);

		table_TinhChiPhiPhatSinh = new JTable();
		table_TinhChiPhiPhatSinh.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Mã phòng",
				"Loại phòng", "Số giờ thêm", "Chi phí phụ thu", "Chi phí hư hỏng", "Thành tiền" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table_TinhChiPhiPhatSinh.setRowHeight(30);
		table_TinhChiPhiPhatSinh.setFont(new Font("Times New Roman", Font.BOLD, 14));
		scrollPane.setViewportView(table_TinhChiPhiPhatSinh);
		table_TinhChiPhiPhatSinh.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 5) { // Cột "Thành tiền" (index 5)
					capNhatTongTienPhatSinh();
				}
			}
		});

		JLabel lblNewLabel_6_1 = new JLabel("Chi phí phát sinh");
		lblNewLabel_6_1.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblNewLabel_6_1.setBounds(257, 293, 260, 37);
		Phaipanel.add(lblNewLabel_6_1);

		tongTienPS = new JTextField();
		tongTienPS.setText((String) null);
		tongTienPS.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tongTienPS.setFocusable(false);
		tongTienPS.setEditable(false);
		tongTienPS.setColumns(10);
		tongTienPS.setBounds(407, 537, 288, 28);
		Phaipanel.add(tongTienPS);

		JLabel lblNewLabel_11 = new JLabel("Tổng chi phi phát sinh:");
		lblNewLabel_11.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_11.setBounds(66, 535, 250, 32);
		Phaipanel.add(lblNewLabel_11);
		JTableHeader header1 = table_TinhChiPhiPhatSinh.getTableHeader();
		header1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		header1.setForeground(Color.black);
		header1.setBackground(new Color(220, 255, 220));

		return Phaipanel;
	}

	private JPanel createPhongTraScrollPane() {
		table_phongTra = new JTable();
		table_phongTra.setShowVerticalLines(false);
		table_phongTra.setShowHorizontalLines(false);
		table_phongTra.setBackground(new Color(255, 255, 255));
		table_phongTra.setFillsViewportHeight(true);
		table_phongTra.setRowHeight(30);
		table_phongTra.setBorder(null);
		table_phongTra.setShowGrid(false);
		Object[][] data = {};
		String[] columnNames = { "", "Mã phòng", "Loại phòng", "Thời gian", "Đơn giá", "Thành tiền" };
		DefaultTableModel model = new DefaultTableModel(data, columnNames) {
			@Override
			public Class<?> getColumnClass(int column) {
				return (column == 0) ? Boolean.class : String.class;
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		};
		table_phongTra.setModel(model);

		table_phongTra.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JPanel panel = new JPanel();
				panel.setBackground(new Color(220, 255, 220));
				panel.setLayout(new GridBagLayout());

				if (column == 0) {
					JCheckBox checkBox = new JCheckBox();
					checkBox.setBackground(new Color(220, 255, 220));
					checkBox.setHorizontalAlignment(JLabel.CENTER);
					panel.add(checkBox);
				} else {
					JLabel label = new JLabel(value.toString());
					label.setHorizontalAlignment(JLabel.CENTER);
					panel.add(label);
				}

				return panel;
			}
		});
		JTableHeader header = table_phongTra.getTableHeader();
		header.setBackground(new Color(220, 255, 220));
		header.setPreferredSize(new Dimension(header.getWidth(), 40));

		JCheckBox selectAll = new JCheckBox();
		selectAll.setBackground(new Color(220, 255, 220));
		selectAll.setHorizontalAlignment(JLabel.CENTER);

		TableColumnModel columnModel = table_phongTra.getColumnModel();
		columnModel.getColumn(0).setHeaderRenderer(new CheckBoxHeader(selectAll));
		columnModel.getColumn(0).setPreferredWidth(20);
		selectAll.addActionListener(e -> {
			boolean checked = selectAll.isSelected();
			for (int i = 0; i < table_phongTra.getRowCount(); i++) {
				table_phongTra.setValueAt(checked, i, 0);
			}
		});

		header.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = columnModel.getColumnIndexAtX(e.getX());
				if (col == 0) {
					selectAll.doClick();
				}
			}
		});

		table_phongTra.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				c.setBackground(new Color(240, 240, 240));
				return c;
			}
		});

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment(JLabel.CENTER);
		for (int i = 1; i < table_phongTra.getColumnCount(); i++) {
			table_phongTra.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
		}

		table_phongTra.setFont(new Font("Times New Roman", Font.PLAIN, 17));
		header.setFont(new Font("Times New Roman", Font.BOLD, 20));

		JScrollPane scrollPane = new JScrollPane(table_phongTra);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(0, 0, 693, 226);
		scrollPane.setBackground(new Color(0, 0, 0, 0));
		scrollPane.setBorder(null);
		scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
		scrollPane.getViewport().setBorder(null);

		JPanel scrollPanel = new JPanel();
		scrollPanel.setBounds(34, 57, 693, 226);
		scrollPanel.setLayout(null);
		scrollPanel.setBackground(new Color(0, 0, 0, 0));
		scrollPanel.setBorder(null);
		scrollPanel.add(scrollPane);

		return scrollPanel;
	}

	class CheckBoxHeader extends JPanel implements TableCellRenderer {
		JCheckBox checkBox;

		public CheckBoxHeader(JCheckBox checkBox) {
			super(new GridBagLayout());
			this.checkBox = checkBox;
			this.setBackground(new Color(220, 255, 220));
			add(checkBox);
			checkBox.setBackground(new Color(220, 255, 220));
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			return this;
		}
	}

	class ButtonPanel extends JPanel {
		public JButton deleteButton;
		public JButton detailButton;

		public ButtonPanel() {
			setLayout(new FlowLayout(FlowLayout.CENTER, 1, 10));

			deleteButton = new JButton("");
			deleteButton.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/xoa.png"));
			deleteButton.setContentAreaFilled(false);
			deleteButton.setBorderPainted(false);
			add(deleteButton);

			detailButton = new JButton();
			detailButton.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/chitiet.png"));
			detailButton.setContentAreaFilled(false);
			detailButton.setBorderPainted(false);
			add(detailButton);
		}

		public JButton getDeleteButton() {
			return deleteButton;
		}

		public JButton getDetailButton() {
			return detailButton;
		}
	}

	class ButtonRenderer extends JPanel implements TableCellRenderer {
		private ButtonPanel panel;

		public ButtonRenderer() {
			setOpaque(true);
			panel = new ButtonPanel();
			setLayout(new BorderLayout());
			add(panel, BorderLayout.CENTER);
		}

		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			if (isSelected) {
				panel.setBackground(table.getSelectionBackground());
			} else {
				panel.setBackground(table.getBackground());
			}
			return panel;
		}
	}

	class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
		private ButtonPanel panel;
		private JTable table;

		public ButtonEditor(JTable table) {
			this.table = table;
			panel = new ButtonPanel();
		}

		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row,
				int column) {
			panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
			return panel;
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		public ButtonPanel getButtonPanel() {
			return panel;
		}
	}

	private void hienThiThongTin(DonDatPhong ddp) {
		maDon.setText(ddp.getMaDonDatPhong());
		hoVaTen.setText(ddp.getKhachHang().getHoTen());
		ngayNhan.setText(ddp.getNgayNhanPhong().toString());
		ngayTra.setText(ddp.getNgayTraPhong().toString());
		soLuongKhach.setText(String.valueOf(ddp.getSoKhach()));
	}

	public void hienThiDanhSachPhong(DonDatPhong ddp) {
		String maDonDatPhong = ddp.getMaDonDatPhong();
		Phong_DAO phongDAO = new Phong_DAO();
		List<Phong> danhSachPhong = phongDAO.getPhongTheoMaDonDatPhong(maDonDatPhong);
		DefaultTableModel model = (DefaultTableModel) table_phongTra.getModel();
		model.setRowCount(0);
		LocalDateTime ngayNhanPhong = ddp.getNgayNhanPhong();
		LocalDateTime ngayTraPhong = ddp.getNgayTraPhong();
		for (Phong p : danhSachPhong) {
			String maPhong = p.getSoPhong();
			String loaiPhong = p.getLoaiPhong().getTenLoai();
			double donGia = 0.0;
			double thanhTien = 0.0;
			String thoiGian = "";
			String hinhThuc = ddp.getLoaiDon(); 
			switch (hinhThuc) {
			case "Theo giờ":
				long soGio = ChronoUnit.HOURS.between(ngayNhanPhong, ngayTraPhong);
				if (soGio == 0)
					soGio = 1; // Tối thiểu 1 giờ
				donGia = p.getLoaiPhong().getGiaTheoGio();
				thanhTien = donGia * soGio;
				thoiGian = soGio + " giờ";
				break;
			case "Theo đêm":
				// Giả sử giá qua đêm cố định
				donGia = p.getLoaiPhong().getGiaTheoDem();
				thanhTien = donGia;
				thoiGian = "Qua đêm";
				break;
			case "Theo ngày":
			default:
				long soNgay = ChronoUnit.DAYS.between(ngayNhanPhong.toLocalDate(), ngayTraPhong.toLocalDate());
				if (soNgay == 0)
					soNgay = 1; // Tối thiểu 1 ngày
				donGia = p.getLoaiPhong().getGiaTheoNgay();
				thanhTien = donGia * soNgay;
				thoiGian = soNgay + " ngày";
				break;
			}
			Object[] rowData = { false, maPhong, loaiPhong, thoiGian, String.format("%.0f", donGia),
					String.format("%.0f", thanhTien) };
			model.addRow(rowData);
		}
	}

	private phieuDichVu_DAO phieuDichVu_DAO = new phieuDichVu_DAO();

	public void loadTableDichVu(String maDonDatPhong) {
		DefaultTableModel model = (DefaultTableModel) table_dichVu.getModel();
		model.setRowCount(0); // Xoá dữ liệu cũ

		List<Object[]> danhSach = phieuDichVu_DAO.getLoaiDichVuVaThanhTienTheoMaDonDatPhong(maDonDatPhong);
		for (Object[] row : danhSach) {
			String maPhieuDV = (String) row[0];
			String tenLoai = (String) row[1];
			String ngayLapPhieu = (String) row[2];
			double thanhTien = (Double) row[3];

			Object[] rowData = { maPhieuDV, tenLoai, ngayLapPhieu, thanhTien };
			model.addRow(rowData);
		}
		capNhatTongTien();
	}

	public void capNhatTongTien() {
		DefaultTableModel model = (DefaultTableModel) table_dichVu.getModel();
		double tong = 0;

		for (int i = 0; i < model.getRowCount(); i++) {
			Object giaObj = model.getValueAt(i, 3); // Cột "Thành tiền"
			if (giaObj != null) {
				try {
					tong += Double.parseDouble(giaObj.toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		tongTienSDDV.setText(String.format("%.0f", tong));
	}

	private void themSuKienCheckbox() {
		table_phongTra.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 0) {
					capNhatTableTinhChiPhiPhatSinh();
				}
			}
		});
	}

	private void capNhatTableTinhChiPhiPhatSinh() {
		DefaultTableModel modelPhongTra = (DefaultTableModel) table_phongTra.getModel();
		DefaultTableModel modelChiPhi = (DefaultTableModel) table_TinhChiPhiPhatSinh.getModel();
		modelChiPhi.setRowCount(0); // Xóa dữ liệu cũ
		for (int i = 0; i < modelPhongTra.getRowCount(); i++) {

			Boolean isChecked = (Boolean) modelPhongTra.getValueAt(i, 0);

			if (isChecked != null && isChecked) {
				String maPhong = (String) modelPhongTra.getValueAt(i, 1);
				String loaiPhong = (String) modelPhongTra.getValueAt(i, 2);
				modelChiPhi.addRow(new Object[] { maPhong, loaiPhong, "", "", "", "" });
			}
		}
		capNhatTongTienPhatSinh();
	}

	private void capNhatTongTienPhatSinh() {
		DefaultTableModel model = (DefaultTableModel) table_TinhChiPhiPhatSinh.getModel();
		double tong = 0;

		for (int i = 0; i < model.getRowCount(); i++) {
			Object thanhTienObj = model.getValueAt(i, 5); // Cột "Thành tiền" (index 5)
			if (thanhTienObj == null || thanhTienObj.toString().isEmpty()) {
				// Nếu ô trống hoặc null, gán giá trị 0
				model.setValueAt(0.0, i, 5);
				tong += 0.0;
			} else {
				try {
					tong += Double.parseDouble(thanhTienObj.toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
					// Nếu có lỗi chuyển đổi, gán giá trị 0 và in lỗi
					model.setValueAt(0.0, i, 5);
					tong += 0.0;
				}
			}
		}
		tongTienPS.setText(String.format("%.0f", tong));
	}

	private void themSuKienClickTableChiPhi() {
		table_TinhChiPhiPhatSinh.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table_TinhChiPhiPhatSinh.getSelectedRow();
				if (row >= 0) {
					// Lấy mã phòng từ dòng được click
					String maPhong = (String) table_TinhChiPhiPhatSinh.getValueAt(row, 0);
					LoaiPhong_DAO loaip = new LoaiPhong_DAO();
					LoaiPhong loai = loaip.getLoaiPhongBySoPhong(maPhong);
					// Truyền maDonDatPhong và số thứ tự dòng vào dialog
					chiPhiPhatSinh_Dialog dialog = new chiPhiPhatSinh_Dialog(currentDonDatPhong.getMaDonDatPhong(), row,
							maPhong, loai.getTenLoai());

					dialog.setChiPhiPhatSinhListener(donDatPhong.this); // Thiết lập donDatPhong làm listener
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setUndecorated(true);
					dialog.setLocationRelativeTo(null);
					dialog.setSize(833, 714);
					dialog.setResizable(false);
					dialog.setVisible(true);
				}
			}
		});
	}

	@Override
	public void onChiPhiPhatSinhUpdated(int row, String maChiPhi, double chiPhiThietBiHong, int soGioThem,
			double chiPhiPhuThu, double tongChiPhi) {
		// TODO Auto-generated method stub
		table_TinhChiPhiPhatSinh.setValueAt(soGioThem, row, 2);
		table_TinhChiPhiPhatSinh.setValueAt(String.format("%.0f", chiPhiPhuThu), row, 3);
		table_TinhChiPhiPhatSinh.setValueAt(String.format("%.0f", chiPhiThietBiHong), row, 4);
		table_TinhChiPhiPhatSinh.setValueAt(String.format("%.0f", tongChiPhi), row, 5);
	}

	private JTextField tongChiPhi1;
	private JTextField tienCoc1;
	private JTextField thanhTien1;
	private JTextField tienKhachDua1;
	private JTextField tienThoi1;
	private JTextField tenKhach1;
	private JTextField dienThoai1;
	private JTextField ngayNhanPhong1;
	private JTextField ngayTraPhong1;
	private JTextField tongTienPhongTable1;
	private JTextField tongTienSuDungDichVu1;
	private JTextField tongChiPhiPhatSinh1;
	private JTextField maHoaDon1;
	private JTextField tienThoiBangChu1;
	private JTable table1;

	public JPanel hoaDonCT() {
		String mahd = maDon.getText();
		String hotenString = hoVaTen.getText();
		String dayNhanString = ngayNhan.getText();
		String dayTraString = ngayTra.getText();
		String chiPhiPhatString = tongTienPS.getText();
		String phiDV = tongTienSDDV.getText();

		KhachHang_DAO khachHang_DAO = new KhachHang_DAO();
		KhachHang khachHang = khachHang_DAO.layKhachHangTheoMaDonDatPhong(mahd);
		String sdt = khachHang.getSdt();

		JPanel panel = new JPanel();
		panel.setBounds(0, 96, 1536, 731);
		panel.setBackground(new Color(192, 192, 192));
		panel.setLayout(null);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(10, 10, 864, 673);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Hóa đơn thanh toán chi tiết");
		lblNewLabel_1.setBounds(222, 48, 391, 44);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 30));
		panel_1.add(lblNewLabel_1);

		JLabel lblKhchHng = new JLabel("Khách hàng:");
		lblKhchHng.setBounds(179, 102, 195, 28);
		lblKhchHng.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblKhchHng);

		JLabel lblinThoi = new JLabel("Điện thoại:");
		lblinThoi.setBounds(179, 145, 195, 28);
		lblinThoi.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblinThoi);

		JLabel lblNgyNhnPhng = new JLabel("Ngày nhận phòng:");
		lblNgyNhnPhng.setBounds(179, 188, 195, 28);
		lblNgyNhnPhng.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblNgyNhnPhng);

		JLabel lblNgyTrPhng = new JLabel("Ngày trả phòng:");
		lblNgyTrPhng.setBounds(179, 231, 195, 28);
		lblNgyTrPhng.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblNgyTrPhng);

		JLabel lblITinPhng = new JLabel("I Tiền phòng:");
		lblITinPhng.setBounds(27, 247, 195, 44);
		lblITinPhng.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblITinPhng);

		JLabel lblIiTinS = new JLabel("II Tiền sử dụng dịch vụ:");
		lblIiTinS.setBounds(27, 472, 225, 44);
		lblIiTinS.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblIiTinS);

		JLabel lblIiiTinChi = new JLabel("III Tiền chi phí phát sinh:");
		lblIiiTinChi.setBounds(27, 553, 225, 44);
		lblIiiTinChi.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblIiiTinChi);

		JLabel lblTngTinPhng = new JLabel("Tổng tiền phòng:");
		lblTngTinPhng.setBounds(320, 434, 195, 44);
		lblTngTinPhng.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblTngTinPhng);

		JLabel lblTngTinS = new JLabel("Tổng tiền sử dụng dịch vụ:");
		lblTngTinS.setBounds(243, 508, 248, 44);
		lblTngTinS.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblTngTinS);

		JLabel lblNgyTrPhng_2_1 = new JLabel("Tổng tiền chi phí phát sinh:");
		lblNgyTrPhng_2_1.setBounds(238, 598, 235, 44);
		lblNgyTrPhng_2_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		panel_1.add(lblNgyTrPhng_2_1);

		JScrollPane scrollPane_11 = new JScrollPane();
		scrollPane_11.setBorder(null); // Tắt viền
		scrollPane_11.setBackground(null); // Tắt màu nền
		scrollPane_11.getViewport().setOpaque(false); // Làm trong suốt phần viewport
		scrollPane_11.setBounds(37, 280, 792, 158);
		panel_1.add(scrollPane_11);

		table1 = new JTable();
		table1.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		table1.setModel(new DefaultTableModel(new Object[][] { { null, null, null, null, null }, },
				new String[] { "M\u00E3 ph\u00F2ng", "Lo\u1EA1i ph\u00F2ng", "Th\u1EDDi gian", "\u0110\u01A1n gi\u00E1",
						"Th\u00E0nh ti\u1EC1n" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table1.setRowHeight(25);

		// Điều chỉnh kích thước header
		JTableHeader header = table1.getTableHeader();
		header.setFont(new Font("Times New Roman", Font.BOLD, 18));
		header.setPreferredSize(new Dimension(header.getWidth(), 20));
		scrollPane_11.setViewportView(table1);

		DefaultTableModel model_HoaDon = (DefaultTableModel) table1.getModel();
		model_HoaDon.setRowCount(0);

		for (Object[] row : danhSachPhongDuocChon) {
			model_HoaDon.addRow(row);
		}

		tenKhach1 = new JTextField();
		tenKhach1.setEditable(false);
		tenKhach1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		tenKhach1.setBounds(360, 102, 304, 28);
		panel_1.add(tenKhach1);
		tenKhach1.setColumns(10);
		tenKhach1.setText(hotenString);

		dienThoai1 = new JTextField();
		dienThoai1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		dienThoai1.setEditable(false);
		dienThoai1.setColumns(10);
		dienThoai1.setBounds(360, 145, 304, 28);
		panel_1.add(dienThoai1);
		dienThoai1.setText(sdt);

		ngayNhanPhong1 = new JTextField();
		ngayNhanPhong1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		ngayNhanPhong1.setEditable(false);
		ngayNhanPhong1.setColumns(10);
		ngayNhanPhong1.setBounds(360, 188, 304, 28);
		panel_1.add(ngayNhanPhong1);
		ngayNhanPhong1.setText(dayNhanString);

		ngayTraPhong1 = new JTextField();
		ngayTraPhong1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		ngayTraPhong1.setEditable(false);
		ngayTraPhong1.setColumns(10);
		ngayTraPhong1.setBounds(360, 231, 304, 28);
		panel_1.add(ngayTraPhong1);
		ngayTraPhong1.setText(dayTraString);

		tongTienPhongTable1 = new JTextField();
		tongTienPhongTable1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		tongTienPhongTable1.setEditable(false);
		tongTienPhongTable1.setColumns(10);
		tongTienPhongTable1.setBounds(483, 445, 275, 28);
		panel_1.add(tongTienPhongTable1);

		tongTienSuDungDichVu1 = new JTextField();
		tongTienSuDungDichVu1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		tongTienSuDungDichVu1.setEditable(false);
		tongTienSuDungDichVu1.setColumns(10);
		tongTienSuDungDichVu1.setBounds(483, 517, 275, 28);
		panel_1.add(tongTienSuDungDichVu1);
		if (phiDV != null) {
			tongTienSuDungDichVu1.setText(String.valueOf(phiDV));
		} else {
			tongTienSuDungDichVu1.setText("0");
		}

		tongChiPhiPhatSinh1 = new JTextField();
		tongChiPhiPhatSinh1.setFont(new Font("Times New Roman", Font.BOLD, 18));
		tongChiPhiPhatSinh1.setEditable(false);
		tongChiPhiPhatSinh1.setColumns(10);
		tongChiPhiPhatSinh1.setBounds(483, 598, 275, 28);
		panel_1.add(tongChiPhiPhatSinh1);

		if (chiPhiPhatString != null) {
			tongChiPhiPhatSinh1.setText(String.valueOf(chiPhiPhatString));
		} else {
			tongChiPhiPhatSinh1.setText("0");
		}

		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/undo.png"));
		lblNewLabel_2.setBounds(124, 89, 32, 44);
		panel_1.add(lblNewLabel_2);

		JLabel lblNewLabel_2_1 = new JLabel("");
		lblNewLabel_2_1.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/phone-call.png"));
		lblNewLabel_2_1.setBounds(124, 138, 45, 35);
		panel_1.add(lblNewLabel_2_1);

		JLabel lblNewLabel_2_2 = new JLabel("");
		lblNewLabel_2_2.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png"));
		lblNewLabel_2_2.setBounds(124, 183, 45, 33);
		panel_1.add(lblNewLabel_2_2);

		JLabel lblNewLabel_2_3 = new JLabel("");
		lblNewLabel_2_3.setIcon(new ImageIcon("img/HinhAnhGiaoDienChinh/AnhTraPhong/calendar.png"));
		lblNewLabel_2_3.setBounds(124, 226, 43, 44);
		panel_1.add(lblNewLabel_2_3);

		maHoaDon1 = new JTextField();
		maHoaDon1.setFont(new Font("Times New Roman", Font.BOLD, 15));
		maHoaDon1.setEditable(false);
		maHoaDon1.setColumns(10);
		maHoaDon1.setBounds(636, 10, 218, 28);
		panel_1.add(maHoaDon1);
		maHoaDon1.setText(mahd);
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 255, 255));
		panel_2.setBounds(884, 10, 642, 621);
		panel.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("Tổng tiền chi phí:");
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel.setBounds(25, 26, 195, 44);
		panel_2.add(lblNewLabel);

		JLabel lblTinCc = new JLabel("Tiền cọc:");
		lblTinCc.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTinCc.setBounds(25, 80, 195, 39);
		panel_2.add(lblTinCc);

		JLabel lblThnhTin = new JLabel("Thành tiền:");
		lblThnhTin.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblThnhTin.setBounds(25, 219, 195, 44);
		panel_2.add(lblThnhTin);

		JRadioButton tienMat = new JRadioButton("Tiền mặt");
		tienMat.setBackground(new Color(255, 255, 255));
		tienMat.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tienMat.setBounds(110, 307, 134, 39);
		panel_2.add(tienMat);

		JRadioButton chuyenKhoan = new JRadioButton("Chuyển khoản");
		chuyenKhoan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		chuyenKhoan.setBackground(Color.WHITE);
		chuyenKhoan.setBounds(369, 307, 201, 39);
		panel_2.add(chuyenKhoan);
		// Nhóm hai RadioButton lại để chỉ được chọn một
		ButtonGroup paymentGroup = new ButtonGroup();
		paymentGroup.add(tienMat);
		paymentGroup.add(chuyenKhoan);

		JLabel lblPhngThcThanh = new JLabel("Phương thức thanh toán:");
		lblPhngThcThanh.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblPhngThcThanh.setBounds(25, 273, 284, 44);
		panel_2.add(lblPhngThcThanh);

		JPanel panel_thoiTien = new JPanel();
		panel_thoiTien.setBackground(new Color(255, 255, 255));
		panel_thoiTien.setBounds(25, 352, 594, 216);
		panel_2.add(panel_thoiTien);
		panel_thoiTien.setLayout(null);

		JLabel lblTinKhcha = new JLabel("Tiền khách đưa:");
		lblTinKhcha.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblTinKhcha.setBounds(10, 10, 195, 44);
		panel_thoiTien.add(lblTinKhcha);

		JLabel lblNewLabel_1_1 = new JLabel("Tiền thối lại:");
		lblNewLabel_1_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblNewLabel_1_1.setBounds(10, 134, 195, 33);
		panel_thoiTien.add(lblNewLabel_1_1);

		tienKhachDua1 = new JTextField();
		tienKhachDua1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tienKhachDua1.setColumns(10);
		tienKhachDua1.setBounds(165, 10, 321, 33);

		panel_thoiTien.add(tienKhachDua1);

		tienThoi1 = new JTextField();
		tienThoi1.setFont(new Font("Times New Roman", Font.BOLD, 20));
		tienThoi1.setEditable(false);
		tienThoi1.setColumns(10);
		tienThoi1.setBounds(165, 136, 321, 33);

		JButton btnNewButton = new JButton("");
		btnNewButton.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		btnNewButton.setBounds(10, 64, 144, 44);
		btnNewButton.setBorderPainted(false);
		btnNewButton.setContentAreaFilled(false);
		btnNewButton.setFocusPainted(false);
		panel_thoiTien.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("");
		btnNewButton_1.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		btnNewButton_1.setBounds(153, 64, 144, 44);
		btnNewButton_1.setBorderPainted(false);
		btnNewButton_1.setContentAreaFilled(false);
		btnNewButton_1.setFocusPainted(false);
		panel_thoiTien.add(btnNewButton_1);

		JButton btnNewButton_2 = new JButton("");
		btnNewButton_2.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		btnNewButton_2.setBounds(292, 64, 144, 44);
		btnNewButton_2.setBorderPainted(false);
		btnNewButton_2.setContentAreaFilled(false);
		btnNewButton_2.setFocusPainted(false);
		panel_thoiTien.add(btnNewButton_2);

		JButton btnNewButton_3 = new JButton("");
		btnNewButton_3.setFont(new Font("Times New Roman", Font.PLAIN, 19));
		btnNewButton_3.setBounds(440, 64, 144, 44);
		btnNewButton_3.setBorderPainted(false);
		btnNewButton_3.setContentAreaFilled(false);
		btnNewButton_3.setFocusPainted(false);
		panel_thoiTien.add(btnNewButton_3);

		ActionListener buttonClickListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton clicked = (JButton) e.getSource();
				String value = clicked.getText().replace(",", "");
				tienKhachDua1.setText(value);
			}
		};

		btnNewButton.addActionListener(buttonClickListener);
		btnNewButton_1.addActionListener(buttonClickListener);
		btnNewButton_2.addActionListener(buttonClickListener);
		btnNewButton_3.addActionListener(buttonClickListener);

		// Ẩn panel_thoiTien lúc đầu
		panel_thoiTien.setVisible(false);
		// Xử lý sự kiện khi chọn tiền mặt
		tienMat.addActionListener(e -> panel_thoiTien.setVisible(true));

		// Xử lý sự kiện khi chọn chuyển khoản
		chuyenKhoan.addActionListener(e -> panel_thoiTien.setVisible(false));

		tongChiPhi1 = new JTextField();
		tongChiPhi1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tongChiPhi1.setEditable(false);
		tongChiPhi1.setBorder(BorderFactory.createEmptyBorder());
		tongChiPhi1.setBackground(null);
		tongChiPhi1.setOpaque(false);
		tongChiPhi1.setBounds(270, 20, 321, 33);
		panel_2.add(tongChiPhi1);
		tongChiPhi1.setColumns(10);
		capNhatTongChiPhi();

		tienCoc1 = new JTextField();
		tienCoc1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		tienCoc1.setEditable(false);
		tienCoc1.setBorder(BorderFactory.createEmptyBorder());
		tienCoc1.setBackground(null);
		tienCoc1.setOpaque(false);
		tienCoc1.setColumns(10);
		tienCoc1.setBounds(270, 68, 321, 33);
		panel_2.add(tienCoc1);

		DonDatPhong_DAO ddp_DAO = new DonDatPhong_DAO();
		DonDatPhong ddp = ddp_DAO.getDonDatPhongTheoMa(mahd);
		double tiencoc = ddp.getTienCoc();
		tienCoc1.setText(String.valueOf(tiencoc).replace(".0", ""));

		thanhTien1 = new JTextField();
		thanhTien1.setEditable(false);
		thanhTien1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		thanhTien1.setBorder(BorderFactory.createEmptyBorder());
		thanhTien1.setBackground(null);
		thanhTien1.setOpaque(false);
		thanhTien1.setColumns(10);
		thanhTien1.setBounds(270, 227, 321, 33);
		panel_2.add(thanhTien1);
		tienKhachDua1.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateTienThoi(); // Tính toán tiền thối
				updateButtons(); // Cập nhật các nút
			}

			public void removeUpdate(DocumentEvent e) {
				updateTienThoi(); // Tính toán tiền thối
				updateButtons(); // Cập nhật các nút
			}

			public void changedUpdate(DocumentEvent e) {
				updateTienThoi(); // Tính toán tiền thối
				updateButtons(); // Cập nhật các nút
			}

			private void updateButtons() {
				String input = tienKhachDua1.getText().trim();

				if (!input.matches("\\d+")) {
					// Nếu không phải số => clear button và tiền thối
					btnNewButton.setText("");
					btnNewButton_1.setText("");
					btnNewButton_2.setText("");
					btnNewButton_3.setText("");
					tienKhachDua1.setText("");
					tienThoi1.setText(""); // Xóa tiền thối
					return;
				}

				// Xử lý các trường hợp độ dài nhập vào
				if (input.length() == 5) {
					btnNewButton_3.setText(""); // Xóa button khi nhập 5 chữ số
					return;
				}
				if (input.length() == 6) {
					btnNewButton_2.setText(""); // Xóa button khi nhập 6 chữ số
					btnNewButton_3.setText(""); // Xóa button khi nhập 6 chữ số
					return;
				}
				if (input.length() > 6) {
					btnNewButton.setText("");
					btnNewButton_1.setText("");
					btnNewButton_2.setText("");
					btnNewButton_3.setText("");
					return;
				}

				try {
					// Nếu số có độ dài hợp lệ (dưới 5 chữ số)
					int base = Integer.parseInt(input);
					btnNewButton.setText(String.format("%,d", base * 1000));
					btnNewButton_1.setText(String.format("%,d", base * 10000));
					btnNewButton_2.setText(String.format("%,d", base * 100000));
					btnNewButton_3.setText(String.format("%,d", base * 1000000));
				} catch (NumberFormatException ex) {
					ex.printStackTrace();
				}
			}

			private void updateTienThoi() {
				// Lấy giá trị tiền khách đưa
				String input = tienKhachDua1.getText().trim();

				// Kiểm tra nếu tiền khách đưa là số hợp lệ
				if (!input.matches("\\d+")) {
					tienThoi1.setText(""); // Nếu không phải số, không tính tiền thối
					return;
				}

				try {
					// Kiểm tra giá trị thành tiền
					String thanhTienText = thanhTien1.getText().replace(",", "").trim();

					if (thanhTienText.isEmpty() || !thanhTienText.matches("\\d+")) {
						tienThoi1.setText(""); // Nếu thanhTien không hợp lệ
						return;
					}

					// Lấy giá trị của thành tiền
					int thanhTienValue = Integer.parseInt(thanhTienText);

					// Lấy giá trị tiền khách đưa
					int tienKhachDuaValue = Integer.parseInt(input);

					// Tính tiền thối
					int tienThoiValue = tienKhachDuaValue - thanhTienValue;

					// Cập nhật tiền thối
					tienThoi1.setText(String.format("%,d", tienThoiValue)); // Hiển thị kết quả tiền thối

				} catch (NumberFormatException ex) {
					// Nếu có lỗi trong việc chuyển đổi thành số (ví dụ thanhTien không hợp lệ)
					tienThoi1.setText(""); // Cập nhật lại tiền thối là rỗng
				}
			}
		});
		tienThoi1.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				updateTienThoiBangChu();
			}

			public void removeUpdate(DocumentEvent e) {
				updateTienThoiBangChu();
			}

			public void changedUpdate(DocumentEvent e) {
				updateTienThoiBangChu();
			}

			private void updateTienThoiBangChu() {
				String text = tienThoi1.getText().replace(",", "").trim();
				if (!text.matches("\\d+")) {
					tienThoiBangChu1.setText("");
					return;
				}
				try {
					int amount = Integer.parseInt(text);
					String bangChu = convertNumberToWords(amount) + " đồng";
					tienThoiBangChu1.setText(bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1));
				} catch (NumberFormatException ex) {
					tienThoiBangChu1.setText("");
				}
			}
		});

		panel_thoiTien.add(tienThoi1);

		tienThoiBangChu1 = new JTextField();
		tienThoiBangChu1.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		tienThoiBangChu1.setEditable(false);
		tienThoiBangChu1.setBorder(BorderFactory.createEmptyBorder());
		tienThoiBangChu1.setBackground(null);
		tienThoiBangChu1.setOpaque(false);
		tienThoiBangChu1.setBounds(10, 177, 574, 29);
		tienThoiBangChu1.setHorizontalAlignment(JTextField.CENTER);
		panel_thoiTien.add(tienThoiBangChu1);
		tienThoiBangChu1.setColumns(10);
		JButton thanhToan = new JButton("Thanh toán");
		thanhToan.setFont(new Font("Times New Roman", Font.BOLD, 20));
		thanhToan.setBackground(new Color(0, 255, 128));
		thanhToan.setBounds(498, 578, 134, 33);
		panel_2.add(thanhToan);
		thanhToan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDate ngayHienTai = LocalDate.now();
				DateTimeFormatter dinhDang = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				String ngayString = ngayHienTai.format(dinhDang);
				double tienPhong = Double.parseDouble(tongTienPhongTable1.getText());
				double tienDichVu = Double.parseDouble(tongTienSuDungDichVu1.getText());
				double chiPhiPhatSinh = Double.parseDouble(tongChiPhiPhatSinh1.getText());
				double tienCoc = Double.parseDouble(tienCoc1.getText());
				double thanhTien = Double.parseDouble(thanhTien1.getText());


//              setPhong
				List<String> maPhongs = layDanhSachMaPhong(table1);
				Phong_DAO phong_DAO = new Phong_DAO();
				for (String ma : maPhongs) {
					phong_DAO.setTrangThaiPhong(ma, "Trống");
				}
				
//				setDonDatPhong
				// Kiểm tra trước khi set trạng thái đơn đặt phòng
				DonDatPhong_DAO ddDatPhong_DAO = new DonDatPhong_DAO();
				String maDon = maHoaDon1.getText();

				if (ddDatPhong_DAO.coTheCapNhatTrangThai(maDon)) {
				    ddDatPhong_DAO.setTrangThaiDonDatPhong(maDon, "Đã thanh toán");
				} 

//				SetPhieuDichVu
				List<Object> maDV = getMaDichVu(table_dichVu);
				phieuDichVu_DAO pdv = new phieuDichVu_DAO();
				for (Object maDichVu : maDV) {
					pdv.capNhatTrangThai((String) maDichVu, "Đã thanh toán");
				}
				JLabel qrLabel = new JLabel("", JLabel.CENTER);

				if (chuyenKhoan.isSelected()) {
					qrLabel.setSize(150, 150);
					try {
						String amount = thanhTien1.getText().trim();
						String bankCode = "agribank"; // viết thường và đúng tên code chuẩn
						String account = "7714205086854";
						String name = "NGO BINH XUYEN";
						String content = "THANH TOAN";

						String qrUrl = "https://img.vietqr.io/image/" + bankCode.toLowerCase() + "-" + account
								+ "-compact2.jpg" + "?amount=" + amount + "&addInfo="
								+ java.net.URLEncoder.encode(content, "UTF-8") + "&accountName="
								+ java.net.URLEncoder.encode(name, "UTF-8");

						BufferedImage originalImage = ImageIO.read(new java.net.URL(qrUrl));
						BufferedImage resizedImage = new BufferedImage(150, 150, BufferedImage.TYPE_INT_ARGB);

						Graphics2D g2d = resizedImage.createGraphics();
						g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
								RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); // Dành cho ảnh QR rõ nét
						g2d.drawImage(originalImage, 0, 0, 150, 150, null);
						g2d.dispose();
						qrLabel.setIcon(new ImageIcon(resizedImage));

					} catch (Exception ex) {
						ex.printStackTrace();
					}

					List<Object[]> data = getTableData(table1);
					try {
						inHoaDon.taoHoaDon(maHoaDon1.getText(), ngayString, tenKhach1.getText(), sdt, dayNhanString,
								dayTraString, data, tienPhong, tienDichVu, chiPhiPhatSinh, tienCoc, "SUMMER10",
								thanhTien, "C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\HoaDon\\HoaDon.pdf", qrLabel);
						System.out.println("Đã tạo hóa đơn thành công!");
					} catch (Exception e1) {
						System.err.println("Lỗi khi tạo hóa đơn:");
						e1.printStackTrace();
					}
					 int option = JOptionPane.showConfirmDialog(null, "Thanh toán thành công!", "Thông báo", JOptionPane.OK_CANCEL_OPTION);
				        if (option == JOptionPane.OK_OPTION) {
				            SwingUtilities.getWindowAncestor(thanhToan).dispose();
				        }
				}else if(tienMat.isSelected()) {
					List<Object[]> data = getTableData(table1);
					try {
						inHoaDon.taoHoaDon(maHoaDon1.getText(), ngayString, tenKhach1.getText(), sdt, dayNhanString,
								dayTraString, data, tienPhong, tienDichVu, chiPhiPhatSinh, tienCoc, "SUMMER10",
								thanhTien, "C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\HoaDon\\HoaDon.pdf", qrLabel);
						System.out.println("Đã tạo hóa đơn thành công!");
					} catch (Exception e1) {
						System.err.println("Lỗi khi tạo hóa đơn:");
						e1.printStackTrace();
					}
					 int option = JOptionPane.showConfirmDialog(null, "Thanh toán thành công!", "Thông báo", JOptionPane.OK_CANCEL_OPTION);
				        if (option == JOptionPane.OK_OPTION) {
				            SwingUtilities.getWindowAncestor(thanhToan).dispose();
				        }
				}else {
					 int option = JOptionPane.showConfirmDialog(null, "Bạn chưa chọn phương thức thanh toán", "Thông báo", JOptionPane.OK_CANCEL_OPTION);
				}
               
			}
			  

		});
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(0, 0, 0));
		panel_4.setBounds(258, 198, 347, 2);
		panel_2.add(panel_4);

		JLabel lblKhuynMi = new JLabel("Khuyến mãi:");
		lblKhuynMi.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblKhuynMi.setBounds(25, 133, 195, 39);
		panel_2.add(lblKhuynMi);

		JComboBox khuyenMai = new JComboBox();
		khuyenMai.setFont(new Font("Times New Roman", Font.BOLD, 20));
		khuyenMai.setBounds(270, 140, 321, 28);
		khuyenMai.setBorder(BorderFactory.createEmptyBorder()); // Tắt viền
		khuyenMai.setBackground(null); // Tắt màu nền
		khuyenMai.setOpaque(false); // Làm comboBox trong suốt
		panel_2.add(khuyenMai);
		return panel;

	}

	private String convertNumberToWords(int number) {
		String[] donVi = { "", "ngàn", "triệu", "tỷ" };
		String[] chuSo = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };

		if (number == 0)
			return "không";

		List<Integer> groups = new ArrayList<>();
		while (number > 0) {
			groups.add(number % 1000);
			number /= 1000;
		}

		StringBuilder result = new StringBuilder();
		boolean hasPreviousGroup = false;

		for (int i = groups.size() - 1; i >= 0; i--) {
			int group = groups.get(i);
			boolean isFirstGroup = (i == groups.size() - 1);

			if (group != 0 || isFirstGroup) {
				String groupStr = readThreeDigits(group, chuSo, hasPreviousGroup, isFirstGroup);
				if (!groupStr.isEmpty()) {
					result.append(groupStr).append(" ");
					if (!donVi[i].isEmpty()) {
						result.append(donVi[i]).append(" ");
					}
					hasPreviousGroup = true;
				}
			}
		}

		return result.toString().trim().replaceAll("\\s+", " ");
	}

	private String readThreeDigits(int number, String[] chuSo, boolean hasPreviousGroup, boolean isFirstGroup) {
		int tram = number / 100;
		int chuc = (number % 100) / 10;
		int donvi = number % 10;

		StringBuilder sb = new StringBuilder();

		if (tram > 0) {
			sb.append(chuSo[tram]).append(" trăm ");
		} else if (hasPreviousGroup && (chuc > 0 || donvi > 0)) {
			sb.append("không trăm ");
		}

		if (chuc > 1) {
			sb.append(chuSo[chuc]).append(" mươi ");
			if (donvi == 1)
				sb.append("mốt ");
			else if (donvi == 5)
				sb.append("lăm ");
			else if (donvi > 0)
				sb.append(chuSo[donvi]).append(" ");
		} else if (chuc == 1) {
			sb.append("mười ");
			if (donvi == 5)
				sb.append("lăm ");
			else if (donvi > 0)
				sb.append(chuSo[donvi]).append(" ");
		} else if (donvi > 0) {
			// ❌ Chỉ thêm “lẻ” nếu KHÔNG phải nhóm cao nhất
			if (!isFirstGroup)
				sb.append("lẻ ");
			sb.append(chuSo[donvi]).append(" ");
		}

		return sb.toString().trim();
	}

	private void capNhatTongTienThanhToan() {
		double tongTien = 0;
		DefaultTableModel model = (DefaultTableModel) table1.getModel();
		int columnThanhTien = 4; // Cột "Thành tiền" là cột thứ 5 (chỉ số 4)

		for (int i = 0; i < model.getRowCount(); i++) {
			Object value = model.getValueAt(i, columnThanhTien);
			if (value != null && value instanceof Number) {
				tongTien += ((Number) value).doubleValue();
			} else if (value != null) {
				try {
					tongTien += Double.parseDouble(value.toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}

		// Cập nhật vào ô tổng tiền và định dạng đẹp nếu muốn
		tongTienPhongTable1.setText(String.format("%.0f", tongTien)); // Hiển thị kiểu ###,### nếu muốn
	}

	private void capNhatTongChiPhi() {
		try {
			double tienPhong = tongTienPhongTable1.getText().isEmpty() ? 0
					: Double.parseDouble(tongTienPhongTable1.getText());
			double tienDV = tongTienSuDungDichVu1.getText().isEmpty() ? 0
					: Double.parseDouble(tongTienSuDungDichVu1.getText());
			double tienPhatSinh = tongChiPhiPhatSinh1.getText().isEmpty() ? 0
					: Double.parseDouble(tongChiPhiPhatSinh1.getText());
			double tong = tienPhong + tienDV + tienPhatSinh;
			tongChiPhi1.setText(String.format("%.0f", tong));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Lỗi định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void layDanhSachPhongDuocChon() {
		danhSachPhongDuocChon.clear(); // Xóa danh sách cũ
		if (table_phongTra != null) {
			DefaultTableModel model = (DefaultTableModel) table_phongTra.getModel();
			int rowCount = model.getRowCount();
			for (int i = 0; i < rowCount; i++) {
				Boolean isSelected = (Boolean) model.getValueAt(i, 0); // Lấy giá trị checkbox từ cột đầu tiên
				if (isSelected != null && isSelected) {
					Object[] phong = new Object[model.getColumnCount() - 1]; // Tạo mảng Object để lưu dữ liệu phòng
					for (int j = 1; j < model.getColumnCount(); j++) {
						phong[j - 1] = model.getValueAt(i, j); // Lấy dữ liệu từ các cột còn lại
					}
					danhSachPhongDuocChon.add(phong);
				}
			}
		}
	}

	private void capNhatHoaDonCT() {
		DefaultTableModel model_HoaDon = (DefaultTableModel) table1.getModel();
		model_HoaDon.setRowCount(0);
		for (Object[] row : danhSachPhongDuocChon) {
			model_HoaDon.addRow(row);
		}

		// Cập nhật các trường dữ liệu khác
		tongChiPhiPhatSinh1.setText(tongTienPS.getText().replace(".0", ""));
		tongTienSuDungDichVu1.setText(tongTienSDDV.getText());
		capNhatTongTienThanhToan();
		capNhatTongChiPhi();
		capNhatThanhTien();
	}

	private void capNhatThanhTien() {
		try {
			double tongChiPhi = Double.parseDouble(tongChiPhi1.getText());
			double tienCoc = Double.parseDouble(tienCoc1.getText()); // Giả sử tienCoc1 là JTextField chứa tiền cọc
			double thanhTien = tongChiPhi - tienCoc;
			thanhTien1.setText(String.format("%.0f", thanhTien).replace(".0", ""));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Lỗi định dạng số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
			thanhTien1.setText("0"); // Hoặc giá trị mặc định khác
		}
	}

	public static List<Object[]> getTableData(JTable table) {
		List<Object[]> data = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();
		int columnCount = model.getColumnCount();

		for (int row = 0; row < rowCount; row++) {
			Object[] rowData = new Object[columnCount];
			for (int col = 0; col < columnCount; col++) {
				rowData[col] = model.getValueAt(row, col);
			}
			data.add(rowData);
		}

		return data;
	}

	public static List<Object> getMaDichVu(JTable table) {
		List<Object> maDichVuList = new ArrayList<>();

		// Lấy model của bảng
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		// Lấy số lượng dòng trong bảng
		int rowCount = model.getRowCount();

		// Duyệt qua từng dòng trong bảng
		for (int i = 0; i < rowCount; i++) {
			// Lấy giá trị cột 0 (Mã phiếu dịch vụ) và lưu vào danh sách
			Object maDichVu = model.getValueAt(i, 0); // Cột đầu tiên là mã dịch vụ
			maDichVuList.add(maDichVu);
		}

		return maDichVuList;
	}

	public List<String> layDanhSachMaPhong(JTable table) {
		List<String> danhSachMaPhong = new ArrayList<>();

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int rowCount = model.getRowCount();

		for (int i = 0; i < rowCount; i++) {
			Object value = model.getValueAt(i, 0); // Cột 0: Mã phòng
			if (value != null) {
				danhSachMaPhong.add(value.toString().trim());
			}
		}

		return danhSachMaPhong;
	}

}