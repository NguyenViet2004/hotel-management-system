package viet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

import entity.NhanVien;
import entity.TaiKhoan;

public class QuanLyTaiKhoan_Panel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private TaiKhoan_Dao tkDao = new TaiKhoan_Dao();
    private NhanVien_Dao nvDao = new NhanVien_Dao();
    private ArrayList<NhanVien> dsNhanVien;

    public QuanLyTaiKhoan_Panel() {
        setLayout(new BorderLayout());
        add(createMainPanel(), BorderLayout.CENTER);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Quản lý tài khoản", SwingConstants.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(title, BorderLayout.NORTH);

        String[] columns = {"Mã NV", "Họ tên", "Chức vụ", "Tài khoản", "Trạng thái", "Chức năng"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        loadTableData();

        table.getColumn("Chức năng").setCellRenderer(new ButtonRenderer());
        table.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), dsNhanVien, tkDao));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void loadTableData() {
        dsNhanVien = nvDao.getAllLeTan();
        for (NhanVien nv : dsNhanVien) {
            TaiKhoan tk = tkDao.getTaiKhoanByMaNV(nv.getMaNV());
            String tenTK = (tk != null) ? tk.getTenDangNhap() : "Chưa có";
            String trangThai = (tk != null) ? tk.getTrangThai() : "";
            model.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), nv.getChucVu(), tenTK, trangThai, "Quản lý"
            });
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setText("Quản lý");
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private int row;
        private ArrayList<NhanVien> dsNhanVien;
        private TaiKhoan_Dao tkDao;

        public ButtonEditor(JCheckBox checkBox, ArrayList<NhanVien> dsNhanVien, TaiKhoan_Dao tkDao) {
            super(checkBox);
            this.dsNhanVien = dsNhanVien;
            this.tkDao = tkDao;

            button = new JButton("Quản lý");
            button.setOpaque(true);
            button.addActionListener(e -> {
                fireEditingStopped();

                NhanVien nv = dsNhanVien.get(row);
                TaiKhoan tk = tkDao.getTaiKhoanByMaNV(nv.getMaNV());

                if (tk == null) {
                    taoTaiKhoan(nv, row);
                } else {
                    suaHoacKhoaTaiKhoan(tk, row);
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.row = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "Quản lý";
        }
    }

    private void taoTaiKhoan(NhanVien nv, int row) {
        String tenDangNhap = JOptionPane.showInputDialog(this, "Tên đăng nhập:");
        String matKhau = JOptionPane.showInputDialog(this, "Mật khẩu:");
        if (tenDangNhap != null && matKhau != null) {
            TaiKhoan tk = new TaiKhoan(tenDangNhap, matKhau, "Hoạt động", nv);
            if (tkDao.taoTaiKhoan(tk)) {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thành công!");
                model.setValueAt(tenDangNhap, row, 3);
                model.setValueAt("Hoạt động", row, 4);
            } else {
                JOptionPane.showMessageDialog(this, "Tạo tài khoản thất bại!");
            }
        }
    }

    private void suaHoacKhoaTaiKhoan(TaiKhoan tk, int row) {
        String[] options = {"Đổi mật khẩu", "Khóa tài khoản"};
        int choice = JOptionPane.showOptionDialog(this, "Chọn thao tác", "Quản lý tài khoản",
            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String matKhauMoi = JOptionPane.showInputDialog(this, "Nhập mật khẩu mới:");
            if (matKhauMoi != null && !matKhauMoi.isEmpty()) {
                tk.setMatKhau(matKhauMoi);
                if (tkDao.capNhatTaiKhoan(tk)) {
                    JOptionPane.showMessageDialog(this, "Cập nhật mật khẩu thành công!");
                }
            }
        } else if (choice == 1) {
            if (tkDao.khoaTaiKhoan(tk.getTenDangNhap())) {
                JOptionPane.showMessageDialog(this, "Tài khoản đã bị khóa!");
                model.setValueAt("Vô hiệu hóa", row, 4);
            }
        }
    }
}
