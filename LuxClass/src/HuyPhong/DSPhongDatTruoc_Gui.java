package HuyPhong;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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

    private JTabbedPane createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Times New Roman", Font.BOLD, 18));

        modelDaDat = new DefaultTableModel(new Object[][]{}, new String[]{
            "Mã đơn", "Tên khách", "SĐT", "Trạng thái", "Phòng"
        });

        modelTamThoi = new DefaultTableModel(new Object[][]{}, new String[]{
            "Mã đơn", "Tên khách", "SĐT", "Trạng thái", "Phòng"
        });

        tableDaDat = createTable(modelDaDat);
        JScrollPane scrollPane1 = new JScrollPane(tableDaDat);

        tableTamThoi = createTable(modelTamThoi);
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
                Object[] row = new Object[]{
                    ddp.getMaDonDatPhong(),
                    ddp.getKhachHang().getHoTen(),
                    ddp.getKhachHang().getSdt(),
                    ddp.getTrangThai(),
                    "P" + ddp.getChiTietPhong()
                };

                if ("Chưa nhận phòng".equals(ddp.getTrangThai()) || "DA_NHAN".equals(ddp.getTrangThai())) {
                    modelDaDat.addRow(row);
                } else {
                    modelTamThoi.addRow(row);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
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
