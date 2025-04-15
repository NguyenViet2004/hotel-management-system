package dao_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.LoaiPhong;

public class LoaiPhong_DAO {

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