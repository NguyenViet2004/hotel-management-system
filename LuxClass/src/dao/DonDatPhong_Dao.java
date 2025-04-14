package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DonDatPhong;


public class DonDatPhong_Dao {
	private ArrayList<DonDatPhong> dsddp;
    public DonDatPhong_Dao() {
    	dsddp = new ArrayList<DonDatPhong>();
    }
 // Hàm xóa đơn đặt phòng theo mã
    public boolean xoaDonDatPhong(String maDonDatPhong) {
        Connection con = ConnectDB.getConnection();
        String sql = "DELETE FROM DonDatPhong WHERE maDonDatPhong = ?";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, maDonDatPhong);
            int n = stmt.executeUpdate();
            return n > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
