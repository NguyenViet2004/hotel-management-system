package viet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVien_Dao {
	public ArrayList<NhanVien> getAllLeTan() {
	    ArrayList<NhanVien> dsLeTan = new ArrayList<>();
	    Connection con = ConnectDB.getConnection();
	    PreparedStatement stmt = null;
	    try {
	        String sql = "SELECT * FROM NhanVien WHERE chucVu = N'Lễ tân'";
	        stmt = con.prepareStatement(sql);
	        ResultSet rs = stmt.executeQuery();
	        while (rs.next()) {
	            String maNV = rs.getString("maNV");
	            String hoTen = rs.getString("hoTen");
	            LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
	            String sdt = rs.getString("sdt");
	            String diaChi = rs.getString("diaChi");
	            String soCCCD = rs.getString("soCCCD");
	            String chucVu = rs.getString("chucVu");
	            String caLamViec = rs.getString("caLamViec");

	            NhanVien nv = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
	            dsLeTan.add(nv);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return dsLeTan;
	}

}
