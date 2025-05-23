package viet;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoan_Dao {

    // Lấy tất cả tài khoản (JOIN với nhân viên)
    public ArrayList<TaiKhoan> getAllTaiKhoan() {
        ArrayList<TaiKhoan> dstk = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        try {
            String sql = "SELECT tk.tenDangNhap, tk.matKhau, tk.trangThai, nv.* " +
                         "FROM TaiKhoan tk JOIN NhanVien nv ON tk.maNV = nv.maNV";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                String trangThai = rs.getString("trangThai");

                // Lấy thông tin nhân viên
                String maNV = rs.getString("maNV");
                String hoTen = rs.getString("hoTen");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String sdt = rs.getString("sdt");
                String diaChi = rs.getString("diaChi");
                String soCCCD = rs.getString("soCCCD");
                String chucVu = rs.getString("chucVu");
                String caLamViec = rs.getString("caLamViec");

                NhanVien nv = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
                TaiKhoan tk = new TaiKhoan(tenDangNhap, matKhau, trangThai, nv);
                dstk.add(tk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dstk;
    }

    // Tìm tài khoản theo mã nhân viên
    public TaiKhoan getTaiKhoanByMaNV(String maNV) {
        Connection con = ConnectDB.getConnection();
        TaiKhoan tk = null;
        try {
            String sql = "SELECT tk.tenDangNhap, tk.matKhau, tk.trangThai, nv.* " +
                         "FROM TaiKhoan tk JOIN NhanVien nv ON tk.maNV = nv.maNV " +
                         "WHERE nv.maNV = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");
                String trangThai = rs.getString("trangThai");

                // Thông tin nhân viên
                String hoTen = rs.getString("hoTen");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String sdt = rs.getString("sdt");
                String diaChi = rs.getString("diaChi");
                String soCCCD = rs.getString("soCCCD");
                String chucVu = rs.getString("chucVu");
                String caLamViec = rs.getString("caLamViec");

                NhanVien nv = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
                tk = new TaiKhoan(tenDangNhap, matKhau, trangThai, nv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tk;
    }

    // Tạo tài khoản mới
    public boolean taoTaiKhoan(TaiKhoan tk) {
        Connection con = ConnectDB.getConnection();
        int n = 0;
        try {
            String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, trangThai, maNV) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tk.getTenDangNhap());
            stmt.setString(2, tk.getMatKhau());
            stmt.setString(3, tk.getTrangThai());
            stmt.setString(4, tk.getNhanVien().getMaNV());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // Cập nhật tài khoản
    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        Connection con = ConnectDB.getConnection();
        int n = 0;
        try {
            String sql = "UPDATE TaiKhoan SET matKhau = ?, trangThai = ? WHERE tenDangNhap = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tk.getMatKhau());
            stmt.setString(2, tk.getTrangThai());
            stmt.setString(3, tk.getTenDangNhap());
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // Khóa tài khoản
    public boolean khoaTaiKhoan(String tenDangNhap) {
        Connection con = ConnectDB.getConnection();
        int n = 0;
        try {
            String sql = "UPDATE TaiKhoan SET trangThai = N'Vô hiệu hóa' WHERE tenDangNhap = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tenDangNhap);
            n = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
}
