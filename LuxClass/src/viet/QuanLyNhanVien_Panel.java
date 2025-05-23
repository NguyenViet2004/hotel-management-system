// File: QuanLyNhanVien_Panel.java
package viet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.IntConsumer;
import java.util.List;
import dao.NhanVien_Dao;
import entity.NhanVien;

public class QuanLyNhanVien_Panel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();

    public QuanLyNhanVien_Panel() {
        setLayout(new BorderLayout());
        add(createNhanVienPanel(), BorderLayout.CENTER);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<NhanVien> list = nhanVienDao.getAllNhanVien();
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMaNV(), nv.getHoTen(), sdf.format(java.sql.Date.valueOf(nv.getNgaySinh())),
                nv.getSdt(), nv.getSoCCCD(), nv.getDiaChi(), nv.getChucVu(), nv.getCaLamViec(), "Sửa"
            });
        }
    }

    private JPanel createNhanVienPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(Color.WHITE);
        JLabel title = new JLabel("Quản lý nhân viên");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        JButton btnThem = new JButton("+ Thêm nhân viên");
        btnThem.addActionListener(e -> openAddDialog());
        header.add(title, BorderLayout.WEST);
        header.add(btnThem, BorderLayout.EAST);

        String[] columns = {"Mã NV", "Họ tên", "Ngày sinh", "SĐT", "CCCD", "Địa chỉ", "Chức vụ", "Ca làm", "Sửa"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getColumn("Sửa").setCellRenderer(new ButtonRenderer("Sửa"));
        table.getColumn("Sửa").setCellEditor(new ButtonEditor(new JCheckBox(), "Sửa", this::openEditDialog));

        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private void openAddDialog() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Thêm nhân viên", true);
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfMa = new JTextField();
        JTextField tfHoTen = new JTextField();
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        JTextField tfSdt = new JTextField();
        JTextField tfCCCD = new JTextField();
        JTextField tfDiaChi = new JTextField();
        JComboBox<String> cbChucVu = new JComboBox<>(new String[]{"Kế toán", "Lễ tân", "Bếp", "Buồng phòng"});
        JComboBox<String> cbCa = new JComboBox<>(new String[]{"Ca 1", "Ca 2", "Ca 3"});

        String[] labels = {"Mã nhân viên", "Họ tên", "Ngày sinh", "Số điện thoại", "CCCD", "Địa chỉ", "Chức vụ", "Ca làm việc"};
        Component[] fields = {tfMa, tfHoTen, dateChooser, tfSdt, tfCCCD, tfDiaChi, cbChucVu, cbCa};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            dialog.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            dialog.add(fields[i], gbc);
        }

        JButton btnLuu = new JButton("Lưu");
        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        btnLuu.addActionListener(e -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate == null) return;

            NhanVien nv = new NhanVien(
                tfMa.getText(), tfHoTen.getText(),
                selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                tfSdt.getText(), tfDiaChi.getText(), tfCCCD.getText(),
                cbChucVu.getSelectedItem().toString(), cbCa.getSelectedItem().toString()
            );

            if (nhanVienDao.them(nv)) {
                model.addRow(new Object[]{
                    nv.getMaNV(), nv.getHoTen(), new SimpleDateFormat("dd/MM/yyyy").format(selectedDate),
                    nv.getSdt(), nv.getSoCCCD(), nv.getDiaChi(), nv.getChucVu(), nv.getCaLamViec(), "Sửa"
                });
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Thêm thất bại (trùng mã?)");
            }
        });
        dialog.add(btnLuu, gbc);
        dialog.setVisible(true);
    }

    private void openEditDialog(int row) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Sửa nhân viên", true);
        dialog.setSize(450, 500);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField tfMa = new JTextField((String) model.getValueAt(row, 0));
        JTextField tfHoTen = new JTextField((String) model.getValueAt(row, 1));
        JDateChooser dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        try {
            Date existingDate = new SimpleDateFormat("dd/MM/yyyy").parse((String) model.getValueAt(row, 2));
            dateChooser.setDate(existingDate);
        } catch (Exception ex) {
            dateChooser.setDate(null);
        }
        JTextField tfSdt = new JTextField((String) model.getValueAt(row, 3));
        JTextField tfCCCD = new JTextField((String) model.getValueAt(row, 4));
        JTextField tfDiaChi = new JTextField((String) model.getValueAt(row, 5));
        JComboBox<String> cbChucVu = new JComboBox<>(new String[]{"Kế toán", "Lễ tân", "Bếp", "Buồng phòng"});
        cbChucVu.setSelectedItem(model.getValueAt(row, 6));
        JComboBox<String> cbCa = new JComboBox<>(new String[]{"Ca 1", "Ca 2", "Ca 3"});
        cbCa.setSelectedItem(model.getValueAt(row, 7));

        String[] labels = {"Mã nhân viên", "Họ tên", "Ngày sinh", "Số điện thoại", "CCCD", "Địa chỉ", "Chức vụ", "Ca làm việc"};
        Component[] fields = {tfMa, tfHoTen, dateChooser, tfSdt, tfCCCD, tfDiaChi, cbChucVu, cbCa};

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0; gbc.gridy = i;
            dialog.add(new JLabel(labels[i]), gbc);
            gbc.gridx = 1;
            dialog.add(fields[i], gbc);
        }

        JButton btnCapNhat = new JButton("Cập nhật");
        gbc.gridx = 0; gbc.gridy = labels.length; gbc.gridwidth = 2;
        btnCapNhat.addActionListener(e -> {
            Date selectedDate = dateChooser.getDate();
            if (selectedDate == null) return;

            NhanVien nv = new NhanVien(
                tfMa.getText(), tfHoTen.getText(),
                selectedDate.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate(),
                tfSdt.getText(), tfDiaChi.getText(), tfCCCD.getText(),
                cbChucVu.getSelectedItem().toString(), cbCa.getSelectedItem().toString()
            );

            if (nhanVienDao.sua(nv)) {
                model.setValueAt(nv.getMaNV(), row, 0);
                model.setValueAt(nv.getHoTen(), row, 1);
                model.setValueAt(new SimpleDateFormat("dd/MM/yyyy").format(selectedDate), row, 2);
                model.setValueAt(nv.getSdt(), row, 3);
                model.setValueAt(nv.getSoCCCD(), row, 4);
                model.setValueAt(nv.getDiaChi(), row, 5);
                model.setValueAt(nv.getChucVu(), row, 6);
                model.setValueAt(nv.getCaLamViec(), row, 7);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại!");
            }
        });
        dialog.add(btnCapNhat, gbc);
        dialog.setVisible(true);
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer(String label) {
        setText(label);
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
    private boolean isPushed;
    private int row;
    private final IntConsumer callback;

    public ButtonEditor(JCheckBox checkBox, String label, IntConsumer callback) {
        super(checkBox);
        this.callback = callback;
        button = new JButton(label);
        button.setOpaque(true);
        button.addActionListener(e -> {
            fireEditingStopped();
            callback.accept(row);
        });
    }
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                  boolean isSelected, int row, int column) {
        this.row = row;
        return button;
    }
    public Object getCellEditorValue() {
        return "Sửa";
    }
}
