package TraPhong_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import connectDB.ConnectDB;
import entity.NhanVien;

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
	 public NhanVien timNhanVien(String maNV, String soCCCD, String sdt, String tenDangNhap) throws SQLException {
	        String sql = "SELECT nv.* " +
	                     "FROM NhanVien nv " +
	                     "LEFT JOIN TaiKhoan tk ON nv.maNV = tk.maNV " +
	                     "WHERE nv.maNV = ? OR nv.soCCCD = ? OR nv.sdt = ? OR tk.tenDangNhap = ?";

	        try (Connection con = ConnectDB.getConnection(); 
	        	PreparedStatement stmt = con.prepareStatement(sql)) {
	            stmt.setString(1, maNV);
	            stmt.setString(2, soCCCD);
	            stmt.setString(3, sdt);
	            stmt.setString(4, tenDangNhap);

	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    NhanVien nv = new NhanVien();
	                    nv.setMaNV(rs.getString("maNV"));
	                    nv.setHoTen(rs.getString("hoTen"));
	                    nv.setNgaySinh(rs.getDate("ngaySinh").toLocalDate());
	                    nv.setSdt(rs.getString("sdt"));
	                    nv.setDiaChi(rs.getString("diaChi"));
	                    nv.setSoCCCD(rs.getString("soCCCD"));
	                    nv.setChucVu(rs.getString("chucVu"));
	                    nv.setCaLamViec(rs.getString("caLamViec"));
	                    return nv;
	                }
	            }
	        }

	        return null; // Không tìm thấy
	    }
}
