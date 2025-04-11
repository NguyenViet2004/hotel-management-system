package TraPhong;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.ScrollPaneConstants;

public class traphong {

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

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    traphong window = new traphong();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public traphong() {
        initialize();
    }

    private void initialize() {
        // Lấy kích thước màn hình thực tế
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        GraphicsConfiguration gc = gd.getDefaultConfiguration();

        // Lấy kích thước màn hình thực sự (bao gồm cả taskbar)
        Rectangle screenBounds = gc.getBounds();
        Insets screenInsets = Toolkit.getDefaultToolkit().getScreenInsets(gc);

        // Tính kích thước thực tế trừ đi taskbar
        int screenWidth = screenBounds.width - (screenInsets.left + screenInsets.right);
        int screenHeight = screenBounds.height - (screenInsets.top + screenInsets.bottom);

        // Tính toán kích thước JFrame
        frameWidth = (int) (screenWidth);
        frameHeight = (int) (screenHeight);
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\logo.png"));
        frame.getContentPane().setBackground(new Color(226, 219, 219));
        frame.setBounds(100, 100, frameWidth, frameHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setLocationRelativeTo(null); // Hiển thị ở giữa màn hình (nếu không phải toàn màn hình)
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Đặt trạng thái phóng to toàn màn hình

        JPanel Header = createHeaderPanel();
        frame.getContentPane().add(Header);

        JPanel body = createBodyPanel();
        frame.getContentPane().add(body);
    }

    private JPanel createHeaderPanel() {
        JPanel Header = new JPanel();
        Header.setBounds(0, 0, frameWidth, (int) (frameHeight * 0.12));
        Header.setBackground(new Color(255, 255, 255));
        Header.setLayout(new BorderLayout());
        Header.setBorder(new LineBorder(Color.black));

        JLabel lblLoGo = new JLabel("");
        ImageIcon originalIcon = new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\logo.png");
        Image image = originalIcon.getImage().getScaledInstance(88, 88, Image.SCALE_SMOOTH);
        ImageIcon logoIcon = new ImageIcon(image);
        lblLoGo.setIcon(logoIcon);
        lblLoGo.setBounds(5, 5, 88, 88);
        Header.add(lblLoGo);

        JButton undo = new JButton("");
        undo.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\undo.png"));
        undo.setBounds(103, 55, 45, 38);
        undo.setContentAreaFilled(false);
        undo.setBorderPainted(false);
        undo.setFocusPainted(false);
        Header.add(undo);

        JButton Home = new JButton("");
        Home.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\home.png"));
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
        help.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\help.png"));
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
        body.setLayout(new GridLayout(1, 2, 10, 0));

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
        Traipanel.setLayout(new BorderLayout());

        JLabel TTDonDat = new JLabel("Thông tin đơn đặt phòng");
        TTDonDat.setBounds(186, 10, 324, 52);
        TTDonDat.setFont(new Font("Times New Roman", Font.BOLD, 30));
        Traipanel.add(TTDonDat);

        JLabel lblNewLabel = new JLabel("Mã đơn đặt phòng:");
        lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel.setBounds(10, 72, 194, 32);
        Traipanel.add(lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Họ tên khách hàng:");
        lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_1.setBounds(10, 118, 175, 24);
        Traipanel.add(lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("Ngày nhận phòng:");
        lblNewLabel_2.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_2.setBounds(10, 159, 175, 24);
        Traipanel.add(lblNewLabel_2);

        JLabel lblNewLabel_3 = new JLabel("Ngày trả phòng:");
        lblNewLabel_3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_3.setBounds(10, 201, 159, 24);
        Traipanel.add(lblNewLabel_3);

        JLabel lblNewLabel_4 = new JLabel("Số lượng khách:");
        lblNewLabel_4.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_4.setBounds(10, 243, 159, 24);
        Traipanel.add(lblNewLabel_4);

        JLabel lblNewLabel_5 = new JLabel("Các dịch vụ đã sử dụng");
        lblNewLabel_5.setFont(new Font("Times New Roman", Font.BOLD, 26));
        lblNewLabel_5.setBounds(205, 284, 270, 32);
        Traipanel.add(lblNewLabel_5);

        maDon = new JTextField();
        maDon.setEnabled(false);
        maDon.setBounds(250, 75, 404, 32);
        Traipanel.add(maDon);
        maDon.setColumns(10);

        hoVaTen = new JTextField();
        hoVaTen.setEnabled(false);
        hoVaTen.setColumns(10);
        hoVaTen.setBounds(250, 117, 404, 32);
        Traipanel.add(hoVaTen);

        ngayNhan = new JTextField();
        ngayNhan.setEnabled(false);
        ngayNhan.setColumns(10);
        ngayNhan.setBounds(250, 158, 404, 32);
        Traipanel.add(ngayNhan);

        textField = new JTextField();
        textField.setEnabled(false);
        textField.setColumns(10);
        textField.setBounds(250, 200, 404, 32);
        Traipanel.add(textField);

        textField_1 = new JTextField();
        textField_1.setEnabled(false);
        textField_1.setColumns(10);
        textField_1.setBounds(250, 242, 404, 32);
        Traipanel.add(textField_1);

        JScrollPane dichVu = createDichVuScrollPane();
        Traipanel.add(dichVu);

        JLabel lblNewLabel_11 = new JLabel("Tổng tiền sử dụng dịch vụ:");
        lblNewLabel_11.setFont(new Font("Times New Roman", Font.BOLD, 20));
        lblNewLabel_11.setBounds(24, 506, 250, 32);
        Traipanel.add(lblNewLabel_11);

        textField_2 = new JTextField();
        textField_2.setEnabled(false);
        textField_2.setBounds(288, 511, 288, 28);
        Traipanel.add(textField_2);
        textField_2.setColumns(10);

        return Traipanel;
    }

    private JScrollPane createDichVuScrollPane() {
        JScrollPane dichVu = new JScrollPane();
        dichVu.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        dichVu.setBorder(null);
        dichVu.setBackground(new Color(255, 255, 255));
        dichVu.setBounds(24, 326, 707, 159);

        table_dichVu = new JTable();
        DefaultTableModel model_dv = new DefaultTableModel(
                new Object[][]{
                        {"DV001", "Massage", "Không"},
                        {"DV002", "Ăn sáng", "Không"},
                        {"DV003", "Giặt ủi", "Có"},
                },
                new String[]{
                        "Mã dịch vụ", "Tên dịch vụ", "Thành tiền", "" // Thêm cột "Hành động"
                }
        ) {
            boolean[] columnEditables = new boolean[]{
                    false, false, false, true // Cột "Hành động" được phép chỉnh sửa
            };

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
        table_dichVu.setFont(new Font("Dialog", Font.PLAIN, 14));
        table_dichVu.setFillsViewportHeight(true);
        table_dichVu.setBorder(null);
        JTableHeader header_dv = table_dichVu.getTableHeader();
        header_dv.setBackground(new Color(220, 255, 220));
        header_dv.setPreferredSize(new Dimension(header_dv.getWidth(), 30));
        header_dv.setFont(new Font("Times New Roman", Font.BOLD, 16));
        header_dv.setBorder(null);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setFont(new Font("Times New Roman", Font.BOLD, 16));
                label.setBackground(new Color(220, 255, 220));
                label.setBorder(BorderFactory.createEmptyBorder()); // Loại bỏ viền
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

        // Tạo ButtonRenderer và ButtonEditor
        ButtonRenderer buttonRenderer = new ButtonRenderer();
        ButtonEditor buttonEditor = new ButtonEditor();

        // Áp dụng Renderer và Editor cho cột cuối cùng ("Hành động")
        int buttonColumnIndex = table_dichVu.getColumnCount() - 1;
        table_dichVu.getColumnModel().getColumn(buttonColumnIndex).setCellRenderer(buttonRenderer);
        table_dichVu.getColumnModel().getColumn(buttonColumnIndex).setCellEditor(buttonEditor);

        // Lấy ButtonPanel từ ButtonEditor để thêm ActionListener bên ngoài
        ButtonPanel buttonPanelEditor = buttonEditor.getButtonPanel();
        if (buttonPanelEditor != null) {
            buttonPanelEditor.getDeleteButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table_dichVu.getSelectedRow();
                    if (row != -1) {
                        System.out.println("Nút Xóa được click ở hàng: " + row);
                        DefaultTableModel model = (DefaultTableModel) table_dichVu.getModel();
                        model.removeRow(row);
                    }
                }
            });

            buttonPanelEditor.getDetailButton().addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int row = table_dichVu.getSelectedRow();
                    if (row != -1) {
                        System.out.println("Nút Chi tiết được click ở hàng: " + row);
                        // Thêm logic xử lý xem chi tiết ở đây
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

        JPanel scrollPanel = createPhongTraScrollPane();
        Phaipanel.add(scrollPanel);

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

        table_phongTra.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JPanel panel = new JPanel();
                panel.setBackground(new Color(220, 255, 220)); // Màu xanh lá cây nhạt
                panel.setLayout(new GridBagLayout()); // Sử dụng GridBagLayout để căn giữa checkbox

                if (column == 0) { // Nếu là cột checkbox
                    JCheckBox checkBox = new JCheckBox();
                    checkBox.setBackground(new Color(220, 255, 220)); // Đặt màu nền cho checkbox
                    checkBox.setHorizontalAlignment(JLabel.CENTER); // Căn giữa checkbox
                    panel.add(checkBox);
                } else { // Nếu là các cột khác
                    JLabel label = new JLabel(value.toString());
                    label.setHorizontalAlignment(JLabel.CENTER); // Căn giữa chữ
                    panel.add(label);
                }

                return panel;
            }
        });
        JTableHeader header = table_phongTra.getTableHeader();
        header.setBackground(new Color(220, 255, 220)); // Màu nền tiêu đề
        header.setPreferredSize(new Dimension(header.getWidth(), 40)); // Đặt chiều cao header là 40 pixel

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

        // Thêm MouseListener vào header
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
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
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
        scrollPane.setBounds(0, 0, 693, 285);
        scrollPane.setBackground(new Color(0, 0, 0, 0));
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(0, 0, 0, 0));
        scrollPane.getViewport().setBorder(null);

