// File: QuanLyNhanVien_Panel.java
package quanLy;

import javax.swing.*;
import javax.swing.table.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import dao.NhanVien_Dao;
import entity.NhanVien;

public class QuanLyNhanVien_Panel extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField tfMaNV, tfHoTen, tfSdt, tfCCCD, tfDiaChi;
    private JComboBox<String> cbChucVu, cbCa;
    private JDateChooser dateChooser;
    private NhanVien_Dao nhanVienDao = new NhanVien_Dao();

    public QuanLyNhanVien_Panel() {
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(null);
        contentPanel.setBackground(new Color(240, 240, 240));
        add(contentPanel, BorderLayout.CENTER);

        JLabel lblTitle = new JLabel("Quản lý nhân viên");
        lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblTitle.setBounds(450, 0, 300, 30);
        contentPanel.add(lblTitle);

        JPanel formPanel = new JPanel(null);
        formPanel.setBounds(30, 40, 1140, 250);
        formPanel.setBackground(Color.WHITE);
        contentPanel.add(formPanel);

        // Trái
        int labelWidth = 130, fieldWidth = 250, height = 30, spacingY = 40;
        int leftX = 40, rightX = 600;

        JLabel lbMaNV = new JLabel("Mã nhân viên:");
        lbMaNV.setBounds(leftX, 20, labelWidth, height);
        tfMaNV = new JTextField();
        tfMaNV.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        tfMaNV.setBounds(leftX + labelWidth + 10, 20, fieldWidth, height);

        JLabel lbHoTen = new JLabel("Họ tên:");
        lbHoTen.setBounds(leftX, 20 + spacingY, labelWidth, height);
        tfHoTen = new JTextField();
        tfHoTen.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        tfHoTen.setBounds(leftX + labelWidth + 10, 20 + spacingY, fieldWidth, height);

        JLabel lbNgaySinh = new JLabel("Ngày sinh:");
        lbNgaySinh.setBounds(leftX, 20 + 2 * spacingY, labelWidth, height);
        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        dateChooser.setBounds(leftX + labelWidth + 10, 20 + 2 * spacingY, fieldWidth, height);

        JLabel lbSdt = new JLabel("SĐT:");
        lbSdt.setBounds(leftX, 20 + 3 * spacingY, labelWidth, height);
        tfSdt = new JTextField();
        tfSdt.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        tfSdt.setBounds(leftX + labelWidth + 10, 20 + 3 * spacingY, fieldWidth, height);

        // Phải
        JLabel lbCCCD = new JLabel("CCCD:");
        lbCCCD.setBounds(rightX, 20, labelWidth, height);
        tfCCCD = new JTextField();
        tfCCCD.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        tfCCCD.setBounds(rightX + labelWidth + 10, 20, fieldWidth, height);

        JLabel lbDiaChi = new JLabel("Địa chỉ:");
        lbDiaChi.setBounds(rightX, 20 + spacingY, labelWidth, height);
        tfDiaChi = new JTextField();
        tfDiaChi.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        tfDiaChi.setBounds(rightX + labelWidth + 10, 20 + spacingY, fieldWidth, height);

        JLabel lbChucVu = new JLabel("Chức vụ:");
        lbChucVu.setBounds(rightX, 20 + 2 * spacingY, labelWidth, height);
        cbChucVu = new JComboBox<>(new String[]{"Kế toán", "Lễ tân", "Bếp", "Buồng phòng"});
        cbChucVu.setBounds(rightX + labelWidth + 10, 20 + 2 * spacingY, fieldWidth, height);

        JLabel lbCa = new JLabel("Ca làm việc:");
        lbCa.setBounds(rightX, 20 + 3 * spacingY, labelWidth, height);
        cbCa = new JComboBox<>(new String[]{"Ca 1", "Ca 2", "Ca 3"});
        cbCa.setBounds(rightX + labelWidth + 10, 20 + 3 * spacingY, fieldWidth, height);

        JButton btnThem = new JButton("Thêm nhân viên");
        btnThem.setFont(new Font("Times New Roman", Font.BOLD, 18));
        btnThem.setBackground(new Color(0, 255, 128));
        btnThem.setBounds(rightX + labelWidth + 10, 20 + 4 * spacingY + 5, 180, 35);
        btnThem.addActionListener(this::handleAdd);

        // add vào formPanel
        Component[] components = {
            lbMaNV, tfMaNV, lbHoTen, tfHoTen, lbNgaySinh, dateChooser, lbSdt, tfSdt,
            lbCCCD, tfCCCD, lbDiaChi, tfDiaChi, lbChucVu, cbChucVu, lbCa, cbCa, btnThem
        };
        for (Component c : components) formPanel.add(c);

        JPanel tablePanel = new JPanel(null);
        tablePanel.setBounds(30, 300, 1140, 360);
        tablePanel.setBackground(Color.WHITE);
        contentPanel.add(tablePanel);

        JLabel lblDanhSach = new JLabel("Danh sách nhân viên");
        lblDanhSach.setFont(new Font("Times New Roman", Font.BOLD, 24));
        lblDanhSach.setBounds(420, 10, 300, 30);
        tablePanel.add(lblDanhSach);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(20, 50, 1100, 280);
        tablePanel.add(scrollPane);

        String[] columns = {"Mã NV", "Họ tên", "Ngày sinh", "SĐT", "CCCD", "Địa chỉ", "Chức vụ", "Ca làm"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("Times New Roman", Font.PLAIN, 16));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 15));
        header.setPreferredSize(new Dimension(header.getWidth(), 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        scrollPane.setViewportView(table);
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        List<NhanVien> list = nhanVienDao.getAllNhanVien();
        for (NhanVien nv : list) {
            model.addRow(new Object[]{
                nv.getMaNV(), nv.getHoTen(),
                sdf.format(java.sql.Date.valueOf(nv.getNgaySinh())),
                nv.getSdt(), nv.getSoCCCD(), nv.getDiaChi(), nv.getChucVu(), nv.getCaLamViec()
            });
        }
    }

    private void handleAdd(ActionEvent e) {
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh hợp lệ!");
            return;
        }
        NhanVien nv = new NhanVien(
            tfMaNV.getText(), tfHoTen.getText(),
            selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
            tfSdt.getText(), tfDiaChi.getText(), tfCCCD.getText(),
            cbChucVu.getSelectedItem().toString(), cbCa.getSelectedItem().toString()
        );
        if (nhanVienDao.them(nv)) {
            model.addRow(new Object[]{
                nv.getMaNV(), nv.getHoTen(), new SimpleDateFormat("dd/MM/yyyy").format(selectedDate),
                nv.getSdt(), nv.getSoCCCD(), nv.getDiaChi(), nv.getChucVu(), nv.getCaLamViec()
            });
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
        } else {
            JOptionPane.showMessageDialog(this, "Thêm thất bại (trùng mã?)");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản lý nhân viên");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1220, 720);
        frame.setContentPane(new QuanLyNhanVien_Panel());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
