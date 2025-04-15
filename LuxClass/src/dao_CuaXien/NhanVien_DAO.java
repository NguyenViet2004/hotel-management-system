package dao_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.NhanVien;

public class NhanVien_DAO {
	public NhanVien getNhanVienByMa(String maNV) {
	    NhanVien nhanVien = null;
	    String sql = "SELECT * FROM NhanVien WHERE maNV = ?";

	    try (Connection con = ConnectDB.getConnection();  // Kết nối từ class Database của bạn
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, maNV);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String hoTen = rs.getString("hoTen");
	                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
	                String sdt = rs.getString("sdt");
	                String diaChi = rs.getString("diaChi");
	                String soCCCD = rs.getString("soCCCD");
	                String chucVu = rs.getString("chucVu");
	                String caLamViec = rs.getString("caLamViec");

	                nhanVien = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return nhanVien;
	}

}
