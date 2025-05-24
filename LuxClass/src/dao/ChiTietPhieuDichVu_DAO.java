package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import connectDB_CuaXien.ConnectDB;

public class ChiTietPhieuDichVu_DAO {

    public boolean xoaChiTietPhieuDichVuTheoMaDichVu(String maDichVu){
        String sql = "DELETE FROM ChiTietPhieuDichVu WHERE maDichVu = ?";

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maDichVu);
            int rowsDeleted = stmt.executeUpdate();

            // Nếu có ít nhất một hàng bị xóa, tức là xóa thành công
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Xóa thất bại
        }
    }
}
