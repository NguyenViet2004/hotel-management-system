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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private JTextField ngayTra;
    private JTextField soLuongKhach;
    private JTable table_phongTra;
    private JTable table_dichVu;
    private JTextField tongTien;

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
        frame = new JFrame();
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\logo.png"));
        frame.getContentPane().setBackground(new Color(226, 219, 219));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        JPanel header = createHeaderPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.12;
        frame.getContentPane().add(header, gbc);

        JPanel body = createBodyPanel();
        gbc.gridy = 1;
        gbc.weighty = 0.88;
        frame.getContentPane().add(body, gbc);
    }

    private JPanel createHeaderPanel() {
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setLayout(null);
        header.setBorder(new LineBorder(Color.BLACK));

        JLabel logo = new JLabel(new ImageIcon(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\HinhAnhGiaoDienChinh\\logo.png").getImage().getScaledInstance(88, 88, Image.SCALE_SMOOTH)));
        logo.setBounds(5, 5, 88, 88);
        header.add(logo);

        JButton undo = new JButton(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\undo.png"));
        undo.setBounds(103, 55, 45, 38);
        setButtonProperties(undo);
        header.add(undo);

        JButton home = new JButton(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\home.png"));
        home.setBounds(158, 55, 37, 38);
        setButtonProperties(home);
        header.add(home);

        JLabel title = new JLabel("Trả phòng");
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setBounds(102, 10, 115, 35);
        header.add(title);

        JButton help = new JButton(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\help.png"));
        help.setBounds(1348, 36, 37, 32);
        setButtonProperties(help);
        header.add(help);

        return header;
    }

    private void setButtonProperties(JButton button) {
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }

    private JPanel createBodyPanel() {
        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(new Color(192, 192, 192));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 0.5;
        gbc.weighty = 1.0;

        JPanel traiPanel = createTraiPanel();
        gbc.gridx = 0;
        gbc.gridy = 0;
        body.add(traiPanel, gbc);

        JPanel phaiPanel = createPhaiPanel();
        gbc.gridx = 1;
        gbc.gridy = 0;
        body.add(phaiPanel, gbc);

        return body;
    }

    private JPanel createTraiPanel() {
        JPanel traiPanel = new JPanel();
        traiPanel.setBackground(Color.WHITE);
        traiPanel.setLayout(null);

        JLabel title = new JLabel("Thông tin đơn đặt phòng");
        title.setFont(new Font("Times New Roman", Font.BOLD, 30));
        title.setBounds(186, 10, 324, 52);
        traiPanel.add(title);

        addLabelAndTextField(traiPanel, "Mã đơn đặt phòng:", maDon = new JTextField(), 10, 72);
        addLabelAndTextField(traiPanel, "Họ tên khách hàng:", hoVaTen = new JTextField(), 10, 118);
        addLabelAndTextField(traiPanel, "Ngày nhận phòng:", ngayNhan = new JTextField(), 10, 159);
        addLabelAndTextField(traiPanel, "Ngày trả phòng:", ngayTra = new JTextField(), 10, 201);
        addLabelAndTextField(traiPanel, "Số lượng khách:", soLuongKhach = new JTextField(), 10, 243);

        JLabel serviceLabel = new JLabel("Các dịch vụ đã sử dụng");
        serviceLabel.setFont(new Font("Times New Roman", Font.BOLD, 26));
        serviceLabel.setBounds(205, 284, 270, 32);
        traiPanel.add(serviceLabel);

        JScrollPane serviceScroll = createDichVuScrollPane();
        serviceScroll.setBounds(24, 326, 707, 159);
        traiPanel.add(serviceScroll);

        JLabel totalLabel = new JLabel("Tổng tiền sử dụng dịch vụ:");
        totalLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        totalLabel.setBounds(24, 506, 250, 32);
        traiPanel.add(totalLabel);

        tongTien = new JTextField();
        tongTien.setEnabled(false);
        tongTien.setBounds(288, 511, 288, 28);
        traiPanel.add(tongTien);
        tongTien.setColumns(10);

        return traiPanel;
    }

    private void addLabelAndTextField(JPanel panel, String labelText, JTextField textField, int x, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Times New Roman", Font.BOLD, 20));
        label.setBounds(x, y, 194, 32);
        panel.add(label);

        textField.setEnabled(false);
        textField.setBounds(250, y, 404, 32);
        panel.add(textField);
        textField.setColumns(10);
    }

    private JScrollPane createDichVuScrollPane() {
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.setBounds(24, 326, 707, 159);

        table_dichVu = new JTable();
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                        {"DV001", "Massage", "Không"},
                        {"DV002", "Ăn sáng", "Không"},
                        {"DV003", "Giặt ủi", "Có"},
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
        table_dichVu.setModel(model);
        table_dichVu.setShowVerticalLines(false);
        table_dichVu.setShowHorizontalLines(false);
        table_dichVu.setShowGrid(false);
        table_dichVu.setRowHeight(40);
        table_dichVu.setFont(new Font("Dialog", Font.PLAIN, 14));
        table_dichVu.setFillsViewportHeight(true);
        table_dichVu.setBorder(null);
        JTableHeader header = table_dichVu.getTableHeader();
        header.setBackground(new Color(220, 255, 220));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
        header.setFont(new Font("Times New Roman", Font.BOLD, 16));
        header.setBorder(null);

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setFont(new Font("Times New Roman", Font.BOLD, 16));
                label.setBackground(new Color(220, 255, 220));
                label.setBorder(BorderFactory.createEmptyBorder());
                return label;
            }
        };
        header.setDefaultRenderer(headerRenderer);

        table_dichVu.setBackground(Color.WHITE);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table_dichVu.getColumnCount() - 1; i++) {
            table_dichVu.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        ButtonRenderer buttonRenderer = new ButtonRenderer();
        ButtonEditor buttonEditor = new ButtonEditor();

        int buttonColumnIndex = table_dichVu.getColumnCount() - 1;
        table_dichVu.getColumnModel().getColumn(buttonColumnIndex).setCellRenderer(buttonRenderer);
        table_dichVu.getColumnModel().getColumn(buttonColumnIndex).setCellEditor(buttonEditor);

        ButtonPanel buttonPanelEditor = buttonEditor.getButtonPanel();
        if (buttonPanelEditor != null) {
            buttonPanelEditor.getDeleteButton().addActionListener(e -> {
                int row = table_dichVu.getSelectedRow();
                if (row != -1) {
                    ((DefaultTableModel) table_dichVu.getModel()).removeRow(row);
                }
            });

            buttonPanelEditor.getDetailButton().addActionListener(e -> {
                int row = table_dichVu.getSelectedRow();
                if (row != -1) {
                }
            });
        }

        scrollPane.setViewportView(table_dichVu);
        return scrollPane;
    }

    private JPanel createPhaiPanel() {
        JPanel phaiPanel = new JPanel();
        phaiPanel.setBackground(Color.WHITE);
        phaiPanel.setLayout(null);

        JLabel title = new JLabel("Chọn phòng muốn trả");
        title.setFont(new Font("Times New Roman", Font.BOLD, 27));
        title.setBounds(243, 10, 260, 37);
        phaiPanel.add(title);

        JButton continueButton = new JButton("Tiếp tục");
        continueButton.setBackground(new Color(0, 255, 64));
        continueButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        continueButton.setBounds(597, 607, 154, 47);
        phaiPanel.add(continueButton);

        JPanel scrollPanel = createPhongTraScrollPane();
        phaiPanel.add(scrollPanel);

        return phaiPanel;
    }

    private JPanel createPhongTraScrollPane() {
        table_phongTra = new JTable();
        table_phongTra.setShowVerticalLines(false);
        table_phongTra.setShowHorizontalLines(false);
        table_phongTra.setBackground(Color.WHITE);
        table_phongTra.setFillsViewportHeight(true);
        table_phongTra.setRowHeight(30);
        table_phongTra.setBorder(null);
        table_phongTra.setShowGrid(false);

        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{
                        {false, "P102", "Single room", "5 ngày", "800.000", "4.800.000"},
                        {false, "P201", "Twin room", "5 ngày", "1.200.000", "6.000.000"},
                        {false, "P202", "Twin room", "5 ngày", "1.200.000", "6.000.000"}
                },
                new String[]{
                        "", "Mã phòng", "Loại phòng", "Thời gian", "Đơn giá", "Thành tiền"}
        ) {
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
            this.setBackground(new Color(220, 255, 220));
            add(checkBox);
            checkBox.setBackground(new Color(220, 255, 220));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    class ButtonPanel extends JPanel {
        public JButton deleteButton;
        public JButton detailButton;

        public ButtonPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 1, 10));

            deleteButton = new JButton(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\xoa.png"));
            deleteButton.setContentAreaFilled(false);
            deleteButton.setBorderPainted(false);
            add(deleteButton);

            detailButton = new JButton(new ImageIcon("C:\\Users\\TOILAXIEN\\OneDrive\\Máy tính\\Phat_trien_ung_dung\\Test\\BaiCuaXien\\src\\AnhTraPhong\\chitiet.png"));
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            panel.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
            return panel;
        }
    }

    class ButtonEditor extends AbstractCellEditor implements TableCellEditor {
        private ButtonPanel panel;

        public ButtonEditor() {
            panel = new ButtonPanel();
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
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
}