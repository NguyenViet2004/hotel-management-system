package dao;

import connectDB.ConnectDB;
import dao.NhanVien_Dao;
import entity.NhanVien;
import entity.TaiKhoan;

import java.sql.*;
import java.util.ArrayList;

public class TaiKhoan_Dao {

    public ArrayList<TaiKhoan> getAllTaiKhoan() {
        ArrayList<TaiKhoan> listTaiKhoan = new ArrayList<TaiKhoan>();
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM TaiKhoan";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String tenDangNhap = rs.getString(1);
                String matKhau = rs.getString(2);
                String trangThai = rs.getString(3);
                String maNV = rs.getString(4);
                NhanVien nhanVien = new NhanVien_Dao().timTheoMa(maNV);
                TaiKhoan taiKhoan = new TaiKhoan(tenDangNhap, matKhau, trangThai, nhanVien);
                listTaiKhoan.add(taiKhoan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTaiKhoan;
    }

    public boolean them(TaiKhoan taiKhoan) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "INSERT INTO TaiKhoan (tenDangNhap, matKhau, trangThai, maNV) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, taiKhoan.getTenDangNhap());
            stmt.setString(2, taiKhoan.getMatKhau());
            stmt.setString(3, taiKhoan.getTrangThai());
            stmt.setString(4, taiKhoan.getNhanVien().getMaNV());

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean sua(TaiKhoan taiKhoan) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "UPDATE TaiKhoan SET matKhau = ?, trangThai = ?, maNV = ? WHERE tenDangNhap = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, taiKhoan.getMatKhau());
            stmt.setString(2, taiKhoan.getTrangThai());
            stmt.setString(3, taiKhoan.getNhanVien().getMaNV());
            stmt.setString(4, taiKhoan.getTenDangNhap());

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean xoa(String tenDangNhap) {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tenDangNhap);

            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public TaiKhoan timTheoTenDangNhap(String tenDangNhap) {
        TaiKhoan taiKhoan = null;
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM TaiKhoan WHERE tenDangNhap = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, tenDangNhap);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String matKhau = rs.getString(2);
                String trangThai = rs.getString(3);
                String maNV = rs.getString(4);

                NhanVien nhanVien = new NhanVien_Dao().timTheoMa(maNV);
                taiKhoan = new TaiKhoan(tenDangNhap, matKhau, trangThai, nhanVien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taiKhoan;
    }
    
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
	                	NhanVien_Dao nhanVienDAO= new NhanVien_Dao();
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
    
}
