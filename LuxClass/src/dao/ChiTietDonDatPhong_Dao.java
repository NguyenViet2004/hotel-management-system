package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChiTietDonDatPhong;
import entity.DonDatPhong;
import entity.Phong;

public class ChiTietDonDatPhong_Dao {
	private ArrayList<ChiTietDonDatPhong> dsctddp;
    
    public ChiTietDonDatPhong_Dao() {
    	dsctddp = new ArrayList<ChiTietDonDatPhong>();
    }

    public ArrayList<ChiTietDonDatPhong> getAllChiTietDonDatPhong() {
        try (Connection con = ConnectDB.getConnection()) {
            String sql = "select * from ChiTietDonDatPhong";
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            while (rs.next()) {
            	DonDatPhong donDatPhong = new DonDatPhong(rs.getString(1));
                Phong phong = new Phong(rs.getString(2));
                int soLuong = rs.getInt(3);

                ChiTietDonDatPhong ctddp = new ChiTietDonDatPhong(donDatPhong,phong,soLuong);
                dsctddp.add(ctddp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dsctddp;
    }
    
    public int CountNumberRoom(String tenLoaiPhong) {
        int soLuong = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // KHÔNG dùng try-with-resources ở đây
            Connection conn = connectDB.ConnectDB.getConnection();
            String sql = "SELECT COUNT(*) AS SoLuongPhong " +
                         "FROM Phong p " +
                         "JOIN LoaiPhong lp ON p.loaiPhong = lp.maLoaiPhong " +
                         "WHERE lp.tenLoai = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setNString(1, tenLoaiPhong); // dùng setNString cho Unicode (có chữ tiếng Việt)

            rs = stmt.executeQuery();

            if (rs.next()) {
                soLuong = rs.getInt("SoLuongPhong");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // KHÔNG được đóng conn ở đây!
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return soLuong;
    }
    
    public int GetPriceToDay(String tenLoaiPhong) {
        int giaTheoNgay = 0;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectDB.getConnection(); // Sử dụng ConnectDB
            String sql = "SELECT giaTheoNgay FROM LoaiPhong WHERE tenLoai = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setNString(1, tenLoaiPhong); // truyền giá trị có dấu

            rs = stmt.executeQuery();
            if (rs.next()) {
                giaTheoNgay = rs.getInt("giaTheoNgay");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Đóng tài nguyên sau khi sử dụng
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                // KHÔNG đóng kết nối nếu đang tái sử dụng ConnectDB
                // ConnectDB.closeConnection(); <-- chỉ đóng khi cần dừng toàn bộ
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return giaTheoNgay;
    }



}
