package dao_CuaXien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.LoaiPhong;
import entity_CuaXien.Phong;

public class Phong_DAO {

    public List<Phong> getAllPhong() {
        List<Phong> danhSach = new ArrayList<>();
        String sql = "SELECT p.soPhong, p.trangThai, lp.*, p.moTa " +
                     "FROM Phong p JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong";

        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                // Dữ liệu phòng
                String soPhong = rs.getString("soPhong");
                String trangThai = rs.getString("trangThai");
                String mota= rs.getString("moTa");

                // Dữ liệu loại phòng đầy đủ
                String maLoai = rs.getString("maLoaiPhong");
                String tenLoai = rs.getString("tenLoai");
                int soLuong = rs.getInt("soLuong");
                float dienTich = rs.getFloat("dienTich");
                double giaGio = rs.getDouble("giaTheoGio");
                double giaNgay = rs.getDouble("giaTheoNgay");
                double giaDem = rs.getDouble("giaTheoDem");
                double phuThu = rs.getDouble("phuThuQuaGio");

                // Tạo LoaiPhong
                LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

                // Tạo Phong
                Phong p = new Phong(soPhong, trangThai, lp, mota);
                danhSach.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }
    public List<Phong> getPhongTheoMaDonDatPhong(String maDonDatPhong) {
        List<Phong> dsPhong = new ArrayList<>();
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con =  ConnectDB.getConnection();
            String sql = "SELECT p.soPhong, p.trangThai, lp.*, p.moTa " +
                    "FROM Phong p " +
                    "JOIN ChiTietDonDatPhong ctd ON p.soPhong = ctd.soPhong " +
                    "JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong " +
                    "WHERE ctd.maDonDatPhong = ? AND p.trangThai = ?";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, maDonDatPhong);
            stmt.setString(2, "Đã đặt"); 
            rs = stmt.executeQuery();

            while (rs.next()) {
                // Dữ liệu phòng
                String soPhong = rs.getString("soPhong");
                String trangThai = rs.getString("trangThai");
                String mota= rs.getString("moTa");

                // Dữ liệu loại phòng đầy đủ
                String maLoai = rs.getString("maLoaiPhong");
                String tenLoai = rs.getString("tenLoai");
                int soLuong = rs.getInt("soLuong");
                float dienTich = rs.getFloat("dienTich");
                double giaGio = rs.getDouble("giaTheoGio");
                double giaNgay = rs.getDouble("giaTheoNgay");
                double giaDem = rs.getDouble("giaTheoDem");
                double phuThu = rs.getDouble("phuThuQuaGio");

                // Tạo LoaiPhong
                LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

                // Tạo Phong
                Phong p = new Phong(soPhong, trangThai, lp, mota);
                dsPhong.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }

        return dsPhong;
    }
    public Phong getPhongBySoPhong(String soPhong) {
        Phong phong = null;
        String sql = "SELECT p.soPhong, p.trangThai, p.moTa, lp.maLoaiPhong, lp.tenLoaiPhong, lp.donGia " +
                     "FROM Phong p JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong " +
                     "WHERE p.soPhong = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, soPhong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
              
                String trangThai = rs.getString("trangThai");
                String mota= rs.getString("moTa");

                // Dữ liệu loại phòng đầy đủ
                String maLoai = rs.getString("maLoaiPhong");
                String tenLoai = rs.getString("tenLoai");
                int soLuong = rs.getInt("soLuong");
                float dienTich = rs.getFloat("dienTich");
                double giaGio = rs.getDouble("giaTheoGio");
                double giaNgay = rs.getDouble("giaTheoNgay");
                double giaDem = rs.getDouble("giaTheoDem");
                double phuThu = rs.getDouble("phuThuQuaGio");

                // Tạo LoaiPhong
                LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

                // Tạo Phong
                Phong p = new Phong(soPhong, trangThai, lp, mota);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return phong;
    }
    public List<Phong> getPhongChuaThanhToan() {
        List<Phong> danhSach = new ArrayList<>();
        String sql = "SELECT p.soPhong, p.trangThai, lp.*, p.moTa " +
                     "FROM Phong p " +
                     "JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong " +
                     "JOIN ChiTietDonDatPhong ctd ON p.soPhong = ctd.soPhong " +
                     "JOIN DonDatPhong ddp ON ctd.maDonDatPhong = ddp.maDonDatPhong "+
                     "WHERE ddp.trangThai = ? AND p.trangThai = ?"; // "Chưa thanh toán"

        try (
            Connection conn = ConnectDB.getConnection(); // Thay ConnectDB bằng class kết nối DB của bạn
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, "Chưa thanh toán");
            stmt.setString(2, "Đã đặt");   
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Dữ liệu phòng
                    String soPhong = rs.getString("soPhong");
                    String trangThai = rs.getString("trangThai");
                    String mota = rs.getString("moTa");

                    // Dữ liệu loại phòng đầy đủ
                    String maLoai = rs.getString("maLoaiPhong");
                    String tenLoai = rs.getString("tenLoai");
                    int soLuong = rs.getInt("soLuong");
                    float dienTich = rs.getFloat("dienTich");
                    double giaGio = rs.getDouble("giaTheoGio");
                    double giaNgay = rs.getDouble("giaTheoNgay");
                    double giaDem = rs.getDouble("giaTheoDem");
                    double phuThu = rs.getDouble("phuThuQuaGio");

                    // Tạo LoaiPhong
                    LoaiPhong lp = new LoaiPhong(maLoai, tenLoai, soLuong, dienTich, giaGio, giaNgay, giaDem, phuThu);

                    // Tạo Phong
                    Phong p = new Phong(soPhong, trangThai, lp, mota);
                    danhSach.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSach;
    }
    
    public boolean setTrangThaiPhong(String soPhong, String trangThai) {
        String sql = "UPDATE Phong SET trangThai = ? WHERE soPhong = ?";
        
        try (
            Connection conn = ConnectDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, trangThai);
            stmt.setString(2, soPhong);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // true nếu có ít nhất 1 dòng bị ảnh hưởng
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
