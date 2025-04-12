package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietDonDatPhong;
import entity.DonDatPhong;
import entity.Phong;

public class ChiTietDonDatPhong_Dao {
	private ArrayList<ChiTietDonDatPhong> dsctddp;
	private Connection connection;
    public ChiTietDonDatPhong_Dao() {
    	dsctddp = new ArrayList<ChiTietDonDatPhong>();
    	connection = ConnectDB.getConnection();
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
    
    public int countSoPhongTrong(Timestamp tuNgay, Timestamp denNgay, String loaiPhong) throws SQLException {
        int soLuong = 0;

        // Lấy LocalDate từ Timestamp rồi set giờ
        LocalDateTime tuNgay14h = tuNgay.toLocalDateTime().toLocalDate().atTime(14, 0);
        LocalDateTime denNgay12h = denNgay.toLocalDateTime().toLocalDate().atTime(12, 0);

        Timestamp tuNgayTimestamp = Timestamp.valueOf(tuNgay14h);
        Timestamp denNgayTimestamp = Timestamp.valueOf(denNgay12h);

        String sql = """
            SELECT COUNT(*) AS SoPhongTrong
            FROM Phong
            WHERE loaiPhong = ?
              AND soPhong NOT IN (
                SELECT CT.soPhong
                FROM ChiTietDonDatPhong CT
                JOIN DonDatPhong DDP ON CT.maDonDatPhong = DDP.maDonDatPhong
                WHERE
                    DDP.ngayNhanPhong < ?
                    AND DDP.ngayTraPhong > ?
              )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loaiPhong);
            stmt.setTimestamp(2, denNgayTimestamp); // ngày nhận < 12:00 ngày kết thúc
            stmt.setTimestamp(3, tuNgayTimestamp);  // ngày trả > 14:00 ngày bắt đầu

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                soLuong = rs.getInt("SoPhongTrong");
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

    public List<String> layDanhSachPhongTrong(Timestamp tuNgay, Timestamp denNgay, String loaiPhong) throws SQLException {
        List<String> danhSachPhong = new ArrayList<>();

        String sql = """
            SELECT soPhong
            FROM Phong
            WHERE loaiPhong = ?
              AND soPhong NOT IN (
                SELECT CT.soPhong
                FROM ChiTietDonDatPhong CT
                JOIN DonDatPhong DDP ON CT.maDonDatPhong = DDP.maDonDatPhong
                WHERE
                    DDP.ngayNhanPhong < ?
                    AND DDP.ngayTraPhong > ?
              )
        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, loaiPhong);

            // Convert Timestamp -> LocalDateTime -> chỉnh giờ -> lại thành Timestamp
            LocalDateTime tuNgay14h = tuNgay.toLocalDateTime().withHour(14).withMinute(0).withSecond(0).withNano(0);
            LocalDateTime denNgay12h = denNgay.toLocalDateTime().withHour(12).withMinute(0).withSecond(0).withNano(0);

            Timestamp tuNgayTimestamp = Timestamp.valueOf(tuNgay14h);
            Timestamp denNgayTimestamp = Timestamp.valueOf(denNgay12h);

            stmt.setTimestamp(2, denNgayTimestamp);
            stmt.setTimestamp(3, tuNgayTimestamp);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                danhSachPhong.add(rs.getString("soPhong"));
            }
        }

        return danhSachPhong;
    }

    
    public int demSoLuongDonTrongNgay() {
        int soLuongDon = 0;
        String sql = "SELECT COUNT(*) AS SoLuongDon FROM DonDatPhong WHERE CONVERT(DATE, ngayNhanPhong) = CONVERT(DATE, GETDATE())";

        try (
            Connection conn = ConnectDB.getConnection();  
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
        ) {
            if (rs.next()) {
                soLuongDon = rs.getInt("SoLuongDon");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return soLuongDon;
    }



}
