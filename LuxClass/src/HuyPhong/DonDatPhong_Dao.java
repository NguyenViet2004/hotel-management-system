package HuyPhong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DonDatPhong;
import entity.KhachHang;

public class DonDatPhong_Dao {
	public ArrayList<DonDatPhong> getDonDatPhongTheoSDT(String sdt) {
	    ArrayList<DonDatPhong> danhSach = new ArrayList<>();

	    String sql = "SELECT dp.maDonDatPhong, dp.ngayDatPhong, dp.ngayNhanPhong, dp.ngayTraPhong, " +
	                 "dp.soKhach, dp.tienCoc, dp.thoiGianCoc, dp.loaiDon, dp.trangThai, " +
	                 "kh.maKH, kh.hoTen, kh.sdt, kh.soCCCD, kh.email " +
	                 "FROM DonDatPhong dp " +
	                 "JOIN KhachHang kh ON dp.maKH = kh.maKH " +
	                 "WHERE kh.sdt = ?";

	    try (Connection conn = ConnectDB.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setString(1, sdt);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            // Tạo khách hàng
	            KhachHang kh = new KhachHang(
	                rs.getString("maKH"),
	                rs.getString("hoTen"),
	                rs.getString("sdt"),
	                rs.getString("soCCCD"),
	                rs.getString("email")
	            );

	            // Tạo đơn đặt phòng
	            DonDatPhong ddp = new DonDatPhong();
	            ddp.setMaDonDatPhong(rs.getString("maDonDatPhong"));
	            ddp.setNgayDatPhong(rs.getTimestamp("ngayDatPhong").toLocalDateTime());
	            ddp.setNgayNhanPhong(rs.getTimestamp("ngayNhanPhong").toLocalDateTime());
	            ddp.setNgayTraPhong(rs.getTimestamp("ngayTraPhong").toLocalDateTime());
	            ddp.setSoKhach(rs.getInt("soKhach"));
	            ddp.setTienCoc(rs.getDouble("tienCoc"));
	            ddp.setThoiGianCoc(rs.getTimestamp("thoiGianCoc").toLocalDateTime());
	            ddp.setLoaiDon(rs.getString("loaiDon"));
	            ddp.setTrangThai(rs.getString("trangThai"));
	            ddp.setKhachHang(kh);

	            danhSach.add(ddp);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return danhSach;
	}


}
