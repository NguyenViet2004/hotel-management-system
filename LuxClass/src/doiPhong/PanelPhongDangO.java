package doiPhong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import dao.DonDatPhong_Dao;
import entity.DonDatPhong;

public class PanelPhongDangO extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtTimKiem;

    public PanelPhongDangO() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(Color.WHITE);

        // Tiêu đề
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Tìm kiếm phòng đang ở", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Serif", Font.BOLD, 26));
        topPanel.add(lblTitle, BorderLayout.NORTH);

        // Thanh tìm kiếm
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 15));
        searchPanel.setBackground(Color.WHITE);
        JLabel lblSoPhong = new JLabel("Số phòng:");
        lblSoPhong.setFont(new Font("Arial", Font.PLAIN, 16));
        txtTimKiem = new JTextField(15);
        JButton btnTimKiem = new JButton("Tìm kiếm");
        searchPanel.add(lblSoPhong);
        searchPanel.add(txtTimKiem);
        searchPanel.add(btnTimKiem);

        topPanel.add(searchPanel, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu
        String[] columnNames = {"Mã đơn", "Số phòng", "Ngày nhận", "Ngày trả", "Hành động"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };

        table = new JTable(model);
        table.setRowHeight(30);

        table.getColumn("Hành động").setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                return (Component) value;
            }
        });

        table.getColumn("Hành động").setCellEditor(new ButtonEditor());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        add(scrollPane, BorderLayout.CENTER);

        taiDuLieuPhongDangO(null);

        // Sự kiện tìm kiếm
        btnTimKiem.addActionListener(e -> {
            String soPhongCanTim = txtTimKiem.getText().trim();
            taiDuLieuPhongDangO(soPhongCanTim.isEmpty() ? null : soPhongCanTim);
        });
    }

    private void taiDuLieuPhongDangO(String soPhongTim) {
        model.setRowCount(0);
        ChiTietSuDungPhong_Dao ctDAO = new ChiTietSuDungPhong_Dao();
        DonDatPhong_Dao donDao = new DonDatPhong_Dao();
        ArrayList<ChiTietSuDungPhong> danhSach = ctDAO.getDanhSachPhongDangO();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (ChiTietSuDungPhong ct : danhSach) {
            if (soPhongTim != null && !ct.getSoPhong().equalsIgnoreCase(soPhongTim))
                continue;

            DonDatPhong don = donDao.getDonDatPhongTheoMa(ct.getMaDonDatPhong());
            if (don != null && "Nhận phòng".equals(don.getTrangThai())) {
                JButton btnDoiPhong = new JButton("Đổi phòng");

                btnDoiPhong.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new DoiPhongDialog(null, ct.getMaDonDatPhong(), ct.getSoPhong(), ct.getNgayKetThuc()).setVisible(true);
                        taiDuLieuPhongDangO(null);
                    }
                });

                model.addRow(new Object[]{
                    ct.getMaDonDatPhong(),
                    ct.getSoPhong(),
                    ct.getNgayBatDau().format(dtf),
                    ct.getNgayKetThuc().format(dtf),
                    btnDoiPhong
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tìm đơn đặt phòng theo số phòng");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(950, 550);
            frame.setLocationRelativeTo(null);

            PanelPhongDangO panel = new PanelPhongDangO();
            frame.add(panel);

            frame.setVisible(true);
        });
    }
}
