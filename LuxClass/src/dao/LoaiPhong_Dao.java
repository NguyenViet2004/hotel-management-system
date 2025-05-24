package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.KhachHang;
import entity.LoaiPhong;

public class LoaiPhong_Dao {
private ArrayList<LoaiPhong> dslp;
    
    public LoaiPhong_Dao() {
        dslp = new ArrayList<LoaiPhong>();
    }

    public ArrayList<LoaiPhong> getAllLoaiPhong() {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "SELECT * FROM LoaiPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
                String maLoaiPhong = rs.getString(1);
                String tenLoai = rs.getString(2);
                int soLuong = rs.getInt(3);
                float dienTich = rs.getFloat(4);
                double giaTheoGio = rs.getDouble(5);
                double giaTheoNgay = rs.getDouble(6);
                double giaTheoDem = rs.getDouble(7);
                double phuThuQuaGio = rs.getDouble(8);

                LoaiPhong lp = new LoaiPhong(maLoaiPhong,tenLoai,soLuong,dienTich,giaTheoGio,giaTheoNgay,giaTheoDem,phuThuQuaGio);
                dslp.add(lp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dslp;
    }
    
    public LoaiPhong getLoaiPhongTheoMa(String maLoaiPhong) {
        LoaiPhong loai = null;
        Connection conn = ConnectDB.getConnection();
        String sql = "SELECT * FROM LoaiPhong WHERE maLoaiPhong = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maLoaiPhong);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String tenLoai = rs.getString("tenLoai");
                int soLuong = rs.getInt("soLuong");
                float dienTich = rs.getFloat("dienTich");
                double giaTheoGio = rs.getDouble("giaTheoGio");
                double giaTheoNgay = rs.getDouble("giaTheoNgay");
                double giaTheoDem = rs.getDouble("giaTheoDem");
                double phuThuQuaGio = rs.getDouble("phuThuQuaGio");

                loai = new LoaiPhong(maLoaiPhong, tenLoai, soLuong, dienTich,
                                      giaTheoGio, giaTheoNgay, giaTheoDem, phuThuQuaGio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return loai;
    }
    
    public LoaiPhong getLoaiPhongBySoPhong(String soPhong) {
        LoaiPhong loaiPhong = null;
        String sql = "SELECT lp.* FROM LoaiPhong lp JOIN Phong p ON lp.maLoaiPhong = p.loaiPhong WHERE p.soPhong = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, soPhong);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                loaiPhong = new LoaiPhong(
                    rs.getString("maLoaiPhong"),
                    rs.getString("tenLoai"),
                    rs.getInt("soLuong"),
                    rs.getFloat("dienTich"),
                    rs.getBigDecimal("giaTheoGio").doubleValue(),
                    rs.getBigDecimal("giaTheoNgay").doubleValue(),
                    rs.getBigDecimal("giaTheoDem").doubleValue(),
                    rs.getBigDecimal("phuThuQuaGio").doubleValue()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loaiPhong;
    }

}
