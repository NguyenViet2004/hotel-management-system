package dao_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.DichVu;
import entity_CuaXien.LoaiDichVu;

public class DichVu_DAO {
	public List<DichVu> getDichVuTheoPhieuVaLoai(String maPhieuDichVu, String tenLoaiDichVu) {
        List<DichVu> ds = new ArrayList<>();
        Connection con = ConnectDB.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String sql = """
                SELECT dv.maDV, dv.tenDV, dv.moTa, dv.giaDV,
                  ldv.maLoai, ldv.tenLoai
                FROM ChiTietPhieuDichVu ctpdv
                JOIN DichVu dv ON ctpdv.maDichVu = dv.maDV
                JOIN LoaiDichVu ldv ON dv.maLoai = ldv.maLoai
                WHERE ctpdv.maPhieuDichVu = ? AND ldv.tenLoai = ?
            """;

            stmt = con.prepareStatement(sql);
            stmt.setString(1, maPhieuDichVu);
            stmt.setString(2, tenLoaiDichVu);

            rs = stmt.executeQuery();
            while (rs.next()) {
            	String maDV = rs.getString("maDV");
            	String tenDV = rs.getString("tenDV"); 
            	String moTa = rs.getString("moTa");
            	double donGia = rs.getDouble("giaDV"); 


				String maLoai = rs.getString("maLoai"); 
				String tenLoai = rs.getString("tenLoai"); 
                LoaiDichVu loai = new LoaiDichVu(maLoai, tenLoai);

                DichVu dv = new DichVu(maDV, tenDV, moTa, donGia, loai);
                ds.add(dv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (stmt != null) stmt.close(); if (rs != null) rs.close(); } catch (SQLException e) {}
        }

        return ds;
    }



}
