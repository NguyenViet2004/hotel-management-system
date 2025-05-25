package TraPhong_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;

public class TaiKhoan_DAO {
	public TaiKhoan getTaiKhoan(String tenDangNhap, String matKhau) {
	    TaiKhoan taiKhoan = null;
	    String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";

	    try (Connection con = ConnectDB.getConnection();  // Giả sử bạn có class Database quản lý connection
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, tenDangNhap);
	        stmt.setString(2, matKhau);

	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String trangThai = rs.getString("trangThai");
	                String maNV = rs.getString("maNV");

	                NhanVien nhanVien = null;
	                if (maNV != null) {
	                    // Gọi DAO của NhanVien để lấy thông tin nhân viên nếu cần
	                	NhanVien_DAO nhanVienDAO= new NhanVien_DAO();
	                    nhanVien = nhanVienDAO.getNhanVienByMa(maNV);
	                }

	                taiKhoan = new TaiKhoan(tenDangNhap, matKhau, trangThai, nhanVien);
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return taiKhoan;
	}
	
	public boolean kiemTraTaiKhoan(String tenDangNhap, String matKhau) {
	    String sql = "SELECT 1 FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";

	    try (Connection con = ConnectDB.getConnection();
	         PreparedStatement stmt = con.prepareStatement(sql)) {

	        stmt.setString(1, tenDangNhap);
	        stmt.setString(2, matKhau);

	        try (ResultSet rs = stmt.executeQuery()) {
	            return rs.next();  // Nếu có dòng trả về, nghĩa là tài khoản hợp lệ
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
    public boolean capNhatTaiKhoan(String mk, String tenDangNhap) throws SQLException {
        String sql = "UPDATE TaiKhoan SET matKhau = ? WHERE tenDangNhap = ?";
        try (  Connection con = ConnectDB.getConnection();
        		PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, mk);
            stmt.setString(2, tenDangNhap);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        }
    }

    public boolean kiemTraMatKhauCu(String tenDangNhap, String matKhauMoi) {
        String sql = "SELECT 1 FROM TaiKhoan WHERE tenDangNhap = ? AND matKhau = ?";

        try (Connection con = ConnectDB.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tenDangNhap);
            stmt.setString(2, matKhauMoi);  // So sánh mật khẩu mới với mật khẩu cũ trong DB

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Nếu có, nghĩa là mật khẩu mới bị trùng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
