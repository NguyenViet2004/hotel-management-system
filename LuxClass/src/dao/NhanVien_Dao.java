package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import connectDB.ConnectDB;
import entity.NhanVien;

public class NhanVien_Dao {
	ArrayList<NhanVien> dsnv;
	NhanVien nv;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public NhanVien_Dao() {
		dsnv = new ArrayList<NhanVien>();
		nv = new NhanVien();
	}

	public ArrayList<NhanVien> getAllNhanVien() {

		try {
			Connection con = ConnectDB.getConnection();
			String sql = "Select * from nhanvien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNV = rs.getString(1);
				String hoTen = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				String sdt= rs.getString(4);
				String diaChi= rs.getString(5);
				String soCCCD= rs.getString(6);
				String chucVu= rs.getString(7);
				String caLamViec= rs.getString(8);

				NhanVien s = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
				dsnv.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Đóng kết nối
		}
		return dsnv;
	}

	public boolean them(NhanVien nv) {
		if(dsnv.contains(nv)) {
			return false;
		} else {
			Connection con = ConnectDB.getConnection();
			PreparedStatement stmt = null;
			int n = 0;
			try {
				stmt = con.prepareStatement("INSERT INTO NhanVien VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				stmt.setString(1, nv.getMaNV());
				stmt.setString(2, nv.getHoTen());
				stmt.setDate(3, java.sql.Date.valueOf(nv.getNgaySinh()));
				stmt.setString(4, nv.getSdt());
				stmt.setString(5, nv.getDiaChi());
				stmt.setString(6, nv.getSoCCCD());
				stmt.setString(7, nv.getChucVu());
				stmt.setString(8, nv.getCaLamViec());
				n = stmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return n > 0;
		}

	}

	public boolean sua(NhanVien nv) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update NhanVien set hoTen=?, ngaySinh=?, sdt=?, diaChi=?, soCCCD=?, chucVu=?, caLamViec=? where maNV=?");

			stmt.setString(1, nv.getHoTen());
			stmt.setDate(2, java.sql.Date.valueOf(nv.getNgaySinh()));
			stmt.setString(3, nv.getSdt());
			stmt.setString(4, nv.getDiaChi());
			stmt.setString(5, nv.getSoCCCD());
			stmt.setString(6, nv.getChucVu());
			stmt.setString(7, nv.getCaLamViec());
			stmt.setString(8, nv.getMaNV());
			n = stmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public boolean xoa(String manv) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("delete from nhanvien where maNV = ?");
			stmt.setString(1, manv);
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return n > 0;
	}

	public NhanVien timTheoMa(String manv) {
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		NhanVien nv = null;
		try {
			stmt = con.prepareStatement("select * from NhanVien where manv = ?");
			stmt.setString(1, manv);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String maNV = rs.getString(1);
				String hoTen = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				String sdt= rs.getString(4);
				String diaChi= rs.getString(5);
				String soCCCD= rs.getString(6);
				String chucVu= rs.getString(7);
				String caLamViec= rs.getString(8);

				nv = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nv;
	}

	public ArrayList<NhanVien> timTheoChucVu(String chucVu) {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		Connection con = ConnectDB.getConnection();
		PreparedStatement statement = null;
		try {
			String sql = "Select * from NhanVien where chucVu = ?";
			statement = con.prepareStatement(sql);
			statement.setString(1, chucVu);
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNV = rs.getString(1);
				String hoTen = rs.getString(2);
				LocalDate ngaySinh = rs.getDate(3).toLocalDate();
				String sdt= rs.getString(4);
				String diaChi= rs.getString(5);
				String soCCCD= rs.getString(6);
				String caLamViec= rs.getString(8);

				NhanVien nv = new NhanVien(maNV, hoTen, ngaySinh, sdt, diaChi, soCCCD, chucVu, caLamViec);
				dsnv.add(nv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dsnv;
	}
	
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
