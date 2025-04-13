package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiPhong;
import entity.Phong;

public class Phong_Dao {
	private ArrayList<Phong> dsp;
    
    public Phong_Dao() {
        dsp = new ArrayList<Phong>();
    }

    public ArrayList<Phong> getAllPhong() {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM Phong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String soPhong = rs.getString(1);
                String trangThai = rs.getString(2);
                LoaiPhong loaiPhong = new LoaiPhong(rs.getString(3));
                String moTa = rs.getString(4);

                Phong p = new Phong(soPhong,trangThai,loaiPhong,moTa);
                dsp.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsp;
    }
    
    public ArrayList<Phong> getDanhSachPhongTheoLoai(String maLoaiPhong) {
    	ArrayList<Phong> danhSachPhong = new ArrayList<>();
        String sql = "SELECT * FROM Phong WHERE loaiPhong = ?";
        
        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, maLoaiPhong);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                String soPhong = rs.getString("soPhong");

                danhSachPhong.add(new Phong(soPhong));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachPhong;
    }

}
