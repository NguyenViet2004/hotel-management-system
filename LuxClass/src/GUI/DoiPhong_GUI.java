package GUI;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import GUI.QuanLyDatPhong_GUI.CustomRoundedPanel;


public class DoiPhong_GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	private JFrame frame;
	private JTextField maDon;
	private JTextField hoVaTen;
	private JTextField ngayNhan;
	private JTextField textField;
	private JTextField textField_1;
	private JTable table;
	private JTable table_phongTra;
	private JTable table_dichVu;
	private JTextField textField_2;
	private int frameWidth;
	private int frameHeight;
	private JPanel mainPanel;


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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);

		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setLayout(new BorderLayout());
		setContentPane(contentPane);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int screenWidth = (int) screenSize.getWidth();
		int screenHeight = (int) screenSize.getHeight();

		// Panel North (Thanh tiêu đề)
		CustomRoundedPanel panelNorth = new CustomRoundedPanel(0, 0, 0, 0);
		panelNorth.setBackground(Color.WHITE);
		panelNorth.setPreferredSize(new Dimension(screenWidth, (int) (screenHeight * 0.15)));
		panelNorth.setLayout(new BorderLayout());
		contentPane.add(panelNorth, BorderLayout.NORTH);

		JPanel leftPanel = new JPanel();
		leftPanel.setOpaque(true);
		leftPanel.setBackground(Color.LIGHT_GRAY);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		panelNorth.add(leftPanel, BorderLayout.WEST);

		int logoSize = (int) (screenHeight * 0.15);
		ImageIcon logoIcon = new ImageIcon(
				new ImageIcon("img/logoLux.png").getImage().getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH));
		JLabel logo = new JLabel(logoIcon);
		leftPanel.add(logo);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(Color.LIGHT_GRAY);
		mainPanel.setBorder(new EmptyBorder(8, 8, 8, 8));
		contentPane.add(mainPanel, BorderLayout.CENTER);
		
		// West panel
		CustomRoundedPanel panelWest = new CustomRoundedPanel(0, 0, 0, 0);
		panelWest.setBackground(Color.WHITE);
		panelWest.setLayout(new BoxLayout(panelWest, BoxLayout.Y_AXIS));
		panelWest.setBorder(new EmptyBorder(20, 40, 20, 40));
		panelWest.setPreferredSize(new Dimension((int) (screenWidth * 0.45), screenHeight));

		// Tiêu đề
		JLabel TTDonDat = new JLabel("Thông tin đơn đặt phòng");
		TTDonDat.setFont(new Font("Times New Roman", Font.BOLD, 30));
		TTDonDat.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelWest.add(TTDonDat);
		panelWest.add(Box.createVerticalStrut(20));

		// Các dòng nhập liệu
		panelWest.add(taoDong("Mã đơn đặt phòng:", maDon = new JTextField()));
		panelWest.add(Box.createVerticalStrut(10));
		panelWest.add(taoDong("Họ tên khách hàng:", hoVaTen = new JTextField()));
		panelWest.add(Box.createVerticalStrut(10));
		panelWest.add(taoDong("Ngày nhận phòng:", ngayNhan = new JTextField()));
		panelWest.add(Box.createVerticalStrut(10));
		panelWest.add(taoDong("Ngày trả phòng:", textField = new JTextField()));
		panelWest.add(Box.createVerticalStrut(10));
		panelWest.add(taoDong("Số lượng khách:", textField_1 = new JTextField()));

		panelWest.add(Box.createVerticalStrut(30));

		// Tiêu đề dịch vụ
		JLabel lblNewLabel_5 = new JLabel("Các dịch vụ đã sử dụng");
		lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblNewLabel_5.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelWest.add(lblNewLabel_5);
		panelWest.add(Box.createVerticalStrut(10));

		// ScrollPane dịch vụ (nếu cần)
		JScrollPane dichVu = createDichVuScrollPane();
		dichVu.setPreferredSize(new Dimension(500, 150));
		panelWest.add(dichVu);

		// Tổng tiền
		panelWest.add(Box.createVerticalStrut(30));
		panelWest.add(taoDong("Tổng tiền dịch vụ:", textField_2 = new JTextField()));
		textField_2.setEnabled(false);

		// Thêm vào mainPanel
		mainPanel.add(panelWest, BorderLayout.WEST);

		// Panel Center (Phải)
		CustomRoundedPanel panelCen = new CustomRoundedPanel(0, 0, 0, 0);
		panelCen.setBackground(Color.WHITE);
		panelCen.setLayout(new BorderLayout());

		// --- Tiêu đề phía trên ---
		JLabel lblTieuDe = new JLabel("Chọn phòng muốn trả", SwingConstants.CENTER);
		lblTieuDe.setFont(new Font("Times New Roman", Font.BOLD, 27));
		lblTieuDe.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
		panelCen.add(lblTieuDe, BorderLayout.NORTH);

		// --- Panel chứa danh sách phòng (scroll) ở giữa ---
		JPanel scrollPanel = createPhongTraScrollPane();
		panelCen.add(scrollPanel, BorderLayout.CENTER);

		// --- Nút "Tiếp tục" phía dưới ---
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		bottomPanel.setBackground(Color.WHITE);

		JButton tiepTuc = new JButton("Tiếp tục");
		tiepTuc.setBackground(new Color(0, 255, 64));
		tiepTuc.setFont(new Font("Times New Roman", Font.BOLD, 25));
		bottomPanel.add(tiepTuc);

		panelCen.add(bottomPanel, BorderLayout.SOUTH);
		tiepTuc.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        // Xóa các thành phần cũ trong mainPanel
		        mainPanel.removeAll();

		        // Tạo giao diện kiểm tra thiết bị mới
		        JPanel kiemTraThietBiPanel = new KiemTraThietBi_GUI(); // JPanel chứ không phải JFrame

		        // Thêm vào mainPanel
		        mainPanel.add(kiemTraThietBiPanel, BorderLayout.CENTER);

		        // Làm mới lại giao diện
		        mainPanel.revalidate();
		        mainPanel.repaint();
		    }
		});
		// --- Thêm panel này vào khu vực CENTER của mainPanel ---
		mainPanel.add(panelCen, BorderLayout.CENTER);

	}

	private JScrollPane createDichVuScrollPane() {
	    JScrollPane dichVu = new JScrollPane();
	    dichVu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    dichVu.setBorder(null);
	    dichVu.setBackground(Color.WHITE);
	    dichVu.setPreferredSize(new Dimension(650, 150));

	    table_dichVu = new JTable();
	    DefaultTableModel model_dv = new DefaultTableModel(
	        new Object[][]{
	            {"DV001", "Massage", "Không"},

	        },
	        new String[]{
	            "Mã dịch vụ", "Tên dịch vụ", "Thành tiền", ""
	        }
	    ) {
	        boolean[] columnEditables = new boolean[]{
	            false, false, false, true
	        };

	        @Override
	        public boolean isCellEditable(int row, int column) {
	            return columnEditables[column];
	        }
	    };
	    table_dichVu.setModel(model_dv);

	    // Hiển thị đường kẻ ô
	    table_dichVu.setShowVerticalLines(true);
	    table_dichVu.setShowHorizontalLines(true);
	    table_dichVu.setGridColor(Color.LIGHT_GRAY);

	    table_dichVu.setRowHeight(40);
	    table_dichVu.setFont(new Font("Dialog", Font.PLAIN, 14));
	    table_dichVu.setFillsViewportHeight(true);
	    table_dichVu.setBorder(null);

	    // Header
	    JTableHeader header_dv = table_dichVu.getTableHeader();
	    header_dv.setBackground(new Color(220, 255, 220));
	    header_dv.setPreferredSize(new Dimension(header_dv.getWidth(), 30));
	    header_dv.setFont(new Font("Times New Roman", Font.BOLD, 16));
	    header_dv.setBorder(null);

	    // Canh giữa tiêu đề
	    DefaultTableCellRenderer headerRenderer = (DefaultTableCellRenderer) header_dv.getDefaultRenderer();
	    headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

	    // Canh giữa nội dung các cột
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    for (int i = 0; i < table_dichVu.getColumnCount(); i++) {
	        table_dichVu.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

	    dichVu.setViewportView(table_dichVu);
	    return dichVu;
	}

	private JPanel taoDong(String labelText, JTextField textField) {
		JPanel row = new JPanel(new BorderLayout(10, 0));
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		row.setOpaque(false);

		JLabel label = new JLabel(labelText);
		label.setFont(new Font("Times New Roman", Font.BOLD, 20));
		label.setPreferredSize(new Dimension(200, 30));

		textField.setEnabled(false);
		textField.setPreferredSize(new Dimension(300, 30));
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 18));

		row.add(label, BorderLayout.WEST);
		row.add(textField, BorderLayout.CENTER);

		return row;
	}


	private JPanel createPhongTraScrollPane() {
	    table_phongTra = new JTable();
	    table_phongTra.setRowHeight(30);
	    table_phongTra.setFont(new Font("Times New Roman", Font.PLAIN, 17));
	    table_phongTra.setFillsViewportHeight(true);
	    
	    // Bật hiển thị đường kẻ ô
	    table_phongTra.setShowGrid(true);
	    table_phongTra.setGridColor(Color.GRAY); // Màu của đường kẻ ô

	    Object[][] data = {
	        {false, "P102", "Single room", "5 ngày", "800.000", "4.800.000"},
	        {false, "P201", "Twin room", "5 ngày", "1.200.000", "6.000.000"},
	        {false, "P202", "Twin room", "5 ngày", "1.200.000", "6.000.000"}
	    };
	    String[] columnNames = {"", "Mã phòng", "Loại phòng", "Thời gian", "Đơn giá", "Thành tiền"};

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

	    // Căn giữa văn bản
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	    for (int i = 1; i < table_phongTra.getColumnCount(); i++) {
	        table_phongTra.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
	    }

	    // Header
	    JTableHeader header = table_phongTra.getTableHeader();
	    header.setBackground(new Color(220, 255, 220));
	    header.setFont(new Font("Times New Roman", Font.BOLD, 20));
	    header.setPreferredSize(new Dimension(0, 40));

	    // Bọc bảng bằng JScrollPane
	    JScrollPane scrollPane = new JScrollPane(table_phongTra);
	    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	    scrollPane.setBorder(BorderFactory.createLineBorder(Color.black)); // Border xung quanh bảng

	    // Bọc trong JPanel sử dụng BorderLayout
	    JPanel panel = new JPanel(new BorderLayout());
	    panel.setBackground(Color.WHITE);
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
	    panel.add(scrollPane, BorderLayout.CENTER);
	    JCheckBox headerCheckBox = new JCheckBox();
	    headerCheckBox.setHorizontalAlignment(JLabel.CENTER);
	    headerCheckBox.setOpaque(true);
	    headerCheckBox.setBackground(new Color(220, 255, 220));
	    headerCheckBox.setBorderPainted(true);

	    // Set renderer cho header của cột checkbox
	    table_phongTra.getColumnModel().getColumn(0)
	        .setHeaderRenderer(new CheckBoxHeaderRenderer(e -> {
	            boolean selected = headerCheckBox.isSelected();
	            for (int i = 0; i < table_phongTra.getRowCount(); i++) {
	                table_phongTra.setValueAt(selected, i, 0);
	            }
	        }));

	    // Xử lý khi click vào header checkbox
	    table_phongTra.getTableHeader().addMouseListener(new MouseAdapter() {
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            int column = table_phongTra.columnAtPoint(e.getPoint());
	            if (column == 0) {
	                headerCheckBox.setSelected(!headerCheckBox.isSelected());
	                for (int i = 0; i < table_phongTra.getRowCount(); i++) {
	                    table_phongTra.setValueAt(headerCheckBox.isSelected(), i, 0);
	                }
	                table_phongTra.getTableHeader().repaint();
	            }
	        }
	    });

	    return panel;
	}
	
	class CheckBoxHeaderRenderer extends JCheckBox implements TableCellRenderer {
	    public CheckBoxHeaderRenderer(ActionListener action) {
	        setHorizontalAlignment(JLabel.CENTER);
	        setOpaque(true);
	        setBackground(new Color(220, 255, 220));
	        setBorderPainted(true);
	        addActionListener(action);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value,
	            boolean isSelected, boolean hasFocus, int row, int column) {
	        return this;
	    }
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

			GeneralPath path = new GeneralPath();
			path.moveTo(topLeft, 0);
			path.lineTo(width - topRight, 0);
			if (topRight > 0) {
				path.quadTo(width, 0, width, topRight);
			}
			path.lineTo(width, height - bottomRight);
			if (bottomRight > 0) {
				path.quadTo(width, height, width - bottomRight, height);
			}
			path.lineTo(bottomLeft, height);
			if (bottomLeft > 0) {
				path.quadTo(0, height, 0, height - bottomLeft);
			}
			path.lineTo(0, topLeft);
			if (topLeft > 0) {
				path.quadTo(0, 0, topLeft, 0);
			}
			path.closePath();
			g2.fill(path);
		}

	}

}
