package dao_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.KhachHang;

public class KhachHang_DAO {
	 public KhachHang layKhachHangTheoMaDonDatPhong(String maDonDatPhong) {
	        KhachHang khachHang = null;
	        String sql = "SELECT kh.* FROM KhachHang kh "
	                   + "JOIN DonDatPhong ddp ON kh.maKH = ddp.maKH "
	                   + "WHERE ddp.maDonDatPhong = ?";

	        try (Connection con = ConnectDB.getConnection();
	             PreparedStatement stmt = con.prepareStatement(sql)) {

	            stmt.setString(1, maDonDatPhong);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next()) {
	                khachHang = new KhachHang(
	                    rs.getString("maKH"),
	                    rs.getString("hoTen"),
	                    rs.getString("sdt"),
	                    rs.getString("soCCCD")
	                );
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return khachHang;
	    }
}