        JPanel scrollPanel = new JPanel();
        scrollPanel.setBounds(34, 57, 693, 285);
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
            this.setBackground(new Color(220, 255, 220)); // Đặt màu nền cho CheckBoxHeader
            add(checkBox);
            checkBox.setBackground(new Color(220, 255, 220));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }
 // Class ButtonPanel (đặt ở ngoài class traphong hoặc trong cùng file)
    class ButtonPanel extends JPanel {
        public JButton deleteButton;
        public JButton detailButton;

        public ButtonPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 1, 10));

            deleteButton = new JButton("");
            deleteButton.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\xoa.png"));
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            add(deleteButton);

            detailButton = new JButton();
            detailButton.setIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\chitiet.png"));
            detailButton.setContentAreaFilled(false);
            detailButton.setBorderPainted(false);
            add(detailButton);
        }

        // Phương thức để lấy button xóa
        public JButton getDeleteButton() {
            return deleteButton;
        }

        // Phương thức để lấy button chi tiết
        public JButton getDetailButton() {
            return detailButton;
        }
    }

    // Class ButtonRenderer (đặt ở ngoài class traphong hoặc trong cùng file)
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private ButtonPanel panel;

        public ButtonRenderer() {
            setOpaque(true);
            panel = new ButtonPanel();
            setLayout(new BorderLayout());
            add(panel, BorderLayout.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }
            return panel;
        }
    }

    // Class ButtonEditor (đặt ở ngoài class traphong hoặc trong cùng file)
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private ButtonPanel panel;

        public ButtonEditor() {
            panel = new ButtonPanel();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                panel.setBackground(table.getSelectionBackground());
            } else {
                panel.setBackground(table.getBackground());
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        // Phương thức để lấy panel chứa các nút (để thêm ActionListener bên ngoài)
        public ButtonPanel getButtonPanel() {
            return panel;
        }
    }
}