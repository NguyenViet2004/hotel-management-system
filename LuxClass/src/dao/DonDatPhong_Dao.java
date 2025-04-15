package dao;


import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

import connectDB.ConnectDB;
import entity.DonDatPhong;
import entity.KhachHang;
import entity.NhanVien;

public class DonDatPhong_Dao {
	private KhachHang_Dao khachHang_Dao = new KhachHang_Dao();
	private NhanVien_Dao nhanVien_Dao = new NhanVien_Dao();

	public DonDatPhong timDonTheoMa(String maDonDatPhong) {
		DonDatPhong don = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			conn = ConnectDB.getConnection();
			String sql = "SELECT * FROM DonDatPhong WHERE maDonDatPhong = ?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, maDonDatPhong);
			rs = ps.executeQuery();

			if (rs.next()) {
				don = new DonDatPhong();

				don.setMaDonDatPhong(rs.getString("maDonDatPhong"));

				// Ngày nhận, trả -> convert sang LocalDateTime
				Timestamp tsNhan = rs.getTimestamp("ngayNhanPhong");
				Timestamp tsTra = rs.getTimestamp("ngayTraPhong");
				if (tsNhan != null)
					don.setNgayNhanPhong(tsNhan.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
				if (tsTra != null)
					don.setNgayTraPhong(tsTra.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

				don.setSoKhach(rs.getInt("soKhach"));
				don.setTienCoc(rs.getDouble("tienCoc"));
				don.setLoaiDon(rs.getString("loaiDon"));
				don.setTrangThai(rs.getString("trangThai"));

				// Gán thông tin khách hàng và nhân viên từ DAO
				String maKH = rs.getString("maKH");
				String maNV = rs.getString("maNV");

				KhachHang kh = khachHang_Dao.timTheoMa(maKH);
				NhanVien nv = nhanVien_Dao.timTheoMa(maNV);

				don.setKhachHang(kh);
				don.setNhanVien(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) rs.close();
				if (ps != null) ps.close();
				if (conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return don;
	}
	public boolean xoaDonDatPhong(String maDonDatPhong) {
        Connection con = ConnectDB.getConnection();
        String sql = "DELETE FROM DonDatPhong WHERE maDonDatPhong = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maDonDatPhong);
            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

