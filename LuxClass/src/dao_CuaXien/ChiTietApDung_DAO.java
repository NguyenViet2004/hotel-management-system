package dao_CuaXien;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.ChiTietApDung;
import entity_CuaXien.DonDatPhong;
import entity_CuaXien.KhuyenMai;

public class ChiTietApDung_DAO {

    // Lấy tất cả chi tiết áp dụng
//    public List<ChiTietApDung> getAllChiTietApDung() {
//        List<ChiTietApDung> list = new ArrayList<>();
//        String sql = "SELECT * FROM ChiTietApDung";
//
//        try (Connection con = ConnectDB.getConnection();
//             Statement stmt = con.createStatement();
//             ResultSet rs = stmt.executeQuery(sql)) {
//
//            while (rs.next()) {
//                String maDonDatPhong = rs.getString("maDonDatPhong");
//                String maKhuyenMai = rs.getString("maKhuyenMai");
//                Float tongThanhToan = rs.getFloat("tongThanhToanSauApDung");
//
//                DonDatPhong ddp = new DonDatPhong(maDonDatPhong);
//                KhuyenMai km = new KhuyenMai(maKhuyenMai, null, null, null, null, null, null);
//
//                ChiTietApDung cta = new ChiTietApDung(maDonDatPhong, maKhuyenMai, tongThanhToan, ddp, km);
//                list.add(cta);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }

//    // Thêm một chi tiết áp dụng mới
//    public boolean addChiTietApDung(ChiTietApDung cta) {
//        String sql = "INSERT INTO ChiTietApDung (maDonDatPhong, maKhuyenMai, tongThanhToanSauApDung) VALUES (?, ?, ?)";
//
//        try (Connection con = ConnectDB.getConnection();
//             PreparedStatement stmt = con.prepareStatement(sql)) {
//
//            stmt.setString(1, cta.getMaDonDatPhong());
//            stmt.setString(2, cta.getMaKhuyenMai());
//            stmt.setFloat(3, cta.getTongThanhToanSauApDung());
//
//            return stmt.executeUpdate() > 0;
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }
	public boolean addChiTietApDung(ChiTietApDung cta) {
	    String sqlCheck = "SELECT tongThanhToanSauApDung FROM ChiTietApDung WHERE maDonDatPhong = ? AND maKhuyenMai = ?";
	    String sqlInsert = "INSERT INTO ChiTietApDung (maDonDatPhong, maKhuyenMai, tongThanhToanSauApDung) VALUES (?, ?, ?)";
	    String sqlUpdate = "UPDATE ChiTietApDung SET tongThanhToanSauApDung = ? WHERE maDonDatPhong = ? AND maKhuyenMai = ?";

	    try (Connection con = ConnectDB.getConnection()) {

	        // 1. Kiểm tra xem bản ghi đã tồn tại
	        try (PreparedStatement checkStmt = con.prepareStatement(sqlCheck)) {
	            checkStmt.setString(1, cta.getMaDonDatPhong());
	            checkStmt.setString(2, cta.getMaKhuyenMai());

	            try (ResultSet rs = checkStmt.executeQuery()) {
	                if (rs.next()) {
	                    // Đã tồn tại → cộng dồn
	                    float tongCu = rs.getFloat("tongThanhToanSauApDung");
	                    float tongMoi = tongCu + cta.getTongThanhToanSauApDung();

	                    try (PreparedStatement updateStmt = con.prepareStatement(sqlUpdate)) {
	                        updateStmt.setFloat(1, tongMoi);
	                        updateStmt.setString(2, cta.getMaDonDatPhong());
	                        updateStmt.setString(3, cta.getMaKhuyenMai());
	                        return updateStmt.executeUpdate() > 0;
	                    }

	                } else {
	                    // Chưa có → thêm mới
	                    try (PreparedStatement insertStmt = con.prepareStatement(sqlInsert)) {
	                        insertStmt.setString(1, cta.getMaDonDatPhong());
	                        insertStmt.setString(2, cta.getMaKhuyenMai());
	                        insertStmt.setFloat(3, cta.getTongThanhToanSauApDung());
	                        return insertStmt.executeUpdate() > 0;
	                    }
	                }
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}

    // Xóa một chi tiết áp dụng
    public boolean deleteChiTietApDung(String maDonDatPhong, String maKhuyenMai) {
        String sql = "DELETE FROM ChiTietApDung WHERE maDonDatPhong = ? AND maKhuyenMai = ?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maDonDatPhong);
            stmt.setString(2, maKhuyenMai);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

//    // Lấy danh sách áp dụng theo mã đơn đặt phòng
//    public List<ChiTietApDung> getByMaDonDatPhong(String maDonDatPhong) {
//        List<ChiTietApDung> list = new ArrayList<>();
//        String sql = "SELECT * FROM ChiTietApDung WHERE maDonDatPhong = ?";
//
//        try (Connection con = ConnectDB.getConnection();
//             PreparedStatement stmt = con.prepareStatement(sql)) {
//
//            stmt.setString(1, maDonDatPhong);
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                String maKhuyenMai = rs.getString("maKhuyenMai");
//                Float tongThanhToan = rs.getFloat("tongThanhToanSauApDung");
//
//                DonDatPhong ddp = new DonDatPhong(maDonDatPhong);
//                KhuyenMai km = new KhuyenMai(maKhuyenMai, null, null, null, null, null, null);
//
//                ChiTietApDung cta = new ChiTietApDung(maDonDatPhong, maKhuyenMai, tongThanhToan, ddp, km);
//                list.add(cta);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
}
