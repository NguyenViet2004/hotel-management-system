package dao_CuaXien;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB_CuaXien.ConnectDB;
import entity_CuaXien.LoaiDichVu;

public class loaiDichVu_DAO {
	public List<LoaiDichVu> getLoaiDichVuByMaPhieu(String maPhieuDichVu) {
        List<LoaiDichVu> list = new ArrayList<>();

        String sql = """
            SELECT DISTINCT ld.maLoai, ld.tenLoai
            FROM ChiTietPhieuDichVu ct
            JOIN DichVu dv ON ct.maDichVu = dv.maDV
            JOIN LoaiDichVu ld ON dv.maLoai = ld.maLoai
            WHERE ct.maPhieuDichVu = ?
        """;

        try (Connection conn = ConnectDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhieuDichVu);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maLoai = rs.getString("maLoai");
                String tenLoai = rs.getString("tenLoai");

                LoaiDichVu ldv = new LoaiDichVu(maLoai, tenLoai);
                list.add(ldv);
            }

        } catch (SQLException e) {
            e.printStackTrace(); // hoặc xử lý log riêng nếu cần
        }

        return list;
    }
}
